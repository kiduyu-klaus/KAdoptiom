package com.kiduyu.wambuiproject.k_adoptiom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class KidDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_details);
        String kidsname = getIntent().getStringExtra("kidsname");
        String kidsage = getIntent().getStringExtra("kidsage");
        String kidslocation = getIntent().getStringExtra("kidslocation");
        String image = getIntent().getStringExtra("image");
        String gender = getIntent().getStringExtra("gender");
        String descr = getIntent().getStringExtra("description");


        Toolbar toolbar =findViewById(R.id.toolbar_kiddetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(kidsname + " Adoption Details");
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        ImageView imageView = findViewById(R.id.image_detailskid);
        Glide.with(this).load(image).into(imageView);
        TextView name = findViewById(R.id.details_name);
        name.setText(kidsname);

        TextView details = findViewById(R.id.details_agelocation);
        details.setText("Aged "+kidsage+", "+kidsname+" lives in "+kidslocation);

        TextView age = findViewById(R.id.details_age);
        age.setText(kidsage);

        TextView gender_tv = findViewById(R.id.details_gender);
        gender_tv.setText(gender);

        TextView location_tv = findViewById(R.id.details_location);
        location_tv.setText(kidslocation);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}