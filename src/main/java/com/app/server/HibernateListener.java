package com.app.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class HibernateListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HibernateUtil.getSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateUtil.getSessionFactory().close();
    }
}
