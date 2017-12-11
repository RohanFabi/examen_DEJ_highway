package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.exception.ClienteNoEncontradoException;
import cl.duoc.dej.tienda.service.ClienteService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/clientes"})
public class ClienteServlet extends HttpServlet {

    @EJB
    ClienteService clienteService;

    public final String JSP_LISTAR = "/WEB-INF/jsp/cliente/listar.jsp";
    public final String JSP_WS = "/WEB-INF/jsp/cliente/ws.jsp";
    public final String JSP_CREAR = "/WEB-INF/jsp/cliente/crear.jsp";

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";

        switch (operacion) {
            case "iniciar":
                iniciar(request, response);
                break;
            case "crear":
                crear(request, response);
                break;
            case "ws":
                ws(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }

    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = clienteService.getClientes();
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher(JSP_LISTAR).forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("id");
        if (stringId == null || stringId.isEmpty()) {
            postCrear(request, response);
        } else {
            postEditar(request, response);
        }
    }

    private void postCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int rut = 0;

        String nombreEmpresa = request.getParameter("nombreEmpresa");
        if (nombreEmpresa.length() == 0) {
            request.setAttribute("mensajes", "error nombreEmpresa ");
            request.getRequestDispatcher(JSP_CREAR).forward(request, response);
            return;
        }

        String nombreCliente = request.getParameter("nombreCliente");
        if (nombreCliente.length() == 0) {
            request.setAttribute("mensajes", "error nombreCliente ");
            request.getRequestDispatcher(JSP_CREAR).forward(request, response);
            return;
        }

        String direccion = request.getParameter("direccion");
        if (direccion.length() == 0) {
            request.setAttribute("mensajes", "error direccion ");
            request.getRequestDispatcher(JSP_CREAR).forward(request, response);
            return;
        }

        String strrut = request.getParameter("rut");
        if (strrut.length() < 0 && strrut.length() > 10) {
            request.setAttribute("mensajes", "error en rut");
            request.getRequestDispatcher(JSP_CREAR).forward(request, response);
            return;
        } else {
            rut = Integer.parseInt(strrut);
        }
        Cliente cl = new Cliente(nombreEmpresa, rut, direccion, nombreCliente);
        clienteService.crearCliente(cl);

        request.setAttribute("mensajes", "cliente " + cl.getNombreEmpresa() + " creado con id " + cl.getId());
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);

    }

    private void postEditar(HttpServletRequest request, HttpServletResponse response) {

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long clienteId = 0L;
        try {
            clienteId = Long.parseLong(stringId);
            clienteService.eliminarCliente(clienteId);
            mensaje = String.format("Se ha eliminado correctamente el cliente con ID %s", clienteId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (ClienteNoEncontradoException ex) {
            error = String.format("No se pudo encontrar el cliente con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void ws(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringRut = request.getParameter("rut");
        int rut = 0;
        Cliente cliente = null;
        try {
            rut = Integer.parseInt(stringRut);
            cliente = clienteService.getClienteByRut(rut);
        } catch (NumberFormatException nfe) {
            logger.log(Level.SEVERE, "Formato del RUT incorrecto");
        }
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher(JSP_WS).forward(request, response);
    }

    private void iniciar(HttpServletRequest request, HttpServletResponse response) {
        List<Cliente> clientes = clienteService.getClientes();
        request.setAttribute("clientes", clientes);
    }

}
