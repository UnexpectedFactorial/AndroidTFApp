package com.example.info3245_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainPage extends AppCompatActivity {

    Button addQ,viewQ,backBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        addQ = findViewById(R.id.addquestionBTN);
        viewQ = findViewById(R.id.viewquestionsBTN);
        backBTN = findViewById(R.id.backBTN);


        backBTN.setOnClickListener(new View.OnClickListener() { //closes the current page and goes back to the main menu
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainPage.this,QuestionManager.class));
            }
        });

        addQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainPage.this,QuestionMaker.class));
            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}