//  =================================
// 	Create by 	: A
//	Create date : 2020-11-03
// 	Update by 	: A
//	Update date : 2020-11-03
// ==================================

package com.poseintelligence.cssd.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Setting {
	
	int PlatForm = 2;
	
    int MD_PrinterNo = 1;

    boolean MN_IsUsedFormNonUsage = false;
    boolean MN_IsUsedComputeDate = false;
    boolean MN_IsUsedReceiveDevice = false;

    boolean MD_IsUsedSoundScanQR = false;
    boolean MD_IsAutoItemCode = false;
    boolean MD_IsItemPriceCode = false;
    boolean MD_IsUsedItemCode2 = false;
    String MD_URL = "";
    
    
    boolean SS_IsUsedItemSet = false;
    boolean SS_IsCopyPayout = false;
    boolean SS_IsShowSender = false;
    boolean SS_IsReceiverDropdown = false;
    boolean SS_IsApprove = false;
    boolean SS_IsUsedItemDepartment = false;
    boolean SS_IsUsedReceiveTime = false;

    boolean SR_IsUsedPreparer = false;
    boolean SR_IsUsedApprover = false;
    boolean SR_IsUsedPacker = false;
    boolean SR_IsUsedSteriler = false;
    boolean SR_IsUsedDBUserOperation = false;
    boolean SR_IsUsedDropdownUserOperation = false;
    boolean SR_IsRememberUserOperation = false;
    boolean SR_IsEditRound = false;
    boolean SR_IsUsedOccupancyRate = false;
    boolean SR_IsUsedUserOperationDetail = false;
    boolean SR_IsApproveSterile = false ;
    boolean SR_IsShowFormCheckList = false ;
    boolean SR_IsUsedImportNonReuse = false;
    boolean SR_IncExp = false;
    boolean SR_Is_Preview_Print_Sticker = false;
    boolean SR_Is_NonSelectRound = false;
    boolean SR_IsEditSterileProgram = false;
    boolean SR_IsNotApprove = false;

    boolean PA_IsUsedRecipienter = false;
    boolean PA_IsUsedApprover = false;
    boolean PA_IsConfirmClosePayout = false;
    boolean PA_IsUsedDepartmentQR = false;
    boolean PA_DefaultDepartmentQR = false;
    boolean PA_IsCreateReceiveDepartment = false;
    
    public Setting(final String S_DB) {
    	try{
	    	com.poseintelligence.cssd.db.Conn c = new com.poseintelligence.cssd.db.Conn();
	        Class.forName(c.S_MYSQL_DRIVER);
	        Connection conn = java.sql.DriverManager.getConnection(c.getHost(S_DB), c.S_USER, c.S_PASSWORD);
	        conn.setAutoCommit(false);
	        
	        Statement stmt = conn.createStatement();
	    	ResultSet rs = null;
	        
	        String sql =
	        		"SELECT	"
		    	+ 	"		`Versions`," 
	            + 	"       `MN_IsUsedFormNonUsage`," 
	            + 	"       `MN_IsUsedComputeDate`," 
	            + 	"       `MN_IsUsedReceiveDevice`," 
	
	            + 	"       `MD_IsUsedSoundScanQR`," 
	            + 	"       1 AS MD_PrinterNo," 
	            + 	"       `MD_IsAutoItemCode`," 
	            + 	"       `MD_IsItemPriceCode`," 
	            + 	"       `MD_IsUsedItemCode2`," 
	            + 	"       `MD_URL`," 
	
	            + 	"       `SS_IsUsedItemSet`," 
	            + 	"       `SS_IsCopyPayout`," 
	            + 	"       `SS_IsShowSender`," 
	            + 	"       `SS_IsReceiverDropdown`," 
	            + 	"       `SS_IsApprove`," 
	            + 	"       `SS_IsUsedItemDepartment`," 
	            + 	"       `SS_IsUsedReceiveTime`," 
	
	            + 	"       `SR_IsUsedPreparer`," 
	            + 	"       `SR_IsUsedApprover`," 
	            + 	"       `SR_IsUsedPacker`," 
	            + 	"       `SR_IsUsedSteriler`," 
	            + 	"       `SR_IsUsedDBUserOperation`," 
	            + 	"       `SR_IsUsedDropdownUserOperation`," 
	            + 	"       `SR_IsRememberUserOperation`," 
	            + 	"       `SR_IsEditRound`," 
	            + 	"       `SR_IsUsedOccupancyRate`," 
	            + 	"       `SR_IsApproveSterile`," 
	            + 	"       `SR_IsShowFormCheckList`," 
	            + 	"       `SR_IsUsedUserOperationDetail`," 
	            + 	"       `SR_IsUsedImportNonReuse`," 
	            + 	"       `SR_IncExp`," 
	            + 	"       `SR_Is_Preview_Print_Sticker`," 
	            + 	"       `SR_Is_NonSelectRound`," 
	            + 	"       `SR_IsEditSterileProgram`," 
	            + 	"       `SR_IsNotApprove`," 
	
	            + 	"       `PA_IsUsedRecipienter`," 
	            + 	"       `PA_IsUsedApprover`, " 
	            + 	"       `PA_IsConfirmClosePayout`, " 
	            + 	"       `PA_IsUsedDepartmentQR`, " 
	            + 	"       `PA_DefaultDepartmentQR`,"
	            + 	"		`PA_IsCreateReceiveDepartment` " 
		
				+ 	"FROM    configuration " 
					
				+ 	"LIMIT 	1";
	
	        System.out.println(sql);
			
	        try{
				rs = stmt.executeQuery(sql);
					
				if(rs.next()){
					
					setPlatForm(rs.getInt("Versions"));
					
	                setMN_IsUsedFormNonUsage(rs.getString("MN_IsUsedFormNonUsage").equals("1"));
	                setMN_IsUsedComputeDate(rs.getString("MN_IsUsedComputeDate").equals("1"));
	                setMN_IsUsedReceiveDevice(rs.getString("MN_IsUsedReceiveDevice").equals("1"));
	
	                setMD_IsUsedSoundScanQR(rs.getString("MD_IsUsedSoundScanQR").equals("1"));
	                setMD_PrinterNo(rs.getInt("MD_PrinterNo"));
	                setMD_IsAutoItemCode(rs.getString("MD_IsAutoItemCode").equals("1"));
	                setMD_IsItemPriceCode(rs.getString("MD_IsItemPriceCode").equals("1"));
	                setMD_IsUsedItemCode2(rs.getString("MD_IsUsedItemCode2").equals("1"));
	                setMD_URL(rs.getString("MD_URL"));
	                
	                setSS_IsUsedItemSet(rs.getString("SS_IsUsedItemSet").equals("1"));
	                setSS_IsCopyPayout(rs.getString("SS_IsCopyPayout").equals("1"));
	                setSS_IsShowSender(rs.getString("SS_IsShowSender").equals("1"));
	                setSS_IsReceiverDropdown(rs.getString("SS_IsReceiverDropdown").equals("1"));
	                setSS_IsApprove(rs.getString("SS_IsApprove").equals("1"));
	                setSS_IsUsedItemDepartment(rs.getString("SS_IsUsedItemDepartment").equals("1"));
	                setSS_IsUsedReceiveTime(rs.getString("SS_IsUsedReceiveTime").equals("1"));
	                
	                setSR_IsUsedPreparer(rs.getString("SR_IsUsedPreparer").equals("1"));
	                setSR_IsUsedApprover(rs.getString("SR_IsUsedApprover").equals("1"));
	                setSR_IsUsedPacker(rs.getString("SR_IsUsedPacker").equals("1"));
	                setSR_IsUsedSteriler(rs.getString("SR_IsUsedSteriler").equals("1"));
	                setSR_IsUsedDBUserOperation(rs.getString("SR_IsUsedDBUserOperation").equals("1"));
	                setSR_IsUsedDropdownUserOperation(rs.getString("SR_IsUsedDropdownUserOperation").equals("1"));
	                setSR_IsRememberUserOperation(rs.getString("SR_IsRememberUserOperation").equals("1"));
	                setSR_IsEditRound(rs.getString("SR_IsEditRound").equals("1"));
	                setSR_IsUsedOccupancyRate(rs.getString("SR_IsUsedOccupancyRate").equals("1"));
	                setSR_IsUsedUserOperationDetail(rs.getString("SR_IsUsedUserOperationDetail").equals("1"));
	                setSR_IsShowFormCheckList(rs.getString("SR_IsShowFormCheckList").equals("1"));
	                setSR_IsApproveSterile(rs.getString("SR_IsApproveSterile").equals("1"));
	                setSR_IsUsedImportNonReuse(rs.getString("SR_IsUsedImportNonReuse").equals("1"));
	                setSR_IncExp(rs.getString("SR_IncExp").equals("1"));
	                setSR_Is_Preview_Print_Sticker(rs.getString("SR_Is_Preview_Print_Sticker").equals("1"));
	                setSR_Is_NonSelectRound(rs.getString("SR_Is_NonSelectRound").equals("1"));
	                setSR_IsEditSterileProgram(rs.getString("SR_IsEditSterileProgram").equals("1"));
	                setSR_IsNotApprove(rs.getString("SR_IsNotApprove").equals("1"));
	
	                setPA_IsUsedRecipienter(rs.getString("PA_IsUsedRecipienter").equals("1"));
	                setPA_IsUsedApprover(rs.getString("PA_IsUsedApprover").equals("1"));
	                setPA_IsConfirmClosePayout(rs.getString("PA_IsConfirmClosePayout").equals("1"));
	                setPA_IsUsedDepartmentQR(rs.getString("PA_IsUsedDepartmentQR").equals("1"));
	                setPA_DefaultDepartmentQR(rs.getString("PA_DefaultDepartmentQR").equals("1"));
	                setPA_IsCreateReceiveDepartment(rs.getString("PA_IsCreateReceiveDepartment").equals("1"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return;
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
    	} catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public boolean isPA_IsCreateReceiveDepartment() {
		return PA_IsCreateReceiveDepartment;
	}

	public void setPA_IsCreateReceiveDepartment(boolean pA_IsCreateReceiveDepartment) {
		PA_IsCreateReceiveDepartment = pA_IsCreateReceiveDepartment;
	}

	public int getPlatForm() {
		return PlatForm;
	}

	public void setPlatForm(int platForm) {
		PlatForm = platForm;
	}

	public String getMD_URL() {
		return MD_URL;
	}

	public void setMD_URL(String mD_URL) {
		MD_URL = mD_URL;
	}

	public boolean isMD_IsUsedSoundScanQR() {
        return MD_IsUsedSoundScanQR;
    }

    public void setMD_IsUsedSoundScanQR(boolean MD_IsUsedSoundScanQR) {
        this.MD_IsUsedSoundScanQR = MD_IsUsedSoundScanQR;
    }

    public boolean isPA_IsUsedDepartmentQR() {
        return PA_IsUsedDepartmentQR;
    }

    public void setPA_IsUsedDepartmentQR(boolean PA_IsUsedDepartmentQR) {
        this.PA_IsUsedDepartmentQR = PA_IsUsedDepartmentQR;
    }

    public boolean isPA_DefaultDepartmentQR() {
        return PA_DefaultDepartmentQR;
    }

    public void setPA_DefaultDepartmentQR(boolean PA_DefaultDepartmentQR) {
        this.PA_DefaultDepartmentQR = PA_DefaultDepartmentQR;
    }

    public boolean isPA_IsConfirmClosePayout() {
        return PA_IsConfirmClosePayout;
    }

    public void setPA_IsConfirmClosePayout(boolean PA_IsConfirmClosePayout) {
        this.PA_IsConfirmClosePayout = PA_IsConfirmClosePayout;
    }

    public boolean isSR_IsNotApprove() {
        return SR_IsNotApprove;
    }

    public void setSR_IsNotApprove(boolean SR_IsNotApprove) {
        this.SR_IsNotApprove = SR_IsNotApprove;
    }

    public boolean isSR_IsEditSterileProgram() {
        return SR_IsEditSterileProgram;
    }

    public void setSR_IsEditSterileProgram(boolean SR_IsEditSterileProgram) {
        this.SR_IsEditSterileProgram = SR_IsEditSterileProgram;
    }

    public boolean isMN_IsUsedComputeDate() {
        return MN_IsUsedComputeDate;
    }

    public void setMN_IsUsedComputeDate(boolean MN_IsUsedComputeDate) {
        this.MN_IsUsedComputeDate = MN_IsUsedComputeDate;
    }

    public boolean isSR_IncExp() {
        return SR_IncExp;
    }

    public void setSR_IncExp(boolean SR_IncExp) {
        this.SR_IncExp = SR_IncExp;
    }

    public boolean isSS_IsUsedItemDepartment() {
        return SS_IsUsedItemDepartment;
    }

    public void setSS_IsUsedItemDepartment(boolean SS_IsUsedItemDepartment) {
        this.SS_IsUsedItemDepartment = SS_IsUsedItemDepartment;
    }

    public boolean isMN_IsUsedFormNonUsage() {
        return MN_IsUsedFormNonUsage;
    }

    public void setMN_IsUsedFormNonUsage(boolean MN_IsUsedFormNonUsage) {
        this.MN_IsUsedFormNonUsage = MN_IsUsedFormNonUsage;
    }

    public boolean isSR_IsUsedImportNonReuse() {
        return SR_IsUsedImportNonReuse;
    }

    public void setSR_IsUsedImportNonReuse(boolean SR_IsUsedImportNonReuse) {
        this.SR_IsUsedImportNonReuse = SR_IsUsedImportNonReuse;
    }

    public int getMD_PrinterNo() {
        return MD_PrinterNo;
    }

    public void setMD_PrinterNo(int MD_PrinterNo) {
        this.MD_PrinterNo = MD_PrinterNo;
    }

    public boolean isMD_IsUsedItemCode2() {
        return MD_IsUsedItemCode2;
    }

    public void setMD_IsUsedItemCode2(boolean MD_IsUsedItemCode2) {
        this.MD_IsUsedItemCode2 = MD_IsUsedItemCode2;
    }

    public boolean isSR_IsApproveSterile() {
        return SR_IsApproveSterile;
    }

    public void setSR_IsApproveSterile(boolean SR_IsApproveSterile) {
        this.SR_IsApproveSterile = SR_IsApproveSterile;
    }

    public boolean isSR_IsShowFormCheckList() {
        return SR_IsShowFormCheckList;
    }

    public void setSR_IsShowFormCheckList(boolean SR_IsShowFormCheckList) {
        this.SR_IsShowFormCheckList = SR_IsShowFormCheckList;
    }

    public boolean isMD_IsItemPriceCode() {
        return MD_IsItemPriceCode;
    }

    public void setMD_IsItemPriceCode(boolean MD_IsItemPriceCode) {
        this.MD_IsItemPriceCode = MD_IsItemPriceCode;
    }

    public boolean isSS_IsUsedItemSet() {
        return SS_IsUsedItemSet;
    }

    public void setSS_IsUsedItemSet(boolean SS_IsUsedItemSet) {
        this.SS_IsUsedItemSet = SS_IsUsedItemSet;
    }

    public boolean isMD_IsAutoItemCode() {
        return MD_IsAutoItemCode;
    }

    public void setMD_IsAutoItemCode(boolean MD_IsAutoItemCode) {
        this.MD_IsAutoItemCode = MD_IsAutoItemCode;
    }

    public boolean isSS_IsCopyPayout() {
        return SS_IsCopyPayout;
    }

    public void setSS_IsCopyPayout(boolean SS_IsCopyPayout) {
        this.SS_IsCopyPayout = SS_IsCopyPayout;
    }

    public boolean isSS_IsShowSender() {
        return SS_IsShowSender;
    }

    public void setSS_IsShowSender(boolean SS_IsShowSender) {
        this.SS_IsShowSender = SS_IsShowSender;
    }

    public boolean isSS_IsReceiverDropdown() {
        return SS_IsReceiverDropdown;
    }

    public void setSS_IsReceiverDropdown(boolean SS_IsReceiverDropdown) {
        this.SS_IsReceiverDropdown = SS_IsReceiverDropdown;
    }

    public boolean isSS_IsApprove() {
        return SS_IsApprove;
    }

    public void setSS_IsApprove(boolean SS_IsApprove) {
        this.SS_IsApprove = SS_IsApprove;
    }

    public boolean isSR_IsUsedPreparer() {
        return SR_IsUsedPreparer;
    }

    public void setSR_IsUsedPreparer(boolean SR_IsUsedPreparer) {
        this.SR_IsUsedPreparer = SR_IsUsedPreparer;
    }

    public boolean isSR_IsUsedApprover() {
        return SR_IsUsedApprover;
    }

    public void setSR_IsUsedApprover(boolean SR_IsUsedApprover) {
        this.SR_IsUsedApprover = SR_IsUsedApprover;
    }

    public boolean isSR_IsUsedPacker() {
        return SR_IsUsedPacker;
    }

    public void setSR_IsUsedPacker(boolean SR_IsUsedPacker) {
        this.SR_IsUsedPacker = SR_IsUsedPacker;
    }

    public boolean isSR_IsUsedSteriler() {
        return SR_IsUsedSteriler;
    }

    public void setSR_IsUsedSteriler(boolean SR_IsUsedSteriler) {
        this.SR_IsUsedSteriler = SR_IsUsedSteriler;
    }

    public boolean isSR_IsUsedDBUserOperation() {
        return SR_IsUsedDBUserOperation;
    }

    public void setSR_IsUsedDBUserOperation(boolean SR_IsUsedDBUserOperation) {
        this.SR_IsUsedDBUserOperation = SR_IsUsedDBUserOperation;
    }

    public boolean isSR_IsUsedDropdownUserOperation() {
        return SR_IsUsedDropdownUserOperation;
    }

    public void setSR_IsUsedDropdownUserOperation(boolean SR_IsUsedDropdownUserOperation) {
        this.SR_IsUsedDropdownUserOperation = SR_IsUsedDropdownUserOperation;
    }

    public boolean isSR_IsRememberUserOperation() {
        return SR_IsRememberUserOperation;
    }

    public void setSR_IsRememberUserOperation(boolean SR_IsRememberUserOperation) {
        this.SR_IsRememberUserOperation = SR_IsRememberUserOperation;
    }

    public boolean isSR_IsEditRound() {
        return SR_IsEditRound;
    }

    public void setSR_IsEditRound(boolean SR_IsEditRound) {
        this.SR_IsEditRound = SR_IsEditRound;
    }

    public boolean isSR_IsUsedOccupancyRate() {
        return SR_IsUsedOccupancyRate;
    }

    public void setSR_IsUsedOccupancyRate(boolean SR_IsUsedOccupancyRate) {
        this.SR_IsUsedOccupancyRate = SR_IsUsedOccupancyRate;
    }

    public boolean isSR_IsUsedUserOperationDetail() {
        return SR_IsUsedUserOperationDetail;
    }

    public void setSR_IsUsedUserOperationDetail(boolean SR_IsUsedUserOperationDetail) {
        this.SR_IsUsedUserOperationDetail = SR_IsUsedUserOperationDetail;
    }

    public boolean isPA_IsUsedRecipienter() {
        return PA_IsUsedRecipienter;
    }

    public void setPA_IsUsedRecipienter(boolean PA_IsUsedRecipienter) {
        this.PA_IsUsedRecipienter = PA_IsUsedRecipienter;
    }

    public boolean isPA_IsUsedApprover() {
        return PA_IsUsedApprover;
    }

    public void setPA_IsUsedApprover(boolean PA_IsUsedApprover) {
        this.PA_IsUsedApprover = PA_IsUsedApprover;
    }

    public boolean isSR_Is_Preview_Print_Sticker() {
        return SR_Is_Preview_Print_Sticker;
    }

    public void setSR_Is_Preview_Print_Sticker(boolean SR_Is_Preview_Print_Sticker) {
        this.SR_Is_Preview_Print_Sticker = SR_Is_Preview_Print_Sticker;
    }

    public boolean isSR_Is_NonSelectRound() {
        return SR_Is_NonSelectRound;
    }

    public void setSR_Is_NonSelectRound(boolean SR_Is_NonSelectRound) {
        this.SR_Is_NonSelectRound = SR_Is_NonSelectRound;
    }

    public boolean isMN_IsUsedReceiveDevice() {
        return MN_IsUsedReceiveDevice;
    }

    public void setMN_IsUsedReceiveDevice(boolean MN_IsUsedReceiveDevice) {
        this.MN_IsUsedReceiveDevice = MN_IsUsedReceiveDevice;
    }

    public boolean isSS_IsUsedReceiveTime() {
        return SS_IsUsedReceiveTime;
    }

    public void setSS_IsUsedReceiveTime(boolean SS_IsUsedReceiveTime) {
        this.SS_IsUsedReceiveTime = SS_IsUsedReceiveTime;
    }
}
