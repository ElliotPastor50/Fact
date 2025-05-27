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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Naomi Alejandra Vega
 */
@WebServlet("/CambiarClaveServlet")
public class CambiarClaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cliente user = (Cliente) session.getAttribute("cliente");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = reader.readLine()) != null) {
            sb.append(linea);
        }

        JSONObject obj = new JSONObject(sb.toString());

        String actual = obj.getString("actual");
        String nueva = obj.getString("nueva");
        String confirma = obj.getString("confirma");

        JSONObject res = new JSONObject();
        if (!user.getPassClie().equals(actual)) {
            res.put("mensaje", "Clave actual incorrecta");
        } else if (!nueva.equals(confirma)) {
            res.put("mensaje", "La nueva clave no coincide");
        } else {
            user.setPassClie(nueva);
            try {
                new ClienteJpaController().edit(user);
            } catch (Exception ex) {
                Logger.getLogger(CambiarClaveServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            res.put("mensaje", "Clave actualizada correctamente");
        }

        response.setContentType("application/json");
        response.getWriter().write(res.toString());
    }
}

