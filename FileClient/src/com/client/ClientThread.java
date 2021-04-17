package com.client;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.common.Protocol;

//서버로부터 수신받은 오브젝트를 처리하는 클래스
public class ClientThread extends Thread{
	ClientSocket clientsocket = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스
	
	LoginView loginview = null;
	AddUserView addView = null;
	DefaultView defaultview = null;
	CreateChattingView ccView = null;
	ChatRoomView chatView = null;
	SelectFileView selectView = null;
	
	ActionHandler action = null;
	
	public ClientThread(ClientSocket client) {
		System.out.println("ClientThread() called");
		this.clientsocket = client;
		action = new ActionHandler();// 액션리스너클래스 실행
		loginview = new LoginView(action);// 최초 로그인 뷰 실행
		action.setInstance(loginview, client); // 액션리스너클래스에 로그인뷰 주소번지 인입
	}
	
	public void run(){
		boolean isStop = false;
		while(!isStop) {
			try {
				String msg = clientsocket.ois.readObject().toString();
				StringTokenizer st = new StringTokenizer(msg, "#");
				switch(st.nextToken()) {
				case Protocol.checkLogin:{//100#
					System.out.println("server msg: "+msg);
					String result = st.nextToken();
					if("difid".equals(result)) {
						JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다");
					}
					else if("difpw".equals(result)) {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다");
					}
					else if("overlap".equals(result)) {
						JOptionPane.showMessageDialog(null, "이미 로그인된 아이디입니다.");
					}
					else if(loginview.jtf_id.getText().equals(result)) {
						Protocol.p_id = result;
						defaultview = new DefaultView();
						loginview.dispose();//기존 화면 닫음
					}
//					defView = new DefaultView();
//					action.setInstance(defView);
					
				}break;
				case Protocol.addUser:{//110#
					
					
				}break;
				case Protocol.showUser:{//120#
					
					
				}break;
				case Protocol.createRoom:{//200#
					
					chatView = new ChatRoomView();
					
				}break;
				case Protocol.closeRoom:{//210#
					
					
				}break;
				case Protocol.sendMessage:{//300#
					
					
				}break;
				case Protocol.sendEmoticon:{//310#
					
					
				}break;
				case Protocol.sendFile:{//320#
					
					
				}break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
