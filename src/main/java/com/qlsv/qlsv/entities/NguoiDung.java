package com.qlsv.qlsv.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "nguoidung")
public class NguoiDung {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSo", nullable = false)
    private Integer id;

    @Column(name = "HoTen", nullable = false)
    private String hoTen;

    @Column(name = "MatKhau", nullable = false)
    private String matKhau;

    @Column(name = "ChucVu", nullable = false)
    private Integer chucVu;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "maSV")
    private Set<TkbSv> tkbSvs = new LinkedHashSet<>();

    public Set<TkbSv> getTkbSvs() {
        return tkbSvs;
    }

    public void setTkbSvs(Set<TkbSv> tkbSvs) {
        this.tkbSvs = tkbSvs;
    }

    public Integer getChucVu() {
        return chucVu;
    }

    public void setChucVu(Integer chucVu) {
        this.chucVu = chucVu;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}