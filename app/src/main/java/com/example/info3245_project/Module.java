package com.example.info3245_project;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Module extends Application {
    public ArrayList <String> qList = new ArrayList<>();
    public ArrayAdapter<String> qAdapt;
    public String qvalue_id,qvalue_name;

    public String getQvalue_id() {
        return qvalue_id;
    }

    public void setQvalue_id(String qvalue_id) {
        this.qvalue_id = qvalue_id;
    }

    public String getQvalue_name() {
        return qvalue_name;
    }

    public void setQvalue_name(String qvalue_name) {
        this.qvalue_name = qvalue_name;
    }
}
