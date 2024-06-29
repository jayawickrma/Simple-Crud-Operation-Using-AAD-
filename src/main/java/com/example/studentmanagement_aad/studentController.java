package com.example.studentmanagement_aad;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

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
        //process
//        BufferedReader reader =req.getReader();
//        StringBuilder sb =new StringBuilder();
//        var writer =resp.getWriter();
//        reader.lines().forEach(line->sb.append(line+"\n"));
//        System.out.println(sb);
//        writer.write(sb.toString());
//        writer.close();


        //Json emulate with parson
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject =reader.readObject();
        System.out.println(jsonObject.getString("email"));

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
