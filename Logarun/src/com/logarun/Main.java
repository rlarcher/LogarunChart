package com.logarun;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    
	public static void main(String[] args) {
		TeamUsers users = new TeamUsers();
		List<Runner> runners = users.getRunners();
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
    	try {
    		dBuilder = dbFactory.newDocumentBuilder();
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		return;
    	}
		for(Runner runner : runners) {
		    try {
		    	String url = String.format("http://www.logarun.com/xml.ashx?username=%s&type=view", runner.getUserName());
		    	Document doc = dBuilder.parse(new URL(url).openStream());
		     
		    	//optional, but recommended
		    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		    	doc.getDocumentElement().normalize();
		     		     
		    	// date will be initialized to current time
		    	Date now = new Date();
		    	Calendar nowCalendar = new GregorianCalendar();
		    	nowCalendar.setTime(now);
		    	long DAY_IN_MS = 1000 * 60 * 60 * 24;
		    	Date weekAgo = new Date(now.getTime() - (DAY_IN_MS * 7));
		    			    	
		    	NodeList dateList = doc.getElementsByTagName("dayItem");
		    	double miles = 0;
		    	int dayIndex = dateList.getLength() - 1;
		    	
		    	while(now.compareTo(weekAgo) > 0) {		    		
		    		Node dateNode = dateList.item(dayIndex);
		    		
		    		Element dateElement = (Element)dateNode;
		    		String date = dateElement.getAttribute("date");
		    		String[] dateArray = date.split("/");
		    		for(String d : dateArray) System.out.println(d);
		    		System.out.println(now);
		    		nowCalendar.set(Calendar.MONTH, Integer.parseInt(dateArray[0])-1);
		    		nowCalendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
		    		nowCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[1]));		    		
		    		now = nowCalendar.getTime();

		    		NodeList dayNodes = dateNode.getChildNodes();
		   			for(int i = 0; i < dayNodes.getLength(); i++) {
		   				Node exercise = dayNodes.item(i);
		   				if(exercise.getNodeType() == Node.ELEMENT_NODE) {
		   					Element exerciseElement = (Element)exercise;
		   					if(exerciseElement.getAttribute("exercise").equals("Run")) {
		   					miles += Double.parseDouble(exerciseElement.getAttribute("value1"));
		    					System.out.println(miles + runner.getUserName());
		   					}
		   				}
		    		}
		     
		    		dayIndex--;
		    	}
	    		runner.setMilesThisWeek(miles);
		    }
		    catch(Exception e) {
		    	System.out.println(e.getMessage());
		    }
		}
		MakeChart chart = new MakeChart("Logarun", runners);
		System.out.println("Should have made chart");
		
	}
}
