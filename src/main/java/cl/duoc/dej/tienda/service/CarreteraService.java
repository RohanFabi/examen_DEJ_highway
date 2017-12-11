package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Carretera;
import cl.duoc.dej.tienda.exception.CarreteraNoEncontradoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CarreteraService {

    static final long serialVersionUID = 11L;
    
    @PersistenceContext
    EntityManager em;

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public CarreteraService() {
    }

    public Carretera crearCarretera(Carretera carretera) {
        em.persist(carretera);
        return carretera;
    }

    public List<Carretera> getCarreteras() {
        TypedQuery<Carretera> query = em.createQuery("SELECT p FROM Carretera p", Carretera.class);
        return query.getResultList();
    }

    /**
     *
     * @param id
     * @return retorna el carretera o nulo en caso de no ser encontrado
     */
    public Carretera getCarreteraById(Long id) {
        return em.find(Carretera.class, id);
    }

    public List<Carretera> buscarCarretera(String nombreCarretera) {
        if (nombreCarretera != null && !nombreCarretera.isEmpty() ) {
            String jpql = "SELECT p FROM Carretera p WHERE LOWER(p.nombre) LIKE :nombre ";
            TypedQuery<Carretera> query = em.createQuery(jpql, Carretera.class);
            query.setParameter("nombre", "%" + nombreCarretera + "%");
           
            return query.getResultList();
        }

        if (nombreCarretera != null && !nombreCarretera.isEmpty()) {
            String jpql = "SELECT p FROM Carretera p WHERE LOWER(p.nombre) LIKE :nombre";
            TypedQuery<Carretera> query = em.createQuery(jpql, Carretera.class);
            query.setParameter("nombre", "%" + nombreCarretera + "%");
            return query.getResultList();
        }

       

        // sino devuelve la lista completa de carreteras
        return getCarreteras();
    }

    public void eliminarCarretera(Long carreteraId) throws CarreteraNoEncontradoException {
        Carretera p = getCarreteraById(carreteraId);
        if (p == null) {
            String mensajeException = String.format("Carretera con ID %s no encontrado para ser eliminado", carreteraId);
            logger.log(Level.SEVERE, mensajeException);
            throw new CarreteraNoEncontradoException(mensajeException);
        }
        em.remove(p);
    }

}
