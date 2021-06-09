package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.poseintelligence.cssd.document.SendSterile;
import com.poseintelligence.cssd.model.ModelMaster;
import com.poseintelligence.cssd.utillity.DateTime;

@SuppressWarnings("rawtypes")
public class DepartmentSendSterile extends GenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3525844790119368775L;
	
	private boolean B_IsCreate = true;
	
	// Variable Configuration
	private int SS_IsFindStatus = 0;
	private int SS_IsTag = 0;
	private int SS_IsHn = 0;
	
	// Variable Session
	private Session session = Sessions.getCurrent();
	
	private String 	S_UserId,
					S_DeptId,
					S_UserName,
					S_IsAdmin,
					S_EmpCode,
					S_DepName,
					S_DB;
	
	private String S_DocNo = null;
	private String S_IsStatus = "-5";
	private String S_TempStatus = "-5";
	public  String xHn_Code;
	public	String DocnoHn;

	// Widget
	private Listbox Listbox_Document;
	private Listbox Listbox_ItemStock;
	private Listbox Listbox_DocumentDetail;
	
	private Textbox Textbox_SearchItemStock;
	private Textbox Textbox_Search;


	private Datebox Datebox_SDocDate;
	private Datebox Datebox_EDocDate;	
	
	private Combobox Combobox_Status;
	

	private Checkbox Checkbox_Mode;
	private Checkbox Checkbox_Group;
	

	
	private Button Button_Send;
	//private Button Button_SearchItemStock;
	private Label Label_Search;
	private Image Image_Search;
	


	
	private Window WinProcess;
	private Window Window_PopUp;
	private Window Window_PopUp_hn;
	
	private Listheader Listheader_UsageID;
	private Listheader Listheader_Mfg;
	private Listheader Listheader_Exp;
	private Listheader Listhead_Tag;
	private Listheader Listhead_Hn;
	
	//	hn
	private Borderlayout PopUp_borderlayout;
	private North PopUp_north;
	private Vlayout PopUp_vlayout;
	private Hlayout PopUp_hlayout;

	
	private Combobox ComboboxNameHN;
	private Textbox txtNameHn;
	private Textbox txtOperationHn;
	private Button btnAddHn;
	private Button btnSaveHn;
	private Button btnCancelHn;
	// private Checkbox Checkbox_IsDispatch;
	
	
	// Variable Local
	List<ModelMaster> Model_ResterileType = new ArrayList<>();
	
	public void onCreate() throws Exception {
		
		bySession();
		
		// Configuration
		onDisplayWebConfig();
		
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
		
		if(SS_IsTag == 1) {
			Listhead_Tag.setVisible(true);
		}else {
			Listhead_Tag.setVisible(false);
		}
		
		if(SS_IsHn == 1) {
			Listhead_Hn.setVisible(true);
		}else {
			Listhead_Hn.setVisible(false);
		}
		
		
		Datebox_SDocDate.setText(DateTime.getDateLastMonth());
		Datebox_EDocDate.setText(DateTime.getDateNow());
		
		defineReSterileType();
		
		
		
		PopUp_borderlayout = (Borderlayout) Window_PopUp_hn.getChildren().get(0);
		PopUp_north = (North) PopUp_borderlayout.getChildren().get(0);
		PopUp_vlayout = (Vlayout) PopUp_north.getChildren().get(0);
		ComboboxNameHN = (Combobox) PopUp_vlayout.getChildren().get(1);
		txtNameHn = (Textbox) PopUp_vlayout.getChildren().get(3);
		txtOperationHn = (Textbox) PopUp_vlayout.getChildren().get(5);

		PopUp_hlayout = (Hlayout) PopUp_vlayout.getChildren().get(6);

		btnAddHn = (Button) PopUp_hlayout.getChildren().get(0);
		btnSaveHn = (Button) PopUp_hlayout.getChildren().get(1);
		btnCancelHn = (Button) PopUp_hlayout.getChildren().get(2);

		
		eventHn();

		
	}
	
	private void onDeleteSendSterileDetail(){
		
		Messagebox.show("ยืนยันการลบรายการ ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
    		public void onEvent(Event evt) throws Exception {
    			switch (((Integer) evt.getData()).intValue()) {
                  	case Messagebox.YES:
                  		
                  		String S_ListID = "";
                		String S_ListItemStockID = "";
                		
                		Iterator<Listitem> li = Listbox_DocumentDetail.getSelectedItems().iterator();
            			
       
            			while(li.hasNext()){
            				Listitem list = (Listitem) li.next();
            				
            				if(list.isDisabled()) {
            					continue;
            				}
            				
            				S_ListItemStockID += (String)list.getAttribute("ItemStockID") + ",";
            				S_ListID += (String)list.getAttribute("ID") + ",";
            			}

                  		if(!S_ListID.equals("")) {
                  			
                  			S_ListID = S_ListID.substring(0, S_ListID.length() - 1);
                  			S_ListItemStockID = S_ListItemStockID.substring(0, S_ListItemStockID.length() - 1);
                  			
                  			onUpdateSterileDetail(S_ListID, S_ListItemStockID);
                  		}
                  		
                  		break;
                  		
                  		
                  	case Messagebox.NO:
                  		break;
    			}
    		}
    	});
	      
	}
	
	private void onAddOccurrence(){
		callResterileType(true, null, null);
	}

	
	
	// Event Hn	
	public void eventHn() throws Exception{
		
		ComboboxNameHN.addEventListener("onBlur", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				System.out.print("onNameTH");
				onNameTH(ComboboxNameHN.getText());

	        }
	    });
		ComboboxNameHN.addEventListener("onChanging", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				System.out.print("defindName");

				defindName(ComboboxNameHN.getText());


	        }
	    });
		ComboboxNameHN.addEventListener("onFocus", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				
				ComboboxNameHN.select();


	        }
	    });
		
		btnAddHn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				
				CreateDocument_HN();


	        }
	    });
    }
	
	// Event
	
	
	
	

	
	public void onCheck$Checkbox_Group(Event event) throws Exception {
		
		Listheader_UsageID.setLabel(Checkbox_Group.isChecked() ? "Item Code" : "UsageID");
		Listheader_Mfg.setLabel(Checkbox_Group.isChecked() ? "จำนวน" : "MFG");
		Listheader_Exp.setLabel(Checkbox_Group.isChecked() ? "ส่งล้าง" : "EXP");

		// Display 
		onDisplayItemStock(Textbox_SearchItemStock.getText());
	}

	public void onSelect$Listbox_Document(Event event) throws Exception {
		
		S_DocNo = (String)Listbox_Document.getSelectedItem().getAttribute("DocNo");
		S_IsStatus = (String)Listbox_Document.getSelectedItem().getAttribute("IsStatus");
		
		// Display Pay Detail
		if(S_DocNo != null && (!S_DocNo.equals("")))
			onDisplayDetail(S_DocNo, true);
	}
	
	public void onSelect$Combobox_Status(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onOK$Textbox_Search(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onClick$Button_SearchDoc(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onClick$Image_SearchDoc(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onChange$Datebox_SDocDate(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onChange$Datebox_EDocDate(Event event) throws Exception {
		// Display Pay
		onDisplayDocument(null);
	}
	
	public void onOK$Textbox_SearchItemStock(Event event) throws Exception {
		if(Checkbox_Mode.isChecked()) {
			// Find ItemStock
			
			String S_Input = com.poseintelligence.cssd.utillity.Text.convertQR(Textbox_SearchItemStock.getText());
			
			addSendSterileByQR(S_Input);
		}else {
			// Display ItemStock
			onDisplayItemStock(Textbox_SearchItemStock.getText());
		}
	}
	
	public void onClick$Button_SearchItemStock(Event event) throws Exception {
		// Display ItemStock
		onDisplayItemStock(Textbox_SearchItemStock.getText());
	}
	
	public void onClick$Image_Search(Event event) throws Exception {
		// Display ItemStock
		onDisplayItemStock(Textbox_SearchItemStock.getText());
	}
	
	public void onCheck$Checkbox_IsDispatch(Event event) throws Exception {
		// Display ItemStock
		onDisplayItemStock(Textbox_SearchItemStock.getText());
	}
	
	public void onChanging$Textbox_SearchItemStock(InputEvent event) throws Exception {
		// Display ItemStock
		onDisplayItemStock(event.getValue());
	}

	public void onClick$Button_Send(Event event) throws Exception {
		onConfirmSend();
	}
	
	public void onCheck$Checkbox_Mode(Event event) throws Exception {
		try {
			boolean B_Chk = !Checkbox_Mode.isChecked();
			
			Button_Send.setVisible(B_Chk);
			//Button_SearchItemStock.setVisible(B_Chk);
			Label_Search.setValue(B_Chk ? "ค้นหา" : "สแกนรหัสอุปกรณ์");
			
			Image_Search.setSrc(B_Chk ? "/images/ic_search_c.png" : "/images/ic_barcode.jpg");
			
			if(!B_Chk)
				Listbox_ItemStock.getItems().clear();
			
			Textbox_SearchItemStock.setText("");
			Textbox_SearchItemStock.focus();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$Label_Resterile(Event event) throws Exception {
		onAddOccurrence();
	}
	
	public void onClick$Image_Resterile(Event event) throws Exception {
		onAddOccurrence();
	}

	public void onClick$Label_Delete(Event event) throws Exception {
		onDeleteSendSterileDetail();
	}
	
	public void onClick$Image_Delete(Event event) throws Exception {	
		onDeleteSendSterileDetail();
	}
	
	
	// ==================================================================================================
	
	public void onDisplayItemStock(String S_Text) throws Exception{
		
		if(Checkbox_Mode.isChecked())
			return;
			
		Listbox_ItemStock.getItems().clear();
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
    	
    	boolean B_IsGroup = Checkbox_Group.isChecked();

		try{	
			
			rs = stmt.executeQuery(B_IsGroup ? getSqlGroupItemStock(S_Text) : getSqlItemStock(S_Text));

			int I_No = 1;
	
			while(rs.next()){
				Listitem list = new Listitem();

				list.appendChild(new Listcell());
				list.appendChild(new Listcell(rs.getString(B_IsGroup ? "ItemCode" : "UsageCode")));
				list.appendChild(new Listcell(rs.getString("Item_name")));
				
				if(B_IsGroup) {
					
					list.appendChild(new Listcell(rs.getString("StockQty")));
					
					int I_StockQty = rs.getInt("StockQty");
					
					final Intbox inb = new Intbox();
					
					inb.setWidth("99%");
					inb.setStyle("text-align:center;font-weight:bold;");
					
					inb.addEventListener("onBlur", new EventListener<Event>() {
						public void onEvent(Event event) throws Exception {		
							
							//System.out.println("onBlur");
							
							try {
								
								if(inb.getText().equals("")) {
									return;
								}
								
								int I_EnterQty = Integer.valueOf(inb.getText()).intValue();
								
								if(I_StockQty >= I_EnterQty) {
									list.setSelected(true);
								}else {
									list.setSelected(false);
									inb.setText("");
									inb.focus();
								}
								
							}catch(Exception e) {
								list.setSelected(false);
								inb.setText("");
								inb.focus();
							}
							
				        }
				    });
					
					inb.addEventListener("onOK", new EventListener<Event>() {
						
						public void onEvent(Event event) throws Exception {
							
							//System.out.println("onOK");
							//System.out.println(inb.getText());
							
							try {
								
								if(inb.getText().equals("")) {
									return;
								}
								
								int I_EnterQty = Integer.valueOf(inb.getText()).intValue();
								
								if(I_StockQty >= I_EnterQty) {
									list.setSelected(true);
								}else {
									list.setSelected(false);
									inb.setText("");
									inb.focus();
									return;
								}
								
							}catch(Exception e) {
								list.setSelected(false);
								inb.setText("");
								inb.focus();
								return;
							}

							onSend();
				        }
				    });
	
					Listcell lc = new Listcell();
					lc.appendChild(inb);
					
					list.appendChild(lc);
					
					list.setAttribute("ItemCode", rs.getString("ItemCode"));
				}else {
					list.appendChild(new Listcell(rs.getString("PackDate")));
					list.appendChild(new Listcell(rs.getString("ExpireDate")));
					
					list.setTooltiptext(rs.getString("RowID"));
					
					list.setAttribute("RowID", rs.getString("RowID"));
				}
				
                Listbox_ItemStock.appendChild(list);
                
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
            
            Textbox_SearchItemStock.setText("");
            Textbox_SearchItemStock.focus();
            
		}
   }
	
	private String getSqlGroupItemStock(String S_Text) {
		
		String S_Sql = "";
		
		S_Sql = 	
			"	SELECT		'' AS RowID, "
		+	"				itemstock.ItemCode, "
		+	"				'' AS PackDate, "
		+	"				'' AS ExpireDate, "
		+	"				'' AS UsageCode, "
		+	"				item.itemname AS Item_name,"
		+ 	"				COUNT(*) AS StockQty "
		
		+	"	FROM		itemstock "
		
		+	"	LEFT JOIN 	item "
		+	"	ON 			item.itemcode = itemstock.ItemCode "
		
		+	"	WHERE 		itemstock.IsStatus = 5 "
		
		+ 	"	AND 		itemstock.DepID = " + S_DeptId + " ";
				
		if(!S_Text.trim().equals("")) {
			S_Sql +=	
			"	AND 		("
			+ 	"				itemstock.ItemCode LIKE '%" + S_Text + "%' "
			+ 	"				OR item.itemname LIKE '%" + S_Text + "%' "
			+ 	"			) ";
		}
		
		if(SS_IsFindStatus > 0) {
			S_Sql +=
			"	AND 		itemstock.IsDispatch = " + ( SS_IsFindStatus == 1 ? "1" : "0" );
		}
	
		S_Sql +=
			"	GROUP BY 	item.itemname, itemstock.ItemCode "
		
		+	"	LIMIT 100 ";
		
		System.out.println(S_Sql);
		
		return S_Sql;
	}

	private String getSqlItemStock(String S_Text) {
		
		String S_Sql = "";
		
		S_Sql = 	
			"	SELECT		itemstock.RowID, "
		+	"				itemstock.ItemCode, "
		+	"				COALESCE(DATE_FORMAT(itemstock.PackDate, '%d-%m-%Y'),'-') AS PackDate, "
		+	"				COALESCE(DATE_FORMAT(itemstock.ExpireDate, '%d-%m-%Y'),'-') AS ExpireDate, "
		+	"				itemstock.UsageCode, "
		+	"				item.itemname AS Item_name "
		
		+	"	FROM		itemstock "
		
		+	"	LEFT JOIN 	item "
		+	"	ON 			item.itemcode = itemstock.ItemCode "
		
		+	"	WHERE 		itemstock.IsStatus = 5 "
		
		+ 	"	AND 		DepID = " + S_DeptId + " ";

		if(!S_Text.trim().equals("")) {
			S_Sql +=	
			"	AND 		("
			+ 	"				itemstock.ItemCode LIKE '%" + S_Text + "%' "
			+ 	"				OR item.itemname LIKE '%" + S_Text + "%' "
			+ 	"				OR itemstock.UsageCode LIKE '%" + S_Text + "%' "
			+ 	"			) ";
		}
		
		if(SS_IsFindStatus > 0) {
			S_Sql +=
			"	AND 		itemstock.IsDispatch = " + ( SS_IsFindStatus == 1 ? "1" : "0" );
		}
	
		S_Sql +=
			"	ORDER BY 	itemstock.UsageCode "
		
		+	"	LIMIT 100 ";
		
		System.out.println(S_Sql);
		
		return S_Sql;
	}
	
	public void onConfirmSend() throws Exception{
		
		if(Listbox_ItemStock.getSelectedCount() == 0) {
			Messagebox.show("ไม่ได้เลือกรายการ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		if(Listbox_Document.getSelectedCount() == 1 && Listbox_Document.getSelectedItem().getAttribute("IsStatus").equals("0")) {
			Messagebox.show("ไม่สามารถนำเข้ารายการได้ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}		
						
		Messagebox.show("ยืนยันนำเข้ารายการ ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
    		public void onEvent(Event evt) throws Exception {
    			switch (((Integer) evt.getData()).intValue()) {
                  	case Messagebox.YES:
                  		onSend();
                  		break;
                  	case Messagebox.NO:
                  		break;
    			}
    		}
    	});
				
	}
	
	public void onSend() throws Exception{
		
		boolean B_IsGroup = Checkbox_Group.isChecked();
		
		if(B_IsGroup) {
			sendByGroupItem();
		}else {
			send();
		}

	}
	
	public void sendByGroupItem() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        Statement stmt_ = conn.createStatement();
        ResultSet rs = null;
        
		try {
			
			String S_SSDocNo = null;
			
			// Check DocNo
			if(S_DocNo == null) {
        		S_SSDocNo = SendSterile.getSendSterileDocNo(S_DB, "", S_DeptId, S_UserId, S_TempStatus, "");
        	}else {
        		S_SSDocNo = S_DocNo;
        	}
			
			if(S_SSDocNo == null) {
				return;
			}
			
			Iterator<Listitem> li = Listbox_ItemStock.getSelectedItems().iterator();
			
			String S_ListRowID = "";
			String S_Qty = "1";	
			String S_ItemCode = null;
			int I_Qty = 0;
			
			while(li.hasNext()){
				
				Listitem list = (Listitem) li.next();	
				
				S_ItemCode = (String)list.getAttribute("ItemCode");
				S_Qty = ((Intbox)((Listcell)list.getChildren().get(4)).getChildren().get(0)).getText();
				S_ListRowID = "";
				
				//System.out.println(S_ItemCode + " = " + S_Qty);
				
				if(!list.isSelected() || S_Qty.trim().equals("") || S_Qty.trim().equals("0")) {
					continue;
				}
				
				I_Qty = Integer.valueOf(S_Qty).intValue();
				
				String S_Sql = 
						
				  	"	SELECT 	itemstock.RowID "
				
				+ 	"   FROM   	itemstock "

				+ 	"   WHERE  	itemstock.ItemCode = '" + S_ItemCode + "' "
				+ 	"	AND		itemstock.DepID = " + S_DeptId + " "
				+	"	AND  	itemstock.IsStatus = 5 "
				+	"	AND  	itemstock.IsCancel = 0 "
				+	"	AND  	itemstock.IsPay = 1 "
				 
				+ 	"	AND 	itemstock.IsStatus = 5 "
				
				+ 	"	ORDER BY itemstock.ExpireDate ASC, itemstock.RowID ASC "
				
				+ 	"	LIMIT " + I_Qty + " ";
				
				rs = stmt_.executeQuery(S_Sql);

				while(rs.next()){
					S_ListRowID += rs.getString("RowID") + ",";
				}
				
				if(S_ListRowID.equals("")) {
					continue;
				}else {
					S_ListRowID = S_ListRowID.substring(0, S_ListRowID.length() - 1);
				}
				
				
				String S_SqlInsertSendSterileDetail = 
						
					"	INSERT INTO  sendsteriledetail "
				+ 	"	( "
				+ 	"   	sendsteriledetail.SendSterileDocNo, "
				+ 	"   	sendsteriledetail.ItemStockID, "
				+ 	"   	sendsteriledetail.Qty, "
				+ 	"   	sendsteriledetail.Remark, "
				+ 	"   	sendsteriledetail.UsageCode, "
				+ 	"   	sendsteriledetail.IsSterile, "
				+ 	"   	sendsteriledetail.IsStatus,"
				+ 	"		sendsteriledetail.SSdetail_IsWeb, "
				+ 	"		sendsteriledetail.RefDocNo "
				+ 	"	) "
				
				+ 	"	SELECT 	"
				+ 	"			'" + S_SSDocNo + "', "
				+ 	"   		itemstock.RowID, "
				+ 	"   		1, "
				+ 	"   		'', "
				+ 	"   		itemstock.UsageCode, "
				+ 	"   		0, "
				+ 	"   		1, "
				+ 	"   		" + S_TempStatus + ", "
				+ 	"			'" + S_SSDocNo + "' "
				

				+ 	"   FROM   	itemstock "

				+ 	"   WHERE  	RowID IN (" + S_ListRowID + ") "
				+ 	"	AND		itemstock.DepID = " + S_DeptId + " "
				+	"	AND  	itemstock.IsStatus = 5 "
				+	"	AND  	itemstock.IsCancel = 0 "
				+	"	AND  	itemstock.IsPay = 1 "
				 
				+ 	"	AND 	itemstock.IsStatus = 5 "
				
				+ 	"	ORDER BY itemstock.RowID"
				
				+ 	"	LIMIT " + I_Qty + " ";
				
				// Update Item Stock
				String S_SqlUpdateItemStock = 
						
						"UPDATE		itemstock "
					+ 	"SET		itemstock.IsStatus = " + S_TempStatus + " "
					
					+ 	"WHERE 		RowID IN (" + S_ListRowID + ") "
					
					+ 	"AND 		IsStatus = 5 " ;
				
				
				System.out.println(S_SqlInsertSendSterileDetail);
				
				System.out.println(S_SqlUpdateItemStock);
				
				stmt.executeUpdate(S_SqlInsertSendSterileDetail);
				
		        stmt.executeUpdate(S_SqlUpdateItemStock);
		        
			}

			
			// Clear Item Sock
			Listbox_ItemStock.getItems().clear();
			
			onDisplayDocument(S_SSDocNo);
        	
        	onDisplayDetail(S_SSDocNo, false);

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
			if (stmt_ != null) {
	            stmt_.close();
	        }
			
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
	
	public void send() throws Exception{
		
		try {
			Iterator<Listitem> li = Listbox_ItemStock.getSelectedItems().iterator();
			
			String S_ListItemStockID = "";
			
			while(li.hasNext()){
				Listitem list = (Listitem) li.next();	
				
				S_ListItemStockID += (String)list.getAttribute("RowID") + ",";
			}
			
				
			// Check S_ListItemStockID
			if(S_ListItemStockID.equals("")) {
				return;
			}else {
				S_ListItemStockID = S_ListItemStockID.substring(0, S_ListItemStockID.length() - 1);
			}
			

			// Create SendSterile
			createSendSterile(S_ListItemStockID);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}
	}
	
	private void createSendSterile(String S_ListItemStockID) throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        
        try{
        	  
        	// Create Send Sterile
        	
        	String S_SSDocNo = null;
        	
	        
        	if(S_DocNo == null) {
        		S_SSDocNo = SendSterile.getSendSterileDocNo(S_DB, "", S_DeptId, S_UserId, S_TempStatus, "");
        	}else {
        		S_SSDocNo = S_DocNo;
        	}
					
			if( S_SSDocNo != null && (!S_SSDocNo.equals("")) ){

				String S_SqlInsertSendSterileDetail = 
						
					"	INSERT INTO  sendsteriledetail "
				+ 	"	( "
				+ 	"   	sendsteriledetail.SendSterileDocNo, "
				+ 	"   	sendsteriledetail.ItemStockID, "
				+ 	"   	sendsteriledetail.Qty, "
				+ 	"   	sendsteriledetail.Remark, "
				+ 	"   	sendsteriledetail.UsageCode, "
				+ 	"   	sendsteriledetail.IsSterile, "
				+ 	"   	sendsteriledetail.IsStatus,"
				+ 	"		sendsteriledetail.SSdetail_IsWeb, "
				+ 	"		sendsteriledetail.RefDocNo "
				+ 	"	) "
				
				+ 	"	SELECT 	"
				+ 	"			'" + S_SSDocNo + "', "
				+ 	"   		itemstock.RowID, "
				+ 	"   		1, "
				+ 	"   		'', "
				+ 	"   		itemstock.UsageCode, "
				+ 	"   		0, "
				+ 	"   		1, "
				+ 	"   		" + S_TempStatus + ", "
				+ 	"			'" + S_SSDocNo + "' "
				

				+ 	"   FROM   	itemstock "

				+ 	"   WHERE  	RowID IN (" + S_ListItemStockID + ") "
				
				+ 	"	AND 	IsStatus = 5 ";
				
				// Update Item Stock
				String S_SqlUpdateItemStock = 
						
						"UPDATE		itemstock "
					+ 	"SET		itemstock.IsStatus = " + S_TempStatus + " "
					
					+ 	"WHERE 		RowID IN (" + S_ListItemStockID + ") "
					
					+ 	"AND 		IsStatus = 5 " ;
				
				
				//System.out.println(S_SqlInsertSendSterileDetail);
				//System.out.println(S_SqlUpdateItemStock);
				
				stmt.executeUpdate(S_SqlInsertSendSterileDetail);
		        stmt.executeUpdate(S_SqlUpdateItemStock);
		        
		        conn.commit();
		        
		        // Clear Item Sock
				Listbox_ItemStock.getItems().clear();
   
		        if(S_DocNo == null) {
		        	
		        	S_DocNo = S_SSDocNo;
		        	
		        	onDisplayDocument(S_SSDocNo);
		        	onDisplayDetail(S_SSDocNo, false);
		        }else {
		        	onDisplayDetail(S_SSDocNo, false);
		        	updateLabelQty();
		        }
  
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
		}
	}
	
	private void addSendSterileByQR(String S_QR) throws Exception {
		
		if(Listbox_Document.getSelectedCount() == 1 && !Listbox_Document.getSelectedItem().getAttribute("IsStatus").equals("-5")) {
			Messagebox.show("ไม่สามารถนำเข้ารายการได้ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		String S_ItemStockID = findItemStock(S_QR);
		
		if(S_ItemStockID == null) {
			Messagebox.show("ไม่พบรายการ !!");
			
			Textbox_SearchItemStock.setText("");
	        
	        Textbox_SearchItemStock.focus();
	        
			return;
		}
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        
        try{
        	  
        	// Create Send Sterile
        	
        	String S_SSDocNo = null;
        	    
        	if(S_DocNo == null) {
        		S_SSDocNo = SendSterile.getSendSterileDocNo(S_DB, "", S_DeptId, S_UserId, S_TempStatus ,"");
        	}else {
        		S_SSDocNo = S_DocNo;
        	}
					
			if( S_SSDocNo != null && (!S_SSDocNo.equals("")) ){

				String S_SqlInsertSendSterileDetail = 
						
					"	INSERT INTO  sendsteriledetail "
				+ 	"	( "
				+ 	"   	sendsteriledetail.SendSterileDocNo, "
				+ 	"   	sendsteriledetail.ItemStockID, "
				+ 	"   	sendsteriledetail.Qty, "
				+ 	"   	sendsteriledetail.Remark, "
				+ 	"   	sendsteriledetail.UsageCode, "
				+ 	"   	sendsteriledetail.IsSterile, "
				+ 	"   	sendsteriledetail.IsStatus,"
				+ 	"		sendsteriledetail.SSdetail_IsWeb, "
				+ 	"		sendsteriledetail.RefDocNo "
				+ 	"	) "
				
				+ 	"	SELECT 	"
				+ 	"			'" + S_SSDocNo + "', "
				+ 	"   		itemstock.RowID, "
				+ 	"   		1, "
				+ 	"   		'', "
				+ 	"   		itemstock.UsageCode, "
				+ 	"   		0, "
				+ 	"   		1, "
				+ 	"   		" + S_TempStatus + ", "
				+ 	"			'" + S_SSDocNo + "' "
				

				+ 	"   FROM   	itemstock "

				+ 	"   WHERE  	RowID IN (" + S_ItemStockID + ") "
				
				+ 	"	AND 	IsStatus = 5 ";
				
				// Update Item Stock
				String S_SqlUpdateItemStock = 
						
						"UPDATE		itemstock "
					+ 	"SET		itemstock.IsStatus = " + S_TempStatus + " "
					
					+ 	"WHERE 		RowID IN (" + S_ItemStockID + ") "
					
					+ 	"AND 		IsStatus = 5 " ;
				
				
				//System.out.println(S_SqlInsertSendSterileDetail);
				//System.out.println(S_SqlUpdateItemStock);
				
				stmt.executeUpdate(S_SqlInsertSendSterileDetail);
		        stmt.executeUpdate(S_SqlUpdateItemStock);
		        
		        conn.commit();
		        
		        // Clear Item Sock
				Listbox_ItemStock.getItems().clear();
   
		        if(S_DocNo == null) {
		        	
		        	S_DocNo = S_SSDocNo;
		        	
		        	onDisplayDocument(S_SSDocNo);
		        	onDisplayDetail(S_SSDocNo, false);
		        }else {
		        	onDisplayDetail(S_SSDocNo, false);
		        	updateLabelQty();
		        }
  
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
	        
	        Textbox_SearchItemStock.setText("");
	        
	        Textbox_SearchItemStock.focus();
		}
	}
	
	private String findItemStock(String S_QR) throws Exception{
		String S_Sql = "";
		
		S_Sql = 	
			"	SELECT		itemstock.RowID "

		+	"	FROM		itemstock "
		
		+	"	WHERE 		itemstock.IsStatus = 5 "
		
		+ 	"	AND			itemstock.UsageCode = '" + S_QR + "' ";

		if(SS_IsFindStatus > 0) {
			S_Sql +=
			"	AND 		itemstock.IsDispatch = " + ( SS_IsFindStatus == 1 ? "1" : "0" ); 
		}
	
		S_Sql +=
			"	LIMIT 1 ";
		
		
		//System.out.println(S_Sql);
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(S_Sql);
			
			return rs.next() ? rs.getString("RowID") : null;	
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
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
	
	private void updateLabelQty(){
		
		// Update List Cell QTY
		
    	try {
    		
    		if(Listbox_Document.getItemCount() == 0) {
    			return;
    		}
    		
    		if(Listbox_Document.getSelectedCount() == 0) {
    			Listbox_Document.setSelectedIndex(0);		
    		}
    		
    		((Listcell) Listbox_Document.getSelectedItem().getChildren().get(3)).setLabel( Integer.toString( Listbox_DocumentDetail.getItemCount() ) );
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private String getSqlSendSterile(String S_SSDocNo) {
		
		final String S_Text = Textbox_Search.getText();
		
		String S_Sql = "";
		
		S_Sql =
			"	SELECT 	sendsterile.DocNo, "
		+	"			DATE_FORMAT(sendsterile.DocDate, '%d-%m-%Y') AS DocDate, " 
		+	"			COALESCE(sendsterile.Remark, '') AS Descriptions, "
		+	"			sendsterile.`IsStatus` AS IsStatus, "
		+	"			sendsterile.`IsHn`, " 
		
		+	"			(SELECT COUNT(*) FROM sendsteriledetail WHERE sendsteriledetail.SendSterileDocNo = sendsterile.DocNo) AS Count_Detail " 
								
		+	"	FROM 	sendsterile "	
	
		+	"	WHERE 	sendsterile.IsCancel = 0 "
		+ 	"	AND		sendsterile.IsWeb = 1 "
		+ 	"	AND		sendsterile.DeptID = " + S_DeptId + " ";
		
		if (!Datebox_SDocDate.getText().trim().equals("") && !Datebox_EDocDate.getText().trim().equals("")) {		
			S_Sql +=
			"	AND  	( DATE(sendsterile.DocDate) BETWEEN DATE('" + DateTime.convertDate(Datebox_SDocDate.getText()) + "') AND DATE('" + DateTime.convertDate(Datebox_EDocDate.getText()) + "') ) ";
		}
	
		if(S_SSDocNo != null) {
			S_Sql +=
				"	AND  	sendsterile.DocNo = '" + S_SSDocNo + "' ";
		}else {
		
			if (!S_Text.equals("")) {		
				S_Sql +=
				"	AND  	(sendsterile.DocNo LIKE '%" + S_Text + "%') ";
			}
			
			try {
				switch (Combobox_Status.getSelectedIndex()) {
				case 1: 
					S_Sql +=
					"	AND  	sendsterile.IsStatus = " + S_TempStatus + " ";
					break;
					
				case 2:
					S_Sql +=
					"	AND  	sendsterile.IsStatus = 0 ";
					break;
					
				default : 
						//S_Sql += "	AND		(sendsterile.IsStatus = " + S_TempStatus + " OR sendsterile.IsStatus = 0) ";
						
				}
			}catch(Exception e) {
				//S_Sql += "	AND		(sendsterile.IsStatus = " + S_TempStatus + " OR sendsterile.IsStatus = 0) ";
			}
			
			
			
			S_Sql += 
				"	ORDER BY sendsterile.`IsStatus`, DATE(sendsterile.DocDate) DESC, sendsterile.DocNo DESC LIMIT 100 ";
	
		}

		System.out.println(S_Sql);
	
		return S_Sql;
	
	}

	public void onDisplayDocument(String S_SSDocNo) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			onProcess(true);
			
			rs = stmt.executeQuery(getSqlSendSterile(S_SSDocNo));
				
			int I_No = 1;
			
			Listbox_Document.getItems().clear();
			Listbox_DocumentDetail.getItems().clear();
			
			while(rs.next()){
				Listitem list = new Listitem();
				
				
				Listcell lc = new Listcell();
				
				int IsHn = rs.getInt("IsHn");
				String DocNo = rs.getString("DocNo");

				
					Button btn = new Button("HN");
					if(IsHn == 1) {
						btn.setStyle("color:#ffffff;background: #28a745;border-radius: 4px;");			
					}else {
						btn.setStyle("color:#ffffff;background: #5a6268;border-radius: 4px;");								
					}
					btn.setHeight("25px");
					btn.setWidth("99%");
					lc.appendChild(btn);
					
 
					btn.addEventListener("onClick", new EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							
								callModalHn(DocNo,IsHn);

				        }
				    });
				
				
				
				
				

				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("DocDate")));
				list.appendChild(new Listcell(rs.getString("DocNo")));
				list.appendChild(new Listcell(rs.getString("Count_Detail")));
				list.appendChild(new Listcell(rs.getString("Descriptions")));
				list.appendChild(lc);	
				list.appendChild(newListcell(rs.getString("DocNo"), rs.getString("IsStatus")));
				
				//Attribute
                list.setAttribute("DocNo", rs.getString("DocNo"));
                list.setAttribute("IsStatus", rs.getString("IsStatus"));
                
                list.setTooltiptext(rs.getString("IsStatus"));
                
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
            S_IsStatus = S_TempStatus;
            
            onProcess(false);
		}
    }
	
	public void callModalHn(final String DocNo, final int IsHn) throws Exception {
		
		
		if(IsHn == 0) {
			Window_PopUp.getChildren().clear();
			Window_PopUp.setVisible(true);
			Window_PopUp.setFocus(true);
			Window_PopUp.setPosition("center");
			Window_PopUp_hn.setAttribute("DocNo",DocNo);
			Window_PopUp_hn.setMode("modal");
			Window_PopUp.setClosable(true);		
		}else {
			cancelHn(DocNo);
		}
		

		

	}

	
	
	private Listcell newListcell(final String S_DocNo, final String IsStatus){
		Listcell lc = new Listcell();
		
		Button btn = new Button("บันทึก");
		
		if(IsStatus.equals("-5")) {
	
			btn.setSclass("btn-success");
			btn.setHeight("25px");
			btn.setWidth("99%");
			btn.setStyle("color:#ffffff;background: #0275d8;border-radius: 4px;");
			
			btn.addEventListener("onClick", new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					
					if(Listbox_DocumentDetail.getItemCount() == 0)
						onDisplayDetail(S_DocNo, false);
					
					Messagebox.show("ยืนยันส่งล้าง ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
			    		public void onEvent(Event evt) throws Exception {
			    			switch (((Integer) evt.getData()).intValue()) {
			                  	case Messagebox.YES:
			                  		onConfirmToSendSterile(S_DocNo);
			                  		break;
			                  	case Messagebox.NO:
			                  		break;
			    			}
			    		}
			    	});
		        }
		    });
			
			lc.appendChild(btn);
			
		} else {
			lc.setStyle("color:#3979dd;");
			lc.setLabel("ส่งล้าง");
		} 
		
		return lc;
	}
	
	private void onConfirmToSendSterile(final String S_DocNo) throws Exception{

		try{
			// Update ItemStock & Send Sterile
			updatesendsterile(S_DocNo);
            		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}
    }
	
	private void updatesendsterile(String S_DocNo_) throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement(); 
        
        try{
        	
        	// Update Send Sterile Detail
			String S_SqlDetail = 
					
					"UPDATE		sendsteriledetail "
				+ 	"SET		IsStatus = 0 "
				+ 	"WHERE		SendSterileDocNo = '" + S_DocNo_ + "' ";
	
			String S_Sql = 
					
					"UPDATE		sendsterile "
				+ 	"SET		IsStatus = 0, "
				+ 	"			ModifyDate = NOW(), "
				+ 	"			Remark = 'แผนกส่งล้าง' "
				+ 	"WHERE		DocNo = '" + S_DocNo_ + "' ";
			
			// Update Item Stock
			String S_SqlUpdateItemStock = 
					
					"UPDATE		itemstock "
					
				+ 	"INNER JOIN	sendsteriledetail "
				+ 	"ON 		itemstock.RowID = sendsteriledetail.ItemStockID "
	                
				+ 	"SET		itemstock.IsStatus = 0, " 
				+ 	"        	itemstock.IsNewUsage = 0, " 
				+ 	"        	itemstock.IsNew = 0, " 
				+ 	"         	itemstock.IsPay = 0 , " 
				+ 	"        	itemstock.IsDispatch = 0, " 
				+ 	"        	itemstock.LastReceiveDeptDate = NOW(), "  						
				+ 	"			itemstock.LastSendDeptDate = NOW() "
				
				+ 	"WHERE 		sendsteriledetail.SendSterileDocNo = '" + S_DocNo_ + "' "
				
				+ 	"AND 		itemstock.IsStatus = " + S_TempStatus + " " ;
			 
			
			System.out.println(S_SqlDetail);
			System.out.println(S_Sql);
			System.out.println(S_SqlUpdateItemStock);
			
			stmt.executeUpdate(S_SqlUpdateItemStock);			
			stmt.executeUpdate(S_SqlDetail);
	        stmt.executeUpdate(S_Sql);
	        
	        conn.commit();
   
	        Messagebox.show("บันทึกสำเร็จ !!", "CSSD", Messagebox.OK, Messagebox.INFORMATION);
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			S_DocNo = null;
	        
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
  
	        S_IsStatus = S_TempStatus;
	        
	        Listbox_Document.getItems().clear();
	        
	        Listbox_DocumentDetail.getItems().clear();

		}
	}
		
	public void onDisplayDetail(String S_DocNo, boolean IsAlert) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			onProcess(true);
			
			this.S_DocNo = S_DocNo;
			
			rs = stmt.executeQuery(getSqlSendSterileDetail(S_DocNo));
				
			int I_No = 1;
			
			Listbox_DocumentDetail.getItems().clear();
			
			while(rs.next()){
				Listitem list = new Listitem();
				
				final String S_ID = rs.getString("ID");
				final String IsTag = rs.getString("IsTag");
				final String S_ItemStockID = rs.getString("ItemStockID");
				
				
				Listcell lc_del = new Listcell("", !rs.getString("ItemStock_IsStatus").equals(S_TempStatus) ? "/images/ic_delete_g.png" : "/images/ic_delete.png");
				lc_del.addEventListener("onClick", new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						
						if(list.isDisabled())
							return;
						
						Messagebox.show("ยืนยันการลบรายการ ?" , "CSSD", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,new EventListener<Event>() {
				    		public void onEvent(Event evt) throws Exception {
				    			switch (((Integer) evt.getData()).intValue()) {
				                  	case Messagebox.YES:
				                  		
				                  		if(list.isDisabled())
											return;
				                  		
				                  		onUpdateSterileDetail(S_ID, S_ItemStockID);
				                  		
				                  		break;
				                  	case Messagebox.NO:
				                  		break;
				    			}
				    		}
				    	});
			        }
			    });
				
				
				final Listcell lc_opt = new Listcell("", getIconR( rs.getString("ResterileType") ) );
				lc_opt.setTooltiptext(rs.getString("ResterileTypeName"));
				lc_opt.setAttribute("ResterileTypeID", rs.getString("ResterileType"));
				
				lc_opt.addEventListener("onClick", new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						
						if(list.isDisabled())
							return;
						
						callResterileType(false, lc_opt, S_ID);
			        }
			    });
				
				
				
				
				final Listcell lc_tag = new Listcell("", getIconTag( rs.getString("IsTag") ) );
				lc_tag.setTooltiptext(rs.getString("IsTag"));
				lc_tag.setAttribute("IsTag", rs.getString("IsTag"));
				
				lc_tag.addEventListener("onClick", new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						
						if(list.isDisabled())
							return;
						onUpdateTag(IsTag, S_ItemStockID);
			        }
			    });
				
				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("UsageCode")));				
				list.appendChild(new Listcell(rs.getString("itemname")));
				list.appendChild(new Listcell(rs.getString("PackDate")));				
				list.appendChild(new Listcell(rs.getString("ExpireDate")));
				list.appendChild(lc_tag);
				list.appendChild(lc_opt);
				list.appendChild(lc_del);
				
				//Attribute
                list.setAttribute("ID", S_ID);
                list.setAttribute("ItemStockID", rs.getString("ItemStockID"));
                
                list.setDisabled(!rs.getString("ItemStock_IsStatus").equals(S_TempStatus));

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
            
            onProcess(false);
		}
    }
	
	
	private String getIconTag(final String ResterileType){	
		return (ResterileType == null || ResterileType.equals("0")) ? "/images/ic_tag_black.png" : "/images/ic_tag_blue.png" ;
	}

	private String getIconR(final String ResterileType){	
		return (ResterileType == null || ResterileType.equals("0")) ? "/images/ic_r_grey.png" : "/images/ic_r_red.png" ;
	}

	private void callResterileType(boolean B_IsMultiple, final Listcell lc_opt, final String S_ID) {
		Window_PopUp.getChildren().clear();
		Window_PopUp.setVisible(true);
		Window_PopUp.setFocus(true);
		Window_PopUp.setPosition("center");
		Window_PopUp.setMode("modal");
		Window_PopUp.setBorder("normal");
		Window_PopUp.setClosable(true);		
		Window_PopUp.setHeight("200px");
		Window_PopUp.setWidth("500px");
		
		Vbox vbx = new Vbox();		
		vbx.setWidth("100%");
		vbx.setAlign("center");
		
		Combobox cbb = new Combobox();
		cbb.setWidth("250px");
		
		/*
		Comboitem citem_ = new Comboitem();
		citem_.setLabel("-");
		citem_.setValue(null);
		cbb.appendChild(citem_);
		*/
		
        Iterator li = Model_ResterileType.iterator();

        while(li.hasNext()) {
        	ModelMaster m = (ModelMaster) li.next();
        	
			Comboitem citem = new Comboitem();
			citem.setLabel(m.getS_Name());
			citem.setValue(m.getS_Id());

			cbb.appendChild(citem);
		}
        
        cbb.setSelectedIndex(0);
		
		Button btn = new Button("บันทึก");
		btn.setWidth("150px");
		btn.setStyle("color:#ffffff;background: #2e5fbd;border-radius: 4px;");
		btn.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {	
				
				try {
					
					String S_ListID = "";
            		
					String S_ReSterileTypeName = cbb.getSelectedItem().getLabel();
					String S_ReSterileTypeId = (String)cbb.getSelectedItem().getValue();
					
					if(B_IsMultiple) {
	
                		Iterator<Listitem> li = Listbox_DocumentDetail.getSelectedItems().iterator();
            			
            			while(li.hasNext()){
            				Listitem list = (Listitem) li.next();
            				
            				if(list.isDisabled()) {
            					continue;
            				}
            				
            				S_ListID += (String)list.getAttribute("ID") + ",";
            			}

                  		if(!S_ListID.equals("")) {
                  			
                  			S_ListID = S_ListID.substring(0, S_ListID.length() - 1);
                  			
                  		}else {
                  			return;
                  		}
					}
						
					if(cbb.getSelectedIndex() <= 0) {
	
						if(B_IsMultiple) {
							
							// Update ReSterile Type ID
							updateReSterileType(S_ListID, null, "");
							
							onDisplayDetail(S_DocNo, false);
							
						}else {
		
							// Update ReSterile Type ID
							updateReSterileType(S_ID, null, "");
							
							// Set list item
							lc_opt.setTooltiptext("");
							lc_opt.setAttribute("ResterileTypeID", null);
							lc_opt.setImage("/images/ic_r_grey.png");
						}
						
					}else {
						if(B_IsMultiple) {
							
							// Update ReSterile Type ID
							updateReSterileType(S_ListID, S_ReSterileTypeId, S_ReSterileTypeName);
							
							onDisplayDetail(S_DocNo, false);
							
						}else {
							
							
							// Update ReSterile Type ID
							updateReSterileType(S_ID, S_ReSterileTypeId, S_ReSterileTypeName);
							
							// Set list item
							lc_opt.setTooltiptext(S_ReSterileTypeName);
							lc_opt.setAttribute("ResterileTypeID", S_ReSterileTypeId);
							lc_opt.setImage("/images/ic_r_red.png");
						}
						
					}
					
					
		
				}catch(Exception e) {
					
					e.printStackTrace();
					
					lc_opt.setTooltiptext("");
					lc_opt.setAttribute("ResterileTypeID", null);
					
					lc_opt.setImage("/images/ic_r_grey.png");
				}
				
				Window_PopUp.setVisible(false);
	        }
	    });
		
		vbx.appendChild(cbb);
		vbx.appendChild(new Separator());
		vbx.appendChild(new Separator());
		vbx.appendChild(btn);		
		
		Window_PopUp.appendChild(new Caption("บันทึก Resterile"));
		Window_PopUp.appendChild(vbx);
	}
	
	private String getSqlSendSterileDetail(String S_DocNo) {
			
		String S_Sql = "";
		
		S_Sql =
			"	SELECT 		item.itemcode, " 
		+	"				item.itemname, "
		+ 	"				sendsteriledetail.ID AS ID, "
		+	"				sendsteriledetail.ItemStockID, "
		+	"				sendsteriledetail.UsageCode, "
		+	"				COALESCE(DATE_FORMAT(itemstock.PackDate, '%d-%m-%Y'),'-') AS PackDate, "
		+	"				COALESCE(DATE_FORMAT(itemstock.ExpireDate, '%d-%m-%Y'),'-') AS ExpireDate, "
		+	"				sendsteriledetail.IsStatus,"
		+ 	"				sendsteriledetail.ResterileType, "
		+ 	"				resteriletype.ResterileType AS ResterileTypeName,"
		+ 	"				itemstock.IsStatus AS ItemStock_IsStatus,"
		+ 	"				itemstock.IsTag"
		
		+	"	FROM 		sendsteriledetail "
		
		+	"	INNER JOIN 	itemstock  "
		+	"	ON			sendsteriledetail.ItemStockID = itemstock.RowId  "
		
		+	"	INNER JOIN 	item  "
		+	"	ON			item.itemcode = itemstock.ItemCode  "
		
		+	"	LEFT JOIN 	resteriletype  "
		+	"	ON			resteriletype.ID = sendsteriledetail.ResterileType "
					
		+	"	WHERE  		sendsteriledetail.SendSterileDocNo = '" + S_DocNo + "' "
		//+ "	AND			itemstock.DepID = " + S_DeptId + " "
		//+	"	AND  		itemstock.IsStatus = " + S_TempStatus + " "
		//+	"	AND  		itemstock.IsCancel = 0 "
		//+	"	AND  		itemstock.IsPay = 1 "
		
		
		
		+	"	ORDER BY 	item.itemcode "
		
		+ 	"	LIMIT 5000 ";
		
		System.out.println(S_Sql);
	
		return S_Sql;
	
	}
	
	private boolean updateReSterileType(final String S_ID, final String S_ReSterileTypeId, final String S_ReSterileTypeName) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        
		try {
			
	        
			String S_SqlUpdate = 
					
					"UPDATE		sendsteriledetail "
				+ 	"SET		ResterileType = " + S_ReSterileTypeId + ", "
				+ 	"			IsSterile = " + (S_ReSterileTypeId == null ? "0" : "1" ) + ", "
				+ 	"			Remark = '" + S_ReSterileTypeName + "' "
				+ 	"WHERE 		ID IN (" + S_ID + ") ";
				
			
			//System.out.println(S_SqlUpdate);
	
			int I_AffectedRows = stmt.executeUpdate(S_SqlUpdate);
			
			//System.out.println("I_AffectedRows = " + I_AffectedRows);
			
			return I_AffectedRows > 0;
			
		}catch(Exception e) {
			return false;
		}finally{
	        
	        if (stmt != null) {
	            stmt.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
		}
	}
	
	private void onUpdateSterileDetail(String S_ID, String S_ItemStockID) throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        
        try{
        	 
        	// Delete Send Sterile Detail
        	String S_SqlDeleteSendSterileDetail = 
        						
        			"DELETE	"
        		+ 	"FROM 	sendsteriledetail "
        		+ 	"WHERE 	ID IN (" + S_ID + ") " ;
        				
        	// Update Item Stock
			String S_SqlUpdateItemStock = 
					
					"UPDATE		itemstock "
				+ 	"SET		itemstock.IsStatus = ( CASE WHEN IsNew = 0 THEN 5 ELSE 10 END ), " 
				+ 	" 			itemstock.IsCancel = ( CASE WHEN IsNew = 0 THEN 0 ELSE 1 END ) "
				
				+ 	"WHERE 		RowID IN (" + S_ItemStockID + ") " 
				
				+ 	"AND 		IsStatus = " + S_TempStatus + " " ;
			
			
			//System.out.println(S_SqlDeleteSendSterileDetail);
			//System.out.println(S_SqlUpdateItemStock);
			
			stmt.executeUpdate(S_SqlDeleteSendSterileDetail);
	        stmt.executeUpdate(S_SqlUpdateItemStock);
	        
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
	        
	        updateLabelQty();
	        
	        Listbox_ItemStock.getItems().clear();
	        
	        Textbox_SearchItemStock.setText("");
	        Textbox_SearchItemStock.focus();
		}
	}
	
	private void onUpdateTag(String IsTag, String S_ItemStockID) throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        
        try{
        	int tag = 1;
        	 if(IsTag.equals("1")) {
        		 tag = 0;
        	 }
        				
        	// Update Item Stock
			String S_SqlUpdateItemStock = 
					
					"UPDATE		itemstock "
				+ 	"SET		itemstock.IsTag = " + tag + " " 
				
				+ 	"WHERE 		RowID IN (" + S_ItemStockID + ") " ;
			
			
			//System.out.println(S_SqlDeleteSendSterileDetail);
			System.out.println(S_SqlUpdateItemStock);
			
	        stmt.executeUpdate(S_SqlUpdateItemStock);
	        
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
	        
	        updateLabelQty();
	        
	        Listbox_ItemStock.getItems().clear();
	        
	        Textbox_SearchItemStock.setText("");
	        Textbox_SearchItemStock.focus();
		}
	}
	

	
	//	==============================================================================
	//  HN 
	//	==============================================================================
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
	
	
 	private void onNameTH(String xSearch) throws Exception {
		int i = (Integer) xSearch.trim().indexOf(':');

		if (i < 0)
			return;

		ComboboxNameHN.getItems().clear();
		
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
		System.out.println("ComboboxNameHN : " + xSearch);

		try {
			rs = stmt.executeQuery(getSqlTab1(xSearch.trim().substring(0, i).replace(" ", "%")));

			if (rs.next()) {
				xHn_Code = rs.getString("HnCode");
				ComboboxNameHN.setText(rs.getString("HnCode"));
				txtNameHn.setText(rs.getString("FName"));
				btnAddHn.setVisible(false);
				btnSaveHn.setVisible(true);
			}	
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
 	public String getSqlTab1(String xSearch) {
		
		String Sql="SELECT " + 
				"                      hotpitalnumber.HnCode,  " + 
				"                      hotpitalnumber.Id , " + 
				"                      hotpitalnumber.FName " + 
				"                    FROM  hotpitalnumber";
				if(!xSearch.equals("")) {
				Sql += "  WHERE ( (hotpitalnumber.HnCode LIKE  '%"+xSearch.replace(" ", "%")+"%') "+
				"	OR (hotpitalnumber.FName LIKE  '%"+xSearch.replace(" ", "%")+"%') )  " ;
		 		}
				
		
		System.out.println("getSqlTab1 : " + Sql);
		return Sql;

}
	
	
 	public void CreateDocument_HN()throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	        Class.forName(c.S_MYSQL_DRIVER);
	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	        conn.setAutoCommit(false);
	        
	        Statement stmt = conn.createStatement();
	    	ResultSet rs = null;
	    	
	String _DocNo =  (String)Window_PopUp_hn.getAttribute("DocNo");
	
	String HnCode_add = ComboboxNameHN.getText();
	System.out.println(_DocNo);
	
	try {
//		
//
			DocnoHn = generateDoc();
//			
			System.out.println("gen Doc = "+DocnoHn);
			String insert = "INSERT INTO hncode( " + 
					"              hncode.DocNo, " + 
					"              hncode.DocDate, " + 
					"              hncode.HnCode, " + 
					"              hncode.ModifyDate, " + 
					"              hncode.UserCode, " + 
					"              hncode.DeptID, " + 
					"              hncode.Qty, " + 
					"              hncode.DocNo_SS, " + 
					"              hncode.IsStatus, " + 
					"              hncode.Remark, " + 
					"              hncode.IsCancel, " + 
					"              hncode.B_ID, " +
					"              hncode.IsWeb " + 
					"            	)VALUES( " + 
					"              '"+DocnoHn+"', " + 
					"              DATE(NOW()), " + 
					"              '"+HnCode_add+"', " + 
					"              NOW(), " + 
					"              '"+S_UserId+"', " + 
					"              '"+S_DeptId+"', " + 
					"              0, " + 
					"               '"+_DocNo+"', " + 
					"              0, " + 
					"              '', " + 
					"              0, " + 
					"              '1', " +
					"              '1' " + 
					"            ) ";
			stmt.executeUpdate(insert);

			String update ="UPDATE sendsterile SET sendsterile.IsHn = 1  WHERE sendsterile.DocNo = '"+_DocNo+"' ";
			stmt.executeUpdate(update);
			
		}catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("ERROR onSaveHn : " + e.getMessage());
			System.out.println("ERROR onSaveHn : " + e.getMessage());
		}finally {
			 Window_PopUp_hn.setMode("embedded");
			 Window_PopUp_hn.setVisible(false);
			 onDisplayDocument(null);
		}

}
 	
 	public void cancelHn(String docNo)throws Exception {
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	        Class.forName(c.S_MYSQL_DRIVER);
	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	        conn.setAutoCommit(false);
	        
	        Statement stmt = conn.createStatement();
	    	ResultSet rs = null;
	    	
	
	try {
//		

			String update ="UPDATE sendsterile SET sendsterile.IsHn = 0  WHERE sendsterile.DocNo = '"+docNo+"' ";
			stmt.executeUpdate(update);
			
			String delete ="DELETE FROM hncode WHERE hncode.DocNo_SS =  '"+docNo+"'  ";
			stmt.executeUpdate(delete);
			
		}catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("ERROR onSaveHn : " + e.getMessage());
			System.out.println("ERROR onSaveHn : " + e.getMessage());
		}finally {
			onDisplayDocument(null);
		}

}

 	
 	
 	public String generateDoc() throws Exception{
 		
 		
 	    try {
 			com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
 	        Class.forName(c.S_MYSQL_DRIVER);
 	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
 	        conn.setAutoCommit(false);
 	        
 	        Statement stmt = conn.createStatement();
 	    	ResultSet rs = null;
 	    	
 		        String sql = "SELECT CONCAT('HN',SUBSTRING(YEAR(DATE(NOW())),3,4),LPAD(MONTH(DATE(NOW())),2,0),'-', LPAD( (COALESCE(MAX(CONVERT(SUBSTRING(DocNo,8,5),UNSIGNED INTEGER)),0)+1) ,5,0)) AS DocNo " + 
 						"        FROM hncode " + 
 						"        WHERE DocNo Like CONCAT('HN',SUBSTRING(YEAR(DATE(NOW())),3,4),LPAD(MONTH(DATE(NOW())),2,0),'%') " + 
 						"        ORDER BY DocNo DESC LIMIT 1";
 		      
 		  rs = stmt.executeQuery(sql);
 		  if(rs.next()) {
 			 DocnoHn = rs.getString("DocNo");
 		  }
 		  
 		  ;
 		  System.out.println(sql);
 		
 		  
 		  
 	}catch (Exception e) {
 		e.printStackTrace();
 		Messagebox.show("ERROR generateDoc : " + e.getMessage());
 		System.out.println("ERROR generateDoc : " + e.getMessage());
 	}
 	    return DocnoHn;
 	}
 	
 	
	// ================================================================================
	// Drop-down
	// ================================================================================
	
	private void defineReSterileType() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			List<ModelMaster> list = new ArrayList<>();
			
			rs = stmt.executeQuery(getSqlReSterileType());
				
			while(rs.next()){
				list.add(
							new ModelMaster(
								rs.getString("ID"),
								rs.getString("ID"),
								rs.getString("ResterileType"),
								false
							)
						);
			}
			
			Model_ResterileType = list;
			
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
	
	private String getSqlReSterileType() {
		
		String S_Sql = "";
		
		S_Sql =
				"	SELECT 		resteriletype.ID, "
			+	"				resteriletype.ResterileType " 
						
			+	"	FROM 		resteriletype "
			
			+	"	WHERE 		IsCancel = 0 "
			
			+	"	ORDER BY 	resteriletype.ResterileType ASC ";
		
		return S_Sql;
	}
	
	// ================================================================================
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
			
			rs = stmt.executeQuery("SELECT SS_IsFindStatus , SS_IsTag , SS_IsHn  FROM configuration_web LIMIT 1");

			if(rs.next()){
				SS_IsFindStatus = rs.getInt("SS_IsFindStatus");
				SS_IsTag = rs.getInt("SS_IsTag");
				SS_IsHn = rs.getInt("SS_IsHn");
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
	
	private void message(String S_Text){
		Messagebox.show(S_Text , "CSSD", Messagebox.OK, Messagebox.INFORMATION, new EventListener<Event>() {
    		public void onEvent(Event evt) throws Exception {
    			switch (((Integer) evt.getData()).intValue()) {
                  	case Messagebox.OK:
                  		break;
    			}
    		}
    	});
	}
}


