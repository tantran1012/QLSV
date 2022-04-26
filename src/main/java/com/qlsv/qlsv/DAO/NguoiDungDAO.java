package com.qlsv.qlsv.DAO;

import com.qlsv.qlsv.entities.NguoiDung;
import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import com.qlsv.qlsv.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class NguoiDungDAO {
    public static List<NguoiDung> danhSachSinhVien(){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select nd from NguoiDung nd where nd.chucVu = 1";
        Query<NguoiDung> query = session.createQuery(hql, NguoiDung.class);
        List<NguoiDung> danhSachSinhVien = query.list();
        session.getTransaction().commit();
        return danhSachSinhVien;
    }

    public static List<Integer> danhSachMaSinhVien(){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select nd.id from NguoiDung nd where nd.chucVu = 1";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        List<Integer> danhSachSinhVien = query.list();
        session.getTransaction().commit();
        return danhSachSinhVien;
    }

    public static NguoiDung layThongTinSinhVien(int id){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        NguoiDung sinhVien = session.get(NguoiDung.class, id);
        session.getTransaction().commit();
        return sinhVien;
    }

    public static boolean themSinhVien(NguoiDung sinhVien) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(sinhVien);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean capNhatThongTinSinhVien(NguoiDung sinhVien) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(sinhVien);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaSinhVien(NguoiDung sinhVien) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(sinhVien);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    //1. sai tài khoản,
    //2. sai mật khẩu,
    //5. thành công.
    public static int dangNhap(int maSo, String matKhau){
        NguoiDung nguoiDung = layThongTinSinhVien(maSo);
        if (nguoiDung == null)
            return 1;
        else if (nguoiDung.getMatKhau().equals(matKhau))
            return 5;
        else
            return 2;
    }

}
