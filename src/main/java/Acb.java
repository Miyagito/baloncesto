import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Acb extends HttpServlet {

    private ModeloDatos bd;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        try {
            bd = new ModeloDatos();
            bd.abrirConexion();
        } catch (Exception e) {
            // Log the exception and rethrow as ServletException
            e.printStackTrace();
            throw new ServletException("No se pudo abrir la conexión con la base de datos", e);
        }
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        String nombreP = req.getParameter("txtNombre");
        String nombre = req.getParameter("R1");
        String accion = req.getParameter("accion");
    
        try {
            if ("VerVotos".equals(accion)) {
                // Si la acción es VerVotos, simplemente redirige a la página correspondiente
                res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
                return;
            }

            if ("resetVotos".equals(accion)) {
                bd.resetearVotos();
                // Redirigir a alguna página o mostrar un mensaje de éxito
                res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
                return;
            }
    
            // Solo intenta procesar votos si la acción no es VerVotos y los parámetros son válidos
            if (nombreP != null && nombre != null) {
                if ("Otros".equals(nombre)) {
                    nombre = req.getParameter("txtOtros");
                }
    
                if (bd != null) {
                    if (bd.existeJugador(nombre)) {
                        bd.actualizarJugador(nombre);
                    } else {
                        bd.insertarJugador(nombre);
                    }
                } else {
                    throw new ServletException("La conexión con la base de datos no está establecida.");
                }
                s.setAttribute("nombreCliente", nombreP);
                res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
            } else {
                throw new ServletException("Nombre del visitante o acción no proporcionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al procesar la petición", e);
        }
    }

    @Override
    public void destroy() {
        if (bd != null) {
            bd.cerrarConexion();
        }
        super.destroy();
    }
}
