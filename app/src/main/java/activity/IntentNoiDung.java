package activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;

public class IntentNoiDung extends AppCompatActivity {

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_noi_dung);

        webView = findViewById(R.id.webView);

        Intent intent = getIntent();
        String duongLink = intent.getStringExtra("link");

        webView.loadUrl(duongLink);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings(); //truy cap vao setting cua trang web

        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);  //tat thanh cong cu zoom cua trang web
        webSettings.setJavaScriptEnabled(true); //thuc hien cac chuc nang + xem video trang web
    }
}
