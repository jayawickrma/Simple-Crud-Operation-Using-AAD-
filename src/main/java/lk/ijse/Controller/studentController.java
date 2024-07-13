package lk.ijse.Controller;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.Student;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.rmi.server.UID;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.json.Json.createReader;

@WebServlet(urlPatterns = "/student",loadOnStartup = 2)
public class studentController extends HttpServlet {
    Connection connection;
    static  String save_student ="INSERT INTO students(id,name,email,city,level) VALUES(?,?,?,?,?)";
    static String get_student ="SELECT * FROM students WHERE id =?";
    static String update_student="UPDATE students SET name=?,email=?,city=?,level=? WHERE id=?";
    static String delete_student ="DELETE FROM students WHERE id=?";
    @Override
    public void init() throws ServletException {
        try {
        var driver = getServletContext().getInitParameter("driver-class");
        var dburl =getServletContext().getInitParameter("dbURL");
        var username =getServletContext().getInitParameter("dbUserName");
        var password =getServletContext().getInitParameter("dbPassword");

            Class.forName(driver);
           this.connection = DriverManager.getConnection(dburl,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: get student details
        var Student =new Student();
        var id = req.getParameter("id");
        try {
          try (var writer = resp.getWriter()) {
             var ps =connection.prepareStatement(get_student);
             ps.setString(1,id);
            var resultSet =ps.executeQuery();
            while (resultSet.next()){
                Student.setId(resultSet.getString("id"));
                Student.setName(resultSet.getString("name"));
                Student.setEmail(resultSet.getString("email"));
                Student.setCity(resultSet.getString("city"));
                Student.setLevel(resultSet.getString("level"));
            }
              System.out.println(Student);
            resp.setContentType("application/json");
            var json =JsonbBuilder.create();
            json.toJson(Student,resp.getWriter());
          }

      } catch (Exception e) {
          throw new RuntimeException(e);
      }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo: save student
        if (!req.getContentType().toLowerCase().contains("application/json")|| req.getContentType()==null){
          //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
       // String ID = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        Student student1 = jsonb.fromJson(req.getReader(), Student.class);

        System.out.println(student1);    //   using student class and ad only i student

            try{
                var ps =connection.prepareStatement(save_student);
                ps.setString(1,student1.getId());
                ps.setString(2,student1.getName());
                ps.setString(3,student1.getEmail());
                ps.setString(4,student1.getCity());
                ps.setString(5,student1.getLevel());
                if (ps.executeUpdate()!=0){
                    resp.getWriter().write("student saved");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


//        List<Student>student = jsonb.fromJson(req.getReader(),new ArrayList<Student>(){}.getClass().getGenericSuperclass());  //front end eken ena data tika bind wenna ona class eka(add student list as an array)
//        student.forEach(System.out::println);


        //process
//        BufferedReader reader =req.getReader();
//        StringBuilder sb =new StringBuilder();
//        var writer =resp.getWriter();
//        reader.lines().forEach(line->sb.append(line+"\n"));
//        System.out.println(sb);
//        writer.write(sb.toString());
//        writer.close();


//        //Json emulate with parson
//        JsonReader reader = Json.createReader(req.getReader());
//        JsonArray jsonArray =reader.readArray();
//        for (int i =0 ;i<jsonArray.size();i++){
//            JsonObject jsonObject =jsonArray.getJsonObject(i);
//            System.out.println(jsonObject.getString("name"));
//        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         //Todo: update student

        try (var writer=resp.getWriter()){
            var ps =connection.prepareStatement(update_student);
            var id =req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            Student student = jsonb.fromJson(req.getReader(), Student.class);
            ps.setString(1,student.getName());
            ps.setString(2,student.getEmail());
            ps.setString(3,student.getCity());
            ps.setString(4,student.getLevel());
            ps.setString(5,id);

            if (ps.executeUpdate()>0){
              writer.write("student updated");
            }else {
                writer.write("something went wrong when updating student details");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: delete student
        var id =req.getParameter("id");
        try(var writer=resp.getWriter()){
            var ps =connection.prepareStatement(delete_student);
            ps.setString(1,id);
            if (ps.executeUpdate()>0){
                writer.write("student deleted");
            }else {
                writer.write("somethung went wrong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
