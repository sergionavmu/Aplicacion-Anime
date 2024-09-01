package com.example.anime;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anime.LoginActivity;

public class InicioActivity extends AppCompatActivity {
    ImageView logoImage;
    TextView textView;
    private static int SPLASH_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);
        //hooks

        logoImage = findViewById(R.id.logoImage);
        textView = findViewById(R.id.TextBienvendio);

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(logoImage, "translationY", -2000f, 0f);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(logoImage, "rotation", 0f, 360f);

        ObjectAnimator translateYAnimatorTextView = ObjectAnimator.ofFloat(textView, "translationX", -1200f, 0f);
        ObjectAnimator rotateAnimatorTextView = ObjectAnimator.ofFloat(textView, "rotation", 180f, 0f);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateYAnimator, rotateAnimator, translateYAnimatorTextView, rotateAnimatorTextView);
        animatorSet.setDuration(1500);

        // Iniciar la animación
        animatorSet.start();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InicioActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logoImage, "imageapp");
                pairs[1] = new Pair<View, String>(textView, "textapp");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InicioActivity.this,
                        pairs);
                startActivity(intent, options.toBundle());
            }
        };
        Handler h = new Handler(Looper.getMainLooper());
        h.postDelayed(r, SPLASH_SCREEN);



    }
}
