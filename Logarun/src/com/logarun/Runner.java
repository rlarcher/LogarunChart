package com.logarun;

public class Runner {
	
	private String fullName;
	private String userName;
	private double milesThisWeek;
	
	public Runner(String userName) {
		this.userName = userName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getMilesThisWeek() {
		return milesThisWeek;
	}
	public void setMilesThisWeek(double milesThisWeek) {
		this.milesThisWeek = milesThisWeek;
	}
}
