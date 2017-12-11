package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Carretera;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.service.PedidoService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/pedidos"})
public class PedidoServlet extends HttpServlet {

    public static final String JSP_LISTAR = "/WEB-INF/jsp/pedido/listar.jsp";
    private final static String JSP_COMPROBANTE = "/WEB-INF/jsp/carrito/comprobante.jsp";

    @EJB
    PedidoService pedidoService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";
        switch (operacion) {

            case "comprar":
                comprar(request, response);
            case "buscar":
                buscar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response, List<Pedido> pedidos) throws ServletException, IOException {
        request.setAttribute("pedidos", pedidos);
        request.getRequestDispatcher(JSP_LISTAR).forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pedido> pedidos = null;
        //pedidoService.getPedidos();
        listar(request, response, pedidos);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clienteBuscado = Integer.parseInt(request.getParameter("rut"));

        List<Pedido> pedidos = pedidoService.getPedidosByRut(clienteBuscado);
        listar(request, response, pedidos);
    }

    private void comprar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";
        
        String stringId = request.getParameter("pedidoId");

        Long id = 0L;
        try {
            id = Long.parseLong(stringId);
            Pedido p = pedidoService.getPedidoById(id);
            p.setId(null);
            pedidoService.crearPedido(p);
            mensaje = String.format("Pedido %s creada correctamente con ID %s",p.getCarreterasNombres(), p.getId());
            mensajes.add(mensaje);

            session.invalidate();
             
        } catch (NumberFormatException nfe) {
            errores.add("Formato numérico incompatible");

            request.setAttribute("errores", errores);
            request.setAttribute("mensajes", mensajes);
            
        }

        request.getRequestDispatcher(JSP_COMPROBANTE).forward(request, response);
    }

}
