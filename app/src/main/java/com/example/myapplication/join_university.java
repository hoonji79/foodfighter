package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class join_university extends AppCompatActivity {
    private static final int REQUEST_IMAGE_SELECTION = 1001;  // 이미지 선택 요청 상수
    EditText user_id,name;
    Button url_bt,next_bt;
    private String selectedImageUrl;  // 학생증 URL
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_university);


        user_id = (EditText) findViewById(R.id.user_id);
        name = (EditText) findViewById(R.id.name);
        url_bt = (Button) findViewById(R.id.setImage);  // URL 미완성 -05.31
        next_bt = (Button) findViewById(R.id.nextBt);


        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || user_id.getText().toString().equals("")) {  // 빈칸 확인
                    Toast.makeText(join_university.this, "이름 또는 학번이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    if (name.getText().toString().length() < 2 || user_id.getText().toString().length() != 10) { // 이름이 1자거나 학번이 9자리 이하일 때
                        Toast.makeText(join_university.this, "이름 2자 이상, 학번 10자로 입력해 주세요", Toast.LENGTH_SHORT).show();
                    }else {
                        // 데이터 전달
                        Intent intent = new Intent(join_university.this, join_password.class);
                        intent.putExtra("user_id", user_id.getText().toString()); // 데이터 추가
                        intent.putExtra("name", name.getText().toString());
                        if (selectedImageUrl != null) {  // 테스트 단계에선 이미지 필수 x
                            intent.putExtra("image_url", selectedImageUrl);
                        }
                        startActivity(intent);
                    }
                }
            }
        });

        url_bt.setOnClickListener(new View.OnClickListener() { // 이미지 업로드 클릭
            @Override
            public void onClick(View v) {
                // 갤러리 앱 호출
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_SELECTION);
            }
        });

    }

// 이미지 URL storage로 업로드 진행 중


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_SELECTION && resultCode == RESULT_OK) {
            // 선택된 이미지의 URI를 가져옴
            Uri selectedImageUri = data.getData();

            // URI를 문자열로 변환하여 저장
            selectedImageUrl = selectedImageUri.toString();
            Toast.makeText(join_university.this, "" + selectedImageUrl, Toast.LENGTH_SHORT).show();
        }
    }
}
