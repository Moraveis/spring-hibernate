package com.joao;

import com.joao.onetomany.Course;
import com.joao.onetoone.Instructor;
import com.joao.onetoone.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class FetchJoinMain {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        try (factory) {
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

            int theId = 1;

            String myQuery = "select i from Instructor i " +
                    "JOIN FETCH i.courses " +
                    "where i.id =:theId";

            Query<Instructor> query = session.createQuery(myQuery, Instructor.class);
            query.setParameter("theId", theId);

            Instructor result = query.getSingleResult();
            System.out.println("Result from query = " + result);

            session.getTransaction().commit();

            System.out.println("Course from Instructor = " + result.getCourses());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
