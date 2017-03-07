package choongyul.android.com.remoteokhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. okhttp 인스턴스 생성
        client = new OkHttpClient();

        // 어씽크태스크 사용
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String result = getData("http://daum.net");
                    Log.e("OkHttp", "result = " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        // 마찬가지로 쓰레드로 할수도 있다.
        new Thread(){
            @Override
            public void run() {
                try {
                    String result = getData("http://google.com");
                    Log.e("OkHttp", "result = " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


        // 2.request 개체 생성
        private String getData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        // 3. client 인스턴스에 request를 담아보낸다.
        Response response = client.newCall(request).execute(); // 서버측으로 요청
        return response.body().string();
    }
}
