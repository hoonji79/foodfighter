package com.example.foodfighter;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class add_search extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_search);

        WebView webView = findViewById(R.id.webView);

        // js 코드를 허용
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new BridgeInterface(), "Android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) { // 페이지 로딩이 끝났을 때
                // android -> js 함수 호출
                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        // 최초 웹 뷰 로드 url
        webView.loadUrl("https://foodfightertest.web.app");
    }

    private class BridgeInterface {
        @JavascriptInterface
        public void processDATA(String data) {
            // 다음 (카카오) 주소 검색 API의 결과 값이 브릿지를 통해 전달 받음. (from js)
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
