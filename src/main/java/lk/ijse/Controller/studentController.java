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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.json.Json.createReader;

@WebServlet(urlPatterns = "/student")
public class studentController extends HttpServlet {
    Connection connection;
    static  String save_student ="INSERT INTO STUDENT VALUES (?,?,?,?,?)";
    @Override
    public void init() throws ServletException {
        try {
        var driver = getServletContext().getInitParameter("driver-class");
        var dburl =getServletContext().getInitParameter("dbURL");
        var username =getServletContext().getInitParameter("dbUserName");
        var password =getServletContext().getInitParameter("dbPassword");

            Class.forName(driver);
            connection = DriverManager.getConnection(dburl,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: get student details
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //Todo: save student
        if (!req.getContentType().toLowerCase().contains("application/json")|| req.getContentType()==null){
          //send error
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        String ID = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        Student student1 = jsonb.fromJson(req.getReader(), Student.class);
        student1.setId(ID);
        System.out.println(student1);    //   using student class and ad only i student

            try{
                var ps =connection.prepareStatement(save_student);
                ps.setString(1,student1.getId());
                ps.setString(2,student1.getName());
                ps.setString(3,student1.getCity());
                ps.setString(4,student1.getEmail());
                ps.setString(4,student1.getLevel());
                if (ps.executeUpdate() !=0){
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
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: delete student
    }
}
