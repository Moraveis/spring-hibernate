package com.joao;

import com.joao.onetomany.Course;
import com.joao.onetomany.Review;
import com.joao.onetoone.Instructor;
import com.joao.onetoone.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ReviewMain {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        try (factory) {
            session.beginTransaction();

            Course course = new Course("Test Course");

            Review review1 = new Review("Review comment 1");
            Review review2 = new Review("Comment 2");
            Review review3 = new Review("some other comment");

            course.addReview(review1);
            course.addReview(review2);
            course.addReview(review3);

            session.save(course);
            session.getTransaction().commit();

            // Get Course and review information
            session = factory.getCurrentSession();
            session.beginTransaction();
            Course courseFound = session.get(Course.class, 1);
            System.out.println("Course: " + courseFound);
            System.out.println("Reviews" + courseFound.getReviews());
            session.getTransaction().commit();

            // Delete example
            session = factory.getCurrentSession();
            session.beginTransaction();
            courseFound = session.get(Course.class, 1);
            System.out.println("Course: " + courseFound);
            System.out.println("Reviews" + courseFound.getReviews());
            session.delete(courseFound);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }


}
