package com.qlsv.qlsv.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tkb_sv")
public class TkbSv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ma_TKB_SV", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MaSV", nullable = false)
    private NguoiDung maSV;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "STTMon", nullable = false)
    private ThoiKhoaBieu sTTMon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "maTkbSv")
    private Set<NgayHoc> ngayhocs = new LinkedHashSet<>();

    public Set<NgayHoc> getNgayhocs() {
        return ngayhocs;
    }

    public void setNgayhocs(Set<NgayHoc> ngayhocs) {
        this.ngayhocs = ngayhocs;
    }

    public NgayHoc getNgayHoc(int tuan){
        for (NgayHoc ngayhoc : ngayhocs) {
            if (ngayhoc.getTuanHoc() == tuan)
                return ngayhoc;
        }
        return null;
    }

    public ThoiKhoaBieu getSTTMon() {
        return sTTMon;
    }

    public void setSTTMon(ThoiKhoaBieu sTTMon) {
        this.sTTMon = sTTMon;
    }

    public NguoiDung getMaSV() {
        return maSV;
    }

    public void setMaSV(NguoiDung maSV) {
        this.maSV = maSV;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}