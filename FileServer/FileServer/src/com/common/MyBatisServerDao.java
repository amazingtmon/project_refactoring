package com.common;
import java.util.List;
import java.util.Vector;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.mapper.MyBatisCommonFactory;

public class MyBatisServerDao {
	
	SqlSessionFactory sqlSessionFactory = null;
	public MyBatisServerDao() {
		sqlSessionFactory = MyBatisCommonFactory.getInstance();//마이바티스 인스턴스화.
	}
	public String gettest(){
		SqlSession sqlSession = null;
		String test = "";
	    try {
	    	sqlSession = sqlSessionFactory.openSession();
	    	test = sqlSession.selectOne("AllSearch");
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	    return test;
	}
	public String loginCheck(String p_id, String p_pw ){
		String result = null;
		System.out.println("id: "+p_id+", "+"pw: "+p_pw);
		SqlSession sqlSession = null;
		try {
	    	sqlSession = sqlSessionFactory.openSession();
	    	//result = sqlSession.selectList("loginchck");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return result;
	}
	
	public List<String> showUser(List<String> onlineUser) {
		List<String> offUser = new Vector<String>();
		System.out.println(onlineUser.toString());
		SqlSession sqlSession = null;
		try {
	    	sqlSession = sqlSessionFactory.openSession();
	    	offUser = sqlSession.selectList("showUser", onlineUser);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return offUser;
	}
	public static void main(String args[]) {
		MyBatisServerDao serDao = new MyBatisServerDao();
		List<String> onlineUser = new Vector<String>();
		onlineUser.add("abcd123");
		onlineUser.add("123");
		onlineUser.add("qwer123");
		onlineUser.add("jsj00203");
		List<String> offUser = new Vector<String>();
		offUser = serDao.showUser(onlineUser);
		System.out.println(offUser.toString());
	}
}














