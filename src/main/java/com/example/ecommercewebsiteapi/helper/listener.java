package com.example.ecommercewebsiteapi.helper;

import com.example.ecommercewebsiteapi.modules.SiteDAO;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.SQLException;

public class listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String DB_NAME = (String) sce.getServletContext().getInitParameter("DB_NAME");
        String USER = (String) sce.getServletContext().getInitParameter("USER_NAME");
        String PASS = (String) sce.getServletContext().getInitParameter("USER_PASSWORD");
        try {
            System.out.println("in listener");
            Class.forName("org.postgresql.Driver").newInstance();
            new SiteDAO(DB_NAME,USER,PASS);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("database connection error "+ e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
