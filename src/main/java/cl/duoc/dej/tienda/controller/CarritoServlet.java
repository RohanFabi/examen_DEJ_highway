package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.entity.LineaPedido;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.entity.Carretera;

import cl.duoc.dej.tienda.exception.CarreteraNoEncontradoException;
import cl.duoc.dej.tienda.service.ClienteService;
import cl.duoc.dej.tienda.service.PedidoBuilder;
import cl.duoc.dej.tienda.service.PedidoService;
import cl.duoc.dej.tienda.service.CarreteraService;
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
import javax.servlet.http.HttpSession;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/carrito"})
public class CarritoServlet extends HttpServlet {

    @EJB
    PedidoBuilder pedidoBuilder;
    @EJB
    PedidoService pedidoService;
    @EJB
    ClienteService clienteService;
    @EJB
    CarreteraService carreteraService;

    private final static String JSP_CARRITO = "/WEB-INF/jsp/carrito/carrito.jsp";
    private final static String JSP_COMPROBANTE = "/WEB-INF/jsp/carrito/comprobante.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");

        HttpSession session = request.getSession();
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        Long carreteraId = 0L;

        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        if ("quitar".equals(operacion)) {
            String stringCarreteraId = request.getParameter("carreteraId");
            try {
                carreteraId = Long.parseLong(stringCarreteraId);
                pedido.quitarCarretera(carreteraId);
                mensaje = String.format("Se quitó correctamente  la carretera con ID %s del carrito", carreteraId);
                mensajes.add(mensaje);
                logger.log(Level.INFO, mensaje);
            } catch (NumberFormatException nfe) {
                error = "ID de carretera mal formateado, no se pudo quitar el carretera del carrito";
                logger.log(Level.SEVERE, error);
                errores.add(error);
            }
        }

        request.setAttribute("errores", errores);
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("pedido", pedido);
        if ("comprar".equals(operacion)) {

            Cliente cliente = buildCliente(request, response);
            if (cliente == null) {
                mensajes.add("Complete todos los campos ");
                request.getRequestDispatcher(JSP_CARRITO).forward(request, response);

                return;
            }

            String pago=request.getParameter("medioPago");
            String retiro=request.getParameter("opcionRetiro");
            
            pedido.setMedioPago(pago);
            pedido.setOpcionRetiro(retiro);
            pedido.setCarreteras(pedido.getCarreterasNombres());
            pedido.setTotalF(pedido.getTotal());
            pedido.setCliente(cliente);
            pedido = pedidoService.crearPedido(pedido);
            request.setAttribute("pedido", pedido);
            session.invalidate();
            request.getRequestDispatcher(JSP_COMPROBANTE).forward(request, response);
        } else {
            request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringCantidad = request.getParameter("cantidad");
        String stringCarreteraId = request.getParameter("carreteraId");
        Carretera carretera = null;
        int cantidad = 1;

        try {
            Long carreteraId = Long.parseLong(stringCarreteraId);
            carretera = carreteraService.getCarreteraById(carreteraId);
            if (carretera == null) {
                throw new CarreteraNoEncontradoException();
            }
        } catch (NumberFormatException nfe) {
            error = "Carretera ID mal formateado";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (CarreteraNoEncontradoException pnee) {
            error = "Carretera no encontrado";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        try {
            cantidad = Integer.parseInt(stringCantidad);
        } catch (NumberFormatException nfe) {
            cantidad = 1;
        }

        HttpSession session = request.getSession();
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if (pedido == null) {
            pedido = pedidoBuilder.agregarCarretera(carretera.getId(), cantidad).build();
        } else {
            pedido.getLineasPedido().add(new LineaPedido(carretera, cantidad));
        }
        session.setAttribute("pedido", pedido);

        request.setAttribute("pedido", pedido);
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("errores", errores);
        request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
    }

    private Cliente buildCliente(HttpServletRequest request, HttpServletResponse response) {

        String stringRut = request.getParameter("rut");

        String nombreEmpresa = request.getParameter("nombreEmpresa");

        String direccion = request.getParameter("direccion");
        String nombreCliente = request.getParameter("nombreCliente");

        // conversiones
        int rut = Integer.parseInt(stringRut);

        // creación de cliente
        Cliente cliente = clienteService.getClienteByRut(rut);
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setRut(rut);

            cliente.setNombreEmpresa(nombreEmpresa);

            cliente.setDireccion(direccion);
            cliente.setNombreCliente(nombreCliente);
            cliente = clienteService.crearCliente(cliente);
        }

        return cliente;
    }

}
