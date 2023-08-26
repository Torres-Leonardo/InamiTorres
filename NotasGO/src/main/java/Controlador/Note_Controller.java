package Controlador;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Maestros.Note;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/registrarNota")
public class Note_Controller extends HttpServlet {

   
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Recupera los datos enviados por el formulario
		String teacherName = request.getParameter("teacherName");
        String courseName = request.getParameter("courseName");
        String studentName = request.getParameter("studentName");
        String noteDetail = request.getParameter("noteDetail");

        // Instanciar el controlador de la base de datos (suponiendo que se llama DatabaseController)
        Note Note = new Note();

        try {
            // Crear el registro de nota en la base de datos utilizando el método createRecord
        	Note.createRecord(teacherName, courseName, studentName, noteDetail);

            // Redirigir a la página de éxito (cambiar "ruta_a_tu_jsp_de_exito" a la ruta correcta)
            response.sendRedirect("MensajeExitoNota.jsp");
        } catch (SQLException e) {
            // Redirigir a la página de error (cambiar "ruta_a_tu_jsp_de_error" a la ruta correcta)
            response.sendRedirect("MensajeErrorNota.jsp");
        }
    }
}
