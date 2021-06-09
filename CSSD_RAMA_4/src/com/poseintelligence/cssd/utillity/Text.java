//  =================================
// 	Create by 	: A
//	Create date : 2020-11-03
// 	Update by 	: A
//	Update date : 2021-02-09
// ==================================

package com.poseintelligence.cssd.utillity;

import java.util.ArrayList;
import java.util.Iterator;

public class Text {

	public static String subLastString(String S_Str, int I_LastString){
		if(S_Str.length() > 0)
			S_Str = S_Str.substring(0, S_Str.length() - I_LastString);
		
		return S_Str;
	}
	
	public static String subString(String S_Str, int I_Digit){
		if(S_Str.length() > I_Digit)
			S_Str = S_Str.substring(0, I_Digit);
		
		return S_Str;
	}
	
	public static String concat(String S_Str1, String S_Str2, boolean B_IsBegin){
		return (S_Str1 == null || S_Str1.equals("")) ? "" : ( B_IsBegin ? (S_Str1 + S_Str2) : (S_Str2 + S_Str1) );	
	}
	
	public static String addDad(String S_Str){
		return (S_Str == null || S_Str.equals("")) ? "-" : S_Str;	
	}
	
	public static String addSingleQuote(String S_Str){
			return S_Str != null ? ("'" + S_Str + "'") : S_Str;	
	}
	
	public static boolean isData(String S_Str){
		return S_Str != null && (!S_Str.equals(""));
	}
	
	public static ArrayList<String> addArrayList(ArrayList<String> ar, String S_Value) throws Exception{

		Iterator<String> iter = ar.iterator();
		
		while (iter.hasNext()) { 
            if(iter.next().equals(S_Value)) {
            	return ar;
            }
        }
		
		ar.add(S_Value);
		
		return ar;
	}
	
    public static String getArrayList(ArrayList<String> ar) throws Exception{
		String S_List = "";
		Iterator<String> iter = ar.iterator();
		
		while (iter.hasNext()) { 
			S_List += iter.next() + ",";
        }
		
		if(!S_List.equals("")) {
			S_List = S_List.substring(0, S_List.length()-1);
		}
		
		return S_List;
	}
    
    public static String[]  get2Line(String str){

        String str_line1 = str.substring(0, 25);
        String str_line2 = "";
        
        //System.out.println(str_line1);

        int last_idx = 0;

        try{
            last_idx = str_line1.lastIndexOf(" ");
            str_line1 = str.substring(0, last_idx);
            str_line2 = str.substring(last_idx+1);
        }catch(Exception e){
            str_line1 = str.substring(0, 25);
            str_line2 = str.substring(25);
        }

        return new String[]{str_line1, str_line2};
    }
    
    public static String getLastAaray(String[] AS_){
    	try{
    		return AS_[AS_.length-1];
    	}catch(Exception e){
            return null;
        }
    }
    
    public static String getMessageboxIcon(char C_Type){
    	switch (C_Type) {
		case 'q': return "z-messagebox-icon z-messagebox-question";
		case 'x': return "z-messagebox-icon z-messagebox-exclamation";
		case 'i': return "z-messagebox-icon z-messagebox-information";
		case 'e': return "z-messagebox-icon z-messagebox-error";
		default:
			return null;
		}
    }
    
    public static String convertQR(String S_Input){

    	String S_QR = "";
	
		if(S_Input.length() > 0) {
			if( (int) S_Input.charAt(0) > 1000) {
				for(int i=0;i<S_Input.length();i++) {
					S_QR += Text.convertEN(S_Input.charAt(i));
				}
			}else {
				S_QR = S_Input;
			}
		}
		
		return S_QR;
    }
    
    public static char convertEN(char C_Char){
    	switch (C_Char) {
		case 'ข':	
			return '-';
		case 'จ':	
			return '0';
		case 'ๅ':	
			return '1';
		case '/':	
			return '2';
		case '-':	
			return '3';
		case 'ภ':	
			return '4';
		case 'ถ':	
			return '5';
		case 'ุ':	
			return '6';
		case 'ึ':	
			return '7';
		case 'ค':	
			return '8';
		case 'ต':	
			return '9';
		case 'ฤ':	
			return 'A';
		case 'ฺ':	
			return 'B';
		case 'ฉ':	
			return 'C';
		case 'ฏ':	
			return 'D';
		case 'ฎ':	
			return 'E';
		case 'โ':	
			return 'F';
		case 'ฌ':	
			return 'G';
		case '็':	
			return 'H';
		case 'ณ':	
			return 'I';
		case '๋':	
			return 'J';
		case 'ษ':	
			return 'K';
		case 'ศ':	
			return 'L';
		case '?':	
			return 'M';
		case '์':	
			return 'N';
		case 'ฯ':	
			return 'O';
		case 'ญ':	
			return 'P';
		case '๐':	
			return 'Q';
		case 'ฑ':	
			return 'R';
		case 'ฆ':	
			return 'S';
		case 'ธ':	
			return 'T';
		case '๊':	
			return 'U';
		case 'ฮ':	
			return 'V';
		case '"':	
			return 'W';    		
		case ')':	
			return 'X';
		case 'ํ':	
			return 'Y';
		case '(':	
			return 'Z';
		case 'ฟ':	
			return 'a';
	    case 'ิ':	
			return 'b';
	    case 'แ':	
			return 'c';
	    case 'ก':	
			return 'd';
	    case 'ำ':	
			return 'e';
	    case 'ด':	
			return 'f';
	    case 'เ':	
			return 'g';
	    case '้':	
			return 'h';
	    case 'ร':	
			return 'i';
	    case '่':	
			return 'j';
	    case 'า':	
			return 'k';
	    case 'ส':	
			return 'l';
	    case 'ท':	
			return 'm';
	    case 'ื':	
			return 'n';
	    case 'น':	
			return 'o';
	    case 'ย':	
			return 'p';
	    case 'ๆ':	
			return 'q';
	    case 'พ':	
			return 'r';
	    case 'ห':	
			return 's';
	    case 'ะ':	
			return 't';
	    case 'ี':	
			return 'u';
	    case 'อ':	
			return 'v';
	    case 'ไ':	
			return 'w';
	    case 'ป':	
			return 'x';
	    case 'ั':	
			return 'y';
	    case 'ผ':	
			return 'z';
		default:
			return ' ';
		}
    	
    }


}
