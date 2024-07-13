package lk.ijse.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.Student;
import lk.ijse.Dao.impl.DataProcess;


import java.io.IOException;
import java.sql.*;

import static jakarta.json.Json.createReader;

@WebServlet(urlPatterns = "/student",loadOnStartup = 2
//        initParams = {
//        @WebInitParam(name = "driver-class",value = "com.mysql.cj.jdbc.Driver"),
//         @WebInitParam(name = "dbURL",value = "jdbc:mysql://localhost:3306/aad67JavaEE?createDatabaseIfNotExist=true"),
//         @WebInitParam(name = "dbUserName",value = "root"),
//         @WebInitParam(name = "dbPassword",value = "Ijse@1234")
//        }
)
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
        var id = req.getParameter("id");
        var dataProcess =new DataProcess();

          try (var writer = resp.getWriter()) {
              Student student1 = dataProcess.getstudent(id, connection);
              resp.setContentType("application/json");
              var jsonb = JsonbBuilder.create();
              jsonb.toJson(student1, writer);
          }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo: save student
      try(var writer = resp.getWriter()) {
          var jsonb = JsonbBuilder.create();
          Student student =jsonb.fromJson(req.getReader(),Student.class);
          var savedata =new DataProcess();
            writer.write(savedata.saveStudent(student,connection));
            resp.setContentType("application/json");

            jsonb.toJson(student,writer);
        }

       // String ID = UUID.randomUUID().toString();




    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         //Todo: update student
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

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
                writer.write("something went wrong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
