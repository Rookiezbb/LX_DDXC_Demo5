package com.bawei.lx_ddxc_demo5.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.lx_ddxc_demo5.PlayActivity;
import com.bawei.lx_ddxc_demo5.R;
import com.bawei.lx_ddxc_demo5.bean.MyBean;
import com.bawei.lx_ddxc_demo5.bean.PlayEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhang on 2017/11/23.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<MyBean.DataBean> list;
    private Context context;

    public MyAdapter(List<MyBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MyBean.DataBean dataBean = list.get(position);
        holder.itemTv.setText(dataBean.getTitle());
        holder.itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new PlayEvent(dataBean.getVedio_url()));
                Intent in = new Intent(context, PlayActivity.class);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        TextView itemTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
