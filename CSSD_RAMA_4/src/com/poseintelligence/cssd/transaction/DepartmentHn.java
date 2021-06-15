package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.zkoss.zhtml.Center;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import com.poseintelligence.cssd.document.Payout;
import com.poseintelligence.cssd.utillity.DateTime;

public class DepartmentHn extends GenericForwardComposer{
	
	private boolean B_IsCreate = true;
	private Combobox Combobox_Department;
	private Combobox Combobox_Status;
	private Combobox ComboboxNameHN;
	private Combobox ComboboxNameHN_Add;
	private Combobox Combobox_PreName;
	
	private Checkbox  chk_add;
	
	private Listbox  Listbox_HistoryDetail;
	private Listbox  Listbox_HistoryDetail2;
	private Listbox  Listboxhncode;
	
	private Tabbox Hn_patient;

	private Textbox  TextboxBirthDayHn;
	private Textbox  TextboxDocNo;
	private Textbox  TextboxDocDate;
	private Textbox  TextboxNameHN_Add;
	private Textbox  TextboxName_Add;
	private Textbox  TextboxBirthDayHn2;
	private Textbox TextboxOld_Add;
	private Textbox TextboxQRcode;
	
	private Button   Button_SearchDoc;
	private Button   Button_SearchDoc2;
	private Button   Button_AddHn;
	private Button   Button_onSave;
	
	private Datebox Datebox_SDocDate;
	private Datebox Datebox_EDocDate;	
	private Datebox Datebox_SDocDate2;
	private Datebox Datebox_EDocDate2;	
	private Datebox DateboxBirthDay_Add;
	
	private Hlayout hlayout_prename;
	private Hlayout hlayout_prename2;	
	private Hlayout hlayout_name;
	private Hlayout hlayout_name2;	
	private Hlayout hlayout_birthdayadd;
	private Hlayout hlayout_birthdayadd2;
	private Hlayout hlayout_old;
	private Hlayout hlayout_old2;
	private Hlayout hlayout_HN_new;
	private Hlayout hlayout_HN_old;
	private Hlayout hlayout_birthday;
	private Hlayout hlayout_birthday2;
	
	private String DocNo = null;
	private String xHn_Code = "";
	private String UsageCode = null;
	private String LastSterileDetailID = null;
	// set HNwindow 
	private Window HNwindow;
	
	private Borderlayout borderlayout_main;
	private Borderlayout borderlayout_sub;
	private Borderlayout borderlayout_sub2;
	private Borderlayout borderlayout_sub3;
	
	private North north_main;
	
	private West west_sub;
	private West west_sub2;
	
	private East east_sub;
	private East east_sub2;
	
	private South south_main;
	
	private org.zkoss.zul.Center center_sub;
	private org.zkoss.zul.Center center_main;
	private org.zkoss.zul.Center center_sub2;
	private org.zkoss.zul.Center center_sub3;
	
	private Hlayout hlayout_sub1;
	private Hlayout hlayout_sub2;
	private Hlayout hlayout_sub3;
	private Hlayout hlayout_sub4;
	private Hlayout hlayout_sub5;
	private Hlayout hlayout_sub6;
	private Hlayout hlayout_sub7;
	private Hlayout hlayout_sub8;
	private Hlayout hlayout_sub9;
	private Hlayout hlayout_sub10;
	private Hlayout hlayout_sub11;
	private Hlayout hlayout_sub12;
	private Hlayout hlayout_sub13;
	private Hlayout hlayout_sub14;
	private Hlayout hlayout_sub15;
	private Hlayout hlayout_sub16;
	private Hlayout hlayout_sub17;
	private Hlayout hlayout_sub18;
	private Hlayout hlayout_sub19;
	private Hlayout hlayout_sub20;
	
	private Vlayout vlayout_westsub;
	private Vlayout vlayout_center_sub;
	private Vlayout vlayout_westsub2;
	private Vlayout vlayout_eastsub2;
	private Vlayout vlayout_centersub2;
	private Vlayout vlayout_centersub3;
	
	private Vbox vbox_vlayout_center_sub;
	private Vbox vbox_vlayout_westsub;
	private Vbox vbox_vlayout_westsub2;
	private Vbox vbox_vlayout_eastsub2;
	private Vbox vbox_vlayout_centersub2;
	
	private Label HnCode_w_n;
	private Label Fname_w_n;
	private Label Date_w_n;
	private Label Date_c_n;
	private Label FItem_c_n;
	private Label Round_c_n;
	private Label sTime_c_n;
	private Label eTime_c_n;
	private Label Date_w_c;
	private Label FItem_w_c;
	private Label Usage_w_c;
	private Label Ready_e_c;
	private Label Check_e_c;
	private Label Date_c_s;
	private Label FItem_c_s;
	private Label Round_c_s;
	private Label sTime_c_s;
	private Label eTime_c_s;
	private Label Approve_c_n;
	private Label Approve_c_n2;
	private Label Approve_c_c;
	private Label Approve_c_c2;
	private Button btn_report;
	
	
	// close set HNwindow
	
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
		
		init_HnWindow();
		
		Datebox_SDocDate.setText(DateTime.getDateNow());
		Datebox_EDocDate.setText(DateTime.getDateNow());
		
		Datebox_SDocDate2.setText(DateTime.getDateNow());
		Datebox_EDocDate2.setText(DateTime.getDateNow());
		Combobox_PreName.setSelectedIndex(0);
		
