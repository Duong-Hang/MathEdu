package com.example.mathedu.PH;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePass extends AppCompatActivity {
    ImageButton imgbtnql;
    Button btnxacnhan;
    EditText edtPassnew,edtpassverf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgbtnql=findViewById(R.id.imgbtnql);
        btnxacnhan=findViewById(R.id.btnxn);
        edtPassnew=findViewById(R.id.edtPassnew);
        edtpassverf=findViewById(R.id.edtpassverf);
        //quay lai
        imgbtnql.setOnClickListener(view -> {
            finish();
        });
        //xac nhan
        btnxacnhan.setOnClickListener(view -> {
            String passnew=edtPassnew.getText().toString();
            String passverf=edtpassverf.getText().toString();
            FirebaseFirestore db= FirebaseFirestore.getInstance();
            if(passnew.isEmpty() || passverf.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            }
            else if(!passnew.equals(passverf)){
                Toast.makeText(this, "Vui lòng nhập đúng Password như trên.", Toast.LENGTH_SHORT).show();
            }
            else if(passnew.length()<6 || !passnew.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")){
                Toast.makeText(this, "Vui lòng nhập mật khẩu có ít nhất 6 ký tự gồm ít nhát 1 chữ cái và số", Toast.LENGTH_SHORT).show();
            }
            else{
                //cap nhat lai pass
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = passnew;
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePass.this,"Mật khẩu  đã được cập nhật",Toast.LENGTH_SHORT).show();
                                    edtPassnew.setText("");
                                    edtpassverf.setText("");
                                    db.collection("Users").document(user.getUid()).update("password",newPassword);
                                }
                                else{
                                    Toast.makeText(ChangePass.this,"Cập nhật thất bại.Hãy đăng nhập lại để thay đổi.",Toast.LENGTH_SHORT).show();
                                    Log.e("Error",task.getException().getMessage());
                                }
                            }
                        });
            }

        });

    }
}