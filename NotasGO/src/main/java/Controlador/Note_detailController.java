package Controlador;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Maestros.Note_detail;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CrearNota")
public class Note_detailController extends HttpServlet {

   
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Recupera los datos enviados por el formulario
        String studentName = request.getParameter("student_id");
        String competence_id = request.getParameter("competence_id");
        String note = request.getParameter("note");
        
     

        // Instanciar el controlador de la base de datos (suponiendo que se llama DatabaseController)
        Note_detail Note_detail = new Note_detail();

        try {
            // Crear el registro de nota en la base de datos utilizando el método createRecord
        	Note_detail.createRecord(studentName, competence_id, note);

            // Redirigir a la página de éxito (cambiar "ruta_a_tu_jsp_de_exito" a la ruta correcta)
            response.sendRedirect("MensajeExitoDetalle.jsp");
        } catch (SQLException e) {
            // Redirigir a la página de error (cambiar "ruta_a_tu_jsp_de_error" a la ruta correcta)
            response.sendRedirect("MensajeErrorDetalle.jsp");
        }
    }
}
