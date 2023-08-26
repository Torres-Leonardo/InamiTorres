package Controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Maestros.student;
import Modelos.ModeloStudent;

@WebServlet({ "/StudentBuscar", "/StudentProcesar", "/StudentActualizar", "/StudentHistorial" })
public class EstudianteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private student service = new student();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/StudentBuscar":
			buscar(request, response);
			break;
		case "/StudentProcesar":
			procesar(request, response);
			break;
		case "/StudentActualizar":
			actualizar(request, response);
			break;
		case "/StudentHistorial":
			historial(request, response);
			break;
		}
	}

	private void procesar(HttpServletRequest request, HttpServletResponse response) {
		// Datos
		String accion = request.getParameter("accion");
		ModeloStudent bean = new ModeloStudent();
		bean.setId(Integer.parseInt(request.getParameter("id")));
		bean.setNames(request.getParameter("names"));
		bean.setLast_name(request.getParameter("last_name"));
		bean.setType_document(request.getParameter("type_document"));
		bean.setNumber_document(request.getParameter("number_document"));
		bean.setEmail(request.getParameter("email"));
		bean.setCellphone(request.getParameter("cellphone"));
		bean.setGrade(request.getParameter("grade"));
		bean.setSection(request.getParameter("section"));
		// Proceso
		try {
			switch (accion) {
			case ControllerUtil.CRUD_NUEVO:
				service.insert(bean);
				break;
			case ControllerUtil.CRUD_EDITAR:
				service.update(bean);
				break;
			case ControllerUtil.CRUD_ELIMINAR:
				service.delete(bean.getId().toString());
				break;
			case ControllerUtil.CRUD_RESTAURAR:
				service.restore(bean.getId().toString());
				break;
			case ControllerUtil.CRUD_ELIMINATE:
				service.eliminate(bean.getId().toString());
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + accion);
			}
			ControllerUtil.responseJson(response, "Proceso ok.");
		} catch (Exception e) {
			ControllerUtil.responseJson(response, e.getMessage());
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Datos
		String names = request.getParameter("names");
		String last_name = request.getParameter("last_name");
		String number_document = request.getParameter("number_document");
		String grade = request.getParameter("grade");
		String section = request.getParameter("section");
		// Proceso
		ModeloStudent bean = new ModeloStudent();
		bean.setNames(names);
		bean.setLast_name(last_name);
		bean.setNumber_document(number_document);
		bean.setGrade(grade);
		bean.setSection(section);
		List<ModeloStudent> lista = service.get(bean);
		// Preparando el JSON
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		// Reporte
		ControllerUtil.responseJson(response, data);
	}

	private void actualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ModeloStudent> lista = service.getActive();
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
	}

	private void historial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ModeloStudent> lista = service.getInactive();
		Gson gson = new Gson();
		String data = gson.toJson(lista);
		ControllerUtil.responseJson(response, data);
	}

}
