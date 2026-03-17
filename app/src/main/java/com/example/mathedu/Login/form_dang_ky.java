package com.example.mathedu.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.file.FileStore;
import java.util.HashMap;
import java.util.Map;

public class form_dang_ky extends AppCompatActivity {
    EditText edthoten,edtemail,edtpassword;
    Button btndk;
    TextView txtlogin;
    ProgressDialog progressDialog;

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
        //tien trinh
        progressDialog =new ProgressDialog(this);
        // bam textview dang nhap
        txtlogin.setOnClickListener(view -> {
            Intent myintent=new Intent(form_dang_ky.this, DangNhap.class);
            startActivity(myintent);
        });
        //bam button dang ky
        btndk.setOnClickListener(view -> {
            onclickdk();
        });
    }

    private void onclickdk() {

        String hoten=edthoten.getText().toString();
        String email=edtemail.getText().toString();
        String password=edtpassword.getText().toString();
        //Kiem tra dinh dang du lieu
        if(hoten.isEmpty()||email.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!email.contains("@") || !email.contains(".") || email.length()<5){
            Toast.makeText(this, "Vui lòng nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
            return;
        }
       else if (password.length()<6 || !password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")){
            Toast.makeText(this, "Vui lòng nhập mật khẩu có ít nhất 6 ký tự gồm ít nhát 1 chữ cái và số", Toast.LENGTH_SHORT).show();
        }//kiem tra password ít nhất một chữ ít nhất 1 số
        else{
            //luu du lieu vao Authentication
            FirebaseAuth auth= FirebaseAuth.getInstance();
            //Tiep tuc luu vao bang firestore để phân role
            FirebaseFirestore db= FirebaseFirestore.getInstance();
            //bat dau call api show tien trinh
            progressDialog.setMessage("Đang đăng ký...");
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Luu tru vao bang User
                                String uid = auth.getCurrentUser().getUid(); //lay id nguoi dung lu tru trong authen
                                Map<String, Object> user = new HashMap<>();
                                user.put("hoten", hoten);
                                user.put("email", email);
                                user.put("password", password);
                                user.put("nhanvat","avatar1");
                                user.put("role", "Phụ huynh"); // vai trò mặc định

                                db.collection("Users").document(uid)
                                        .set(user)
                                        .addOnSuccessListener(unused -> {

                                            Toast.makeText(form_dang_ky.this,
                                                    "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            // chuyen sang trang login
                                            Intent myintent = new Intent(form_dang_ky.this, DangNhap.class);
                                            startActivity(myintent);
                                            finishAffinity();

                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(form_dang_ky.this,
                                                    "Lưu dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                                        });

                            } else {
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(form_dang_ky.this, "Email đã được sử dụng",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(form_dang_ky.this, "Đăng ký không thành công",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });


        }
    }
}