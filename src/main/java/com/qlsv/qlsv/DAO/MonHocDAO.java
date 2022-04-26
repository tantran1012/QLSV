package com.qlsv.qlsv.DAO;

import com.qlsv.qlsv.entities.MonHoc;
import com.qlsv.qlsv.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MonHocDAO {
    public static List<MonHoc> danhSachMonHoc() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select m from MonHoc m";
        Query<MonHoc> query = session.createQuery(hql, MonHoc.class);
        List<MonHoc> dsMonHoc = query.list();
        session.getTransaction().commit();
        return dsMonHoc;
    }

    public static List<String> danhSachMaMonHoc() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select m.id from MonHoc m";
        Query<String> query = session.createQuery(hql, String.class);
        List<String> dsMaMonHoc = query.list();
        session.getTransaction().commit();
        return dsMaMonHoc;
    }

    public static MonHoc layThongTinMon(String id){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        MonHoc monHoc = session.get(MonHoc.class,id);
        session.getTransaction().commit();
        return monHoc;
    }

    public static boolean themMon(MonHoc monHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(monHoc);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean capNhatThongTinMon(MonHoc monHoc) {
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

    public static boolean xoaMon(MonHoc monHoc) {
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
