package com.clearcapital.source;

public class NameUtilityImproved {
	
	public static String convertNameToInitials(String name) {
		String split[] = name.split(" ");
		
		if ( split.length != 0 ) {
			StringBuffer sb = new StringBuffer();
			for ( String s : split ) {
				sb.append(s.substring(0, 1)).append(".");
			}
			
			return sb.toString();
		}
		
		return "";
	}
	
	public static void main(String args[]) {
		System.out.println(NameUtilityImproved.convertNameToInitials("Bruno Mars"));
		System.out.println(NameUtilityImproved.convertNameToInitials("Dave M Jones"));
		System.out.println(NameUtilityImproved.convertNameToInitials("MichaelSmith"));
	}

}
