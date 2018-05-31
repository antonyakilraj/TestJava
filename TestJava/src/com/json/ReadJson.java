//This program is used to read a json file.
package com.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadJson {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("sample.json"));
            
            JSONArray jsonObjects = (JSONArray) obj;
            System.out.println(jsonObjects);

    
            
            JSONArray jsonarray = (JSONArray)obj;

            for (int i=0; i<jsonarray.size(); i++) {

            JSONObject jsonObject= (JSONObject)jsonarray.get(i);
            String id = (String) jsonObject.get("Emp_Id");
            System.out.println(id);
            String age = (String) jsonObject.get("Salary");
            System.out.println(age);
            String name = (String) jsonObject.get("Name");
            System.out.println(name);

 
            }
        }

         catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
