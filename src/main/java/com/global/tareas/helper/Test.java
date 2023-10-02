package com.global.tareas.helper;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="AAAa9";
		System.out.println( s!= null &&   s.chars().allMatch(Character::isLetter));
	}

}
