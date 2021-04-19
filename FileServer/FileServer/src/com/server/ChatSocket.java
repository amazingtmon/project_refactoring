package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import com.common.Protocol;

//소켓과 쓰레드를 하나의 클래스로 제작, 수신받은 오브젝트를 처리 후 송신하는 역할 담당.
//모든 기능은 메소드로 대체
public class ChatSocket extends Socket implements Runnable{
   ObjectOutputStream oos = null;
   ObjectInputStream ois = null;
   private Stack<Exception> errorList = null;
   Thread thread = null;
   
   ChatServer chatServer = null;
   ChatServerMethod chatservermethod = new ChatServerMethod(this);

   public ChatSocket(ChatServer chatServer) {
      this.chatServer = chatServer;
   }

   protected void serverStart() throws IOException {
      thread = new Thread(this);
      ois = new ObjectInputStream(getInputStream());
      oos = new ObjectOutputStream(getOutputStream());
      errorList = new Stack<Exception>();
      thread.start();//ChatSocket.run();실행.
   }
   
   /*ChatServerMethod로 이동 ㄱㄱ
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
      */

   private List<String> decompose(String result){
		List<String> list = new Vector<>();
		String[] values = result.replaceAll("\\p{Punct}", "").split(" ");
		for(String str:values) {
			list.add(str);
			}
		return list;
	}
   
   @Override
   public void run() {
      boolean isStop = false;
      if(ois==null || this==null) { //무한루프 방지
         isStop = true;
      }
      try {
         run_start://while문같은 반복문 전체를 빠져 나가도록 처리할 때
            while(!isStop) {
               String msg = ois.readObject().toString();
               System.out.println("msg: "+msg);
               StringTokenizer st = new StringTokenizer(msg, "#");
               switch(st.nextToken()) {
               case Protocol.checkLogin:{ //100#
                  String p_id = st.nextToken();
                  String p_pw = st.nextToken();
                  chatservermethod.checkLogin(p_id, p_pw);
               }break;
               case Protocol.addUser:{ //110#
					String new_id = st.nextToken();
					String new_pw = st.nextToken();
					String new_name = st.nextToken();
					chatservermethod.addUser(new_id, new_pw, new_name);
               }break;
               case Protocol.showUser:{ //120#

               }break;
               case Protocol.createRoom:{ //200#요청아이디#초대된아이디들#채팅방이름
            	   //여기에 작업
            	   String p_id = st.nextToken();
            	   List<String> selected_ID = decompose(st.nextToken());
            	   String roomName = st.nextToken();
            	   chatservermethod.openRoom(p_id,selected_ID,roomName);
               }break;
               case Protocol.closeRoom:{ //210#

               }break;
               case Protocol.sendMessage:{ //300#

               }break;
               case Protocol.sendEmoticon:{ //310#

               }break;
               case Protocol.sendFile:{ //320#

               }break;
               }
            }
      } catch(Exception e) {

      }
   }

}
