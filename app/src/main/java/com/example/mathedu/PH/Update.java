package com.example.mathedu.PH;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mathedu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    Button btnxacnhan,btnavatar;
    EditText edtname,edtemail;
    TextView txtql;
    ImageButton avatar1,avatar2,avatar3,avatar4,avatar5,avatar6,btnql;
    CardView Cardavatar;
    MaterialCardView Cardtt;
    ImageView imgavatar;
    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnxacnhan=findViewById(R.id.btnxn);
        btnavatar=findViewById(R.id.btnavatar);
        edtname=findViewById(R.id.edtname);
        edtemail=findViewById(R.id.edtemail);
        btnql=findViewById(R.id.btnql);
        //avatar
        avatar1=findViewById(R.id.avatar1);
        avatar2=findViewById(R.id.avatar2);
        avatar3=findViewById(R.id.avatar3);
        avatar4=findViewById(R.id.avatar4);
        avatar5=findViewById(R.id.avatar5);
        avatar6=findViewById(R.id.avatar6);
        Cardavatar=findViewById(R.id.Cardavatar);
        Cardtt=findViewById(R.id.Cardtt);
        imgavatar=findViewById(R.id.imgavatar);

       user = FirebaseAuth.getInstance().getCurrentUser();
       db = FirebaseFirestore.getInstance();
        //lay avatar cu
        loadavatar();
        //tu dong dien ten
        if (user != null) {
            Intent intent=getIntent();
            String name= intent.getStringExtra("name");
            edtname.setText(name);
            //email
            String email = user.getEmail();
            if (email != null) {
                edtemail.setText(email);
            }
        }
        //quay lai
       btnql.setOnClickListener(v->{
            if(Cardavatar.getVisibility()==CardView.VISIBLE){
                Cardavatar.setVisibility(CardView.GONE);
                Cardtt.setVisibility(CardView.VISIBLE);
            }
            else{
                finish();
            }
       });
        //xac nhan
        btnxacnhan.setOnClickListener(view -> {
            String name=edtname.getText().toString();
            String email=edtemail.getText().toString();
            FirebaseFirestore db= FirebaseFirestore.getInstance();
            //dat lai ten
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Update.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                db.collection("Users").document(user.getUid()).update("hoten",name);
                            }
                            else{
                                Toast.makeText(Update.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("Error",task.getException().getMessage());
                            }
                        }
                    });

            //dat lai email

            user.updateEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Update.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                db.collection("Users").document(user.getUid()).update("email",email);
                            }
                            else {
                                Toast.makeText(Update.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("Error",task.getException().getMessage());
                            }
                        }
                    });

        });
        //thay doi avatar
        btnavatar.setOnClickListener(v->{
            Cardavatar.setVisibility(CardView.VISIBLE);
            Cardtt.setVisibility(CardView.GONE);
        });
        //chon nhan vat-lay nhan vat tam thơi
        avatar1.setOnClickListener(view -> Selectavatar(R.drawable.avatar1,"avatar1"));
        avatar2.setOnClickListener(view -> Selectavatar(R.drawable.avatar2,"avatar2"));
        avatar3.setOnClickListener(view -> Selectavatar(R.drawable.avatar3,"avatar3"));
        avatar4.setOnClickListener(view -> Selectavatar(R.drawable.avatar4,"avatar4"));
        avatar5.setOnClickListener(view -> Selectavatar(R.drawable.avatar5,"avatar5"));
        avatar6.setOnClickListener(view -> Selectavatar(R.drawable.avatar6,"avatar6"));


    }

    private void loadavatar() {
        //hien thi avatar tu firebase
        if(user == null) return;
        db.collection("Users").document(user.getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String avatar=documentSnapshot.getString("nhanvat");
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
                }).addOnFailureListener(v->{
                   Toast.makeText(this,"Tải dữu liệu thất bại",Toast.LENGTH_SHORT).show();
                });
    }
    //khi quay lai

    private void Selectavatar(int imageRes, String avatarName) {

        if(user == null) return;

        db.collection("Users")
                .document(user.getUid())
                .update("nhanvat", avatarName);

        Toast.makeText(Update.this,"Set avatar thành công",Toast.LENGTH_SHORT).show();
        imgavatar.setImageResource(imageRes);

    }
}