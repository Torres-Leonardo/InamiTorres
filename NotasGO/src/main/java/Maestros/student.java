package Maestros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import AccesoDB.AccesoDB;
import MaestrosSpec.CrudServiceSpec;
import MaestrosSpec.RowMapper;
import Modelos.ModeloStudent;

public class student implements CrudServiceSpec<ModeloStudent>, RowMapper<ModeloStudent> {

	private final String SQL_SELECT_ACTIVE = "SELECT * FROM student WHERE active='A'";
	private final String SQL_SELECT_INACTIVE = "SELECT * FROM student WHERE active='I'";
	private final String SQL_SELECT_ID = "SELECT * FROM student WHERE active='A' AND id=?";
	private final String SQL_SELECT_LIKE = "SELECT * FROM student WHERE names LIKE ? AND last_name LIKE ? AND number_document LIKE ? AND grade LIKE ? AND section LIKE ? AND active='A'";
	private final String SQL_INSERT = "INSERT INTO student (names, last_name, type_document, number_document, email, cellphone, grade, section) VALUES (?,?,?,?,?,?,?,?)";
	private final String SQL_UPDATE = "UPDATE student SET names=?, last_name=?, type_document=?, number_document=?, email=?, cellphone=?, grade=?, section=? WHERE id=?";
	private final String SQL_DELETE = "UPDATE student SET active='I' WHERE id=?";
	private final String SQL_RESTORE = "UPDATE student SET active='A' WHERE id=?";
	private final String SQL_ELIMINATE = "DELETE FROM student WHERE id=?";

	@Override
	public List<ModeloStudent> getActive() {
		List<ModeloStudent> lista = new ArrayList<>();
		try (Connection cn = AccesoDB.getConnection();
				PreparedStatement pstm = cn.prepareStatement(SQL_SELECT_ACTIVE);
				ResultSet rs = pstm.executeQuery();) {
			while (rs.next()) {
				ModeloStudent bean = mapRow(rs);
				lista.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<ModeloStudent> getInactive() {
		List<ModeloStudent> lista = new ArrayList<>();
		try (Connection cn = AccesoDB.getConnection();
				PreparedStatement pstm = cn.prepareStatement(SQL_SELECT_INACTIVE);
				ResultSet rs = pstm.executeQuery();) {
			while (rs.next()) {
				ModeloStudent bean = mapRow(rs);
				lista.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public ModeloStudent getForId(String id) {
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ModeloStudent bean = null;
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_SELECT_ID);
			pstm.setInt(1, Integer.parseInt(id));
			rs = pstm.executeQuery();
			if(rs.next()) {
				bean = mapRow(rs);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return bean;
	}

	@Override
	public List<ModeloStudent> get(ModeloStudent bean) {
		Connection cn = null;
		List<ModeloStudent> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ModeloStudent item;
		String names, last_name, number_document, grade, section;
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		last_name = "%" + UtilService.setStringVacio(bean.getLast_name()) + "%";
		number_document = "%" + UtilService.setStringVacio(bean.getNumber_document()) + "%";
		grade = "%" + UtilService.setStringVacio(bean.getGrade()) + "%";
		section = "%" + UtilService.setStringVacio(bean.getSection()) + "%";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_SELECT_LIKE);
			pstm.setString(1, names);
			pstm.setString(2, last_name);
			pstm.setString(3, number_document);
			pstm.setString(4, grade);
			pstm.setString(5, section);
			rs = pstm.executeQuery();
			while(rs.next()) {
				item = mapRow(rs);
				lista.add(item);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	@Override
	public void insert(ModeloStudent bean) {
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas;
		try {
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			pstm = cn.prepareStatement(SQL_INSERT);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getLast_name());
			pstm.setString(3, bean.getType_document());
			pstm.setString(4, bean.getNumber_document());
			pstm.setString(5, bean.getEmail());
			pstm.setString(6, bean.getCellphone());
			pstm.setString(7, bean.getGrade());
			pstm.setString(8, bean.getSection());
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("Error, verifique sus datos e intentelo nuevamente.");
			}
			cn.commit();
		} catch (Exception e) {
			try {
				cn.rollback();
				cn.close();
			} catch (Exception e2) {
			}
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void update(ModeloStudent bean) {
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas;
		try {
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			pstm = cn.prepareStatement(SQL_UPDATE);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getLast_name());
			pstm.setString(3, bean.getType_document());
			pstm.setString(4, bean.getNumber_document());
			pstm.setString(5, bean.getEmail());
			pstm.setString(6, bean.getCellphone());
			pstm.setString(7, bean.getGrade());
			pstm.setString(8, bean.getSection());
			pstm.setInt(9, bean.getId());
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("Error, verifique sus datos e intentelo nuevamente.");
			}
			cn.commit();
		} catch (Exception e) {
			try {
				cn.rollback();
				cn.close();
			} catch (Exception e2) {
			}
		} finally {
			try {
				cn.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void delete(String id) {
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas = 0;
		try {
			// Inicio de Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			pstm = cn.prepareStatement(SQL_DELETE);
			pstm.setInt(1, Integer.parseInt(id));
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("No se pudo eliminar el usuario.");
			}
			cn.commit();
		} catch (SQLException e) {
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
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas = 0;
		try {
			// Inicio de Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			pstm = cn.prepareStatement(SQL_RESTORE);
			pstm.setInt(1, Integer.parseInt(id));
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("No se pudo restaurar el usuario.");
			}
			cn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
	}

	@Override
	public void eliminate(String id) {
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas = 0;
		try {
			// Inicio de Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			pstm = cn.prepareStatement(SQL_ELIMINATE);
			pstm.setInt(1, Integer.parseInt(id));
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("No se pudo eliminar el usuario.");
			}
			cn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
	}

	@Override
	public ModeloStudent mapRow(ResultSet rs) throws SQLException {
		ModeloStudent bean = new ModeloStudent();
		bean.setId(rs.getInt("id"));
		bean.setNames(rs.getString("names"));
		bean.setLast_name(rs.getString("last_name"));
		bean.setType_document(rs.getString("type_document"));
		bean.setNumber_document(rs.getString("number_document"));
		bean.setEmail(rs.getString("email"));
		bean.setCellphone(rs.getString("cellphone"));
		bean.setGrade(rs.getString("grade"));
		bean.setSection(rs.getString("section"));
		bean.setActive(rs.getString("active"));
		return bean;
	}

}
