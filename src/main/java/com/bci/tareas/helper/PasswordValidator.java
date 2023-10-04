package com.bci.tareas.helper;

import java.util.regex.Pattern;

public class PasswordValidator {

	public static boolean isValid(String password) {
		 final int min=8;
         
		 final int max=12;
         
		// especifica  el numero de letras con uppercase 


		             final int MIN_Uppercase=1;
		             // Specifying the minimum lowercase letters in password
		             final int MIN_Lowercase=1;
		             // Specifying the number of digits in a password
		             final int NUM_Digits=1;
		             // Specify the minimum number of special case letters
		             final int Special=1;
		             // Count number of uppercase letters in a password
		             int uppercaseCounter=0;
		             // Counter lowercase letters in a password
		             int lowercaseCounter=0;
		             // Count digits in a password
		             int digitCounter=0;
		             // count special case letters in a password
		             int specialCounter=0;
        for (int i=0; i < password.length(); i++ ) {
            char c = password.charAt(i);
            if(Character.isUpperCase(c)) 
                  uppercaseCounter++;
            else if(Character.isLowerCase(c)) 
                  lowercaseCounter++;
            else if(Character.isDigit(c)) 
                  digitCounter++;     
             if(c>=33&&c<=46||c==64){
              specialCounter++;
          }
            
     }



		if (password.length() >= min && password.length() <= max && uppercaseCounter >= MIN_Uppercase 
				&& lowercaseCounter >= MIN_Lowercase && digitCounter >= NUM_Digits && specialCounter >= Special) {
			return true;
		} else
			return false;
	}

	

}