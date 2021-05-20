/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Users;
import com.mycompany.services.ServiceApprenant;

import java.util.ArrayList;

import java.util.HashMap;

/**
 *
 * @author ahmed
 */
public class StatForm extends Form {
     Form current ; 

       public StatForm(Form previous) { 
        current=this;
     setTitle("Stats");
                setLayout(BoxLayout.y());
      this.add(createPieChartForm());
      
     
    }
private DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(30);
    renderer.setLegendTextSize(30);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}

/**
 * Builds a category series using the provided values.
 *
 * @param titles the series titles
 * @param values the values
 * @return the category series
 */
protected CategorySeries buildCategoryDataset(String title, HashMap<String, Integer> values) {
    
    
    
    
    
    
    CategorySeries series = new CategorySeries(title);
   series.add("apprenant", values.get("apprenant"));
        series.add("professeur", values.get("professeur"));
        series.add("admin", values.get("admin"));
    

    return series;
}

public Form createPieChartForm() {
      int apprenant = 0;
    int professeur = 0;
    int admin = 0;
    ArrayList<Users> th = ServiceApprenant.getInstance().AfficheAllUsers();
    for (int i=0;i<th.size();i++)
    {
        switch (th.get(i).getRole().toLowerCase()) {
            case "apprenant":
                apprenant++;
                break;
            case "professeur":
                professeur++;
                break;
            default:
                admin++;
                break;
        }
    }
     HashMap<String, Integer> values = new HashMap<>();
    values.put("apprenant", apprenant);
    values.put("professeur", professeur);
    values.put("admin", admin);

    // Set up the renderer
  int[] colors = new int[]{ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.LTGRAY};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(30);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    renderer.setLabelsColor(ColorUtil.BLACK);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);

    // Create a form and show it.
    Form f = new Form("Budget", new BorderLayout());

    f.add(BorderLayout.CENTER, c);
    f.show();
    return f;

}
}
