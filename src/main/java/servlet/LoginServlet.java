package servlet;

import dao.ClienteJpaController;
import dto.Cliente;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = reader.readLine()) != null) {
            sb.append(linea);
        }

        JSONObject json = new JSONObject(sb.toString());
        String login = json.getString("login");
        String clave = json.getString("clave");

        Cliente cliente = new ClienteJpaController().validar(login, clave);

        response.setContentType("application/json");

        if (cliente != null) {
            request.getSession().setAttribute("cliente", cliente);
            response.setStatus(HttpServletResponse.SC_OK); // 200
            response.getWriter().write("{\"mensaje\":\"Login exitoso\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("{\"mensaje\":\"Login o contraseña incorrectos\"}");
        }
    }
}
