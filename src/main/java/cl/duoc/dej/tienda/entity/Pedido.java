package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
public class Pedido implements Serializable {

    static final long serialVersionUID = 4449879L;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ElementCollection
    
    @CollectionTable(name = "LINEAPEDIDO")
    private List<LineaPedido> lineasPedido = new ArrayList<>();
    @ManyToOne
    private Cliente cliente;
    
    @Column(nullable = false)
    private String medioPago;
    
    @Column(nullable = false)
    private String opcionRetiro;
    
    private String carreteras;
    private Long totalF;
    
    // Constructores
    public Pedido() {
    }

    // operaciones
    public void quitarCarretera(Long carreteraId) {
        List<LineaPedido> paraRemover = new ArrayList<>();
        for(LineaPedido lp:getLineasPedido()) {
            if(Objects.equals(lp.getCarretera().getId(), carreteraId)) {
                paraRemover.add(lp);
            }
        }
        getLineasPedido().removeAll(paraRemover);
    }

   

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getOpcionRetiro() {
        return opcionRetiro;
    }

    public void setOpcionRetiro(String opcionRetiro) {
        this.opcionRetiro = opcionRetiro;
    }

    //metodo para los pedidos anteriores
    public String getCarreterasNombres() {
        String nombres="";
        for (LineaPedido lp : lineasPedido) {
            nombres += "-"+lp.getCarretera().getNombre();
        }
        return nombres;
    }
    
   
    // cálculos
    public Long getTotalConIva() {
        return getTotal() + getIva();
    }
    
    public Long getIva() {
        return Math.round(0.19d * getTotal().doubleValue() );
    }
    
    public Long getTotal() {
        Long total = 0L;
        for (LineaPedido lp : lineasPedido) {
            total += lp.getPrecio() * lp.getCantidad();
        }
        return total;
    }

    public String getCarreteras() {
        return carreteras;
    }

    public void setCarreteras(String carreteras) {
        this.carreteras = carreteras;
    }

    public Long getTotalF() {
        return totalF;
    }

    public void setTotalF(Long totalF) {
        this.totalF = totalF;
    }

    


}
