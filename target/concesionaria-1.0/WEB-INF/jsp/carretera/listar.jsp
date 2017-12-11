<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Carreteras</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <!-- formulario de búsqueda -->
            <form method="get" action="catalogo">
                <input type="hidden" name="op" value="buscar" />
                <div class="form-row align-items-center">
                    <div class="col-6">
                        <label class="sr-only" for="carretera">Carretera</label>
                        <input type="text" name="carretera" id="carretera" value="${fn:escapeXml(carreteraBuscado)}" class="form-control form-control-lg mb-2 mb-sm-0" placeholder="Ingrese el nombre del carretera a buscar">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-warning">Buscar</button>
                    </div>
                </div>
            </form>
            <!-- END formulario de búsqueda -->


            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

           

            <c:if test="${empty carreteras}">
                No hay carreteras para mostrar.
            </c:if>            

            <c:if test="${!empty carreteras}">
                <!-- tabla con carreteras -->

                <table class="table table-bordered table-dark">

                    <thead class="thead-inverse">
                        <tr>

                            <th scope="col">ID</th>
                            <th scope="col">Carretera</th>
                            <th scope="col">Precio</th>
                            <th scope="col">Acciones</th>      
                        </tr>
                    </thead>             


                    <tbody>
                        <c:forEach items="${carreteras}" var="p">
                            <tr class="bg-info">
                                <th>${p.id}</th>
                                <td><dt>${p.nombre}<dt></td>
                        <td><dt>
                            $<fmt:formatNumber value="${p.precio}" />
                        </dt></td>
                        <td>
                            <!-- agregar al carro -->
                            <form method="post" action="carrito" class="form-inline">
                                <div class="form-group">
                                    <input name="cantidad" value="1" type="number" min="1" max="10" />
                                    <input type="hidden" name="carreteraId" value="${p.id}" />
                                </div>
                                <button type="submit" class="btn btn-primary">Agregar</button>
                            </form>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>