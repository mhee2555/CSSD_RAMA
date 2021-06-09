package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.poseintelligence.cssd.db.Conn;

@SuppressWarnings("rawtypes")
public class CssdLogin extends GenericForwardComposer{
	
	private static final long serialVersionUID = -8716887210135693330L;
	
	// Widget
	private Textbox Textbox_UserName;
	private Textbox Textbox_Password;
	private Label 	Label_DB;
	private Label 	Label_Caption;
	
	public void onCreate() throws Exception {
		String S_DB = Label_DB.getValue();
		
		Label_Caption.setValue( ( S_DB.equals("") || S_DB.equals("cssd_2_usage_test")) ? "เข้าสู่ระบบ (สำหรับทดลอง)" : ("เข้าสู่ระบบ " + S_DB.replace("2_usage_", "") ) );
    }
	
	public void onOK$Textbox_UserName(Event event) throws Exception {
		if(Textbox_Password.getText().toString().trim().equals("")) {
			Textbox_Password.focus();
		}else {
			onLogin();
		}
	}
	
	public void onOK$Textbox_Password(Event event) throws Exception {
		onLogin();
	}
	
	public void onClick$Button_Submit(Event event) throws Exception {
		onLogin();	
	}
	
	private void onLogin() throws Exception{
		String S_UserName = Textbox_UserName.getText();
		String S_Password = Textbox_Password.getText();
		
		S_Password = S_Password.replace("'", "");
		
		String S_DB = Label_DB.getValue();
		
		//S_DB = S_DB.equals("") ? "cssd_2_usage_test" : S_DB;
		
		com.poseintelligence.cssd.db.Conn c = new Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        System.out.println(c.getHost(S_DB));
        
        Statement stmt = conn.createStatement();
        Statement stmt_ = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			String S_Sql = 
					  	"SELECT 	`users`.ID,"
					+ 	"			employee.DepID, " 
					+ 	"			CONCAT('คุณ ', employee.FirstName , ' ' , employee.LastName) AS UserName, "
					+ 	"			department.DepName2, "
					+ 	"			if(`employee`.`IsAdmin`,'1','0') AS IsAdmin, "
					+ 	"			employee.EmpCode "
			
					+ 	"FROM 		users " 
								
					+ 	"INNER JOIN employee "
					+ 	"ON 		users.EmpCode = employee.EmpCode "
					
					+ 	"INNER JOIN department "
					+ 	"ON 		department.ID = employee.DepID " 
								
					+ 	"WHERE 		users.`UserName` = '" + S_UserName + "' " 
					+ 	"AND 		users.`Password` = '" + S_Password + "' " 
										
					+ 	"LIMIT 1 ";
			
			rs = stmt.executeQuery( S_Sql );
			
			if(rs.next()) {
				
				String S_Sql_ =
						
						"INSERT INTO user_login_log ( "
					+ 	"	user_id, "
					+ 	"	login_mode, "
					+ 	"	login_date "
					+ 	") VALUES ( "
					+ 	"	" + rs.getString("ID") + ", "
					+ 	"	1, "
					+ 	"	NOW() "
					+ 	") ";
				
				stmt_.executeUpdate( S_Sql_ );
				
				HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
				Cookie userCookie = new Cookie("S_DB", S_DB);
				response.addCookie(userCookie);
				
				Session session = Sessions.getCurrent();
				session.setAttribute("S_UserId", rs.getString("ID"));
				session.setAttribute("S_DeptId", rs.getString("DepID"));
				session.setAttribute("S_UserName", rs.getString("UserName"));
				session.setAttribute("S_IsAdmin", rs.getString("IsAdmin"));
				session.setAttribute("S_EmpCode", rs.getString("EmpCode"));
				session.setAttribute("S_DepName", rs.getString("DepName2"));
				session.setAttribute("S_DB", S_DB);		
				
				Executions.sendRedirect("department_menu.zul");
				
			}else {
				Messagebox.show("ไม่พบผู้ใช้งานนี้ !!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
			Messagebox.show("Error : " + e.getMessage());
		}finally{
			if (rs != null) {
                rs.close();
            }
            
            if (stmt != null) {
                stmt.close();
            }
            
            if (stmt_ != null) {
            	stmt_.close();
            }
            
            if (conn != null) {
                conn.close();
            }
		}
	}
}
