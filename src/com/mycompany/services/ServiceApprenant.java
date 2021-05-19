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
import com.mycompany.entities.Users;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Benzarti
 */
public class ServiceApprenant {
    
    public static ServiceApprenant instance=null;
    public static boolean resulatok=true;
    private ConnectionRequest req;
    
    
    public static ServiceApprenant getInstance()
    { if(instance == null)
            instance = new ServiceApprenant();
            return instance;
        
    }
    
    
    public ServiceApprenant()
    {
        req=new ConnectionRequest();
        
    }
    
    public void AjouterAppprenant(Users users)
    {
        String url=Statics.BASE_URL+"/AddApprenantJson?nom="+users.getNom()+"&prenom="+users.getPrenom()+"&photo="+users.getPhoto()+"&email="+users.getEmail()+"&password="+users.getPassword();        
        req.setUrl(url);
        req.addResponseListener((e)->{
        
        String str=new String(req.getResponseData());
                
              System.out.println("data =="+str);
                
                
                });
        
            NetworkManager.getInstance().addToQueueAndWait(req); 
    }
    
    
    
    public ArrayList<Users>affichageApprenant()
    {
        ArrayList<Users> result=new ArrayList<>();
        String url=Statics.BASE_URL+"/AfficheApprenantJson";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser Jsonp;
                Jsonp=new JSONParser();
                try{
                    Map<String,Object>mapApprenant= Jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapApprenant.get("root");
                    
                    
                    for(Map<String,Object> obj : listofMaps)
                    {
                        Users re=new Users();
                        
                        float id=Float.parseFloat(obj.get("id").toString());
                        
                        String nom=obj.get("nom").toString();
                        String prenom=obj.get("prenom").toString();
                        
                        String photo=obj.get("photo").toString();
                        String email=obj.get("email").toString();
                        
                        String password=obj.get("password").toString();
                        
                        
                        re.setId((int) id);
                        re.setNom(nom);
                        re.setPrenom(prenom);
                        re.setPhoto(photo);
                        re.setEmail(email);
                        re.setPassword(password);
                        
                        result.add(re);
                        
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
    
    
    
    
    public Users DetailApprenant(int id,Users users)
    {
        
        String url=Statics.BASE_URL+"/DetailApprenantJson?"+id;
        req.setUrl(url);
         String sr=new String(req.getResponseData());
        
        req.addResponseListener((evt)->{
            
            JSONParser jSONParser=new JSONParser();
            try {
          Map<String,Object>obj= jSONParser.parseJSON(new CharArrayReader(new String(sr).toCharArray()));

          users.setNom(obj.get("nom").toString());
          users.setPrenom(obj.get("prenom").toString());

          users.setPhoto(obj.get("photo").toString());
          users.setEmail(obj.get("email").toString());

          users.setPassword(obj.get("password").toString());

          
          
          
          
            } catch (IOException e) {
                System.out.println("error related to sql"+e.getMessage());
            }
            System.out.println("date "+sr);

           
        });
                  NetworkManager.getInstance().addToQueueAndWait(req); 
                  return users;

    }
    
    
    public boolean deleteApprenant(int id)
    {
        String url=Statics.BASE_URL + "/DeleteApprenantJson?id="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resulatok;
        
    }
    
    
    public boolean modifierApprenant(Users users)
    {
        String url=Statics.BASE_URL+"/UpdateApprenantJson?id="+users.getId()+"&nom="+users.getNom()+"&prenom="+users.getPrenom()+"&photo="+users.getPhoto()+"&email="+users.getEmail()+"&password="+users.getPassword();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                resulatok=req.getResponseCode() == 200 ;
                req.removeResponseListener(this);
            }
        });
        
        
                NetworkManager.getInstance().addToQueueAndWait(req);
                return resulatok;

        
    }
    
    
    
    
}
