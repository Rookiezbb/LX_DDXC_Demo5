package com.bawei.lx_ddxc_demo5.model;

import com.bawei.lx_ddxc_demo5.PlayConstract;
import com.bawei.lx_ddxc_demo5.bean.MyBean;
import com.bawei.lx_ddxc_demo5.utils.ApiService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zhang on 2017/11/23.
 */

public class PlayModel implements PlayConstract.IModel {
    @Override
    public void OnRequestData(String url, final PlayConstract.OnPlayListener onPlayListener) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<MyBean> data = apiService.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onPlayListener.OnError(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(MyBean myBean) {
                        List<MyBean.DataBean> data1 = myBean.getData();
                        onPlayListener.onSuccess(data1);
                    }
                });
    }
}