		onCheckAdd();
		
		
	}
	
	public void init_HnWindow() {
		
		
		borderlayout_main = (Borderlayout) HNwindow.getChildren().get(0);
		north_main = (North)  borderlayout_main.getChildren().get(0);
		borderlayout_sub = (Borderlayout) north_main.getChildren().get(0);
		
		// west north_sub
		west_sub = (West) borderlayout_sub.getChildren().get(0);
		vlayout_westsub = (Vlayout) west_sub.getChildren().get(0);
		vbox_vlayout_westsub = (Vbox) vlayout_westsub.getChildren().get(1);
		hlayout_sub1 = (Hlayout) vbox_vlayout_westsub.getChildren().get(2);
		hlayout_sub2 = (Hlayout) vbox_vlayout_westsub.getChildren().get(3);
		hlayout_sub3 = (Hlayout) vbox_vlayout_westsub.getChildren().get(4);
		HnCode_w_n = (Label) hlayout_sub1.getChildren().get(1);
		Fname_w_n  = (Label) hlayout_sub2.getChildren().get(1);
		Date_w_n  = (Label) hlayout_sub3.getChildren().get(1);
		
		
		// center_sub
		
		center_sub =  (org.zkoss.zul.Center)  borderlayout_sub.getChildren().get(1);
		vlayout_center_sub = (Vlayout) center_sub.getChildren().get(0);
		vbox_vlayout_center_sub = (Vbox) vlayout_center_sub.getChildren().get(0);
		hlayout_sub4 = (Hlayout) vbox_vlayout_center_sub.getChildren().get(0);
		hlayout_sub5 = (Hlayout) vbox_vlayout_center_sub.getChildren().get(1);
		hlayout_sub6 = (Hlayout) vbox_vlayout_center_sub.getChildren().get(2);
		hlayout_sub7 = (Hlayout) vbox_vlayout_center_sub.getChildren().get(3);
		hlayout_sub8 = (Hlayout) vbox_vlayout_center_sub.getChildren().get(4);
		hlayout_sub20 = (Hlayout) ((Hlayout) vlayout_center_sub.getChildren().get(1)).getChildren().get(0);
		
		
		Date_c_n =  (Label) hlayout_sub4.getChildren().get(1);
		FItem_c_n =  (Label) hlayout_sub5.getChildren().get(1);
		Round_c_n =  (Label) hlayout_sub6.getChildren().get(1);
		sTime_c_n =  (Label) hlayout_sub7.getChildren().get(1);
		eTime_c_n =  (Label) hlayout_sub8.getChildren().get(1);
		Approve_c_n =  (Label) hlayout_sub20.getChildren().get(1);
		Approve_c_n2  =  (Label) hlayout_sub20.getChildren().get(4);
		
		// east
		east_sub = (East) borderlayout_sub.getChildren().get(2);
		btn_report = (Button)  ( (Div)east_sub.getChildren().get(0)).getChildren().get(0);
		
		//center Main
		center_main = (org.zkoss.zul.Center)  borderlayout_main.getChildren().get(1);
		borderlayout_sub2 = (Borderlayout) center_main.getChildren().get(0);
		// center main west_sub2
		west_sub2 = (West)  borderlayout_sub2.getChildren().get(0);
		vlayout_westsub2 = (Vlayout) west_sub2.getChildren().get(0);
		vbox_vlayout_westsub2 =  (Vbox) vlayout_westsub2.getChildren().get(1);
		hlayout_sub9 = (Hlayout) vbox_vlayout_westsub2.getChildren().get(2);
		hlayout_sub10 = (Hlayout) vbox_vlayout_westsub2.getChildren().get(3);
		hlayout_sub11 = (Hlayout) vbox_vlayout_westsub2.getChildren().get(4);
		Date_w_c =  (Label) hlayout_sub9.getChildren().get(1);
		FItem_w_c  =  (Label) hlayout_sub10.getChildren().get(1);
		Usage_w_c  =  (Label) hlayout_sub11.getChildren().get(1);
		
		// center main center_sub3
		
		center_sub3 = (org.zkoss.zul.Center)  borderlayout_sub2.getChildren().get(1);
		vlayout_centersub3   = (Vlayout) center_sub3.getChildren().get(0);
		hlayout_sub19 = (Hlayout) vlayout_centersub3.getChildren().get(1);
		Approve_c_c =  (Label) hlayout_sub19.getChildren().get(1);
		Approve_c_c2 =  (Label) hlayout_sub19.getChildren().get(4);
		
		// center main east_sub2
		east_sub2 = (East) borderlayout_sub2.getChildren().get(2);
		vlayout_eastsub2 = (Vlayout) east_sub2.getChildren().get(0);
		vbox_vlayout_eastsub2 =  (Vbox) vlayout_eastsub2.getChildren().get(1);
		hlayout_sub12 = (Hlayout) vbox_vlayout_eastsub2.getChildren().get(2);
		hlayout_sub13 = (Hlayout) vbox_vlayout_eastsub2.getChildren().get(3);
		Ready_e_c =  (Label) hlayout_sub12.getChildren().get(1);
		Check_e_c  =  (Label) hlayout_sub13.getChildren().get(1);
		
		// south main
		
		south_main = (South) borderlayout_main.getChildren().get(2);
		borderlayout_sub3 = (Borderlayout) south_main.getChildren().get(0);
		center_sub2 = (org.zkoss.zul.Center) borderlayout_sub3.getChildren().get(1);
		vlayout_centersub2 = (Vlayout) center_sub2.getChildren().get(0);
		vbox_vlayout_centersub2 =  (Vbox) vlayout_centersub2.getChildren().get(0);
		hlayout_sub14 = (Hlayout) vbox_vlayout_centersub2.getChildren().get(0);
		hlayout_sub15 = (Hlayout) vbox_vlayout_centersub2.getChildren().get(1);
		hlayout_sub16 = (Hlayout) vbox_vlayout_centersub2.getChildren().get(2);
		hlayout_sub17 = (Hlayout) vbox_vlayout_centersub2.getChildren().get(3);
		hlayout_sub18 = (Hlayout) vbox_vlayout_centersub2.getChildren().get(4);
		
		Date_c_s =  (Label) hlayout_sub14.getChildren().get(1);
		FItem_c_s =  (Label) hlayout_sub15.getChildren().get(1);
		Round_c_s =  (Label) hlayout_sub16.getChildren().get(1);
		sTime_c_s =  (Label) hlayout_sub17.getChildren().get(1);
		eTime_c_s =  (Label) hlayout_sub18.getChildren().get(1);
		
	}
	
	
	public void onClick$Button_SearchDoc(Event event) throws Exception {
		
		onDisplayHistoryDetail(null);
	}
	
	public void onClick$Button_SearchDoc2(Event event) throws Exception {
		
		onDisplayHistoryDetail2();
	}
	
	public void onClick$Button_AddHn(Event event) throws Exception {
		
		onUpdate();
	}
	
	public void onClick$chk_add(Event event) throws Exception {
		
		onCheckAdd();
	}
	
	public void onClick$Button_onSave(Event event) throws Exception {
		
		oncheck_save();
	}
	
	public void onFocus$ComboboxNameHN(Event event) throws Exception {
		
		ComboboxNameHN.select();
	}

	
	public void onChanging$ComboboxNameHN(InputEvent event2) throws Exception {
		
		defindName(event2.getValue(),ComboboxNameHN);
	}

	public void onBlur$ComboboxNameHN(Event event) throws Exception {
		
		onNameTH(ComboboxNameHN.getText(),ComboboxNameHN,TextboxBirthDayHn);
	}
	
	public void onFocus$ComboboxNameHN_Add(Event event) throws Exception {
		
		ComboboxNameHN_Add.select();
	}
	public void onBlur$DateboxBirthDay_Add(Event event) throws Exception {
		
		checkage();
	}
	
	public void onChange$DateboxBirthDay_Add(Event event) throws Exception {
		
		checkage();
	}
	
	public void onChanging$ComboboxNameHN_Add(InputEvent event2) throws Exception {
		
		defindName(event2.getValue(),ComboboxNameHN_Add);
	}

	public void onBlur$ComboboxNameHN_Add(Event event) throws Exception {
		
		onNameTH(ComboboxNameHN_Add.getText(),ComboboxNameHN_Add,TextboxBirthDayHn2);
	}
	

	
