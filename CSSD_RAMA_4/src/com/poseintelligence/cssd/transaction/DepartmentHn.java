package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.poseintelligence.cssd.document.Payout;
import com.poseintelligence.cssd.utillity.DateTime;

public class DepartmentHn extends GenericForwardComposer{
	
	private boolean B_IsCreate = true;
	private Combobox Combobox_Department;
	private Combobox Combobox_Status;
	private Image    Image_Search;
	private Listbox  Listbox_ItemLeft;
	private Listbox  Listbox_Document;
	private Listbox  Listbox_DocumentDetail;
	private Listbox  Listbox_Return_Document;
	private Listbox  Listbox_Return_Item;
	private Textbox  Textbox_SearchItemStock;
	private Textbox  Textbox_SearchDoc;
	private Button   Button_Send;
	private String S_DocNo = null;
	private String S_DocNo_Return = null;
	private Datebox Datebox_SDocDate;
	private Datebox Datebox_EDocDate;	
	private Datebox Datebox_Return_SDocDate;
	private Datebox Datebox_Return_EDocDate;	
	// Variable Session
	private Session session = Sessions.getCurrent();
	
	private String 	S_UserId,
					S_DeptId,
					S_UserName,
					S_IsAdmin,
					S_EmpCode,
					S_DepName,
					S_DB;
	
	
	public void onCreate() throws Exception {
		
		bySession();
		
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
	
	private void init() throws Exception{
		
		Datebox_SDocDate.setText(DateTime.getDateLastMonth());
		Datebox_EDocDate.setText(DateTime.getDateNow());
		
		Datebox_Return_SDocDate.setText(DateTime.getDateLastMonth());
		Datebox_Return_EDocDate.setText(DateTime.getDateNow());
		
		selection_Department();
		showdata_item();
		onDisplayDocumentReturn();
	}
	
	public void onSelect$Listbox_Document(Event event) throws Exception {
		
		S_DocNo = (String)Listbox_Document.getSelectedItem().getAttribute("DocNo");
		
		// Display Pay Detail
		if(S_DocNo != null && (!S_DocNo.equals("")))
			onDisplayDetail(S_DocNo, true);
	}
	
	public void onSelect$Listbox_Return_Document(Event event) throws Exception {
		
		S_DocNo_Return = (String)Listbox_Return_Document.getSelectedItem().getAttribute("DocNo");
		
		// Display Pay Detail
		if(S_DocNo_Return != null && (!S_DocNo_Return.equals("")))
			onDisplayReturnDetail(S_DocNo_Return, true);
	}

	
	public void onClick$Image_Search(Event event) throws Exception {
		// Display ItemStock
		showdata_item();
	}
	
	public void onClick$Button_Send(Event event) throws Exception {
		onConfirmSend();
	}
	
	public void onClick$Image_SearchDoc(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onClick$Button_Return_SearchDoc(Event event) throws Exception {
		onDisplayDocumentReturn();
	}

	
	
	private void selection_Department() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{
			
			Combobox_Department.getItems().clear();

			String S_Sql = "SELECT" + 
							"	department.ID," + 
							"	department.DepName " + 
							"FROM" + 
							"	department";
			
			System.out.println(S_Sql);
			
			rs = stmt.executeQuery(S_Sql);
			
			
//			====
			Comboitem cbi_defalut = new Comboitem();
			cbi_defalut.setLabel("กรุณาเลือกแผนก");
			cbi_defalut.setValue("0");
			
			Combobox_Department.appendChild(cbi_defalut);
// 			====
			
			while(rs.next()){
				Comboitem cbi = new Comboitem();
				cbi.setLabel(rs.getString("DepName"));
				cbi.setValue(rs.getString("ID"));
				
			
				
				Combobox_Department.appendChild(cbi);
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
	
	private void showdata_item() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{
		String S_Text = Textbox_SearchItemStock.getText();
			
			String S_Sql = "SELECT" + 
							"	itemstock.RowID," + 
							"	itemstock.UsageCode," + 
							"	item.itemname," +
							"	item.itemcode," + 
							"	units.UnitName," + 
							"	DATE( itemstock.ExpireDate ) AS ExpireDate " + 
							"FROM" + 
							"	itemstock" + 
							"	INNER JOIN item ON itemstock.ItemCode = item.itemcode" + 
							"	INNER JOIN units ON item.UnitID = units.ID " + 
							"WHERE" + 
							"	itemstock.IsNewUsage = 0 " + 
							"	AND itemstock.IsNew = 0 " + 
							"	AND itemstock.IsStatus = 5 " + 
							"	AND itemstock.IsPay = 1 " +
							"	AND itemstock.IsBorrow = 0 " + 
							"	AND itemstock.IsDispatch = 0 " ;
							if(!S_Text.trim().equals("")) {
								S_Sql +=	
								"	AND 		("
								+ 	"				item.itemcode 		LIKE '%" + S_Text + "%' "
								+ 	"				OR item.itemname 	LIKE '%" + S_Text + "%' "
								+ 	"			) ";
							}
				 S_Sql +=
							"	AND itemstock.DepID =  '" + S_DeptId + "' " + 
							"ORDER BY" + 
							"	itemstock.ExpireDate ASC," + 
							"	itemstock.UsageCode ASC " + 
							"	LIMIT 50 ";
			
			System.out.println(S_Sql);
			
			rs = stmt.executeQuery(S_Sql);
			
			
			Listbox_ItemLeft.getItems().clear();
			int I_No = 1;

			
			while(rs.next()){
				final Listitem list = new Listitem();
				
				
				
				
				
				list.appendChild(new Listcell());
				list.appendChild(new Listcell(rs.getString("UsageCode")));
				list.appendChild(new Listcell(rs.getString("itemname")));
				
				list.setAttribute("RowID", rs.getString("RowID"));		
				list.setAttribute("itemcode", rs.getString("itemcode"));		
				Listbox_ItemLeft.appendChild(list);
                
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
	
//	createDocument ==================================================================================
	
	public void onConfirmSend() throws Exception{
		
		if(Combobox_Department.getSelectedItem().getValue().equals("0")) {
			Messagebox.show("ไม่ได้เลือกแผนก !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		if(Listbox_ItemLeft.getSelectedCount() == 0) {
			Messagebox.show("ไม่ได้เลือกรายการ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		
						
		Messagebox.show("ยืนยันขอเบิกรายการ ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
    		public void onEvent(Event evt) throws Exception {
    			switch (((Integer) evt.getData()).intValue()) {
                  	case Messagebox.YES:
                  		onRequest();
                  		break;
                  	case Messagebox.NO:
                  		break;
    			}
    		}
    	});
				
	}

	public void onRequest() throws Exception{
		
		try {
			
			Iterator<Listitem> li = Listbox_ItemLeft.getSelectedItems().iterator();
			
			ArrayList<String> AS_ListRowID = new ArrayList<String>();
			ArrayList<String> AS_Listitemcode = new ArrayList<String>();

			
			while(li.hasNext()){
				Listitem list = (Listitem) li.next();	
				
				AS_ListRowID.add( (String)list.getAttribute("RowID") );
				AS_Listitemcode.add( (String)list.getAttribute("itemcode") );
				
			}

//			 Create Payout
			crearePayout(AS_ListRowID,AS_Listitemcode);
	
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}
	}
	
	private void crearePayout(ArrayList<String> AS_ListRowID,ArrayList<String> AS_Listitemcode) throws Exception {
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        
        try{
        	// -------------------------------------------------------  
        	// Create Payout
        	// -------------------------------------------------------
        	String S_PADocNo = null;
        	
	        
        	if(S_DocNo == null) {
        		
        		String isStatus = "2";
        		String DepartmentRef = (String)Combobox_Department.getSelectedItem().getValue();
        		
        		S_PADocNo = Payout.getPayoutDocNo(S_DB, "", S_DeptId, S_UserId, isStatus, "สร้างใบจ่ายเอกสารยืม" , DepartmentRef);
        		
    
        		
        	}else {
        		S_PADocNo = S_DocNo; 	
        	}
        	
        	// -------------------------------------------------------  
        	// Create Payout Detail
        	// -------------------------------------------------------
        	
        	String S_Value = "";
        	String S_ValueSub = "";

//        	insert payoutdetail

        	for(int i=0; i< AS_ListRowID.size(); i++) {        	
        		
        		String _idPayoutDetail = null;
        		
        		String Sql_paydetail = "SELECT" + 
        				"                 payoutdetail.ID" + 
        				"               FROM" + 
        				"                 payoutdetail" + 
        				"               WHERE" + 
        				"                 payoutdetail.DocNo = '" + S_PADocNo + "' " + 
        				"                 AND payoutdetail.ItemCode = '" + AS_Listitemcode.get(i) + "'  ";
    			System.out.println(Sql_paydetail);			
    			rs = stmt.executeQuery(Sql_paydetail);
    			if(rs.next()) {
    			 _idPayoutDetail = rs.getString("ID");	 
    			}
    			
    			 if(_idPayoutDetail == null) {
    				 _idPayoutDetail = "";
    			 }
    			 
        		if(_idPayoutDetail.equals("")) {
            		S_Value = "('" + S_PADocNo + "' , '" + AS_ListRowID.get(i) + "' , '" + AS_Listitemcode.get(i) + "' , '1' ,'' , NOW(),  0),";
            		
            		
                	if(!S_Value.equals("")) {
                		addPayoutDetail(S_Value.substring(0, S_Value.length() - 1));
                	}		
        		}
        		
	        	String S_SqlUpdate = 
						
	        			" UPDATE itemstock SET IsDispatch = 0, IsBorrow = 1, IsNew = 0, IsNewUsage = 0, IsStatus = 5, IsPay = 1 WHERE itemstock.RowID = '" + AS_ListRowID.get(i) + "' " ;
	        	
			    System.out.println(S_SqlUpdate);
			    
				stmt.executeUpdate(S_SqlUpdate);
        	} 
        	

        	
        	
//        	insert payoutdetailsub
        	
        	for(int i=0; i< AS_ListRowID.size(); i++) {        	

        	
    		String _idPayoutDetail_2 = null;

        	
	    		String Sql_paydetail_2 = "SELECT" + 
	    				"                 payoutdetail.ID" + 
	    				"               FROM" + 
	    				"                 payoutdetail" + 
	    				"               WHERE" + 
	    				"                 payoutdetail.DocNo = '" + S_PADocNo + "' " + 
	    				"                 AND payoutdetail.ItemCode = '" + AS_Listitemcode.get(i) + "'  ";
				System.out.println(Sql_paydetail_2);			
				rs = stmt.executeQuery(Sql_paydetail_2);
				if(rs.next()) {
					_idPayoutDetail_2 = rs.getString("ID");	 
				}

	   			 
     			S_ValueSub = "('" + _idPayoutDetail_2 + "' , '" + AS_ListRowID.get(i) + "' , 1 , 0 , '' , '" + AS_ListRowID.get(i) + "' , NOW() , 0 ),";

        	
            	if(!S_ValueSub.equals("")) {
            		addPayoutDetailSub(S_ValueSub.substring(0, S_ValueSub.length() - 1));
            	}
            	
//            	count qty
            	
            	
        		String _countQtyPayoutDetailSub = null;

            	
        		String Sql_Countpaydetail = "SELECT" + 
        				"                          COUNT( payoutdetailsub.Payoutdetail_RowID )  AS count_id" + 
        				"                        FROM" + 
        				"                          payoutdetailsub" + 
        				"                        WHERE payoutdetailsub.Payoutdetail_RowID = '" + _idPayoutDetail_2 + "'  ";
    			System.out.println(Sql_Countpaydetail);			
    			rs = stmt.executeQuery(Sql_Countpaydetail);
    			if(rs.next()) {
    				_countQtyPayoutDetailSub = rs.getString("count_id");	 
    			}
    			
	        	String S_SqlUpdateQty = 
						
	        			"UPDATE payoutdetail SET Qty = '" + _countQtyPayoutDetailSub + "'  WHERE payoutdetail.ID = '" + _idPayoutDetail_2 + "' " ;
	        	
			    System.out.println(S_SqlUpdateQty);
				stmt.executeUpdate(S_SqlUpdateQty);

        	} 
        	
        	
        	

        	
        	

        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	if(S_DocNo == null) {
	        	
	        	S_DocNo = S_PADocNo;
	        	
	        	onDisplayDocument(S_PADocNo);
	        	
	        	onDisplayDetail(S_PADocNo, false);
	        	
	        	showdata_item();
	        	
	        }else {
	        	onDisplayDetail(S_PADocNo, false);	        	
	        }
        	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	        
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	        
	        if (rs != null) {
                rs.close();
            }
	        
//	        Textbox_SearchItemStock.setText("");
//	        
//	        Textbox_SearchItemStock.focus();
//	        
//	        Listbox_Item.getItems().clear();
		}
	}
	
	private void addPayoutDetail(String S_Value) throws Exception {
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        
        
        try {
   
		     // -----------------------------------------------------
		     // Create Payout Detail
		     // -----------------------------------------------------
		     String S_Sql_Insert =  
		        		
		        		"	INSERT INTO payoutdetail ( " 
		    		 
		        	+	"	DocNo, " 
		        	+	"	ItemStockID, " 
					+	"	ItemCode, " 
					+	"	Qty, " 
					+	"	Remark, " 
					+	"	PayDate, " 
					+	"	IsStatus " 
					
					+	"	) VALUES ";
		     

		     
		    System.out.println(S_Sql_Insert + S_Value);
		    
			stmt.executeUpdate(S_Sql_Insert + S_Value);
				
			conn.commit();
				
        }catch(Exception e){
			e.printStackTrace();
		}finally{
		        
		 	if (stmt != null) {
		    	stmt.close();
		 	}
		        
		 	if (conn != null) {
		 		conn.close();
		 	}
        }
	}

	private void addPayoutDetailSub(String S_Value_Sub) throws Exception {
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        
        
        try {
   
		     // -----------------------------------------------------
		     // Create Payout Detail
		     // -----------------------------------------------------
		     String S_Sql_Insert =  
		        		
		        		"	INSERT INTO payoutdetailsub ( " 
		    		 
		        	+	"	Payoutdetail_RowID, " 
		        	+	"	ItemStockID, " 
					+	"	Qty, " 
					+	"	IsStatus, " 
					+	"	Remark, " 
					+	"	UsageCode, " 
					+	"	PayDate, " 
					+	"	OccuranceTypeID " 

					+	"	) VALUES ";
		     

		     
		    System.out.println(S_Sql_Insert + S_Value_Sub);
		    
			stmt.executeUpdate(S_Sql_Insert + S_Value_Sub);
				
			conn.commit();
				
        }catch(Exception e){
			e.printStackTrace();
		}finally{
		        
		 	if (stmt != null) {
		    	stmt.close();
		 	}
		        
		 	if (conn != null) {
		 		conn.close();
		 	}
        }
	}
//===================================================================================================

//  showdocument ====================================================================================
	
	public void onDisplayDocument(String S_PADocNo) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(getSqlPayout(S_PADocNo));
				
			int I_No = 1;
			
			Listbox_Document.getItems().clear();
//			Listbox_DocumentDetail.getItems().clear();
			
			while(rs.next()){
				Listitem list = new Listitem();

				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("DocNo")));
				list.appendChild(new Listcell(rs.getString("DocDate")));
				list.appendChild(new Listcell(rs.getString("Count_Detail")));
				list.appendChild(new Listcell(rs.getString("DepName")));	
				list.appendChild(newListcell(true, rs.getString("DocNo"), rs.getString("IsBorrowStatus")));
				list.appendChild(newListcellDelete(true, rs.getString("DocNo"), rs.getString("IsBorrowStatus")));

				//Attribute
                list.setAttribute("DocNo", rs.getString("DocNo"));
                list.setAttribute("IsStatus", rs.getString("IsStatus"));
                
                Listbox_Document.appendChild(list);
      
                I_No++;

			}
			
			if(I_No == 1) {
				 Messagebox.show("ไม่พบรายการ !!", "CSSD", Messagebox.OK, Messagebox.EXCLAMATION);
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
            
            S_DocNo = null;
		}
    }
	
	private String getSqlPayout(String S_PADocNo) {
		
		final String S_Text = Textbox_SearchDoc.getText();
		
		String S_Sql = "";
		
		S_Sql =
			"	SELECT 	payout.DocNo, "
		+	"			DATE_FORMAT(payout.CreateDate, '%d-%m-%Y') AS DocDate, " 
		+	"			COALESCE(payout.Remark, '') AS Descriptions, "
		+	"			payout.`IsStatus` AS IsStatus, "
		+	"			payout.`IsBorrowStatus`, " 
		+	"			department.DepName, " 
		
		+	"			(SELECT SUM(Qty) FROM payoutdetail WHERE payoutdetail.DocNo = payout.DocNo) AS Count_Detail " 
		
		+	"	FROM 	payout "	
		+	"   INNER JOIN department ON payout.DeptRec = department.ID  "
		+	"	WHERE 	payout.IsCancel = 0 "
		+ 	"	AND		payout.IsWeb = 1 "
		+ 	"	AND		payout.IsBorrowStatus = 6 "
		+ 	"	AND		payout.DeptID = " + S_DeptId + " "
		+   "   AND  	payout.IsStatus = 2    "; 
	
		if(S_PADocNo != null) {
			S_Sql +=
				"	AND  	payout.DocNo = '" + S_PADocNo + "' ";
		}else {
		
			if (!S_Text.equals("")) {		
				S_Sql +=
				"	AND  	(payout.DocNo LIKE '%" + S_Text + "%') ";
			}
			

			
			if (!Datebox_SDocDate.getText().trim().equals("") && !Datebox_EDocDate.getText().trim().equals("")) {		
				S_Sql +=
				"	AND  	( DATE(payout.CreateDate) BETWEEN DATE('" + DateTime.convertDate(Datebox_SDocDate.getText()) + "') AND DATE('" + DateTime.convertDate(Datebox_EDocDate.getText()) + "') ) ";
			}
			
			S_Sql += 
				"	ORDER BY payout.`IsStatus`, DATE(payout.CreateDate) DESC, payout.DocNo DESC LIMIT 100 ";
	
		}

		System.out.println(S_Sql);
	
		return S_Sql;
	
	}

	private Listcell newListcell(final boolean IsSave, final String S_DocNo, final String IsBorrowStatus){
		Listcell lc = new Listcell();

		if(IsBorrowStatus.equals("6")){
			
			Button btn = new Button("บันทึก");
			
			btn.setSclass("btn-success");
			btn.setHeight("25px");
			btn.setWidth("99%");
			btn.setStyle("background: " + ("#0275d8;") + "color:#ffffff;border-radius: 4px;"); 
			
			btn.addEventListener("onClick", new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
						if(Listbox_DocumentDetail.getItemCount() == 0)
							onDisplayDetail(S_DocNo, false);
						
						Messagebox.show("ยืนยันการยืม?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
				    		public void onEvent(Event evt) throws Exception {
				    			switch (((Integer) evt.getData()).intValue()) {
				                  	case Messagebox.YES:
				                  		onConfirmToPayout(S_DocNo);
				                  		break;
				                  	case Messagebox.NO:
				                  		break;
				    			}
				    		}
				    	});
					
		        }
		    });
			
			lc.appendChild(btn);
		}else if(IsBorrowStatus.equals("7") || IsBorrowStatus.equals("8")) {
			Button btn = new Button("คืน");
			
			btn.setSclass("btn-success");
			btn.setHeight("25px");
			btn.setWidth("99%");
			btn.setStyle("background: " + ("#0275d8;") + "color:#ffffff;border-radius: 4px;"); 
			
			btn.addEventListener("onClick", new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
//						if(Listbox_DocumentDetail.getItemCount() == 0)
//							onDisplayDetail(S_DocNo, false);
						
						Messagebox.show("ยืนยันการคืน?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
				    		public void onEvent(Event evt) throws Exception {
				    			switch (((Integer) evt.getData()).intValue()) {
				                  	case Messagebox.YES:
				                  		onConfirmToPayoutReturn(S_DocNo);
				                  		break;
				                  	case Messagebox.NO:
				                  		break;
				    			}
				    		}
				    	});
					
		        }
		    });
			
			lc.appendChild(btn);
		}
		
