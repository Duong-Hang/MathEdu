package com.example.mathedu.Login;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.MainActivity;
import com.example.mathedu.PH.HomePH;
import com.example.mathedu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DangNhap extends AppCompatActivity {
    Button btnlogin;
    EditText edtemail, edtpassword;
    TextView txtdk, txtquen;


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
        btnlogin = findViewById(R.id.btnlogin);
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        txtdk = findViewById(R.id.txtdk);
        txtquen = findViewById(R.id.txtquen);
        // bam textview dang ky
        txtdk.setOnClickListener(view -> {
            Intent myintent = new Intent(DangNhap.this, form_dang_ky.class);
            startActivity(myintent);
        });
        //bam button login
        btnlogin.setOnClickListener(view -> {
            loginstudents();
        });
        //bam quen mat khau
        txtquen.setOnClickListener(view -> {
            Intent myintent = new Intent(DangNhap.this, QuenPass.class);
            startActivity(myintent);
        });

    }

    private void loginParents() {
        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();

        //Kiem tra dinh dang du lieu
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        //dăng nhap
        else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            String uid = auth.getCurrentUser().getUid();
                            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent myintent = new Intent(DangNhap.this, HomePH.class);
                            startActivity(myintent);

                        } else {
                            Log.e("Error", task.getException().getMessage());
                            Toast.makeText(this, "Đăng nhập thất bại.Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            edtpassword.setText("");
                        }

                    });

        }

    }

    private void loginstudents() {
        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();

        //Kiem tra dinh dang du lieu
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        //dăng nhap
        else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Students").whereEqualTo("hoten", email).get()
                    .addOnSuccessListener(documentSnapshots -> {
                        if (!documentSnapshots.isEmpty()) {
                            Intent myintent = new Intent(DangNhap.this, MainActivity.class);
                            startActivity(myintent);
                        } else {
                            loginParents();
                        }
                    })
                    .addOnFailureListener(v -> {
                        Toast.makeText(this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                    });

        }
    }
}
