package com.qlsv.qlsv.DAO;

import com.qlsv.qlsv.entities.MonHoc;
import com.qlsv.qlsv.entities.NguoiDung;
import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import com.qlsv.qlsv.entities.TkbSv;
import com.qlsv.qlsv.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class TkbSvDAO {
    public static List<TkbSv> thoiKhoaBieu(NguoiDung sinhVien){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select tkb from TkbSv tkb where tkb.maSV = :sinhVien";
        Query<TkbSv> query = session.createQuery(hql, TkbSv.class).setParameter("sinhVien",sinhVien);
        List<TkbSv> danhSachMon = query.list();
        session.getTransaction().commit();
        return danhSachMon;
    }

    public static List<ThoiKhoaBieu> danhSachMon(NguoiDung sinhVien){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select tkb.sTTMon from TkbSv tkb where tkb.maSV = :sinhVien";
        Query<ThoiKhoaBieu> query = session.createQuery(hql, ThoiKhoaBieu.class);
        List<ThoiKhoaBieu> danhSachMon = query.list();
        session.getTransaction().commit();
        return danhSachMon;
    }

    public static List<TkbSv> thoiKhoaBieu(ThoiKhoaBieu monHoc){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select tkb from TkbSv tkb where tkb.sTTMon = :monHoc";
        Query<TkbSv> query = session.createQuery(hql, TkbSv.class);
        query.setParameter("monHoc",monHoc);
        List<TkbSv> danhSachMon = query.list();
        session.getTransaction().commit();
        return danhSachMon;
    }

    public static TkbSv layThongTinMon(int id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        TkbSv monHoc = session.get(TkbSv.class, id);
        session.getTransaction().commit();
        return monHoc;
    }

    public static TkbSv layThongTinMon(NguoiDung sinhVien, ThoiKhoaBieu thoiKhoaBieu) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            String hql = "SELECT tkb FROM TkbSv tkb WHERE tkb.sTTMon = :thoiKhoaBieu AND tkb.maSV = :sinhVien";
            Query<TkbSv> query = session.createQuery(hql, TkbSv.class);
            query.setParameter("sinhVien", sinhVien);
            query.setParameter("thoiKhoaBieu", thoiKhoaBieu);
            TkbSv monHoc = query.getSingleResult();
            session.getTransaction().commit();
            return monHoc;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    public static boolean kiemTraHocCungMon(NguoiDung sinhVien, MonHoc monHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            String hql = "SELECT tkb FROM TkbSv tkb WHERE tkb.sTTMon.maMon = :monHoc AND tkb.maSV = :sinhVien";
            Query<TkbSv> query = session.createQuery(hql, TkbSv.class);
            query.setParameter("sinhVien", sinhVien);
            query.setParameter("monHoc", monHoc);
            TkbSv tkbSv = query.getSingleResult();
            session.getTransaction().commit();
            return tkbSv != null;
        } catch (NoResultException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean themSinhVienVaoMon(TkbSv tkbSV){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(tkbSV);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean capNhatThongTin(TkbSv tkbSv) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(tkbSv);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaMon(TkbSv tkbSv) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(tkbSv);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
