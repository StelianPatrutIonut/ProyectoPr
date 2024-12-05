package proyectoFinalFinal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Statement;

public class ranking extends menuPrincipal {
	static ArrayList<jugadores> jugadores = new ArrayList();
	// Conexión a la base de datos
    private static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3308";
    private static final String DB_NAME = "juegoDamGGM";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "dam2223";
    private static final String DB_PASS = "dam2223";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    // Configuración de la tabla Clientes
    private static final String DB_CLI = "ranking";
    private static final String DB_CLI_SELECT = "SELECT * FROM " + DB_CLI;
    private static final String DB_Name = "nickname";
    private static final String DB_Puntuacion = "puntuacion";
    private static final String DB_NAME_COLUMN = "nickname";
    private static final String DB_SCORE_COLUMN = "puntuacion";


    //////////////////////////////////////////////////
    // MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
    //////////////////////////////////////////////////
    ;
    
    /**
     * Intenta cargar el JDBC driver.
     * @return true si pudo cargar el driver, false en caso contrario
     */
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
    public static void generarRaking(ArrayList<jugadores> jugadores) {
        Connection conn = null;
        PreparedStatement stmt = null;
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores jugador = jugadores.get(i);
            String nombre = jugador.getNombre();
            int puntuacion = jugador.getPuntuacion();

            insertarJugador(nombre, puntuacion);
        }
    }
    
    public static void insertarJugador(String nombre, int puntuacion) {
        if (nombre.toLowerCase().startsWith("cpu")) {
            System.out.println("Los jugadores CPU no se guardan en el ranking.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Verificar si el jugador ya existe en el ranking
            if (jugadorExiste(nombre)) {
                // Actualizar la puntuación del jugador existente
                String sql = "UPDATE ranking SET puntuacion = puntuacion + ? WHERE nickname = ?";
                stmt = conn.prepareStatement(sql);

                // Establecer los valores de los parámetros
                stmt.setInt(1, puntuacion);
                stmt.setString(2, nombre);
            } else {
                // Crear la sentencia SQL para la inserción del nuevo jugador
                String sql = "INSERT INTO ranking (nickname, puntuacion) VALUES (?, ?)";
                stmt = conn.prepareStatement(sql);

                // Establecer los valores de los parámetros
                stmt.setString(1, nombre);
                stmt.setInt(2, puntuacion);
            }

            // Ejecutar la sentencia SQL
            stmt.executeUpdate();

            System.out.println("Jugador insertado/actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión y liberar los recursos
            try {
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

    public static void mostrarRanking() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Obtener los datos del ranking ordenados por puntuación descendente
            String query = "SELECT * FROM ranking ORDER BY " + DB_SCORE_COLUMN + " DESC";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            System.out.println("Ranking:");
            int posicion = 1;

            // Mostrar los datos del ranking por pantalla
            while (rs.next()) {
                String nombre = rs.getString(DB_NAME_COLUMN);
                int puntuacion = rs.getInt(DB_SCORE_COLUMN);

                System.out.println(posicion + ". " + nombre + " - Puntuación: " + puntuacion);
                posicion++;
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
    public static void agregarJugador(String nombre) {
        // Verificar si el jugador ya existe en el ranking
        if (jugadorExiste(nombre)) {
            System.out.println("El jugador ya existe en el ranking.");
        } else {
            // Agregar jugador nuevo con puntuación inicial de 0
            insertarJugador(nombre, 0);
            System.out.println("Jugador agregado al ranking.");
        }
    }

    public static void eliminarJugador(String nombre) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        if (jugadorExiste(nombre)) {
            try {
                // Establecer la conexión con la base de datos
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                // Crear la sentencia SQL para eliminar el jugador
                String sql = "DELETE FROM ranking WHERE nickname = ?";
                stmt = conn.prepareStatement(sql);

                // Establecer el valor del parámetro
                stmt.setString(1, nombre);

                // Ejecutar la consulta
                stmt.executeUpdate();
                
                System.out.println("El jugador " + nombre + " ha sido eliminado.");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Cerrar la conexión y liberar los recursos
                try {
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
        } else {
            System.out.println("El jugador " + nombre + " no existe en el ranking.");
        }
    }

    public static void mostrarJugadores() {
        // Obtener los jugadores del ranking
        ArrayList<jugadores> jugadoresRanking = obtenerJugadoresRanking();

        // Mostrar los jugadores existentes
        System.out.println("Jugadores en el ranking:");
        for (jugadores jugador : jugadoresRanking) {
            System.out.println(jugador.getNombre());
        }
    }

   public static boolean jugadorExiste(String nombre) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establecer la conexión con la base de datos
        	conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Crear la sentencia SQL para verificar si el jugador existe
            String sql = "SELECT COUNT(*) FROM ranking WHERE nickname = ?";
            stmt = conn.prepareStatement(sql);

            // Establecer el valor del parámetro
            stmt.setString(1, nombre);

            // Ejecutar la consulta
            rs = stmt.executeQuery();

            // Obtener el resultado
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
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

        return false;
    }

    private static ArrayList<jugadores> obtenerJugadoresRanking() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<jugadores> jugadoresRanking = new ArrayList<>();

        try {
            // Establecer la conexión con la base de datos
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Obtener los datos del ranking
            String query = "SELECT * FROM ranking";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Crear objetos de jugadores y agregarlos a la lista
            while (rs.next()) {
                String nombre = rs.getString(DB_NAME_COLUMN);
                int puntuacion = rs.getInt(DB_SCORE_COLUMN);

                jugadores jugador = new jugadores(nombre);
                jugador.setPuntuacion(puntuacion);

                jugadoresRanking.add(jugador);
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

        return jugadoresRanking;
    }

    // ...
}