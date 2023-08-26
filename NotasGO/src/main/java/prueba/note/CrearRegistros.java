package prueba.note;

import java.sql.SQLException;

import Maestros.Note;

public class CrearRegistros {
    public static void main(String[] args) {
        // Crear una instancia de tu clase que maneja las operaciones de la base de datos
    	Note noteService = new Note();

        // Datos de ejemplo
        String usersName = "Juana Lopez";
        String courseName = "Castellano como segunda lengua";
        String studentName = "Ana Sanchez";
        String noteDetail = "AD";

        try {
	        // Ejecutar la creación de registro
	        noteService.createRecord(usersName, courseName, studentName, noteDetail);
	        // Si llega a esta línea, significa que no se lanzó una excepción y la inserción fue exitosa.
	        System.out.println(":)");
	    } catch (SQLException e) {
	        // Si ocurre una excepción, muestra el mensaje de error en la consola.
	        System.err.println("Error en la creación del registro: " + e.getMessage());
	    }
	 }
}