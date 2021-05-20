/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.entities;

/**
 *
 * @author USER
 */


public class Formation {
    
  private int idform;
private String intitule;
private int volumehoraire;
private String langue;
private String modeEnseignement;

    public Formation(int idform, String intitule, int volumehoraire, String langue, String modeEnseignement) {
        this.idform = idform;
        this.intitule = intitule;
        this.volumehoraire = volumehoraire;
        this.langue = langue;
        this.modeEnseignement = modeEnseignement;
    }

  public Formation(String intitule, int volumehoraire, String langue, String modeEnseignement) {
        
        this.intitule = intitule;
        this.volumehoraire = volumehoraire;
        this.langue = langue;
        this.modeEnseignement = modeEnseignement;
    }

    public Formation() {
    }

  

    public int getIdform() {
        return idform;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getVolumehoraire() {
        return volumehoraire;
    }

    public String getLangue() {
        return langue;
    }

    public String getModeEnseignement() {
        return modeEnseignement;
    }

    public void setIdform(int idform) {
        this.idform = idform;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setVolumehoraire(int volumehoraire) {
        this.volumehoraire = volumehoraire;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setModeEnseignement(String modeEnseignement) {
        this.modeEnseignement = modeEnseignement;
    }




    

}
