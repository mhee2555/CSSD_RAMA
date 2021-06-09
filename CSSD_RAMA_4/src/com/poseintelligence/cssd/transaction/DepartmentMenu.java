package com.poseintelligence.cssd.transaction;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.poseintelligence.cssd.utillity.Cons;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

@SuppressWarnings("rawtypes")
public class DepartmentMenu extends GenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3669018259070311653L;
	private EventQueue<Event> qe;
	private boolean B_IsCreate = true;
	
	private boolean B_DP_IsUsedDispatch;
	
	// Variable Session
	private Session session = Sessions.getCurrent();
	
	private String 	S_UserId,
					S_DeptId,
					S_UserName,
					S_IsAdmin,
					S_EmpCode,
					S_DepName,
					S_DB;
	
    // Widget
	private Toolbarbutton Toolbarbutton_Project;
	private Toolbarbutton Toolbarbutton_Branch;
	private Toolbarbutton Toolbarbutton_User;
	
	private Tree TreeMenu;
	
	private Treeitem Treeitem_MainMenu;
	private Treeitem Treeitem_StockReceive;
	private Treeitem Treeitem_UseEquipment;
	private Treeitem Treeitem_Payout;
	private Treeitem Treeitem_SendSterile;
	private Treeitem Treeitem_SendSterile1;
	private Treeitem Treeitem_SendSterile2;
	private Treeitem Treeitem_Report;
	private Treeitem Treeitem_Report1;
	private Treeitem Treeitem_Report2;
	private Treeitem Treeitem_Report3;
	private Treeitem Treeitem_Report4;
	private Treeitem Treeitem_Requisition;
	private Treeitem Treeitem_Stock;
	private Treeitem Treeitem_Setting;
	private Treeitem Treeitem_Tracking;
	private Treeitem Treeitem_Borrow;

	
	private Window WinProcess;
	
	private Include Include_Src;

	public void onCreate() throws Exception {
		
		bySession();
		
		// Configuration
		onDisplayWebConfig();
		
		// Menu Department
		setMenuDepartment();
		
		init();

    }
	
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception {
		super.doAfterCompose(comp); 
		qe = EventQueues.lookup(Cons.EVENTQUEUE_CONNECTION, EventQueues.DESKTOP, true);
		qe.subscribe(new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				
				Include_Src.setSrc(null);
				
				switch (event.getName()) {
				case Cons.EVENT_CALL_DEPARTMENT_RECEIVE:
					callPage("department_receive_stock.zul");
					TreeMenu.setSelectedItem(Treeitem_StockReceive);
					break;
				case Cons.EVENT_CALL_EXPIRING_SOON:
					callPage("department_expiring_soon.zul");
					TreeMenu.clearSelection();
					break;
				case Cons.EVENT_CALL_EXPIRE:
					callPage("department_expire.zul");
					TreeMenu.clearSelection();
					break;
				case Cons.EVENT_SHOW_DISPATCH_MENU:
					callPage("department_setting.zul");
					Treeitem_UseEquipment.setVisible(true);
					break;
				case Cons.EVENT_HIDE_DISPATCH_MENU:
					callPage("department_setting.zul");
					Treeitem_UseEquipment.setVisible(false);
					break;	
				default:
					break;
				}
				
				
			}
		});
	}
	
	
	
	
	private void callPage(String page)throws Exception {
		onProcess(true);
		
		Include_Src.setSrc(null);
		Include_Src.setSrc(page);
		
		onProcess(false);
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
		
		//System.out.println("S_DB == " + S_DB);
	}
	
	private void init(){
		Toolbarbutton_Project.setLabel( ( S_DB == null || S_DB.equals("") || S_DB.equals("cssd_2_usage_test")) ? "cssd_test" : ( S_DB.replace("2_usage_", "") ) );
		Toolbarbutton_Branch.setLabel(S_DepName);
		Toolbarbutton_User.setLabel(S_UserName);
		
		TreeMenu.setSelectedItem(Treeitem_MainMenu);
		
		// Default page
		Include_Src.setSrc(null);
		Include_Src.setSrc("department_portal.zul");
		
		Treeitem_UseEquipment.setVisible(B_DP_IsUsedDispatch);
	}
	
	public void refresh() throws Exception {
    	
    }
	
	public void onLogout(){
		Messagebox.show("ยืนยันการออกจากระบบ?", " ", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
    		public void onEvent(Event evt) throws Exception {
    			switch (((Integer) evt.getData()).intValue()) {
                  	case Messagebox.YES:
                  		
                  		//get cookie
    		 			Cookie [] cookies = ((HttpServletRequest)Executions.getCurrent().getNativeRequest()).getCookies();
    		 			int i = 0;
    		 			
    		 			String S_DB = "";
    		 			
    		 			if (cookies != null) {
    		 			 	for (Cookie cookie : cookies) {
    		 			   		if (cookie.getName().equals("S_DB")) {
    		 				  		S_DB = "?db=" + cookies[i].getValue();
    		 			     		break;
    		 			    	}
    		 			   
    		 			   		i++;
    		 			  	}	
    		 			}
    		 					 		
    					Executions.sendRedirect("index.zul" + S_DB);
                  		
                		//Executions.sendRedirect("/index.zul");
                		
                  		break;
                  	case Messagebox.NO:
                  		break;
    			}
    		}
      });
	}

	public void onDisplayWebConfig() throws Exception{
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery("SELECT DP_IsUsedDispatch FROM configuration_web LIMIT 1");

			if(rs.next()){
				B_DP_IsUsedDispatch = rs.getBoolean("DP_IsUsedDispatch");
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
	
	public void setMenuDepartment() throws Exception{
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			String S_Sql = 
					
							"SELECT 	MainMenu," 
						+	"			MenuStockReceive," 
						+	"			MenuPayout," 
						+	"			MenuUseEquipment," 
						+	"			MenuSendSterile," 
						
						+	"			SendsterileInDepartment," 
						+	"			SendsterileNewItemStock," 
						+	"			MenuReport," 
						+	"			Report_1," 
						+	"			Report_2," 
						
						+	"			Report_3," 
						+	"			Report_4," 
						+	"			MenuRequisition," 
						+	"			MenuStock,"
						+	"			MenuTracking,"
						+	"			MenuBorrow," 
						+	"			MenuSetting " 
						
						+	"FROM 		menu_department "
						
						+ 	"LIMIT 1";
			
			rs = stmt.executeQuery(S_Sql);

			if(rs.next()){
				Treeitem_MainMenu.setVisible(rs.getBoolean("MainMenu"));
				Treeitem_StockReceive.setVisible(rs.getBoolean("MenuStockReceive"));
				Treeitem_UseEquipment.setVisible(rs.getBoolean("MenuUseEquipment"));
				Treeitem_Payout.setVisible(rs.getBoolean("MenuPayout"));
				Treeitem_SendSterile.setVisible(rs.getBoolean("MenuSendSterile"));
				
				Treeitem_SendSterile1.setVisible(rs.getBoolean("SendsterileInDepartment"));
				Treeitem_SendSterile2.setVisible(rs.getBoolean("SendsterileNewItemStock"));
				Treeitem_Report.setVisible(rs.getBoolean("MenuReport"));
				Treeitem_Report1.setVisible(rs.getBoolean("Report_1"));
				Treeitem_Report2.setVisible(rs.getBoolean("Report_2"));
				Treeitem_Report3.setVisible(rs.getBoolean("Report_3"));
				Treeitem_Report4.setVisible(rs.getBoolean("Report_4"));
				Treeitem_Tracking.setVisible(rs.getBoolean("MenuTracking"));
				Treeitem_Borrow.setVisible(rs.getBoolean("MenuBorrow"));
				Treeitem_Requisition.setVisible(rs.getBoolean("MenuRequisition"));
				Treeitem_Stock.setVisible(rs.getBoolean("MenuStock"));
				Treeitem_Setting.setVisible(rs.getBoolean("MenuSetting"));
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
    
    public void onProcess(boolean b) throws Exception {
    	
    	WinProcess.setVisible(b);
    	
    	if(b) {
	    	WinProcess.setFocus(b);
	    	WinProcess.setPosition("center");
	    	WinProcess.setMode("modal");
    	}
    }
    
    // Event
	
 	public void onClick$Toolbarbutton_Logout(Event event) throws Exception {
 		onLogout(); 
 	}
 		
 	public void onClick$Toolbarbutton_Refresh(Event event) throws Exception {
 		refresh(); 
 	}
 	
 	public void onClick$Treeitem_MainMenu(Event event) throws Exception {
 		callPage("department_portal.zul");
 	}
 	
 	public void onClick$Treeitem_StockReceive(Event event) throws Exception {	
 		callPage("department_receive_stock.zul");
 	}
 	
 	public void onClick$Treeitem_Payout(Event event) throws Exception {
 		callPage("department_payout.zul");
 	}
 	
 	public void onClick$Treeitem_SendSterile1(Event event) throws Exception {
 		callPage("department_send_sterile.zul");
 	}
 	
 	public void onClick$Treeitem_SendSterile2(Event event) throws Exception {
 		callPage("department_send_sterile_new_usage.zul");
 	}
 	
 	public void onClick$Treeitem_Stock(Event event) throws Exception {	
 		callPage("department_item_stock.zul");
 	}
 	
 	public void onClick$Treeitem_Setting(Event event) throws Exception {
 		callPage("department_setting.zul");
 	}
 	
 	public void onClick$Treeitem_UseEquipment(Event event) throws Exception {
 		callPage("department_dispatch_stock.zul");
 	}
 	
 	public void onClick$Treeitem_Requisition(Event event) throws Exception {
 		callPage("department_request_payout.zul");
 	}
 	
 	public void onClick$Treeitem_Report1(Event event) throws Exception {
 		callPage("department_report.zul?report=1&report_name=รายงานบันทึกรับอุปกรณ์");
 	}
 	
 	public void onClick$Treeitem_Report2(Event event) throws Exception {
 		callPage("department_report.zul?report=2&report_name=รายงานจ่ายอุปกรณ์");
 	}
 	
 	public void onClick$Treeitem_Report3(Event event) throws Exception {
 		callPage("department_report.zul?report=3&report_name=รายงานส่งฆ่าเชื้ออุปกรณ์หมดอายุ");
 	}
 	
 	public void onClick$Treeitem_Report4(Event event) throws Exception {
 		callPage("department_report.zul?report=4&report_name=รายงานบันทึกการส่งอุปกรณ์ฆ่าเชื้อ");
 	}
 	
 	public void onClick$Treeitem_Tracking(Event event) throws Exception {
 		callPage("department_tracking.zul");
 	}
 	public void onClick$Treeitem_Borrow(Event event) throws Exception {
 		callPage("department_borrow.zul");
 	}
 	
 	public void onURIChange$Include_Src(Event event) throws Exception {
 		String S_Src = Include_Src.getSrc();
     	S_Src.replace("/", "");
     	
     	if(S_Src.equals("timeout.zul"))
     		Executions.sendRedirect("/index.zul");
 	}
    

}
