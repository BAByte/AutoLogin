package com.example.ba.jpushdemo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.ba.jpushdemo.cookie_tool.PersistentCookieJar;
import com.example.ba.jpushdemo.cookie_tool.cache.SetCookieCache;
import com.example.ba.jpushdemo.cookie_tool.persistence.SharedPrefsCookiePersistor;
import com.example.ba.jpushdemo.encryption_tool.EncryptionFactory;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String IP = "http://120.24.95.57";
    /**
     * The constant LOGIN.登录接口
     * 参数：status:1 一家厨房开始接单，status:0 一家厨房拒绝接单
     * 返回：失败时返回:{"status":1,"message":"厨房状态修改失败"} ，成功时返回:{"status":0,"message":"修改成功!"}
     */
    String LOGIN = IP + "/order/index.php/Android/Login/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       String s=  new EncryptionFactory().getClient(EncryptionFactory.TYPE_SHA).getResult("12345");
        Log.d(TAG, "onCreate: "+s);




        final Button aLogin=(Button)findViewById(R.id.a);
        aLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button bLogin=(Button)findViewById(R.id.b);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoLogin();
            }
        });

        Button c=(Button)findViewById(R.id.c);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersistentCookieJar persistentCookieJar=(PersistentCookieJar) MyApplication.client.cookieJar();
                persistentCookieJar.clear();
                Log.d(TAG, "onClick: 清除成功");
            }
        });

    }

    /**
     * Login.
     */
    public void login() {
      

        /*
         * 加密，这里用MD5,还有一种是SHA，这里没有用到，加密方法要看后台怎么选
         */
        String hexPasswordText = new EncryptionFactory().getClient(EncryptionFactory.TYPE_MD5).getResult("填写密码");

        FormBody body = new FormBody.Builder()
                .add("mobile", "填写账号")
                .add("password", hexPasswordText)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(LOGIN)
                .build();

        MyApplication.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();

                Log.d(TAG, "onResponse: " + text);
            }
        });

    }

    public void autoLogin() {

        FormBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(LOGIN)
                .build();
        MyApplication.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text=response.body().string();
                Log.d(TAG, "onResponse: "+text);
            }
        });

    }
}
