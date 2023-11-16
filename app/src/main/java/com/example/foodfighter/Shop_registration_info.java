package com.example.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Shop_registration_info extends AppCompatActivity {


    EditText ShopName, Address, Representative, Phone;
    Button Category, Confirm, BusinessLicense;


    private int getRandomStoreNumber() {
        // 100000 ~ 999999 범위의 6자리 정수를 생성
        return (int) (Math.random() * 900000) + 100000;
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.shop_registration_info);

        ShopName = findViewById(R.id.setShopName);
        Address = findViewById(R.id.setAddress);
        Representative = findViewById(R.id.setName);
        BusinessLicense = findViewById(R.id.setBusinessLicense);
        Phone = findViewById(R.id.setPhone);

        Category = findViewById(R.id.setCategory);
        Confirm = findViewById(R.id.confirm);

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> data = new HashMap<>();
                int randomStoreNumber = getRandomStoreNumber(); // 랜덤 가게 번호.
                data.put("shop_name", ShopName.getText().toString());
                data.put("address", Address.getText().toString());
                data.put("representative", Representative.getText().toString());
                data.put("phone", Phone.getText().toString());

                data.put("businessLicense", 0); // storage 연결 예정
                data.put("category", 0); // category 선택 예정
                data.put("review", 0); // default
                data.put("businessHours", 0); // 영업시간 연결 예정

                // Firestore에 데이터 추가 (문서 ID로 storeNumber 사용)
                db.collection("stores")
                        .document(String.valueOf(randomStoreNumber)) // storeNumber를 문서 ID로 사용
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Shop_registration_info.this, "success, Document ID: " + randomStoreNumber, Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Shop_registration_info.this, Main.class);
//                                startActivity(intent);
                            }
                        });
            }
        });

    }
}
