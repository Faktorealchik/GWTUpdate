package com.app.server.impl;

import com.app.server.HibernateUtil;
import com.app.server.dao.ClubDao;
import com.app.shared.Club;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

class ImplHelper {
    private SessionFactory sessionFactory;

    ImplHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private int count = 0;

    List<Club> getClubs() {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            List<Club> list = dao.getClubs();
            session.close();
            count=0;
            return list;
        } catch (RuntimeException e) {
            count++;
            if (count > 2) return null;
            sessionFactory = reconnect(e);
            return getClubs();
        }
    }

    Club getClub(int id) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            Club club = dao.getClub(id);
            session.close();
            count=0;
            return club;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return null;
            sessionFactory = reconnect(e);
            return getClub(id);
        }
    }

    SessionFactory reconnect(RuntimeException e) {
        e.printStackTrace();
        HibernateUtil.shutdown();
        HibernateUtil.buildSessionFactory();
        return HibernateUtil.getSessionFactory();
    }
}
