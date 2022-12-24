package com.joao;

import com.joao.onetomany.Course;
import com.joao.onetoone.Instructor;
import com.joao.onetoone.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneToOneMain {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            /*
             * Create instructor Example
             * */
            Instructor instructor1 = new Instructor("Joao", "Vitor", "joao@vitor.me");
            InstructorDetail instructorDetail1 = new InstructorDetail("udemy course", "coding");
            instructor1.setInstructorDetail(instructorDetail1);

            Instructor instructor2 = new Instructor("Bruce", "Lee", "bruce@lee.he");
            InstructorDetail instructorDetail2 = new InstructorDetail("Martial Arts 101", "Meditation");
            instructor2.setInstructorDetail(instructorDetail2);

            session.beginTransaction();
            session.save(instructor1);
            session.save(instructor2);
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

            /*
             * Bidirectional mapping example
             * */
            session = factory.getCurrentSession();
            session.beginTransaction();

            InstructorDetail detail = session.get(InstructorDetail.class, 2);
            System.out.println("Result = " + detail);
            System.out.println("instructorDetail > " + detail.getInstructor());

            session.getTransaction().commit();

            /*
             * Deleting Instruction Detail
             * */
            session = factory.getCurrentSession();
            session.beginTransaction();

            detail = session.get(InstructorDetail.class, 2);
            System.out.println("Result = " + detail);
            System.out.println("instructorDetail > " + detail.getInstructor());

            detail.getInstructor().setInstructorDetail(null);
            session.delete(detail);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }

    }
}
