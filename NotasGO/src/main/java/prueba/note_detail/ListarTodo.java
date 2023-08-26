package prueba.note_detail;

import java.util.List;

import Modelos.ModeloNote_detail;
import Maestros.Note_detail;

public class ListarTodo {
	
	public static void main(String[] args) {
		try {
			Note_detail note_detailService = new Note_detail();
			List<ModeloNote_detail> lista = note_detailService.getAll();
			for (ModeloNote_detail note_detail : lista) {
				System.out.println(note_detail);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
