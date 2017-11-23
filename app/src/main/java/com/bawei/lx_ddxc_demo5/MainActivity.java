package com.bawei.lx_ddxc_demo5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.lx_ddxc_demo5.adapter.MyAdapter;
import com.bawei.lx_ddxc_demo5.bean.MyBean;
import com.bawei.lx_ddxc_demo5.bean.VideoEvent;
import com.bawei.lx_ddxc_demo5.presenter.PlayPresenter;
import com.bawei.lx_ddxc_demo5.utils.Api;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PlayConstract.IView ,XRecyclerView.LoadingListener{


    @BindView(R.id.xrcv)
    XRecyclerView xrcv;
    private XBanner banner;
    private PlayPresenter playPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //http://api.svipmovie.com/front/columns/getVideoList.do?catalogId=402834815584e463015584e539330016&pnum=33
        playPresenter = new PlayPresenter(this);
        playPresenter.LoadData(Api.PATH);
        View banview = View.inflate(this,R.layout.topbanner,null);
        banner = (XBanner) banview.findViewById(R.id.banner);
        xrcv.addHeaderView(banview);
        // 设置XBanner的页面切换特效
        banner.setPageTransformer(Transformer.Depth);
        // 设置XBanner页面切换的时间，即动画时长
        banner.setPageChangeDuration(1000);
    }

    @Override
    public void ShowData(final List<MyBean.DataBean> list) {
        final List<String> listimg = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listimg.add(list.get(i).getImage_url());
        }
        banner.setData(listimg,null);
        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageLoader.getInstance().displayImage(listimg.get(position), (ImageView) view);
            }
        });
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                //Toast.makeText(MainActivity.this, ""+list.get(position).getVedio_url(), Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new VideoEvent(list.get(position).getVedio_url()));
                Intent in = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(in);
            }
        });
        MyAdapter myAdapter = new MyAdapter(list,this);
        xrcv.setAdapter(myAdapter);
        xrcv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void ShowError(String e) {
        Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        Log.e("哈哈哈哈哈哈哈哈哈",e);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void onRefresh() {
        playPresenter.LoadData(Api.PATH);
    }

    @Override
    public void onLoadMore() {

    }
}
