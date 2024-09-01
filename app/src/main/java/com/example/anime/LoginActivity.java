package com.example.anime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, havntAccont, forgotPass;
    EditText email, password;

    SharedPreferences sharedPreferences;
    private static final String SHARE_PREF_NAME = "mypref";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String KEY_MAIL = "email";
    private static final String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        havntAccont = findViewById(R.id.HavntAccont);
        forgotPass = findViewById(R.id.ForgotPass);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);

        sharedPreferences = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);

        if (checkSession()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/user/login";
                final String emailV = email.getText().toString();
                final String passwordV = password.getText().toString();

                // Validar que se haya ingresado un nombre de usuario y contraseña
                if (emailV.isEmpty() || passwordV.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("email", emailV);
                params.put("password", passwordV);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url,
                                new JSONObject(params),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Verificar si se han devuelto datos de usuario
                                        if (response.has("id")) {
                                            int userId = response.optInt("id");
                                            String userName = response.optString("name");
                                            String userPhone = response.optString("phone");

                                            saveSession(userId, userName, passwordV, emailV, userPhone);

                                            Toast.makeText(LoginActivity.this, "Ha iniciado sesión correctamente", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Correo electrónico o contraseña incorrectas", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                            }
                        });

                Volley.newRequestQueue(LoginActivity.this).add(jsonObjectRequest);
            }
        });



        havntAccont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecoverPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveSession(int id, String name, String pass, String mail, String phone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASS, pass);
        editor.putString(KEY_MAIL, mail);
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    private boolean checkSession() {
        SharedPreferences prefs = getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_MAIL, null) != null && prefs.getString(KEY_NAME, null) != null && prefs.getString(KEY_PASS, null) != null && prefs.getString(KEY_PHONE, null) != null;
    }


}
