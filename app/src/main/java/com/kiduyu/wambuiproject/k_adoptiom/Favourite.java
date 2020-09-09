package com.kiduyu.wambuiproject.k_adoptiom;

public class Favourite {
    String kidname,kidage,kidagency,kiddescription,kidlocation,kidimage;
    public Favourite (){

    }

    public Favourite(String kidname, String kidage, String kidagency, String kiddescription, String kidlocation, String kidimage) {
        this.kidname = kidname;
        this.kidage = kidage;
        this.kidagency = kidagency;
        this.kiddescription = kiddescription;
        this.kidlocation = kidlocation;
        this.kidimage = kidimage;
    }

    public String getKidname() {
        return kidname;
    }

    public void setKidname(String kidname) {
        this.kidname = kidname;
    }

    public String getKidage() {
        return kidage;
    }

    public void setKidage(String kidage) {
        this.kidage = kidage;
    }

    public String getKidagency() {
        return kidagency;
    }

    public void setKidagency(String kidagency) {
        this.kidagency = kidagency;
    }

    public String getKiddescription() {
        return kiddescription;
    }

    public void setKiddescription(String kiddescription) {
        this.kiddescription = kiddescription;
    }

    public String getKidlocation() {
        return kidlocation;
    }

    public void setKidlocation(String kidlocation) {
        this.kidlocation = kidlocation;
    }

    public String getKidimage() {
        return kidimage;
    }

    public void setKidimage(String kidimage) {
        this.kidimage = kidimage;
    }
}
