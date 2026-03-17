package com.example.mathedu.Login;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenPass extends AppCompatActivity {
    EditText edtemail;
    Button btnxacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quen_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtemail=findViewById(R.id.edtemail);
        btnxacnhan=findViewById(R.id.btnxacnhan);

        //bam xac nhan
        btnxacnhan.setOnClickListener(view -> {
           // String email=edtemail.getText().toString();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = "dduong290517@gmail.com";

            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuenPass.this, "Link xác nhận đã được gửi đến Email của bạn.Vui lòng kiểm tra Email.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Log.e("Error",task.getException().getMessage());
                                Toast.makeText(QuenPass.this, "Đã xảy ra lỗi.Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}