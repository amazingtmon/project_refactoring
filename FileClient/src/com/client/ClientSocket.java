package com.client;

import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	
	
	public void createRoom(String p_id,List<String> selected_ID,String roomName) throws IOException {
		 //#200#요청아이디#초대된아이디들#채팅방이름
		String msg = Protocol.createRoom
					+Protocol.seperator+p_id
					+Protocol.seperator+selected_ID
					+Protocol.seperator+roomName;
		oos.writeObject(msg);
		//send(Protocol.createRoom,selected_ID,roomName); //#200#요청아이디#초대된아이디들#채팅방이름
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
	 *  메소드
	 */
	
	/**
	 *  메소드
	 */
	public void showUser (List<String> onlineUser, List<String> offlineUser) {
		while(thread.defView.dtm_online.getRowCount() > 0) {
			thread.defView.dtm_online.removeRow(0);
		}
		for(int i=0; i< onlineUser.size(); i++) {
			Vector<Object> onRow = new Vector<Object>();
			onRow.add(onlineUser.get(i)); 
			thread.defView.dtm_online.addRow(onRow);
		}
		while(thread.defView.dtm_offline.getRowCount() > 0) {
			thread.defView.dtm_offline.removeRow(0);
		}
		for(int i=0; i< offlineUser.size(); i++) {
			Vector<Object> onRow = new Vector<Object>();
			onRow.add(offlineUser.get(i));
			thread.defView.dtm_offline.addRow(onRow);
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
