package com.example.do_an_2;

import java.util.List;

public class user_donhang {
    private List<Donhang> list;

    public user_donhang(List<Donhang> list) {
        this.list = list;
    }

    public user_donhang() {
    }

    public List<Donhang> getList() {
        return list;
    }

    public void setList(List<Donhang> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "user_donhang{" +
                "list=" + list +
                '}';
    }
}
