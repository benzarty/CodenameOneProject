/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author Benzarti
 */
public class Users {

    private int id;
    private String nom;
    private String prenom;
    private String photo;
    private String email;
    private String password;
    private String specialite;
    private String profil;
    private String status;
    private String role;
    private int codesecurity;

    public Users(int id, String nom, String prenom, String photo, String email, String password, String specialite, String profil, String status, String role, int codesecurity) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.email = email;
        this.password = password;
        this.specialite = specialite;
        this.profil = profil;
        this.status = status;
        this.role = role;
        this.codesecurity = codesecurity;
    }

    public Users() {
    }

    public Users(String nom, String prenom, String photo, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.email = email;
        this.password = password;
        
    }

    
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the specialite
     */
    public String getSpecialite() {
        return specialite;
    }

    /**
     * @param specialite the specialite to set
     */
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    /**
     * @return the profil
     */
    public String getProfil() {
        return profil;
    }

    /**
     * @param profil the profil to set
     */
    public void setProfil(String profil) {
        this.profil = profil;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the codesecurity
     */
    public int getCodesecurity() {
        return codesecurity;
    }

    /**
     * @param codesecurity the codesecurity to set
     */
    public void setCodesecurity(int codesecurity) {
        this.codesecurity = codesecurity;
    }
    

}
