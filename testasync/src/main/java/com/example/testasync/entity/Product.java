package com.example.testasync.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maSp;

    @Column(name = "tenNguoiBan", nullable = false, columnDefinition = "nvarchar(255)")
    private String tenNguoiBan;

    @Column(name = "giaSp", nullable = false, columnDefinition = "int constraint ck_gia check(giaSp > 0) ")
    private Integer giaSp;

    @Column(name = "motaSp", nullable = false, columnDefinition = "nvarchar(255)")
    private String motaSp;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    private Integer giamgia;

    //kết hợp giữa insertable = false và nullable = false thì mới thực thi được constraint default
    @Column(name = "numberBought", columnDefinition = "int constraint df_number default 0", insertable = false, nullable = false)
    private Integer numberBought;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonBackReference
    private User usr_pro;

    public Product() {
    }

    public Product(String tenNguoiBan, Integer giaSp, String motaSp, String thumbnail, Integer giamgia, Integer numberBought) {
        this.tenNguoiBan = tenNguoiBan;
        this.giaSp = giaSp;
        this.motaSp = motaSp;
        this.thumbnail = thumbnail;
        this.giamgia = giamgia;
        this.numberBought = numberBought;
    }

    public Integer getMaSp() {
        return maSp;
    }

    public void setMaSp(Integer maSp) {
        this.maSp = maSp;
    }

    public String getTenNguoiBan() {
        return tenNguoiBan;
    }

    public void setTenNguoiBan(String tenNguoiBan) {
        this.tenNguoiBan = tenNguoiBan;
    }

    public Integer getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(Integer giaSp) {
        this.giaSp = giaSp;
    }

    public String getMotaSp() {
        return motaSp;
    }

    public void setMotaSp(String motaSp) {
        this.motaSp = motaSp;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(Integer giamgia) {
        this.giamgia = giamgia;
    }

    public Integer getNumberBought() {
        return numberBought;
    }

    public void setNumberBought(Integer numberBought) {
        this.numberBought = numberBought;
    }

    public User getUsr_pro() {
        return usr_pro;
    }

    public void setUsr_pro(User usr_pro) {
        this.usr_pro = usr_pro;
    }
}

