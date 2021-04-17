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
	
	
	public void checkBox(CreateChattingView ccView) {
		System.out.println("checkBox() 호출 성공");
		System.out.println("thread.defView.dtm_online.getRowCount() 성공? " + thread.defView.dtm_online.getRowCount());
		System.out.println("진짜 널 찾았다"+thread.ccView);
		System.out.println("이거는 널 아니지 그치?"+ccView);
		ccView.jp_center = new JPanel(new GridLayout(thread.defView.dtm_online.getRowCount(),1,2,2)); //접속중 유저만큼 그리드레이아웃 만들기
		ccView.onlines = new String[thread.defView.dtm_online.getRowCount()]; 	  //dtm값 넣을 배열 크기 초기화
		ccView.jcb_online = new JCheckBox[thread.defView.dtm_online.getRowCount()]; //체크 박스 크기 초기화
		
		for(int i=0; i<thread.defView.dtm_online.getRowCount(); i++) {    
			if(!thread.defView.p_id.equals(thread.defView.dtm_online.getValueAt(i, 0))) {//equals써보자
				ccView.onlines[i]=thread.defView.dtm_online.getValueAt(i, 0).toString(); //dtm값을 배열에 넣기
				//System.out.println("onlines["+i+"]  :  "+onlines[i]);
				ccView.jcb_online[i] = new JCheckBox(ccView.onlines[i]); //배열의 값을 담은 체크박스 생성
				ccView.jp_center.add(ccView.jcb_online[i]); //체크박스 패널에 추가
				//thread.ccView.jcb_online[i].addItemListener(this); //이벤트 처리
			}
		}
		ccView.initDisplay();
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
