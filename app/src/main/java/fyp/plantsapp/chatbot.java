package fyp.plantsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class chatbot extends AppCompatActivity {
    WebView browser;
    private PermissionRequest myRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(1);
//        getWindow().setFlags(1024,1024);
        setContentView(R.layout.activity_chatbot);
        browser=findViewById(R.id.browser);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.setWebViewClient(new MyWebViewClient());
        browser.loadUrl(getIntent().getStringExtra("url"));
        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                myRequest = request;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    for (String permission : request.getResources()) {
                        switch (permission) {
                            case "android.webkit.resource.AUDIO_CAPTURE": {
                                askForPermission(request.getOrigin().toString(), Manifest.permission.RECORD_AUDIO, 1000);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (browser.canGoBack()) {
                        browser.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                Log.d("WebView", "PERMISSION FOR AUDIO");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        myRequest.grant(myRequest.getResources());
                    }
                    browser.loadUrl(getIntent().getStringExtra("url"));

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void askForPermission(String origin, String permission, int requestCode) {
        Log.d("WebView", "inside askForPermission for" + origin + "with" + permission);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(chatbot.this,
                    permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(chatbot.this,
                        new String[]{permission},
                        requestCode);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myRequest.grant(myRequest.getResources());
            }
        }
    }

}
