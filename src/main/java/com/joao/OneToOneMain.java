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
            /*
            * Create instructor Example
            * */
            Instructor instructor = new Instructor("Joao", "Vitor", "joao@vitor.me");
            InstructorDetail instructorDetail = new InstructorDetail("udemy course", "coding");

            instructor.setInstructorDetail(instructorDetail);

            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(instructor);
            session.getTransaction().commit();

            /*
            * Deleting an Instructor record
            * */
            session = factory.getCurrentSession();
            session.beginTransaction();

            Instructor result = session.get(Instructor.class, 1);
            System.out.println("Result = " + result);
            if (result != null) {
                session.delete(result);
                System.out.println("Instructor deleted.");
            }
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
