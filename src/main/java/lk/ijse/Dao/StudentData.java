package lk.ijse.Dao;

import lk.ijse.DTO.Student;

import java.sql.Connection;

public interface StudentData {

        Student getstudent(String studentID,Connection connection);
        boolean savestudent(Student student ,Connection connection);
        boolean deletestudent(String studentId ,Connection connection);
        boolean updatestudent(String studentId,Student student,Connection connection);
}
