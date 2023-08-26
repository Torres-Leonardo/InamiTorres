package Maestros;

public class UtilService {

	private UtilService() {
	}

	public static String setStringVacio(String dato) {
		if(dato == null) {
			dato = "";
		}
		dato = dato.trim(); // Para pensarlo
		return dato;
	}

	///agregue esto xd
	public static int setIntegerVacio(Integer dato) {
	    if (dato == null) {
	        return 0;
	    } else {
	        return dato;
	    }
	}

}
