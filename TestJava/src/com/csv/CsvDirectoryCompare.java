package com.csv;

//This Program is used to compare the CSV file and the Database(Ping Directory) and update the CSV file respectively. 
//It uses the CsvDto class as POJO
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import au.com.bytecode.opencsv.CSVWriter;

public class CsvDirectoryCompare {
	static DirContext ldapContext;



	public static void main(String[] args) throws IOException {

		try
		{
			Hashtable<String, String> ldapkey = new Hashtable<String, String>(11);//creating a obj to access LDAP

			ldapkey.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); 
			ldapkey.put(Context.PROVIDER_URL, "ldap://localhost:389");
			ldapkey.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapkey.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
			ldapkey.put(Context.SECURITY_CREDENTIALS, "Capgemini#123");
			ldapContext = new InitialDirContext(ldapkey);  //Adding the ldapkey object to ldapContext

			SearchControls searchCtl = new SearchControls(); //Creating a obj for Search Control
			/*String returnedAtts[]={"uid"};                    //the attributes to be returned from LDAP
              searchCtl.setReturningAttributes(returnedAtts);*/
			searchCtl.setSearchScope(SearchControls.SUBTREE_SCOPE); //The Scope to Search 
			//(The SUBTREE_SCOPE method defines the search method for all entries starting from the named object and all descendants below the named object.)

			String searchBase = "dc=example,dc=com"; //Setting the base value to search

			BufferedReader readCsv = new BufferedReader(new FileReader("Sample.csv")); //reading a csv file
			ArrayList<CsvDto> empList = new ArrayList<CsvDto>();  //Creating a array list to add the csv data

			String empData=null;
			readCsv.readLine();
			while((empData=readCsv.readLine())!=null) //if there is data in csv
			{
				String[] entries=empData.split(","); //create a array of strings using delimiter to separate strings
				CsvDto employee=new CsvDto();  //creating a obj for employee class (POJO)
				employee.setId(entries[0]);        //Set the ID value from the array
				empList.add(employee);             //add the POJO obj value to the Array list
				System.out.println("Emp ID from CSV : " +employee.getId()); //printing the id
			}
			readCsv.close();





			for(CsvDto emp : empList) 
			{

				String searchFilter = "(&(objectClass=inetOrgPerson)(uid=" + emp.getId() + "))";   //search filter for LDAP
				System.out.println("SearchFilter :" +searchFilter);
				NamingEnumeration<SearchResult> answer = null;
				answer = ldapContext.search(searchBase, searchFilter, searchCtl);
				//System.out.println(answer);
				while (answer.hasMoreElements())
				{
					SearchResult sr = (SearchResult)answer.next();

					javax.naming.directory.Attributes attrs = sr.getAttributes();

					String dn =sr.getNameInNamespace();
					String uid = attrs.get("uid").toString();

					System.out.println("Emp ID LDAP= "+uid);
					System.out.println("DN value= "+dn);
					emp.setDn(dn);
				}
			}
			ldapContext.close();


			File inputFile = new File("Sample.csv");
			CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');
			writer.writeNext(new String[] {"EMP","DN"});
			for(CsvDto emp : empList) 
			{

				writer.writeNext(new String[] {emp.getId(),emp.getDn()}); 

			}
			writer.flush();
			writer.close();
		}

		catch (Exception e)
		{
			System.out.println(" Search error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