		return lc;
	}

	private Listcell newListcellDelete(final boolean IsSave, final String S_DocNo, final String IsBorrowStatus){
		Listcell lc = new Listcell();

		if(IsBorrowStatus.equals("6")){
			
			Button btn = new Button("ยกเลิก");
			
			btn.setSclass("btn-danger");
			btn.setHeight("25px");
			btn.setWidth("99%");
			btn.setStyle("background: " + ("#d9534f;") + "color:#ffffff;border-radius: 4px;"); 
			
			btn.addEventListener("onClick", new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
						if(Listbox_DocumentDetail.getItemCount() == 0)
							onDisplayDetail(S_DocNo, false);
						
						Messagebox.show("ยืนยันการยกเลิก?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
				    		public void onEvent(Event evt) throws Exception {
				    			switch (((Integer) evt.getData()).intValue()) {
				                  	case Messagebox.YES:
				                  		onCancelToPayout(S_DocNo);
				                  		break;
				                  	case Messagebox.NO:
				                  		break;
				    			}
				    		}
				    	});
					
		        }
		    });
			
			lc.appendChild(btn);
		}
		
		return lc;
	}

	
	
	public void onDisplayDocumentReturn() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(getSqlPayoutReturn());
				
			int I_No = 1;
			
			Listbox_Return_Document.getItems().clear();
