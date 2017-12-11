package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class LineaPedido implements Serializable {

    static final long serialVersionUID = 564654L;
    
    @ManyToOne
    Carretera carretera;
    @Column(nullable = false)
    int cantidad;
    @Column(nullable = false)
    Long precio;

    // Constructores
    public LineaPedido() {
    }

    public LineaPedido(Carretera carretera, int cantidad) {
        this.carretera = carretera;
        this.cantidad = cantidad;
        this.precio = carretera.getPrecio();
    }
    
    public LineaPedido(Carretera carretera, int cantidad, Long precio) {
        this.carretera = carretera;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    // Cálculos
    public Long getSubtotal() {
        return cantidad * precio;
    }
    
    
    // getters y setters
    public Carretera getCarretera() {
        return carretera;
    }

    public void setCarretera(Carretera carretera) {
        this.carretera = carretera;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

}
