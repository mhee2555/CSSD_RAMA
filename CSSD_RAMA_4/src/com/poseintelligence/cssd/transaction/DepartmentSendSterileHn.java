package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

public class DepartmentSendSterileHn extends GenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variable Session
	private Session session = Sessions.getCurrent();
	private boolean B_IsCreate = true;

	private String 	S_UserId,
					S_DeptId,
					S_UserName,
					S_IsAdmin,
					S_EmpCode,
					S_DepName,
					S_DB;
	
	private Combobox ComboboxNameHN;
	
	
	public void onCreate() throws Exception {

		bySession();
		init();
    }
	
	private void init() throws Exception{
		System.out.print("222323");
		defindName("");
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

	public void defindName(String xSearch) throws Exception {
		String HnCode = null;


		if (xSearch != null) {
			if (xSearch.length() >= 1) {
				ComboboxNameHN.setOpen(false);
				ComboboxNameHN.getItems().clear();

				com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		        Class.forName(c.S_MYSQL_DRIVER);
		        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		        conn.setAutoCommit(false);
		        
		        Statement stmt = conn.createStatement();
		    	ResultSet rs = null;
				
				
				try {
					String sSql = "SELECT " + 
							"                      hotpitalnumber.HnCode,  " + 
							"                      hotpitalnumber.Id , " + 
							"                      hotpitalnumber.FName " + 
							"                    FROM  hotpitalnumber";
					if(!xSearch.equals("")) {
						sSql += "  WHERE ( (hotpitalnumber.HnCode LIKE  '%"+xSearch.replace(" ", "%")+"%') "+
								"	OR (hotpitalnumber.FName LIKE  '%"+xSearch.replace(" ", "%")+"%') )  " ;

					}
					
					System.out.println(sSql);

					rs = stmt.executeQuery(sSql);
					while(rs.next()){

						Comboitem citem = new Comboitem();

						HnCode = rs.getString("FName") + " : " + rs.getString("HnCode") ;

						citem.setLabel(HnCode);
						citem.setValue(rs.getString("HnCode"));
						

						ComboboxNameHN.appendChild(citem);
					}

					if (ComboboxNameHN.getItemCount() > 0) {
						ComboboxNameHN.setOpen(true);
					}
				
					} catch (Exception e) {
					e.printStackTrace();
				} finally {
			        if (stmt != null) {
			            stmt.close();
			        }
			        
			        if (conn != null) {
			            conn.close();
			        }
			        
			        if (rs != null) {
		                rs.close();
		            }
				}
			}
		}
	}
	
	
	
	
	
}
