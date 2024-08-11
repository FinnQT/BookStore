package com.example.do_an_2.model;

public class sach {

    private int id;
    private String tensanpham;
    private String hinhanhloaisanpham;
    private Float giasanpham;
    private String tacgia;
    private String chitietsanpham;
    private int loaisanpham;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanhloaisanpham() {
        return hinhanhloaisanpham;
    }

    public void setHinhanhloaisanpham(String hinhanhloaisanpham) {
        this.hinhanhloaisanpham = hinhanhloaisanpham;
    }

    public Float getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(Float giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getChitietsanpham() {
        return chitietsanpham;
    }

    public void setChitietsanpham(String chitietsanpham) {
        this.chitietsanpham = chitietsanpham;
    }

    public int getLoaisanpham() {
        return loaisanpham;
    }

    public void setLoaisanpham(int loaisanpham) {
        this.loaisanpham = loaisanpham;
    }

    public sach(int id, String tensanpham, String hinhanhloaisanpham, Float giasanpham, String tacgia, String chitietsanpham, int loaisanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.hinhanhloaisanpham = hinhanhloaisanpham;
        this.giasanpham = giasanpham;
        this.tacgia = tacgia;
        this.chitietsanpham = chitietsanpham;
        this.loaisanpham = loaisanpham;
    }
}
