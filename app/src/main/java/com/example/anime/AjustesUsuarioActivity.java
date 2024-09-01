package com.example.anime;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class AjustesUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustesusuario);

        EditText usernameText = findViewById(R.id.Username);
        EditText emailText = findViewById(R.id.Email);
        EditText passwordText = findViewById(R.id.Password);
        EditText phoneText = findViewById(R.id.Phone);
        Button modifyButton = findViewById(R.id.btnModify);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String phone = phoneText.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AjustesUsuarioActivity.this, "Porfavor rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    PerfilActivity.updateUser(getApplicationContext(), username, email, password, phone);
                }
            }
        });
    }



}
