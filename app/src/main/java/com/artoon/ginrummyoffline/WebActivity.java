package com.artoon.ginrummyoffline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import utils.C;
import utils.Logger;
import utils.PreferenceManager;

public class WebActivity extends Activity {

    static final int RC_REQUEST = 10001;
    public Handler handler;
    public AVLoadingIndicatorView prgLoader;
    String path = "";
    ImageView close;
    String cid = "";
    private C c = C.getInstance();
    private String offerSkuId, offerPlanId;
    private Dialog ConfirmationDialogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        prgLoader = findViewById(R.id.prgLoader);

        int w = c.width * 100 / 1280;
        FrameLayout.LayoutParams frm = (FrameLayout.LayoutParams) findViewById(R.id.prgLoader).getLayoutParams();
        frm.gravity = Gravity.CENTER;

        //frm.rightMargin = c.width * 5 / 1280;
        //frm.topMargin = c.height * 5 / 720;

        frm.width = w;
        frm.height = w;

        close = findViewById(R.id.web_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        int width = c.width * 90 / 1280;
        frm = (FrameLayout.LayoutParams) findViewById(R.id.web_close).getLayoutParams();
        frm.width = w;
        frm.height = w;
        frm.topMargin = c.height * 20 / 720;
        frm.rightMargin = c.width * 20 / 1280;

        final WebView webTeenPatti = findViewById(R.id.web_view);

        String urlPath = getIntent().getStringExtra("path");
        cid = getIntent().getStringExtra("cid");

        webTeenPatti.loadUrl(urlPath);

        webTeenPatti.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                prgLoader.hide();
                webTeenPatti.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                prgLoader.show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Logger.print("<<<< URL OF CLICK:" + url);

                Uri data = Uri.parse(url);

                if (!url.contains("play.google.com")) {
                    webTeenPatti.loadUrl(url);
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

                if (data != null) {

                    if (data.toString().contains("close")) {

                        finish();
                        overridePendingTransition(0, R.anim.dialogue_scale_anim_exit);

                    } else {

                        if (data.getQueryParameter("InAppId") != null && data.getQueryParameter("id") != null) {

                            offerSkuId = data.getQueryParameter("InAppId");
                            offerPlanId = data.getQueryParameter("id");
                            Logger.print("WebView Params from Url : " + offerSkuId + " " + offerPlanId);


                     /*       if (c.jsonData.getChipStore() != null && c.jsonData.getChipStore().length() > 0) {
                                Intent i = new Intent(WebActivity.this, Store.class);
                                i.putExtra(Parameters.InAppId, offerSkuId);
                                i.putExtra(Parameters.PlanId, offerPlanId);
                                i.putExtra("isFromHTML", true);
                                i.putExtra("DATA", c.jsonData.getChipStore().toString());
                                startActivity(i);
                                overridePendingTransition(R.anim.dialogue_scale_anim_open, 0);
                                finish();
                            } else {
                                GlobalLoaderSHOW(getResources().getString(R.string.PleaseWait));
                                JSONObject jobj = new JSONObject();
                                EmitManager.Process(jobj, Events.ChipStore);
                            }

                            */

                        }
                    }
                }

                return true;
            }
        });

        if (PreferenceManager.isInternetConnected()) {

            path = "http://gamewithpals.com:3500/offlineCampCron?cid=" + cid;

            String rulone = "http://gamewithpals.com:3500/offlineCampCron?cid=" + cid;

            try {

                new MyAsyncTask().execute(rulone);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void httpPostcall(String rulone) {


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://dev.gamewithpals.com:4005");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

        nameValuePair.add(new BasicNameValuePair("cid", "" + cid));
        // Url Encoding the POST parameters

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Making HTTP Request

        try {

            HttpResponse response = httpClient.execute(httpPost);
            Logger.print("<<<<< Http responce:" + response.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        c.payment_Handler = this.handler;

        c.con = this;
        c.activity = WebActivity.this;
    }

    private void showInfoDialog(String DialogTitle, String Message, String buttonText) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams lin;

        if (ConfirmationDialogInfo != null) {
            if (ConfirmationDialogInfo.isShowing()) {
                //  ConnectionManager.dismissDialog(ConfirmationDialogInfo);
            }
            ConfirmationDialogInfo = null;
        }

        ConfirmationDialogInfo = new Dialog(WebActivity.this,
                R.style.Theme_Transparent);
        ConfirmationDialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ConfirmationDialogInfo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ConfirmationDialogInfo.setContentView(R.layout.dialog);

        TextView title = ConfirmationDialogInfo
                .findViewById(R.id.tvTitle);
        title.setPadding(getwidth(20), getheight(20), getwidth(20),
                getheight(20));
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(36));
        title.setTypeface(c.typeface);
        title.setText(DialogTitle);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.line).getLayoutParams();
        lin.height = getheight(4);
        lin.width = getwidth(600);

        TextView message = ConfirmationDialogInfo
                .findViewById(R.id.tvMessage);
        message.setTypeface(c.typeface);
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(32));

        Button btnOne = ConfirmationDialogInfo.findViewById(R.id.button1);
        btnOne.setTypeface(c.typeface);
        btnOne.setTextSize(TypedValue.COMPLEX_UNIT_PX, getwidth(24));
        btnOne.setText(buttonText);

        Button btnTwo = ConfirmationDialogInfo.findViewById(R.id.button2);
        btnTwo.setVisibility(View.GONE);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.button1).getLayoutParams();
        lin.height = getheight(60);
        lin.width = getwidth(130);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.tvMessage).getLayoutParams();
        lin.topMargin = getheight(30);

        lin = (LinearLayout.LayoutParams) ConfirmationDialogInfo.findViewById(
                R.id.llButtons).getLayoutParams();
        lin.topMargin = getheight(30);

        message.setText(Message);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method

                //ConnectionManager.dismissDialog(ConfirmationDialogInfo);

            }
        });

    }


    private int getheight(int val) {
        // TODO Auto-generated method stub
        return c.height * val / 720;
    }

    private int getwidth(int val) {
        // TODO Auto-generated method stub
        return c.width * val / 1280;
    }

    public void LoaderFinish(int i) {
        // TODO Auto-generated method stub
        try {
            // loader.FinishMe(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result) {
            //  pb.setVisibility(View.GONE);

            // Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {
            //pb.setProgress(progress[0]);
        }

        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(path);

            try {

                // Add your data

//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
//
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                Logger.print("<<<<<< PRINT RESP:" + response.toString());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

    }


}
