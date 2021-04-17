package com.server;

import java.util.Iterator;

import com.common.FTSDao;

import com.common.Protocol;


public class ChatServerMethod {
	ChatSocket chatsocket = null;
	
	public ChatServerMethod(ChatSocket chatSocket) {
		this.chatsocket = chatSocket;
		System.out.println("chatsocket"+chatsocket);
	}

	public void checkLogin(String p_id, String p_pw) {
		try {
			FTSDao ftsDao = new FTSDao();
			String result = ftsDao.loginCheck(p_id, p_pw);
			//result값은 difid  or   difpw   or  로그인 성공
			if(p_id.equals(result)) {
				boolean seccess = true;
				Iterator<String> keys = chatsocket.onlineUser.keySet().iterator();
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
					chatsocket.onlineUser.put(result, chatsocket);
					//showUser(chatsocket.chatServer.onlineUser);
				}
			}
			else { //그외 모든 경우 로그인 실패했을때
				chatsocket.oos.writeObject(Protocol.checkLogin+Protocol.seperator+result);//로그인실패메세지
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
		

