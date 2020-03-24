package com.qs.udp.tool;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
/*
 * 根据传入的数据库配置参数以及sql语句，返回查询的结果
 * 
 * */
public class SqlLinkFactory {
	
	public String sqlLink(String driver,String URL,String userName,String password,String sql ){
		System.out.println(driver+"=====================================");
		System.out.println(URL);
		System.out.println(userName);
		System.out.println(password);
		System.out.println(sql);
		 try {
	            Class.forName(driver);
	           
	            Connection conn = (Connection) DriverManager.getConnection(URL, userName,
	            		password);
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                System.out.println("id : " + rs.getInt(1) + " name : "
	                        + rs.getString(2) + " password : " + rs.getString(3));
	            }
	 
	            // 关闭记录集
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	 
	            // 关闭声明
	            if (ps != null) {
	                try {
	                    ps.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	 
	            // 关闭链接对象
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return "";
	}
	
//	String driver = "com.mysql.jdbc.Driver";
//    String URL="jdbc:mysql://localhost:3306/ssm?characterEncoding=utf-8";
// 	String userName="root";
// 	String passwrod="123456";
//    String sql = "select * from user";
    
   
  
   

}
