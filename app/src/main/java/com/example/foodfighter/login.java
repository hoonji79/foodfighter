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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText id,pw;
    private TextView signUp,findId;
    private CheckBox keepLogin;
    private Button login,naverLogin;

    private FirebaseAuth mAuth;
    private String selectedImageUrl;  // 학생증 URL
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.login);

        id = findViewById(R.id.setId);
        pw = findViewById(R.id.setPw);
        signUp = findViewById(R.id.signUp);
        findId = findViewById(R.id.findId);
        keepLogin = findViewById(R.id.keepLogin);
        login = findViewById(R.id.login);
        naverLogin = findViewById(R.id.naverLogin);

        mAuth = FirebaseAuth.getInstance();


        String userId = id.getText().toString();
        String password = pw.getText().toString();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {  // 앱 로그인 버튼.
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(userId + "@naver.com", password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 로그인 성공
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(Login.this, Main.class);
                                    startActivity(intent);
                                    // 사용자 정보를 가져올 수 있습니다.
                                } else {
                                    // 로그인 실패
                                    Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}
