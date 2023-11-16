//package com.example.foodfighter;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import java.text.SimpleDateFormat;
//import android.content.Intent;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.util.Date;
//import java.util.HashMap;
//
//
//public class join_password extends AppCompatActivity {
//
//    private FirebaseAuth mFirebaseAuth;
//    private DatabaseReference mDatabaseRef;
//    private EditText email, password,checkPwd;
//    private Button complete_btn;
//    private TextView chkPwdText,ex;
//
//    private CheckBox info,gps;
//
//
//    public String getCurrentTime() {  // 현재 시간 구하는 함수
//        long currentTime = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 출력 형식을 지정할 수 있습니다.
//        Date currentDate = new Date(currentTime);
//        return sdf.format(currentDate);
//    }
//    public boolean isValidEmail(String email) {
//        // 이메일 형식을 검증하기 위한 정규표현식
//        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        // true, false 반환
//        return email.matches(emailRegex);
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.join_password);
//
//
////이전 액티비티에서 전달된 데이터 가져오기
//        Intent intent = getIntent();
//        String user_id = intent.getStringExtra("user_id"); //학번
//        String name = intent.getStringExtra("name"); //이름
//        String card_url = intent.getStringExtra("image_url");   // URL미완성
//        final String[] imageURL = new String[1];  // URL용
//
//        email = findViewById(R.id.setEmail);
//        password = findViewById(R.id.setPwd);
//        checkPwd = findViewById(R.id.chkPwd);//비밀번호 재확인
//        chkPwdText = findViewById(R.id.chkPwdText);
//        complete_btn = findViewById(R.id.comp_register);
//
//         info = findViewById(R.id.info);
//         gps = findViewById(R.id.gps);
//        RadioButton checkedMan = findViewById(R.id.man);
//        checkedMan.setChecked(true);
//        RadioGroup radioGroup = findViewById(R.id.radioGroup);
//        int selectRadioBtn = radioGroup.getCheckedRadioButtonId();
//
//        mFirebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth 초기화
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference(); // DatabaseReference 초기화
//
//
//
//        complete_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userEmail = email.getText().toString();
//                if (isValidEmail(userEmail)) { // 이메일 형식이 맞다면
//                    if (password.getText().toString().equals(checkPwd.getText().toString())) {   // ㅣ비밀번호, 비밀번호확인이 같다면.
//                        boolean isCheckedInfo = info.isChecked();
//                        boolean isCheckedGps = gps.isChecked();
//                        if (!isCheckedGps || !isCheckedInfo) {  // 동의 2개가 체크 확인
//                            Toast.makeText(join_password.this, "개인정보수집 동의와 위치정보사용 동의는 필수입니다.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            String strPwd = password.getText().toString();
//                            RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
//
//                            mFirebaseAuth.createUserWithEmailAndPassword(userEmail, strPwd).addOnCompleteListener(join_password.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
//                                        try {
//                                            if (card_url != null) {   // Image URL 처리
//                                                // Firebase Storage의 레퍼런스 가져오기
//                                                FirebaseStorage storage = FirebaseStorage.getInstance();
//                                                StorageReference storageRef = storage.getReference();
//
//                                                // 이미지를 업로드할 경로 및 파일명 생성
//                                                String fileName = "StudentCard"+".jpg"; // 예시 파일명, 실제로는 고유한 파일명을 사용하는 것이 좋습니다.
//                                                StorageReference imageRef = storageRef.child("images/"+userEmail +"/"+ fileName);
//
//                                                // 이미지 업로드
//                                                UploadTask uploadTask = imageRef.putFile(Uri.parse(card_url));
//                                                uploadTask.addOnCompleteListener(join_password.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                                        if (task.isSuccessful()) {
//                                                            // 이미지 업로드 성공 시9
//                                                            Toast.makeText(join_password.this, "이미지 업로드 완료", Toast.LENGTH_SHORT).show();
//
//                                                            // 업로드된 이미지의 다운로드 URL 저장
//                                                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                                @Override
//                                                                public void onSuccess(Uri uri) {
//                                                                    imageURL[0] = uri.toString();
//                                                                }
//                                                            });
//                                                        } else {
//                                                            // 이미지 업로드 실패
//                                                            Toast.makeText(join_password.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                });
//                                            }
//
//                                            String userId = firebaseUser.getUid(); // ID토근
//                                            String currentTime = getCurrentTime(); // 현재 시간
//
//
//                                            HashMap<String, Object> userData = new HashMap<>(); //데이터를 저장할 HashMap생성
//
//                                            // Firebase Realtime Database에 데이터 저장
//                                            userData.put("card_url", card_url);
//                                            userData.put("name", name);
//                                            userData.put("sex", selectedRadioButton.getText().toString());
//                                            userData.put("user_id", user_id);
//                                            userData.put("createdAt", currentTime);
//                                            userData.put("password", strPwd);
//                                            userData.put("agreeInfo", isCheckedInfo);
//                                            userData.put("agreeGps", isCheckedGps);
//                                            userData.put("review",0);
//                                            userData.put("profileUrl",null);
//                                            userData.put("pushAlarm",false);
//                                            userData.put("home",null);
//                                            userData.put("car_number",null);
//
//                                            String emailPath = userEmail.replace(".", "_").trim(); // '.'을 '_'로 변환한 경로 생성
//
//                                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(emailPath);
//                                            userRef.setValue(userData);
//                                            Toast.makeText(join_password.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
//
//
//                                        } catch (Exception e) {  // 혹시 몰라서 냅둠
//                                            Toast.makeText(join_password.this, "다시 한번 클릭해 주세요" + e, Toast.LENGTH_LONG).show();
//                                        }
//
//                                    } else {
//                                        String errorMessage = task.getException().getMessage();  // 에러코드를 보기위한 임시 코드
//                                        Log.e("TAG", "회원가입 실패: " + errorMessage);
//                                        Toast.makeText(join_password.this, "회원가입 실패: " + errorMessage, Toast.LENGTH_SHORT).show();
//                                        ex.setText(errorMessage);
//                                    }
//                                }
//                            });
//                        }
//                    } else { // 비밀번호 확인 틀렸을 때
//                        chkPwdText.setText("잘못된 비밀번호입니다");
//                        chkPwdText.setTextColor(Color.RED);
//                        Toast.makeText(join_password.this, "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT).show();
//                    }
//            } else { // 이메일 형식이 아닐 때
//                    Toast.makeText(join_password.this,"이메일이 입력되지 않았거나 올바른 이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//}