package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.entity.LineaPedido;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.entity.Carretera;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PedidoBuilder implements Serializable {

    static final long serialVersionUID = 52L;
    
    Pedido pedido;
    
    @EJB
    CarreteraService carreteraService;
    @EJB
    ClienteService clienteService;
    
    
    public PedidoBuilder() {
        pedido = new Pedido();
    }
    
    public PedidoBuilder setCliente(Long clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        pedido.setCliente(cliente);
        return this;
    }
    
    
    
    public PedidoBuilder agregarCarretera(Long carreteraId, int cantidad) {
        Carretera carretera = carreteraService.getCarreteraById(carreteraId);
        LineaPedido lineaPedido = new LineaPedido(carretera, cantidad, carretera.getPrecio());
        pedido.getLineasPedido().add(lineaPedido);
        return this;
    }

    public Pedido build() {
        Pedido p = pedido;
        pedido = new Pedido();
        return p;
    }

    
    
    
}
