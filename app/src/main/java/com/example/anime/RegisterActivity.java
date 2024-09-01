package com.example.anime;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button HaveAccont, btnRegister;
    EditText name, email, phone, password;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        HaveAccont = findViewById(R.id.HaveAccont);
        btnRegister = findViewById(R.id.btnRegister);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.Phone);
        password = findViewById(R.id.Password);

        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/user/register";
                final String nameV = name.getText().toString().trim();
                final String emailV= email.getText().toString().trim();
                final String phoneV = phone.getText().toString().trim();
                final String passwordV = password.getText().toString().trim();

                // Verificar si los campos están vacíos
                if (nameV.isEmpty() || emailV.isEmpty() || phoneV.isEmpty() || passwordV.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("name", nameV);
                params.put("email", emailV);
                params.put("password", passwordV);
                params.put("phone", phoneV);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String responseMessage = response.getString("response");

                                    if (responseMessage.equals("Usuario registrado correctamente")) {

                                        saveSession(nameV, emailV, phoneV); // Guardar datos del usuario en SharedPreferences

                                        Toast.makeText(RegisterActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else if (responseMessage.equals("Usuario ya registrado")) {
                                        Toast.makeText(RegisterActivity.this, "El correo electrónico ya está en uso", Toast.LENGTH_LONG).show();

                                    } else if (responseMessage.equals("Mail incorrecto")) {
                                        Toast.makeText(RegisterActivity.this, "Por favor, introduzca un correo electrónico válido", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(RegisterActivity.this, "Error al analizar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterActivity.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                Volley.newRequestQueue(RegisterActivity.this).add(jsonObjectRequest);
            }
        });


        // Si un usuario ya tiene cuenta puede volver a la página de Login
        HaveAccont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveSession(String name, String email, String phone) {
        // Guardar datos del usuario en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.apply();
    }
}
