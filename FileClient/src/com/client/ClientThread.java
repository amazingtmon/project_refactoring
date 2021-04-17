package com.client;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.common.Protocol;

//서버로부터 수신받은 오브젝트를 처리하는 클래스
public class ClientThread extends Thread{
	ClientSocket client = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스
	
	LoginView logView = null;
	AddUserView addView = null;
	DefaultView defView = null;
	CreateChattingView ccView = null; //얘도 어디선가 초기화 해줘야함 아니면 null뜬다..
	ChatRoomView chatView = null;
	SelectFileView selectView = null;
	
	ActionHandler action = null;
	
	public ClientThread(ClientSocket client) {
		System.out.println("ClientThread() called");
		this.client = client;
		action = new ActionHandler();// 액션리스너클래스 실행
		logView = new LoginView(action);// 최초 로그인 뷰 실행
		action.setInstance(logView, client); // 액션리스너클래스에 로그인뷰 주소번지 인입
	}
	
	private List<String> decompose(String result){
		List<String> list = new Vector<>();
		String[] values = result.replaceAll("\\p{Punct}", "").split(" ");
		for(String str:values) {
			list.add(str);
			}
		return list;
	}
	
	public void run(){
		boolean isStop = false;
		while(!isStop) {
			try {
				String msg = client.ois.readObject().toString();
				StringTokenizer st = new StringTokenizer(msg, "#");
				switch(st.nextToken()) {
				case Protocol.checkLogin:{//100#result(overlap)
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
					else if(logView.jtf_id.getText().equals(result)) {
						//온라인 리스트 벡터 가져오기
						Protocol.p_id = result;
						logView.dispose(); //기존 화면 닫음
						defView = new DefaultView(action, Protocol.p_id);
						action.setInstance(defView);
					}
				}break;
				case Protocol.addUser:{//110#
					
					
				}break;
				case Protocol.showUser:{//120#onlineUser#offlineUser
					String first = st.nextToken();//온라인유저
					String second = st.nextToken();//오프라인유저
					List<String> onlineUser,offlineUser = null;
					onlineUser = decompose(first);
					offlineUser  = decompose(second);
					client.showUser(onlineUser, offlineUser);
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
