package com.qlsv.qlsv.DAO;

import com.qlsv.qlsv.TrangThaiMonHoc;
import com.qlsv.qlsv.entities.*;
import com.qlsv.qlsv.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class NgayHocDAO {

    public static List<TrangThaiMonHoc> trangThaiMonHoc(NguoiDung nd){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<TrangThaiMonHoc> tTMH = new ArrayList<>();
        List<TkbSv> danhSachMonDK = nd.getTkbSvs().stream().toList();
        for(TkbSv tkb_sv : danhSachMonDK) {
            String hql = "SELECT nh.diemDanh " +
                         "FROM NgayHoc nh " +
                         "JOIN nh.maTkbSv ts " +
                         "Where (ts.maSV = :nd) and (nh.maTkbSv = :tkb_sv)";
            Query<Boolean> query = session.createQuery(hql, Boolean.class);
            query.setParameter("nd",nd);
            query.setParameter("tkb_sv", tkb_sv);
            List<Boolean> dsDiemdanh = query.list();
            TrangThaiMonHoc statMH = new TrangThaiMonHoc(tkb_sv.getSTTMon().getMaMon().getId(),tkb_sv.getSTTMon().getMaMon().getTenMon(), tkb_sv,
                                                         dsDiemdanh.get(0),dsDiemdanh.get(1),dsDiemdanh.get(2),dsDiemdanh.get(3),dsDiemdanh.get(4),
                                                         dsDiemdanh.get(5),dsDiemdanh.get(6),dsDiemdanh.get(7),dsDiemdanh.get(8),dsDiemdanh.get(9),
                                                         dsDiemdanh.get(10),dsDiemdanh.get(11),dsDiemdanh.get(12),dsDiemdanh.get(13),dsDiemdanh.get(14));
            tTMH.add(statMH);
        }
        session.getTransaction().commit();
        return tTMH;
    }

    public static List<TrangThaiMonHoc> trangThaiMonHoc(ThoiKhoaBieu thoiKhoaBieu){
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<TrangThaiMonHoc> tTMH = new ArrayList<>();
        List<TkbSv> danhSachSinhVienDK = thoiKhoaBieu.getTkbSvs().stream().toList();
        for(TkbSv tkb_sv : danhSachSinhVienDK) {
            String hql = "SELECT nh.diemDanh " +
                         "FROM NgayHoc nh " +
                         "JOIN nh.maTkbSv ts " +
                         "WHERE ts.sTTMon = :thoiKhoaBieu " +
                         "AND nh.maTkbSv = :tkb_sv";
            Query<Boolean> query = session.createQuery(hql, Boolean.class);
            query.setParameter("thoiKhoaBieu",thoiKhoaBieu);
            query.setParameter("tkb_sv", tkb_sv);
            List<Boolean> dsDiemdanh = query.list();
            TrangThaiMonHoc statMH = new TrangThaiMonHoc(tkb_sv.getMaSV().getId(),tkb_sv.getMaSV().getHoTen(), tkb_sv,
                    dsDiemdanh.get(0),dsDiemdanh.get(1),dsDiemdanh.get(2),dsDiemdanh.get(3),dsDiemdanh.get(4),
                    dsDiemdanh.get(5),dsDiemdanh.get(6),dsDiemdanh.get(7),dsDiemdanh.get(8),dsDiemdanh.get(9),
                    dsDiemdanh.get(10),dsDiemdanh.get(11),dsDiemdanh.get(12),dsDiemdanh.get(13),dsDiemdanh.get(14));
            tTMH.add(statMH);
        }
        session.getTransaction().commit();
        return tTMH;
    }

    public static NgayHoc layThongTinNgayHoc(int id) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        NgayHoc ngayHoc = session.get(NgayHoc.class, id);
        session.getTransaction().commit();
        return ngayHoc;
    }

    public static NgayHoc layThongTinNgayHoc(int tuan, TkbSv tkbSv) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "select nh from NgayHoc nh where nh.maTkbSv = :idTKB_SV and nh.tuanHoc = :tuan";
        Query<NgayHoc> query = session.createQuery(hql, NgayHoc.class);
        query.setParameter("idTKB_SV", tkbSv);
        query.setParameter("tuan", tuan);
        NgayHoc ngayHoc = query.getSingleResult();
        session.getTransaction().commit();
        return ngayHoc;
    }

    public static boolean themNgayHoc(NgayHoc ngayHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(ngayHoc);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean capNhatThongTinNgayHoc(NgayHoc ngayHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.update(ngayHoc);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean capNhatThongTinNgayHoc(TrangThaiMonHoc trangThaiMonHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            IntStream.range(1, 16).forEach(i -> {
                String hql = "UPDATE NgayHoc SET diemDanh = :diemDanh WHERE maTkbSv = :maTkbSv AND tuanHoc = :tuan";
                Query query = session.createQuery(hql);
                query.setParameter("diemDanh", trangThaiMonHoc.getTuan(i));
                query.setParameter("maTkbSv", trangThaiMonHoc.getTkbSv());
                query.setParameter("tuan", i);
                int affectedRows = query.executeUpdate();
            });
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaNgayHoc(NgayHoc ngayHoc) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(ngayHoc);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean xoaNgayHoc(TkbSv tkbSv) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            String hql = "DELETE FROM NgayHoc nh " +
                         "WHERE nh.maTkbSv = :tkbSv";
            Query query = session.createQuery(hql);
            query.setParameter("tkbSv",tkbSv);
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
