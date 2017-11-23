package com.bawei.lx_ddxc_demo5.presenter;

import com.bawei.lx_ddxc_demo5.PlayConstract;
import com.bawei.lx_ddxc_demo5.bean.MyBean;
import com.bawei.lx_ddxc_demo5.model.PlayModel;

import java.util.List;

/**
 * Created by Zhang on 2017/11/23.
 */

public class PlayPresenter implements PlayConstract.IPresenter {
    PlayConstract.IView iView;
    PlayConstract.IModel iModel;

    public PlayPresenter(PlayConstract.IView iView) {
        this.iView = iView;
        iModel = new PlayModel();
    }

    @Override
    public void LoadData(String url) {
        iModel.OnRequestData(url, new PlayConstract.OnPlayListener() {
            @Override
            public void onSuccess(List<MyBean.DataBean> list) {
                iView.ShowData(list);
            }

            @Override
            public void OnError(String e) {
                iView.ShowError(e);
            }
        });
    }
}
