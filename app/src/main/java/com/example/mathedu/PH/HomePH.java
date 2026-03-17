package com.example.mathedu.PH;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.mathedu.HS.History;
import com.example.mathedu.HS.Xephang;
import com.example.mathedu.Login.DangNhap;
import com.example.mathedu.Login.form_dang_ky;
import com.example.mathedu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomePH extends AppCompatActivity {
    LinearLayout lntkhs,lnupdate,lnbaocao,lnhistory,lnxephang,lnchangepass,lndangxuat;
    ImageButton imgbtnql,imgbtntb;
    ImageView imgavatar;
    TextView tvname;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgbtnql=findViewById(R.id.imgbtnql);
        imgbtntb=findViewById(R.id.imgbtntb);
        lnbaocao=findViewById(R.id.lnbaocao);
        lnchangepass=findViewById(R.id.lnchangepass);
        lnhistory=findViewById(R.id.lnhistory);
        lnupdate=findViewById(R.id.lnupdate);
        lnxephang=findViewById(R.id.lnxephang);
        lndangxuat=findViewById(R.id.lndangxuat);
        lntkhs=findViewById(R.id.lntkhs);
        imgavatar=findViewById(R.id.imgavatar);
        tvname=findViewById(R.id.tvname);
        //show infor
        showinfor();
        //tạo tài khoản học sinh
        lntkhs.setOnClickListener(view -> {
            Intent myintent=new Intent(HomePH.this, TKHs.class);
            startActivity(myintent);
        });
        //quay lai
        imgbtnql.setOnClickListener(view -> {
            finish();
        });
        //thong bao
        imgbtntb.setOnClickListener(view -> {
            Intent myintent=new Intent(HomePH.this,Notification.class);
            startActivity(myintent);
        });
        //Đổi mật khẩu
        lnchangepass.setOnClickListener(view -> {
            Intent myintent=new Intent(HomePH.this,ChangePass.class);
            startActivity(myintent);
        });

        //Đăng xuất
        lndangxuat.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent myintent=new Intent(HomePH.this, DangNhap.class);
            startActivity(myintent);
            finishAffinity();
        });
        //cap nhat
        lnupdate.setOnClickListener(view -> {
            Intent myintent=new Intent(HomePH.this,Update.class);
            myintent.putExtra("name",name);
            startActivity(myintent);
        });
        //bao cao thong ke
        lnbaocao.setOnClickListener(view->{
            Intent myintent=new Intent(HomePH.this,Baocao.class);
            startActivity(myintent);
        });
        //lich su
        lnhistory.setOnClickListener(view->{
            Intent myintent=new Intent(HomePH.this, History.class);
            startActivity(myintent);
        });
        //Xep hang
        lnxephang.setOnClickListener(view -> {
            Intent myintent=new Intent(HomePH.this, Xephang.class);
            startActivity(myintent);
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        showinfor();
    }
    private void showinfor() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        //chua login
        if(user==null){
            return;
        }
        else{
            String uid=user.getUid();
            db.collection("Users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot ->
                    {
                        name=documentSnapshot.getString("hoten");
                        String avatar=documentSnapshot.getString("nhanvat");
                        if(name==null){
                            tvname.setVisibility(View.GONE);
                        }
                        else{
                            tvname.setVisibility(View.VISIBLE);
                        }
                        tvname.setText(name);
                        
                        Map<String,Integer> avatarMap = new HashMap<>();
                        avatarMap.put("avatar1",R.drawable.avatar1);
                        avatarMap.put("avatar2",R.drawable.avatar2);
                        avatarMap.put("avatar3",R.drawable.avatar3);
                        avatarMap.put("avatar4",R.drawable.avatar4);
                        avatarMap.put("avatar5",R.drawable.avatar5);
                        avatarMap.put("avatar6",R.drawable.avatar6);

                        Integer avatarRes = avatarMap.get(avatar);

                        if(avatarRes != null){
                            imgavatar.setImageResource(avatarRes);
                        }

                    })
                    .addOnFailureListener(e-> {
                        //xu ly loi
                        Toast.makeText(HomePH.this, "Tải dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                        Log.e("Error",e.getMessage());

                    });
        }

    }
}