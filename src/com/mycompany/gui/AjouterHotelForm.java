/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;


import com.codename1.l10n.SimpleDateFormat;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Formation;
import com.mycompany.entities.Hotel;
import com.mycompany.services.ServiceHotel;


import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Properties;
import javax.mail.Message;


import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author lenovo
 */
public class AjouterHotelForm extends BaseForm {
    
    
    Form current;
    public AjouterHotelForm(Resources res)
    {
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("AjouterHotel");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e -> {
            
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1,res.getImage("image.jpg"),"","",res);
        
        //
          swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Les hotels", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Reclamer", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
         new ListHotelForm(res).show();
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

       
        
        //
        
        //Nomhotell
        TextField Nomhotel = new TextField("", "enter inttitule");
        Nomhotel.setUIID("TextFieldBlack");
        addStringValue("Intitule",Nomhotel);
        


        //SiteWeb
         TextField SiteWeb = new TextField("", "enter volumehoraire");
        SiteWeb.setUIID("TextFieldBlack");
        addStringValue("volumehoraire",SiteWeb);
        
        
        //Email
         TextField Email = new TextField("", "enter langue");
        Email.setUIID("TextFieldBlack");
        addStringValue("Langue",Email);
        
        
        //Telephone
         TextField Telephone = new TextField("", "enter modeEnseignement");
        Telephone.setUIID("TextFieldBlack");
        addStringValue("modeEnseignement",Telephone);
        
        
        
        
        Button btnAjouter = new Button("Ajouter ");
        addStringValue("", btnAjouter);
        
        //onclick button event
        
        btnAjouter.addActionListener((e) -> {
            try
            {
                if (Nomhotel.getText().equals("")|| SiteWeb.getText().equals("")|| Email.getText().equals("")  || Telephone.getText().equals("") )
                {                   // sendMail();

                    Dialog.show("verifier les donnes","" ,"Annuler","OK");
                }
                
                else 
                {
                        InfiniteProgress ip = new InfiniteProgress(); //loading after insert data
                        final Dialog iDialog = ip.showInfiniteBlocking();
                        
                        Formation h = new Formation(String.valueOf(Nomhotel.getText()).toString(),
                        
                                Integer.valueOf(SiteWeb.getText()),
                                String.valueOf(Email.getText()).toString(),
                                String.valueOf(Telephone.getText()).toString()
                          
                                            );
                        System.out.println("data   == "+h);
                                
                                ServiceHotel.getInstance().ajouterform(h);
                                
                                iDialog.dispose();  //pour annuler le loading after ajouter
                                
                                new ListHotelForm(res).show();
                                
                                refreshTheme();
               
                }
            }catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        });
        sendMail();
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"paddedLabel"))
        .add(BorderLayout.CENTER,v) );
        add(createLineSeparator(0xeeeeee));
        
       
        
        
    }

    private void addTab(Tabs swipe,Label spacer  ,Image image, String string, String text, Resources res) {
     int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
     
     if (image.getHeight() < size)
     {
         image = image.scaledHeight(size);
     }
     
     
     
     if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2)
     {
         image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
     }
     
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        
        imageScale.setUIID("container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","Imageoverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargewhiteText"),
                                        //FlowLayout.encloseIn(null),
                                        spacer
                                        
                                        
                                )
                        )
                );
        
        swipe.addTab("", res.getImage("image.jpg"),page1);
    }
    
    
    public void bindButtonSelection(Button btn, Label l)
    {
        btn.addActionListener(e -> {
        if(btn.isSelected()) {
         updateArrowPosition(btn,l); 
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX()+ btn.getWidth() /2 - l.getWidth() / 2);
        l.getParent().repaint();
    }
    
    
     public void sendMail()
    {
        try {
            Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp"); //SMTP Host
                props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication


                Session session = Session.getInstance(props,null);
                
               MimeMessage msg = new MimeMessage( session);
                
                msg.setFrom(new InternetAddress("malek.ayadi@esprit.tn"));
                msg.setRecipients(Message.RecipientType.TO,"m.benzarti.1996@gmail.com");
                msg.setSubject("traveminers");
               String txt = "salut mohamed ";
                msg.setText(txt);
                String mp = "";
                //String txt = "salut";
                
                SMTPTransport st = (SMTPTransport)session.getTransport("smtps");
                st.connect("smtp.gmail.com",465,"m.benzarti.1996@gmail.com","redtube96");
                st.sendMessage(msg, msg.getAllRecipients());
                
                System.out.println("com.mycompany.gui.AjouterHotelForm.sendMail()");
                
                
                
        }catch(Exception e)
                {
                  e.printStackTrace();
                  
                }
    }
    
    
}