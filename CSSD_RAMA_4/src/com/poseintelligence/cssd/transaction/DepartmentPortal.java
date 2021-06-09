package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Window;

import com.poseintelligence.cssd.core.Setting;
import com.poseintelligence.cssd.utillity.Cons;

@SuppressWarnings("rawtypes")
public class DepartmentPortal extends GenericForwardComposer{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2088359352495714419L;
	private EventQueue<Event> qe;
	private boolean B_IsCreate = true;
	// Variable Session
	private Session session = Sessions.getCurrent();
	
	private String 	S_UserId,
					S_DeptId,
					S_UserName,
					S_IsAdmin,
					S_EmpCode,
					S_DepName,
					S_DB;
	
	private Window Window_Form;
	private Hbox Hbox_form;
	
	private String 	S_ListStatus = "-5, 5, 6";
	
	private boolean PA_IsCreateReceiveDepartment = false;
	
	private boolean B_GN_IsUsedWarningExpire = false;	
	private boolean B_GN_IsUsedWarningExpiringSoon = false;
	private boolean B_GN_IsUsedAfterReceive = false;

	
	private int I_GN_WarningExpiringSoonDay = 7;
	
	public void onCreate() throws Exception {
		
		bySession();
		
		onDisplayWebConfig();
		
		byConfig();
		
		init();

    }
	
	private void bySession(){
		if (B_IsCreate) {
			if (session.getAttribute("S_UserId") == null) {
				Executions.sendRedirect("/index.zul");
			} else {	
				S_UserId = (String)session.getAttribute("S_UserId");
				S_DeptId = (String)session.getAttribute("S_DeptId");
				S_UserName = (String)session.getAttribute("S_UserName");
				S_IsAdmin = (String)session.getAttribute("S_IsAdmin");
				S_EmpCode = (String)session.getAttribute("S_EmpCode");
				S_DepName = (String)session.getAttribute("S_DepName");
				S_DB = (String)session.getAttribute("S_DB");
	        }
			
			B_IsCreate = false;
		}
	}
	
	public void byConfig() {
		
        // -----------------------------------------------------------------------------------------
        // Get Configuration
        // -----------------------------------------------------------------------------------------
		
        Setting s = new Setting(S_DB);
        
        PA_IsCreateReceiveDepartment = s.isPA_IsCreateReceiveDepartment();
        
        
	}
	
	private void init() throws Exception{
		
			onDisplay(1);
		
		if(B_GN_IsUsedWarningExpiringSoon)
			onDisplay(2);
		
		if(B_GN_IsUsedWarningExpire)
			onDisplay(3);
		
		if(B_GN_IsUsedAfterReceive)
			onDisplay(4);
	
	}

	public void onDisplay(int selected) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	

			rs = stmt.executeQuery(getSql(selected));
					
