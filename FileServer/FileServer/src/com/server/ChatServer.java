package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

//1개만 존재하는 서버, 모든 클라이언트의 연결 및 클라이언트 1:1 맞춤 쓰레드 할당 담당
//접속중인 쓰레드 관리도 담당
public class ChatServer extends ServerSocket implements Runnable{
   private Thread thread = null;
   
   public Map<String, ChatSocket> onlineUser = null;
   public List<String> offlineUser = null;
   public Map<String, List<ChatSocket>> chatRoom = null;
   
   /************************
    * Key : 접속된 유저 아이디
    * Value : 접속된 유저의 소켓
    ************************/
   public ChatServer(int port) throws IOException {
      super(port);//서버 소켓 생성.
      System.out.println("Chatserver 생성자 실행.");
      onlineUser = new Hashtable<String, ChatSocket>();
      offlineUser = new Vector<String>();
      chatRoom = new Hashtable<String, List<ChatSocket>>();
      this.start();
   }
   
   /**
    * 서버 개시
    */
   public void start() {
      thread = new Thread(this);
      thread.start();//ChatServer.run(); 실행.
   }
   
   /**
    * 클라이언트가 접속을 할 때 실행되는 메소드
    * @param chatServer 
    * @param chatServer 
    */
   public ChatSocket accpet() throws IOException {
      ChatSocket chat = new ChatSocket(this);
      implAccept(chat);
      chat.serverStart();
      return chat;
   }
   
   @Override
   public void run() { // 클라이언트 접속을 계속 받아야 하기때문에 쓰레드 구성
      System.out.println("run 실행");
      boolean isStop = false;
      while (!isStop) {
         try {
            ChatSocket chat = this.accpet();
            System.out.println(chat+" - 연결 성공");
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}