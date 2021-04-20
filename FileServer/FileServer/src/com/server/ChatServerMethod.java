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

   //100번 로그인 체크////////////////////////////////////////
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
   }
   
   //유저 메인뷰에 보여주기
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
   
   
   //200번 채팅방 열기////////////////////////////
   public void openRoom(String p_id, List<String>selected_ID, String roomName) {
	   List<ChatSocket> userSocket = new Vector<ChatSocket>();
	   userSocket.add(chatsocket.chatServer.onlineUser.get(p_id)); //내 자신(소켓) 리스트에 넣기
	   for(String id:selected_ID) {
		   userSocket.add(chatsocket.chatServer.onlineUser.get(id)); //선택된 아이디들(소켓) 리스트에 넣기
		}
	   chatsocket.chatServer.chatRoom.put(roomName,userSocket);
	   
	   try {//200#p_id#roomName
		   chatsocket.oos.writeObject(Protocol.createRoom
									+Protocol.seperator+p_id
									+Protocol.seperator+roomName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
   }
   
   //300번 메세지 보내기//////////////////////////
   public void sendMessage(String p_id, String roomName, String chat_msg) {
	   try {
		   //roomName의 이름을 가진 채팅방 유저 소켓들을 꺼내 넣을 벡터 초기화
		   List<ChatSocket> room_user_socket = new Vector<ChatSocket>();
		   //roomName의 이름을 키로 가진 소켓들을 넣어줌
		   room_user_socket.addAll(chatsocket.chatServer.chatRoom.get(roomName));
		   //가져온 유저소켓들에게 각각 oos를 쏘기
		   for(ChatSocket user:room_user_socket) {
			  //300#p_id(말한 사람)#방이름#채팅메세지
			  user.oos.writeObject(Protocol.sendMessage
					   			  +Protocol.seperator+p_id
					   			  +Protocol.seperator+roomName
					   			  +Protocol.seperator+chat_msg);
		   }
		   
	   	} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
   
   //
   
   
}
      
