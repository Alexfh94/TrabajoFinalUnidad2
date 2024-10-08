package com.example.trabajofinalunidad2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View view) {
        Intent launchActividad2 = new Intent(this, Actividad2.class);
        EditText textMsj = findViewById(R.id.textMsj);
        TextView historialMsj = findViewById(R.id.historial);
        String mensaje = String.valueOf(textMsj.getText());
        String historial = String.valueOf(historialMsj.getText());
        launchActividad2.putExtra("historial", historial);
        launchActividad2.putExtra("mensajeNuevo1", mensaje);
        startActivity(launchActividad2);

    }
}