package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author mnava
 */
public class GestorDB {

	private String CONN = "jdbc:sqlserver://LAPTOP-0CRE86U4\\SQLEXPRESS:1433;databaseName=Carreras";
	private String USER = "sa";
	private String PASS = "123456";

	public ArrayList<Corredor> obtenerTodosLosCorredores() {
		ArrayList<Corredor> lista = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(CONN, USER, PASS);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Corredores");

			while (rs.next()) {
				int id = rs.getInt(1);
				String nombre = rs.getString(2);

				Corredor a = new Corredor(id, nombre);
				lista.add(a);
			}

			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return lista;
	}

	public void insertarTiempo(Tiempo t) {
		try {
			Connection conn = DriverManager.getConnection(CONN, USER, PASS);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Tiempos VALUES (?,?,?)");
			pstmt.setInt(1, t.getC().getId());
			pstmt.setDouble(2, t.getTiempo());
			pstmt.setInt(3, t.getAnio());

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<Tiempo> obtenerTodosLosTiempos() {
		ArrayList<Tiempo> lista = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(CONN, USER, PASS);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Tiempos");

			while (rs.next()) {
				int id = rs.getInt(1);
				int corredorId = rs.getInt(2);
				double tiempo = rs.getDouble(3);
				int anio = rs.getInt(4);

				Corredor c = obtenerCorredorById(corredorId);
				Tiempo t = new Tiempo(c, tiempo, anio);
				lista.add(t);
			}

			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return lista;
	}

	private Corredor obtenerCorredorById(int corredorId) {
		Corredor resultado = null;

		try {
			Connection conn = DriverManager.getConnection(CONN, USER, PASS);
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Corredores WHERE id=?");
			pstmt.setInt(1, corredorId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString(2);

				resultado = new Corredor(corredorId, nombre);
			}

			pstmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return resultado;
	}

	public String reporte1() {
		float resultado = -1;

		try {

			Connection conn = DriverManager.getConnection(CONN, USER, PASS);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as cuenta FROM (SELECT idCorredor, COUNT(*) as c FROM Tiempos WHERE tiempo < 12 GROUP BY idCorredor) as a;");

			if (rs.next()) {
				resultado = rs.getInt("cuenta");
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return String.valueOf(resultado);
	}

	public ArrayList<ReporteDTO> reporte2() {
		ArrayList<ReporteDTO> lista = new ArrayList<>();

		try {

			Connection conn = DriverManager.getConnection(CONN, USER, PASS);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT c.nombre, AVG(tiempo) as promedio FROM Tiempos JOIN Corredores c ON idCorredor = c.id GROUP BY (c.nombre)");

			while (rs.next()) {
				String nombre = rs.getString("nombre");
				Double promedio = rs.getDouble("promedio");

				ReporteDTO r = new ReporteDTO(nombre, promedio);
				lista.add(r);
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return lista;
	}
}
