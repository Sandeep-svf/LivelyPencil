package com.webnmobapps.livelyPencil.Activity.PostType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.StrictMode;

import com.webnmobapps.livelyPencil.Activity.PostType.Fragment.HomePostFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.UserWallFragment;
import com.webnmobapps.livelyPencil.R;

public class CreateOfflinePostActivity extends AppCompatActivity {

    ConstraintLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offline_post);

        inits();

        // Default fragment
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new HomePostFragment()).commit();


    }

    private void inits() {
        fragment_container = findViewById(R.id.fragment_container2);
    }
}