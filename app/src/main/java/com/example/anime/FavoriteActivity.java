package com.example.anime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    //private static final String SHARE_PREF_NAME = "mypref";
    //private static final String KEY_MAIL = "email";
    //private static final String JASONFAVORITO = "https://joanseculi.com/edt69/animesfavorites3.php?email=";
    private String imageUrl1 = "https://joanseculi.com/";
    private List<Anime> animes = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private MyAdapter myAdapter;
    private RecyclerView favoriteListView;
    private RequestQueue requestQueue;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteListView = findViewById(R.id.favoriteListView);
        requestQueue = Volley.newRequestQueue(this);

        // Recuperar el objeto User de SharedPreferences
        //SharedPreferences prefs = getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        // String email = prefs.getString(KEY_MAIL, "");
        SharedPreferences prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int userId = prefs.getInt("id", -1);

        //user = new User();
        //user.setEmail(email); // Establecer el email del usuario

        getFavoriteAnimes();
    }

    private void getFavoriteAnimes() {
        SharedPreferences prefs = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int userId = prefs.getInt("id", -1);

        if (userId != -1) {
            String url = "http://10.0.2.2:8080/favorito/" + userId;
            String animeUrl = "http://10.0.2.2:8080/anime/";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int animeId = jsonObject.getInt("idanime");
                                    String fullAnimeUrl = animeUrl + animeId;

                                    // Realizar una solicitud adicional para obtener la información completa del anime
                                    JsonObjectRequest animeDetailsRequest = new JsonObjectRequest(
                                            Request.Method.GET,
                                            fullAnimeUrl,
                                            null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject animeResponse) {
                                                    try {
                                                        Anime anime = new Anime();
                                                        anime.setId(animeResponse.getInt("id"));
                                                        anime.setName(animeResponse.getString("name"));
                                                        anime.setDescription(animeResponse.getString("description"));
                                                        anime.setType(animeResponse.getString("type"));
                                                        anime.setYear(animeResponse.getInt("year"));
                                                        String image1 = animeResponse.getString("image").replace("\\", "");
                                                        String image2 = animeResponse.getString("image2").replace("\\", "");
                                                        anime.setImage(imageUrl1 + image1);
                                                        anime.setImage2(imageUrl1 + image2);
                                                        anime.setOriginalName(animeResponse.getString("originalName"));
                                                        anime.setRating(animeResponse.getString("rating"));
                                                        anime.setDemography(animeResponse.getString("demography"));
                                                        anime.setGenre(animeResponse.getString("genre"));
                                                        anime.setActive(animeResponse.getBoolean("active"));
                                                        animes.add(anime);

                                                        // Actualizar el RecyclerView cuando se hayan recuperado todos los animes
                                                        if (animes.size() == response.length()) {
                                                            favoriteListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                            myAdapter = new MyAdapter(animes, getApplicationContext());
                                                            favoriteListView.setAdapter(myAdapter);
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(FavoriteActivity.this, "Error al cargar la información del anime", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    );
                                    requestQueue.add(animeDetailsRequest);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(FavoriteActivity.this, "No tienes animes favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonArrayRequest);
        } else {
            // No se pudo obtener la ID del usuario de las preferencias compartidas
            Toast.makeText(this, "Error: No se pudo obtener la ID del usuario", Toast.LENGTH_SHORT).show();
        }
    }






}
