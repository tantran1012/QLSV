package com.qlsv.qlsv.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "thoikhoabieu")
public class ThoiKhoaBieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STT", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MaMon", nullable = false)
    private MonHoc maMon;

    @Column(name = "NgayBatDau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "Thu", nullable = false)
    private Integer thu;

    @Column(name = "GioBatDau", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "GioKetThuc", nullable = false)
    private LocalTime gioKetThuc;

    @Column(name = "PhongHoc", nullable = false, length = 5)
    private String phongHoc;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sTTMon")
    private Set<TkbSv> tkbSvs = new LinkedHashSet<>();

    public Set<TkbSv> getTkbSvs() {
        return tkbSvs;
    }

    public void setTkbSvs(Set<TkbSv> tkbSvs) {
        this.tkbSvs = tkbSvs;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public Integer getThu() {
        return thu;
    }

    public void setThu(Integer thu) {
        this.thu = thu;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public MonHoc getMaMon() {
        return maMon;
    }

    public void setMaMon(MonHoc maMon) {
        this.maMon = maMon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean compare(ThoiKhoaBieu tkb){
        return (this.maMon.getId().equals(tkb.maMon.getId()) && this.ngayBatDau.equals(tkb.ngayBatDau) && this.gioBatDau.equals(tkb.gioBatDau) && this.phongHoc.equals(tkb.phongHoc) && !this.id.equals(tkb.id));
    }
}