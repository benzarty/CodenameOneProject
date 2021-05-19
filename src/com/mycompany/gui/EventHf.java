/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Evenement;
import com.mycompany.services.ServiceEvenement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author gicke
 */
public class EventHf extends BaseForm{

    public EventHf(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
        TextField theme = new TextField("", "Theme", 20, TextField.ANY);
        TextField presentateur = new TextField("", "Presentateur", 20, TextField.ANY);
        TextField lien = new TextField("", "Lien", 20, TextField.URL);
        Picker date = new Picker();
        
        
       
        theme.setSingleLineTextArea(false);
        presentateur.setSingleLineTextArea(false);
        lien.setSingleLineTextArea(false);

        Button Enregistrer = new Button("Enregistrer");
  
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Ajouter Event", "LogoLabel"),
                new FloatingHint(theme),
                createLineSeparator(),
                new FloatingHint(presentateur),
                createLineSeparator(),
                new FloatingHint(lien),
                createLineSeparator(),
                date
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                Enregistrer
        ));
        Enregistrer.requestFocus();
        Enregistrer.addActionListener(e -> 
        
        {
           
            String elien = lien.getText();
            String etheme = theme.getText();
            String epresentateur = presentateur.getText();
            String edate = date.getDate().toString();
            Date rdate = date.getDate();
             ToastBar.showMessage(elien+" \n"
                     + etheme+"\n"
                             + epresentateur+"\n"
                     + edate, FontImage.MATERIAL_INFO);
             
             System.out.println(edate);
             
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   
            String fsdate = dateFormat.format(rdate); 
            
            System.out.println("la date convertie"+fsdate);
            
            Evenement evt =  new Evenement(elien, etheme, epresentateur, fsdate);

            ServiceEvenement service = new ServiceEvenement();
            
            Boolean b = service.AjouterEvnement(evt);
            System.out.println(evt);
            if(b){
                ToastBar.showInfoMessage("Nguin Nguin !! L'Evenement a été ajouté");
            }
            else{
                ToastBar.showErrorMessage("Oups ! Une erreur s'est produite !");
            }
            

                    
        }
        
        );
    }
            
            
    }
    


