package com.webnmobapps.livelyPencil.Activity.Share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.webnmobapps.livelyPencil.Adapter.AddEmailLayoutAdapter;
import com.webnmobapps.livelyPencil.Model.AddItemModel;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;

public class ToEmailActivity extends AppCompatActivity {

    LinearLayout add_more_address , cancel_circle;
    LinearLayoutCompat add_address_layout;
    ArrayList<AddItemModel> addItemModelArrayList = new ArrayList<>();

    AddEmailLayoutAdapter addEmailLayoutAdapter;
    RecyclerView addItemRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_email);

        inits();

        RecyclerView.LayoutManager addItemManager = new LinearLayoutManager(ToEmailActivity.this, LinearLayoutManager.VERTICAL, false);
        addItemRecyclerView.setLayoutManager(addItemManager);
        addItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addEmailLayoutAdapter = new AddEmailLayoutAdapter(ToEmailActivity.this);
        addEmailLayoutAdapter.add(addItemModelArrayList);
        addItemRecyclerView.setAdapter(addEmailLayoutAdapter);

        cancel_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        add_more_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemModel addItemModel = new AddItemModel();
                addItemModel.setView(true);
                addItemModelArrayList.add(addItemModel);
                addEmailLayoutAdapter.add(addItemModelArrayList);
            }
        });
    }

    private void inits() {
        add_more_address = findViewById(R.id.add_more_address);
        addItemRecyclerView = findViewById(R.id.addItemRecyclerView);
        cancel_circle = findViewById(R.id.cancel_circle);
        // add_address_layout = findViewById(R.id.add_address_layout);
    }
}