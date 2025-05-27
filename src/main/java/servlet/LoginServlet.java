package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String clave = request.getParameter("clave");

        Cliente cliente = new ClienteJpaController().validar(login, clave);

        if (cliente != null) {
            HttpSession session = request.getSession();
            session.setAttribute("cliente", cliente);
            response.sendRedirect("principal.html");
        } else {
            response.sendRedirect("login.html?error=1");
        }
    }
}
