package com.gse23.fspreng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SpielAnleitung extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spiel_anleitung);
        Button go_Back = findViewById(R.id.go_back);
        go_Back.setOnClickListener(v->{
            Intent intent = new Intent(this, GameView.class);
            startActivity(intent);
        });
    }
}
