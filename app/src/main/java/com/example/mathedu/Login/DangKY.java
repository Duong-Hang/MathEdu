package com.example.mathedu.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.R;

public class DangKY extends AppCompatActivity {
    Button btnDangKy, btnDangNhap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Dangky), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent=new Intent(DangKY.this, form_dang_ky.class);
                startActivity(myintent);
            }
        });
      btnDangNhap.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(DangKY.this, DangNhap.class);
              startActivity(intent);
          }
      });
    }

}