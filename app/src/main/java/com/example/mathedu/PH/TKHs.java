package com.example.mathedu.PH;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TKHs extends AppCompatActivity {
    EditText edthoten,edtpassword;
    Button btntao;
    ImageButton imgbtnql;
    Spinner spinner;
    String lop;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tkhs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edthoten=findViewById(R.id.edthoten);
        edtpassword=findViewById(R.id.edtpassword);
        btntao=findViewById(R.id.btntao);
        imgbtnql=findViewById(R.id.imgbtnql);
        spinner=findViewById(R.id.spinner);
        progressDialog =new ProgressDialog(this);
        //quay lai
        imgbtnql.setOnClickListener(view -> {
            finish();
        });
        //tao tai khoan
        btntao.setOnClickListener(view -> {
            Checkten();
        });
        //chon lop
        String[] classes={"Lớp 1","Lớp 2","Lớp 3","Lớp 4","Lớp 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            lop= spinner.getSelectedItem().toString();
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(TKHs.this,"Vui lòng chọn lớp",Toast.LENGTH_SHORT).show();
          }
      });
    }

    private void onclicktao() {
        String hoten=edthoten.getText().toString();
        String password=edtpassword.getText().toString();

        if(hoten.isEmpty()||password.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.length()<6 || !password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")){
            Toast.makeText(this, "Vui lòng nhập mật khẩu có ít nhất 6 ký tự gồm ít nhát 1 chữ cái và số", Toast.LENGTH_SHORT).show();
        }//kiem tra password ít nhất một chữ ít nhất 1 số
        else{
            FirebaseFirestore db= FirebaseFirestore.getInstance();
            String UserId =FirebaseAuth.getInstance().getCurrentUser().getUid();//lay id phu huynh hien tai

            progressDialog.setMessage("Đang đăng ký...");
            progressDialog.show();
            //luu du lieu vao bang firestore
            Map<String, Object> student = new HashMap<>();
            student.put("hoten", hoten);
            student.put("password", password);
            student.put("lop", lop);
            student.put("avatar","avatar1");
            student.put("CurrentStar",0);
            student.put("Status","Đang hoạt động");
            student.put("CompletedLessonCount",0);
            student.put("UserID",UserId);
            progressDialog.dismiss();
            db.collection("Students").add(student)
                    .addOnSuccessListener(documentReference ->{
                        Toast.makeText(TKHs.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();

                    })
                    .addOnFailureListener(v->{
                        Toast.makeText(TKHs.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        Log.e("Error",v.getMessage());
                    });

        }
    }
    private void Checkten(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Students").whereEqualTo("hoten",edthoten.getText().toString()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        Toast.makeText(TKHs.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        onclicktao();//neu chua thi cho phep them
                    }
                })
                .addOnFailureListener(v->{
                    Toast.makeText(TKHs.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                });
    }
}