/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Evenement;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gicke
 */
public class ServiceEvenement {
     public static ServiceEvenement instance=null;
    public static boolean resulatok=true;
    private ConnectionRequest req;
    private Boolean resultOK;
    
    
    
    public static ServiceEvenement getInstance()
    { if(instance == null)
            instance = new ServiceEvenement();
            return instance;
        
    }
    
    
    public ServiceEvenement()
    {
        req=new ConnectionRequest();
        
    }
    
     public ArrayList<Evenement>affichageEvenement()
    {
        ArrayList<Evenement> result=new ArrayList<>();
        String url=Statics.BASE_URL+"/evenement/mobile";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser Jsonp;
                Jsonp=new JSONParser();
                try{
                    Map<String,Object>mapEvenement= Jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapEvenement.get("root");
                    
                    
                    for(Map<String,Object> obj : listofMaps)
                    {
                        Evenement event = new Evenement();
                        
                        Float id= Float.parseFloat(obj.get("idEvenement").toString());
                        
                        String lien =obj.get("lien").toString();
                        String theme =obj.get("theme").toString();
                        
                        String presentateur = obj.get("presentateur").toString();
                        String image = obj.get("image").toString();
                        
                      ///date
                       
        
                        String date= obj.get("dateEvenement").toString().substring(0, 10);
         
                      
                      //date

                        event.setId_evenement(id);
                        event.setLien(lien);
                        event.setPresentateur(presentateur);
                        event.setImage(image);
                        event.setTheme(theme);
                        event.setDate(date);

                        
                        result.add(event);
                        
                    }
                    
                    
                    
                    
                } catch (IOException ex) {
                    System.out.println(
                            "good");
                    
                }
            }
        });
                    NetworkManager.getInstance().addToQueueAndWait(req); 
                    return result;

        
    }
     
     
       public boolean AjouterEvnement(Evenement evt) {
        String url=Statics.BASE_URL+"/evenement/mobileAjouter?lien="+evt.getLien()+"&theme="+evt.getTheme()+"&presentateur="+evt.getPresentateur()+"&date="+evt.getDate();        
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
}
