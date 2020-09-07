package com.example.zerohungerlogin;

public class food_donor_list {
    String name;
    String address;
    String pin;
    String ph;
    String rice;
    String curry;
    String uid;
    String url;

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }



    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public food_donor_list() {
    }

    @Override
    public String toString() {
        return "food_donor_list{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pin='" + pin + '\'' +
                ", ph='" + ph + '\'' +
                ", rice='" + rice + '\'' +
                ", curry='" + curry + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getRice() {
        return rice;
    }

    public void setRice(String rice) {
        this.rice = rice;
    }

    public String getCurry() {
        return curry;
    }

    public void setCurry(String curry) {
        this.curry = curry;
    }

}
