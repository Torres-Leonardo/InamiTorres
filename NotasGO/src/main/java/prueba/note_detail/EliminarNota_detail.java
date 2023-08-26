package prueba.note_detail;


import Maestros.Note_detail;

public class EliminarNota_detail {

	public static void main(String[] args) {
		try {
			// Datos
			String id = "10";
			// Proceso
			Note_detail studentService = new Note_detail();
			studentService.delete(id);
			System.out.println("Registro eliminado.");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
