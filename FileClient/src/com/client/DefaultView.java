package com.client;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class DefaultView extends JFrame{
	JPanel jp_north = new JPanel();
	JLabel jlb_name = new JLabel();  //사용자이름
	//중단1
	JPanel jp_online = new JPanel();
	JLabel jlb_online = new JLabel("온라인");
	String online[] = {"아이디"};
	DefaultTableModel dtm_online = new DefaultTableModel(online,0); 
	JTable jtb_online = new JTable(dtm_online);
	JScrollPane jsp_online = new JScrollPane(jtb_online);
	//중단2
	JPanel jp_offline = new JPanel();
	JLabel jlb_offline = new JLabel("오프라인");
	String offline[] = {"아이디"};
	DefaultTableModel dtm_offline = new DefaultTableModel(online,0); 
	JTable jtb_offline = new JTable(dtm_offline);
	JScrollPane jsp_offline = new JScrollPane(jtb_offline);
	//하단
	JPanel jp_south   = new JPanel();
	JButton jbtn_chat = new JButton("방 만들기");

	public DefaultView() {
		initDisplay();
	}
	
	public void initDisplay() {
		//상단
		//절대값으로 위치 선정
		jp_north.setBounds(0, 20, 500, 40);
		jp_online.setBounds(0, 60, 500, 200);
		jp_offline.setBounds(0, 280, 500, 200);
		jp_south.setBounds(0, 500, 500, 40);
		/////////////////
		jlb_name.setFont(new Font("맑은고딕",Font.BOLD,15));
		jp_north.add(jlb_name);

		//중단
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
		TableColumnModel tcm = jtb_online.getColumnModel() ; // 정렬할 테이블의 컬럼모델을 가져옴
		for(int i = 0 ; i < tcm.getColumnCount() ; i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		} 
		tcm = null;
		tcm = jtb_offline.getColumnModel() ; // 정렬할 테이블의 컬럼모델을 가져옴
		for(int i = 0 ; i < tcm.getColumnCount() ; i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		jtb_online.addMouseListener(null);
		jp_online.add(jlb_online);
		jp_online.add(jsp_online);
		jp_offline.add(jlb_offline);
		jp_offline.add(jsp_offline);
		jtb_online.addMouseListener(null);
		jtb_offline.addMouseListener(null);

		//하단
		jbtn_chat.addActionListener(null);
		jp_south.add(jbtn_chat);

		//프레임
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setLayout(null);
		//jf.getContentPane().setLayout(new GridLayout(0,1));
		this.add(jp_north);
		this.add(jp_online); //온라인 테이블 적용
		this.add(jp_offline); //오프라인 테이블 적용
		this.add(jp_south);
		this.setBounds(650,200,500,600);
		this.setVisible(true);
	}
}
