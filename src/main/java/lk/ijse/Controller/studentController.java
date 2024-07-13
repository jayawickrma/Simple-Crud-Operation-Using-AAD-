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


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!req.getContentType().toLowerCase().startsWith("application/json")|| req.getContentType() == null){
            //send error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        // Persist Data




    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Todo: update student

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

