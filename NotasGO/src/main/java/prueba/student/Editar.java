package prueba.student;

import Maestros.student;
import Modelos.ModeloStudent;

public class Editar {

	public static void main(String[] args) {
		try {
			ModeloStudent bean = new ModeloStudent(26, "jose","Cuadros","DNI","70000002","mcuadros@gmail.com","900000002", "1", "B");
			student student = new student();
			student.update(bean);
			System.out.println(bean);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
