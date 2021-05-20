/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;


import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.notifications.LocalNotification;
import com.codename1.notifications.LocalNotificationCallback;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reclamation;
import com.mycompany.services.ServiceReclamation;

/**
 *
 * @author ASUS
 */
public class AjoutReclamationForm extends BaseForm implements LocalNotificationCallback{
    
    Form current;
    public AjoutReclamationForm(Resources res){
    super("Newsfeed",BoxLayout.y());
        Toolbar tb=new Toolbar(true);
        current=this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand((e)->{});
        
        Tabs swipe=new Tabs();
        Label s1= new Label();
        Label s2= new Label();
        
       
        
        TextField title=new TextField("","enter Title");
        title.setUIID("TextFieldBlack");
        addStringValue("Title",title);
        
        TextField description =new TextField("","enter description");
        description.setUIID("TextFieldBlack");
        addStringValue("Description",description);
        
        Button btnAjouter=new Button("Ajouter");
        addStringValue("",btnAjouter);
        
        btnAjouter.addActionListener((ActionEvent e)->{
            
        LocalNotification n = new LocalNotification();
n.setId("reclamation a ete ajouter avec succes");
n.setAlertBody("votre reclamation a ete ajouter avec succes");
n.setAlertTitle("reclamation");
localNotificationReceived("reclamation");

Display.getInstance().scheduleLocalNotification( n, System.currentTimeMillis() , LocalNotification.REPEAT_MINUTE ); // Whether to repeat and what frequency

        
    try{  if(title.getText().equals("") || description.getText().equals("")){  Dialog.show("Veuillez verifier les donnees","", "Annuler", "OK");} 
    else{ InfiniteProgress ip=new InfiniteProgress();
                
    final Dialog iDialog =ip.showInfiniteBlocking();
    Reclamation r=new Reclamation(String.valueOf(title.getText()).toString(),String.valueOf(description.getText()).toString());
    System.out.println("data reclamation=="+r);
    ServiceReclamation.getInstance().ajouterReclamation(r);
    iDialog.dispose();
    new ListReclamationForm(res).show();  refreshTheme(); }
    } catch(Exception ex){  ex.printStackTrace();   } });   
    
    
               Container cnt = new Container(BoxLayout.y());
        Button btnAnnuler=new Button("Annuler");
        btnAnnuler.addPointerPressedListener(e->{  new ListReclamationForm(res).show();   });
        cnt.addAll(btnAnnuler);
        super.add(cnt);
            
    
    }

    
    
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }
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
    
    public void bindButtonSelection(Button btn, Label l){
    btn.addActionListener(e->{
    if(btn.isSelected()){
    updateArrowPosition(btn,l);
    }
    
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX()+btn.getWidth()/2-l.getWidth()/2);
    }

    @Override
    public void localNotificationReceived(String notificationId) {
        System.out.println("Received local notification "+notificationId+" in callback localNotificationReceived");
    }

   
    
}
