package com.poseintelligence.cssd.db;

import java.io.IOException;

public class Conn {
	
	public final String S_USER = "root";
	public final String S_PASSWORD = "A$192dijd";
	public final String S_MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    public String getHost(String db) throws IOException {
    	String S_Drivers = "jdbc:mysql://";
    	
     	//String S_ServerName = "poseintelligence.dyndns.biz";
    	//String S_Port = "3308";
    	
    	String S_ServerName = "localhost";
        String S_Port = "3306";
    	
        String S_DatabaseName = ( db == null || db.equals("") ) ? "cssd_2_usage_test" : db;
        
        //System.out.println(S_Drivers + S_ServerName + ":" + S_Port + "/" + S_DatabaseName + "?useUnicode=true&characterEncoding=UTF-8");
        
        return S_Drivers + S_ServerName + ":" + S_Port + "/" + S_DatabaseName + "?useUnicode=true&characterEncoding=UTF-8";        
    }
}
