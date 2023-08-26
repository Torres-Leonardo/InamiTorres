package Maestros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import AccesoDB.AccesoDB;
import Modelos.ModeloNote;

public class Note {
	// Método para obtener una conexión a la base de datos
	private Connection getConnection() throws SQLException {
		return AccesoDB.getConnection();
	}

	// Listar todos los registros
	public List<ModeloNote> listAllRecords() {
		List<ModeloNote> notes = new ArrayList<>();

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM note_list");

			while (resultSet.next()) {
				int id = resultSet.getInt("Id");
				String usersId = resultSet.getString("Usuarios");
				String courseId = resultSet.getString("Cursos");
				String studentId = resultSet.getString("Estudiantes");
				String note = resultSet.getString("Nota");

				ModeloNote noteObj = new ModeloNote();
				noteObj.setId(id);
				noteObj.setUsers_id(usersId);
				noteObj.setCourse_id(courseId);
				noteObj.setStudent_id(studentId);
				noteObj.setNote_detail_id(note);

				notes.add(noteObj);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notes;
	}

	// Listar registros por nombre de usuario
	public List<ModeloNote> listByUsersName(String UsersName) {
		List<ModeloNote> notesByUsers = new ArrayList<>();

		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM note_list WHERE Usuarios LIKE ?");
			statement.setString(1, UsersName);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("Id");
				String usersId = resultSet.getString("Usuarios");
				String courseId = resultSet.getString("Cursos");
				String studentId = resultSet.getString("Estudiantes");
				String note = resultSet.getString("Nota");

				ModeloNote noteObj = new ModeloNote();
				noteObj.setId(id);
				noteObj.setUsers_id(usersId);
				noteObj.setCourse_id(courseId);
				noteObj.setStudent_id(studentId);
				noteObj.setNote_detail_id(note);

				notesByUsers.add(noteObj);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notesByUsers;
	}

	// Listar registros por nombre de estudiante
	public List<ModeloNote> listByStudentName(String studentName) {
		List<ModeloNote> notesByStudent = new ArrayList<>();

		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM note_list WHERE Estudiantes = ?");
			statement.setString(1, studentName);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("Id");
				String usersId = resultSet.getString("Usuarios");
				String courseId = resultSet.getString("Cursos");
				String studentId = resultSet.getString("Estudiantes");
				String note = resultSet.getString("Nota");

				ModeloNote noteObj = new ModeloNote();
				noteObj.setId(id);
				noteObj.setUsers_id(usersId);
				noteObj.setCourse_id(courseId);
				noteObj.setStudent_id(studentId);
				noteObj.setNote_detail_id(note);

				notesByStudent.add(noteObj);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notesByStudent;
	}

	// Crear un nuevo registro en la tabla 'note' utilizando los registros existentes en las tablas relacionadas
	public void createRecord(String usersName, String courseName, String studentName, String noteDetail) 
    		throws SQLException{
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();

            // Obtener el ID del profesor por su nombre
            int usersId = getUsersIdByName(usersName);

            // Obtener el ID del curso por su nombre
            int courseId = getCourseIdByName(courseName);

            // Obtener el ID del estudiante por su nombre
            int studentId = getStudentIdByName(studentName);

         // Obtener el ID del detalle de nota por su descripción
            int noteDetailId = getNoteDetailIdByDescription(noteDetail);
           

            if (usersId == -1 || courseId == -1 || studentId == -1 || noteDetailId == -1  ) {
                System.out.println("No se pudo crear el registro. Verifica que los nombres proporcionados existan en las tablas relacionadas.");
                return;
            }

            // Crear la sentencia SQL para insertar el nuevo registro en la tabla 'note'
            String sql = "INSERT INTO note (users_id, course_id, student_id, note_detail_id) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, usersId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setInt(3, studentId);
            preparedStatement.setInt(4, noteDetailId);
            
        
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("El registro se ha creado exitosamente.");
            } else {
                System.out.println("No se pudo crear el registro.");
            }

        } finally {
            // Cerramos los recursos de la base de datos en el bloque finally
            // para asegurarnos de que se cierren en caso de excepción o no.
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } 
	}
        
        
     

	// Obtener el ID del usua por su nombre
	private int getUsersIdByName(String UsersName) {
		int usersId = -1; // Valor predeterminado si no se encuentra el profesor

		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT id FROM Users WHERE CONCAT(names, ' ', last_name) = ?");
			statement.setString(1, UsersName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				usersId = resultSet.getInt("id");
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersId;
	}

	// Obtener el ID del curso por su nombre
	private int getCourseIdByName(String courseName) {
		int courseId = -1; // Valor predeterminado si no se encuentra el curso

		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM course WHERE course_name = ?");
			statement.setString(1, courseName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				courseId = resultSet.getInt("id");
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return courseId;
	}

	// Obtener el ID del estudiante por su nombre
	private int getStudentIdByName(String studentName) {
		int studentId = -1; // Valor predeterminado si no se encuentra el estudiante

		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT id FROM student WHERE CONCAT(names, ' ', last_name) = ?");
			statement.setString(1, studentName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				studentId = resultSet.getInt("id");
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentId;
	}
	
	// Obtener el ID del detalle de nota por su descripción
    private int getNoteDetailIdByDescription(String noteDetail) {
        int noteDetailId = -1; // Valor predeterminado si no se encuentra el detalle de nota

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM note_detail WHERE note = ?");
            statement.setString(1, noteDetail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                noteDetailId = resultSet.getInt("id");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noteDetailId;
    }
	
	
	


	// Eliminar un registro
	public void deleteRecord(int noteId) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM note WHERE id = ?");

			preparedStatement.setInt(1, noteId);

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("El registro se ha eliminado exitosamente.");
			} else {
				System.out.println("No se encontró el registro con el ID especificado.");
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
