package com.litesuits.android;

        import android.app.Activity;
        import android.os.Bundle;
        import android.webkit.WebView;
        import com.litesuits.common.R;

/**
 * @author MaTianyu
 * @date 2014-12-11
 */
public class SamplesActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sample);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("https://github.com/litesuits/android-common");
    }
}
