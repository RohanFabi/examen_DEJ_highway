package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Carretera;
import cl.duoc.dej.tienda.entity.Carretera;

import cl.duoc.dej.tienda.exception.CarreteraNoEncontradoException;

import cl.duoc.dej.tienda.service.CarreteraService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CarreteraServlet", urlPatterns = {"/catalogo"})
public class CarreteraServlet extends HttpServlet {

    @EJB
    CarreteraService carreteraService;

    private final String JSP_LISTA_PRODUCTOS = "/WEB-INF/jsp/carretera/listar.jsp";
    private final String JSP_CREAR = "/WEB-INF/jsp/carretera/crear.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";
        switch (operacion) {

            case "buscar":
                buscar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response, List<Carretera> carreteras) throws ServletException, IOException {

        request.setAttribute("carreteras", carreteras);

        request.getRequestDispatcher(JSP_LISTA_PRODUCTOS).forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Carretera> carreteras = carreteraService.getCarreteras();
        listar(request, response, carreteras);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long carreteraId = 0L;
        try {
            carreteraId = Long.parseLong(stringId);
            carreteraService.eliminarCarretera(carreteraId);
            mensaje = String.format("Se ha eliminado correctamente el carretera con ID %s", carreteraId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (CarreteraNoEncontradoException ex) {
            error = String.format("No se pudo encontrar el carretera con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carreteraBuscada = request.getParameter("carretera");

        List<Carretera> carreteras = carreteraService.buscarCarretera(carreteraBuscada);
        listar(request, response, carreteras);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String nombre = request.getParameter("carretera");

        String stringPrecio = request.getParameter("precio");

        Long precio = 0L;

        try {

            precio = Long.parseLong(stringPrecio);
            Carretera carretera = new Carretera(precio, nombre, precio);
            carretera = carreteraService.crearCarretera(carretera);
            mensaje = String.format("Carretera %s creada correctamente con ID %s", carretera.getNombre(), carretera.getId());
            mensajes.add(mensaje);

        } catch (NumberFormatException nfe) {
            errores.add("Formato numérico incompatible");

            request.setAttribute("errores", errores);
            request.setAttribute("mensajes", mensajes);
            request.getRequestDispatcher(JSP_CREAR).forward(request, response);
        }
    }

}
