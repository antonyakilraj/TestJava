package com.csv;

public class CsvDto {

	String id;
	String name;
	private String na=("na");
	String dn=na;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	
	@Override
	public String toString() {
		return "Employee [Id=" + id + ", Name=" + name + "]";
	}
	
	
	
}
