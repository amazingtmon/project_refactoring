package com.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ActionHandler implements ActionListener, ItemListener, FocusListener{
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
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
	//로그인
		if(obj == logView.jbtn_login || obj == logView.jtf_pw) {
			try {
				client.checkLogin(logView.jtf_id.getText(), logView.jtf_pw.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	//로그인 창에서 회원가입 버튼 누름(회원가입 신청)
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
	//기본화면
		
		
	//유저선택화면
		else if(obj == defView.jbtn_chat) {
			System.out.println("Clicked 방 만들기!!"); 
			//방 만들기 버튼 두번 눌렀을때 중복창 안뜨게 하기!!!!!!!
			//ccView = new CreateChattingView(this);
			ccView.jp_center = new JPanel(new GridLayout(defView.dtm_online.getRowCount(),1,2,2)); //접속중 유저만큼 그리드레이아웃 만들기
			ccView.onlines = new String[defView.dtm_online.getRowCount()]; 	  //dtm값 넣을 배열 크기 초기화
			ccView.jcb_online = new JCheckBox[defView.dtm_online.getRowCount()]; //체크 박스 크기 초기화
			
			for(int i=0; i<defView.dtm_online.getRowCount(); i++) {    
				if(!defView.p_id.equals(defView.dtm_online.getValueAt(i, 0))) {
					ccView.onlines[i]=defView.dtm_online.getValueAt(i, 0).toString(); //dtm값을 배열에 넣기
					ccView.jcb_online[i] = new JCheckBox(ccView.onlines[i]); //배열의 값을 담은 체크박스 생성
					ccView.jp_center.add(ccView.jcb_online[i]); //체크박스 패널에 추가
					ccView.jcb_online[i].addItemListener(this); //이벤트 처리
					}
			}
			ccView.initDisplay();
		}
		
	//초대 유저 서버에 보내기
		else if(obj == ccView.jbtn_create) {
			//System.out.println("이게 눌렸다고????????왜");
			String roomName = JOptionPane.showInputDialog(null,"채팅방 이름을 설정해주세요.");
			ccView.dispose();//유저초대창 닫기
			
			try {
				client.createRoom(defView.p_id,ccView.selected_ID,roomName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	//채팅화면
		else if(obj==chatView.jtf_msg||obj==chatView.jbtn_send) {
			System.out.println("button action!! 메세지 전송");
			String chat_msg = chatView.jtf_msg.getText();
			try {
				client.sendMessage(chatView.p_id,chatView.roomName,chat_msg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			chatView.jtf_msg.setText("");
		}
		
		
	//파일전송화면
		
	
	}///////////////////////////////////////////////////////end of actionPerformed
	
	
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
		Object obj = ie.getSource();
		System.out.println("itemStateChanged 호출");
		if(ie.getStateChange() == ie.SELECTED) {
			ccView.selected_ID.add(((JCheckBox) ie.getSource()).getText()); //체크박스의 값 들어가야함.
			System.out.println("체크 성공   :   "+ccView.selected_ID);
		}
		
		else if(ie.getStateChange() == ie.DESELECTED) {
			ccView.selected_ID.remove(((JCheckBox) ie.getSource()).getText()); //체크박스의 값 들어가야함.
			System.out.println("체크 해제   :   "+ccView.selected_ID);
		}
		System.out.println("넘어가는 값"+ccView.selected_ID);
	}///////////////////////////////////////////////////////end of itemStateChanged


}
