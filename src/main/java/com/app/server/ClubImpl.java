package com.app.server;

import com.app.client.ClubInt;
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
    public ClubImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private SessionFactory sessionFactory;

    @Override
    public List<Club> getClubs() {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            List<Club> list = dao.getClubs();
            session.close();
            return list;
        } catch (HibernateException e) {
            reconnect(e);
            getClubs();
        }
        return null;
    }

    @Override
    public void insertClub(Club club) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            dao.insertClub(club);
            session.close();
        } catch (HibernateException e) {
            reconnect(e);
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
        } catch (HibernateException e) {
            reconnect(e);
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
        } catch (HibernateException e) {
            reconnect(e);
            deleteClub(id);
        }
    }

    @Override
    public List<Player> getPlayersForClub(Club club) {
        try{
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            List<Player> playersForClub = dao.getPlayersForClub(club);
            session.close();
            return playersForClub;
        }
        catch (HibernateException e){
//            reconnect(e);
        }
        return null;
    }

    @Override
    public Club getClub(int id) {
        try{
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            Club club = dao.getClub(id);
//            Club club = session.load(Club.class, id);
            System.out.println(club);
            session.close();
            return club;
        }
        catch (HibernateException e){
            //TODO:
        }
        return null;
    }

    private void reconnect(HibernateException e) {
        e.printStackTrace();
        HibernateUtil.shutdown();
        HibernateUtil.buildSessionFactory();
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
}
