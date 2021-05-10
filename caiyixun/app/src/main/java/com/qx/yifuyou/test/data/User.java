package com.qx.yifuyou.test.data;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private ArrayList<Package> send_packages;
    private ArrayList<Package>receive_packages;
    private boolean isCorrect;


    public User(ArrayList<Package> send_packages, ArrayList<Package> receive_packages, boolean isCorrect) {
        this.send_packages = send_packages;
        this.receive_packages = receive_packages;
        this.isCorrect=isCorrect;
    }

    public ArrayList<Package> getSend_packages() {
        return send_packages;
    }

    public void setSend_packages(ArrayList<Package> send_packages) {
        this.send_packages = send_packages;
    }

    public ArrayList<Package> getReceive_packages() {
        return receive_packages;
    }

    public void setReceive_packages(ArrayList<Package> receive_packages) {
        this.receive_packages = receive_packages;
    }
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

}

