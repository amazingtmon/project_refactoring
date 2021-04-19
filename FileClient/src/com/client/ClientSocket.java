package com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.common.Protocol;
import com.file.ClientAddress;

//전송관련 메소드를 전부 담당하는 클래스
public class ClientSocket extends Socket{
	private ClientAddress address = null;//클라이언트 소켓.
	public ObjectOutputStream oos = null;
	public ObjectInputStream ois = null;
	private Stack<Exception> errorList = null;
	ClientThread thread = null;
	
	public ClientSocket(ClientAddress address) throws IOException {
		System.out.println("ClientSocket called");
		this.address = address;
		connection();
	}
	
	public void send(String... str) throws IOException {
	      String msg = "";
	      for(int i=0;i<str.length;i++) {
	         if(i==str.length-1) 
	            msg = msg+str[i];
	         else 
	            msg = msg+str[i]+Protocol.seperator;            
	      }
	      System.out.println("C_Socket_send: "+msg);
	      oos.writeObject(msg);
	   }
	
	/**
	 * 서버 접속 메소드
	 */
	private void connection() throws IOException {
		super.connect(address);
		System.out.println("connection() called");
		oos = new ObjectOutputStream(getOutputStream());
		ois = new ObjectInputStream(getInputStream());
		thread = new ClientThread(this);
		thread.start();
		//구분을 줘서 서버연결 성공 시 쓰레드 실행, 서버 연결 불가시 메세지 출력
	}
	/**
	 *  로그인 시도 메소드
	 * @param p_pw 
	 * @param p_id 
	 * @throws IOException 
	 */
	public void checkLogin(String p_id, String p_pw) throws IOException {
		send(Protocol.checkLogin, p_id, p_pw);
	}

	/**
	 *  showUser 메소드
	 */
	public void showUser (List<String> onlineUser, List<String> offlineUser) {
		while(thread.defaultview.dtm_online.getRowCount() > 0) {
			thread.defaultview.dtm_online.removeRow(0);
		}
		for(int i=0; i< onlineUser.size(); i++) {
			Vector<Object> onRow = new Vector<Object>();
			onRow.add(onlineUser.get(i)); 
			thread.defaultview.dtm_online.addRow(onRow);
		}
		while(thread.defaultview.dtm_offline.getRowCount() > 0) {
			thread.defaultview.dtm_offline.removeRow(0);
		}
		for(int i=0; i< offlineUser.size(); i++) {
			Vector<Object> onRow = new Vector<Object>();
			onRow.add(offlineUser.get(i));
			thread.defaultview.dtm_offline.addRow(onRow);
		}
	}
	
	/**
	 * AddUser 메소드
	 */
	public void addUser(String new_id, String new_pw, String new_name) throws IOException{
		send(Protocol.addUser, new_id, new_pw, new_name);
	}

	public void addResult(String result) {
		if("성공".equals(result)) {
			JOptionPane.showMessageDialog(thread.addView, thread.addView.jtf_id.getText()+"님 가입을 환영합니다.");
			thread.addView.dispose();
		}
	}
	
	/**
	 *  createChatRoom 메소드
	 */
	public void createroom(String p_id, Vector<String> withRoomIDs) {
		String roomName = JOptionPane.showInputDialog(null,"채팅방 이름을 설정해주세요.");
		String RoomIDs = withRoomIDs.toString();
//		String msg = Protocol.createRoom 
//					+ Protocol.seperator+name
//					+Protocol.seperator+withRoomIDs
//					+Protocol.seperator+roomName;
		try {
//			oos.writeObject(msg);
			send(Protocol.createRoom,p_id,RoomIDs,roomName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */
	
}