//			Listbox_DocumentDetail.getItems().clear();
			
			while(rs.next()){
				Listitem list = new Listitem();

				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("DocDate")));
				list.appendChild(new Listcell(rs.getString("DocNo")));
				list.appendChild(new Listcell(rs.getString("borrowName")));
				list.appendChild(new Listcell(rs.getString("Qty")));	
				list.appendChild(new Listcell(rs.getString("DepName")));	
				list.appendChild(newListcell(true, rs.getString("DocNo"), rs.getString("IsBorrowStatus")));

				//Attribute
                list.setAttribute("DocNo", rs.getString("DocNo"));
                
                Listbox_Return_Document.appendChild(list);
      
                I_No++;

			}
			
			if(I_No == 1) {
				 Messagebox.show("ไม่พบรายการ !!", "CSSD", Messagebox.OK, Messagebox.EXCLAMATION);
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
            
            S_DocNo = null;
		}
    }

	
	private String getSqlPayoutReturn() {
		
		
		String S_Sql = "";
		
		S_Sql =
			"SELECT" + 
			"	payout.ID," + 
			"	payout.DocNo," + 
			"	DATE_FORMAT( payout.CreateDate, '%d/%m/%Y' ) AS DocDate," + 
			"	payout.IsBorrowStatus," + 
			"	department.DepName," + 
			"	( SELECT COUNT( payoutdetailsub.Qty ) " + 
			"					FROM payoutdetail" + 
			"					INNER JOIN payoutdetailsub ON payoutdetailsub.Payoutdetail_RowID = payoutdetail.ID" + 
			"					WHERE payoutdetail.DocNo = payout.DocNo AND payoutdetailsub.IsStatus = 0 ) AS Qty," + 
			"CASE" + 
			"		payout.IsBorrowStatus " + 
			"		WHEN '7' THEN" + 
			"		'รอคืน' " + 
			"		WHEN '8' THEN" + 
			"		'คืน (ค้างบางตัว)' " + 
			"		WHEN '9' THEN" + 
			"		'คืนครบ' " + 
			"	END AS borrowName " + 
			"FROM" + 
			"	payout" + 
			"	INNER JOIN department ON department.ID = payout.DeptRec " + 
			"WHERE" + 
			"	payout.IsStatus = 2 " + 
			"	AND ( payout.IsBorrowStatus = 7 OR payout.IsBorrowStatus = 8 OR payout.IsBorrowStatus = 9 )"; 
	


		System.out.println(S_Sql);
	
		return S_Sql;
	
	}

	
	
	
	
	
	
	
