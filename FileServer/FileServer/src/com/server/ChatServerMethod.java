package com.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.common.FTSDao;
import com.common.MyBatisServerDao;
import com.common.Protocol;


public class ChatServerMethod {
	ChatSocket chatsocket = null;
	
	public ChatServerMethod(ChatSocket chatSocket) {
		this.chatsocket = chatSocket;
		System.out.println("chatsocket"+chatsocket);
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
	      chatsocket.oos.writeObject(msg);
	   }

	//100# 로그인 체크//////////////////////////////////
	public void checkLogin(String p_id, String p_pw) {
		try {
			FTSDao ftsDao = new FTSDao();
			String result = ftsDao.loginCheck(p_id, p_pw);
			//result값은 difid  or   difpw   or  로그인 성공
			if(p_id.equals(result)) {
				boolean seccess = true;
				Iterator<String> keys = chatsocket.chatServer.onlineUser.keySet().iterator();
				while(keys.hasNext()) {
					if(result.equals(keys.next())) {
						String overlap = "overlap";
						chatsocket.oos.writeObject(Protocol.checkLogin+Protocol.seperator+overlap);//중복메세지
						seccess = false;
						break;
					}
					
				}
				if(seccess) {//기존사용자가 없을때(최초접속일때), 중복로그인이 아닐때 실행됨
					System.out.println("login 성공~! ");
					chatsocket.oos.writeObject(Protocol.checkLogin+Protocol.seperator+result);//정상로그인
					chatsocket.chatServer.onlineUser.put(result, chatsocket);
					System.out.println("onlineUser: "+chatsocket.chatServer.onlineUser);
					showUser(chatsocket.chatServer.onlineUser);
				}
			}
			else { //그외 모든 경우 로그인 실패했을때
				chatsocket.oos.writeObject(Protocol.checkLogin+Protocol.seperator+result);//로그인실패메세지
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//120번 유저리스트/////////////////////////////////////
	//전체 접속자한테 유저리스트를 보내기, 온라인 오프라인 구분하기
	public void showUser(Map<String, ChatSocket> onlineuser) {
		try {
			List<String> onlineUser = new Vector<String>();
			for(String p_id:onlineuser.keySet()) {
				onlineUser.add(p_id);
			}
			MyBatisServerDao serDao = new MyBatisServerDao();
			chatsocket.chatServer.offlineUser = serDao.showUser(onlineUser);
			for(String key:chatsocket.chatServer.onlineUser.keySet()) {
				chatsocket.chatServer.onlineUser.get(key).oos.writeObject(Protocol.showUser
						+Protocol.seperator+onlineUser
						+Protocol.seperator+chatsocket.chatServer.offlineUser);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//110번 회원가입/////////////////////////////
	public void addUser(String new_id, String new_pw, String new_name) {
		try {
			FTSDao ftsDao = new FTSDao();
			String msg = ftsDao.addUser(new_id,new_pw,new_name);
			//110#내용
			chatsocket.oos.writeObject(Protocol.addUser
									+Protocol.seperator+"성공"); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//200# 채팅방 만들기//////////////////////////
	public void creatRoom(String p_id, List<String> RoomUserIds, String roomName) {
		List<ChatSocket> user = new Vector<ChatSocket>();
		user.add(chatsocket.chatServer.onlineUser.get(p_id));
		System.out.println("유저리스트의 사이즈  : "+RoomUserIds.size());
		System.out.println("요거 찍어보기  "+p_id+"  "+RoomUserIds);
		for(String id:RoomUserIds) {
			//id haeri1127  --초대된 아이디들이 id에 넣어짐
			//id test100
			//id abcd123
			// .....
			user.add(chatsocket.chatServer.onlineUser.get(id)); 
			//onlineUser<String, ThreadHandler> 안에 있는 키(id)를 통해 get해오면
			//ThreadHandler 값이 나오겠지? 그거를 user<ThreadHandler>에 넣기
			//user라는건 초대된 아이디들의 스레드를 모아둔 리스트
		}
		chatsocket.chatServer.chatRoom.put(roomName, user); //Map<roomName, user(List<ThreadHandler>)>
		//<바나나 우유방,haeri1127&abcd123>  <초콜릿 방, qwer123&...>
		System.out.println(chatsocket.chatServer.chatRoom.keySet());
		
		
		try {//200#p_id#roomName
			chatsocket.oos.writeObject(Protocol.createRoom
									+Protocol.seperator+p_id
									+Protocol.seperator+roomName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
		

