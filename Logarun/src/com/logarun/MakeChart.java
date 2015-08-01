package com.logarun;


import java.io.*;

import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

import java.util.List;

public class MakeChart {

	public MakeChart(String title, List<Runner> runners) {;

	      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      for(Runner r : runners) {
	      	dataset.addValue(r.getMilesThisWeek(), r.getUserName(), "Miles");
	      	System.out.println(r.getMilesThisWeek() + r.getUserName());
	      }
	      JFreeChart barChart = ChartFactory.createBarChart(
	         "Mileage", 
	         "Runner", "Miles", 
	         dataset,PlotOrientation.VERTICAL, 
	         true, true, false);
	         
	      int width = 640; /* Width of the image */
	      int height = 480; /* Height of the image */ 
	      File BarChart = new File( "TartanTraining.jpeg" ); 
	      try {
			ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
