package com.example.info3245_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SurveyPage extends AppCompatActivity {

    Button yes, no;
    TextView question;

    int qNum = 0, yesClicked = 0, noClicked = 0, totalYes = 0, totalNo = 0;

    FirebaseDatabase rootnode;

    DatabaseReference databaseReference, getInt;

    questionGetter getter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_page);

        question = (TextView) findViewById(R.id.question);

        yes = (Button) findViewById(R.id.yesBtn);
        no = (Button) findViewById(R.id.noBtn);


        updateQuestion();

    }


    private void updateQuestion() {

        rootnode = FirebaseDatabase.getInstance();

        getInt = rootnode.getReference("QuestionNo");


        getInt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String value = snapshot.getValue(String.class);

                Integer i = Integer.parseInt(value);
                i++;
                qNum++;

                if (qNum < i){

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Question").child(String.valueOf(qNum)); //opens q1

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            getter = snapshot.getValue(questionGetter.class);

                            question.setText(getter.getQuestion());


                            totalYes = Integer.parseInt(getter.getYes());
                            totalNo = Integer.parseInt(getter.getNo());

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    yesClicked = yesClicked + 1;
                                    int totY = totalYes + yesClicked;
                                    databaseReference.child("yes").setValue(String.valueOf(totY));
                                    updateQuestion();
                                    yesClicked = 0;
                                }
                            });

                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    noClicked = noClicked + 1;
                                    int totN = totalNo + noClicked;
                                    databaseReference.child("no").setValue(String.valueOf(totN));
                                    updateQuestion();
                                    noClicked = 0;
                                }
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    startActivity(new Intent(getApplicationContext(), ThankYou.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}