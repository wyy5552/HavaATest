package com.example.webview;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends Activity implements OnClickListener {
    private String url = null;
    private WebView webView;
    private EditText et_url;
    private Button btn_fresh;
    private Button btn_forward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// 窗口进度条
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        setProgressBarIndeterminate(true);
        webView = (WebView) findViewById(R.id.webview_id);
        et_url = (EditText) findViewById(R.id.url_input);
        btn_fresh = (Button) findViewById(R.id.btn_fresh);
        btn_forward = (Button) findViewById(R.id.btn_forward);
// 对五个按钮添加点击监听事件
        btn_forward.setOnClickListener(this);
        btn_fresh.setOnClickListener(this);

        // 覆盖webView默认通过系统或者第三方浏览器打开网页的行为
// 如果为false调用系统或者第三方浏览器打开网页的行为
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
// TODO Auto-generated method stub
// webView加载web资源
                view.loadUrl(url);
                return true;
            }
        });
// 启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
// web加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
// 当打开页面时 显示进度条 页面打开完全时 隐藏进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
// TODO Auto-generated method stub
                setTitle("本页面已加载" + newProgress + "%");
                if (newProgress == 100) {
                    closeProgressBar();
                } else {
                    openProgressBar(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }
    // btn_login的触发事件 点击后 webView开始读取url
    protected void startReadUrl(String url) {
// TODO Auto-generated method stub
// webView加载web资源
        webView.loadUrl(url);
    }
    // 打开进度条
    protected void openProgressBar(int x) {
// TODO Auto-generated method stub
        setProgressBarIndeterminateVisibility(true);
        setProgress(x);
    }
    // 关闭进度条
    protected void closeProgressBar() {
// TODO Auto-generated method stub
        setProgressBarIndeterminateVisibility(false);
    }
    // 改写物理按键 返回键的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
// 返回上一页面
                webView.goBack();
                return true;
            } else {
// 退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    // 对按钮事件的处理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forward:
                String url = uriHttpFirst(et_url.getText().toString());
                startReadUrl(url);
                break;
        }
    }
    private String uriHttpFirst(String strUri){

        if(strUri.indexOf("http://",0) != 0 && strUri.indexOf("https://",0) != 0 ){
            strUri = "http://" + strUri;
        }

        return strUri;
    }
}