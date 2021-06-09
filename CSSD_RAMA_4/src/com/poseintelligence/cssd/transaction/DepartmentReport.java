package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.poseintelligence.cssd.core.Setting;

@SuppressWarnings("rawtypes")
public class DepartmentReport extends GenericForwardComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6637098633119469091L;
	
	// Widget
	private Window WindowForm;
	private Window WindowFormCondition;
	private Window WinProcess;
	
	private Iframe Iframe_Report;

	private Textbox TextboxName;
	private Datebox DateboxSDocDate;
	private Datebox DateboxEDocDate;
	
	private Combobox ComboboxDepartment;
	
	private Checkbox Checkbox_PrintType;
	
	private Label Label_DocDate;
	private Label Label_To;
	private Label Label_Department;
	private Label Label_Report;
	private Label Label_ReportName;
	
	// Var 
	
	private String S_ReportType = "";
	private String S_ReportUrl = "";
	
	// Variable Session
	private boolean B_IsCreate = true;
	private Session session = Sessions.getCurrent();
	
	private String MD_URL = "";
		
	private String 	S_UserId,
					S_DeptId,
					S_UserName,
					S_IsAdmin,
					S_EmpCode,
					S_DepName,
					S_DB;
	
	public void onCreate() throws Exception {
		
		bySession();
		
		byConfig();
		
		init();
		
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
		
		define(ComboboxDepartment, S_SqlDepartment, "ทุกแผนก");
		
    }
	
	public void byConfig() {
        // -----------------------------------------------------------------------------------------
        // Get Config
        // -----------------------------------------------------------------------------------------
        Setting s = new Setting(S_DB);
       
        MD_URL = s.getMD_URL();
        
        //System.out.println(MD_URL);
    }

	private void bySession(){
		if (B_IsCreate) {
			if (session.getAttribute("S_UserId") == null) {
				Executions.sendRedirect("/timeout.zul");
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
	
	private void init(){
		
		WindowFormCondition.setVisible(true);
		WindowFormCondition.setFocus(true);
		WindowFormCondition.setPosition("center");
		WindowFormCondition.setMode("modal");
		WindowFormCondition.setBorder("normal");
		WindowFormCondition.setClosable(true);		
		WindowFormCondition.setHeight("90%");
		WindowFormCondition.setWidth("700px");
		
		WindowFormCondition.setStyle("background: #2e5fbd;");
		
		DateboxSDocDate.setText(com.poseintelligence.cssd.utillity.DateTime.getDateNow());
		DateboxEDocDate.setText(com.poseintelligence.cssd.utillity.DateTime.getDateNow());
		
		Iframe_Report = (Iframe) ((Center)((Borderlayout)WindowForm.getChildren().get(0)).getChildren().get(0)).getChildren().get(0);
		
		String S_Label = Label_Report.getValue();
		String S_ReportName = "";
		
		// Report Name
		if(S_Label.equals("1")) {
			S_ReportName = "cssd_report_receiving_New_building.php";
			
			Label_Department.setVisible(false);
    		ComboboxDepartment.setVisible(false);
			
		}else if(S_Label.equals("2")) {
			
			S_ReportName = "cssd_report_payout_New_building.php";
			
		}else if(S_Label.equals("3")) {
			S_ReportName = "cssd_report_Sendsterili_Expire.php";
			Label_Department.setVisible(false);
    		ComboboxDepartment.setVisible(false);
    		
		}else if(S_Label.equals("4")) {
			S_ReportName = "cssd_report_sendsterile_by_department.php";
			Label_Department.setVisible(false);
    		ComboboxDepartment.setVisible(false);
    		
		} else {
			return;
		}
		
		TextboxName.setText(S_ReportName);
		Label_ReportName.setValue(S_ReportName);
	}
	
	private void define(Combobox cbb, String S_Sql, String S_DefualtValue) throws Exception{
		cbb.getItems().clear();
		cbb.setText("");
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
    	ResultSet rs = null;
        
		try{	
			
			rs = stmt.executeQuery(S_Sql);
			
			Comboitem citem_ = new Comboitem();
			citem_.setLabel(S_DefualtValue);
			citem_.setValue(null);
			cbb.appendChild(citem_);
			
			while(rs.next()){
				Comboitem citem = new Comboitem();
		        citem.setLabel(rs.getString("Label"));
		        citem.setValue(rs.getString("Value"));
		        
		        if(rs.getString("Description") != null && !rs.getString("Description").equals("") )
		        	citem.setDescription(rs.getString("Description"));
		        
		        cbb.appendChild(citem); 
			}
			
			cbb.setSelectedIndex(0);
			
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
	
	/*
	private void setView(String ReportUrl_1, String ReportUrl_2, String ReportUrl_3, String ReportType) {
		
		S_ReportUrl = MD_URL; //ReportUrl_3;
		
		// Set Name
		TextboxName.setText(Checkbox_PrintType.isChecked() ? ReportUrl_1 : ReportUrl_2);
		Label_ReportName.setValue(TextboxName.getText());
		
		// All Visible
		DateboxSDocDate.setVisible(false);
		DateboxEDocDate.setVisible(false);
		
		ComboboxDepartment.setVisible(false);
			
		Label_To.setVisible(false);
		Label_DocDate.setVisible(false);
		Label_Department.setVisible(false);
		

		this.S_ReportType = ReportType;
		
		if(ReportType.equals("0")){
			Label_DocDate.setVisible(true);
            DateboxSDocDate.setVisible(true);

        }else if(ReportType.equals("1")){
        	Label_DocDate.setVisible(true);
            DateboxSDocDate.setVisible(true);
            Label_To.setVisible(true);
            DateboxEDocDate.setVisible(true);
            
            Label_Department.setVisible(true);
            ComboboxDepartment.setVisible(true);

        }else if(ReportType.equals("2")){
        	Label_DocDate.setVisible(true);
            DateboxSDocDate.setVisible(true);
            
            Label_Department.setVisible(true);
            ComboboxDepartment.setVisible(true);

        }else if(ReportType.equals("3")){
        	Label_DocDate.setVisible(true);
            DateboxSDocDate.setVisible(true);
            
            Label_Department.setVisible(true);
            ComboboxDepartment.setVisible(true);

        }else if(ReportType.equals("4")){
        	Label_DocDate.setVisible(true);
            DateboxSDocDate.setVisible(true);
            Label_To.setVisible(true);
            DateboxEDocDate.setVisible(true);
            
            Label_Department.setVisible(false);
            ComboboxDepartment.setVisible(false);
            
        }
	}
	*/
	
	/*
	private void callReport() {
		
		//String S_ReportUrl = "http://poseintelligence.dyndns.biz:8888/report/";
		
		String S_ReportUrl = this.S_ReportUrl + "report/";

        String S_File = TextboxName.getText().toString();
        String sDate = DateboxSDocDate.getText().toString().replace("-","/");
        String eDate = DateboxEDocDate.getText().toString().replace("-","/");
        String S_Dept_Id = "";
        String S_Dept_Name = "";

        try {
        	S_Dept_Id = ComboboxDepartment.getSelectedItem().getValue();
        	S_Dept_Name = ComboboxDepartment.getSelectedItem().getLabel();
        }catch(Exception e) {
        	
        }

        if(S_ReportType.equals("0")){

        	S_ReportUrl += S_File + "?eDate="+sDate;

        }else if(S_ReportType.equals("1")){

        	S_ReportUrl += S_File + "?sDate=" + sDate + "&eDate=" + eDate + "&DeptID=" + S_Dept_Id + "&In=true";

        }else if(S_ReportType.equals("2")){

        	S_ReportUrl += S_File + "?eDate=" + sDate ;

        	if(S_Dept_Id != null) {
            	S_ReportUrl += "&Dept=" + S_Dept_Id;
            	S_ReportUrl += "&DeptName=" + S_Dept_Name;
            }
        }else if(S_ReportType.equals("3")){

            DateThai dh = new DateThai();

            String p_start_date =   Integer.valueOf(sDate.substring(0,2)).intValue() + " " + dh.getMonthThai( Integer.valueOf(sDate.substring(3,5)).intValue()) + " " + (Integer.valueOf(sDate.substring(6,10)).intValue() + 543);

            S_ReportUrl += S_File + "?sDate=" + sDate + "&p_start_date=" + p_start_date;
            

            if(S_Dept_Id != null) {
            	S_ReportUrl += "&DeptID=" + S_Dept_Id;
            	S_ReportUrl += "&DeptName=" + S_Dept_Name;
            }

        }else if(S_ReportType.equals("4")){

            S_ReportUrl += S_File + "?p_sdate=" + DateTime.convertDate(sDate.replace("/","-")) + "&p_edate=" + DateTime.convertDate(eDate.replace("/","-")) ;

            if(S_Dept_Id != null) {
            	S_ReportUrl += "&p_div_id=0&p_department_id=" + S_Dept_Id;
            }

        }else {
        	return;
        }

        System.out.println(S_ReportUrl);

        Iframe_Report.setSrc(S_ReportUrl);

    }
	*/
	
	private void callReport() {
		
		S_ReportUrl = MD_URL;
		String S_ReportCondtion = "";
			
		// Report Condition
		
		String S_ReportName = TextboxName.getText().toString();
        String sDate = DateboxSDocDate.getText().toString().replace("-","/");
        String eDate = DateboxEDocDate.getText().toString().replace("-","/");
        String S_Dept_Id = "0";
        //String S_Dept_Name = "";
        
        System.out.println("ComboboxDepartment.isVisible() = " +  ComboboxDepartment.isVisible());

        if(ComboboxDepartment.isVisible()) {
        	try {
	        	S_Dept_Id = ComboboxDepartment.getSelectedItem().getValue();
	        	//S_Dept_Name = ComboboxDepartment.getSelectedItem().getLabel();
	        }catch(Exception e) {
	        	
	        }
	        
	        if(S_Dept_Id == null) {
	        	S_Dept_Id= "0";
	        }
        }else {
        	S_Dept_Id = S_DeptId;
        }
        
        S_ReportCondtion = "?sDate=" + sDate + "&eDate=" + eDate + "&DeptID=" + S_Dept_Id;
		
		// Report URL
		String S_ReportUrl = this.S_ReportUrl + "report/" + S_ReportName + S_ReportCondtion;
		
		System.out.println(S_ReportUrl);
		
		Iframe_Report.setSrc(S_ReportUrl);
		
		WindowForm.setVisible(true);
		WindowFormCondition.setVisible(false);
	}
	
	// Event
	
	public void onClick$Button_CallReport(Event event) throws Exception {
		callReport();
	}
	
	public void onCheck$Checkbox_PrintType(Event event) throws Exception {
		Checkbox_PrintType.setLabel(Checkbox_PrintType.isChecked() ? "Excel" : "PDF");
		
		String S_ReportName = TextboxName.getText();
		
		TextboxName.setText(Checkbox_PrintType.isChecked() ? ( S_ReportName.replace(".php", "_xls") + ".php" ) : S_ReportName.replace("_xls", "") ) ;
		Label_ReportName.setValue(TextboxName.getText());

	}
	
	
	// ---------------------------------------------------------------------
	// Utility
	// ---------------------------------------------------------------------
	
	public void onProcess(boolean b) throws Exception {
    	WinProcess.setVisible(b);
    	WinProcess.setFocus(b);
    	WinProcess.setPosition("center");
    	WinProcess.setMode("modal");
    }
}
