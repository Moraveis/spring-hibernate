package com.joao;

import com.joao.entity.Student;
import com.joao.onetomany.Course;
import com.joao.onetomany.Review;
import com.joao.onetoone.Instructor;
import com.joao.onetoone.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManyToManyMain {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try (factory) {

            // SAVING EXAMPLE
            session.beginTransaction();

            Course course = new Course("Spring hibernate");
            session.save(course);

            Student student1 = new Student("Joao", "Vitor", "joao@vitor.me");
            Student student2 = new Student("temp", "student", "temp@student.me");

            course.addStudent(student1);
            course.addStudent(student2);

            session.save(student1);
            session.save(student2);

            session.getTransaction().commit();

            // FETCH EXAMPLE + SAVING OTHER ENTITIES
            session = factory.getCurrentSession();
            session.beginTransaction();
            Student result = session.get(Student.class, 2);
            System.out.println("Student: " + result);
            System.out.println("Student's courses " + result.getCourses());

            Course course2 = new Course("Maths for programming");
            Course course3 = new Course("Programming logic");

            course2.addStudent(result);
            course3.addStudent(result);

            session.save(course2);
            session.save(course3);

            session.getTransaction().commit();

            // VERIFICATION OF INSERTS
            session = factory.getCurrentSession();
            session.beginTransaction();

            result = session.get(Student.class, 2);
            System.out.println("Result: " + result.getCourses());

            session.getTransaction().commit();

            // DELETING EXAMPLE
            session = factory.getCurrentSession();
            session.beginTransaction();

            Course toDelete = session.get(Course.class, 1);
            session.delete(toDelete);
            session.getTransaction().commit();

            System.out.println("Student 1: " + student1);
            System.out.println("Student 1's course: " + student1.getCourses());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
