package com.example.anime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);

        Button buttonUpdate = findViewById(R.id.button);
        Button buttonDelete = findViewById(R.id.button2);
        Button buttonLogOut = findViewById(R.id.button3);

        TextView nameTextView = findViewById(R.id.Name);
        TextView emailTextView = findViewById(R.id.Email);
        TextView phoneTextView = findViewById(R.id.Phone);

        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");

        // Establecer la información del usuario en los TextView
        nameTextView.setText(name);
        emailTextView.setText(email);
        phoneTextView.setText(phone);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, AjustesUsuarioActivity.class);
                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar la sesión del usuario
                clearSession();

                // Redirigir a la actividad de inicio de sesión
                Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static void updateUser(final Context context, final String nameV, final String emailV, final String passwordV, final String phoneV) {
        String URL = "http://10.0.2.2:8080/user/" + emailV;

        Map<String, String> params = new HashMap<>();
        params.put("name", nameV);
        params.put("email", emailV);
        params.put("password", passwordV);
        params.put("phone", phoneV);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, URL,
                        new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.equals("User updated")) {
                            Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("Error updating user")) {
                            Toast.makeText(context, "Error updating user", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Usuario modificado", Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public static void deleteUser(final Context context, final String emailV) {
        String URL = "http://10.0.2.2:8080/user/" + emailV;

        Map<String, String> params = new HashMap<>();
        params.put("email", emailV);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.DELETE, URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.equals("User deleted")) {
                            Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("No user exists")) {
                            Toast.makeText(context, "No user exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    private void clearSession() {
        // Borrar datos de sesión
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
