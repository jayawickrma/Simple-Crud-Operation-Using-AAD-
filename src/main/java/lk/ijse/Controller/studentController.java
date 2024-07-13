package lk.ijse.Controller;

import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.DTO.Student;
import lk.ijse.Dao.impl.DataProcess;
import lk.ijse.util.UtilProcess;


import java.io.IOException;
import java.io.Writer;
import java.rmi.RemoteException;
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


    @Override
    public void init() throws ServletException {
        try {
            var driver = getServletContext().getInitParameter("driver-class");
            var dburl = getServletContext().getInitParameter("dbURL");
            var username = getServletContext().getInitParameter("dbUserName");
            var password = getServletContext().getInitParameter("dbPassword");

            Class.forName(driver);
            this.connection = DriverManager.getConnection(dburl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: get student details
        var id = req.getParameter("id");
        var dataProcess = new DataProcess();
            try(var writer =resp.getWriter()){
                var getstudent = dataProcess.getstudent(id, connection);
                writer.write(String.valueOf(getstudent));
                resp.setContentType("application/json");
                var jsonb = JsonbBuilder.create();
                jsonb.toJson(getstudent,resp.getWriter());

            }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
            try (Writer writer =resp.getWriter()){
                var jsonb = JsonbBuilder.create();
                Student student=jsonb.fromJson(req.getReader(), Student.class);
                student.setId(UtilProcess.generateId());
                var dataProcess = new DataProcess();
               if(dataProcess.savestudent(student,connection)){
                    writer.write("student saved successfully");
                }else{
                   writer.write("something went wrong");
               }
            }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: update student
        try(Writer writer =resp.getWriter()) {
            var studentID =req.getParameter("id");
            var jsonb = JsonbBuilder.create();
            var dataProcess = new DataProcess();
            var updateStudnt =jsonb.fromJson(req.getReader(), Student.class);
            if (dataProcess.updatestudent(studentID,updateStudnt,connection)){
                writer.write("student updated");
            }else {
                writer.write("student update failed");
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id =req.getParameter("id");
        try (Writer writer =resp.getWriter()){
            var dataProcess = new DataProcess();
                if (dataProcess.deletestudent(id,connection)){
                    writer.write("student deleted");
                }else{
                    writer.write("student delete failed");
                }
        }
    }
}

