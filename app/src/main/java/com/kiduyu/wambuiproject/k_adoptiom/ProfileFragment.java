package com.kiduyu.wambuiproject.k_adoptiom;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    EditText edtFullName;
    EditText edtEmail;
    EditText edtPassword;
    Button edt_profile;
    EditText edtMobile;
    Toolbar toolbar;
    ProgressDialog pDialog;
    Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.toolbar_fragment);
        toolbar.setTitle("Edit Profile Details");
        pDialog = new ProgressDialog(getActivity());

        edtFullName = view.findViewById(R.id.edt_name);
        edtFullName.setEnabled(false);
        edt_profile = view.findViewById(R.id.edt_profile);
        edtEmail = view.findViewById(R.id.edt_email);
        edtEmail.setEnabled(false);
        edtPassword = view.findViewById(R.id.edt_password);
        edtPassword.setEnabled(false);
        edtMobile = view.findViewById(R.id.edt_phone);
        edtMobile.setEnabled(false);
        String uniqueid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        GetProfile(uniqueid);

        edt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtFullName.setEnabled(true);
                edtFullName.setText("");
                edtFullName.setMaxLines(1);
                edtFullName.setHint("Input New Full Name");
                edtFullName.setHintTextColor(getResources().getColor(R.color.dialog_color));

                edtEmail.setEnabled(true);
                edtEmail.setText("");
                edtEmail.setMaxLines(1);
                edtEmail.setHint("Input New Email");
                edtEmail.setHintTextColor(getResources().getColor(R.color.dialog_color));

                edtPassword.setEnabled(true);
                edtPassword.setText("");
                edtPassword.setMaxLines(1);
                edtPassword.setHint("Input New password");
                edtPassword.setHintTextColor(getResources().getColor(R.color.dialog_color));


                edtMobile.setEnabled(true);
                edtMobile.setText("");
                edtMobile.setMaxLines(1);
                edtMobile.setHint("Input New mobile number");
                edtMobile.setHintTextColor(getResources().getColor(R.color.dialog_color));

            }
        });

        return view;
    }

    private void GetProfile(final String uniqueid) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(uniqueid).exists()) {
                    User usersData = dataSnapshot.child("Users").child(uniqueid).getValue(User.class);
                    edtFullName.setText(usersData.getName());
                    edtFullName.setTextSize(23.00f);

                    edtEmail.setText(usersData.getEmail());
                    edtEmail.setTextSize(23.00f);

                    edtPassword.setText(usersData.getPassword());
                    edtPassword.setTextSize(23.00f);

                    edtMobile.setText(usersData.getMobile());
                    edtMobile.setTextSize(23.00f);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
