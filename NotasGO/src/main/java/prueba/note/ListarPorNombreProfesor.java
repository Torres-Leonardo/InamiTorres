package prueba.note;

import java.util.List;

import Modelos.ModeloNote;
import Maestros.Note;

public class ListarPorNombreProfesor {

    public static void main(String[] args) {
        String usersName = "Carmen Armas"; // Nombre del profesor que deseas buscar

        Note Note = new Note();
        List<ModeloNote> notesByTeacher = Note.listByUsersName(usersName);

        if (notesByTeacher.isEmpty()) {
            System.out.println("No se encontraron notas para el profesor: " + usersName);
        } else {
            for (ModeloNote note : notesByTeacher) {
                System.out.println(note.toString());
            }
        }
    }
}
