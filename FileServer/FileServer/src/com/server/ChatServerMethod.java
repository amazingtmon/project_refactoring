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
               //System.out.println("onlineUser: "+chatsocket.chatServer.onlineUser);
               showUser(chatsocket.chatServer.onlineUser);
            }
         }
         else { //그외 모든 경우 로그인 실패했을때
            chatsocket.oos.writeObject(Protocol.checkLogin+Protocol.seperator+result);//로그인실패메세지
         }
      }catch (Exception e) {
         e.printStackTrace();
      }
   }////////////////////////////////////////end of checkLogin
   
   public void showUser(Map<String, ChatSocket> user) {
	   try {
			List<String> onlineUser = new Vector<String>();
			for(String p_id:user.keySet()) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   
	}////////////////////////////////////////end of showUser
   
   
   
}
      
