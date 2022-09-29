package com.clearcapital.source;

public class NameUtility {
	public static String convertNameToInitials(String name) {
		int firstSpace = name.indexOf(" ");
		int lastSpace = name.lastIndexOf(" ");
		String firstName = "";
		String middleName = "";
		String lastName = "";
		
		if ( firstSpace != -1 ) {
			firstName = name.substring(0, firstSpace).substring(0, 1);
			middleName = name.substring(firstSpace, lastSpace);
		}
		else 
			return name.trim().substring(0, 1) + ".";
		
		if ( lastSpace != -1 )
			lastName = name.substring(lastSpace).trim().substring(0, 1);
		
		if ( middleName == null || middleName.isEmpty() )
			return firstName + "." + lastName + ".";
		else
			return firstName.trim() + "." + middleName.trim() + "." + lastName + ".";
	}
	
	public static void main(String args[]) {
		System.out.println(NameUtility.convertNameToInitials("Bruno Mars"));
		System.out.println(NameUtility.convertNameToInitials("Dave M Jones"));
		System.out.println(NameUtility.convertNameToInitials("MichaelSmith"));
	}
}
