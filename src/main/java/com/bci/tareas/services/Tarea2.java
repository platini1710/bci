package com.bci.tareas.services;

import java.util.ArrayList;
import java.util.List;

public class Tarea2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> listString = new ArrayList<>();
		
		listString.add("as");
		listString.add("vb");
		listString.add("ss");
		listString
		  .stream()
		  .filter(c -> c=="as").forEach(System.out::println);;
		
	}

}
