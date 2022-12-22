package com.joao;

import com.joao.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (factory) {

            Student student1 = new Student("John", "Doo", "john@test.com");
            Student student2 = new Student("Mary", "Public", "mary@test.com");
            Student student3 = new Student("Bonita", "Applebum", "bonita@test.com");
            Student student4 = new Student("Daffy", "Duck", "daffy@test.com");

            /*
             * Saving Objects
             */
            Session session = factory.getCurrentSession();
            savingEntityExample(session, Arrays.asList(student1, student2, student3, student4));

            /*
             * Reading Objects
             */
            session = factory.getCurrentSession();
            retrieveEntityById(session, 2);

            /*
             * Querying objects
             */
            session = factory.getCurrentSession();
            session.beginTransaction();

            List<Student> studentList = queryDatabase(session, "from Student");
            printQueryResult(studentList);

            studentList = queryDatabase(session, "from Student s where s.lastName = 'Doo'");
            printQueryResult(studentList);

            studentList = queryDatabase(session, "from Student s where s.lastName = 'Doo' or s.firstName = 'Daffy'");
            printQueryResult(studentList);

            studentList = queryDatabase(session, "from Student s where s.email like '%test.com'");
            printQueryResult(studentList);

            session.getTransaction().commit();

            /*
             * Updating records in a transaction
             * */
            session = factory.getCurrentSession();

            session.beginTransaction();
            Student result = session.get(Student.class, 1);
            result.setFirstName("Scooby");
            session.getTransaction().commit();

            /*
             * Writing update statement
             * */
            session = factory.getCurrentSession();

            session.beginTransaction();
            session.createQuery("update Student set email = 'scoobydoo@test.com' where id = 1").executeUpdate();
            session.getTransaction().commit();

            /*
             * Deleting record in a transaction
             * */
            session = factory.getCurrentSession();
            result = retrieveEntityById(session, 2);

            session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(result);
            session.getTransaction().commit();

            /*
             * Delete statement
             * */
            session = factory.getCurrentSession();

            session.beginTransaction();
            session.createQuery("delete Student where id = 3").executeUpdate();
            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();

            int total = queryDatabase(session, "from Student").size();
            System.out.println("Total of records: " + total );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printQueryResult(List<Student> studentList) {
        studentList.forEach(student -> {
            System.out.println(student.toString());
        });
    }

    private static void savingEntityExample(Session session, List<Student> entities) {
        session.beginTransaction();
        entities.forEach(session::save);
        session.getTransaction().commit();
    }

    private static Student retrieveEntityById(Session session, Integer id) {
        session.beginTransaction();
        Student result = session.get(Student.class, id);
        session.getTransaction().commit();

        System.out.println(result.toString());
        return result;
    }

    private static List queryDatabase(Session session, String from_Student) {
        return session.createQuery(from_Student).getResultList();
    }
}