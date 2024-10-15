package com.example.trabajofinalunidad2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private LinearLayout historialLayout;
    private EditText textMsj;
    private ScrollView scrollView;
    private ArrayList<String> historialMensajes;
    private ArrayList<String> historialHoras;
    private final String TAG = " TEST MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historialLayout = findViewById(R.id.historialLayout);
        textMsj = findViewById(R.id.textMsj);
        scrollView = findViewById(R.id.scrollView);
        historialMensajes = new ArrayList<>();
        historialHoras = new ArrayList<>();

        Log.i(TAG, "Entrando en MainActivity.onCreate");

        // Recuperar historial de mensajes y horas desde Actividad2
        Intent intent = getIntent();
        ArrayList<String> historial = intent.getStringArrayListExtra("historial");
        ArrayList<String> horas = intent.getStringArrayListExtra("horas");

        // Si hay un historial y horas, los mostramos
        if (historial != null && horas != null) {
            historialMensajes = historial;
            historialHoras = horas;
            Log.i(TAG, "Historial recibido en MainActivity: " + historialMensajes + historialHoras);

            for (int i = 0; i < historialMensajes.size(); i++) {
                boolean esEnviado = (i % 2 == 0); // En MainActivity, los mensajes pares son los enviados
                mostrarMensajeConHora(historialMensajes.get(i), historialHoras.get(i), esEnviado);
            }
        }
    }

    // Metodo para añadir mensajes al historial
    private void addMessage(String mensaje, boolean enviado) {
        Log.i(TAG, "Añadiendo mensaje en MainActivity: " + mensaje + ", enviado: " + enviado);

        // Obtener la hora actual y guardarla
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String horaActual = sdf.format(new Date());
        historialHoras.add(horaActual);

        // Guardar el mensaje en el historial
        historialMensajes.add(mensaje);

        // Mostrar el mensaje con su hora
        mostrarMensajeConHora(mensaje, horaActual, enviado);
    }

    // Metodo para mostrar un mensaje junto con su hora
    private void mostrarMensajeConHora(String mensaje, String hora, boolean enviado) {
        // Crear un TextView para la hora
        TextView horaView = new TextView(this);
        horaView.setText(hora);
        horaView.setTextSize(12);
        horaView.setPadding(16, 4, 16, 4);
        horaView.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Crear un TextView para el contenido del mensaje
        TextView mensajeView = new TextView(this);
        mensajeView.setText(mensaje);
        mensajeView.setTextSize(18);
        mensajeView.setPadding(16, 8, 16, 8);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);

        if (enviado) {
            mensajeView.setBackgroundResource(R.drawable.enviado_bg);
            params.gravity = Gravity.END;
        } else {
            mensajeView.setBackgroundResource(R.drawable.recibido_bg);
            params.gravity = Gravity.START;
        }

        // Añadir la hora y el mensaje al layout
        historialLayout.addView(horaView, params);
        historialLayout.addView(mensajeView, params);

        scrollToBottom(); // Desplazar hacia abajo despues de agregar el mensaje
    }

    // Metodo para desplazar el ScrollView hacia abajo
    private void scrollToBottom() {
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    // Enviar el mensaje a Actividad2
    public void onClick(View view) {
        Log.i(TAG, "Boton pulsado en MainActivity");

        String mensaje = textMsj.getText().toString();
        if (!mensaje.isEmpty()) {
            addMessage(mensaje, true);

            Log.i(TAG, "Enviando mensaje desde MainActivity: " + mensaje);
            Log.i(TAG, "Historial antes de cambiar de actividad: " + historialMensajes);

            // Enviar el historial de mensajes y horas a Actividad2
            Intent launchActividad2 = new Intent(this, Actividad2.class);
            launchActividad2.putStringArrayListExtra("historial", historialMensajes);
            launchActividad2.putStringArrayListExtra("horas", historialHoras);
            startActivity(launchActividad2);

            Log.i(TAG, "Cambio de actividad: MainActivity -> Actividad2");
        }
    }
}
