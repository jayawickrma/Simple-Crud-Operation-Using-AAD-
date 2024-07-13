package lk.ijse.Dao;

import lk.ijse.DTO.Student;

import java.sql.Connection;

public interface Data {


    Student getstudent(String studID, Connection connection);

    String saveStudent(Student student, Connection connection);
    boolean deleteStudent(String studentID,Connection connection);
    boolean updtstudent(String studentID,Student student);

}
