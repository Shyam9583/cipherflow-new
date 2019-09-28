package service;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    static {
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            // Create MetadataSources
            MetadataSources sources = new MetadataSources(registry);
            // Create Metadata
            Metadata metadata = sources.getMetadataBuilder().build();
            // Create SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}