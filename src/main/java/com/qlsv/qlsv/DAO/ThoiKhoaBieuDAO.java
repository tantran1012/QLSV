package com.qlsv.qlsv.DAO;

import com.qlsv.qlsv.entities.ThoiKhoaBieu;
import com.qlsv.qlsv.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ThoiKhoaBieuDAO {
    public static List<ThoiKhoaBieu> thoiKhoaBieu(){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select tkb from ThoiKhoaBieu tkb";
        Query<ThoiKhoaBieu> query = session.createQuery(hql, ThoiKhoaBieu.class);
        List<ThoiKhoaBieu> danhSachMon = query.list();
        session.getTransaction().commit();
        return danhSachMon;
    }

    public static ThoiKhoaBieu layThongTinMon(int id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ThoiKhoaBieu monHoc = session.get(ThoiKhoaBieu.class, id);
        session.getTransaction().commit();
        return monHoc;
    }

    public static boolean themMonVaoThoiKhoaBieu(ThoiKhoaBieu monDuocThem){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(monDuocThem);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }


    public static boolean capNhatThongTinMon(ThoiKhoaBieu monHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(monHoc);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaMon(ThoiKhoaBieu monHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(monHoc);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
