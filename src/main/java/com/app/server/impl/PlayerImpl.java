package com.app.server.impl;

import com.app.client.interfaces.AppInt;
import com.app.server.dao.PlayerDao;
import com.app.shared.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerImpl extends Impl implements AppInt {
    public PlayerImpl() {
        super();
    }

    @Override
    public Player addPlayer(Player player) {
        try {
            Session session = sessionFactory.openSession();
            PlayerDao dao = new PlayerDao(session);
            dao.insertPlayer(player);
            session.close();
            return player;
        } catch (Exception e) {
            try {
                reconnect(e);
                return addPlayer(player);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    @Override
    public List<Player> getPlayers() {
        try {
            Session session = sessionFactory.openSession();
            PlayerDao dao = new PlayerDao(session);
            List<Player> list = dao.getPlayers();//(List<Player>) session.createQuery("from Player").list()
            session.close();
            return list;
        } catch (Exception e) {
            try {
                reconnect(e);
                return getPlayers();
            } catch (Exception e1) {
                return null;
            }
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
        } catch (Exception e) {
            try {
                reconnect(e);
                updatePlayer(player);
            } catch (Exception e1) {
                return;
            }
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
        } catch (Exception e) {
            try {
                reconnect(e);
                deletePlayer(player);
            } catch (Exception e1) {
                return;
            }
        }
    }
}
