package com.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatRoomView extends JFrame{
	ActionHandler action = null;
	
	//선언부
	String roomName = null; //방 이름
	String p_id = null; //현재 유저 아이디
	JFrame 	jf = new JFrame();
	JPanel	jp_first = new JPanel();
	JPanel	jp_first_south = new JPanel();
	
	JTextField jtf_msg = new JTextField(); 
	JTextArea jta_display = new JTextArea();
	JScrollPane jsp_display = new JScrollPane(jta_display);
	
	JButton jbtn_send  = new JButton("전송");
	
	//생성자
	public ChatRoomView(String p_id, String roomName, ActionHandler action) {
		this.p_id = p_id;
		this.roomName = roomName;
		this.action = action;
		initDisplay();
	}
	
	
	//화면처리
	public void initDisplay() {
		
		jtf_msg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("엔터키!! 메세지 전송");
				jtf_msg.setText("");
			}
			
		});
		jbtn_send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("버튼클릭!! 메세지 전송");
				jtf_msg.setText("");
			}
			
		});
		jta_display.setFont(new Font("고딕체",Font.BOLD,20));
		
		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);
		
		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		
		jf.setTitle("방 이름 : "+roomName + "  /  내 아이디 : "+p_id); //앞에 방 이름 나중에 삭제할 것
		jf.add(jp_first);
		jf.setBounds(1000, 200, 500, 600);
		//jf.setSize(500, 600);
		jf.setVisible(true);
	}
}
