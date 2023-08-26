package prueba.note;

import java.util.List;

import Modelos.ModeloNote;
import Maestros.Note;

public class ListarPorNombreEstudiante {

    public static void main(String[] args) {
        String studentName = "Maria Torres"; // Nombre del estudiante que deseas buscar

        Note crudNote = new Note();
        List<ModeloNote> notesByStudent = crudNote.listByStudentName(studentName);

        if (notesByStudent.isEmpty()) {
            System.out.println("No se encontraron el estudiante: " + studentName);
        } else {
            for (ModeloNote note : notesByStudent) {
                System.out.println(note.toString());
            }
        }
    }
}
