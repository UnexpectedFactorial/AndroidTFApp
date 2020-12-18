package com.example.info3245_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionMaker extends AppCompatActivity {
    //this section is now done.
    Button backBTN,qSubmit;
    EditText userQ;

    FirebaseDatabase rootnode;
    DatabaseReference myref;

    DatabaseReference updateInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_maker);

        backBTN=findViewById(R.id.QM_backBTN);
        qSubmit=findViewById(R.id.QM_questionSubmit);

        userQ=findViewById(R.id.questionInput);

        rootnode = FirebaseDatabase.getInstance();
        myref = rootnode.getReference("Question");
        updateInt = rootnode.getReference("QuestionNo");



        updateInt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String value = snapshot.getValue(String.class);

                qSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        String question = userQ.getText().toString().trim();

                        String no = "0";
                        String yes = "0";

                        Integer i = Integer.parseInt(value);

                        i++;

                        String questionNo = Integer.toString(i);

                        questionGetter getter =new questionGetter(question,yes,no,questionNo);
                        updateInt.setValue(questionNo);


                        myref.child(questionNo).setValue(getter);

                        Toast.makeText(QuestionMaker.this,"Question Made",Toast.LENGTH_LONG).show();
                        finish();


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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