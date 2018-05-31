//This program is used to alter the value in a specific row and column

package com.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class CsvColumnRow {

	public static void main(String[] args) throws IOException {
		
		File inputFile = new File("Sample.csv");
		CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
		List<String[]> csvBody = reader.readAll();
		csvBody.get(0)[1] = "akil";
		reader.close();

		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
		
		

	}

}