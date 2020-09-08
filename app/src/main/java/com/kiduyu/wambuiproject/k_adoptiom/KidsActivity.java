package com.kiduyu.wambuiproject.k_adoptiom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.ornolfr.ratingview.RatingView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KidsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    LinearLayout notfound;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Kids");
        mDatabase.keepSynced(true);

        notfound = findViewById(R.id.lyt_not_found);
        progressBar = findViewById(R.id.progressBar_kids);
        recyclerView = findViewById(R.id.kids_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        String agency=getIntent().getStringExtra("agency");
        getKid(agency);
    }

    private void getKid(final String agency) {
progressBar.setVisibility(View.VISIBLE);
        FirebaseRecyclerOptions<Kid> option = new FirebaseRecyclerOptions.Builder<Kid>()
                .setQuery(mDatabase.child(agency), Kid.class)
                .build();


        FirebaseRecyclerAdapter<Kid, KidViewHolder> adapter = new FirebaseRecyclerAdapter<Kid, KidViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull final KidViewHolder holder, int position, @NonNull final Kid model) {
                if (model.getName().isEmpty()){
                    Toast.makeText(KidsActivity.this, "empty", Toast.LENGTH_SHORT).show();
                }
                /*
                mDatabase.child(agency).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(KidsActivity.this, "some", Toast.LENGTH_SHORT).show();
                            Glide.with(getApplicationContext()).load(model.getImage()).into(holder.image);
                            holder.kidsname.setText(model.getName());
                            holder.kidsage.setText("Age: "+model.getAge());
                            holder.kidsgender.setText("Gender: "+model.getGender());
                            holder.kidslocation.setText("Location: "+model.getGender());
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(KidsActivity.this, "none", Toast.LENGTH_SHORT).show();
                            recyclerView.setVisibility(View.GONE);
                            notfound.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(KidsActivity.this, String.valueOf(databaseError.getMessage()), Toast.LENGTH_SHORT).show();


                    }
                });
*/

            }

            @NonNull
            @Override
            public KidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kid_item, parent, false);
                return new KidViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class KidViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        private TextView kidsname, kidsage, kidsgender, kidslocation;
        private RelativeLayout lyt_parent;
        LinearLayout favourite;

        public KidViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.kid_image);
            lyt_parent = itemView.findViewById(R.id.kid_rootLayout);
            kidsname = itemView.findViewById(R.id.kid_name);
            kidsage = itemView.findViewById(R.id.kid_age);
            favourite = itemView.findViewById(R.id.kid_favourite);
            kidsgender = itemView.findViewById(R.id.kid_gender);
            kidslocation = itemView.findViewById(R.id.kid_location);
        }
    }
}