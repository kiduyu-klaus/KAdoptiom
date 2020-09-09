package com.kiduyu.wambuiproject.k_adoptiom;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FavouritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    LinearLayout notfound;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Favourites");
        mDatabase.keepSynced(true);

        notfound = view.findViewById(R.id.lyt_not_found1);
        progressBar = view.findViewById(R.id.progressBar_favourites);
        recyclerView = view.findViewById(R.id.favourites_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        String uniqueid= Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        getFavourites(uniqueid);
        return view;
    }

    private void getFavourites(String uniqueid) {

        progressBar.setVisibility(View.VISIBLE);
        FirebaseRecyclerOptions<Favourite> option = new FirebaseRecyclerOptions.Builder<Favourite>()
                .setQuery(mDatabase.child(uniqueid), Favourite.class)
                .build();

        FirebaseRecyclerAdapter<Favourite, FavouriteViewHolder> adapter = new FirebaseRecyclerAdapter<Favourite, FavouriteViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position, @NonNull final Favourite model) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "some", Toast.LENGTH_SHORT).show();
                Glide.with(getContext()).load(model.getKidimage()).into(holder.image);
                holder.kidsname.setText(model.getKidname());
                holder.kidsage.setText("Age: " + model.getKidage());
                holder.kidsgender.setText("Gender: " + model.getKidgender());
                holder.kidslocation.setText("Location: " + model.getKidlocation());
                holder.favourite.setVisibility(View.GONE);
               /* holder.favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Aleady Added", Toast.LENGTH_SHORT).show();

                    }
                });

                */
                holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(getActivity(),KidDetails.class);
                        intent.putExtra("kidsname",model.getKidname());
                        intent.putExtra("kidsage",model.getKidage());
                        intent.putExtra("kidslocation",model.getKidlocation());
                        intent.putExtra("image",model.getKidimage());
                        intent.putExtra("gender",model.getKidgender());
                        intent.putExtra("description",model.getKiddescription());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kid_item, parent, false);
                return new FavouriteViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        private TextView kidsname, kidsage, kidsgender, kidslocation;
        private RelativeLayout lyt_parent;
        LinearLayout favourite;

        public FavouriteViewHolder(@NonNull View itemView) {
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
