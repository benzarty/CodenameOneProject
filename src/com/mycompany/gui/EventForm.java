/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.io.URL;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Evenement;
import com.mycompany.services.ServiceEvenement;
import java.io.IOException;
import java.util.ArrayList;




/**
 *
 * @author gicke
 */
public class EventForm extends BaseForm {
       public EventForm(Resources res) {    
           
                super("Evenements", BoxLayout.y());
                Toolbar tb = new Toolbar(false);
                setToolbar(tb);
                tb.setUIID("Container");
                getTitleArea().setUIID("Container");
                Form previous = Display.getInstance().getCurrent();
                tb.setBackCommand("", e -> previous.showBack());
                setUIID("SignIn");
        
              getStyle().setBgColor(255200100);
              String url = "http://127.0.0.1:8000/uploads";
              Image photo = null;
              
              ArrayList<Evenement> list = ServiceEvenement.getInstance().affichageEvenement();
               EncodedImage enc = null;    
              // Toolbar tb = new Toolbar();
               //tb.getStyle().setBgColor(255);
               setToolbar(tb);
               getTitleArea().setUIID("Container");
               
              // setTitle("E");
 
               getContentPane().setScrollVisible(false);
               
               //super.addSideMenu(res);
               Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
                FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_BALLOT, s);
                FontImage ajouter = FontImage.createMaterial(FontImage.MATERIAL_UPDATE, s);
                tb.addCommandToLeftBar("", icon, (e) -> {
                    Image phot = null;
                    EncodedImage en = null;
                    try {
                        en = EncodedImage.create("/load.png");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                   for (Evenement u : list) {
                   phot = URLImage.createToStorage(en, url+"/"+u.getImage(), url+"/"+u.getImage(), URLImage.RESIZE_SCALE);
                   //System.out.println(url+"/"+u.getImage());
                   this.addButton(phot, u.getTheme(), false, u.getDate(), u.getPresentateur(), u.getLien());
               }             
                    System.out.println("Je dois liste les evenements");
                });
                tb.addCommandToOverflowMenu("Retour", icon, (e) -> new NewsfeedForm(res).show());
                tb.addCommandToRightBar("Ajouter", ajouter, (e) -> new EventHf(res).show());
      
               tb.addSearchCommand(e -> {
                   this.removeAll();
                    Image phot = null;
                    EncodedImage en = null;
                    try {
                        en = EncodedImage.create("/load.png");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    String date="";
                    String chaine = (String)e.getSource();
                    
                    if(chaine.length()>=10){
                    String jour = chaine.substring(0,2);
                    String moi = chaine.substring(3,5);
                    String annee = chaine.substring(6);
                     date = annee+"-"+moi+"-"+jour;
       
                    System.out.println(date);
                   for (Evenement u : list) {
                       if(u.getDate().equals(date)){
                           System.out.println("nguin nguin !!!!!");
                           phot = URLImage.createToStorage(en, url+"/"+u.getImage(), url+"/"+u.getImage(), URLImage.RESIZE_SCALE);
                   //System.out.println(url+"/"+u.getImage());
                           this.addButton(phot, u.getTheme(), false, u.getDate(), u.getPresentateur(), u.getLien());
                           
                       }
                       else{
                            ToastBar.showMessage("Aucun invenement ! \n "
                                    + "Veuillez saisir la date avec oe format 'jj-mm-aaaa'", FontImage.MATERIAL_INFO);
                       }
  
               }
                    }
                   
               
               });
               
               Tabs swipe = new Tabs();
               
               
           try {
               enc = EncodedImage.create("/load.png");
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
           }
               for (Evenement u : list) {
                   photo = URLImage.createToStorage(enc, url+"/"+u.getImage(), url+"/"+u.getImage(), URLImage.RESIZE_SCALE);
                   //System.out.println(url+"/"+u.getImage());
                   this.addButton(photo, u.getTheme(), false, u.getDate(), u.getPresentateur(), u.getLien());
               }  

    }
       
   
    
    public void teste(){
        
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
   /* private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
*///Gestion Evenement DEBUT
    
    private void addButton(Image img, String title, boolean liked, String date, String prensetateur, String lien) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);
       TextArea ta = new TextArea(title);
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);

       Label likes = new Label("Date "+date, "NewsBottomLine");
       likes.setTextPosition(RIGHT);
       if(!liked) {
           FontImage.setMaterialIcon(likes, FontImage.MATERIAL_DATA_USAGE);
       } else {
           Style s = new Style(likes.getUnselectedStyle());
           s.setFgColor(0xff2d55);
           FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_DATA_USAGE, s);
           likes.setIcon(heartImage);
       }
       Label comments = new Label(prensetateur, "NewsBottomLine");
       FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
       
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,
                       BoxLayout.encloseX(likes, comments)
               ));
       
       add(cnt);

       image.addActionListener(e -> {
           
            Dialog dlg = new Dialog("Participer", new BoxLayout(TOP));
           Button retour = new Button("Retour");
           
           
        retour.addActionListener(e3 -> {
            dlg.dispose();
        });
        Button Participer = new Button("Participer");
        Participer.addActionListener(e3 -> {
            Display.getInstance().execute(lien);

        });
        
        dlg.add(retour);
        dlg.add(Participer);
          
           
           dlg.show();
           
           ToastBar.showMessage(title, FontImage.MATERIAL_INFO);
           
               });
       
   }
   
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
}
