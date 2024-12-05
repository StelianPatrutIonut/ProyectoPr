package proyectoFinalFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class historico extends menuPrincipal {

	static ArrayList<jugadores> jugadores = new ArrayList();
	// Conexión a la base de datos
	private static int partidaId = 1;
	private static Set<String> nombresRegistrados = new HashSet<>();
	private static Connection conn = null;

	// Configuración de la conexión a la base de datos
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3308";
	private static final String DB_NAME = "juegoDamGGM";
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
			+ "?serverTimezone=UTC";
	private static final String DB_USER = "dam2223";
	private static final String DB_PASS = "dam2223";
	private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
	private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

	// Configuración de la tabla Clientes
	private static final String DB_CLI = "histórico";
	private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
	private static final String DB_ID = "id_partida";
	private static final String DB_Name = "nickname";
	private static final String DB_Puntuacion = "puntuacion";
	private static final String DB_NAME_COLUMN = "nickname";
	private static final String DB_SCORE_COLUMN = "puntuacion";

	public static boolean loadDriver() {
		try {
			System.out.print("Cargando Driver...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("OK!");
			return true;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Intenta conectar con la base de datos.
	 *
	 * @return true si pudo conectarse, false en caso contrario
	 */
	public static boolean connect() {
		try {
			System.out.print("Conectando a la base de datos...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("OK!");
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Comprueba la conexión y muestra su estado por pantalla
	 *
	 * @return true si la conexión existe y es válida, false en caso contrario
	 */
	public static boolean isConnected() {
		// Comprobamos estado de la conexión
		try {
			if (conn != null && conn.isValid(0)) {
				System.out.println(DB_MSQ_CONN_OK);
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println(DB_MSQ_CONN_NO);
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public static void close() {
		try {
			System.out.print("Cerrando la conexión...");
			conn.close();
			System.out.println("OK!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void insertarJugador(String nombre, int puntuacion) {
	    PreparedStatement stmt = null;

	    try {
	        // Obtener el último ID de partida de la tabla
	        String getLastIdQuery = "SELECT MAX(id_partida) FROM histórico";
	        Statement getLastIdStmt = conn.createStatement();
	        ResultSet lastIdResult = getLastIdStmt.executeQuery(getLastIdQuery);
	        int lastId = 0;

	        if (lastIdResult.next()) {
	            lastId = lastIdResult.getInt(1);
	        }

	        // Generar el nuevo ID de partida sumando 1 al último ID obtenido
	        int nuevaPartidaId = lastId + 1;

	        // Crear la sentencia SQL para la inserción del registro en la tabla "histórico"
	        String sql = "INSERT INTO histórico (id_partida, nickname, puntuacion) VALUES (?, ?, ?)";
	        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	        // Establecer los valores de los parámetros
	        stmt.setInt(1, nuevaPartidaId);
	        stmt.setString(2, nombre);
	        stmt.setInt(3, puntuacion);

	        // Ejecutar la sentencia SQL para insertar el registro en la tabla "histórico"
	        stmt.executeUpdate();

	        // Obtener el valor generado para el campo id_partida
	        ResultSet generatedKeys = stmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            int generatedId = generatedKeys.getInt(1);
	            System.out.println("ID de partida generado: " + generatedId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Cerrar el PreparedStatement
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	public static void mostrarHistorial() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// Establecer la conexión con la base de datos
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			// Obtener los datos del ranking ordenados por puntuación descendente
			String query = DB_CLI_SELECT;
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			System.out.println("Historico:");

			// Mostrar los datos del ranking por pantalla
			while (rs.next()) {
				String id = rs.getString(DB_ID);
				String nombre = rs.getString(DB_NAME_COLUMN);
				int puntuacion = rs.getInt(DB_SCORE_COLUMN);

				System.out.println(id + ". " + nombre + " - Puntuación: " + puntuacion);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Cerrar la conexión y liberar los recursos
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
