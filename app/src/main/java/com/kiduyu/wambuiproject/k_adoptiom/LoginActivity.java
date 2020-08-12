package com.kiduyu.wambuiproject.k_adoptiom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void signup(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}