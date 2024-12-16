package com.example.examen;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] listacanciones = {"Chirriquitin", "Burrito sabanero", "Campana sobre Campana", "La marimorena", "Peces en el río"};
    List<Musica> playlist = new ArrayList<Musica>();
    ListView vistacanciones;
    ImageView btnpandereta, btnzambomba, btnplay, btnpause, btnstop, fondo;
    SeekBar barra;
    SoundPool sonido;
    MediaPlayer reproductor;
    Runnable handlertask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addMusica();

        // Si pulsamos en la pandereta sonara el sonido de la pandereta con sounpool
        btnpandereta.setOnClickListener(v -> {

        });

        // Si pulsamos en la zambomba sonara el sonido de la zambomba con soundpool
        btnzambomba.setOnClickListener(v -> {
            sonido.play(R.raw.zambomba, 1, 1, 1, 1, 1);
        });

        btnplay.setOnClickListener(v -> {
            if(!reproductor.isPlaying()){
                reproductor.start();
            }
        });

        btnpause.setOnClickListener(v -> {
            if(reproductor.isPlaying()){
                reproductor.pause();
            }
        });

        btnstop.setOnClickListener(v -> {
            try{
                reproductor.stop();
                reproductor.release();
                handlertask = null;
            } catch (Exception e) {}
        });
    }

    public void init(){
        vistacanciones = findViewById(R.id.vistacanciones);
        btnpandereta = findViewById(R.id.btnpandereta);
        btnzambomba = findViewById(R.id.btnzambomba);
        btnstop = findViewById(R.id.btnparar);
        btnpause = findViewById(R.id.btnpausa);
        btnplay = findViewById(R.id.btnplay);
        barra = findViewById(R.id.barra);
        fondo = findViewById(R.id.fondo);

        vistacanciones.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listacanciones));

        vistacanciones.setOnItemClickListener((parent, view, position, id) -> {
            if(reproductor != null){
                reproductor.stop();
                reproductor.release();
            }
            reproductor = MediaPlayer.create(this, playlist.get(position).getCancion());
            reproductor.start();
            barra.setMax(reproductor.getDuration());
            barra.setMin(0);
            barra.setProgress(0);
            empezarTiempo(barra);

            switch (playlist.get(position).getImagen()){
                case 1:
                    fondo.setImageDrawable(getDrawable(R.drawable.chirriquitin));
                    break;
                case 2:
                    fondo.setImageDrawable(getDrawable(R.drawable.burritosabanero));
                    break;
                case 3:
                    fondo.setImageDrawable(getDrawable(R.drawable.campana));
                    break;
                case 4:
                    fondo.setImageDrawable(getDrawable(R.drawable.marimorena));
                    break;
                case 5:
                    fondo.setImageDrawable(getDrawable(R.drawable.pecesrio));
                    break;
            }
        });

        // Cuando el usuario cambie la posicion de la barra el mediaplayer debera irse a ese tiempo

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reproductor.isPlaying()){
            reproductor.stop();
            reproductor = null;
        }
        if(sonido != null){
            sonido = null;
        }
        handlertask = null;
    }

    public void addMusica(){
        playlist.add(new Musica("Chirriquitin", 1, R.raw.chirriquitin));
        playlist.add(new Musica("Burrito sabanero", 2, R.raw.burritosabanero));
        playlist.add(new Musica("Campana sobre Campana", 3, R.raw.campana));
        playlist.add(new Musica("La marimorena", 4, R.raw.marimorena));
        playlist.add(new Musica("Peces en el río", 5, R.raw.pecesrio));
    }

    private void empezarTiempo(SeekBar barra){
        if(handlertask == null){
            handlertask = () -> {
                barra.setProgress(reproductor.getCurrentPosition());
                new Handler().postDelayed(handlertask, 1000);
            };
            handlertask.run();
        }
    }
}