/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author gicke
 */
public class Evenement {
    private Float id_evenement;
    private String lien;
    private String theme;
    private String  presentateur;
    private String  date;
    private String  image;

    public Float getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(Float id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPresentateur() {
        return presentateur;
    }

    public void setPresentateur(String presentateur) {
        this.presentateur = presentateur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Evenement(Float id_evenement, String lien, String theme, String presentateur, String date, String image) {
        this.id_evenement = id_evenement;
        this.lien = lien;
        this.theme = theme;
        this.presentateur = presentateur;
        this.date = date;
        this.image = image;
    }

    public Evenement(String lien, String theme, String presentateur, String date, String image) {
        this.lien = lien;
        this.theme = theme;
        this.presentateur = presentateur;
        this.date = date;
        this.image = image;
    }
        public Evenement(String lien, String theme, String presentateur, String date) {
        this.lien = lien;
        this.theme = theme;
        this.presentateur = presentateur;
        this.date = date;
    }
    
    public Evenement(){
        
    }

    @Override
    public String toString() {
        return "Evenement{" + "id_evenement=" + id_evenement + ", lien=" + lien + ", theme=" + theme + ", presentateur=" + presentateur + ", date=" + date + ", image=" + image + '}';
    }
    
    
    
}
