package lk.ijse.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(urlPatterns = "/Demo",loadOnStartup = 1)
public class Demo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String quaryString =req.getQueryString();
        System.out.println(quaryString);
        var myheader = req.getHeader("X-myheader");
        System.out.println(myheader);
    }
}
