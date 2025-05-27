/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
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

/**
 *
 * @author Naomi Alejandra Vega
 */
@WebServlet("/CambiarClaveServlet")
public class CambiarClaveServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cliente user = (Cliente) session.getAttribute("cliente");

        String actual = request.getParameter("actual");
        String nueva = request.getParameter("nueva");
        String confirma = request.getParameter("confirma");

        if (!user.getPassClie().equals(actual)) {
            response.sendRedirect("cambiarClave.html?error=clave_incorrecta");
            return;
        }
        if (!nueva.equals(confirma)) {
            response.sendRedirect("cambiarClave.html?error=no_coincide");
            return;
        }

        user.setPassClie(nueva);
        try {
            new ClienteJpaController().edit(user);
        } catch (Exception ex) {
            Logger.getLogger(CambiarClaveServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("principal.html?clave_actualizada=1");
    }
}
