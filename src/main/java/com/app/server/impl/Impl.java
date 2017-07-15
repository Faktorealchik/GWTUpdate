package com.app.server.impl;

import com.app.server.HibernateUtil;
import com.app.server.dao.ClubDao;
import com.app.shared.Club;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class Impl extends RemoteServiceServlet {
    protected SessionFactory sessionFactory;

    Impl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Club> getClubs() {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            List<Club> list = dao.getClubs();
            session.close();
            return list;
        } catch (Exception e) {
            try {
                reconnect(e);
                return getClubs();
            } catch (Exception e1) {
                return null;
            }
        }
    }

    public Club getClub(int id) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            Club club = dao.getClub(id);
            session.close();
            return club;
        } catch (Exception e) {
            try {
                reconnect(e);
                return getClub(id);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    private int i = 0;
    protected void reconnect(Exception e) throws Exception {
        i = i + 1;
        e.printStackTrace();
        HibernateUtil.shutdown();
        HibernateUtil.buildSessionFactory();
        sessionFactory = HibernateUtil.getSessionFactory();
        if (i > 3) {
            i = 0;
            throw new Exception(e);
        }
    }
}
