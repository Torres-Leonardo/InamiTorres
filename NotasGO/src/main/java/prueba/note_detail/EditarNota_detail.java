package prueba.note_detail;

import Maestros.Note_detail;

public class EditarNota_detail {

	public static void main(String[] args) {
		Note_detail note_detailService = new Note_detail(); // Reemplaza 'YourClassName' con el nombre de tu clase

	    // Datos de prueba
	    String studentName = "Ana Sanchez";
	    String competence = "2";
	    String note = "A";
	    

	    // Llamar al método para actualizar el registro
	    note_detailService.updateRecord(studentName, competence, note);
	}

}