package com.joao;

import com.joao.oneToOne.Instructor;
import com.joao.oneToOne.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneToOneMain {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();

        try (factory) {
            Instructor instructor = new Instructor("Joao", "Vitor", "joao@vitor.me");
            InstructorDetail instructorDetail = new InstructorDetail("udemy course", "coding");

            instructor.setInstructorDetail(instructorDetail);

            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(instructor);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
