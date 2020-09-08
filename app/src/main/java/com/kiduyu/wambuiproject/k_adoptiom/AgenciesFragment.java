package com.kiduyu.wambuiproject.k_adoptiom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.ornolfr.ratingview.RatingView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgenciesFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agencies, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Agencies");
        mDatabase.keepSynced(true);

        recyclerView = view.findViewById(R.id.agency_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getAgencies();
        return view;

    }

    private void getAgencies() {
        FirebaseRecyclerOptions<Agency> option = new FirebaseRecyclerOptions.Builder<Agency>()
                .setQuery(mDatabase, Agency.class)
                .build();


        FirebaseRecyclerAdapter<Agency, AgencyViewHolder> adapter = new FirebaseRecyclerAdapter<Agency, AgencyViewHolder>(option) {

            @NonNull
            @Override
            public AgencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_item, parent, false);
                return new AgencyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AgencyViewHolder holder, int position, @NonNull final Agency model) {

                holder.text_address.setText(model.getAddress());
                holder.text_title.setText(model.getName());
                holder.text_rate_total.setText("34 ratings");
                holder.text_avg_rate.setText("4");
                holder.ratingView.setRating(4f);

                Glide.with(getActivity()).load(model.getImage()).into(holder.image);
                holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(getActivity(),KidsActivity.class);
                        intent.putExtra("agency",model.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AgencyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        private TextView text_title, text_address, text_rate_total, text_avg_rate;
        private RelativeLayout lyt_parent;
        private RatingView ratingView;

        public AgencyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            lyt_parent = itemView.findViewById(R.id.rootLayout);
            text_title = itemView.findViewById(R.id.text_place_title);
            text_address = itemView.findViewById(R.id.text_place_address);
            text_rate_total = itemView.findViewById(R.id.text_place_rate_total);
            text_avg_rate = itemView.findViewById(R.id.text_place_rate_Avg);
            ratingView = itemView.findViewById(R.id.ratingView);
        }
    }
}
