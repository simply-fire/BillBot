package com.devheat.billbot;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;

/**
 * The Main Activity that is the starting Activity of the WebView
 */
public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                    //Creating the web view and getting the necessary settings for displaying a webpage
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.myWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setWebViewClient(new Callback());
        webView.loadUrl("file:///android_asset/for_smartphone.html");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d("WebView", consoleMessage.message());
                return true;
            }
        });

    }




    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiating the interface and setting the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }
        /**
         * Receives food items one by one and adds the attributes to Bill.data array
         */

        @JavascriptInterface
        public void receive_item(String index, String name, String price, String qty, String amount){
            int i = Integer.parseInt(index);

            Bill.data[i][0] = name;
            Bill.data[i][1] = price;
            Bill.data[i][2] = qty;
            Bill.data[i][3] = amount;

        }

        /**
         * Reads from user.json and returns a JSON string to JS
         */
        @JavascriptInterface                    //Reads from user.json and returns a JSON string to JS
        public String load_user_data(){
            return User.LoadUserData(mContext);
        }

        /**
         * Passes the parameter string onto the function to store in user.json
         */
        @JavascriptInterface
        public String add_user_data(String json_string){
            return User.AddUserData(json_string,mContext);
        }

        /**
         * During bill generation reads ands stores the necessary values
         */
        @JavascriptInterface
        public void add_bill_info(String name, String address, String phone, String email, String GSTIN, String invoice_num, String date, String count, String total_amount, String split){
            User.name = name;
            User.address = address;
            User.phone = phone;
            User.email = email;
            User.GSTIN = GSTIN;
            User.invoice_num = invoice_num;
            Bill.date = date;
            Bill.count = Integer.parseInt(count);
            Bill.totol_amount = total_amount;
            Bill.split = split;
            Bill.data = new String[Bill.count][4];
        }

        /**
         * Prints the PDF
         */
        @JavascriptInterface
        public void printpdf(){
            if (ContextCompat.checkSelfPermission(
                    mContext, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED){try {
                    PDFcreator.createpdf();
                    Toast.makeText(mContext, "Pdf Created", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                Toast.makeText(mContext, "Already a file with the same invoice number and GSTIN", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                requestPermissionLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

        /**
         * Shows a small notification at the bottom of the screen
         */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Allows us to load all the URLs in our WebView
     */
    private class Callback extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            return false;
        }
    }

    /**
     * allows to override the back functionality to let our WebView act as a browse keeping a history of URLs
     */
    @Override
    public void onBackPressed(){
        if(webView != null && webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    try {
                        PDFcreator.createpdf();
                        Toast.makeText(this, "Pdf Created", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Already a file with the same invoice number and GSTIN", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Need Storage permission to store", Toast.LENGTH_LONG).show();
                }
            });
}

