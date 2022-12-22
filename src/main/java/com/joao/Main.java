package com.joao;

import com.joao.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

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

            /*
             * Saving Objects
             */
            session.beginTransaction();

            session.save(student1);
            session.save(student2);
            session.save(student3);
            session.save(student4);
            System.out.println("Id: " + student4.getId());

            session.getTransaction().commit();

            /*
             * Reading Objects
             */

            session = factory.getCurrentSession();
            session.beginTransaction();
            Student result = session.get(Student.class, 1);
            session.getTransaction().commit();

            System.out.println(result.toString());

            /*
             * Querying objects
             */
            session = factory.getCurrentSession();
            session.beginTransaction();

            List<Student> studentList = session.createQuery("from Student").getResultList();
            studentList.forEach(student -> {
                System.out.println(student.toString());
            });

            studentList = session.createQuery("from Student s where s.lastName = 'Doe'").getResultList();
            studentList.forEach(student -> {
                System.out.println(student.toString());
            });

            studentList = session.createQuery("from Student s where s.lastName = 'Doe' or s.firstName = 'Daffy'").getResultList();
            studentList.forEach(student -> {
                System.out.println(student.toString());
            });

            studentList = session.createQuery("from Student s where s.email like '%test.com'").getResultList();
            studentList.forEach(student -> {
                System.out.println(student.toString());
            });

            session.getTransaction().commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}