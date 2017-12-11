package cl.duoc.dej.tienda.exception;

public class CarreteraNoEncontradoException extends Exception {

    public CarreteraNoEncontradoException() {
    }

    /**
     * Constructs an instance of <code>ProductoNoEncontradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CarreteraNoEncontradoException(String msg) {
        super(msg);
    }
}
