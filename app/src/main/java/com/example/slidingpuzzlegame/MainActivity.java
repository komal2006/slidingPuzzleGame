package com.example.slidingpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FifteenSquares board = findViewById(R.id.board);
        SurfaceView game = findViewById(R.id.board);
        Button button = findViewById(R.id.Reset);
        button.setOnClickListener(board);
        game.setOnTouchListener(board);

    }
}
