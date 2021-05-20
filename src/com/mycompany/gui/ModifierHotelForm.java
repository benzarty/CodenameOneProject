/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Formation;
import com.mycompany.entities.Hotel;
import com.mycompany.services.ServiceHotel;

/**
 *
 * @author lenovo
 */
public class ModifierHotelForm extends BaseForm {
    
    Form current;
    public ModifierHotelForm (Resources res , Formation forma)
    {
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("AjouterHotel");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        TextField Nomhotel = new TextField(forma.getIntitule(),"Nomhotel",20, TextField.ANY);
        TextField SiteWeb = new TextField(String.valueOf(forma.getVolumehoraire()),"SiteWeb",20, TextField.ANY);
        TextField Email = new TextField(forma.getLangue(),"Email",20, TextField.ANY);
        TextField Telephone = new TextField(forma.getModeEnseignement(),"Telephone",20, TextField.ANY);
       ;
        
    
        
        
        Nomhotel.setUIID("NewsTopLine");
        SiteWeb.setUIID("NewsTopLine");
        Email.setUIID("NewsTopLine");
        Telephone.setUIID("NewsTopLine");
        
        
        
        Nomhotel.setSingleLineTextArea(true);
        SiteWeb.setSingleLineTextArea(true);
        Email.setSingleLineTextArea(true);
        Telephone.setSingleLineTextArea(true);
    
        
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("button");
        
        
        //onclique modifier
        
        btnModifier.addPointerPressedListener( l ->  {
            forma.setIntitule(Nomhotel.getText());
            forma.setVolumehoraire(Integer.parseInt(SiteWeb.getText()));
            forma.setLangue(Email.getText());
            forma.setModeEnseignement(Telephone.getText());
          
        
        
        if(ServiceHotel.getInstance().modifierformm(forma))
        {
            new ListHotelForm(res).show();
        }
        }
                
        );
        Button btnAnnuller = new Button("Annuler");
        btnAnnuller.addActionListener(e -> {
            new ListHotelForm(res).show();
            
        }
                
        );
        
      
        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");
        
        
        Label l1 = new Label("");
        
        Container content = BoxLayout.encloseY(
                
                l1,l2,
                new FloatingHint(Nomhotel),
                createLineSeparator(),
                new FloatingHint(SiteWeb),
                createLineSeparator(),
                new FloatingHint(Email),
                createLineSeparator(),
                new FloatingHint(Telephone),
                createLineSeparator(),
               
                l4,l5,
                btnModifier,
                btnAnnuller
        );
        
        add(content);
        show();
        
    }    
}