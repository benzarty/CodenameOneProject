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
import com.mycompany.entities.Formation;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.db.Database;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

/**
 *
 * @author lenovo
 */
public class ServiceHotel {

  public Database db;
    public static ServiceHotel instance = null;

    public static boolean resultOK = true;
    ArrayList resultpanier = new ArrayList();
   
    //Initialisation connection request
    private ConnectionRequest req;
    
    //singleton
    //public static ServiceHotel /instance = null;
    public static boolean resultok = true;
    
  
    
    
    
    public static ServiceHotel getInstance()
    {
        if(instance == null)
            instance = new ServiceHotel();
        return instance;
    }
    
    public ServiceHotel()
            {
                req = new ConnectionRequest();
            }
            
    public void ajouterform(Formation formation)
    {
        String url =Statics.BASE_URL+"/AddformationJSON/new?intitule="+formation.getIntitule()+"&volumehoraire="+formation.getVolumehoraire()+"&modeEnseignement="+formation.getModeEnseignement()+"&langue="+formation.getLangue();
          
        req.setUrl(url);
        req.addExceptionListener((e) -> {
            
            String str = new String(req.getResponseData());  //reponse json dans le navigateur
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution du request
        
        
    
    }
    
    public ArrayList<Formation>affichageHotel()
    {
        ArrayList<Formation>result = new ArrayList<>();
        String url =Statics.BASE_URL+"/AfficheJSON";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try
                {
                    Map<String,Object>mapHotels = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapHotels.get("root");
                    
                    for(Map<String,Object> obj : listofMaps){
                        Formation ho = new Formation();
                        
                        float idFormation = Float.parseFloat(obj.get("idFormation").toString());
                        
                        //String photo = obj.get("photo").toString();
                        String intitule = obj.get("intitule").toString();
                       // int volumeHoraire = Integer.parseInt(obj.get("volumeHoraire"));
                        float volumeHoraire = Float.parseFloat(obj.get("volumeHoraire").toString());
                        String langue = obj.get("langue").toString();
                        String modeEnseignement = obj.get("modeEnseignement").toString();
                        //String test = obj.get("test").toString();
                        
                        ho.setIdform((int)idFormation);
                        ho.setIntitule(intitule);
                        ho.setVolumehoraire((int)volumeHoraire);
                        ho.setLangue(langue);
                        ho.setModeEnseignement(modeEnseignement);
                       // ho.setAdresse(Adresse);
                        //ho.setDescription(Description);
                        
                        result.add(ho);
                    }
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution du request
        
        return result;
    }
    
    public boolean deleteHotel(int id)
    {
        String url = Statics.BASE_URL+"/deleteformJSON/"+id;   

        
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
                return resultok;
    }
    
     //UPDATE
    public boolean modifierformm(Formation formation) {

        String url = Statics.BASE_URL + "/updateformJSON/" + formation.getIdform() + "?intitule="+formation.getIntitule()+"&volumehoraire="+formation.getVolumehoraire()+"&modeEnseignement="+formation.getModeEnseignement()+"&langue="+formation.getLangue();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                resultOK = req.getResponseCode() == 200; //code 200 si c bon
                req.removeResponseListener(this);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
   /* public boolean modifierHotel (Formation formation)
    {
              //String url =Statics.BASE_URL+"/addhotel?Nomhotel="+hotel.getNomhotel()+"&SiteWeb="+hotel.getSiteWeb()+"&Email="+hotel.getEmail()+"&Adresse="+hotel.getAdresse()+"&Telephone="+hotel.getTelephone()+"&Description="+hotel.getDescription();
        String url =Statics.BASE_URL+"/AddformationJSON/new?intitule="+formation.getIntitule()+"&volumehoraire="+formation.getVolumehoraire()+"&modeEnseignement="+formation.getModeEnseignement()+"&langue="+formation.getLangue();
             

 req.setUrl(url);
              
              req.addResponseListener(new ActionListener<NetworkEvent>() {
                  @Override
                  public void actionPerformed(NetworkEvent evt) {
                      resultok = req.getResponseCode() == 200; //code response http
                      req.removeResponseListener(this);
                  }
              });
              
                NetworkManager.getInstance().addToQueueAndWait(req);
                return resultok;
    }*/
    
}