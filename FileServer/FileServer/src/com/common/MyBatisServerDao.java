package com.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public  List<Map<String,Object>> loginCheck(Map<String, Object> beforeRS ){
		List<Map<String, Object>> result = new ArrayList<>();
		System.out.println("in method: "+beforeRS);
		SqlSession sqlSession = null;
		try {
	    	sqlSession = sqlSessionFactory.openSession();
	    	System.out.println("sqlSession: "+sqlSession);
	    	result = sqlSession.selectList("loginchck", beforeRS);
	    	System.out.println("result: "+result);
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
		System.out.println("Mybatis dao: "+serDao);
//		List<String> onlineUser = new Vector<String>();
//		onlineUser.add("abcd123");
//		onlineUser.add("123");
//		onlineUser.add("qwer123");
//		onlineUser.add("jsj00203");
//		List<String> offUser = new Vector<String>();
//		offUser = serDao.showUser(onlineUser);
//		System.out.println(offUser.toString());
		
		Map<String, Object> beforeRS = new HashMap<String, Object>();
		beforeRS.put("p_id", "admin");
		beforeRS.put("p_pw", "tiger");
		System.out.println("beforeRS: "+beforeRS);
		
		List<Map<String, Object>> result_a = serDao.loginCheck(beforeRS);
		System.out.println("loginCheck(beforeRS): "+ serDao.loginCheck(beforeRS));
		System.out.println("result_a: "+result_a);
		
	}
}














