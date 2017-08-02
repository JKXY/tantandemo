package com.wanpu.tantandemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.wanpu.tantandemo.login.RegistOneActivity;
import com.wanpu.tantandemo.widget.RecyclerBanner;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class EntityList extends BaseObservable{
    List<RecyclerBanner.BannerEntity> list;

    public EntityList(List<RecyclerBanner.BannerEntity> list) {
        this.list = list;
    }

    public static class Entity implements RecyclerBanner.BannerEntity {
        int id;
        Bitmap bitmap;
        String url;

        public Entity(int id, Bitmap bitmap) {
            this.id = id;
            this.bitmap = bitmap;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public Bitmap getBitmap() {
            return null;
        }

        @Override
        public String getUrl() {
            return null;
        }
    }

    @Bindable
    public List<RecyclerBanner.BannerEntity> getList() {
        return list;
    }


    public void setList(List<RecyclerBanner.BannerEntity> list) {
        this.list = list;
        notifyPropertyChanged(com.wanpu.tantandemo.BR.list);
    }

    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), RegistOneActivity.class);
        view.getContext().startActivity(intent);
        ((Activity)(view.getContext())).finish();
    }

}
