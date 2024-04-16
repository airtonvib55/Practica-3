package com.mycompany.practica3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Inscripcion obEst = new Inscripcion();
        ArrayList<Inscripcion> listaIns = (ArrayList<Inscripcion>) sesion.getAttribute("li");

        int id, posicion;
        
        String opcion = request.getParameter("opcion");
        switch (opcion) {
            case "nuevo":
                request.setAttribute("objetoIns", obEst);
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                break;
            case "editar":
                id = Integer.parseInt(request.getParameter("id"));
                posicion = buscarObjeto(request, id);
                obEst = listaIns.get(posicion);
                
                request.setAttribute("objetoIns", obEst);
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                break;
                
            case "eliminar":
                id = Integer.parseInt(request.getParameter("id"));
                posicion = buscarObjeto(request, id);
                
                if(posicion >= 0){
                    listaIns.remove(posicion);
                }
                request.setAttribute("li", listaIns);
                response.sendRedirect("index.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Inscripcion obEst = new Inscripcion();
        HttpSession sesion = request.getSession();

        ArrayList<Inscripcion> arrayL = (ArrayList<Inscripcion>) sesion.getAttribute("li");

        int id = Integer.parseInt(request.getParameter("id"));
        String[] sem = request.getParameterValues("seminario");

        obEst.setId(id); //revisar
        obEst.setFecha(request.getParameter("fecha"));
        obEst.setNombre(request.getParameter("nombre"));
        obEst.setApellidos(request.getParameter("apellidos"));
        obEst.setTurno(request.getParameter("turno"));

        obEst.setSeminarios(sem);

        if (id == 0) {
            int idNuevo = obtenerId(request);
            obEst.setId(idNuevo);
            arrayL.add(obEst);
        } else {
            int pos = buscarObjeto(request, id);
            arrayL.set(pos, obEst);
        }

        request.setAttribute("li", arrayL);
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    public int obtenerId(HttpServletRequest request) {
        HttpSession sesion = request.getSession();

        ArrayList<Inscripcion> lista = (ArrayList<Inscripcion>) sesion.getAttribute("li");

        int idNum = 0;

        for (Inscripcion item : lista) {
            idNum = item.getId();
        }
        return idNum + 1;

    }

    public int buscarObjeto(HttpServletRequest request, int id) {
        HttpSession sesion = request.getSession();

        ArrayList<Inscripcion> lista = (ArrayList<Inscripcion>) sesion.getAttribute("li");
        int posicion = -1;

        if (lista != null) {

            for (Inscripcion item : lista) {
                posicion++;
                if (item.getId() == id) {
                    break;
                }
            }
        }
        return posicion;

    }
}
