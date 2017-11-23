package com.bawei.lx_ddxc_demo5.utils;

import com.bawei.lx_ddxc_demo5.bean.MyBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Zhang on 2017/11/23.
 */

public interface ApiService {

    @GET("iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=vedio")
    Observable<MyBean> getData();
}
