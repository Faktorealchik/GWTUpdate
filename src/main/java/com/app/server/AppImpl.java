package com.app.server;

import com.app.client.AppInt;
import com.app.server.dao.ClubDao;
import com.app.server.dao.PlayerDao;
import com.app.shared.Club;
import com.app.shared.Player;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppImpl extends RemoteServiceServlet implements AppInt {

    public AppImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private SessionFactory sessionFactory;

    @Override//TODO: только этот метод принимает не Player
    public Player addPlayer(String lastName, String firstName, String secondName, Date dateBirth, int clubId) {
        try {
            Session session = sessionFactory.openSession();
            ClubDao clubDao = new ClubDao(session);
            Club club = clubDao.getClub(clubId);
            club.getName();

            Player player = new Player(lastName, firstName, secondName, dateBirth, clubId);
            PlayerDao dao = new PlayerDao(session);
            dao.insertPlayer(player);

            session.close();
            return player;
        } catch (ObjectNotFoundException | NullPointerException e) {
            //не добавляем и не пробуем заного (такого клуба нет)
        } catch (HibernateException e) {
            reconnect(e);
            addPlayer(lastName, firstName, secondName, dateBirth, clubId);
        }
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        try {
            Session session = sessionFactory.openSession();
            PlayerDao dao = new PlayerDao(session);
            List<Player> list = dao.getPlayers();//(List<Player>) session.createQuery("from Player").list()
            session.close();
            return list;
        } catch (HibernateException e) {
            reconnect(e);
            getPlayers();
        }

        return new ArrayList<>();
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
        } catch (HibernateException e) {
            reconnect(e);
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
        } catch (HibernateException e) {
            reconnect(e);
            deletePlayer(player);
        }
    }

    @Override
    public Club getClubName(int clubId) {
        try{
            Session session = sessionFactory.openSession();
            ClubDao dao = new ClubDao(session);
            Club club = dao.getClub(clubId);
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
