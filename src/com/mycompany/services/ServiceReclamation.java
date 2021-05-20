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
import com.mycompany.entities.Reclamation;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author ASUS
 */
public class ServiceReclamation {
    
    public static ServiceReclamation instance=null;
    public static boolean resulatok=true;
    private ConnectionRequest req;
    public ArrayList<Reclamation> recs;
    public boolean resultOK;

    
    
    public static ServiceReclamation getInstance()  { if(instance == null)  instance = new ServiceReclamation();  return instance;      }
    
    public ServiceReclamation() { req=new ConnectionRequest();    }
    
    
    
   public void ajouterReclamation(Reclamation reclamation){
        String url=Statics.BASE_URL+"/AddRUJson?title="+reclamation.getTitle()+"&recl="+reclamation.getRecl();
        req.setUrl(url);
        req.addResponseListener((e) -> {
        String str = new String(req.getResponseData());
        System.out.println("data == "+str);   });
        NetworkManager.getInstance().addToQueueAndWait(req);   }
    
    
   
    public ArrayList<Reclamation> affichageReclamation(int id)
    {
        ArrayList<Reclamation> result=new ArrayList<>();
        String url=Statics.BASE_URL+"/RUjson/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json =new JSONParser();
                try{
                     Map<String, Object> mapReclamations = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listofMaps = (List<Map<String, Object>>) mapReclamations.get("root");
                    
                    
                    for(Map<String,Object> obj : listofMaps)
                    {
                        Reclamation re=new Reclamation();
                        
                        float id=Float.parseFloat(obj.get("id").toString());                      
                        String title=obj.get("title").toString();
                        String etat=obj.get("etat").toString();
                        String date=obj.get("date").toString();
                        String recl=obj.get("recl").toString();
                        
                             
                        re.setId((int) id);
                        re.setTitle(title);
                        re.setEtat(etat);
                        re.setDate(date);   
                        re.setRecl(recl);   
                        result.add(re);                      
                    }                   
                } catch (IOException ex) {System.out.println("good");       }
            }   });
                    NetworkManager.getInstance().addToQueueAndWait(req); 
                    return result;         }
    
    
    
    
    public Reclamation DetailReclamation(int id,Reclamation r)
    {
        
        String url=Statics.BASE_URL+"/MessagerieRUJson?"+id;
        req.setUrl(url);
         String sr=new String(req.getResponseData());
        
        req.addResponseListener((evt)->{    JSONParser jSONParser=new JSONParser();
           
        try {
          Map<String,Object>obj= jSONParser.parseJSON(new CharArrayReader(new String(sr).toCharArray()));

          r.setTitle(obj.get("title").toString());
          r.setRecl(obj.get("recl").toString());
         
            } catch (IOException e) { System.out.println("error related to sql"+e.getMessage()); }
            System.out.println("date "+sr);      
            });
                  NetworkManager.getInstance().addToQueueAndWait(req); 
                  return r;
    }
    
    
    
    public boolean deleteReclamation(int id)
    {
        String url=Statics.BASE_URL + "/DeleteRUJson?id="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {  req.removeResponseCodeListener(this);   } });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resulatok;      
    }
    
    
    
    public boolean modifierReclamation(Reclamation r)
    {
        String url=Statics.BASE_URL+"/UpdateRUJson?id="+r.getId()+"&recl="+r.getRecl()+"&title="+r.getTitle();
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
    
    
    
    public ArrayList<Reclamation> affichageRC(int id)
    {
        ArrayList<Reclamation> result=new ArrayList<>();
        String url=Statics.BASE_URL+"/RUCjson/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json=new JSONParser();
                try{
                     Map<String, Object> mapReclamations = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listofMaps = (List<Map<String, Object>>) mapReclamations.get("root");
                            
                    for(Map<String,Object> obj : listofMaps)
                    {  Reclamation re=new Reclamation();
                        
    float id=Float.parseFloat(obj.get("id").toString());                      
    String title=obj.get("title").toString();    String etat=obj.get("etat").toString();
    String date=obj.get("date").toString();      String recl=obj.get("recl").toString();
                                                 
     re.setId((int) id);   re.setTitle(title);  re.setEtat(etat); re.setDate(date);     re.setRecl(recl);     result.add(re);    }     
                    
                } catch (IOException ex) {System.out.println("good");    }
            } }); NetworkManager.getInstance().addToQueueAndWait(req);   return result;    }
    
    
    
       public ArrayList<Reclamation> affichageRA(int id)
    {
        ArrayList<Reclamation> result=new ArrayList<>();
        String url=Statics.BASE_URL+"/RUAjson/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser json;
                json=new JSONParser();
                try{
                     Map<String, Object> mapReclamations = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listofMaps = (List<Map<String, Object>>) mapReclamations.get("root");
                    
                    for(Map<String,Object> obj : listofMaps)
                    {
                        Reclamation re=new Reclamation();
                        
                        float id=Float.parseFloat(obj.get("id").toString());                      
                        String title=obj.get("title").toString();
                        String etat=obj.get("etat").toString();
                        String date=obj.get("date").toString();
                        String recl=obj.get("recl").toString();
                        
                             
                        re.setId((int) id);
                        re.setTitle(title);
                        re.setEtat(etat);
                        re.setDate(date);   
                        re.setRecl(recl);   
                        result.add(re);                      
                    }                   
                } catch (IOException ex) {System.out.println("good");       }
            }   });
                    NetworkManager.getInstance().addToQueueAndWait(req); 
                    return result;         }
    
    
       
       
        
    public boolean corbeilleReclamation(int id)
    {
        String url=Statics.BASE_URL + "/CorbeilleJson?id="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {  req.removeResponseCodeListener(this);   } });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resulatok;      
    }
    
    
    
     
    public boolean archiverReclamation(int id)
    {
        String url=Statics.BASE_URL + "/ArchiverJson?id="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {  req.removeResponseCodeListener(this);   } });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resulatok;      
    }
    
    
    
     
    public boolean restaurerReclamation(int id)
    {
        String url=Statics.BASE_URL + "/RestorerJson?id="+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {  req.removeResponseCodeListener(this);   } });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resulatok;      
    }
    
    
    
}
