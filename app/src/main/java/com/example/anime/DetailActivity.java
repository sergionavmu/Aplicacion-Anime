package com.example.anime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private MyAdapterVideo myAdapterVideo;
    private ImageView backImg, normalImg, imageHeart, imageArrow, imageVideo;
    private TextView animeTitle, category, stars, summary, summaryInfo, capitulos, seeMoreLessButton;
    private RecyclerView VideosRecyclerView;
    String imageUrl1 = "https://joanseculi.com/";
    private static String JSONVIDEO = "http://10.0.2.2:8080/videos/";

    private static String URLFAVORITE = "http://10.0.2.2:8080/favorito/";
    SharedPreferences sharedPreferences;

    List<AnimeVideos> animeVideosList = new ArrayList<>();
    RequestQueue requestQueue;
    Anime anime;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Hooks
        backImg = findViewById(R.id.backImg);
        normalImg = findViewById(R.id.normalImg);
        imageHeart = findViewById(R.id.imageHeart);
        imageArrow = findViewById(R.id.imageArrow);
        animeTitle = findViewById(R.id.animeTitle);
        category = findViewById(R.id.category);
        stars = findViewById(R.id.stars);
        summary = findViewById(R.id.summary);
        summaryInfo = findViewById(R.id.summaryInfo);
        VideosRecyclerView = findViewById(R.id.VideosRecyclerView);
        seeMoreLessButton = findViewById(R.id.seeMoreLessButton);

        anime = (Anime) getIntent().getSerializableExtra("anime");
        summaryInfo.setText(anime.getDescription());
        animeTitle.setText(anime.getName());
        category.setText(anime.getDemography());
        stars.setText(anime.getRating());
        Picasso.get().load(anime.getImage()).into(normalImg);
        Picasso.get().load(anime.getImage()).into(backImg);
        user = (User) getIntent().getSerializableExtra("user");
        SharedPreferences prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int userId = prefs.getInt("id", -1);


        seeMoreLessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (summaryInfo.getMaxLines() == 3) {
                    summaryInfo.setMaxLines(Integer.MAX_VALUE);
                    seeMoreLessButton.setText("See less");
                } else {
                    summaryInfo.setMaxLines(3);
                    seeMoreLessButton.setText("See more");
                }
            }
        });

        imageArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    toggleFavorite(userId);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        getAnimeVideos(anime.getId());
        checkIfFavorite(userId, anime.getId());

        requestQueue = Volley.newRequestQueue(this);
    }

    private void checkIfFavorite(int userId, int animeId) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = URLFAVORITE + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean isFavorite = false;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                if (jsonObject.getInt("idanime") == animeId) {
                                    isFavorite = true;
                                    break;
                                }
                            }
                            if (isFavorite) {
                                imageHeart.setImageResource(R.drawable.ic_favorite_filled);
                            } else {
                                imageHeart.setImageResource(R.drawable.ic_favorite_outline);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    private void getAnimeVideos(int idAnime) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = JSONVIDEO + idAnime;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                AnimeVideos animeVideos = new AnimeVideos();
                                animeVideos.setIdAnime(jsonObject.getInt("idanime"));
                                animeVideos.setEpisode(jsonObject.getString("episode"));
                                String originalUrl = jsonObject.getString("url");
                                String videoId = originalUrl.substring(originalUrl.lastIndexOf("/") + 1);
                                String correctedUrl = "https://mega.nz/embed/" + videoId;
                                animeVideos.setUrl(correctedUrl);
                                String image1 = jsonObject.getString("image").replace("\\", "");
                                animeVideos.setImage(imageUrl1 + image1);
                                animeVideosList.add(animeVideos);
                            }

                            VideosRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            myAdapterVideo = new MyAdapterVideo(animeVideosList, getApplicationContext());
                            VideosRecyclerView.setAdapter(myAdapterVideo);
                            myAdapterVideo.setOnItemClickListener(new MyAdapterVideo.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(DetailActivity.this, VideoActivity.class);
                                    intent.putExtra("url", animeVideosList.get(position).getUrl());
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, "ERROR LOADING JSON", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void toggleFavorite(int userId) throws UnsupportedEncodingException {
        if (anime.isFavorite() == null || anime.isFavorite().isEmpty()) {
            addFavorite(userId, anime.getId());
            imageHeart.setImageResource(R.drawable.ic_favorite_filled);
            anime.setFavorite("true");
        } else {
            deleteFavorite(userId, anime.getId());
            imageHeart.setImageResource(R.drawable.ic_favorite_outline);
            anime.setFavorite(null);
        }
    }

    private void addFavorite(int userId, int animeId) {
        String url = "http://10.0.2.2:8080/favorito/add";

        Map<String, Integer> params = new HashMap<>();
        params.put("iduser", userId);
        params.put("idanime", animeId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.get("response").equals("Favorito")) {
                                        Toast.makeText(getApplicationContext(), "Anime agregado a favoritos", Toast.LENGTH_SHORT).show();
                                        imageHeart.setImageResource(R.drawable.ic_favorite_filled);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error al agregar el anime a favoritos", Toast.LENGTH_SHORT).show();
                                Log.e("Volley Error", error.toString());
                            }
                        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void deleteFavorite(int userId, int animeId) {
        String url = "http://10.0.2.2:8080/favorito/" + userId + "/" + animeId;

        Map<String, Integer> params = new HashMap<>();
        params.put("iduser", userId);
        params.put("idanime", animeId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.get("response").equals("Favorito eliminado")) {
                                        Toast.makeText(getApplicationContext(), "Anime eliminado de favoritos", Toast.LENGTH_SHORT).show();
                                        anime.setFavorite(null); // Marcar el anime como no favorito después de eliminarlo
                                        imageHeart.setImageResource(R.drawable.ic_favorite_outline); // Cambiar el icono del corazón a favorito no seleccionado
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error al eliminar el anime de favoritos", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error al eliminar el anime de favoritos", Toast.LENGTH_SHORT).show();
                                Log.e("Volley Error", error.toString());
                            }
                        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
