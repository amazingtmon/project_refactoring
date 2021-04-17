package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActionHandler implements ActionListener{
	private ClientSocket client = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스
	
	private LoginView logView = null;
	private AddUserView addView = null;
	private DefaultView defView = null;
	private ChatRoomView chatView = null;
	private CreateChattingView ccView = null;
	private SelectFileView selectView = null;
	
	ActionHandler(){}
	
	public void setInstance(LoginView logView, ClientSocket client) {
		this.logView = logView;
		this.client = client;
	}
	public void setInstance(AddUserView addView) {
		this.addView = addView;
	}
	public void setInstance(DefaultView defView) {
		this.defView = defView;
	}
	public void setInstance(ChatRoomView chatView) {
		this.chatView = chatView;
	}
	public void setInstance(CreateChattingView ccView) {
		this.ccView = ccView;
	}
	public void setInstance(SelectFileView selectView) {
		this.selectView = selectView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
	//로그인
		if(obj == logView.jbtn_login || obj == logView.jtf_pw) {
			try {
				client.checkLogin(logView.jtf_id.getText(), logView.jtf_pw.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	//회원가입
		else if(obj == logView.jbtn_join) {
			System.out.println("Clicked join!!");
		}
	//기본화면
		
	//유저선택화면
		
	//채팅화면
		
	//파일전송화면
		
	}

}
