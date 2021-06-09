package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import com.poseintelligence.cssd.core.Setting;
import com.poseintelligence.cssd.document.Payout;
import com.poseintelligence.cssd.utillity.DateTime;


@SuppressWarnings("rawtypes")
public class DepartmentPayout extends GenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5353900399813358869L;

	/**
	 * 
	 */
	
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
	
	private Textbox Textbox_Input;
	private Textbox Textbox_SerarhDepartment;
	
	private Checkbox Checkbox_Mode;
	private Checkbox Checkbox_Search;
	
	private Label Label_Search;
	private Label Label_Department;
	
	private Listbox Listbox_Department;
	private Listbox Listbox_Document;
	private Listbox Listbox_Item;
	
	private String S_DocNo = null;
	private String S_SelectDeptId = null;
	
	private String S_CreateStatus = "7";
	private String S_CloseStatus = "8";
	private String S_ReportUrl = "";
	private String MD_URL = "";
	
	private Image Image_CloseDocument;
	private Image Image_ShowReport;
	
	private Window Window_ItemDetailSub;
	private Window Window_Document;
	private Window Window_Report;

	
	public void onCreate() throws Exception {
		
		bySession();
		
		byConfig();
			
		init();
		
		displayDepartment("", true);
			
		focus();

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
	
	private void init() throws Exception{
		Textbox_SerarhDepartment.addEventListener(Events.ON_CHANGING, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				displayDepartment(((InputEvent)event).getValue(), false);
	        }
	    });
	}
	
	public void byConfig() {
        // -----------------------------------------------------------------------------------------
        // Get Config
        // -----------------------------------------------------------------------------------------
        Setting s = new Setting(S_DB);
       
        MD_URL = s.getMD_URL();
        
    }
	
	private void define(Combobox cbb, String S_Sql, String S_DefualtLabel, String S_DefualtValue) throws Exception {
		cbb.getItems().clear();
		cbb.setText("");

		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {

			rs = stmt.executeQuery(S_Sql);

			Comboitem citem_ = new Comboitem();
			citem_.setLabel(S_DefualtLabel);
			citem_.setValue(S_DefualtValue);
			cbb.appendChild(citem_);

			while (rs.next()) {
				Comboitem citem = new Comboitem();
				citem.setLabel(rs.getString("Label"));
				citem.setValue(rs.getString("Value"));

				if (rs.getString("Description") != null && !rs.getString("Description").equals(""))
					citem.setDescription(rs.getString("Description"));

				cbb.appendChild(citem);
			}

			cbb.setSelectedIndex(0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	private void onInput(String S_Text) throws Exception {
		if(Checkbox_Mode.isChecked()) {
			onRemove(S_Text);
		}else {
			onAdd(S_Text);
		}
	}
	
	private void onRemove(String S_Text) throws Exception {
		
		if(S_SelectDeptId == null) {
			showMessage("ยังไม่ได้เลือกแผนก !!");
			focus();
			return;
		}
		
		if(S_DocNo == null) {
			showMessage("ยังไม่ได้เลือกเอกสาร !!");
			focus();
			return;
		}
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		Statement stmt3 = conn.createStatement();
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String S_RowID = null;
		String S_ID = null;
		String S_Payoutdetail_RowID = null;

		try {

			// Check Usage Code
			
			String S_Sql = 
					"	SELECT 		itemstock.RowID, "
				+ 	"				itemstock.UsageCode,"
				+ 	"				itemstock.ItemCode,"
				+ 	"				payoutdetailsub.ID,"
				+ 	"				payoutdetailsub.Payoutdetail_RowID "

				+ 	"	FROM 		payoutdetailsub "
				
				+ 	"	INNER JOIN	itemstock "
				+ 	"	ON 			itemstock.RowID = payoutdetailsub.ItemStockID "

				+ 	"	WHERE 		itemstock.DepID = " + S_DeptId + " "
				+ 	"	AND 		itemstock.UsageCode = '" + S_Text + "' "
				+ 	"	AND 		itemstock.IsStock = 1 "
				+ 	"	AND 		itemstock.IsCancel = 0"
				+ 	"	AND 		itemstock.IsPay = 1 "
				+ 	"	AND 		itemstock.IsStatus = 77 "
				+ 	"	ORDER BY 	payoutdetailsub.ID DESC "
				+ 	"	LIMIT 1 ";
			
			rs = stmt.executeQuery(S_Sql);
			
			System.out.println(S_Sql);

			
			if (rs.next()) {
				
				S_RowID = rs.getString("RowID");
				S_ID = rs.getString("ID");
				S_Payoutdetail_RowID = rs.getString("Payoutdetail_RowID");
				
				S_Sql = "DELETE FROM payoutdetailsub WHERE ID = " + S_ID + " ";
				
				System.out.println(S_Sql);
						
				int I_Rusult = stmt2.executeUpdate(S_Sql);
				
				if(I_Rusult > 0) {
					S_Sql = 	"UPDATE 	itemstock "
							+	"SET 		IsStatus = 5, "
							+ 	"	 		IsStock = 1, "
							+ 	"	 		IsPay = 1 "
							+	"WHERE		RowID = " + S_RowID + " " 
							+	"AND 		IsCancel = 0 ";
					
					System.out.println(S_Sql);

					stmt3.executeUpdate(S_Sql);
					
					// Update QTY
					S_Sql = 	"UPDATE 	payoutdetail "
							+	"SET 		payoutdetail.Qty = (Qty-1) "
							+	"WHERE 		payoutdetail.ID = " + S_Payoutdetail_RowID + " ";
					
					System.out.println(S_Sql);

					stmt3.executeUpdate(S_Sql);
				}
				
				// Display Detail
				displayDocumentDetail(S_DocNo);
				
			}else {
				Notification.show("ไม่พบรายการนี้ !!", "info", Textbox_Input, "warning", 2000, false);
				return;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			
			if (rs2 != null) {
				rs2.close();
			}

			if (stmt != null) {
				stmt.close();
			}
			
			if (stmt2 != null) {
				stmt2.close();
			}
			
			if (stmt3 != null) {
				stmt3.close();
			}

			if (conn != null) {
				conn.close();
			}
			
			focus();
		}
	}
	
	private void onAdd(String S_Text) throws Exception {
		
		if(S_SelectDeptId == null) {
			showMessage("ยังไม่ได้เลือกแผนก !!");
			focus();
			return;
		}
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();
		Statement stmt3 = conn.createStatement();
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String S_RowID = null;
		String S_ItemCode = null;
		String S_UsageCode = null;

		try {

			// Check Usage Code
			
			String S_Sql = 
					"	SELECT 		RowID, "
				+ 	"				UsageCode,"
				+ 	"				ItemCode "

				+ 	"	FROM 		itemstock  "

				+ 	"	WHERE 		DepID = " + S_DeptId + " "
				+ 	"	AND 		UsageCode = '" + S_Text + "' "
				+ 	"	AND 		IsStock = 1 "
				+ 	"	AND 		IsCancel = 0"
				+ 	"	AND 		IsPay = 1 "
				+ 	"	AND 		IsStatus = 5 "
				+ 	"	LIMIT 1 ";
			
			rs = stmt.executeQuery(S_Sql);

			String S_PADocNo = null;
			
			// Check Payout DocNo
			if (rs.next()) {
				if(S_DocNo == null) {
	        		S_PADocNo = Payout.getPayoutDocNo(S_DB, null, S_SelectDeptId, S_UserId, S_CreateStatus, "สร้างใบจ่ายของให้แผนก " + S_DeptId + " ไป " + S_SelectDeptId,null);
	        	}else {
	        		S_PADocNo = S_DocNo;
	        	}
				
				S_RowID = rs.getString("RowID");
				S_ItemCode = rs.getString("ItemCode");
				S_UsageCode = rs.getString("UsageCode");
				
			}else {
				Notification.show("ไม่พบรายการนี้ !!", "info", Textbox_Input, "warning", 2000, false);
				return;
			}

			if(S_PADocNo == null){
				showMessage("ไม่สามารถสร้างใบส่งล้างได้ !!");
				return;
			}
			
			// Select DocNo
			if(S_DocNo == null) {
				S_DocNo = S_PADocNo;		
				displayDocument(S_SelectDeptId, S_DocNo);
			}
			
			// Check Payout Detail
			S_Sql = 
					"	SELECT 		ID "

				+ 	"	FROM 		payoutdetail  "

				+ 	"	WHERE 		DocNo = '" + S_PADocNo + "' "
				+ 	"	AND 		ItemCode = '" + S_ItemCode + "' "
				
				+ 	"	LIMIT 1 ";
			
			rs2 = stmt2.executeQuery(S_Sql);
			
			// Insert Payout Detail
			if (!rs2.next()) {
				
				S_Sql = "	INSERT INTO payoutdetail ( " 
	    		 
		        	+	"	DocNo, " 
		        	+	"	ItemStockID, " 
					+	"	ItemCode, " 
					+	"	Qty, " 
					+	"	Remark, " 
					+	"	PayDate, " 
					+	"	IsCheckPay, " 
					+	"	IsStatus " 
					
					+	"	) VALUES ("
					+ 	"	'" + S_PADocNo + "', "
					+ 	"	null, "
					+ 	"	'" + S_ItemCode + "', "
					+ 	"	1, "
					+ 	"	'', "
					+ 	"	NOW(), "
					+ 	"	1, "
					+ 	"	" + S_CreateStatus + " "
					+ ")";
				
				stmt3.executeUpdate(S_Sql);
			}
			
			// Insert Payout Detail Sub
			
			S_Sql = 		"INSERT INTO 	payoutdetailsub "
						+	"( "
						+	"	Payoutdetail_RowID, "
                        +	"	ItemStockID, "
                        +	"	Qty, "
                        +	"	IsStatus, "
                        +	"	Remark, "
                        +	"	PayDate, "
                        +	"	OccuranceTypeID, "
                        +	"	UsageCode, "
                        +	"	IsCheckPay, "
                        +	"	Printno, "
                        +	"	SterileDocNo, "
                        +	"	B_ID, "
                        +	"	CreateDateTime "
                        +	") VALUES ( "
                        +	"	(SELECT payoutdetail.ID FROM payoutdetail WHERE DocNo = '" + S_PADocNo + "' AND ItemCode = '" + S_ItemCode + "' ), "
                        +	"	" + S_RowID + ", "
                        +	"	1, "
                        +	"	1, "
                        +	"	'', "
                        +	"	NOW(), "
                        +	"	null, "
                        +	"	'" + S_UsageCode + " ',"
                        +	"	1, "
                        +	"	0, "
                        +	"	'', "
                        +	"	1, "
                        +	"	NOW() "
                        +	") ";

			int I_Rusult = stmt3.executeUpdate(S_Sql);
			
			if(I_Rusult > 0) {
				S_Sql = 	"UPDATE 	itemstock "
						+	"SET 		IsStatus = 77 "
						+	"WHERE		RowID = " + S_RowID + " " 
						+	"AND 		IsCancel = 0 ";

				stmt3.executeUpdate(S_Sql);
				
				// Update QTY
				S_Sql = 	"UPDATE 	payoutdetail "
						+	"SET 		payoutdetail.Qty = (SELECT COUNT(*) FROM payoutdetailsub WHERE payoutdetailsub.Payoutdetail_RowID = payoutdetail.ID ) "
						+	"WHERE 		payoutdetail.DocNo = '" + S_PADocNo + "' "
						+ 	"AND 		payoutdetail.ItemCode = '" + S_ItemCode + "' ";

				stmt3.executeUpdate(S_Sql);
			}
			
			// Display Detail
			displayDocumentDetail(S_DocNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			
			if (rs2 != null) {
				rs2.close();
			}

			if (stmt != null) {
				stmt.close();
			}
			
			if (stmt2 != null) {
				stmt2.close();
			}
			
			if (stmt3 != null) {
				stmt3.close();
			}

			if (conn != null) {
				conn.close();
			}
			
			focus();
		}
	}
	
	private void onCloseDocument() throws Exception{
		if(S_DocNo == null) {
			showMessage("ยังไม่ได้เลือกแผนก !!");
			focus();
			return;
		}
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		
		String	S_Sql = "";
		
		try {
			S_Sql = 	"UPDATE 	payout "
					+ 	"SET		IsStatus = " + S_CloseStatus + " " 
					+ 	"WHERE 		DocNo = '" + S_DocNo + "' ";
			
			//System.out.println(S_Sql);
					
			int I_Rusult = stmt.executeUpdate(S_Sql);
	
			if(I_Rusult > 0) {
				S_Sql = 	"UPDATE 	payoutdetail "
						+ 	"SET		IsStatus = " + S_CloseStatus + " " 
						+ 	"WHERE 		DocNo = '" + S_DocNo + "' ";
				
				//System.out.println(S_Sql);
							
				stmt.executeUpdate(S_Sql);
					
				S_Sql = 	"	UPDATE 		itemstock "
						
						+	"	INNER JOIN 	payoutdetailsub " 
						+	"	ON			payoutdetailsub.ItemStockID = itemstock.RowID "
						
						+	"	INNER JOIN 	payoutdetail " 
						+	"	ON			payoutdetail.ID = payoutdetailsub.Payoutdetail_RowID "
						
						+	"	INNER JOIN 	payout " 
						+	"	ON			payout.DocNo = payoutdetail.DocNo "
						
						+	"	SET 		itemstock.IsStatus = 4, "
						+ 	"				itemstock.DepID = payout.DeptID, "
						+ 	"	 			itemstock.IsPay = 1 "
						
						+	"	WHERE		payout.DocNo = '" + S_DocNo + "' " 
						+ 	"	AND 		itemstock.IsStock = 1 "
						+ 	"	AND 		itemstock.IsCancel = 0"
						+ 	"	AND 		itemstock.IsPay = 1 "
						+ 	"	AND 		itemstock.IsStatus = 77 ";
				
				//System.out.println(S_Sql);
				
				I_Rusult = stmt.executeUpdate(S_Sql);
						
				if(I_Rusult > 0) {
					Notification.show("ปิดใบจ่ายเครื่องมือสำเร็จ !!", "info", Image_CloseDocument, "info", 2000, false);
					
					onClear();
					
					displayDepartment("", true);
				}	
				
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
			
			focus();
		}
	}
	
	private void onClear(){
		S_DocNo = null;
		Label_Search.setValue("");
		Label_Department.setValue("");
		
		Listbox_Department.clearSelection();
		Listbox_Document.getItems().clear();
		Listbox_Item.getItems().clear();
		
		S_DocNo = null;
		S_SelectDeptId = null;
		
		focus();
	}
	
	private void displayDepartment(String S_Text, boolean IsFocus) throws Exception {
		Listbox_Department.getItems().clear();
		S_DocNo = null;
		Label_Department.setValue("");
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {

			String S_SqlDepartment = 
					"	SELECT 		department.ID 			AS Value, "
				+ 	"				department.DepName2 	AS Label, "
				+ 	"				("
				+ 	"					SELECT 	COUNT(*) "
				+ 	"					FROM 	payout "
				+ 	"					WHERE 	payout.DeptID = department.ID "
				+ 	"					AND 	payout.IsStatus = 7 "
				+ 	"					AND 	payout.IsCancel = 0 " 
				+ 	"				)	AS Description "
				+ 	""
				+ 	"	FROM 		department  "
				+ 	""
				+ 	"	WHERE 		department.IsCancel = 0 "
				+ 	"	AND 		department.ID <> 20 "
				+ 	"	AND 		department.DepName2 LIKE '%" + S_Text.replace(" ", "%") + "%' ";
			
			S_SqlDepartment = 
					"	SELECT * "
				+ 	"	FROM (" + S_SqlDepartment + ") AS d "
				+ 	"	ORDER BY 	d.Description DESC, d.Label ASC ";
			
			rs = stmt.executeQuery(S_SqlDepartment);

			while (rs.next()) {
				Listitem li = new Listitem();
				
				li.appendChild(new Listcell(rs.getString("Label") + ( rs.getInt("Description") > 0 ? (" (" + rs.getInt("Description") + ")") : "") ) );
				
				li.setAttribute("DeptName", rs.getString("Label"));
				li.setAttribute("ID", rs.getString("Value"));
				
				li.setValue(rs.getString("Value"));
				
				Listbox_Department.appendChild(li);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
			
			if(IsFocus) {
				focus();
			}
		}
	}
	
	private void displayDocument(String S_DeptID, String S_DocNoSearch) throws Exception {
		
		if(S_SelectDeptId == null) {
			showMessage("ยังไม่ได้เลือกแผนก !!");
			focus();
			return;
		}
		
		Listbox_Document.getItems().clear();
		S_DocNo = null;
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {

			String S_Sql = 
								"SELECT 		department.ID AS Department_ID, "
							+ 	"				department.DepName2, "
							+ 	"				payout.DocNo, "
							+ 	"				DATE_FORMAT(payout.CreateDate, '%d/%m/%y') AS CreateDate, "
							+ 	"				payout.IsStatus, "
					
							+ 	"				("
							+ 	"				CASE payout.IsStatus "
							
							+ 	"				WHEN '8' THEN 'ปิดเอกสาร' " 
							+ 	"				WHEN '7' THEN 'รอปิดเอกสาร' "
							
							/*
							+ 	"				WHEN '7' THEN "  
							+ 	"				( " 
							+ 	"				CASE WHEN ( "
							+ 	"					SELECT      COUNT(*) " 
							+ 	"					FROM        payoutdetail  "
							+ 	"					WHERE       payoutdetail.DocNo = payout.DocNo  "
							+ 	"					AND         payoutdetail.Qty <> (SELECT COUNT(*) FROM payoutdetailsub WHERE payoutdetailsub.Payoutdetail_RowID = payoutdetail.ID) "
							+ 	"				) > 0 "
							+ 	"				THEN  'รอปิดเอกสาร' " 
							+ 	"				ELSE  'ร่างเอกสาร' "
							
							
							+ 	"				END "
							+ 	"				) "
							*/
							+ 	"				ELSE "
							+ 	"				'-' "
							+ 	"				END "
							+ 	"				) AS Payout_Status "
							
							+ 	"				FROM 		payout "
							
							+ 	"				LEFT JOIN 	department "
							+ 	"				ON			department.ID = payout.DeptID "
							
							+ 	"				WHERE		department.ID = " + S_DeptID + " "
							+ 	"				AND 		payout.IsCancel = 0 ";

			if(Checkbox_Search.isChecked()) {
				S_Sql 		+= 	"				AND         (payout.IsStatus = 7 OR payout.IsStatus = 8) ";
			}else {
				S_Sql 		+= 	"				AND         payout.IsStatus = 7 ";
			}
			
			if(S_DocNoSearch != null) {
				S_Sql 		+= 	"				AND         payout.DocNo = '" + S_DocNoSearch + "' ";
			}
			
			S_Sql += "ORDER BY DATE(payout.CreateDate) ASC, payout.DocNo ASC LIMIT 100";
			
			System.out.println(S_Sql);
	
			rs = stmt.executeQuery(S_Sql);

			while (rs.next()) {
				Listitem li = new Listitem();
				
				Hlayout hlo = new Hlayout();
				hlo.setValign("middle");
				
				Label lab_date = new Label(rs.getString("CreateDate"));
				Label lab_doc = new Label(rs.getString("DocNo"));
				lab_doc.setStyle("font-size:16px;font-weight:bold;");
				
				Space spc = new Space();
				spc.setHflex("1");
				
				Textbox txb = new Textbox();
				txb.setReadonly(true);
				txb.setWidth("100px");
				txb.setText(rs.getString("Payout_Status"));
				txb.setStyle("font-size:14px;text-align:center;");
				
				hlo.appendChild(new Space());
				hlo.appendChild(lab_doc);
				hlo.appendChild(new Space());
				hlo.appendChild(lab_date);
				hlo.appendChild(spc);
				hlo.appendChild(txb);
				
				Listcell lc = new Listcell();
				lc.appendChild(hlo);
				
				li.appendChild(lc);
				
				li.setAttribute("Value", rs.getString("DocNo"));
				li.setAttribute("IsStatus", rs.getString("IsStatus"));
				
				li.setValue(rs.getString("DocNo"));
				
				Listbox_Document.appendChild(li);
			}
			
			if(S_DocNoSearch != null && Listbox_Document.getItems().size() > 0) {
				S_DocNo = S_DocNoSearch;
				Listbox_Document.setSelectedIndex(0);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
			
			focus();
		}
	}
	
	private void displayDocumentDetail(String S_DocNo) throws Exception {
		
		if(S_DocNo == null) {
			focus();
			return;
		}
		
		Listbox_Item.getItems().clear();

		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {

			String S_Sql = 
						"	SELECT 		payoutdetail.ID AS ID, "
					+	"				item.itemcode, " 
					+	"				item.itemname, "
					+	"				(		SELECT 	COUNT(*) " 
					+	"						FROM 	itemstock "  
					+	"						WHERE 	itemstock.ItemCode = payoutdetail.itemcode " 
					+	"						AND 	itemstock.IsStatus = 5 " 
					+	"						AND 	itemstock.IsStock = 1  " 
					+	"						AND 	itemstock.IsPay = 1 " 
					+	"						AND 	itemstock.DepID = " + S_DeptId + " "  
					+	"					) AS Stock_Qty, "
					+	"				(SELECT COUNT(*) FROM payoutdetailsub WHERE payoutdetailsub.Payoutdetail_RowID = payoutdetail.ID) AS Pay_Qty "

					+	"	FROM 		payoutdetail " 

					+	"	INNER JOIN 	payout " 
					+	"	ON			payout.DocNo = payoutdetail.DocNo " 
					
					+	"	INNER JOIN 	item "
					+	"	ON			item.itemcode = payoutdetail.ItemCode " 
							
					+	"	WHERE 		payout.DocNo = '" + S_DocNo + "'  " 

					+	"	AND			payoutdetail.IsStatus IN (" + S_CreateStatus + ", " + S_CloseStatus + ") " ;

			
			rs = stmt.executeQuery(S_Sql);

			int I_No = 1;
			String S_Pay_Qty = null;
			
			while (rs.next()) {
				
				S_Pay_Qty = rs.getString("Pay_Qty");
				
				// Check = 0
				if(S_Pay_Qty != null && S_Pay_Qty.equals("0")) {
					continue;
				}
				
				Listitem li = new Listitem();
				
				li.appendChild(new Listcell(I_No + "."));
				li.appendChild(new Listcell(rs.getString("itemcode") + " : " + rs.getString("itemname")));
				li.appendChild(new Listcell(rs.getString("Stock_Qty")));
				li.appendChild(new Listcell(S_Pay_Qty));
				
				li.setAttribute("ID", rs.getString("ID"));
				
				Listbox_Item.appendChild(li);
				
				I_No++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
			
			focus();
		}
	}
	
	private EventListener<Event> evl_1;
    private EventListener<Event> evl_2;
    private EventListener<Event> evl_3;
    private EventListener<Event> evl_4;
    private EventListener<Event> evl_5;
    private EventListener<Event> evl_6;
    
	private void callWindowDocument() throws Exception {
		
		try {
			Window_Document.setVisible(true);
			Window_Document.setFocus(true);
			Window_Document.setPosition("center");
			Window_Document.setMode("modal");
			Window_Document.setBorder("normal");
			Window_Document.setClosable(true);		
			Window_Document.setHeight("95%");
			Window_Document.setWidth("95%");
			Window_Document.setStyle("background: #2e5fbd;");
		
			Borderlayout b = (Borderlayout)Window_Document.getChildren().get(1);
			
			North n = (North)b.getChildren().get(0);
			West w = (West)b.getChildren().get(1);
			Center c = (Center)b.getChildren().get(2);
			South s = (South)b.getChildren().get(3);
			
			final Combobox cbb_dept = (Combobox)((Hlayout)n.getChildren().get(0)).getChildren().get(2);
			final Datebox dte_seatch = (Datebox)((Hlayout)n.getChildren().get(0)).getChildren().get(4);
			final Textbox txt_seatch = (Textbox)((Hlayout)n.getChildren().get(0)).getChildren().get(6);
			final Image img_seatch = (Image)((Hlayout)n.getChildren().get(0)).getChildren().get(7);
			
			final Listbox listbox_doc = (Listbox)((Vlayout)w.getChildren().get(0)).getChildren().get(0);
			final Listbox listbox_detail = (Listbox)((Vlayout)c.getChildren().get(0)).getChildren().get(0);
			
			final Image img_show_report = (Image)((Div)s.getChildren().get(0)).getChildren().get(0);
			
			String S_SqlDepartment = 
					"	SELECT 		ID 			AS Value, "
				+ 	"				DepName2 	AS Label, "
				+ 	"				'' 			AS Description "
				+ 	""
				+ 	"	FROM 		department  "
				+ 	""
				+ 	"	WHERE 		IsCancel = 0 "
				+ 	""
				+ 	"	ORDER BY 	PriorityNo, DepName2 ";
			
			// Set Department Data
			define(cbb_dept, S_SqlDepartment, "ทุกแผนก", null);
			
			// Set Date Data
			dte_seatch.setText(DateTime.getDateNow());
			
			txt_seatch.setText("");
			listbox_doc.getItems().clear();
			listbox_detail.getItems().clear();
	
			if(evl_1 != null) {
				txt_seatch.removeEventListener("onOK", evl_1);
				img_seatch.removeEventListener("onClick", evl_2);
				listbox_doc.removeEventListener("onSelect", evl_3);
				cbb_dept.removeEventListener("onSelect", evl_4);
				dte_seatch.removeEventListener("onSelect", evl_5);
				img_show_report.removeEventListener("onClick", evl_6);
			}

			txt_seatch.addEventListener("onOK", evl_1 = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
					String S_Select_DeptId = null;
					
					try {
						S_Select_DeptId = cbb_dept.getSelectedItem().getValue();
					}catch(Exception e) {
						
					}
					
					onDisplay(listbox_doc, txt_seatch.getText(), S_Select_DeptId, DateTime.convertDate(dte_seatch.getText()));
		        }
		    }); 
	
			img_seatch.addEventListener("onClick", evl_2 = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					String S_Select_DeptId = null;
					
					try {
						S_Select_DeptId = cbb_dept.getSelectedItem().getValue();
					}catch(Exception e) {
						
					}
					
					onDisplay(listbox_doc, txt_seatch.getText(), S_Select_DeptId, DateTime.convertDate(dte_seatch.getText()));
		        }
		    });
	
			listbox_doc.addEventListener("onSelect", evl_3 = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem li = listbox_doc.getSelectedItem();
					onDisplayDetail(listbox_detail, (String) li.getAttribute("DocNo"));
		        }
		    });
			
			cbb_dept.addEventListener("onSelect", evl_4 = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
					String S_Select_DeptId = null;
					
					try {
						S_Select_DeptId = cbb_dept.getSelectedItem().getValue();
					}catch(Exception e) {
						
					}
					
					onDisplay(listbox_doc, txt_seatch.getText(), S_Select_DeptId, DateTime.convertDate(dte_seatch.getText()));
		        }
		    }); 
			
			dte_seatch.addEventListener("onChange", evl_5 = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
					String S_Select_DeptId = null;
					
					try {
						S_Select_DeptId = cbb_dept.getSelectedItem().getValue();
					}catch(Exception e) {
						
					}
					
					onDisplay(listbox_doc, txt_seatch.getText(), S_Select_DeptId, DateTime.convertDate(dte_seatch.getText()));
		        }
		    }); 
			
			img_show_report.addEventListener("onClick", evl_6 = new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
					try {
						Listitem li = listbox_doc.getSelectedItem();
						callReport((String) li.getAttribute("DocNo"));
					}catch(Exception e) {
						
					}
		        }
		    });
	
			String S_Select_DeptId = null;
			
			try {
				S_Select_DeptId = cbb_dept.getSelectedItem().getValue();
			}catch(Exception e) {
				
			}
			
			onDisplay(listbox_doc, txt_seatch.getText(), S_Select_DeptId, DateTime.convertDate(dte_seatch.getText()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
    private void onDisplay(final Listbox Listbox_, String S_Txt, String S_DeptID, String S_Date) throws Exception{
    	Listbox_.getItems().clear();
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			// -------------------------------------------------------
		    // Select Pay
		    // -------------------------------------------------------
	
			String S_Sql = 
					"SELECT 		department.ID AS Department_ID, "
				+ 	"				department.DepName2, "
				+ 	"				payout.DocNo, "
				+ 	"				DATE_FORMAT(payout.CreateDate, '%d/%m/%y %H:%i') AS CreateDate, "
				+ 	"				payout.IsStatus, "
		
				+ 	"				("
				+ 	"				CASE payout.IsStatus "
				
				+ 	"				WHEN '8' THEN 'ปิดเอกสาร' " 
				+ 	"				WHEN '7' THEN 'รอปิดเอกสาร' "

				+ 	"				ELSE "
				+ 	"				'-' "
				+ 	"				END "
				+ 	"				) AS Payout_Status "
				
				+ 	"				FROM 		payout "
				
				+ 	"				LEFT JOIN 	department "
				+ 	"				ON			department.ID = payout.DeptID "
				
				+ 	"				WHERE		payout.IsCancel = 0 "

				+ 	"				AND         (payout.IsStatus = 7 OR payout.IsStatus = 8) ";
			
			
			if(!S_Txt.equals("")) {
				S_Sql 		+= 	"	AND         payout.DocNo LIKE '%" + S_Txt + "%' ";
			}
			
			if(S_DeptID != null) {
				S_Sql 		+= 	"	AND         department.ID = " + S_DeptID + " ";
			}
			
			if(!S_Date.equals("")) {
				S_Sql 		+= 	"	AND         DATE(payout.CreateDate) = DATE('" + S_Date + "') ";
			}
			
			S_Sql += "ORDER BY DATE(payout.CreateDate) ASC, payout.DocNo ASC LIMIT 100";

			rs = stmt.executeQuery(S_Sql);

			while(rs.next()){
				final Listitem list = new Listitem();

				list.appendChild(new Listcell(rs.getString("DepName2")));
				list.appendChild(new Listcell(rs.getString("CreateDate")));
				list.appendChild(new Listcell(rs.getString("DocNo")));
				list.appendChild(new Listcell(rs.getString("Payout_Status")));
				
				final String S_DocNo = rs.getString("DocNo");
				list.setAttribute("DocNo", S_DocNo);

				Listbox_.appendChild(list);  
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
    
    private void onDisplayDetail(final Listbox Listbox_, String S_DocNo) throws Exception{
    	Listbox_.getItems().clear();
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			String S_Sql = 
					"	SELECT 		payoutdetail.ID AS ID, "
				+	"				item.itemcode, " 
				+	"				item.itemname, "
				+	"				(SELECT COUNT(*) FROM payoutdetailsub WHERE payoutdetailsub.Payoutdetail_RowID = payoutdetail.ID) AS Pay_Qty "

				+	"	FROM 		payoutdetail " 

				+	"	INNER JOIN 	payout " 
				+	"	ON			payout.DocNo = payoutdetail.DocNo " 
				
				+	"	INNER JOIN 	item "
				+	"	ON			item.itemcode = payoutdetail.ItemCode " 
						
				+	"	WHERE 		payout.DocNo = '" + S_DocNo + "' " ;
			
			rs = stmt.executeQuery(S_Sql);
			int I_No = 1;
			
			while(rs.next()){
				Listitem list = new Listitem();

				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("itemcode")));
				list.appendChild(new Listcell(rs.getString("itemname")));
				list.appendChild(new Listcell(rs.getString("Pay_Qty")));
				
				Listbox_.appendChild(list);  
				
				I_No++;
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
    
    private void callReport(String S_DocNo){
    	try {
    		Window_Report.setVisible(true);
    		Window_Report.setFocus(true);
    		Window_Report.setPosition("center");
    		Window_Report.setMode("modal");
    		Window_Report.setBorder("normal");
    		Window_Report.setClosable(true);		
    		Window_Report.setHeight("100%");
    		Window_Report.setWidth("100%");
    		Window_Report.setStyle("background: #2e5fbd;");
		
    		Iframe ifr = (Iframe)((Vlayout)Window_Report.getChildren().get(1)).getChildren().get(0);
    		ifr.setVflex("1");
    		S_ReportUrl = MD_URL;
    		
    		String S_ReportUrl = this.S_ReportUrl + "report/" + "cssd_report_payout_by_docno.php?p_docno=" + S_DocNo;
    		
    		ifr.setSrc(S_ReportUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private void callItemDetailSub(final String S_ID) throws Exception {
		Window_ItemDetailSub.setVisible(true);
		Window_ItemDetailSub.setFocus(true);
		Window_ItemDetailSub.setPosition("center");
		Window_ItemDetailSub.setMode("modal");
		Window_ItemDetailSub.setBorder("normal");
		Window_ItemDetailSub.setClosable(true);		
		Window_ItemDetailSub.setHeight("95%");
		Window_ItemDetailSub.setWidth("400px");
		Window_ItemDetailSub.setStyle("background: #2e5fbd;");
	
		Caption cap = (Caption)Window_ItemDetailSub.getChildren().get(0);
		cap.setStyle("background: #2e5fbd;color:#FFFFFF;font-size:20px;");
		cap.setLabel("Usage Code");
		
		Vlayout vlo = (Vlayout)Window_ItemDetailSub.getChildren().get(1);
		Listbox lbx = (Listbox)vlo.getChildren().get(0);

		lbx.getItems().clear();

		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
		Class.forName(c.S_MYSQL_DRIVER);
		Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
		conn.setAutoCommit(false);

		Statement stmt = conn.createStatement();
		ResultSet rs = null;

		try {

			String S_Sql = 
						"	SELECT 		payoutdetailsub.UsageCode "
					
					+	"	FROM 		payoutdetailsub " 

					+	"	WHERE 		payoutdetailsub.Payoutdetail_RowID = " + S_ID + " " ;

			System.out.println(S_Sql);
			
			rs = stmt.executeQuery(S_Sql);

			int I_No = 1;
			
			while (rs.next()) {
				Listitem li = new Listitem();
				
				li.appendChild(new Listcell(I_No + "."));
				li.appendChild(new Listcell(rs.getString("UsageCode")));
				
				lbx.appendChild(li);
				
				I_No++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	
	// Event
	public void onOK$Textbox_Input(Event event) throws Exception {
		onInput(Textbox_Input.getText());
	}
	
	public void onOK$Textbox_SerarhDepartment(Event event) throws Exception {
		displayDepartment(Textbox_SerarhDepartment.getText(), true);
	}
	
	public void onClick$Image_Search(Event event) throws Exception {
		displayDepartment(Textbox_SerarhDepartment.getText(), true);
	}
	
	public void onCheck$Checkbox_Search(Event event) throws Exception {
		Label_Search.setValue(Checkbox_Search.isChecked() ? "ทั้งหมด" : "ยังไม่ปิด");
		Listbox_Item.getItems().clear();
		displayDocument(S_SelectDeptId, null);
	}
	
	public void onCheck$Checkbox_Mode(Event event) throws Exception {
		Checkbox_Mode.setLabel(Checkbox_Mode.isChecked() ? "ลบ" : "เพิ่ม");
		focus();
	}

	public void onClick$Image_ShowReport(Event event) throws Exception {
		if(S_DocNo != null)
			callReport(S_DocNo);
	}

	public void onClick$Image_CloseDocument(Event event) throws Exception {
		
		if(S_DocNo == null) {
			return;
		}
		
		if(Listbox_Item.getItems().size() < 1) {
			showMessage("ไม่สามารถปิดใบจ่ายได้ เนื่องจากไม่มีรายการอยู่ในใบจ่าย !!");
			return;
		}
		
		Messagebox.show("ยืนเพื่อปิดใบจ่ายของ ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
    		public void onEvent(Event evt) throws Exception {
    			switch (((Integer) evt.getData()).intValue()) {
                  	case Messagebox.YES:
                  		onCloseDocument();
                  		break;
                  	case Messagebox.NO: 
                  		break;
    			}
    		}
    	});
	}
	
	public void onSelect$Listbox_Department(Event event) throws Exception {	
		Label_Department.setValue( "แผนก : " + (String)Listbox_Department.getSelectedItem().getAttribute("DeptName") );
		S_SelectDeptId = (String)Listbox_Department.getSelectedItem().getValue();
		Listbox_Item.getItems().clear();
		displayDocument((String)Listbox_Department.getSelectedItem().getValue(), null);
	}
	
	public void onSelect$Listbox_Document(Event event) throws Exception {
		S_DocNo = (String)Listbox_Document.getSelectedItem().getValue();
		
		Label_Department.setValue("แผนก : " + (String)Listbox_Department.getSelectedItem().getAttribute("DeptName") + " ใบจ่ายที่ " + S_DocNo);
		
		displayDocumentDetail(S_DocNo);
	
	}
	
	public void onDoubleClick$Listbox_Item(Event event) throws Exception {
		String S_ID = (String)Listbox_Item.getSelectedItem().getAttribute("ID");

		callItemDetailSub(S_ID);
	
	}
	
	public void onClick$Image_ShowDocument(Event event) throws Exception {
		callWindowDocument();
	}

	
	private void focus() {
		Textbox_Input.setText("");
		Textbox_Input.focus();
	}
	
	private void showMessage(String S_Info) {
		Messagebox.show(S_Info);
	}

}
