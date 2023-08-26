package prueba.note;

import java.util.List;
import Modelos.ModeloNote;
import Maestros.Note;

public class ListarTodo {

    public static void main(String[] args) {
    	Note Note = new Note();
    	List<ModeloNote> allNotes = Note.listAllRecords();

    	for (ModeloNote note : allNotes) {
    	    System.out.println(note.toString());
    	}
    }
}
