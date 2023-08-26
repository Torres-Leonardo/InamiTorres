package prueba.note_detail;
import Maestros.Note_detail;
import java.sql.SQLException;

public class CrearNota_detail {

	public static void main(String[] args) {
	    // Crear una instancia del objeto que contiene el método createRecord
		Note_detail note_detailService = new Note_detail();

	    // Valores para el nuevo registro
	    String studentName = "Carmen Condori";
	    String competence_id = "3";
	    String note = "A";

	    try {
	        // Ejecutar la creación de registro
	        note_detailService.createRecord(studentName, competence_id, note);
	        // Si llega a esta línea, significa que no se lanzó una excepción y la inserción fue exitosa.
	        System.out.println(":)");
	    } catch (SQLException e) {
	        // Si ocurre una excepción, muestra el mensaje de error en la consola.
	        System.err.println("Error en la creación del registro: " + e.getMessage());
	    }
	 }
}
