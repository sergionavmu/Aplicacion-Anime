package com.example.anime;

import android.content.Context;
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

public class MyAdapterVideo extends RecyclerView.Adapter<MyAdapterVideo.MyViewHolder> {

    private List<AnimeVideos> nAnimeVideo;
    private Context nContext;
    private OnItemClickListener itemClickListener;

    public MyAdapterVideo(List<AnimeVideos> nAnimevideo, Context ncontext) {
        this.nContext = ncontext;
        this.nAnimeVideo = nAnimevideo;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public MyAdapterVideo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(nContext);
        View view = layoutInflater.inflate(R.layout.animevideo_layout, parent, false);
        return new MyAdapterVideo.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapterVideo.MyViewHolder holder, int position) {
        holder.textTitleVideo.setText(nAnimeVideo.get(position).getEpisode());

        Picasso.get().load(nAnimeVideo.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.imageVideo);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nAnimeVideo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageVideo;
        private TextView textTitleVideo;
        private ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitleVideo = itemView.findViewById(R.id.textTitleVideo);
            imageVideo = itemView.findViewById(R.id.imageVideo);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
