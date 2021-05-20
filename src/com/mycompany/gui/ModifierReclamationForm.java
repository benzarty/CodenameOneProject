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
import com.mycompany.entities.Reclamation;
import com.mycompany.services.ServiceReclamation;

/**
 *
 * @author lenovo
 */
public class ModifierReclamationForm extends BaseForm {
    
    Form current;
    public ModifierReclamationForm (Resources res , Reclamation r)
    {
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("AjouterHotel");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        TextField Title = new TextField(r.getTitle(),"Title",20, TextField.ANY);
        TextField Recl = new TextField(String.valueOf(r.getRecl()),"Reclamation",20, TextField.ANY);
        
        
       
        
        Title.setUIID("NewsTopLine");
        Recl.setUIID("NewsTopLine");

        
        
        
        Title.setSingleLineTextArea(true);
        Recl.setSingleLineTextArea(true);   
    
        
        Button btnModifier = new Button("Modifier");
        
        
        //onclique modifier
        
        btnModifier.addPointerPressedListener( l ->  {
            r.setTitle(Title.getText());
            r.setRecl(Recl.getText());
          
        
        
        if(ServiceReclamation.getInstance().modifierReclamation(r))
        {   new ListReclamationForm(res).show();  }  }    );
        
        Button btnAnnuller = new Button("Annuler");
        btnAnnuller.addActionListener(e -> {
            new ListReclamationForm(res).show();
            
        }
                
        );
        
      
        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");
        
        
        Label l1 = new Label("");
        
        Container content = BoxLayout.encloseY(
                
                l1,l2,
                new FloatingHint(Title),
                createLineSeparator(),
                new FloatingHint(Recl),
                createLineSeparator(),

               
                l4,l5,
                btnModifier,
                btnAnnuller
        );
        
        add(content);
        show();
        
    }    
}
