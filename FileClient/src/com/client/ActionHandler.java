package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

public class ActionHandler implements ActionListener, FocusListener{
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
				e1.printStackTrace();
			}
		}
		
		else if(obj == logView.jbtn_join) {
			addView.initDisplay();
		}
	//회원가입
		else if(obj == addView.jbtn_join || obj == addView.jtf_name) {
			System.out.println("join clicked");
			String new_id = addView.jtf_id.getText();
			String new_pw = addView.jtf_pw.getText();
			String new_name = addView.jtf_name.getText();
			try {
				client.addUser(new_id, new_pw, new_name);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	//메인화면
		else if(obj == defView.jbtn_chat) {
			System.out.println("inviteBtn clicked");
		}
		
	//유저선택화면
		
	//채팅화면
		
	//파일전송화면
		
	}
	
	//회원가입
	@Override
	public void focusGained(FocusEvent fe) {
		Object obj = fe.getSource();
		
		if(obj == addView.jtf_id ) {
			addView.jtf_id.setText("");
		}
		else if(obj == addView.jtf_pw) {
			addView.jtf_pw.setText("");
		}
		else if(obj == addView.jtf_name) {
			addView.jtf_name.setText("");
		}
		
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

}
