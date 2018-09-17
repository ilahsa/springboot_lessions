package com.alta.hello.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by baiba on 2018-09-14.
 * 启动的时候启动类添加 @ServletComponentScan("com.alta.hello.servlet")
 */
@WebServlet(urlPatterns="/myservlet2/*",description="Servlet的说明")
public class MyServlet2  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">>>>>>>>>>doGet()<<<<<<<<<<<");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(">>>>>>>>>>doPost()<<<<<<<<<<<");

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");

        out.println("<title>HelloWorld</title>");

        out.println("</head>");

        out.println("<body>");

        out.println("<h1>这是：MyServlet2</h1>");

        out.println("</body>");

        out.println("</html>");

    }
}
