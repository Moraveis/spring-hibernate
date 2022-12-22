package com.joao;

import com.joao.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (factory) {
            Session session = factory.getCurrentSession();

            Student student1 = new Student("John", "Doe", "john@test.com");
            Student student2 = new Student("Mary", "Public", "mary@test.com");
            Student student3 = new Student("Bonita", "Applebum", "bonita@test.com");
            Student student4 = new Student("Daffy", "Duck", "daffy@test.com");


            /**
             * Saving Objects
             */
            session.beginTransaction();

            session.save(student1);
            session.save(student2);
            session.save(student3);
            session.save(student4);
            System.out.println("Id: "+ student4.getId());

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}