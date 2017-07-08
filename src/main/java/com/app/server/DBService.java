//package com.app.server;
//
//import com.app.server.dao.ClubDao;
//import com.app.server.dao.PlayerDao;
//import com.app.shared.Club;
//import com.app.shared.Player;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class DBService {
//    private static final String hibernate_show_sql = "true";
//    private static final String hibernate_hbm2ddl_auto = "update";
//
//    private final SessionFactory sessionFactory;
//
//    public DBService() {
//        Configuration configuration = getMySqlConfiguration();
//        sessionFactory = createSessionFactory(configuration);
//    }
//
//    private Configuration getMySqlConfiguration() { //конфигурация базы данных
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(Player.class);
//        configuration.addAnnotatedClass(Club.class);
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://asresh4w.beget.tech:3306/asresh4w_gwttest");
//        configuration.setProperty("hibernate.connection.username", "asresh4w_gwttest");
//        configuration.setProperty("hibernate.connection.password", "gwttest");
//        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
//        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
//        return configuration;
//    }
//
//    public List<Player> getPlayers() {
//        try {
//            Session session = sessionFactory.openSession();
//            PlayerDao dao = new PlayerDao(em);
//            return dao.getPlayers();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public Player getPlayer(int id) throws NullPointerException {
//        try {
//            Session session = sessionFactory.openSession();
//            PlayerDao dao = new PlayerDao(em);
//            session.close();
//            return dao.getPlayer(id);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Player addPlayer(String lastName, String firstName, String secondName, Date dateBirth, int clubId) throws DbException {
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            PlayerDao dao = new PlayerDao(sesion);
//            Player player = new Player(lastName, firstName, secondName, dateBirth, clubId);
//            dao.insertPlayer(player);
//            transaction.commit();
//            session.close();
//            return player;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DbException();
//        }
//    }
//
////    public void updatePlayer(String lastName, String firstName, String secondName, Date dateBirth, int clubId) {
////        try {
////            Session session = sessionFactory.openSession();
////            Transaction transaction = session.beginTransaction();
////            PlayerDao dao = new PlayerDao(session);
////            dao.updatePlayer(new Player(lastName, firstName, secondName, dateBirth, clubId));
////            transaction.commit();
////            session.close();
////        } catch (HibernateException e) {
////            e.printStackTrace();
////        }
////    }
//
//    public void deletePlayer(int id) {
//        try{
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            PlayerDao dao = new PlayerDao(em);
//            dao.deletePlayer(id);
//            transaction.commit();
//            session.close();
//        }
//        catch (HibernateException e){
//            e.printStackTrace();
//        }
//    }
//
//    public List<Club> getClubs() {
//        try {
//            Session session = sessionFactory.openSession();
//            ClubDao dao = new ClubDao(session);
//            return dao.getClubs();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public Club getClub(int id) throws NullPointerException {
//        try {
//            Session session = sessionFactory.openSession();
//            ClubDao dao = new ClubDao(session);
//            session.close();
//            return dao.getClub(id);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void addClub(String name) {
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            ClubDao dao = new ClubDao(session);
//            dao.insertClub(new Club(name));
//            transaction.commit();
//            session.close();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//    }
//
////    public void updateClub(String name) {
////        try {
////            Session session = sessionFactory.openSession();
////            Transaction transaction = session.beginTransaction();
////            ClubDao dao = new ClubDao(session);
////            dao.updateClub(new Club(name));
////            transaction.commit();
////            session.close();
////        } catch (HibernateException e) {
////            e.printStackTrace();
////        }
////    }
//
//    public void deleteClub(int id) {
//        try{
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            ClubDao dao = new ClubDao(session);
//            dao.deleteClub(id);
//            transaction.commit();
//            session.close();
//        }
//        catch (HibernateException e){
//            e.printStackTrace();
//        }
//    }
//
//    private static SessionFactory createSessionFactory(Configuration configuration) {
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
//        builder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();
//        return configuration.buildSessionFactory(serviceRegistry);
//    }
//}
