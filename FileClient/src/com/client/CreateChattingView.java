package com.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateChattingView {
	ActionHandler action = null;
	DefaultView defaultview = null;
	
	//선언부
	String onlines[] = null;
	Vector<String> withRoomIDs = new Vector<>();
	
	JFrame jf = new JFrame();
	JPanel jp_north = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JLabel jlb_selectUser = new JLabel("접속중인 유저");
	JCheckBox[] jcb_online = null;
	JCheckBox jcb_apple = null;
	JButton jbtn_create = new JButton("방 만들기");
	
	//생성자
	public CreateChattingView(DefaultView defaultview, ActionHandler action) {
		this.defaultview = defaultview;
		this.action = action;
		System.out.println("param status: "+this.defaultview+this.action);
	}
	
//	public CreateChattingView() {
//		checkbox();
//		initDisplay();
//	}

	//체크박스 생성 메소드
	void checkbox() {
		jp_center = new JPanel(new GridLayout(defaultview.dtm_online.getRowCount(),1,2,2)); //접속중 유저만큼 그리드레이아웃 만들기
		onlines = new String[defaultview.dtm_online.getRowCount()]; 	  //dtm값 넣을 배열 크기 초기화
		jcb_online = new JCheckBox[defaultview.dtm_online.getRowCount()]; //체크 박스 크기 초기화
		
		for(int i=0; i<defaultview.dtm_online.getRowCount(); i++) {    
			System.out.println("포문실행?");
			if(!defaultview.p_id.equals(defaultview.dtm_online.getValueAt(i, 0))) {//equals써보자
				System.out.println("이프문 실행?");
				onlines[i]=defaultview.dtm_online.getValueAt(i, 0).toString(); //dtm값을 배열에 넣기
				System.out.println("onlines["+i+"]  :  "+onlines[i]);
				jcb_online[i] = new JCheckBox(onlines[i]); //배열의 값을 담은 체크박스 생성
				jp_center.add(jcb_online[i]); //체크박스 패널에 추가
				jcb_online[i].addItemListener(action); //이벤트 처리
			}
		}
		
	}
	
	//화면처리부
	public void initDisplay() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		jp_center.setBackground(Color.WHITE);
		jbtn_create.addActionListener(action);
		jlb_selectUser.setFont(new Font("고딕체", Font.BOLD, 15));
		jp_north.add(jlb_selectUser);
		jp_south.add(jbtn_create);
		jp_north.setBackground(Color.WHITE);
		jf.add("North",jp_north);
		jf.add("Center",jp_center);
		jf.add("South",jp_south);
		jf.setTitle("초대 유저 선택");
		jf.setBounds(1000, 200, 300, 400);
		jf.setVisible(true);
	}
}
