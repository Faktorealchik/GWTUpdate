package com.app.server.impl;

import com.app.client.interfaces.ClubInt;
import com.app.server.HibernateUtil;
import com.app.server.dao.ClubDao;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ClubImpl extends RemoteServiceServlet implements ClubInt {
    private ImplHelper helper;

    public ClubImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
        helper = new ImplHelper(sessionFactory);
    }

    private SessionFactory sessionFactory;
    private int count = 0;

    @Override
    public List<Club> getClubs() {
        return helper.getClubs();
    }

    @Override
    public void insertClub(Club club) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            dao.insertClub(club);
            session.close();
            count = 0;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return;
            sessionFactory = helper.reconnect(e);
            insertClub(club);
        }
    }

    @Override
    public void updateClub(Club club) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ClubDao dao = new ClubDao(session);
            dao.updateClub(club);
            transaction.commit();
            session.close();
            count = 0;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return;
            sessionFactory = helper.reconnect(e);
            updateClub(club);
        }
    }

    @Override
    public void deleteClub(int id) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            ClubDao dao = new ClubDao(session);
            dao.deleteClub(id);
            transaction.commit();
            session.close();
            count = 0;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return;
            sessionFactory = helper.reconnect(e);
            deleteClub(id);
        }
    }

    @Override
    public List<Player> getPlayersForClub(Club club) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            List<Player> playersForClub = dao.getPlayersForClub(club);
            session.close();
            count = 0;
            return playersForClub;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return null;
            sessionFactory = helper.reconnect(e);
            return getPlayersForClub(club);
        }
    }

    @Override
    public Club addClub(String name) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao clubDao = new ClubDao(session);
            Club club = clubDao.insertClub(new Club(name));
            session.close();
            count = 0;
            return club;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return null;
            sessionFactory = helper.reconnect(e);
            addClub(name);
        }
        return null;
    }

    @Override
    public Club getClub(int id) {
        return helper.getClub(id);
    }
}
