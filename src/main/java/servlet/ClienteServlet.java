/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Naomi Alejandra Vega
 */
@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = reader.readLine()) != null) {
            sb.append(linea);
        }

        JSONObject obj = new JSONObject(sb.toString());

        Cliente c = new Cliente();
        c.setNdniClie(obj.getString("ndniClie"));
        c.setAppaClie(obj.getString("appaClie"));
        c.setApmaClie(obj.getString("apmaClie"));
        c.setNombClie(obj.getString("nombClie"));
        c.setFechNaciClie(Date.valueOf(obj.getString("fechNaciClie")));
        c.setLogiClie(obj.getString("logiClie"));
        c.setPassClie("123456"); // por defecto

        ClienteJpaController dao = new ClienteJpaController();
        dao.create(c);

        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"ok\"}");
    }
}