//	=================================================================================================
	
//	showdetail ======================================================================================
	
	public void onDisplayDetail(String S_DocNo, boolean IsAlert) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			this.S_DocNo = S_DocNo;
			
			rs = stmt.executeQuery(getSqlPayoutDetail(S_DocNo));
				
			int I_No = 1;
			
			Listbox_DocumentDetail.getItems().clear();
			
			while(rs.next()){
				Listitem list = new Listitem();
				
				final String S_ID = rs.getString("ID");
				final String S_ItemStockID = rs.getString("ItemStockID");
				
				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("UsageCode")));				
				list.appendChild(new Listcell(rs.getString("itemname")));
				list.appendChild(new Listcell(rs.getString("Qty")));
				list.appendChild(new Listcell(rs.getString("UnitName")));
				
				if(rs.getString("IsBorrowStatus").equals("6")) {
					
					Listcell lc_del = new Listcell("", "/images/ic_delete.png");
					lc_del.addEventListener("onClick", new EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							
							Messagebox.show("ยืนยันการลบรายการ ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
					    		public void onEvent(Event evt) throws Exception {
					    			switch (((Integer) evt.getData()).intValue()) {
					                  	case Messagebox.YES:
					                  		onUpdatePayoutDetail(S_ID,S_ItemStockID);
					                  		break;
					                  	case Messagebox.NO:
					                  		break;
					    			}
					    		}
					    	});
				        }
				    });
					
					list.appendChild(lc_del);
					
				}else {
					list.appendChild(new Listcell(""));
				}
				
				//Attribute
                list.setAttribute("ID", S_ID);
                
   
                Listbox_DocumentDetail.appendChild(list);
      
                I_No++;

			}
			
			if(IsAlert && I_No == 1) {
				 Messagebox.show("ไม่พบรายการ !!", "CSSD", Messagebox.OK, Messagebox.EXCLAMATION);
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
	
	private String getSqlPayoutDetail(String S_DocNo) {
		
		String S_Sql = "";
		
		S_Sql =
			"SELECT" + 
			"            payoutdetailsub.ID," + 
			"            payoutdetailsub.ItemStockID," + 
			"            payoutdetail.DocNo," + 
			"            itemstock.UsageCode," +
			"            item.itemname," + 
			"            item.itemcode," + 
			"            units.UnitName," + 
			"            payoutdetailsub.Qty," + 
			"            ( SELECT payout.IsBorrowStatus FROM payout WHERE payout.DocNo = '" + S_DocNo + "' LIMIT 1 ) AS IsBorrowStatus " + 
			"          FROM" + 
			"            payoutdetailsub" + 
			"            INNER JOIN itemstock ON payoutdetailsub.ItemstockID = itemstock.RowID" + 
			"            INNER JOIN payoutdetail ON payoutdetail.ID = payoutdetailsub.Payoutdetail_RowID" + 
			"            INNER JOIN item ON itemstock.ItemCode = item.itemcode" + 
			"            INNER JOIN units ON item.UnitID = units.ID " + 
			"          WHERE" + 
			"            payoutdetail.DocNo = '" + S_DocNo + "' ";
		
		System.out.println(S_Sql);
	
		return S_Sql;
	
	}

	private void onUpdatePayoutDetail(String S_ID, String S_ItemStockID) throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        
        try{
        	String _S_DocNo = S_DocNo ;
        	
    		String _Payoutdetail_RowID = null;
    		int _count_paydetailsub = 0;

        	
    		String Sql_paydetailSub = "SELECT payoutdetailsub.Payoutdetail_RowID " + 
    				"                   FROM payoutdetailsub " + 
    				"                   WHERE payoutdetailsub.ID = '" + S_ID + "' ";
			System.out.println(Sql_paydetailSub);			
			rs = stmt.executeQuery(Sql_paydetailSub);
			if(rs.next()) {
				_Payoutdetail_RowID = rs.getString("Payoutdetail_RowID");	 
			}
			
    		String Sql_paydetailSub_count = "SELECT COUNT(payoutdetailsub.Payoutdetail_RowID) AS count_paydetailsub " + 
    				"                  FROM payoutdetailsub " + 
    				"                  WHERE payoutdetailsub.Payoutdetail_RowID = '" + _Payoutdetail_RowID + "' ";
			System.out.println(Sql_paydetailSub_count);			
			rs = stmt.executeQuery(Sql_paydetailSub_count);
			if(rs.next()) {
				_count_paydetailsub = rs.getInt("count_paydetailsub");	 
			}
			

        	
//	          Delete DetailSub

        	String S_SqlDeleteSub = 
					
        			"DELETE	"
        		+ 	"FROM 	payoutdetailsub "
        		+ 	"WHERE 	payoutdetailsub.ID = " + S_ID ;
        	
			System.out.println(S_SqlDeleteSub);
			stmt.executeUpdate(S_SqlDeleteSub);

			
			if(_count_paydetailsub == 1) {
//		          Delete Detail
		        	String S_SqlDelete = 
		        						
		        			"DELETE	"
		        		+ 	"FROM 	payoutdetail "
		        		+ 	"WHERE 	ID = " + _Payoutdetail_RowID ;
		        	
					System.out.println(S_SqlDelete);
					stmt.executeUpdate(S_SqlDelete);
				}else {
					
		    		String _countQtyPayoutDetailSub = null;

		        	
		    		String Sql_Countpaydetail = "SELECT" + 
		    				"                          COUNT( payoutdetailsub.Payoutdetail_RowID )  AS count_id" + 
		    				"                        FROM" + 
		    				"                          payoutdetailsub" + 
		    				"                        WHERE payoutdetailsub.Payoutdetail_RowID = '" + _Payoutdetail_RowID + "'  ";
					System.out.println(Sql_Countpaydetail);			
					rs = stmt.executeQuery(Sql_Countpaydetail);
					if(rs.next()) {
						_countQtyPayoutDetailSub = rs.getString("count_id");	 
					}
					
		        	String S_SqlUpdateQty = 
							
		        			"UPDATE payoutdetail SET Qty = '" + _countQtyPayoutDetailSub + "'  WHERE payoutdetail.ID = '" + _Payoutdetail_RowID + "' " ;
		        	
				    System.out.println(S_SqlUpdateQty);
					stmt.executeUpdate(S_SqlUpdateQty);
					
				}
        	
        	
        	String S_SqlUpdate = 
					
        			" UPDATE itemstock SET IsBorrow = 0 WHERE itemstock.RowID = '" + S_ItemStockID + "' " ;
			System.out.println(S_SqlUpdate);
			stmt.executeUpdate(S_SqlUpdate);
			
			


	        conn.commit();
  
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	        
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	        
	        if (rs != null) {
                rs.close();
            }
	        
	        onDisplayDetail(S_DocNo, false);
	        
//	        updateLabelQty();
	        
	        showdata_item();
	        
	        Textbox_SearchItemStock.setText("");
	        Textbox_SearchItemStock.focus();
		}
	}

	
	
	public void onDisplayReturnDetail(String S_DocNo, boolean IsAlert) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			this.S_DocNo = S_DocNo;
			
			rs = stmt.executeQuery(getSqlPayoutReturnDetail(S_DocNo));
				
			int I_No = 1;
			
			Listbox_Return_Item.getItems().clear();
			
			while(rs.next()){
				Listitem list = new Listitem();
				
				final String S_ID = rs.getString("ID");
				final String S_ItemStockID = rs.getString("ItemStockID");
				final String S_UsageCode = rs.getString("UsageCode");

				list.appendChild(new Listcell());
				list.appendChild(new Listcell(rs.getString("UsageCode")));				
				list.appendChild(new Listcell(rs.getString("itemname")));
				list.appendChild(new Listcell(rs.getString("Qty")));
				list.appendChild(new Listcell(rs.getString("UnitName")));

				
				//Attribute
                list.setAttribute("ID", S_ID);
                list.setAttribute("ItemStockID", S_ItemStockID);
                list.setAttribute("UsageCode", S_UsageCode);

   
                Listbox_Return_Item.appendChild(list);
      
                I_No++;

			}
			
			if(IsAlert && I_No == 1) {
				 Messagebox.show("ไม่พบรายการ !!", "CSSD", Messagebox.OK, Messagebox.EXCLAMATION);
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

	private String getSqlPayoutReturnDetail(String S_DocNo) {
		
		String S_Sql = "";
		
		S_Sql =
			"SELECT" + 
			"            payoutdetailsub.ID," + 
			"            payoutdetailsub.ItemStockID," + 
			"            payoutdetail.DocNo," + 
			"            itemstock.UsageCode," +
			"            item.itemname," + 
			"            item.itemcode," + 
			"            units.UnitName," + 
			"            payoutdetailsub.Qty," + 
			"            ( SELECT payout.IsBorrowStatus FROM payout WHERE payout.DocNo = '" + S_DocNo + "' LIMIT 1 ) AS IsBorrowStatus " + 
			"          FROM" + 
			"            payoutdetailsub" + 
			"            INNER JOIN itemstock ON payoutdetailsub.ItemstockID = itemstock.RowID" + 
			"            INNER JOIN payoutdetail ON payoutdetail.ID = payoutdetailsub.Payoutdetail_RowID" + 
			"            INNER JOIN item ON itemstock.ItemCode = item.itemcode" + 
			"            INNER JOIN units ON item.UnitID = units.ID " + 
			"          WHERE" + 
			"            payoutdetail.DocNo = '" + S_DocNo + "' "
					+ "AND payoutdetailsub.IsStatus = 0";
		
		System.out.println(S_Sql);
	
		return S_Sql;
	
	}

	
//	=================================================================================================
	
//	saveBorrow  AND cancelBorrow
	
	private void onConfirmToPayout(final String S_DocNo) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        
		try{
			
        	// Update Detail
			String S_SqlDetail = 
					
					"UPDATE		payoutdetail "
				+ 	"SET		IsStatus = 0 "
				+ 	"WHERE		DocNo = '" + S_DocNo + "' ";
	
			String S_Sql = 
					
					"UPDATE		payout "
				+ 	"SET		IsBorrowStatus = 7, "
				+ 	"			ModifyDate = NOW(), "
				+ 	"			Remark = 'ยืมบันทึก(รอคืน)' "
				+ 	"WHERE		DocNo = '" + S_DocNo + "' ";
	
			System.out.println(S_SqlDetail);
			System.out.println(S_Sql);
					
			stmt.executeUpdate(S_SqlDetail);
	        stmt.executeUpdate(S_Sql);
	        
	        conn.commit();
	        
	        Messagebox.show("บันทึกสำเร็จ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
            			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	        

			
			
	        this.S_DocNo = null;
	    	Listbox_Document.getItems().clear();	
	    	Listbox_DocumentDetail.getItems().clear();
	    	
	    	Textbox_SearchItemStock.setText("");
	    	Textbox_SearchItemStock.focus();
		}
    }
	
	private void onConfirmToPayoutReturn(final String S_DocNo) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        Statement stmt2 = conn.createStatement();
        ResultSet rs2 = null;

		try{
			
			Iterator<Listitem> li = Listbox_Return_Item.getSelectedItems().iterator();
			
			ArrayList<String> AS_ItemStockID = new ArrayList<String>();
			ArrayList<String> AS_PayoutDetailSubID = new ArrayList<String>();
			ArrayList<String> AS_UsageCode = new ArrayList<String>();

			int _count =  0;
			while(li.hasNext()){
				Listitem list = (Listitem) li.next();	
				
				
				AS_ItemStockID.add( (String)list.getAttribute("ItemStockID") );
				AS_UsageCode.add( (String)list.getAttribute("UsageCode") );
				AS_PayoutDetailSubID.add( (String)list.getAttribute("ID") );
				
				
				_count ++;
			}
			
//			count all
			int _countAll = Listbox_Return_Item.getItemCount();

			
//			check count
			
			if(_count == _countAll) {
	        	// Update Detail
				String S_SqlUpdateStatus = 
						
						"UPDATE		payout "
					+ 	"SET		IsBorrowStatus = 9 "
					+ 	"WHERE		DocNo = '" + S_DocNo + "' ";
				
				System.out.println(S_SqlUpdateStatus);				
				stmt.executeUpdate(S_SqlUpdateStatus);
			}else {
				String S_SqlUpdateStatus = 
						
						"UPDATE		payout "
					+ 	"SET		IsBorrowStatus = 8 "
					+ 	"WHERE		DocNo = '" + S_DocNo + "' ";
				
				System.out.println(S_SqlUpdateStatus);				
				stmt.executeUpdate(S_SqlUpdateStatus);
			}
			
			
			
        	for(int i=0; i< AS_PayoutDetailSubID.size(); i++) {        	
        		
        		String _Payoutdetail_RowID = null;
        		int _Payoutdetail_Count = 0;

        		String Sql_paydetailSub = "SELECT" + 
        				"                 payoutdetailsub.Payoutdetail_RowID" + 
        				"               FROM" + 
        				"                 payoutdetailsub" + 
        				"               WHERE" + 
        				"                 payoutdetailsub.ID = '" + AS_PayoutDetailSubID.get(i) + "'  ";
        		
    			System.out.println(Sql_paydetailSub);			
    			rs = stmt.executeQuery(Sql_paydetailSub);
    			if(rs.next()) {
    				_Payoutdetail_RowID = rs.getString("Payoutdetail_RowID");	 
    			}
        		
        		String Sql_paydetailSubCount = "SELECT" + 
		        				"                 COUNT(payoutdetailsub.Payoutdetail_RowID) AS count_paydetailsub" + 
		        				"               FROM" + 
		        				"                 payoutdetailsub" + 
		        				"               WHERE" + 
		        				"                 payoutdetailsub.Payoutdetail_RowID = '" + _Payoutdetail_RowID + "'  " + 
								"               AND payoutdetailsub.IsStatus = 0  ";
        		
    			System.out.println(Sql_paydetailSubCount);			
    			rs2 = stmt2.executeQuery(Sql_paydetailSubCount);
    			if(rs2.next()) {
    				_Payoutdetail_Count = rs2.getInt("count_paydetailsub");	 
    			}
    			
    			if(_Payoutdetail_Count == 1) {
    				String S_SqlUpdatePayoutDetail = 
    						
    						"UPDATE		payoutdetail "
    					+ 	"SET		IsStatus = 1 "
    					+ 	"WHERE		ID = '" + _Payoutdetail_RowID + "' ";
    				
    				System.out.println(S_SqlUpdatePayoutDetail);				
    				stmt.executeUpdate(S_SqlUpdatePayoutDetail);
    			}
    			
				String S_SqlUpdatePayoutDetailSub = 
						
						"UPDATE		payoutdetailsub "
					+ 	"SET		IsStatus = 3 "
					+ 	"WHERE		ID = '" + AS_PayoutDetailSubID.get(i) + "' ";
				
				System.out.println(S_SqlUpdatePayoutDetailSub);				
				stmt.executeUpdate(S_SqlUpdatePayoutDetailSub);
				
				String S_SqlUpdateItemStock = 
						
						"UPDATE		itemstock "
					+ 	"SET		IsDispatch = 0, "
					+ 	"		    IsBorrow = 0 "
					+ 	"WHERE		RowID = '" + AS_ItemStockID.get(i) + "' ";
				
				System.out.println(S_SqlUpdatePayoutDetailSub);				
				stmt.executeUpdate(S_SqlUpdatePayoutDetailSub);
				
        		
        	}


	        
	        conn.commit();
	        
	        Messagebox.show("บันทึกสำเร็จ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
            			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	        
			onDisplayReturnDetail(S_DocNo, true);
			onDisplayDocumentReturn();
	        
	        this.S_DocNo = null;
	    	Listbox_Document.getItems().clear();	
	    	Listbox_DocumentDetail.getItems().clear();
	    	
	    	Textbox_SearchItemStock.setText("");
	    	Textbox_SearchItemStock.focus();
		}
    }

	
	
	
	
	private void onCancelToPayout(final String S_DocNo) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        
		try{
			
        	// Update Detail
//			String S_SqlDetail = 
//					
//					"UPDATE		payoutdetail "
//				+ 	"SET		IsCancel = 0 "
//				+ 	"WHERE		DocNo = '" + S_DocNo + "' ";
	
			String S_Sql = 
					
					"UPDATE		payout "
				+ 	"SET		IsCancel = 1, "
				+ 	"			ModifyDate = NOW(), "
				+ 	"			Remark = 'ยกเลิกเอกสารยืม' "
				+ 	"WHERE		DocNo = '" + S_DocNo + "' ";
	
//			System.out.println(S_SqlDetail);
			System.out.println(S_Sql);
					
//			stmt.executeUpdate(S_SqlDetail);
	        stmt.executeUpdate(S_Sql);
	        
	        conn.commit();
	        
	        Messagebox.show("ยกเลิกสำเร็จ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
            			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	        
	        this.S_DocNo = null;
	    	Listbox_Document.getItems().clear();	
	    	Listbox_DocumentDetail.getItems().clear();
	    	
	    	Textbox_SearchItemStock.setText("");
	    	Textbox_SearchItemStock.focus();
		}
    }

//	

}
