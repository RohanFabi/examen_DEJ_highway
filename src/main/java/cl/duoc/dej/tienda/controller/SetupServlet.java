package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Carretera;
import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.entity.Carretera;

import cl.duoc.dej.tienda.service.ClienteService;
import cl.duoc.dej.tienda.service.PedidoBuilder;
import cl.duoc.dej.tienda.service.PedidoService;
import cl.duoc.dej.tienda.service.CarreteraService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SetupServlet", urlPatterns = {"/setup"})
public class SetupServlet extends HttpServlet {

    @EJB
    CarreteraService carreteraService;

    @EJB
    ClienteService clienteService;

    @EJB
    PedidoService pedidoService;

    @EJB
    PedidoBuilder pedidoBuilder;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> mensajes = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        try {

            Calendar fechaNacimiento = Calendar.getInstance();
            fechaNacimiento.add(Calendar.YEAR, -20);
            Cliente cliente1 = new Cliente("Telesur", 12345678, "an Carlos 6654", "Juan perez");
            Cliente cliente2 = new Cliente("TeleNorte", 13245678, "San Carlos 6654", "Diego jerez");
            Cliente cliente3 = new Cliente("Telecentro", 212345678, "Concha y Toro 659", "Aldo Gatica");
            Cliente cliente4 = new Cliente("TeleAlMedio", 69874563, "Av Colombia 56324", "Marcela Sanchez");
            clienteService.crearCliente(cliente1);
            clienteService.crearCliente(cliente2);
            clienteService.crearCliente(cliente3);
            clienteService.crearCliente(cliente4);
            mensajes.add(String.format("Cliente %s creado con ID %s", cliente1.getNombreEmpresa(), cliente1.getId()));
            mensajes.add(String.format("Cliente %s creado con ID %s", cliente2.getNombreEmpresa(), cliente2.getId()));
            mensajes.add(String.format("Cliente %s creado con ID %s", cliente3.getNombreEmpresa(), cliente3.getId()));
            mensajes.add(String.format("Cliente %s creado con ID %s", cliente4.getNombreEmpresa(), cliente4.getId()));

            Carretera carreterita = new Carretera("Ruta Del Sol", 29990L);

            carreterita = carreteraService.crearCarretera(carreterita);
            Carretera RutaDelSol = carreterita;
            mensajes.add(String.format("Carretera %s creado con ID %s", carreterita.getNombre(), carreterita.getId()));

            carreterita = new Carretera("Ruta68", 39990L);
            carreterita = carreteraService.crearCarretera(carreterita);
            Carretera Ruta68 = carreterita;

            mensajes.add(String.format("Carretera %s creado con ID %s", carreterita.getNombre(), carreterita.getId()));

            carreterita = new Carretera("Ruta del Maipo", 27000L);
            carreterita = carreteraService.crearCarretera(carreterita);
            Carretera RutaDelMaipo = carreterita;

            mensajes.add(String.format("Carretera %s creado con ID %s", carreterita.getNombre(), carreterita.getId()));

            carreterita = new Carretera("Panamericana", 70000L);
            carreterita = carreteraService.crearCarretera(carreterita);
            Carretera Panamericana = carreterita;

            mensajes.add(String.format("Carretera %s creado con ID %s", carreterita.getNombre(), carreterita.getId()));

            Pedido pedido = pedidoBuilder.setCliente(cliente1.getId())
                    .agregarCarretera(RutaDelSol.getId(), 2)
                    .agregarCarretera(Ruta68.getId(), 1)
                    .build();
            pedido.setMedioPago("transferencia");
            pedido.setOpcionRetiro("oficina");
            pedido.setTotalF(pedido.getTotal());
            pedido.setCarreteras(pedido.getCarreterasNombres());
            pedidoService.crearPedido(pedido);

        } catch (Exception e) {
            errores.add(e.getMessage());
        }

        request.setAttribute("mensajes", mensajes);
        request.setAttribute("errores", errores);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
