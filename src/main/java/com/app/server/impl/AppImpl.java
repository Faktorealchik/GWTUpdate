package com.app.server.impl;

import com.app.client.interfaces.AppInt;
import com.app.server.HibernateUtil;
import com.app.server.dao.PlayerDao;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AppImpl extends RemoteServiceServlet implements AppInt {

    private ImplHelper helper;

    public AppImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
        helper = new ImplHelper(sessionFactory);
    }

    private SessionFactory sessionFactory;
    private int count = 0;

    @Override
    public Player addPlayer(Player player) {
        try {
            Session session = sessionFactory.openSession();
            PlayerDao dao = new PlayerDao(session);
            dao.insertPlayer(player);
            session.close();
            count = 0;
            return player;
        } catch (RuntimeException e) {
            count++;
            if (count > 2) return null;
            sessionFactory = helper.reconnect(e);
            return addPlayer(player);
        }
    }

    @Override
    public List<Player> getPlayers() {
        try {
            Session session = sessionFactory.openSession();
            PlayerDao dao = new PlayerDao(session);
            List<Player> list = dao.getPlayers();//(List<Player>) session.createQuery("from Player").list()
            session.close();
            count = 0;
            return list;
        } catch (RuntimeException e) {
            count++;
            if (count > 2) return null;
            sessionFactory = helper.reconnect(e);
            return getPlayers();
        }
    }

    @Override
    public void updatePlayer(Player player) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PlayerDao dao = new PlayerDao(session);
            dao.updatePlayer(player);
            transaction.commit();
            session.close();
            count = 0;
        } catch (RuntimeException e) {
            count++;
            if (count > 2) return;
            sessionFactory = helper.reconnect(e);
            updatePlayer(player);
        }
    }

    @Override
    public void deletePlayer(Player player) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            PlayerDao dao = new PlayerDao(session);
            dao.deletePlayer(player);
            transaction.commit();
            session.close();
            count = 0;
        } catch (HibernateException e) {
            count++;
            if (count > 2) return;
            sessionFactory = helper.reconnect(e);
            deletePlayer(player);
        }
    }

    @Override
    public Club getClub(int clubId) {
        return helper.getClub(clubId);
    }

    @Override
    public List<Club> getClubs() {
        return helper.getClubs();
    }

}
