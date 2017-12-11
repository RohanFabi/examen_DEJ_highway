<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Carrito de Compras</title>

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <style>
            fieldset {
                border: 1px solid #ccc;
                padding: 10px;
            }

            legend {
                display: inline;
                padding: 0 10px;
                width: auto;
            }
        </style>
    </head>
    <body>
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

            <c:if test="${empty pedido.lineasPedido}">
                No hay peajes en el carrito.
            </c:if>            

            <c:if test="${!empty pedido.lineasPedido}">
                <h1>Carrito de Compras</h1>

                <form method="get" action="carrito">
                    <fieldset>
                        <legend>Datos del Cliente</legend>

                        <input type="hidden" name="id" value="0" />
                        <div class="form-row">
                            <div class="form-group col-md-5">
                                <label>Nombre Empresa</label>
                                <input type="text" name="nombreEmpresa" id="nombreEmpresa" class="form-control" />    
                            </div>
                            <div class="form-group col-md-2">
                                <label>RUT</label>
                                <input type="text" id="rut" name="rut" class="form-control" />    
                            </div>

                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-3">
                                <label>Dirección</label>
                                <input type="text" name="direccion" id="direccion" class="form-control" />    
                            </div>
                            <div class="form-group col-md-5">
                                <label>Nombre Comprador</label>
                                <input type="text" name="nombreCliente" id="nombreCliente" class="form-control" />    
                            </div>

                        </div>
                        <table>
                            <tr>
                                <th>Medio de Pago</th>
                                <th><input type="radio" name="medioPago" value="transferencia" checked="true">Transferencia<br>
                                    <input type="radio" name="medioPago" value="pagoEnLinea">Pago en Linea<br>
                                    <input type="radio" name="medioPago" value="ordenDeCompra">Orden de Compra<br>
                                </th>
                            </tr>
                            <tr>
                                <th>Opcion de Retiro</th>
                                <th><input type="radio" name="opcionRetiro" value="oficina" checked="true">Oficina<br>
                                    <input type="radio" name="opcionRetiro" value="envio">Envio<br>
                                </th>
                            </tr>
                        </table>
                        <div class="form-row">
                            <div class="form-group">
                                <button type="submit" class="btn btn-success">Comprar</button>
                            </div>
                        </div>

                    </fieldset>

                    <input type="hidden" name="op" value="comprar" />

                </form>

                <!-- tabla con carreteras -->
                TOTAL: $<fmt:formatNumber value="${pedido.total}" />
                <table class="table table-striped">
                    <thead class="thead-inverse">
                        <tr>
                            <th>Carretera</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Subtotal</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pedido.lineasPedido}" var="lp">
                            <tr>

                                <td>${lp.carretera.nombre}</td>
                                <td>
                                    $<fmt:formatNumber value="${lp.precio}" />
                                </td>
                                <td>${lp.cantidad}</td>
                                <td>
                                    $<fmt:formatNumber value="${lp.subtotal}" />
                                </td>                                
                                <td>
                                    <form method="get" action="carrito" class="form-inline">
                                        <input type="hidden" name="op" value="quitar" />
                                        <input type="hidden" name="carreteraId" value="${lp.carretera.id}" />
                                        <button type="submit" class="btn btn-primary">Quitar</button>
                                    </form>                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />

        <script type="text/javascript">
            jQuery(function () {
                jQuery("#rut").blur(function () {
                    var url = "${pageContext.request.contextPath}/clientes";
                    var d = {op: "ws", rut: jQuery(this).val()};
                    jQuery.ajax({
                        type: "GET"
                        , url: url
                        , data: d
                        , dataType: "json"
                    }).done(function (res) {
                        console.log("procesando respuesta JSON");
                        jQuery("#nombreEmpresa").val(res.nombreEmpresa);
                        jQuery("#direccion").val(res.direccion);
                        jQuery("#nombreCliente").val(res.nombreCliente);
                    }).fail(function (jqXHR, textStatus, errorThrown) {
                        console.log("AJAX call failed: " + textStatus + ", " + errorThrown);
                    });
                });
            });

        </script>
    </body>
</html>