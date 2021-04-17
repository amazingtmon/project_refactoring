package com.client;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame{
	ActionHandler action = null;
	
	JLabel 			jlb_id = new JLabel("ID");
	JLabel 			jlb_pw = new JLabel("PW");
	JTextField 		jtf_id = new JTextField();
	JPasswordField 	jtf_pw = new JPasswordField();
	JButton 		jbtn_login = new JButton("로그인");
	JButton 		jbtn_join  = new JButton("회원가입");
	Font 			font 	   = new Font("고딕체",Font.BOLD, 17);
	
	LoginView() {
		
	}
	
	LoginView(ActionHandler action) {
		this.action = action;
		initDisplay();
	}
	
	public void initDisplay() {

		this.setLayout(null);
		this.add(jlb_id);
		this.add(jlb_pw);
		jlb_id.setFont(font);
		jlb_pw.setFont(font);
		jlb_id.setBounds(55, 200, 80, 40);
		jlb_pw.setBounds(55, 250, 80, 40);

		this.add(jtf_id);
		jtf_pw.addActionListener(action);
		this.add(jtf_pw);
		jtf_id.setBounds(120, 200, 185, 40);
		jtf_pw.setBounds(120, 250, 185, 40);
		
		jbtn_login.addActionListener(action);
		this.add(jbtn_login);
		jbtn_login.setBounds(160, 300, 100, 40);
		
		jbtn_join.addActionListener(action);
		this.add(jbtn_join);
		jbtn_join.setBounds(160, 350, 100, 40);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Login");
		this.setBounds(700, 200, 400, 600);
		this.setVisible(true);
	}

}
