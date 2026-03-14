package com.example.mathedu.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.R;

public class DangNhap extends AppCompatActivity {
    Button btnlogin;
    EditText edtemail,edtpassword;
    TextView txtdk;
    RadioButton radph,radhs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnlogin=findViewById(R.id.btnlogin);
        edtemail=findViewById(R.id.edtemail);
        edtpassword=findViewById(R.id.edtpassword);
        txtdk=findViewById(R.id.txtdk);
        radph=findViewById(R.id.radph);
        radhs=findViewById(R.id.radhs);
        // bam textview dang ky
        txtdk.setOnClickListener(view -> {
            Intent myintent=new Intent(DangNhap.this, form_dang_ky.class);
            startActivity(myintent);
        });

    }
}