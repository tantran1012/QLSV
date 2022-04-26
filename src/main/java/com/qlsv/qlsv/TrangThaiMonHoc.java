package com.qlsv.qlsv;

import com.qlsv.qlsv.entities.TkbSv;

import java.util.List;

public class TrangThaiMonHoc {
    private String maMon;
    private String tenMon;
    private Integer maSinhVien;
    private String hoTen;
    private TkbSv tkbSv;
    private Boolean tuan1;
    private Boolean tuan2;
    private Boolean tuan3;
    private Boolean tuan4;
    private Boolean tuan5;
    private Boolean tuan6;
    private Boolean tuan7;
    private Boolean tuan8;
    private Boolean tuan9;
    private Boolean tuan10;
    private Boolean tuan11;
    private Boolean tuan12;
    private Boolean tuan13;
    private Boolean tuan14;
    private Boolean tuan15;

    public TrangThaiMonHoc(String maMon, String tenMon, TkbSv tkbSv, Boolean tuan1, Boolean tuan2, Boolean tuan3, Boolean tuan4, Boolean tuan5, Boolean tuan6, Boolean tuan7, Boolean tuan8, Boolean tuan9, Boolean tuan10, Boolean tuan11, Boolean tuan12, Boolean tuan13, Boolean tuan14, Boolean tuan15) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.tkbSv = tkbSv;
        this.tuan1 = tuan1;
        this.tuan2 = tuan2;
        this.tuan3 = tuan3;
        this.tuan4 = tuan4;
        this.tuan5 = tuan5;
        this.tuan6 = tuan6;
        this.tuan7 = tuan7;
        this.tuan8 = tuan8;
        this.tuan9 = tuan9;
        this.tuan10 = tuan10;
        this.tuan11 = tuan11;
        this.tuan12 = tuan12;
        this.tuan13 = tuan13;
        this.tuan14 = tuan14;
        this.tuan15 = tuan15;

