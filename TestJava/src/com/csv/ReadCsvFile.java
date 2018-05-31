//This program is used to read the CSV file.

package com.csv;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadCsvFile {

	public static void main(String[] args) throws IOException {

		FileInputStream file= new FileInputStream("sample.csv");
		int c;
		while((c=file.read())!=-1) {
			System.out.print((char) c);
			
		}
		file.close();
	
	}
	

}

