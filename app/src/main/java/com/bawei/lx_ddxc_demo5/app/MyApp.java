package com.bawei.lx_ddxc_demo5.app;

import android.app.Application;
import android.graphics.Bitmap;

import com.bawei.lx_ddxc_demo5.R;
import com.bawei.lx_ddxc_demo5.gen.DaoMaster;
import com.bawei.lx_ddxc_demo5.gen.DaoSession;
import com.bawei.lx_ddxc_demo5.gen.UserDao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Zhang on 2017/11/23.
 */

public class MyApp extends Application {
    public static UserDao userDao;
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoder();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "lenvess.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }
    /**
     * 图片加载
     */
    private void initImageLoder() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.displayer(new RoundedBitmapDisplayer(360))//圆角问题：必须在xml里面指定具体大小
                //设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                //设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                //设置图片的解码类型
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                //设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                //设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                //设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                //图像将被二次采样的整数倍
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                //设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor  preProcessor)
                //设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                //是否设置为圆角，弧度是多少
                .displayer(new RoundedBitmapDisplayer(20))
                //是否图片加载好后渐入的动画时间
                .displayer(new FadeInBitmapDisplayer(100))
                //构建完成
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
