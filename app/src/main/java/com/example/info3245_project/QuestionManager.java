package com.example.info3245_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionManager extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button back,update,delete;

    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_manager);

        update=findViewById(R.id.updateQuestionBTN);
        delete=findViewById(R.id.deleteQuestionBTN);



        databaseReference = FirebaseDatabase.getInstance().getReference("Question");
        listView=(ListView) findViewById(R.id.questionsListView);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList); //list template from android library
        listView.setAdapter(arrayAdapter);
        module=((Module)getApplicationContext());

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(questionGetter.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //gets the current position of the question that got clicked
                module.setQvalue_id(arrayList.get(position));
                module.setQvalue_name(arrayList.get(position));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (module.getQvalue_id().equals("")){
                    Toast.makeText(QuestionManager.this,"Please Select a Question to update",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent passData = new Intent(getApplicationContext(),QuestionUpdater.class); //passes the data to the update page. Mainly the position id/question no
                    startActivity(passData);
                    finish();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str= module.getQvalue_id().substring(0,1); //gets the first character of the string in the list view. In this case, it would just be the question number
                if(str==""){
                    Toast.makeText(QuestionManager.this,"Please Select a Question to delete",Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child("Question").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(str).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(QuestionManager.this,"Question has been deleted",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),QuestionManager.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        back=findViewById(R.id.QM_backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}