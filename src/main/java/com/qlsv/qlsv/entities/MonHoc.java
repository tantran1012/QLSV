package com.qlsv.qlsv.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "monhoc")
public class MonHoc {
    @Id
    @Column(name = "MaMon", nullable = false, length = 10)
    private String id;

    @Column(name = "TenMon", nullable = false)
    private String tenMon;

    @OneToMany(mappedBy = "maMon")
    private Set<ThoiKhoaBieu> thoikhoabieuses = new LinkedHashSet<>();

    public MonHoc() {
    }

    public MonHoc(String id, String tenMon) {
        this.id = id;
        this.tenMon = tenMon;
    }

    public Set<ThoiKhoaBieu> getThoikhoabieus() {
        return thoikhoabieuses;
    }

    public void setThoikhoabieus(Set<ThoiKhoaBieu> thoikhoabieuses) {
        this.thoikhoabieuses = thoikhoabieuses;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StringProperty tenMonProperty() {
        StringProperty tenMonProperty = new SimpleStringProperty();
        tenMonProperty.set(this.tenMon);
        return tenMonProperty;
    }
}