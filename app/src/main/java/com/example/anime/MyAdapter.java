package com.example.anime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Anime> nAnime;
    private Context nContext;

    User user;

    public MyAdapter(List<Anime> nAnime, Context ncontext) {
        this.nContext = ncontext;
        this.nAnime = nAnime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(nContext);
        View view = layoutInflater.inflate(R.layout.anime_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textName2.setText(nAnime.get(position).getName());
        holder.textDescription2.setText(nAnime.get(position).getOriginalName());

        Picasso.get().load(nAnime.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.imageView2);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nContext, DetailActivity.class);
                intent.putExtra("anime", nAnime.get(holder.getAdapterPosition()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Agregar la bandera FLAG_ACTIVITY_NEW_TASK
                nContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nAnime.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        private ImageView imageView2;
        private TextView textDescription2;
        private TextView textName2;
        private TextView textType2;
        private ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //textType2 = itemView.findViewById(R.id.textCountry2);
            textName2 = itemView.findViewById(R.id.textName2);
            textDescription2 = itemView.findViewById(R.id.textViewEpisode);
            imageView2 = itemView.findViewById(R.id.imageVideo);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
