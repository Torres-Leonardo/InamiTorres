package prueba.note;


import Maestros.Note;


public class EliminarNote {
    public static void main(String[] args) {
    	Note note = new Note();

    	// ID de la nota a eliminar
    	int noteId = 14;

    	note.deleteRecord(noteId);
    	System.out.println("Nota con el ID " + noteId + ", eliminada exitosamente.");

    	}

}