        this.maSinhVien = null;
        this.hoTen = null;
    }

    public TrangThaiMonHoc(Integer maSinhVien, String hoTen, TkbSv tkbSv, Boolean tuan1, Boolean tuan2, Boolean tuan3, Boolean tuan4, Boolean tuan5, Boolean tuan6, Boolean tuan7, Boolean tuan8, Boolean tuan9, Boolean tuan10, Boolean tuan11, Boolean tuan12, Boolean tuan13, Boolean tuan14, Boolean tuan15) {
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.tkbSv = tkbSv;
        this.tuan1 = tuan1;
        this.tuan2 = tuan2;
        this.tuan3 = tuan3;
        this.tuan4 = tuan4;
        this.tuan5 = tuan5;
        this.tuan6 = tuan6;
        this.tuan7 = tuan7;
        this.tuan8 = tuan8;
        this.tuan9 = tuan9;
        this.tuan10 = tuan10;
        this.tuan11 = tuan11;
        this.tuan12 = tuan12;
        this.tuan13 = tuan13;
        this.tuan14 = tuan14;
        this.tuan15 = tuan15;

        this.tenMon = null;
        this.maMon = null;
    }

    public TrangThaiMonHoc() {

    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public TkbSv getTkbSv() {
        return tkbSv;
    }

    public void setTkbSv(TkbSv tkbSv) {
        this.tkbSv = tkbSv;
    }

    public Boolean getTuan1() {
        return tuan1;
    }

    public void setTuan1(Boolean tuan1) {
        this.tuan1 = tuan1;
    }

    public Boolean getTuan2() {
        return tuan2;
    }

    public void setTuan2(Boolean tuan2) {
        this.tuan2 = tuan2;
    }

    public Boolean getTuan3() {
        return tuan3;
    }

    public void setTuan3(Boolean tuan3) {
        this.tuan3 = tuan3;
    }

    public Boolean getTuan4() {
        return tuan4;
    }

    public void setTuan4(Boolean tuan4) {
        this.tuan4 = tuan4;
    }

    public Boolean getTuan5() {
        return tuan5;
    }

    public void setTuan5(Boolean tuan5) {
        this.tuan5 = tuan5;
    }

    public Boolean getTuan6() {
        return tuan6;
    }

    public void setTuan6(Boolean tuan6) {
        this.tuan6 = tuan6;
    }

    public Boolean getTuan7() {
        return tuan7;
    }

    public void setTuan7(Boolean tuan7) {
        this.tuan7 = tuan7;
    }

    public Boolean getTuan8() {
        return tuan8;
    }

    public void setTuan8(Boolean tuan8) {
        this.tuan8 = tuan8;
    }

    public Boolean getTuan9() {
        return tuan9;
    }

    public void setTuan9(Boolean tuan9) {
        this.tuan9 = tuan9;
    }

    public Boolean getTuan10() {
        return tuan10;
    }

    public void setTuan10(Boolean tuan10) {
        this.tuan10 = tuan10;
    }

    public Boolean getTuan11() {
        return tuan11;
    }

    public void setTuan11(Boolean tuan11) {
        this.tuan11 = tuan11;
    }

    public Boolean getTuan12() {
        return tuan12;
    }

    public void setTuan12(Boolean tuan12) {
        this.tuan12 = tuan12;
    }

    public Boolean getTuan13() {
        return tuan13;
    }

    public void setTuan13(Boolean tuan13) {
        this.tuan13 = tuan13;
    }

    public Boolean getTuan14() {
        return tuan14;
    }

    public void setTuan14(Boolean tuan14) {
        this.tuan14 = tuan14;
    }

    public Boolean getTuan15() {
        return tuan15;
    }

    public void setTuan15(Boolean tuan15) {
        this.tuan15 = tuan15;
    }

    public Integer getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(Integer maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setData(Integer maSinhVien, String hoTen, List<Boolean> tuan){
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.tuan1 = tuan.get(0);
        this.tuan2 = tuan.get(1);
        this.tuan3 = tuan.get(2);
        this.tuan4 = tuan.get(3);
        this.tuan5 = tuan.get(4);
        this.tuan6 = tuan.get(5);
        this.tuan7 = tuan.get(6);
        this.tuan8 = tuan.get(7);
        this.tuan9 = tuan.get(8);
        this.tuan10 = tuan.get(9);
        this.tuan11 = tuan.get(10);
        this.tuan12 = tuan.get(11);
        this.tuan13 = tuan.get(12);
        this.tuan14 = tuan.get(13);
        this.tuan15 = tuan.get(14);
    }

    public boolean getTuan(int i) {
        return switch (i) {
            case 1 -> tuan1;
            case 2 -> tuan2;
            case 3 -> tuan3;
            case 4 -> tuan4;
            case 5 -> tuan5;
            case 6 -> tuan6;
            case 7 -> tuan7;
            case 8 -> tuan8;
            case 9 -> tuan9;
            case 10 -> tuan10;
            case 11 -> tuan11;
            case 12 -> tuan12;
            case 13 -> tuan13;
            case 14 -> tuan14;
            case 15 -> tuan15;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    public void setTuan(int i, Boolean checkIn) {
        switch (i) {
            case 1 -> tuan1 = checkIn;
            case 2 -> tuan2 = checkIn;
            case 3 -> tuan3 = checkIn;
            case 4 -> tuan4 = checkIn;
            case 5 -> tuan5 = checkIn;
            case 6 -> tuan6 = checkIn;
            case 7 -> tuan7 = checkIn;
            case 8 -> tuan8 = checkIn;
            case 9 -> tuan9 = checkIn;
            case 10 -> tuan10 = checkIn;
            case 11 -> tuan11 = checkIn;
            case 12 -> tuan12 = checkIn;
            case 13 -> tuan13 = checkIn;
            case 14 -> tuan14 = checkIn;
            case 15 -> tuan15 = checkIn;
        }
    }
}
