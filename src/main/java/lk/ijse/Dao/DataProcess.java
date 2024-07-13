package lk.ijse.Dao;

import lk.ijse.DTO.Student;

import java.sql.Connection;
import java.sql.SQLException;

public class DataProcess implements Data{
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