//	checkAdd  ==================================================================================
	
	public void onCheckAdd() throws Exception{
		
		
		
		if(chk_add.isChecked()==true) {
			
			hlayout_birthday.setVisible(false);
			hlayout_birthday2.setVisible(false);
			
			hlayout_prename.setVisible(true);
			hlayout_prename2.setVisible(true);
			
			hlayout_name.setVisible(true);
			hlayout_name2.setVisible(true);
			
			hlayout_birthdayadd.setVisible(true);
			hlayout_birthdayadd2.setVisible(true);
			
			hlayout_old.setVisible(true);
			hlayout_old2.setVisible(true);
			
			hlayout_HN_new.setVisible(true);
			hlayout_HN_old.setVisible(false);
		}else {
			
			hlayout_birthday.setVisible(true);
			hlayout_birthday2.setVisible(true);
			
			hlayout_prename.setVisible(false);
			hlayout_prename2.setVisible(false);
			
			hlayout_name.setVisible(false);
			hlayout_name2.setVisible(false);
			
			hlayout_birthdayadd.setVisible(false);
			hlayout_birthdayadd2.setVisible(false);
			
			hlayout_old.setVisible(false);
			hlayout_old2.setVisible(false);
			
			
			hlayout_HN_new.setVisible(false);
			hlayout_HN_old.setVisible(true);
		}
			
	}

	
	
	
	// sql
	public String getSqlhn(String xSearch) {
			
			String Sql="SELECT " + 
					"                      hotpitalnumber.HnCode,  " + 
					"                      hotpitalnumber.Id , " + 
					"                      hotpitalnumber.FName, " + 
					"                      DATE_FORMAT(BirthDay ,'%d-%m-%Y') AS BirthDay " + 
					"                    FROM  hotpitalnumber";
					if(!xSearch.equals("")) {
					Sql += "  WHERE ( (hotpitalnumber.HnCode LIKE  '%"+xSearch.replace(" ", "%")+"%') "+
					"	OR (hotpitalnumber.FName LIKE  '%"+xSearch.replace(" ", "%")+"%') )  " ;
			 		}
					
			
			System.out.println("getSqlTab1 : " + Sql);
			return Sql;
	
	}
	
	public String getSqlHistoryDetail(String DocNo) {
		
		String Sql="SELECT " + 
				"			hncode.DocNo, " +
				"            hotpitalnumber.HnCode, " + 
				"            hotpitalnumber.FName, " + 
				"            itemstock.UsageCode, " + 
				"            DATE_FORMAT(hotpitalnumber.BirthDay ,'%d-%m-%Y') AS BirthDay, " + 

				"            hncode_detail.LastSterileDetailID, " + 
				"            item.itemname, " + 
				"            DATE_FORMAT( hncode.DocDate, '%d/%m/%Y' ) AS DocDate, " + 
				"            hotpitalnumber.HnAge, " + 
				"            hotpitalnumber.HnMonth, " + 
				"            PERIOD_DIFF( " + 
				"            DATE_FORMAT( NOW(), '%Y%m' ), " + 
				"            DATE_FORMAT( hotpitalnumber.CreateDate, '%Y%m' )) AS DiffMonth  " + 
				"          	 FROM hncode " + 
				"            INNER JOIN hncode_detail ON hncode.DocNo = hncode_detail.DocNo " + 
				"            INNER JOIN itemstock ON hncode_detail.ItemStockID = itemstock.RowID " + 
			

				"            INNER JOIN item ON itemstock.ItemCode = item.itemcode " + 
				"            INNER JOIN hotpitalnumber ON hncode.HnCode = hotpitalnumber.HnCode  ";
				
				if(DocNo == null) {
					Sql += " WHERE hncode.HnCode ='"+xHn_Code+"'";
					Sql += " AND DocDate BETWEEN '" + DateTime.convertDate(Datebox_SDocDate.getText()) + "' AND '" + DateTime.convertDate(Datebox_EDocDate.getText()) + "'  " ;
				}else {
					Sql += " WHERE hncode.DocNo ='"+DocNo+"'";
				}
				Sql += "ORDER BY hncode.DocDate DESC";
				
				System.out.println(Sql);
				return Sql;
		
		
	}
	
	
	public String getSqlHistoryDetail2() {
		
		 String Sql = "SELECT " + 
			 		"            hotpitalnumber.HnCode, " + 
					"			 hncode.DocNo,	"+
			 		"            hotpitalnumber.FName, " + 
			 		"            itemstock.UsageCode, " + 
			 		"            hncode_detail.LastSterileDetailID, " + 
			 		"            item.itemname, " + 
			 		"            DATE_FORMAT( hncode.DocDate, '%d/%m/%Y' ) AS DocDate, " + 
			 		"            hotpitalnumber.HnAge, " + 
			 		"            hotpitalnumber.HnMonth, " + 
			 		"            PERIOD_DIFF( " + 
			 		"              DATE_FORMAT( NOW(), '%Y%m' ), " + 
			 		"            DATE_FORMAT( hotpitalnumber.CreateDate, '%Y%m' )) AS DiffMonth  " + 
			 		"          FROM " + 
			 		"            hncode " + 
			 		"            INNER JOIN hncode_detail ON hncode.DocNo = hncode_detail.DocNo " + 
			 		"            INNER JOIN itemstock ON hncode_detail.ItemStockID = itemstock.RowID " + 
			 		"            INNER JOIN item ON itemstock.ItemCode = item.itemcode " + 
			 		"            INNER JOIN hotpitalnumber ON hncode.HnCode = hotpitalnumber.HnCode  " + 
			 		"          WHERE " + 
			 		"             hncode.IsCancel = 0  " + 
			 		"            AND hncode.DeptID = "+S_DeptId+"  " + 
			 		
			 		"            AND DocDate BETWEEN '" + DateTime.convertDate(Datebox_SDocDate2.getText()) + "' AND '" + DateTime.convertDate(Datebox_EDocDate2.getText()) + "'  " + 
			 		"			GROUP BY hncode.DocNo "	+
			 		"          ORDER BY  hncode.DocDate DESC ";			
		 System.out.println(Sql);
				return Sql;
		
		
	}
	
	public String getSqlWestNorth() {
		
		String Sql="SELECT " + 
				"	hotpitalnumber.HnCode, " + 
				"	hotpitalnumber.FName, " + 
				"	itemstock.UsageCode, " + 
				"	hncode_detail.LastSterileDetailID, " + 
				"	item.itemname, " + 
				"	DATE_FORMAT(hncode.DocDate, '%d/%m/%Y') AS DocDate, " + 
				"	hotpitalnumber.HnAge, " + 
				"	hotpitalnumber.HnMonth, " + 
				"	PERIOD_DIFF( " + 
				"		DATE_FORMAT(NOW(), '%Y%m'), " + 
				"		DATE_FORMAT( " + 
				"			hotpitalnumber.CreateDate, " + 
				"			'%Y%m' " + 
				"		) " + 
				"	) AS DiffMonth " + 
				"FROM " + 
				"	hncode " + 
				"INNER JOIN hncode_detail ON hncode.DocNo = hncode_detail.DocNo " + 
				"INNER JOIN itemstock ON hncode_detail.ItemStockID = itemstock.RowID " + 
				"INNER JOIN item ON itemstock.ItemCode = item.itemcode " + 
				"INNER JOIN hotpitalnumber ON hncode.HnCode = hotpitalnumber.HnCode " + 
				"WHERE " + 
				"	hncode.IsCancel = 0 " +
				"AND hncode.HnCode = '"+xHn_Code+"' " + 
				"ORDER BY " + 
				"	hncode.DocDate DESC " + 
				"LIMIT 1  ";
		
//		System.out.println("getSqlWestNorth : " + Sql);
		return Sql;
		
	}
	
	
	public String getSqlCenterNorth() {
		
	String Sql="SELECT " + 
			"            DATE_FORMAT( NOW(), '%d-%m-%Y' ) AS ddate, " + 
			"            item.itemname, " + 
			"			 steriledetail.ID, "+
//			"            hncode_detail.LastSterileDetailID, " + 
			"            itemstock.UsageCode, " + 
			"            DATE_FORMAT( wash.DocDate, '%d-%m-%Y' ) AS washdate, " + 
			"            washmachine.MachineName AS WashMachineName, " + 
			"            wash.WashRoundNumber AS WashRoundNumber, " + 
			"            TIME( wash.StartTime ) AS timeSdatew, " + 
			"            TIME( wash.FinishTime ) AS timeEdatew, " + 
			"            CONCAT( emp1.FirstName, ' ', emp1.LastName ) AS washBeforeApprovename, " + 
			"            CONCAT( emp2.FirstName, ' ', emp2.LastName ) AS washAfterApprovename, " + 
			"            DATE_FORMAT( sterile.DocDate, '%d-%m-%Y' ) AS steriledate, " + 
			"            sterilemachine.MachineName2 AS SterileMachineName, " + 
			"            sterile.SterileRoundNumber AS SterileRoundNumber, " + 
			"            TIME( sterile.StartTime ) AS timeSdates, " + 
			"            TIME( sterile.FinishTime ) AS timeEdates, " + 
			"            CONCAT( emp3.FirstName, ' ', emp3.LastName ) AS ppsname, " + 
			"            CONCAT( emp4.FirstName, ' ', emp4.LastName ) AS appsname, " + 
			"            CONCAT( emp5.FirstName, ' ', emp5.LastName ) AS sterilesname, " + 
			"            CONCAT( emp6.FirstName, ' ', emp6.LastName ) AS sterileBeforeApprovename, " + 
			"            CONCAT( emp7.FirstName, ' ', emp7.LastName ) AS sterileAfterApprovename  " + 
			"          FROM " + 
			"            itemstock " + 
			"            LEFT JOIN item ON itemstock.ItemCode = item.itemcode " + 
			"            LEFT JOIN washdetail ON itemstock.RowID = washdetail.ItemStockID " + 
//			"			 INNER JOIN hncode_detail ON itemstock.LastSterileDetailID = hncode_detail.LastSterileDetailID "+
			"            LEFT JOIN wash ON washdetail.WashDocNo = wash.DocNo " + 
			"            LEFT JOIN washmachine ON wash.WashMachineID = washmachine.ID " + 
			"            LEFT JOIN employee AS emp1 ON wash.BeforeApprove = emp1.EmpCode " + 
			"            LEFT JOIN employee AS emp2 ON wash.AfterApprove = emp2.EmpCode " + 
			"            LEFT JOIN steriledetail ON itemstock.RowID = steriledetail.ItemStockID " + 
			"            LEFT JOIN sterile ON steriledetail.DocNo = sterile.DocNo " + 
			"            LEFT JOIN sterilemachine ON sterile.SterileMachineID = sterilemachine.ID " + 
			"            LEFT JOIN employee AS emp3 ON sterile.PrepareCode = emp3.ID " + 
			"            LEFT JOIN employee AS emp4 ON sterile.ApproveCode = emp4.ID " + 
			"            LEFT JOIN employee AS emp5 ON sterile.SterileCode = emp5.ID " + 
			"            LEFT JOIN employee AS emp6 ON sterile.BeforeApprove = emp6.EmpCode " + 
			"            LEFT JOIN employee AS emp7 ON sterile.AfterApprove = emp7.EmpCode  " + 
			"          WHERE " + 
			"            itemstock.UsageCode = '"+UsageCode+"'  " + 
			"            AND steriledetail.ID = '"+LastSterileDetailID+"' " + 
			"          ORDER BY " + 
			"            wash.DocDate DESC, " + 
			"            sterile.DocDate DESC  " + 
			"            LIMIT 1 ";
		
		return Sql;
		
	}
	
	
	public String getSqlCreateDocument(String DocNo) {
		
		String Sql = "SELECT " + 
		 		"            hotpitalnumber.HnCode, " + 
				"			hncode.DocNo,	"+
		 		"            hotpitalnumber.FName, " + 
		 		"            hncode_detail.ID, " + 
		 		"            itemstock.UsageCode, " + 
		 		"            hncode_detail.LastSterileDetailID, " + 
		 		"            item.itemname, " + 
		 		"            DATE_FORMAT( hncode.DocDate, '%d/%m/%Y' ) AS DocDate, " + 
		 		"            hotpitalnumber.HnAge, " + 
		 		"            hotpitalnumber.HnMonth, " + 
		 		"            PERIOD_DIFF( " + 
		 		"              DATE_FORMAT( NOW(), '%Y%m' ), " + 
		 		"            DATE_FORMAT( hotpitalnumber.CreateDate, '%Y%m' )) AS DiffMonth  " + 
		 		"          FROM " + 
		 		"            hncode " + 
		 		"            INNER JOIN hncode_detail ON hncode.DocNo = hncode_detail.DocNo " + 
		 		"            INNER JOIN itemstock ON hncode_detail.ItemStockID = itemstock.RowID " + 
		 		"            INNER JOIN item ON itemstock.ItemCode = item.itemcode " + 
		 		"            INNER JOIN hotpitalnumber ON hncode.HnCode = hotpitalnumber.HnCode  " + 
		 		"          WHERE " + 
		 		"             hncode.IsCancel = 0  " + 
		 		"            AND hncode.DocNo = '"+DocNo+"'  " + 
		 		"            AND hncode.DeptID = "+S_DeptId+"  " + 
		 		"          ORDER BY  hncode.DocDate DESC ";
//		 System.out.println(Sql);
				return Sql;
		
		
	}
	
	// defindName HN
	public void defindName(String xSearch,Combobox cbb) throws Exception {
		String HnCode = null;
//		System.out.println("xSearch : "+xSearch);
		
		if (xSearch != null) {
			if (xSearch.length() >= 1) {
				cbb.setOpen(false);
				cbb.getItems().clear();

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
						

						cbb.appendChild(citem);
					}

					if (cbb.getItemCount() > 0) {
						cbb.setOpen(true);
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
	
	
 	private void onNameTH(String xSearch,Combobox cbb,Textbox BirthDay) throws Exception {
		int i = (Integer) xSearch.trim().indexOf(':');

		if (i < 0)
			return;

		cbb.getItems().clear();
		
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
		System.out.println("cbb : " + xSearch);

		try {
			rs = stmt.executeQuery(getSqlhn(xSearch.trim().substring(0, i).replace(" ", "%")));

			if (rs.next()) {
				xHn_Code = rs.getString("HnCode");
				cbb.setText(rs.getString("FName")+ " : " + rs.getString("HnCode"));
				BirthDay.setText(rs.getString("BirthDay"));
			}	
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	
 	// ondisplayHistoryDetail
 	
 	public void onDisplayHistoryDetail(String DocNo) throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(getSqlHistoryDetail(DocNo));
				
			int I_No = 1;
			
			Listbox_HistoryDetail.getItems().clear();
			
			String FName = null;
			String HnCode = null;
			String BirthDay = null;
			while(rs.next()){
				FName = rs.getString("FName");
				xHn_Code = rs.getString("HnCode");
				HnCode = rs.getString("HnCode");
				BirthDay = rs.getString("BirthDay");
				UsageCode = rs.getString("UsageCode");
				LastSterileDetailID = rs.getString("LastSterileDetailID");
				Listitem list = new Listitem();

				list.appendChild(new Listcell(I_No + "."));
				list.appendChild(new Listcell(rs.getString("DocDate")));
				list.appendChild(new Listcell(rs.getString("UsageCode")));
			
				
				Listcell lc_ItemName= new Listcell();
				A ItemName = new A(rs.getString("itemname"));
				
				ItemName.addEventListener("onClick", new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						
						try {
								openHnWiwdon();
								onDisplayWestNorth();
								onDisplayCenterNorth();
					        
						}catch (Exception e) {
							e.printStackTrace();
						}
			        }
			    });
				
				lc_ItemName.appendChild( ItemName );
				list.appendChild( lc_ItemName );
			

				//Attribute
              
                
                Listbox_HistoryDetail.appendChild(list);
      
                I_No++;

			}
			ComboboxNameHN.setText(FName+ " : " + HnCode);
			TextboxBirthDayHn.setText(BirthDay);
			
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
 	
 	
 	public void onDisplayHistoryDetail2() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(getSqlHistoryDetail2());
				
			int I_No = 1;
			
			Listbox_HistoryDetail2.getItems().clear();

			
			while(rs.next()){
				Listitem list = new Listitem();

				final String DocNo = rs.getString("DocNo");
				
				
				list.appendChild(new Listcell(I_No + "."));
				
				list.appendChild(new Listcell(rs.getString("DocNo")));
				list.appendChild(new Listcell(rs.getString("DocDate")));
				
				Listcell lc_HnCode= new Listcell();
				
				A L_HnCoe = new A(rs.getString("FName"));
				
				L_HnCoe.addEventListener("onClick", new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						Hn_patient.setSelectedIndex(0);
						onDisplayHistoryDetail(DocNo);
						
				    	
			        }
			    });
				
				lc_HnCode.appendChild( L_HnCoe );
				list.appendChild( lc_HnCode );

				//Attribute
              
                
				Listbox_HistoryDetail2.appendChild(list);
      
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
 	
 	// ondisplay HnWindow
 	
 	public void onDisplayWestNorth() throws Exception {
 		
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(getSqlWestNorth());
				
			if(rs.next()) {
				
				
				HnCode_w_n.setValue(rs.getString("HnCode"));
				Fname_w_n.setValue(rs.getString("FName"));
				Date_w_n.setValue(rs.getString("DocDate"));
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
 	
 	public void onDisplayCenterNorth() throws Exception {
 		
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(getSqlCenterNorth());
				
			if(rs.next()) {
		
					
				
				Date_c_n.setValue(rs.getString("washdate"));
				FItem_c_n.setValue(rs.getString("WashMachineName"));
				Round_c_n.setValue(rs.getString("WashRoundNumber"));
				sTime_c_n.setValue(rs.getString("timeSdatew"));
				eTime_c_n.setValue(rs.getString("timeEdatew"));
				
				Approve_c_n.setValue("DDDDDDDDDD");
				Approve_c_n2.setValue("FFFFFFFFFFFFFFFF");
				
				 
				 Date_w_c.setValue(rs.getString("ddate"));
				 FItem_w_c.setValue(rs.getString("itemname"));
				 Usage_w_c.setValue(rs.getString("UsageCode"));


				 Approve_c_c.setValue("XXXXXXXXXXXXXXX");
				 Approve_c_c2.setValue("AAAAAAAAAa");
				 
				 Ready_e_c.setValue(rs.getString("ppsname"));
				 Check_e_c.setValue(rs.getString("appsname"));
				 
				 Date_c_s.setValue(rs.getString("steriledate"));
				 FItem_c_s.setValue(rs.getString("SterileMachineName"));
				 Round_c_s.setValue(rs.getString("SterileRoundNumber"));
				 sTime_c_s.setValue(rs.getString("timeSdates")+" น.");
				 eTime_c_s.setValue(rs.getString("timeEdates")+" น.");
				 
				 
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
 	
 	public void oncheck_save() throws Exception {
 		
 		if(!chk_add.isChecked()) {	

 			onSaveHn();
 		}else {
 			onSaveNewEmp();
 		}
 	}
 	
 	public void onSaveHn() throws Exception {
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	        Class.forName(c.S_MYSQL_DRIVER);
	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	        conn.setAutoCommit(false);
	        
	        Statement stmt = conn.createStatement();
	    	ResultSet rs = null;
 		try {
 		
 		String	DocNo =	TextboxDocNo.getText();

 		if(DocNo.equals("")) {
 			DocNo = generateDoc();
 			TextboxDocNo.setText(DocNo);
 			
 			TextboxDocDate.setText(DateTime.getDateNow());
 			System.out.println("gen Doc = "+DocNo);
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
 					"              hncode.B_ID " + 
 					"            	)VALUES( " + 
 					"              '"+DocNo+"', " + 
 					"              DATE(NOW()), " + 
 					"              '"+xHn_Code+"', " + 
 					"              NOW(), " + 
 					"              '"+S_UserId+"', " + 
 					"              '"+S_DeptId+"', " + 
 					"              0, " + 
 					"              '', " + 
 					"              0, " + 
 					"              '', " + 
 					"              0, " + 
 					"              '1' " + 
 					"            ) ";
 			stmt.executeUpdate(insert);
 			System.out.println("insert : "+insert);
 			
 			
 			onSaveHn_Detail(DocNo);
 			
 		}else {
 			
 			String update ="UPDATE hncode SET hncode.ModifyDate = NOW() , hncode.UserCode = '"+S_UserId+"', hncode.DeptID = '"+S_DeptId+"' WHERE DocNo = '"+DocNo+"' ";

 			stmt.executeUpdate(update);
 			
 			onSaveHn_Detail(DocNo);
 			
 		}
 		chk_add.setDisabled(true);
 		}catch (Exception e) {
 			e.printStackTrace();
 			Messagebox.show("ERROR onSaveHn : " + e.getMessage());
 			System.out.println("ERROR onSaveHn : " + e.getMessage());
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
 	
 	public String generateDoc() throws Exception{
 		
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	        Class.forName(c.S_MYSQL_DRIVER);
	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	        conn.setAutoCommit(false);
	        
	        Statement stmt = conn.createStatement();
	    	ResultSet rs = null;
 		
 	    try {
 	    	
 		        String sql = "SELECT CONCAT('HN',SUBSTRING(YEAR(DATE(NOW())),3,4),LPAD(MONTH(DATE(NOW())),2,0),'-', LPAD( (COALESCE(MAX(CONVERT(SUBSTRING(DocNo,8,5),UNSIGNED INTEGER)),0)+1) ,5,0)) AS DocNo " + 
 						"        FROM hncode " + 
 						"        WHERE DocNo Like CONCAT('HN',SUBSTRING(YEAR(DATE(NOW())),3,4),LPAD(MONTH(DATE(NOW())),2,0),'%') " + 
 						"        ORDER BY DocNo DESC LIMIT 1";
 		      
 		  rs = stmt.executeQuery(sql);
 		  if(rs.next()) {
 			  DocNo = rs.getString("DocNo");
 		  }
 		  
 		  
 		  System.out.println(sql);
 		
 		  
 		  
	 	}catch (Exception e) {
	 		e.printStackTrace();
	 		Messagebox.show("ERROR generateDoc : " + e.getMessage());
	 		System.out.println("ERROR generateDoc : " + e.getMessage());
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
			
	 	    return DocNo;
 	}
 		
 	public void onSaveNewEmp() throws Exception {
 			
 			com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
 	        Class.forName(c.S_MYSQL_DRIVER);
 	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
 	        conn.setAutoCommit(false);
 	        
 	        Statement stmt = conn.createStatement();
 	    	ResultSet rs = null;
 		
 			try {
 				
 				String sqll="SELECT COUNT(*) AS cnt FROM hotpitalnumber WHERE HnCode ='"+TextboxNameHN_Add.getText()+"'";
 				int cnt =0;
 				rs= stmt.executeQuery(sqll);
 				if(rs.next()) {
 					cnt = rs.getInt("cnt");
 				}
 				
 				if(cnt>0) {
 					
 					String updatee = "UPDATE  hotpitalnumber SET CreateDate = NOW() WHERE HnCode ='"+TextboxNameHN_Add.getText()+"'";
 					stmt.executeUpdate(updatee);
// 					Messagebox.show("มีรายชื่อHNนี้อยู่แล้ว. \n รหัสHNเลขที่ : \n [ "+TextboxNameHN2.getText()+" ]" );
 				}else {
 				
 				String insert2 = "INSERT INTO hotpitalnumber( " + 
 						"              hotpitalnumber.FName, " + 
 						"              hotpitalnumber.CreateDate, " + 
 						"              hotpitalnumber.HnCode, " + 
 						"              hotpitalnumber.HnAge, " + 
 						"              hotpitalnumber.TitleName, " + 
 						"              hotpitalnumber.BrithDay " + 
 						"              )VALUES( " + 
 						"              '"+TextboxName_Add.getText()+"', " + 
 						"              NOW(), " + 
 						"              '"+TextboxNameHN_Add.getText()+"', " + 
 						"              '"+TextboxOld_Add.getText()+"', " + 
 						"              '"+Combobox_PreName.getText()+"', " + 
 						"              '"+DateTime.convertDate(DateboxBirthDay_Add.getText())+"' " + 
 						
 						"            ) ";
 				stmt.executeUpdate(insert2);
 				}
 			String	DocNo =	TextboxDocNo.getText();

 			
 			
	 			if(DocNo.equals("")) {
	 				DocNo = generateDoc();
	 				TextboxDocNo.setText(DocNo);
	 				TextboxDocDate.setText(DateTime.getDateNow());
	 				System.out.println("gen Doc = "+DocNo);
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
	 						"              hncode.B_ID " + 
	 						"            	)VALUES( " + 
	 						"              '"+DocNo+"', " + 
	 						"              DATE(NOW()), " + 
	 						"              '"+TextboxNameHN_Add.getText()+"', " + 
	 						"              NOW(), " + 
	 						"              '"+S_UserId+"', " + 
	 						"              '"+S_DeptId+"', " + 
	 						"              0, " + 
	 						"              '', " + 
	 						"              0, " + 
	 						"              '', " + 
	 						"              0, " + 
	 						"              '1' " + 
	 						"            ) ";
	 				stmt.executeUpdate(insert);
	 				
	 				onSaveHn_Detail(DocNo);
	 			}else {
	 				
	 				String update ="UPDATE hncode SET hncode.ModifyDate = DATE(NOW()) , hncode.UserCode = '"+S_UserId+"', hncode.DeptID = '"+S_DeptId+"' WHERE DocNo = '"+DocNo+"' ";
	 				
	 				stmt.executeUpdate(update);
	 				onSaveHn_Detail(DocNo);
//	 				onDisplay();
	 			}
	 			chk_add.setDisabled(true);
 			}catch (Exception e) {
 				e.printStackTrace();
 				Messagebox.show("ERROR onSaveHn : " + e.getMessage());
 				System.out.println("ERROR onSaveHn : " + e.getMessage());
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
 		
 	public void onSaveHn_Detail(String DocNo) throws Exception {
 			
 			com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
 	        Class.forName(c.S_MYSQL_DRIVER);
 	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
 	        conn.setAutoCommit(false);
 	        
 	        Statement stmt = conn.createStatement();
 	    	ResultSet rs = null;
 	    	ResultSet rs2 = null;
 		try {
 				
 				if(TextboxQRcode.getText().equals("")) {
 					Messagebox.show("ไม่สามารถเพิ่มรายการได้	"," ", Messagebox.YES, Messagebox.INFORMATION);
 					
 					return;
 					}
 				
 				
 			String sql="select UsageCode,RowID,LastSterileDetailID  from itemstock WHERE IsStatus = '5' AND IsPay = '1' AND UsageCode='"+TextboxQRcode.getText()+"' ";
 			String RowID = null;
 			String LastSterileDetailID = null;
 			String UsageCode = null;
// 			String HnCode = null;

 			int cntHn = 0;
 			rs = stmt.executeQuery(sql);
 							System.out.println("DocNo= "+sql);	

 							
 						if(rs.next()) {
 							UsageCode = rs.getString("UsageCode");
 							RowID = rs.getString("RowID");
 							LastSterileDetailID = rs.getString("LastSterileDetailID");
// 							HnCode = rs2.getString("HnCode");
 				System.out.println("DocNo= "+DocNo);
 			
 				
 				
 				String sqll="SELECT COUNT(hncode_detail.ItemStockID) AS ItemStockID FROM hncode_detail INNER JOIN hncode ON hncode_detail.DocNo = hncode.DocNo "
 						+ "	WHERE hncode_detail.DocNo='"+DocNo+"' AND hncode_detail.ItemStockID ='"+RowID+"' ";
 				
// 				
 				System.out.println("cnt= ItemStockID "+sqll);	
 				rs2 = stmt.executeQuery(sqll);
 				
 				if(rs2.next()) {
 					cntHn = rs2.getInt("ItemStockID");
 					
 				}
 				System.out.println("cntHn= "+cntHn);
 				
 				
 				
 				if(cntHn>0) {
 					Messagebox.show("มีรายชื่ออุปกรณ์นี้อยู่แล้ว. \n รหัสใช้งานเลขที่ : \n [ "+UsageCode+" ]" ," ", Messagebox.YES, Messagebox.INFORMATION);
 					System.out.println("AAAAAAAAAAAA");
 				}else {
 				
 				

 				String insert2 = "INSERT INTO hncode_detail( " + 
 						"              hncode_detail.DocNo, " + 
 						"              hncode_detail.ItemStockID, " + 
 						"              hncode_detail.Qty, " + 
 						"              hncode_detail.IsStatus, " + 
 						"              hncode_detail.IsCancel, " + 
 						"              hncode_detail.B_ID, " + 
 						"              hncode_detail.LastSterileDetailID " + 
 						"            	)VALUES( " + 
 						"              '"+DocNo+"', " + 
 						"              '"+RowID+"', " + 
 						"               '1', " + 
 						"               '1', " + 
 						"              	'0', " + 
 						"              	'1', " + 
 						"               " +isNull(LastSterileDetailID) + " " + 
 						"            )  ";
 				stmt.executeUpdate(insert2);
 				System.out.println("SSSSSSSSSSSSSSsssss");
 				}

 				
 				
 							
 					}	
 				TextboxQRcode.setText("");		
 				CreateDocument(DocNo);

 						
 				
 		}catch (Exception e) {
 			e.printStackTrace();
 			Messagebox.show("ERROR onSaveHn_Detail : " + e.getMessage());
 			System.out.println("ERROR onSaveHn_Detail : " + e.getMessage());
 		}finally{
 			if (rs != null) {
 	               rs.close();
 	           }
 			
 			if (rs2 != null) {
	               rs2.close();
	           }
 	            
 	           if (stmt != null) {
 	                stmt.close();
 	           }
 	            
 	           if (conn != null) {
 	                conn.close();
 	           }
 	            
 			}
 	}
 	public void CreateDocument(String DocNo)throws Exception {
 		
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	        Class.forName(c.S_MYSQL_DRIVER);
	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	        conn.setAutoCommit(false);
	        
	        Statement stmt = conn.createStatement();
	    	ResultSet rs = null;
 		try {
 			
 			
 			rs = stmt.executeQuery(getSqlCreateDocument(DocNo));
 			Listboxhncode.getItems().clear();
 			int No = 1;
 			
 			while(rs.next()) {
 				
 				Listitem list = new Listitem();
 				
 				list.appendChild(new Listcell(No + "."));
 				list.appendChild(new Listcell(rs.getString("UsageCode")));
 				list.appendChild(new Listcell(rs.getString("itemname")));
 				list.appendChild(Delete(rs.getString("ID"),DocNo));
 				
 				list.setAttribute("TextboxDocNo", rs.getString("DocNo"));
 				Listboxhncode.appendChild(list);


 				No++;
 			}
 		}catch (Exception e) {
 			e.printStackTrace();
 			Messagebox.show("ERROR CreateDocument : " + e.getMessage());
 			System.out.println("ERROR CreateDocument : " + e.getMessage());
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
 	
 	public Listcell Delete(final String ID,final String DocNo)throws Exception {
 		
 		Listcell lc = new Listcell();
 		
 		Image imgdel = new Image();
 		imgdel.setHeight("25px");
 		imgdel.setSrc("/img/delete-24.png");
 		imgdel.setStyle("align:center;");
 		lc.setStyle("align:center; ");
 		imgdel.addEventListener("onClick", new EventListener<Event>() {
 			public void onEvent(Event event) throws Exception {
 				onDeleteItem(ID);
 				
 				CreateDocument(DocNo);
 				
 			}
 			
 				});
// 		System.out.println("newListcell " + sql);
 		
 		lc.appendChild(imgdel);
 		return lc;
 	}
 	
 	public void onDeleteItem(final String ID) throws Exception {
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
		try {
		stmt.executeUpdate("DELETE FROM hncode_detail WHERE ID = '"+ID+"' ");
		
		System.out.println("DELETE ID : " +ID);
		
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("ERROR Delete : " + e.getMessage());
			System.out.println("ERROR Delete : " + e.getMessage());
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
 	public void onUpdate() throws Exception {
 		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
 		try {
 		
 		String	DocNo =	TextboxDocNo.getText();
 		
 			if(DocNo.trim().equals("")) {
 				
 			}else {
 			
 			String update ="UPDATE hncode SET hncode.IsStatus = 1  WHERE DocNo = '"+DocNo+"' ";
 			
 			stmt.executeUpdate(update);
 			Messagebox.show("บันทึกสำเร็จ"," ", Messagebox.YES, Messagebox.INFORMATION);
 			onclr();
 			onDisplayHistoryDetail2();
 			}
 		}catch (Exception e) {
 			e.printStackTrace();
 			Messagebox.show("ERROR onSaveHn : " + e.getMessage());
 			System.out.println("ERROR onSaveHn : " + e.getMessage());
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
 	
 	public void onclr() {
 		
 		TextboxDocNo.setText("");
 		TextboxDocDate.setText("");
 		TextboxBirthDayHn2.setText("");
 		TextboxBirthDayHn.setText("");
 		TextboxName_Add.setText("");
 		TextboxQRcode.setText("");
 		TextboxOld_Add.setText("");
 		ComboboxNameHN_Add.setText("");
 		Listboxhncode.getItems().clear();
 		chk_add.setDisabled(false);
 	}
 	
 	public void checkage() {
 		
 		String year = DateTime.getYearNow();
 		String year_add =  DateboxBirthDay_Add.getText().substring(6,10);
 		int age ;
 		age = Integer.parseInt(year) - Integer.parseInt(year_add);
 		
 		TextboxOld_Add.setText(Integer.toString(age));
 		
 	}
 	
 	
 	public void openHnWiwdon() {
 		
// 		HNwindow.getChildren().clear();
 		HNwindow.setVisible(true);
 		HNwindow.setFocus(true);
 		HNwindow.setPosition("center");
 		HNwindow.setMode("modal");
 		HNwindow.setClosable(true);		
 	}
 	
 	private String isNull(String Value){
 		return Value == null ? null : ("'" + Value + "'");
 	}
}
