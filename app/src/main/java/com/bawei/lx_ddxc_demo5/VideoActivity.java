package com.bawei.lx_ddxc_demo5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.bawei.lx_ddxc_demo5.bean.PlayEvent;
import com.bawei.lx_ddxc_demo5.bean.VideoEvent;
import com.bawei.lx_ddxc_demo5.common.PlayerManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VideoActivity extends AppCompatActivity implements PlayerManager.PlayerStateListener {


    private PlayerManager player;
    private String vvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        EventBus.getDefault().register(this);
        initPlayer();
    }

    private void initPlayer() {
        //初始化播放器
        player = new PlayerManager(this);
        //player.setFullScreenOnly(true);
        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
       // player.playInFullScreen(true);
        player.setPlayerStateListener(this);
        if (vvv != null) {
            player.play(vvv);
        }

        //Toast.makeText(this, ""+video, Toast.LENGTH_SHORT).show();

        // player.play("http://video.jiecao.fm/5/1/%E8%87%AA%E5%8F%96%E5%85%B6%E8%BE%B1.mp4");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getVideo(VideoEvent playEvent) {
        //Toast.makeText(this, ""+playEvent.getVideourl(), Toast.LENGTH_SHORT).show();
        vvv = playEvent.getVideo().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (player.gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onPlay() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
