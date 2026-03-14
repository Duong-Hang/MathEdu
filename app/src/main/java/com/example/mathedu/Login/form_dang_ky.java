package com.example.mathedu.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.R;

public class form_dang_ky extends AppCompatActivity {
    EditText edthoten,edtemail,edtpassword;
    Button btndk;
    TextView txtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Dangky), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edthoten=findViewById(R.id.edthoten);
        edtemail=findViewById(R.id.edtemail);
        edtpassword=findViewById(R.id.edtpassword);
        btndk=findViewById(R.id.btnlogin);
        txtlogin=findViewById(R.id.txtdk);
        // bam textview dang nhap
        txtlogin.setOnClickListener(view -> {
            Intent myintent=new Intent(form_dang_ky.this, DangNhap.class);
            startActivity(myintent);
        });
    }
}