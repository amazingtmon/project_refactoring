package com.client;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatRoomView extends JFrame{
	ActionHandler action = null;
	
	String roomName = null; //방 이름
	String p_id = null; //현재 유저 아이디
	
	JPanel	jp_first = new JPanel();
	JPanel	jp_first_south = new JPanel();
	
	JTextField jtf_msg = new JTextField(); 
	JTextArea jta_display = new JTextArea();
	JScrollPane jsp_display = new JScrollPane(jta_display);
	
	JButton jbtn_send  = new JButton("전송");
	
	
	public ChatRoomView(ActionHandler action,String p_id, String roomName) {
		this.action = action;
		this.p_id = p_id;
		this.roomName = roomName;
		//initDisplay();
	}
	
	
	public void initDisplay() {
		jtf_msg.addActionListener(action);
		jbtn_send.addActionListener(action);
		jta_display.setFont(new Font("고딕체",Font.BOLD,20));
		
		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);
		
		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		
		setTitle("방 이름 : "+roomName + "  /  내 아이디 : "+p_id); //앞에 방 이름 나중에 삭제할 것
		add(jp_first);
		setBounds(1000, 200, 500, 600);
		//jf.setSize(500, 600);
		setVisible(true);
	}
	
}
