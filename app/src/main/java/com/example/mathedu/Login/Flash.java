package com.example.mathedu.Login;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.MainActivity;
import com.example.mathedu.PH.HomePH;
import com.example.mathedu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Flash extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    Runnable gotomain = new Runnable() {
        @Override
        public void run() {

            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            //chua login
            if(user ==null){
                startActivity(new Intent(Flash.this, DangKY.class));
                finish();
            }
            else{
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String uid=auth.getCurrentUser().getUid();
                //kiem tra trong bang user
                db.collection("Users").document(uid).get()
                        .addOnSuccessListener(
                                documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        String role = documentSnapshot.getString("role");
                                        if (role.equals("Phụ huynh")) {
                                            Intent myintents = new Intent(Flash.this, HomePH.class);
                                            startActivity(myintents);
                                            finish();
                                        }
                                    }
                                    //neu ko tim thay trong user thi chuyen sang collection students
                                    else{
                                        db.collection("Students").document(uid).get()
                                                .addOnSuccessListener(
                                                        documentSnapshot1 -> {
                                                            if(documentSnapshot1.exists()){
                                                                Intent myintent = new Intent(Flash.this, MainActivity.class);
                                                                startActivity(myintent);
                                                                finish();
                                                            }
                                                        }
                                                )
                                                .addOnFailureListener(
                                                        e -> {
                                                            Log.w(TAG, "Error getting documents.", e);
                                                            Toast.makeText(Flash.this,"Không tìm thấy tài khoản",Toast.LENGTH_SHORT).show();
                                                        });
                                    }

                                }
                        )
                        .addOnFailureListener(
                                e -> {
                                    Log.w(TAG, "Error getting documents.", e);
                                    Toast.makeText(Flash.this,"Không tìm thấy tài khoản",Toast.LENGTH_SHORT).show();
                                }
                        );
            }
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

        // Post delayed để chờ 3s rồi chuyển
        handler.postDelayed(gotomain, 2000);
    }
}