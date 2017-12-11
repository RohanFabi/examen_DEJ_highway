<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Creación y Edición de Clientes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />

    </head>
    <body>      

        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />
            <div class="row">
                <div class="col col-lg-6">
                    <h1>Crear Clientes</h1>
                    <form method="post" action="clientes">
                        <fieldset>
                            <div class="form-group">
                                <label for="nombreEmpresa">Nombre Empresa</label>
                                <input value="${!empty cliente?cliente.nombreEmpresa:''}" type="text" class="form-control" id="nombreEmpresa" name="nombreEmpresa" placeholder="Ingrese el nombre de la empresa" aria-describedby="descripcion-help">

                            </div>
                            <div class="form-group">
                                <label for="rut">Rut</label>
                                <input value="${!empty consola.rut}" type="number" class="form-control" id="rut" name="rut"  placeholder="Ingrese rut de la empresa" aria-describedby="id-help">
                                <small id="id-help" class="form-text text-muted">Ejemplo: 123456789</small>
                            </div>

                            <div class="form-group">
                                <label for="direccion">Direccion</label>
                                <input value="${!empty cliente?cliente.direccion:''}" type="text" class="form-control" id="direccion" name="direccion" placeholder="Ingrese direccion" aria-describedby="descripcion-help">

                            </div>
                            <div class="form-group">
                                <label for="nombreCliente">Nombre Comprador</label>
                                <input value="${!empty cliente?cliente.nombreCliente:''}" type="text" class="form-control" id="nombreCliente" name="nombreCliente" placeholder="Ingrese el nombre del comprador" aria-describedby="descripcion-help">

                            </div>  

                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </fieldset>

                    </form>
                </div>
            </div><!-- end col-->
        </div><!-- end row-->

    </form>    
    <jsp:include page="/WEB-INF/jspf/footer.jsp" />

</body>
</html>