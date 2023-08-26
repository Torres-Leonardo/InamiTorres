package Maestros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import AccesoDB.AccesoDB;
import MaestrosSpec.CrudServiceSpec;
import MaestrosSpec.RowMapper;
import Modelos.ModeloNote_detail;

public class Note_detail implements CrudServiceSpec<ModeloNote_detail>, RowMapper<ModeloNote_detail> {

	private final String SQL_SELECT_BASE = "SELECT * FROM list_note_detail";
	private final String SQL_DELETE = "DELETE FROM note_detail WHERE id=?";
	
	  // Método para obtener una conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return AccesoDB.getConnection();
    }

    public List<ModeloNote_detail> getAll() {
      	 List<ModeloNote_detail> notes = new ArrayList<>();

           try {
               Connection connection = getConnection();
               Statement statement = connection.createStatement();
               ResultSet resultSet = statement.executeQuery(SQL_SELECT_BASE);

               while (resultSet.next()) {
                   int id = resultSet.getInt("Id");
                   String studentId = resultSet.getString("Estudiantes");
                   String competence_id = resultSet.getString("Competencia");
                   String note = resultSet.getString("Nota");
                   String dateNote = resultSet.getString("Fecha de registro");

                   ModeloNote_detail noteObj = new ModeloNote_detail();
                   noteObj.setId(id);
                   noteObj.setStudent_id(studentId);
                   noteObj.setCompetence_id(competence_id);
                   noteObj.setNote(note);
                   noteObj.setDate_note(dateNote);

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

	// Listar registros por nombre de estudiante
    public List<ModeloNote_detail> buscarStudent(String studentName) {
        List<ModeloNote_detail> notesByStudent = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM list_note_detail WHERE Estudiantes = ?");
            statement.setString(1, studentName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String studentId = resultSet.getString("Estudiantes");
                String competence_id = resultSet.getString("Competencia");
                String note = resultSet.getString("Nota");
                String dateNote = resultSet.getString("Fecha de registro");
                
                ModeloNote_detail noteObj = new ModeloNote_detail();
                noteObj.setId(id);
                noteObj.setStudent_id(studentId);
                noteObj.setCompetence_id(competence_id);
                noteObj.setNote(note);
                noteObj.setDate_note(dateNote);
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
	
    public void createRecord(String studentName, String competence, String note) 
   		 throws SQLException{
   	 Connection connection = null;
        PreparedStatement preparedStatement = null;
        
       try {
            connection = getConnection();

           // Obtener el ID del estudiante por su nombre
           int studentId = getStudentIdByName(studentName);

           
           if (studentId == -1 ) {
           	throw new SQLException("No se pudo crear el registro. Verifica que los nombres proporcionados existan en las tablas relacionadas.");
           }

           // Crear la instancia de Note_detail con los valores proporcionados
           ModeloNote_detail noteDetail = new ModeloNote_detail();
           noteDetail.setStudent_id(String.valueOf(studentId));
           noteDetail.setCompetence_id(competence);
           noteDetail.setNote(note);
           

           // Establecer la fecha actual por defecto en el formato "d-m-y"
           LocalDate currentDate = LocalDate.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMM - yyyy");
           String formattedDate = currentDate.format(formatter);
           noteDetail.setDate_note(formattedDate);

           // Crear la sentencia SQL para insertar el nuevo registro en la tabla 'note_detail'
           String sql = "INSERT INTO note_detail (student_id, competence_id, note, date_note) VALUES (?, ?, ?, ?)";
           preparedStatement = connection.prepareStatement(sql);

           preparedStatement.setString(1, noteDetail.getStudent_id());
           preparedStatement.setString(2, noteDetail.getCompetence_id());
           preparedStatement.setString(3, noteDetail.getNote());
           preparedStatement.setString(4, noteDetail.getDate_note());

           int rowsAffected = preparedStatement.executeUpdate();
           if (rowsAffected > 0) {
               System.out.println("El registro se ha creado exitosamente.");
           }  else {
               throw new SQLException("No se pudo crear el registro.");
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
    
 // Obtener el ID del estudiante por su nombre
    private int getStudentIdByName(String studentName) {
        int studentId = -1; // Valor predeterminado si no se encuentra el estudiante

        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM student WHERE CONCAT(names, ' ', last_name) = ?");
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
    
    public void updateRecord(String studentName,  String competence_id, String note) {
        try {
            Connection connection = getConnection();

            // Obtener el ID del estudiante por su nombre
            int studentId = getStudentIdByName(studentName);

            if (studentId == -1) {
                System.out.println("No se pudo actualizar el registro. Verifica que los nombres proporcionados existan en las tablas relacionadas.");
                return;
            }

            // Actualizar la instancia de Note_detail con los nuevos valores proporcionados
            ModeloNote_detail noteDetail = new ModeloNote_detail();
            noteDetail.setStudent_id(String.valueOf(studentId));
            noteDetail.setCompetence_id(competence_id);
            noteDetail.setNote(note);

            // Establecer la fecha actual por defecto en el formato "d-m-y"
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd - MMM - yyyy");
            String formattedDate = currentDate.format(formatter);
            noteDetail.setDate_note(formattedDate);

            // Crear la sentencia SQL para actualizar el registro en la tabla 'note_detail'
            String sql = "UPDATE note_detail SET competence_id=?, note=?, date_note=? WHERE student_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, noteDetail.getCompetence_id());
            preparedStatement.setString(2, noteDetail.getNote());
            preparedStatement.setString(3, noteDetail.getDate_note());
            preparedStatement.setString(4, noteDetail.getStudent_id());
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("El registro se ha actualizado exitosamente.");
            } else {
                System.out.println("No se pudo actualizar el registro.");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 
	
	@Override
	public ModeloNote_detail mapRow(ResultSet rs) throws SQLException {
		ModeloNote_detail bean = new ModeloNote_detail();
  		// Columnas: id, apellido, nombre, tipo de documento, email, numero de documento, celular, estado y id de grado
  		bean.setId(rs.getInt("id"));
  		bean.setStudent_id(rs.getString("student_id"));
  		bean.setCompetence_id(rs.getString("competencia_id"));
  		bean.setNote(rs.getString("note"));
  		bean.setDate_note(rs.getString("date_note"));
  		
  	
  		return bean;
	}

	@Override
	public List<ModeloNote_detail> getActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModeloNote_detail> getInactive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModeloNote_detail getForId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModeloNote_detail> get(ModeloNote_detail bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(ModeloNote_detail bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ModeloNote_detail bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String id) {
		// Variables
	    Connection cn = null;
	    String sql = null;
	    PreparedStatement pstm = null;
	    // Proceso
	    try {
	        // Iniciar la Tx
	        cn = AccesoDB.getConnection();
	        cn.setAutoCommit(false);
	        pstm = cn.prepareStatement(SQL_DELETE);
			pstm.setInt(1, Integer.parseInt(id));

	        // Eliminar el estudiante de la tabla
	        sql = "DELETE FROM note_detail WHERE id = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, id);
	        pstm.executeUpdate();
	        pstm.close();

	        // Fin de Tx
	        cn.commit();
	    } catch (SQLException e) {
	        try {
	            cn.rollback();
	            cn.close();
	        } catch (Exception e2) {
	        }
	        throw new RuntimeException(e.getMessage());
	    } finally {
	        try {
	            cn.close();
	        } catch (Exception e2) {
	        }
	    }
	}

	@Override
	public void restore(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminate(String id) {
		// TODO Auto-generated method stub
		
	}
}
