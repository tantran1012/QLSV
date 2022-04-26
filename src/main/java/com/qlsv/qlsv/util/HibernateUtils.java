package com.qlsv.qlsv.util;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
public class HibernateUtils {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Khởi tạo SessionFactory thất bại: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
