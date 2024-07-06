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
import java.util.UUID;

import static jakarta.json.Json.createReader;

@WebServlet(urlPatterns = "/student")
public class studentController extends HttpServlet {
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
        Student student = jsonb.fromJson(req.getReader(), Student.class);  //front end eken ena data tika bind wenna ona class eka
        student.setId(ID);
        System.out.println(student);


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
