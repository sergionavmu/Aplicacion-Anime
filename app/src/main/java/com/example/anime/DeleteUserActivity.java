package com.example.anime;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarusuario);

        EditText emailText = findViewById(R.id.Email);
        Button deleteButton = findViewById(R.id.btnDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(DeleteUserActivity.this, "Por favor ingresa un correo electrónico", Toast.LENGTH_SHORT).show();
                } else {
                    PerfilActivity.deleteUser(getApplicationContext(), email);
                }
            }
        });
    }

}
