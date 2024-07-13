package lk.ijse.Dao.impl;

import lk.ijse.DTO.Student;
import lk.ijse.Dao.StudentData;
import lombok.var;


import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataProcess implements StudentData {
    static String save_student = "INSERT INTO students(id,name,email,city,level) VALUES(?,?,?,?,?)";
    static String get_student = "SELECT * FROM students WHERE id =?";
    static String update_student = "UPDATE students SET name=?,email=?,city=?,level=? WHERE id=?";
    static String delete_student = "DELETE FROM students WHERE id=?";


    @Override
    public Student getstudent(String studentID, Connection connection) {
        var student =new Student();
        try{
            var preparedStatement = connection.prepareStatement(get_student);
            preparedStatement.setString(1,studentID);
            var resultSet =preparedStatement.executeQuery();
            while (resultSet.next()){
                student.setId(resultSet.getString("id"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setCity(resultSet.getString("city"));
                student.setLevel(resultSet.getString("level"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    @Override
    public boolean savestudent(Student student, Connection connection) {
       try {
           var preparedStatement =connection.prepareStatement(save_student);
                    preparedStatement.setString(1,student.getId());
                    preparedStatement.setString(2,student.getName());
                    preparedStatement.setString(3,student.getEmail());
                    preparedStatement.setString(4,student.getCity());
                    preparedStatement.setString(5,student.getLevel());
                    return preparedStatement.executeUpdate()!=0;
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public boolean deletestudent(String studentId, Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(delete_student);
            preparedStatement.setString(1,studentId);
            return preparedStatement.executeUpdate()!= 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatestudent(String studentId, Student updateStudent, Connection connection) {
        try {
            var preparedStatement = connection.prepareStatement(update_student);
            preparedStatement.setString(1,updateStudent.getName());
            preparedStatement.setString(2,updateStudent.getEmail());
            preparedStatement.setString(3,updateStudent.getCity());
            preparedStatement.setString(4,updateStudent.getLevel());
            preparedStatement.setString(5,studentId);
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
