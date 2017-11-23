package com.bawei.lx_ddxc_demo5;

import com.bawei.lx_ddxc_demo5.bean.MyBean;

import java.util.List;

/**
 * Created by Zhang on 2017/11/23.
 */

public interface PlayConstract {

    interface IView{
        void ShowData(List<MyBean.DataBean> list);
        void ShowError(String e);
    }

    interface OnPlayListener{
        void onSuccess(List<MyBean.DataBean> list);
        void OnError(String e);
    }

    interface IModel{
        void OnRequestData(String url,OnPlayListener onPlayListener);
    }

    interface IPresenter{
        void LoadData(String url);
    }

}
