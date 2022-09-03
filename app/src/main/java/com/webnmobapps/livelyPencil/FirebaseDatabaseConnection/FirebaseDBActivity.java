package com.webnmobapps.livelyPencil.FirebaseDatabaseConnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.webnmobapps.livelyPencil.R;

public class FirebaseDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_dbactivity);


        // Set value in dataset....

    /*    FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/


    }
}