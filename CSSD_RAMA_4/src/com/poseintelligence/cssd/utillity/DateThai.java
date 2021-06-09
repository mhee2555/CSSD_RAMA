package com.poseintelligence.cssd.utillity;

public class DateThai {

    public String getDayThai(String yDate) {
        String xDay="";
        switch(yDate){
            case "Sun":xDay="อาทิตย์";
                break;
            case "Mon":xDay="จันทร์";
                break;
            case "Tue":xDay="อังคาร";
                break;
            case "Wed":xDay="พุธ";
                break;
            case "Thu":xDay="พฤหัสบดี";
                break;
            case "Fri":xDay="ศุกร์";
                break;
            case "Sat":xDay="จันทร์";
                break;
        }
        return "วัน"+xDay;
    }

    public String getMonthThai(int yMonth) {
        String xMonth="";
        switch(yMonth){
            case 1:xMonth="มกราคม";
                break;
            case 2:xMonth="กุมภาพันธ์";
                break;
            case 3:xMonth="มีนาคม";
                break;
            case 4:xMonth="เมษายน";
                break;
            case 5:xMonth="พฤศภาคม";
                break;
            case 6:xMonth="มิถุนายน";
                break;
            case 7:xMonth="กรกฏาคม";
                break;
            case 8:xMonth="สิงหาคม";
                break;
            case 9:xMonth="กันยายน";
                break;
            case 10:xMonth="ตุลาคม";
                break;
            case 11:xMonth="พฤศจิกายน";
                break;
            case 12:xMonth="ธันวาคม";
                break;
        }
        return xMonth;
    }

    public int getYearThai(int yDate) {
        return yDate+543;
    }

}