			if(rs.next()){
				switch (selected) {
					case 1:
						
						Hbox_form.appendChild(getWindow(rs.getString("c"))); break;
					case 2:
						
						Hbox_form.appendChild(getWindow2(rs.getString("c"))); break;
					case 3:
						
						Hbox_form.appendChild(getWindow3(rs.getString("c"))); break;
					case 4:
						
						Hbox_form.appendChild(getWindow4(rs.getString("c"))); break;
					default: break;
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) {
                rs.close();
            }
            
            if (stmt != null) {
                stmt.close();
            }
            
            if (conn != null) {
                conn.close();
            }
		}
    }
	
	private String getSql(int selected) {
		switch (selected) {
			case 1:
				
				return getSqlCountDocument();
			case 2:
				
				return getSqlExpiringSoon();
			case 3:
				
				return getSqlExpire();
				
			case 4:
				return getSqlAfterReceive();
			default:
				return null;
		}
	}
	
	private String getSqlCountDocument() {
		
		String S_Sql = "";
		
		if(PA_IsCreateReceiveDepartment) {
			S_Sql = 
					
					"SELECT 	COUNT(*) AS c "
										
				+	"FROM 		payout_department "
			
				+	"WHERE 		payout_department.IsCancel = 0 " 
				+	"AND		payout_department.IsStatus IN (2) "			
				+	"AND 		payout_department.DeptID = " + S_DeptId + "  ";
		}else {
			S_Sql = 
					
				"SELECT 	COUNT(*) AS c "
									
			+	"FROM 		payout "
		
			+	"WHERE 		payout.IsCancel = 0 " 
			+	"AND		payout.IsStatus IN (2, 8) "			
			+	"AND 		payout.DeptID = " + S_DeptId + "  ";
		}
		
		return S_Sql;

	}
	
	private String getSqlExpiringSoon() {
		
		String S_Sql = "";
		
		S_Sql = 
				
			"SELECT 	COUNT(*) AS c "
								
		+	"FROM 		itemstock "
	
		+	"WHERE 		itemstock.DepID = " + S_DeptId + " "
		
		+ 	"AND 		itemstock.IsStatus IN (" + S_ListStatus + ") "
		+ 	"AND 		itemstock.IsCancel = 0 "
		+ 	"AND 		itemstock.IsPay = 1 "
		+ 	"AND 		DATE(itemstock.ExpireDate) BETWEEN DATE(NOW()) AND DATE(NOW() + INTERVAL " + I_GN_WarningExpiringSoonDay + " DAY) ";
		
		return S_Sql;

	}
	
	private String getSqlExpire() {
		
		String S_Sql = "";
		
		S_Sql = 
				
			"SELECT 	COUNT(*) AS c "
								
		+	"FROM 		itemstock "
	
		+	"WHERE 		itemstock.DepID = " + S_DeptId + " "
		
		+ 	"AND 		itemstock.IsStatus IN (" + S_ListStatus + ") "
		+ 	"AND 		itemstock.IsCancel = 0 "
		+ 	"AND 		itemstock.IsPay = 1 "
		+ 	"AND 		DATE(itemstock.ExpireDate) <= DATE(NOW()) ";
		
		return S_Sql;

	}
	
	private String getSqlAfterReceive() {
		
		String S_Sql = "";
		
		S_Sql = 
				
			"SELECT 	COUNT(*) AS c "
								
		+	"FROM 		itemstock "
	
		+	"WHERE 		itemstock.DepID = " + S_DeptId + " "
		
		+ 	"AND 		itemstock.IsStatus IN (" + S_ListStatus + ") "
		+ 	"AND 		itemstock.IsCancel = 0 "
		+ 	"AND 		itemstock.IsPay = 1 "
		+ 	"AND 		DATE(itemstock.ExpireDate) <= DATE(NOW()) ";
		
		return S_Sql;

	}

	
	private Window getWindow(String S_Count) {
		Window w = new Window();
		w.setBorder("normal");
		w.setSclass("sysWin");
		w.setHeight("175px");
		w.setWidth("300px");
		
		Button btn = new Button("ไปยังหน้ารับของเข้าสต๊อก >");
		btn.setSclass("btn-success");
		//btn.setIconSclass("z-icon-ban");
		btn.setHeight("35px");
		btn.setWidth("99%");
		btn.setStyle("background: #1eac74; color:#ffffff;border-radius: 4px;");
		//btn.setImage("/images/ic_search_c.png");
		//btn.setHoverImage("/images/pose_favicon.png");
		
		btn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				qe = EventQueues.lookup(Cons.EVENTQUEUE_CONNECTION, EventQueues.DESKTOP, true);
				qe.publish(new Event(Cons.EVENT_CALL_DEPARTMENT_RECEIVE, null, null));
	        }
	    });
		
		Separator sep = new Separator();
		sep.setHeight("35px");
		
		w.appendChild(new Caption("เอกสารใหม่รับเข้าสต๊อก"));
		w.appendChild(new Label("(" + S_Count + " เอกสาร)")) ;		
		w.appendChild(sep);
		w.appendChild(btn);
		
		return w;
	}
	
	private Window getWindow2(String S_Count) {
		Window w = new Window();
		w.setBorder("normal");
		w.setSclass("sysWin");
		w.setHeight("175px");
		w.setWidth("300px");
		
		Button btn = new Button("ไปยังหน้าอุปกรณ์ใกล้หมดอายุ >");
		btn.setSclass("btn-success");
		//btn.setIconSclass("z-icon-ban");
		btn.setHeight("35px");
		btn.setWidth("99%");
		btn.setStyle("background: #f0ad4e; color:#ffffff;border-radius: 4px;");
		//btn.setImage("/images/ic_search_c.png");
		//btn.setHoverImage("/images/pose_favicon.png");
		
		btn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				qe = EventQueues.lookup(Cons.EVENTQUEUE_CONNECTION, EventQueues.DESKTOP, true);
				qe.publish(new Event(Cons.EVENT_CALL_EXPIRING_SOON, null, null));
	        }
	    });
		
		Separator sep = new Separator();
		sep.setHeight("35px");
		
		w.appendChild(new Caption("อุปกรณ์ใกล้หมดอายุ"));
		w.appendChild(new Label("(" + S_Count + " รายการ)")) ;		
		w.appendChild(sep);
		w.appendChild(btn);
		
		return w;
	}
	
	private Window getWindow3(String S_Count) {
		Window w = new Window();
		w.setBorder("normal");
		w.setSclass("sysWin");
		w.setHeight("175px");
		w.setWidth("300px");
		
		Button btn = new Button("ไปยังหน้าอุปกรณ์หมดอายุ >");
		btn.setSclass("btn-success");
		//btn.setIconSclass("z-icon-ban");
		btn.setHeight("35px");
		btn.setWidth("99%");
		btn.setStyle("background: #d9534f; color:#ffffff;border-radius: 4px;");
		//btn.setImage("/images/ic_search_c.png");
		//btn.setHoverImage("/images/pose_favicon.png");
		
		btn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				qe = EventQueues.lookup(Cons.EVENTQUEUE_CONNECTION, EventQueues.DESKTOP, true);
				qe.publish(new Event(Cons.EVENT_CALL_EXPIRE, null, null));
	        }
	    });
		
		Separator sep = new Separator();
		sep.setHeight("35px");
		
		w.appendChild(new Caption("อุปกรณ์หมดอายุ"));
		w.appendChild(new Label("(" + S_Count + " รายการ)")) ;		
		w.appendChild(sep);
		w.appendChild(btn);
		
		return w;
	}
	
	private Window getWindow4(String S_Count) {
		Window w = new Window();
		w.setBorder("normal");
		w.setSclass("sysWin");
		w.setHeight("175px");
		w.setWidth("300px");
		
		Button btn = new Button("ไปยังหน้ารับเข้าเอกสารย้อนหลัง >");
		btn.setSclass("btn-success");
		//btn.setIconSclass("z-icon-ban");
		btn.setHeight("35px");
		btn.setWidth("99%");
		btn.setStyle("background: #d9534f; color:#ffffff;border-radius: 4px;");
		//btn.setImage("/images/ic_search_c.png");
		//btn.setHoverImage("/images/pose_favicon.png");
		
		btn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				qe = EventQueues.lookup(Cons.EVENTQUEUE_CONNECTION, EventQueues.DESKTOP, true);
				qe.publish(new Event(Cons.EVENT_CALL_EXPIRE, null, null));
	        }
	    });
		
		Separator sep = new Separator();
		sep.setHeight("35px");
		
		w.appendChild(new Caption("เอกสารรับเข้า"));
		w.appendChild(new Label("(" + S_Count + " รายการ)")) ;		
		w.appendChild(sep);
		w.appendChild(btn);
		
		return w;
	}

	//================================================================================
	// Web Configuration 
	// ================================================================================
		
	public void onDisplayWebConfig() throws Exception{
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	    Class.forName(c.S_MYSQL_DRIVER);
	    Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	    conn.setAutoCommit(false);
	     
	    Statement stmt = conn.createStatement();
	     
	 	ResultSet rs = null;
     
		try{	
			
			rs = stmt.executeQuery("SELECT GN_IsUsedWarningExpiringSoon, GN_IsUsedWarningExpire, GN_WarningExpiringSoonDay FROM configuration_web LIMIT 1");

			if(rs.next()){
				B_GN_IsUsedWarningExpire = rs.getBoolean("GN_IsUsedWarningExpire");	
				B_GN_IsUsedWarningExpiringSoon = rs.getBoolean("GN_IsUsedWarningExpiringSoon");	
				I_GN_WarningExpiringSoonDay = rs.getInt("GN_WarningExpiringSoonDay");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) {
				rs.close();
	     	}
	         
	    	if (stmt != null) {
	    		stmt.close();
	     	}
	         
	    	if (conn != null) {
	    		conn.close();
	    	}
		}
	}
}


