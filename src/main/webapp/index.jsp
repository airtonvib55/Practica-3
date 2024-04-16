<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.practica3.Inscripcion"%>
<%
    if (session.getAttribute("li") == null) {
        ArrayList<Inscripcion> arrayLista = new ArrayList<Inscripcion>();
        session.setAttribute("li", arrayLista);
    }
    ArrayList<Inscripcion> lista = (ArrayList<Inscripcion>) session.getAttribute("li");

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de inscritos</h1>
        <a href="MainServlet?opcion=nuevo">Nuevo</a>
        <table border="2px">
            <tr>
                <th>Id</th>
                <th>Fecha</th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Turno</th>
                <th>Seminarios</th>
                <th></th>
                <th></th>
            </tr>

            <%                for (Inscripcion item : lista) {

            %>
            <tr>
                <td><%= item.getId()%></td>
                <td><%= item.getFecha()%></td>
                <td><%= item.getNombre()%></td>
                <td><%= item.getApellidos()%></td>
                <td><%= item.getTurno()%></td>
                <td><% boolean primero = true;
                    for (String is : item.getSeminarios()) {
                        if (!primero) {
                            %>
                            <%=";"%>
                            <%
                        } else {
                            primero = false;
                        }
                        %>
                        <%=is%>
                        <%
                    }
                    %></td>
                <td><a href="MainServlet?opcion=editar&id=<%= item.getId()%>">Editar</a></td>
                <td><a href="MainServlet?opcion=eliminar&id=<%= item.getId()%>" onclick="return(confirm('Seguro de Eliminar'))">Eliminar</a></td>
            </tr>
            <%
                }%>

        </table>

    </body>
</html>
