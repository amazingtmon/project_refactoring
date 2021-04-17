package com.client;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateChattingView extends JFrame {
	//선언부
	String onlines[] = null;
	
	JPanel jp_north = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JLabel jlb_selectUser = new JLabel("접속중인 유저");
	JCheckBox[] jcb_online = null;
	JButton jbtn_create = new JButton("방 만들기");
	
	//생성자
	
	public CreateChattingView() {
		//initDisplay();
	}
	
	//화면처리부
	public void initDisplay() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		jp_center.setBackground(Color.WHITE);
		jbtn_create.addActionListener(null);
		jlb_selectUser.setFont(new Font("고딕체", Font.BOLD, 15));
		jp_north.add(jlb_selectUser);
		jp_south.add(jbtn_create);
		jp_north.setBackground(Color.WHITE);
		add("North",jp_north);
		add("Center",jp_center);
		add("South",jp_south);
		setTitle("초대 유저 선택");
		setBounds(1000, 200, 300, 400);
		setVisible(true);
	}
	
	
}
