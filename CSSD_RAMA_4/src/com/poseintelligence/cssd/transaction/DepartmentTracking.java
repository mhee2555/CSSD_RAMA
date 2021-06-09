package com.poseintelligence.cssd.transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Slider;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vlayout;

import com.poseintelligence.cssd.utillity.DateTime;

public class DepartmentTracking extends GenericForwardComposer{
		
	private boolean B_IsCreate = true;
	private Tree Treetracking;
	private Panel showStep;
	private Panel showDonut;
	private Vlayout panelsouth;
	public Vlayout panelnorth;
	public Label labelLastTime;
	public Textbox txtLastMins;
	public Label labelTimeDetail;
	
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
		onDisplay();
	}
	
	private void onDisplay() throws Exception{
		
		com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
        Class.forName(c.S_MYSQL_DRIVER);
        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
        conn.setAutoCommit(false);
        
        Statement stmt = conn.createStatement();
        Statement stmt2 = conn.createStatement();
    	ResultSet rs = null;
    	ResultSet rs2 = null;

//		Treetracking.getChildren().clear();
		Treechildren treeChild = new Treechildren();
		try{
			
		 String S_Sql = "SELECT " + 
				 		"            item.itemcode, " + 
				 		"            item.itemname, " + 
				 		"            COUNT( itemstock.UsageCode ) AS qty, " + 
				 		"            units.UnitName " + 
				 		"          FROM " + 
				 		"            item " + 
				 		"            INNER JOIN itemstock ON item.itemcode = itemstock.ItemCode " + 
				 		"            INNER JOIN units ON item.UnitID = units.ID " + 
				 		"          WHERE " + 
				 		"            itemstock.IsTag = '1'  " + 
				 		"            AND itemstock.DepID = '"+S_DeptId+"' "+ 
				 		"            AND itemstock.IsStatus != '5' " + 
				 		"            AND NOT item.NoWash = 1 " + 
				 		"            AND NOT item.NoWashType = 2 " ;
			
			System.out.println(S_Sql);
			rs = stmt.executeQuery(S_Sql);
			while(rs.next()){
				Treeitem treeItem = new Treeitem();
	        	Treerow treeRow = new Treerow();
	           	Treecell treecell = new Treecell();
	           	
	           	
	            treeRow.appendChild(treecell);
				treeRow.appendChild(new Treecell(rs.getString("itemname")));
				treeRow.appendChild(new Treecell(rs.getString("qty")));
				treeRow.appendChild(new Treecell(rs.getString("UnitName")));
				treeItem.appendChild(treeRow);
                treeChild.appendChild(treeItem);
                
                Treetracking.appendChild(treeChild);
                
                
                
                
	 			 String Sql2 = "SELECT " +
	 			         " item.itemcode, "+
	 			        " itemstock.RowID, "+
	 			         " itemstock.IsStatus, "+
	 			        " item.itemname, "+
	 			         " itemstock.UsageCode "+
	 			       " FROM "+
	 			         " itemstock "+
	 			         " INNER JOIN item ON item.itemcode = itemstock.ItemCode "+
	 			       " WHERE "+
	 			         " item.itemcode = '"+rs.getString("itemcode")+"' "+
	 			         " AND	itemstock.IsTag = '1' "+
	 			         " AND itemstock.DepID = '"+S_DeptId+"' "+
	 			         " AND NOT item.NoWash = 1 "+
	 			         " AND NOT item.NoWashType = 2 "+
	 			         " AND itemstock.IsStatus != '5' " ;
	 			System.out.println(Sql2);
				rs2 = stmt2.executeQuery(Sql2);	 
                Treechildren treeChild2 = new Treechildren();
                while(rs2.next()) {
                	Treeitem treeItem2 = new Treeitem();
                	treeItem.setOpen(false);

    	        	Treerow treeRow2 = new Treerow();
    	           	Treecell treecell2 = new Treecell();
    	           	treeRow2.appendChild(new Treecell(""));
    	           	treecell2.setLabel(rs2.getString("UsageCode"));
    	           	
                    treeRow2.appendChild(treecell2);
                    treeRow2.appendChild(new Treecell(""));
                    treeRow2.appendChild(Status(rs2.getInt("IsStatus"),rs2.getString("itemcode"),rs2.getString("RowID")));

                    treeItem2.appendChild(treeRow2);
                    treeChild2.appendChild(treeItem2);
                    treeItem.appendChild(treeChild2);
                    
                    
                    
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

	
	private Treecell Status(int IsStatus,String itemcode,final String RowID)throws Exception{
		

		
		Treecell tcc = new Treecell();
		Button btnStatus = new Button();
		btnStatus.setWidth("80px");
		btnStatus.setHeight("40px");
		btnStatus.setLabel("ค้นหา"); 	
		btnStatus.setStyle("font-size : 15px; text-align: center; background: #28a745; font-family: myFirstFont;");
		
		
		btnStatus.addEventListener("onClick", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception{
				
				//Step2//////////////////////////////////
				int percent = 0;
				int alltime = 0;
				double xhours = 0.00;
				double xmin =0.00;
				String xxhours =null;
				String xxmin =null;
				
				//Step4//////////////////////////////////
				int percent2 = 0;
				int alltime2 = 0;
				double xhours2 = 0.00;
				double xmin2 =0.00;
				String xxhours2 =null;
				String xxmin2 =null;
				
				if(IsStatus==1) {
					
					com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
			        Class.forName(c.S_MYSQL_DRIVER);
			        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
			        conn.setAutoCommit(false);
			        
			        Statement stmt = conn.createStatement();
			    	ResultSet rs = null;
					
					String sqlTime="SELECT	SEC_TO_TIME(All_Time - Run_Time) AS Now_Run_Time," + 
							"  All_Time - Run_Time AS Now_Run_Time_Second," + 
							"  TRUNCATE(100-((Run_Time/All_Time) * 100), 2) AS Percent_Run_Time," + 
							"  Run_Time," + 
							"  All_Time," + 
							"  StartTime," + 
							"  FinishTime," + 
							"  IsPause" + 
							"  FROM (SELECT TIME_TO_SEC( TIMEDIFF(washmachine.FinishTime, washmachine.StartTime) ) AS All_Time," + 
							"    TIME_TO_SEC( TIMEDIFF(washmachine.FinishTime, NOW()) ) AS Run_Time," + 
							"    TIMEDIFF(washmachine.FinishTime, wash.StartTime)," + 
							"    TIMEDIFF(washmachine.FinishTime, NOW())," + 
							"    COALESCE(DATE_FORMAT(washmachine.StartTime, '%d-%m-%Y %H:%i:%s'),'-') AS StartTime," + 
							"    COALESCE(DATE_FORMAT(washmachine.FinishTime, '%d-%m-%Y %H:%i:%s'),'-') AS FinishTime," + 
							"    washmachine.IsPause" + 
							"    FROM itemstock" + 
							"    LEFT JOIN  item ON item.ItemCode = itemstock.ItemCode" + 
							"    LEFT JOIN 	washdetail ON washdetail.ItemStockID = itemstock.RowID" + 
							"    LEFT JOIN 	wash ON wash.DocNo = washdetail.WashDocNo" + 
							"    LEFT JOIN 	washmachine ON washmachine.DocNo = wash.DocNo" + 
							"    WHERE 	itemstock.IsTag = 1" + 
							"    AND wash.IsStatus = 0" + 
							"    AND itemstock.RowID = '"+RowID+"'" + 
							"  ) AS washmachine" + 
							"  WHERE Run_Time > 0 ";
					rs = stmt.executeQuery(sqlTime);
					
					System.out.println("sqlTime :: " + sqlTime);
					
					if(rs.next()) {
						percent = rs.getInt("Percent_Run_Time");
						alltime = rs.getInt("Run_Time");
						
					}
					System.out.println("percent : "+percent);
					System.out.println("alltime : "+alltime);
					
					
					xhours = Math.floor(alltime / 3600); //Get whole hours
					alltime -= xhours* 3600;
					xmin = Math.floor(alltime / 60); //Get remaining minutes
					alltime-= xmin * 60;
				    String  ftime =  xhours + ":" + (xmin < 10 ? '0' + xmin:xmin) + ":" + (alltime < 10 ? '0' + alltime:alltime); //zero padding on minutes and seconds
						System.out.println("ftime : "+ftime);
						
						System.out.println("xhours : "+xhours);
						System.out.println("xmin : "+xmin);
						System.out.println("alltime : "+alltime);
						
						if(xhours>=10) {
							xxhours = Double.toString(xhours).substring(0,2);
						}else {
							xxhours =  Double.toString(xhours).substring(0,1);
						}
						
						
						if(xmin>=10) {
							xxmin = Double.toString(xmin).substring(0,2);
						}else {
							xxmin =  Double.toString(xmin).substring(0,1);
						}
						
						System.out.println("xxhours SubString : "+xxhours);
						System.out.println("xxmin SubString : "+xxmin);
					
		
				}else if(IsStatus==2){
					
					com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
			        Class.forName(c.S_MYSQL_DRIVER);
			        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
			        conn.setAutoCommit(false);
			        
			        Statement stmt2 = conn.createStatement();
			    	ResultSet rs2 = null;
					
					
					String sqlTimester = "SELECT SEC_TO_TIME(All_Time - Run_Time) AS Now_Run_Time, " + 
							"  All_Time - Run_Time AS Now_Run_Time_Second, " + 
							"  TRUNCATE(100-((Run_Time/All_Time) * 100), 2) AS Percent_Run_Time, " + 
							"  Run_Time, " + 
							"  All_Time, " + 
							"  StartTime, " + 
							"  FinishTime, " + 
							"  IsPause " + 
							"  FROM ( " + 
							"    SELECT TIME_TO_SEC( TIMEDIFF(sterilemachine.FinishTime, sterilemachine.StartTime) ) AS All_Time, " + 
							"    TIME_TO_SEC( TIMEDIFF(sterilemachine.FinishTime, NOW()) ) AS Run_Time, " + 
							"    TIMEDIFF(sterilemachine.FinishTime, sterile.StartTime), " + 
							"    TIMEDIFF(sterilemachine.FinishTime, NOW()), " + 
							"    COALESCE(DATE_FORMAT(sterilemachine.StartTime, '%d-%m-%Y %H:%i:%s'),'-') AS StartTime, " + 
							"    COALESCE(DATE_FORMAT(sterilemachine.FinishTime, '%d-%m-%Y %H:%i:%s'),'-') AS FinishTime, " + 
							"    sterilemachine.IsPause " + 
							"    FROM itemstock " + 
							"    LEFT JOIN 	item              ON			item.ItemCode = itemstock.ItemCode " + 
							"    LEFT JOIN 	steriledetail     ON			steriledetail.ItemStockID = itemstock.RowID " + 
							"    LEFT JOIN 	sterile           ON			sterile.DocNo = steriledetail.DocNo " + 
							"    LEFT JOIN 	sterilemachine    ON			sterilemachine.DocNo = sterile.DocNo " + 
							"    WHERE 		itemstock.IsTag = 1 " + 
							"    AND			  sterile.IsStatus = 0 " + 
							"    AND			  itemstock.RowID = '"+RowID+"'  " + 
							"  ) AS sterilemachine " + 
							" " + 
							"  WHERE Run_Time > 0 ";
					rs2 = stmt2.executeQuery(sqlTimester);
					System.out.println("sqlTimester :: " + sqlTimester);
					
					if(rs2.next()) {
						percent2 = rs2.getInt("Percent_Run_Time");
						alltime2 = rs2.getInt("Run_Time");
						
					}
					System.out.println("percent2 : "+percent2);
					System.out.println("alltime2 : "+alltime2);
					
					
					xhours2 = Math.floor(alltime2 / 3600); //Get whole hours
					alltime2 -= xhours2* 3600;
					xmin2 = Math.floor(alltime2 / 60); //Get remaining minutes
					alltime2-= xmin2 * 60;
				    String  ftime2 =  xhours2 + ":" + (xmin2 < 10 ? '0' + xmin2:xmin2) + ":" + (alltime2 < 10 ? '0' + alltime2:alltime2); //zero padding on minutes and seconds
						System.out.println("ftime2 : "+ftime2);
						
						System.out.println("xhours2 : "+xhours2);
						System.out.println("xmin2 : "+xmin2);
						System.out.println("alltime2 : "+alltime2);
						
						if(xhours2>=10) {
							xxhours2 = Double.toString(xhours2).substring(0,3);
						}else {
							xxhours2 =  Double.toString(xhours2).substring(0,1);
						}
						
						
						if(xmin2>=10) {
							xxmin2 = Double.toString(xmin2).substring(0,2);
						}else {
							xxmin2 =  Double.toString(xmin2).substring(0,1);
						}
						
						System.out.println("xxhours SubString : "+xxhours2);
						System.out.println("xxmin SubString : "+xxmin2);
				}
	
				Panelchildren pn = new Panelchildren();	
				pn.setStyle("align: left");
				Panelchildren pnR = new Panelchildren();	
				pnR.setStyle("align: right");
				
				showDonut.getChildren().clear();
				showStep.getChildren().clear();
				
				showDonut.setWidth("100%");
				showDonut.setHeight("500px");
				
				if(IsStatus==0)
				{		
					Image img = new Image();

					img.setWidth("100%");
					img.setHeight("650px");
					img.setStyle("align:center");
					img.setSrc("/img_step/step1.png");		
					pn.appendChild(img);
					
					
					
					labelLastTime.setVisible(false);
					txtLastMins.setVisible(false);
					labelTimeDetail.setVisible(false);
				}
				else if(IsStatus==1)
				{
					
					Image img = new Image();
					Slider sld = new Slider();
					sld.setWidth("100%"); 
					sld.setHeight("400px");
					sld.setStyle("align: center");
					sld.setMold("knob");
	                sld.setMaxpos(100); 	//เธชเธดเน�เธ�เธชเธธเธ”
	                sld.setCurpos(percent); //เน€เธฃเธดเน�เธกเธ•เน�เธ�
	                sld.setStep(1);			//เธ�เธฑเธ�เธ—เธตเน�เธฅเธฐ1
	                sld.setAngleArc(360);	//360 เธญเธ�เธจเธฒ
	                sld.setStrokeWidth(75);	//เธ�เธงเธฒเธกเธ�เธงเน�เธฒเธ�เธ�เธญเธ�เน�เธ–เธ�
	                sld.setSlidingtext("50");

	                Timer timer = new Timer();
	                timer.setRunning(true);
	                timer.setDelay(1000);
	                timer.setRepeats(true);
	                timer.start();
	                
					img.setWidth("100%");
					img.setHeight("650px");
					img.setStyle("align:center");
					img.setSrc("/img_step/step2.png");
					
					panelnorth.setVisible(true);
					panelnorth.setStyle("align: center;");
					
					panelsouth.setVisible(true);
					panelsouth.setStyle("align: center;");
					
					labelLastTime.setVisible(true);
					txtLastMins.setVisible(true);
					labelTimeDetail.setVisible(true);
					
					
					labelLastTime.setValue("เน€เธงเธฅเธฒเธ—เธตเน�เน€เธซเธฅเธทเธญ  ");
					labelLastTime.setStyle("color: #000000; font-size: 40px; font-weight: bold; margin-right: 170px;" );
					
					
					
					txtLastMins.setText(xxhours +" : "+xxmin+" : "+alltime);
					txtLastMins.setStyle("background: #FFFFFF; border: none; color: #000000; font-size: 40px; font-weight: bold; margin-left: 150px;");
					txtLastMins.setHeight("50px");
					txtLastMins.setReadonly(true);
					
					
					labelTimeDetail.setValue(" ( HH : MM : SS ) ");
					labelTimeDetail.setStyle("color: #000000; font-size: 35px; font-weight: bold; margin-right: 130px;");
					
					
					pn.appendChild(img);
					pnR.appendChild(sld);
					
					panelsouth.appendChild(labelLastTime);
					panelsouth.appendChild(txtLastMins);
					panelsouth.appendChild(labelTimeDetail);
					
					panelnorth.appendChild(timer);
					panelsouth.appendChild(timer);
					
					if(percent == 100) {
						
						sld.setVisible(false);
						labelLastTime.setVisible(false);
						txtLastMins.setVisible(false);
						labelTimeDetail.setVisible(false);
					}
				}else if(IsStatus==1)
				{
					Image img = new Image();
					img.setWidth("100%");
					img.setHeight("650px");
					img.setStyle("align:center");
					img.setSrc("/img_step/step3.png");
					pn.appendChild(img);
					
					labelLastTime.setVisible(false);
					txtLastMins.setVisible(false);
					labelTimeDetail.setVisible(false);
				}
				else if(IsStatus==2)
				{
					Image img = new Image();
					Slider sld = new Slider();
					sld.setWidth("100%"); 
					sld.setHeight("400px");
					sld.setStyle("align: center");
					sld.setMold("knob");
	                sld.setMaxpos(100); 	//เธชเธดเน�เธ�เธชเธธเธ”
	                sld.setCurpos(percent2); 		//เน€เธฃเธดเน�เธกเธ•เน�เธ�
	                sld.setStep(1);			//เธ�เธฑเธ�เธ—เธตเน�เธฅเธฐ1
	                sld.setAngleArc(360);	//360 เธญเธ�เธจเธฒ
	                sld.setStrokeWidth(75);	//เธ�เธงเธฒเธกเธ�เธงเน�เธฒเธ�เธ�เธญเธ�เน�เธ–เธ�
	                sld.setSlidingtext("50");
	                sld.disableBindingAnnotation();
	                sld.setFocus(true);
	                
	                Timer timer = new Timer();
	                timer.setRunning(true);
	                timer.setDelay(1000);
	                timer.setRepeats(true);
	                timer.start();
	                
					img.setWidth("100%");
					img.setHeight("650px");
					img.setStyle("align:center");
					img.setSrc("/img_step/step4.png");
					
					panelnorth.setVisible(true);
					panelnorth.setStyle("align: center;");
					
					panelsouth.setVisible(true);
					panelsouth.setStyle("align: center;");
					
					labelLastTime.setVisible(true);
					txtLastMins.setVisible(true);
					labelTimeDetail.setVisible(true);
					
					
					labelLastTime.setValue("เน€เธงเธฅเธฒเธ—เธตเน�เน€เธซเธฅเธทเธญ  "
							);
					labelLastTime.setStyle("color: #000000; font-size: 40px; font-weight: bold; margin-right: 170px;" );
					
					
					
					txtLastMins.setText(xxhours2 +" : "+xxmin2+" : "+alltime2);
					txtLastMins.setStyle("background: #FFFFFF; border: none; color: #000000; font-size: 40px; font-weight: bold; margin-left: 150px;");
					txtLastMins.setHeight("50px");
					txtLastMins.setReadonly(true);
					
					
					labelTimeDetail.setValue(" ( HH : MM : SS ) ");
					labelTimeDetail.setStyle("color: #000000; font-size: 35px; font-weight: bold; margin-right: 130px;");
					
					
					pn.appendChild(img);
					pnR.appendChild(sld);
					timer.start();
					panelsouth.appendChild(labelLastTime);
					panelsouth.appendChild(txtLastMins);
					panelsouth.appendChild(labelTimeDetail);
					
					panelnorth.appendChild(timer);
					panelsouth.appendChild(timer);
					if(percent2 == 100) {
						sld.setVisible(false);
						labelLastTime.setVisible(false);
						txtLastMins.setVisible(false);
						labelTimeDetail.setVisible(false);
					}
				}
				else if(IsStatus==3)
				{
					Image img = new Image();
					img.setWidth("100%");
					img.setHeight("650px");
					img.setStyle("align:center");
					img.setSrc("/img_step/step5.png");
					pn.appendChild(img);
					
					labelLastTime.setVisible(false);
					txtLastMins.setVisible(false);
					labelTimeDetail.setVisible(false);
				}
				else if(IsStatus==4)
				{
					Image img = new Image();
					img.setWidth("100%");
					img.setHeight("650px");
					img.setStyle("align:center");
					img.setSrc("/img_step/step6.png");
					pn.appendChild(img);
					
					labelLastTime.setVisible(false);
					txtLastMins.setVisible(false);
					labelTimeDetail.setVisible(false);
				}
				else if(IsStatus==6)
				{
					labelLastTime.setVisible(false);
					txtLastMins.setVisible(false);
					labelTimeDetail.setVisible(false);
				}
				else if(IsStatus==7)
				{
					labelLastTime.setVisible(false);
					txtLastMins.setVisible(false);
					labelTimeDetail.setVisible(false);
				}

				showStep.appendChild(pn);
				showDonut.appendChild(pnR);
			}
		});

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		tcc.appendChild(btnStatus);
		return tcc;
	}
	
}
