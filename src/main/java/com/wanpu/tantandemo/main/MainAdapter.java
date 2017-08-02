package com.wanpu.tantandemo.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanpu.tantandemo.BR;
import com.wanpu.tantandemo.BindingViewHolder;
import com.wanpu.tantandemo.PersonBean;
import com.wanpu.tantandemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MainAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private final LayoutInflater mLayouInflater;
    private onItemClickListener onItemClickListener;
    private List<PersonBean> personList;

    public interface onItemClickListener {
        void onItemClickListener(BindingViewHolder holder,PersonBean personBean);
    }


    public MainAdapter(Context context) {
        mLayouInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(mLayouInflater, R.layout.item_main, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        final PersonBean personBean = personList.get(position);
        holder.getmBinding().setVariable(BR.person, personBean);
        holder.getmBinding().executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClickListener(holder,personBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (personList != null)
            return personList.size();
        return 0;
    }

    public void setOnItemClickListener(MainAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPersonList(List<PersonBean> personList) {
        this.personList = personList;
    }

    public void add(PersonBean employee) {
        personList.add(employee);
        notifyItemInserted(personList.size());
    }

    public void remove() {
        if (personList.size() == 0)
            return;
        personList.remove(0);
        notifyItemRemoved(0);
    }
}
