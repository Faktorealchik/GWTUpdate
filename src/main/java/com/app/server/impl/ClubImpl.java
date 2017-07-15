package com.app.server.impl;

import com.app.client.interfaces.ClubInt;
import com.app.server.dao.ClubDao;
import com.app.shared.Club;
import com.app.shared.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClubImpl extends Impl implements ClubInt {
    public ClubImpl() {
        super();
    }

    @Override
    public void insertClub(Club club) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            dao.insertClub(club);
            session.close();
        } catch (HibernateException e) {
            try {
                reconnect(e);
                insertClub(club);
            } catch (Exception e1) {
                return;
            }
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
        } catch (HibernateException e) {
            try {
                reconnect(e);
                updateClub(club);
            } catch (Exception e1) {
                return;
            }
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
        } catch (HibernateException e) {
            try {
                reconnect(e);
                deleteClub(id);
            } catch (Exception e1) {
                return;
            }
        }
    }

    @Override
    public List<Player> getPlayersForClub(Club club) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            List<Player> playersForClub = dao.getPlayersForClub(club);
            session.close();
            return playersForClub;
        } catch (HibernateException e) {
            try {
                reconnect(e);
                return getPlayersForClub(club);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    @Override
    public Club addClub(String name) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao clubDao = new ClubDao(session);
            Club club = clubDao.insertClub(new Club(name));
            session.close();
            return club;
        } catch (HibernateException e) {
            try {
                reconnect(e);
                addClub(name);
            } catch (Exception e1) {
                return null;
            }
        }
        return null;
    }
}
