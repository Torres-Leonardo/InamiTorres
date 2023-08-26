package prueba.note_detail;

import Modelos.ModeloNote_detail;
import Maestros.Note_detail;

import java.util.List;

public class BuscarEstudiante {

    public static void main(String[] args) {
        String studentName = "Ana Sanchez"; // Nombre del estudiante que deseas buscar

        Note_detail note_detail = new Note_detail();
        List<ModeloNote_detail> notesByStudent = note_detail.buscarStudent(studentName);

        if (notesByStudent.isEmpty()) {
            System.out.println("No se encontraron notas para el estudiante: " + studentName);
        } else {
            for (ModeloNote_detail note : notesByStudent) {
                System.out.println(note.toString());
            }
        }
    }
}
