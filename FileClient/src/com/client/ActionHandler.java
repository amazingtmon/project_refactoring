package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JCheckBox;

public class ActionHandler implements ActionListener, FocusListener, ItemListener{
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
			ccView.checkbox();
			ccView.initDisplay();
		}
	//유저선택화면
		else if(obj == ccView.jbtn_create) {
			System.out.println("create room clicked");
			client.createroom(defView.p_id, ccView.withRoomIDs);
		}
	//채팅화면
		else if(obj == chatView.jtf_msg ||obj ==chatView.jbtn_send) {
			System.out.println("메세지 전송!!!");
			chatView.jtf_msg.setText("");
		}
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

	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object c = ie.getSource();
		System.out.println("itemStateChanged 호출");
		if(ie.getStateChange() == ie.SELECTED) {
			ccView.withRoomIDs.add(((JCheckBox) ie.getSource()).getText()); //체크박스의 값 들어가야함.
			System.out.println("체크 성공   :   "+ccView.withRoomIDs);
		}
		
		else if(ie.getStateChange() == ie.DESELECTED) {
			ccView.withRoomIDs.remove(((JCheckBox) ie.getSource()).getText()); //체크박스의 값 들어가야함.
			System.out.println("체크 해제   :   "+ccView.withRoomIDs);
		}
		
	}

}
