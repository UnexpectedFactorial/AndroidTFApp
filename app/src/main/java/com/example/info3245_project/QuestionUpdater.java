package com.example.info3245_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionUpdater extends AppCompatActivity {

    Button back,update;

    EditText qUpdate;

    DatabaseReference myref;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_updater);

        back = findViewById(R.id.qupdatebackBTN);
        update = findViewById(R.id.qupdateBTN);

        qUpdate = findViewById(R.id.qupdateINPUT);

        myref = FirebaseDatabase.getInstance().getReference("Question");
        module = ((Module)getApplicationContext());

        final String str = module.getQvalue_id().substring(0,1);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionGetter question = snapshot.child(str).getValue(questionGetter.class);
                qUpdate.setText(question.getQuestion());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void ClearEntry(){
        String qUpdate = "";
    }

    private void updateQuestion(){
        final String question = qUpdate.getText().toString().trim();
        final String yesANS = "0";
        final String noANS = "0";
        final String qNo = module.getQvalue_id().substring(0,1);

        if (TextUtils.isEmpty(question)){
            qUpdate.setError("Please enter in a question.");
        }
        else{
            questionGetter updatedQuestion = new questionGetter(question,yesANS,noANS,qNo);
            myref.child("Question").child(qNo).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    myref=FirebaseDatabase.getInstance().getReference();
                    myref.child("Question").child(qNo).child("question").setValue(question);
                    myref.child("Question").child(qNo).child("yes").setValue(yesANS);
                    myref.child("Question").child(qNo).child("no").setValue(noANS);

                    ClearEntry();
                    Intent intent = new Intent(getApplicationContext(),QuestionManager.class);
                    startActivity(intent);
                    Toast.makeText(QuestionUpdater.this,"Question updated.",Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

}