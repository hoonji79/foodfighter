package com.example.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodfighter.add_search;

public class address_search extends AppCompatActivity {

    private EditText Address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_search);

        Address = findViewById(R.id.setAddress);

        // block touch
        Address.setFocusable(false);
        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(address_search.this, add_search.class);
                getSearchResult.launch(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // search의 결과 값이 전달되는 부분
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String data = result.getData().getStringExtra("data");
                        Address.setText(data);
                    }
                }
            }
    );
}
