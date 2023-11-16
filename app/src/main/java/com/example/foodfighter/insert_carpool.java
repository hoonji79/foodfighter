//package com.example.myapplication;
//
//import android.content.Intent;
//import android.media.Image;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//
//public class insert_carpool extends AppCompatActivity {
//
//    private ImageView map_icon, map_icon2;
//    private EditText departure,destination,selectdate, time, comment, tag,people;
//    private Button ragist_bt;
//    private FirebaseAuth mFirebaseAuth;
//    private DatabaseReference mDatabaseRef;
//    private Intent intent;
//
//
//
//    public String getCurrentTime() {  // 현재 시간 구하는 함수
//        long currentTime = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 출력 형식을 지정할 수 있습니다.
//        Date currentDate = new Date(currentTime);
//        return sdf.format(currentDate);
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.insert_carpool);
//
//        map_icon = findViewById(R.id.map_icon);
//        map_icon2 = findViewById(R.id.map_icon2);
//        departure = findViewById(R.id.Start);
//        destination = findViewById(R.id.end);
//        selectdate = findViewById(R.id.selectdate);
//        time = findViewById(R.id.time);
//        comment = findViewById(R.id.comment);
//        tag = findViewById(R.id.tag1);
//        people = findViewById(R.id.people);
//        ragist_bt = findViewById(R.id.ragist_bt);
//        String currentTime = getCurrentTime(); // 현재 시간
//
//        ragist_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mFirebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth 초기화
//                mDatabaseRef = FirebaseDatabase.getInstance().getReference(); // DatabaseReference 초기화
//                // 이메일 값을 받기 위해 Intent 초기화
//
//                HashMap<String, Object>  carpoolData = new HashMap<>(); //데이터를 저장할 HashMap생성
//
//                intent = getIntent();
//                String email = intent.getStringExtra("email");
//                String emailPath = email.replace(".", "_").trim(); // '.'을 '_'로 변환한 경로 생성
//                // Firebase Realtime Database에 데이터 저장
//
//                carpoolData.put("departure", departure.getText().toString());
//                carpoolData.put("destination", destination.getText().toString());
//                carpoolData.put("pickup_time", time.getText().toString());
//                carpoolData.put("cost", 0);
//                carpoolData.put("distance", 0);
//                carpoolData.put("createdAt", currentTime);
//                carpoolData.put("comment", comment.getText().toString());
//                carpoolData.put("max_people", people.getText().toString());
//                carpoolData.put("tag", tag.getText().toString());
//
//                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("carpool").child(emailPath);
//                userRef.setValue(carpoolData);
//                Toast.makeText(insert_carpool.this, "등록 완료", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//
//        map_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(insert_carpool.this , mapfragment.class);
//                startActivity(intent);
//            }
//        });
//        map_icon2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(insert_carpool.this , mapfragment.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//
//    }
//}
