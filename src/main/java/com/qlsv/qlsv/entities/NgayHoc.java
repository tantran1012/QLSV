package com.qlsv.qlsv.entities;

import javax.persistence.*;

@Entity
@Table(name = "ngayhoc")
public class NgayHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STT", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Ma_TKB_SV", nullable = false)
    private TkbSv maTkbSv;

    @Column(name = "TuanHoc", nullable = false)
    private Integer tuanHoc;

    @Column(name = "DiemDanh", nullable = false)
    private Boolean diemDanh = false;

    public Boolean getDiemDanh() {
        return diemDanh;
    }

    public void setDiemDanh(Boolean diemDanh) {
        this.diemDanh = diemDanh;
    }

    public Integer getTuanHoc() {
        return tuanHoc;
    }

    public void setTuanHoc(Integer tuanHoc) {
        this.tuanHoc = tuanHoc;
    }

    public TkbSv getMaTkbSv() {
        return maTkbSv;
    }

    public void setMaTkbSv(TkbSv maTkbSv) {
        this.maTkbSv = maTkbSv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}