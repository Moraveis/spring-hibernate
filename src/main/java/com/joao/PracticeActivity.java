package com.joao;

import com.joao.entity.Employee;
import com.joao.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class PracticeActivity {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        try (factory) {
            Employee employee1 = new Employee("Joao", "A", "Company A");
            Employee employee2 = new Employee("Maria", "B", "Company A");
            Employee employee3 = new Employee("Lucas", "C", "Company B");
            Employee employee4 = new Employee("Pedro", "D", "Company C");
            Employee employee5 = new Employee("Joaquim", "E", "Company D");

            Session session = factory.getCurrentSession();
            savingEntityExample(session, Arrays.asList(employee1, employee2, employee3, employee4, employee5));

            session = factory.getCurrentSession();
            Employee result = retrieveEntityById(session, 2);
            System.out.println(result);

            session = factory.getCurrentSession();
            session.beginTransaction();
            List<Employee> list = queryDatabase(session, "from Employee where company = 'Company A'");
            session.getTransaction().commit();
            printQueryResult(list);

            session = factory.getCurrentSession();
            result = retrieveEntityById(session, 3);

            session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(result);
            session.getTransaction().commit();

            session = factory.getCurrentSession();
            session.beginTransaction();

            int total = queryDatabase(session, "from Employee").size();
            System.out.println("Total of records: " + total);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printQueryResult(List<Employee> studentList) {
        studentList.forEach(employee -> {
            System.out.println(employee.toString());
        });
    }

    private static void savingEntityExample(Session session, List<Employee> entities) {
        session.beginTransaction();
        entities.forEach(session::save);
        session.getTransaction().commit();
    }

    private static Employee retrieveEntityById(Session session, Integer id) {
        session.beginTransaction();
        Employee result = session.get(Employee.class, id);
        session.getTransaction().commit();

        System.out.println(result.toString());
        return result;
    }

    private static List queryDatabase(Session session, String query) {
        return session.createQuery(query).getResultList();
    }
}
