package com.example.mathedu.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.MainActivity;
import com.example.mathedu.R;
import com.google.firebase.auth.FirebaseAuth;

public class Flash extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    Runnable gotomain = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(Flash.this, DangKY.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Dangky), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setContentView(R.layout.activity_flash);

        // Post delayed để chờ 3s rồi chuyển
        handler.postDelayed(gotomain, 2000);
    }
}