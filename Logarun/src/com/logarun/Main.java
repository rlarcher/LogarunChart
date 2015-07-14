package com.logarun;

import java.net.URL;
import java.util.List;

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
		for(Runner runner : runners) {
		    try {
		    	String url = String.format("http://www.logarun.com/xml.ashx?username=%s&type=view", runner.getUserName());
		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(new URL(url).openStream());
		     
		    	//optional, but recommended
		    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		    	doc.getDocumentElement().normalize();
		     		     
		    	NodeList nList = doc.getElementsByTagName("item");
		    	double miles = 0;
		    	int temp = nList.getLength() - 1;
		    	int end = nList.getLength() - 8;
		    	while(temp > end) {
		    		
		    		if(temp < 0) break;
		    		Node nNode = nList.item(temp);
		     
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		     
		    			Element eElement = (Element) nNode;
		    			if(eElement.getAttribute("exercise").equals("Run")) {
		    					miles += Double.parseDouble(eElement.getAttribute("value1"));
					    		if((eElement.getAttribute("order").equals("2"))) {
					    			System.out.println("Double by " + runner.getUserName());
					    			end--;
					    		}
			    		}
					    else {
					    	end--;
					    }
		    		}
		    		temp--;
		    		System.out.println(temp + " " + end);
		    	}
		    	runner.setMilesThisWeek(miles);
		        } catch (Exception e) {
		    	e.printStackTrace();
		        }
		}
		MakeChart chart = new MakeChart("Logarun", runners);
		System.out.println("Should have made chart");

	}
}
