/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reclamation;
import com.mycompany.entities.Users;
import com.mycompany.services.ServiceReclamation;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class ListReclamationForm extends BaseForm{
    
    Form current;
    
    
    
    public ListReclamationForm(Resources res){
        super("Newsfeed",BoxLayout.y());
        Toolbar tb=new Toolbar(true);
        current=this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("");
        getContentPane().setScrollVisible(false); 
        tb.addSearchCommand((e)->{ });  
        
        Tabs swipe=new Tabs();    Label s1= new Label();    Label s2= new Label();
        
   
    Container cnt = new Container(BoxLayout.y());
    Button btnAddRecs = new Button("Ajouter une réclamation");
    btnAddRecs.addActionListener(e -> new AjoutReclamationForm(res).show());
    cnt.addAll(btnAddRecs); super.add(cnt);
    
    
        super.addSideMenu(res);
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton BR = RadioButton.createToggle("Boite Reception", barGroup);
        BR.setUIID("SelectBar");
        RadioButton Corbeille = RadioButton.createToggle("Corbeille", barGroup);
        Corbeille.setUIID("SelectBar");
        RadioButton Archive = RadioButton.createToggle("Archive", barGroup);
        Archive.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        BR.addActionListener(e -> new ListReclamationForm(res).show());
        Corbeille.addActionListener(e -> new ListCorbeilleForm(res).show());
        Archive.addActionListener(e -> new ListArchiveForm(res).show());

        add(LayeredLayout.encloseIn(GridLayout.encloseIn(3, BR, Corbeille, Archive),FlowLayout.encloseBottom(arrow)));
        BR.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> { arrow.setVisible(true); updateArrowPosition(BR, arrow); });
        bindButtonSelection(BR, arrow);
        bindButtonSelection(Corbeille, arrow);
        bindButtonSelection(Archive, arrow);
        // special case for rotation
        addOrientationListener(e -> { updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow); });
        
            
        ArrayList<Reclamation>list = ServiceReclamation.getInstance().affichageReclamation(42);
        for (Reclamation rec : list) {  addButton(rec,res);  } }
    
     
    
    private void addButton(Reclamation rec,Resources res) {
        
       Form fy =  new Form("FormY", BoxLayout.y());
       fy.setScrollable(false);
       
       SpanLabel titre=new SpanLabel("Title : "+rec.getTitle(),"NewsTopLine2");
       Label etat=new Label("Etat : "+rec.getEtat(),"NewsTopLine2");

        Label lSupprimer=new Label("Supprimer");
        lSupprimer.setUIID("NewsTopLine");
        Style supprimerStyle=new Style(lSupprimer.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        
        FontImage supprimerImage=FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        lSupprimer.setIcon(supprimerImage);
        lSupprimer.setTextPosition(RIGHT); 
        lSupprimer.addPointerPressedListener(l -> { Dialog dig=new Dialog("Suppression"); 
        if(dig.show("Suppression","Vous voullez mettre cette reclamation dans la corbeille?","Annuler","Oui")){    dig.dispose();      }
        else{  dig.dispose(); if(ServiceReclamation.getInstance().corbeilleReclamation(rec.getId())){ new ListReclamationForm(res).show(); }}   });
        
        
        Label lModifier=new Label("Modifier ");
        lModifier.setUIID("NewsTopLine");
        Style modifierStyle=new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage=FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(RIGHT);  
        lModifier.addPointerPressedListener(l -> { new ModifierReclamationForm(res,rec).show();    });
          
        
        Label lArchiver=new Label("Archiver");
        lArchiver.setUIID("NewsTopLine");
        Style archiverStyle=new Style(lArchiver.getUnselectedStyle());
        archiverStyle.setFgColor(0x333399);
        
        FontImage archiverImage=FontImage.createMaterial(FontImage.MATERIAL_ARCHIVE, archiverStyle);
        lArchiver.setIcon(archiverImage);
        lArchiver.setTextPosition(RIGHT); 
        lArchiver.addPointerPressedListener(l -> { Dialog dig=new Dialog("archiver"); 
        if(dig.show("archiver","Vous voullez archiver cette reclamation ?","Annuler","Oui")){    dig.dispose();      }
        else{  dig.dispose(); if(ServiceReclamation.getInstance().archiverReclamation(rec.getId())){ new ListReclamationForm(res).show(); }}   });
          
       // f.add(GridLayout.encloseIn(titre,etat,lModifier,lSupprimer,lArchiver)); add(f);
        fy.addAll(titre,etat,lModifier,lSupprimer,lArchiver);  add(fy);
       // f.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(titre),BoxLayout.encloseX(etat),BoxLayout.encloseX(lModifier,lSupprimer,lArchiver)));
       // add(f);
        
    }

    
    
         private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel")).add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee)); } 

    
    public void bindButtonSelection(Button btn, Label l){  btn.addActionListener(e->{ if(btn.isSelected()){ updateArrowPosition(btn,l);}  }); }
    
    private void updateArrowPosition(Button btn, Label l) {
  l.getUnselectedStyle().setMargin(LEFT, btn.getX()+btn.getWidth()/2-l.getWidth()/2);  l.getParent().repaint();  }
  
    
}


    /* if(rec.getAnswer().equals("")){
           etatTxLabel.setText("non traitée");
       }else{
           etatTxLabel.setText("traitée");
       }  */



/*
    private void addTab(Tabs swipe,Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight()<size){
            image = image.scaledHeight(size);
        }
        if(image.getHeight()>Display.getInstance().getDisplayHeight()/2){
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() /2);
        }
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay=new Label("","ImageOverlay");
        
        Container page1=LayeredLayout.encloseIn(imageScale,overLay,BorderLayout.south(BoxLayout.encloseY(new SpanLabel(text,"LargeWhiteText"),FlowLayout.encloseIn(null),spacer)));
        swipe.addTab("",res.getImage("back-logo.jpeg"),page1);
    }
*/