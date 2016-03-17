package com.dennisjonsson.cst.util;

import java.util.ArrayList;

public class SQLUtil {
	
	public static String ListToString(ArrayList<String> list){
		
		StringBuilder bd = new StringBuilder();
		for(String item : list){
			bd.append(item+",");
		}
		if(bd.length() > 0){
			bd.deleteCharAt(bd.length()-1);
		}
		
		return bd.toString();
	}

}
