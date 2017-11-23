package com.bawei.lx_ddxc_demo5;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.lx_ddxc_demo5.bean.PlayEvent;
import com.bawei.lx_ddxc_demo5.utils.DownloadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



public class PlayActivity extends AppCompatActivity {


    private String path;
    private static final String TAG = PlayActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private Button start;
    private Button pause;


    private TextView total;
    private int max;
    private DownloadUtil mDownloadUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        EventBus.getDefault().register(this);
        total= (TextView) findViewById(R.id.textView);
        start= (Button) findViewById(R.id.start);
        pause= (Button) findViewById(R.id.delete);
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        //String urlString = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
        String localPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/aaaaaaaaa";
        mDownloadUtil = new DownloadUtil(4, localPath, "adc.mp4", path,
                this);
        mDownloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {

            @Override
            public void downloadStart(int fileSize) {
                // TODO Auto-generated method stub
                Log.w(TAG, "fileSize::" + fileSize);
                Toast.makeText(PlayActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                max = fileSize;
                mProgressBar.setMax(fileSize);
            }

            @Override
            public void downloadProgress(int downloadedSize) {
                // TODO Auto-generated method stub
                Log.w(TAG, "Compelete::" + downloadedSize);
                mProgressBar.setProgress(downloadedSize);
                total.setText((int) downloadedSize * 100 / max + "%");
            }

            @Override
            public void downloadEnd() {
                // TODO Auto-generated method stub
                Log.w(TAG, "ENd");
                Toast.makeText(PlayActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mDownloadUtil.start();

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mDownloadUtil.pause();
                Toast.makeText(PlayActivity.this, "下载已暂停", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getVideo(PlayEvent playEvent) {
        Toast.makeText(this, "" + playEvent.getVideourl(), Toast.LENGTH_SHORT).show();
        path = playEvent.getVideourl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
