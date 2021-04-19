package com.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class FTSDao {
	DBConnectionMgr dbMgr = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	CallableStatement cstmt = null;
	ResultSet rs = null;
	
	public FTSDao() {
		dbMgr = DBConnectionMgr.getInstance();
		try {
			con = dbMgr.getConnection();
		} catch (Exception e) {
			System.out.println("오라클 연결 실패"+e.toString());
		}
	}
	/***************************************************
	 * 
	 * @param p_id = 사용자가 입력한 아이디
	 * @param p_pw = 사용자가 입력한 비밀번호
	 * @return 
	 * difid = 아이디틀림, difpw = 비밀번호 틀림, p_id = 로그인성공
	 **************************************************/
	public String loginCheck(String p_id, String p_pw) {
		String msg = "";
		StringBuilder sql = new StringBuilder();
		sql.append("{call proc_login(?, ?, ?)}");
		try {
			cstmt = con.prepareCall(sql.toString());
			cstmt.setString(1, p_id);
			cstmt.setString(2, p_pw);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			int result = 0;
			result = cstmt.executeUpdate();
			if(result == 1) {
				msg = cstmt.getString(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            dbMgr.freeConnection(con, cstmt);
        }
		return msg;
	}
	//온라인 유저 명단을 입력했을 때 전체유저목록 중 오프라인인 유저만 출력이 나와야 함.
	public void userList() {
		StringBuilder sql = new StringBuilder();
		sql.append("select fid from filemem");
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getUser() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from filemem where = ?");
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "jsj00203");
			rs = pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String addUser(String p_id ,String p_pw, String p_name){
		String msg = "";
		StringBuilder sql = new StringBuilder();
		sql.append("insert into filemem values(?,?,?,to_char(sysdate,'yyyymmdd'))");
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,p_id);
			pstmt.setString(2,p_pw);
			pstmt.setString(3,p_name);
			result	= pstmt.executeUpdate();
			if(result == 1) {
				System.out.println("회원가입 성공");
				msg = p_id;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dbMgr.freeConnection(con, pstmt);
		}
		return msg;//클라이언트로 보내는 결과값을 리턴해줘야됨
		
	}
}
