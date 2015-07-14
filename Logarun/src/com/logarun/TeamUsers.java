package com.logarun;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.net.URL;


public class TeamUsers {

	public List<Runner> getRunners() {
		List<Runner> runners = new ArrayList<Runner>();
	    try {
	    	String url = "http://www.logarun.com/xml.ashx?type=userddl&teamid=1285";
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(new URL(url).openStream());
	     
	    	//optional, but recommended
	    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	    	doc.getDocumentElement().normalize();
	     
	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	     
	    	NodeList nList = doc.getElementsByTagName("option");
	     
	    	for (int temp = 0; temp < nList.getLength(); temp++) {
	     
	    		Node nNode = nList.item(temp);
	     
	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	     
	    			Element eElement = (Element) nNode;
	     
	    			System.out.println("id : " + eElement.getAttribute("value"));
	    			Runner runner = new Runner(eElement.getAttribute("value"));
	    			runners.add(runner);
	    		}
	    	}
	        } catch (Exception e) {
	    	e.printStackTrace();
	        }
		return runners;
	}
}
