<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pedidos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <!-- formulario de búsqueda -->
            <form method="get" action="pedidos">
                <input type="hidden" name="op" value="buscar" />
                <div class="form-row align-items-center">
                    <div class="col-6">
                        <label class="sr-only" for="rut">Rut Empresa</label>
                        <input type="number" name="rut" id="rut" value="${fn:escapeXml(carreteraBuscado)}" class="form-control form-control-lg mb-2 mb-sm-0" placeholder="Ingrese el rut de la empresa a buscar">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-warning">Buscar</button>
                    </div>
                </div>
            </form>
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

            <c:if test="${empty pedidos}">
                Ingrese el rut de la empresa para buscar sus pedidos
            </c:if>            

            <c:if test="${!empty pedidos}">
                <!-- tabla con pedidos -->
                <table class="table table-bordered table-dark">
                    <thead class="thead-inverse">
                        <tr>
                            <th>Número</th>
                            <th>Pedido</th>
                            <th>Total</th>
                            <th>Accion</th>            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pedidos}" var="p">
                            <tr class="bg-info">

                                <th><dt>${p.id}</dt></th>                                
                        <td><dt>${p.carreteras}</dt></td>
                        <td><dt> $<fmt:formatNumber value="${p.totalF}"/></dt></td> 

                        <td>
                            <form method="get" action="pedidos">
                                <input type="hidden" name="op" value="comprar" />    
                                <input type="hidden" name="pedidoId" value="${p.id}">
                                <button type="submit" class="btn btn-warning">Comprar</button>
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