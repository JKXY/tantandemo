package com.wanpu.tantandemo.login;

import android.databinding.ObservableField;

import java.util.Observable;

/**
 * Created by Administrator on 2017/6/19.
 */

public class AreaCode{

    ObservableField<String> name = new ObservableField<String>();
    ObservableField<String> id = new ObservableField<String>();

    public AreaCode(String id) {
        this.id.set(id);
    }

    public AreaCode(String name, String id) {
        this.name.set(name);
        this.id.set(id);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }
}
