package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Cliente implements Serializable {

    static final long serialVersionUID = 9L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String nombreEmpresa;    
      
    private int rut;        
    
    private String direccion; 
    
    private String nombreCliente;
    

    // constructores
    public Cliente() {
    }

    public Cliente(Long id, String nombreEmpresa, int rut, String direccion, String nombreCliente) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.rut = rut;
        this.direccion = direccion;
        this.nombreCliente = nombreCliente;
    }

   
    

    public Cliente(String nombreEmpresa, int rut, String direccion, String nombreCliente) {
        this.nombreEmpresa = nombreEmpresa;
        this.rut = rut;
       
        this.direccion = direccion;
        this.nombreCliente=nombreCliente ;
       
    }
    
    

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

   
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

   

    
}
