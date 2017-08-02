package com.wanpu.tantandemo;

import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/22.
 */

public class PersonBean implements Serializable{
    ObservableField<String> imgUrl = new ObservableField<>();
    ObservableField<String> name = new ObservableField<>();
    ObservableField<Integer> age = new ObservableField<>();
    ObservableField<Integer> sex = new ObservableField<>();//0男  1女
    ObservableField<String> constell = new ObservableField<>();//星座
    ObservableField<String> job = new ObservableField<>();

    public PersonBean(String imgUrl, String name, Integer age, Integer sex, String constell,String job) {
        this.imgUrl.set(imgUrl);
        this.name.set(name);
        this.age.set(age);
        this.sex.set(sex);
        this.constell.set(constell);
        this.job.set(job);
    }

    public ObservableField<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl.set(imgUrl);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<Integer> getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age.set(age);
    }

    public ObservableField<Integer> getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex.set(sex);
    }

    public ObservableField<String> getConstell() {
        return constell;
    }

    public void setConstell(String constell) {
        this.constell.set(constell);
    }

    public ObservableField<String> getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job.set(job);
    }
}
