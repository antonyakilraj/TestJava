//This program reads the csv and stores it in a collection object and sorts them. Also used to search specific data in the CSV.
package com.csv;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.Scanner;

import java.util.TreeMap;

public class CsvCollectionSort {
	public static void main(String[] args) throws IOException {

		BufferedReader read= new BufferedReader(new FileReader("sample.csv"));
		Map<String, String> employeeMap= new HashMap<String,String>();
		ArrayList<CsvDto> lines = new ArrayList<CsvDto>();
		String line=null;
		while((line=read.readLine())!=null) {
			String[] entries=line.split(",");
			CsvDto employee=new CsvDto();
			employee.setId(entries[0]);
			employee.setName(entries[1]);
			employeeMap.put(entries[0], entries[1]);
			lines.add(employee);
		}
		System.out.println("Displaying Collection");
		for(CsvDto output:lines) {
			System.out.println(output);
		}

		System.out.println("\n");
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter Employee ID: ");
		String eid=sc.next();
		System.out.println(employeeMap.get(eid));
		System.out.println("\n");
		Map<String, String> treeMap = new TreeMap<>(employeeMap);
		System.out.println("Sorted List :\n" + treeMap);
		System.out.println("\n");
		read.close();
		sc.close();
	}
}