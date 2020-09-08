package com.kiduyu.wambuiproject.k_adoptiom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.view.LayoutInflaterCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Button signUp;
    private EditText Fullname ,PhoneNumber,Email,Address, Password,password_confirm;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Account");
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        signUp = findViewById(R.id.btn_signup);
        Fullname = findViewById(R.id.fullname);
        PhoneNumber =  findViewById(R.id.phone_number);
        Email =  findViewById(R.id.email_signup);
        Address =  findViewById(R.id.address_signup);
        password_confirm =  findViewById(R.id.password_confm);
        Password =findViewById(R.id.password_reg);

        loadingBar= new ProgressDialog(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void toHome(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void createAccount(View view) {

        String name = Fullname.getText().toString();
        String phone = PhoneNumber.getText().toString();
        String memail = Email.getText().toString();
        String maddress = Address.getText().toString();
        String password = Password.getText().toString();
        String password2 = password_confirm.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            Fullname.setError("Name Is Required..");
            return;
        }
        else if (TextUtils.isEmpty(phone))
        {
            PhoneNumber.setError("Phone Number Is Required..");
            return;
        }else if (TextUtils.isEmpty(memail))
        {
            Email.setError("Phone Number Is Required..");
            return;
        }else if (TextUtils.isEmpty(maddress))
        {
            Address.setError("Phone Number Is Required..");
            return;
        }
        else if (TextUtils.isEmpty(password))
        {
            Password.setError("Password Is Required..");
            return;
        }
        else if (!password.equals(password2))
        {
            password_confirm.setError("Both Passwords Don't Match..");
            return;
        }
        else
        {
            String first3 ="";
            first3=phone.substring(0 , 3);
            Log.d("TAG", "createAccount: "+first3);

            if (!first3.equals("254")){
                PhoneNumber.setError("Phone Number must start with 254..");
                return;
            }else {
                loadingBar.setTitle("Creating Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                //String uniqueid= UUID.randomUUID().toString();
                String uniqueid= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                Log.d("TAG", "onCreate: "+uniqueid);
                ValidatephoneNumber(uniqueid,name, phone,memail,maddress, password);
            }

        }
    }

    private void ValidatephoneNumber(final String uniqueid, final String name, final String phone, final String memail, final String maddress, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User userdata = new User(uniqueid,name,phone,memail,maddress,password);
                RootRef.child("Users").child(uniqueid).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "Congratulations "+name+", your account has been created.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });

    }
}