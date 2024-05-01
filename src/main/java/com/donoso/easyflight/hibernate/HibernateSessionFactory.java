package com.donoso.easyflight.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    public org.hibernate.Session session;

    public HibernateSessionFactory() {
        openSession();
    }

    public void openSession(){
        if(session == null || !session.isOpen()) {
            SessionFactory sf = new Configuration().configure().buildSessionFactory();
            this.session = sf.openSession();
        }
    }

}
