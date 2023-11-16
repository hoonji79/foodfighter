package com.example.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Signup extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private EditText userId, password,checkPassword,nickname,email;
    private Button confirm;
    private CheckBox AGREE_TERMS, AGREE_INFORMATION, AGREE_GPS;
    private TextView view_TERMS, view_INFORMATION, view_GPS;


    public String getCurrentTime() {  // 현재 시간을 구하기
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 출력 형식을 지정
        Date currentDate = new Date(currentTime);
        return sdf.format(currentDate);
    }
    public boolean isValidEmail(String email) {  // 이메일 형식을 검증하기 위한 정규표현식
        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailRegex);  // true, false 반환
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.signup);

        email = findViewById(R.id.setEmail);
        password = findViewById(R.id.setPw);
        checkPassword = findViewById(R.id.setPw2);  // 비밀번호 재확인
        userId = findViewById(R.id.setId);
        nickname = findViewById(R.id.setNickName);

        confirm = findViewById(R.id.confirm);

        AGREE_TERMS = findViewById(R.id.AGREE_TERMS);
        AGREE_INFORMATION = findViewById(R.id.AGREE_INFORMATION);
        AGREE_GPS = findViewById(R.id.AGREE_GPS);

        view_TERMS = findViewById(R.id.view_TERMS);
        view_INFORMATION = findViewById(R.id.view_INFORMATION);
        view_GPS = findViewById(R.id.view_GPS);

        mFirebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth 초기화
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        // ...

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if (isValidEmail(userEmail)) {  // 이메일 형식이 맞다면
                    if (password.getText().toString().equals(checkPassword.getText().toString())) {  // 비밀번호 체크

                        // 비밀번호 길이 체크
                        if (password.length() < 6) {
                            Toast.makeText(Signup.this, "비밀번호를 6자리 이상 입력해 주세요!", Toast.LENGTH_SHORT).show();
                            return; // 길이가 충족되지 않으면 함수 종료
                        }

                        //  필수 동의 체크 여부 확인.
                        boolean isChecked_TERMS = AGREE_TERMS.isChecked();
                        boolean isChecked_IMFO = AGREE_INFORMATION.isChecked();
                        boolean isChecked_GPS = AGREE_GPS.isChecked();


                        if (!isChecked_TERMS || !isChecked_IMFO || !isChecked_GPS) { // 체크 안 한 것이 있다면
                            Toast.makeText(Signup.this, "필수 동의를 체크해 주세요!", Toast.LENGTH_SHORT).show();

                        } else {  // 정상 체크 시

                            // firebase Authentication 사용자 생성
                            mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                    .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
//                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                        try {  // 데이터 삽입

//                                            String UId = firebaseUser.getUid();
                                            String currentTime = getCurrentTime();
                                            HashMap<String, Object> data = new HashMap<>();
                                            data.put("id", userId.getText().toString());  // 문자열로 변환
                                            data.put("nickname", nickname.getText().toString());  // 문자열로 변환
                                            data.put("createdAt", currentTime);
                                            data.put("password", password.getText().toString());  // 문자열로 변환
                                            data.put("email", userEmail);
//
                                            db.collection("users")  // 데이터 삽입
                                                    .document(userId.getText().toString())
                                                    .set(data)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(Signup.this,"success", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Signup.this, Login.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                        } finally {

                                        }
                                    } else {
                                        String errorMessage = task.getException().getMessage();
                                        if (errorMessage.contains("email address is already in use")) {
                                            // 이미 사용 중인 이메일 주소인 경우
                                            Toast.makeText(Signup.this, "이미 사용 중인 이메일 주소입니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // 기타 오류인 경우
                                            Toast.makeText(Signup.this, "오류: " + errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(Signup.this, "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signup.this, "이메일이 입력되지 않았거나 올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
