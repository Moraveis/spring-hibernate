package com.joao;

import com.joao.onetomany.Course;
import com.joao.onetoone.Instructor;
import com.joao.onetoone.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneToManyMain {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try (factory;) {
            Instructor instructor = new Instructor("Bruce", "Lee", "bruce@lee.he");
            InstructorDetail instructorDetail1 = new InstructorDetail("Martial Arts 101", "Meditation");
            instructor.setInstructorDetail(instructorDetail1);

            session.beginTransaction();
            session.save(instructor);
            session.getTransaction().commit();

            Course karateCourse = new Course("Karate");
            Course meditationCourse = new Course("Meditation");

            instructor.addCourse(karateCourse);
            instructor.addCourse(meditationCourse);

            session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(karateCourse);
            session.save(meditationCourse);
            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();
            Instructor result = session.get(Instructor.class, 1);
            System.out.println("Result = " + result);
            System.out.println("Course = " + result.getCourses());
            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();

            Course toDelete = session.get(Course.class, 2);
            session.delete(toDelete);
            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();

            result = session.get(Instructor.class, 1);
            System.out.println("Result = " + result);
            System.out.println("Course = " + result.getCourses());
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
