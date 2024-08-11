package com.example.do_an_2;

import java.io.Serializable;
import java.util.List;

public class Donhang implements Serializable{
    private String day;
    private String diachi;
    private String madonhang;
    private List<Book_demo1> sachmua;
    private String sdt;
    private String tenkhachhang;
    private double tongtien;
    private String trangthai;

    public Donhang() {
    }

    public Donhang(String day, String diachi, String madonhang, List<Book_demo1> sachmua, String sdt,
                   String tenkhachhang, double tongtien, String trangthai) {
        this.day = day;
        this.diachi = diachi;
        this.madonhang = madonhang;
        this.sachmua = sachmua;
        this.sdt = sdt;
        this.tenkhachhang = tenkhachhang;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public List<Book_demo1> getSachmua() {
        return sachmua;
    }

    public void setSachmua(List<Book_demo1> sachmua) {
        this.sachmua = sachmua;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public String toString() {
        return "Donhang{" +
                "day='" + day + '\'' +
                ", diachi='" + diachi + '\'' +
                ", madonhang='" + madonhang + '\'' +
                ", sachmua=" + sachmua +
                ", sdt='" + sdt + '\'' +
                ", tenkhachhang='" + tenkhachhang + '\'' +
                ", tongtien=" + tongtien +
                ", trangthai='" + trangthai + '\'' +
                '}';
    }
}
