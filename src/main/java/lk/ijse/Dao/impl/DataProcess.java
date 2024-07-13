package lk.ijse.Dao.impl;

import lk.ijse.DTO.Student;
import lk.ijse.Dao.Data;

import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

public class DataProcess implements Data {
    static  String save_student ="INSERT INTO students(id,name,email,city,level) VALUES(?,?,?,?,?)";
    static String get_student ="SELECT * FROM students WHERE id =?";
    static String update_student="UPDATE students SET name=?,email=?,city=?,level=? WHERE id=?";
    static String delete_student ="DELETE FROM students WHERE id=?";
    Connection connection;

    @Override
    public Student getstudent(String studID, Connection connection) {
        var student = new Student();
        try {
            var ps = connection.prepareStatement(get_student);
            ps.setString(1, studID);
            var resultSet = ps.executeQuery();
            while (resultSet.next()) {
                student.setId(resultSet.getString("id"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setCity(resultSet.getString("city"));
                student.setLevel(resultSet.getString("level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }




    @Override
    public String saveStudent(Student student, Connection connection) {
        try{
            var ps =connection.prepareStatement(save_student);
            ps.setString(1,student.getId());
            ps.setString(2,student.getName());
            ps.setString(3,student.getEmail());
            ps.setString(4,student.getCity());
            ps.setString(5,student.getLevel());
            if (ps.executeUpdate()!=0){
                System.out.println("student saved successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteStudent(String studentID, Connection connection) {
        return false;
    }

    @Override
    public boolean updtstudent(String studentID, Student student) {
        return false;
    }


}
