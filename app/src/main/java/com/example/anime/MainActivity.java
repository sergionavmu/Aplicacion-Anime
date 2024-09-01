package com.example.anime;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String imageUrl1 = "https://joanseculi.com/";
    //private static final String JSON = "https://joanseculi.com/edt69/animes3.php?email=snavarro@mail.com";
    private static final String JSON = "http://10.0.2.2:8080/anime";
    private List<Anime> animes = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private ViewPager2 viewPager2;
    private TextView textView;
    private ImageView imageBefore, imageNext;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MyAdapter myAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_menu);
        viewPager2 = findViewById(R.id.viewPager2);
        textView = findViewById(R.id.textView);
        imageBefore = findViewById(R.id.imageBefore);
        imageNext = findViewById(R.id.imageNext);
        linearLayout = findViewById(R.id.linearLayout);

        user = (User)getIntent().getSerializableExtra("user");

        getAnimes();

        NavigationDrawer();


    }

    private void NavigationDrawer() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_animes);
    }

    private void ImageSlider() {

        imageAdapter = new ImageAdapter(this, images);
        viewPager2.setAdapter(imageAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        createDots();
        updateDots(viewPager2.getCurrentItem());
        setDotsListener();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Handle visibility of before and next images
                if (viewPager2.getCurrentItem() == 0) {
                    imageBefore.setVisibility(View.GONE);
                } else {
                    imageBefore.setVisibility(View.VISIBLE);
                }
                if (viewPager2.getCurrentItem() == images.size() - 1) {
                    imageNext.setVisibility(View.GONE);
                } else {
                    imageNext.setVisibility(View.VISIBLE);
                }
                updateDots(position);
            }
        });

        imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
            }
        });

        imageBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1, true);
            }
        });
    }


    private void createDots() {
        for (int i = 0; i < images.size(); i++) {
            imageViews.add(new ImageView(this));
            imageViews.get(i).setImageResource(R.drawable.ic_cercle_white);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(4, 0, 4, 0);
            linearLayout.addView(imageViews.get(i), params);
        }
    }

    private void updateDots(int position) {
        for (int i = 0; i < images.size(); i++) {
            imageViews.get(i).setImageResource(R.drawable.ic_cercle_white);
        }
        imageViews.get(position).setImageResource(R.drawable.ic_cercle_orange);
    }

    private void setDotsListener() {
        for (int i = 0; i < images.size(); i++) {
            int finalI = i;
            imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager2.setCurrentItem(finalI);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_animes) {
            Toast.makeText(this, "Animes", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_perfil) {
            Intent intentPerfil = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(intentPerfil);
        } else if (itemId == R.id.favoritos) {
            Intent intentFavorito = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intentFavorito);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getAnimes() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                //method
                Request.Method.GET,
                //url
                JSON,
                //body
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Anime anime = new Anime();
                                anime.setId(jsonObject.getInt("id"));
                                anime.setName(jsonObject.getString("name"));
                                anime.setDescription(jsonObject.getString("description"));
                                anime.setType(jsonObject.getString("type"));
                                anime.setYear(jsonObject.getInt("year"));
                                String image1 = jsonObject.getString("image").replace("\\", "");
                                String image2 = jsonObject.getString("image2").replace("\\", "");
                                anime.setImage(imageUrl1 + image1);
                                anime.setImage2(imageUrl1 + image2);
                                anime.setOriginalName(jsonObject.getString("originalName"));
                                anime.setRating(jsonObject.getString("rating"));
                                anime.setDemography(jsonObject.getString("demography"));
                                anime.setGenre(jsonObject.getString("genre"));
                                anime.setActive(jsonObject.getBoolean("active"));
                                //anime.setFavorite(jsonObject.getString("favorite"));
                                animes.add(anime);

                                images.add(anime.getImage());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        myAdapter = new MyAdapter(animes, getApplicationContext());
                        recyclerView.setAdapter(myAdapter);

                        ImageSlider();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR LOADING JSON", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    protected void onStart() {
        super.onStart();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myAdapter = new MyAdapter(animes, getApplicationContext());
        recyclerView.setAdapter(myAdapter);

    }
}
