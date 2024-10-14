package com.example.trabajofinalunidad2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Actividad2 extends AppCompatActivity {

    private LinearLayout historialLayout;
    private EditText textMsj;
    private ArrayList<String> historialMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        historialLayout = findViewById(R.id.historialLayout);
        textMsj = findViewById(R.id.textMsj);
        historialMensajes = new ArrayList<>();

        Log.i("TEST", "Entrando en Actividad2.onCreate");

        // Recuperar historial desde MainActivity
        Intent intent = getIntent();
        ArrayList<String> historial = intent.getStringArrayListExtra("historial");

        // Si hay un historial, lo mostramos
        if (historial != null) {
            historialMensajes = historial; // Actualizar el historial con lo que venga de la otra actividad
            Log.i("TEST", "Historial recibido en Actividad2: " + historialMensajes);
            for (int i = 0; i < historialMensajes.size(); i++) {
                boolean esEnviado = (i % 2 != 0); // En Actividad2, los mensajes impares son enviados
                añadirMensaje(historialMensajes.get(i), esEnviado);
            }
        }
    }

    // Método para añadir mensajes al historial
    private void añadirMensaje(String mensaje, boolean enviado) {
        Log.i("TEST", "Añadiendo mensaje en Actividad2: " + mensaje + ", enviado: " + enviado);

        TextView mensajeView = new TextView(this);
        mensajeView.setText(mensaje);
        mensajeView.setPadding(16, 8, 16, 8);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);

        if (enviado) {
            mensajeView.setBackgroundResource(R.drawable.enviado_bg);
            params.gravity = Gravity.END; // Alineación a la derecha
        } else {
            mensajeView.setBackgroundResource(R.drawable.recibido_bg);
            params.gravity = Gravity.START; // Alineación a la izquierda
        }

        historialLayout.addView(mensajeView, params);
    }

    // Enviar el mensaje a MainActivity
    public void onClick(View view) {
        Log.i("TEST", "Boton pulsado en Actividad2");

        String mensaje = textMsj.getText().toString();
        if (!mensaje.isEmpty()) {
            añadirMensaje(mensaje, true); // Mostrar como enviado
            historialMensajes.add(mensaje); // Agregar solo el mensaje enviado al historial

            Log.i("TEST", "Enviando mensaje desde Actividad2: " + mensaje);
            Log.i("TEST", "Historial antes de cambiar de actividad: " + historialMensajes);

            // Enviar el historial completo a MainActivity
            Intent launchMainActivity = new Intent(this, MainActivity.class);
            launchMainActivity.putStringArrayListExtra("historial", historialMensajes);
            startActivity(launchMainActivity);

            Log.i("TEST", "Cambio de actividad: Actividad2 -> MainActivity");
        }
    }
}
