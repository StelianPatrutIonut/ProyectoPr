package proyectoFinalFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.script.ScriptException;

public class jugadores extends menuPrincipal {
	private ArrayList<jugadores> jugadores;
	private String nombre;
	private int puntuacion;

	public jugadores(String nombre) {
		this.nombre = nombre;
		this.puntuacion = 0;
	}

	public ArrayList<jugadores> getJugadores() {
		return jugadores;
	}

	public void setJugadores(ArrayList<jugadores> jugadores) {
		this.jugadores = jugadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public void mostrarDatos() {
		System.out.println("Nombre: " + nombre);
	}
	public void addJugadores(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
	    Scanner teclado = new Scanner(System.in);
	    System.out.println("¿Qué quieres añadir, jugadores humano o jugadores CPU?");
	    String respuesta2 = teclado.nextLine().toLowerCase();
	    if (respuesta2.equals("humano")) {
	        System.out.println("¿Cuántos jugadores deseas agregar?");
	        int numJugadores = teclado.nextInt();
	        teclado.nextLine();

	        int jugadoresAgregados = jugadores.size(); // Obtener la cantidad actual de jugadores agregados

	        if (jugadoresAgregados >= 4) {
	            System.out.println("No puedes añadir más jugadores.");
	        } else {
	            int jugadoresRestantes = 4 - jugadoresAgregados; // Calcular la cantidad de jugadores restantes que se pueden agregar
	            int jugadoresAAgregar = Math.min(numJugadores, jugadoresRestantes); // Determinar cuántos jugadores se pueden agregar

	            for (int i = 0; i < jugadoresAAgregar; i++) {
	                System.out.println("Ingrese el nombre del jugador " + (i + 1) + ":");
	                String nombre = teclado.nextLine();

	                boolean existe = ranking.jugadorExiste(nombre);

	                if (existe) {
	                    System.out.println("El jugador " + nombre + " ya existe en el ranking.");
	                    System.out.println("¿Deseas seleccionar a este jugador para jugar? (s/n)");
	                    String seleccionar = teclado.nextLine().toLowerCase();

	                    if (seleccionar.equals("s")) {
	                        boolean jugadorEncontrado = obtenerJugador(nombre);
	                        if (jugadorEncontrado) {
	                            jugadores j = new jugadores(nombre);
	                            jugadores.add(j);
	                            jugadoresAgregados++;
	                            System.out.println("Jugador seleccionado: " + nombre);
	                        } else {
	                            System.out.println("No se encontró al jugador en el ranking.");
	                        }
	                    }
	                } else {
	                    ranking.insertarJugador(nombre, 0);
	                    jugadoresAgregados++;
	                    jugadores j = new jugadores(nombre);
	                    jugadores.add(j);
	                }
	            }

	            if (jugadoresAAgregar > 0) {
	                System.out.println("Se han agregado " + jugadoresAAgregar + " jugadores humanos.");
	            }
	        }
	    } else if (respuesta2.equals("cpu")) {
	        System.out.println("¿Cuántas CPUs quieres añadir?");
	        int numCpus = teclado.nextInt();
	        teclado.nextLine();

	        int jugadoresAgregados = jugadores.size(); // Obtener la cantidad actual de jugadores agregados

	        if (jugadoresAgregados >= 4) {
	            System.out.println("No puedes añadir más jugadores.");
	        } else {
	            int jugadoresRestantes = 4 - jugadoresAgregados; // Calcular la cantidad de jugadores restantes que se pueden agregar
	            int cpusAAgregar = Math.min(numCpus, jugadoresRestantes); // Determinar cuántas CPUs se pueden agregar

	            for (int i = 0; i < cpusAAgregar; i++) {
	                String nombre = "CPU" + (i + 1);
	                jugadores cpu = new jugadores(nombre.toLowerCase());
	                jugadores.add(cpu);
	                jugadoresAgregados++;
	            }

	            if (cpusAAgregar > 0) {
	                System.out.println("Se han agregado " + cpusAAgregar + " CPUs.");
	            }
	        }
	    }

	    jugadores(jugadores);
	}

	public boolean obtenerJugador(String nombre) {
		if (ranking.jugadorExiste(nombre)==true) {
			return true;
		}else {
			return false;
		}
	}
	
	public void verJugadores(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
		  System.out.println("Jugadores agregados:");
			ranking.mostrarJugadores();
	      
	        
	   jugadores(jugadores);

	}

	public void eliminarJugadores(String jugador) throws SQLException, ScriptException, IOException {
		
		
		if (ranking.jugadorExiste(jugador)==true) {
			ranking.eliminarJugador(jugador);
		}else {
			for (int i = 0; i < jugadores.size(); i++) {
				if (jugadores.get(i).getNombre()==jugador) {
					jugadores.remove(i);
				}
			}
		}
		    
		    jugadores(jugadores);
		}
	
	public void sumarPuntos() {
	    this.puntuacion = this.puntuacion+1;
	}



		
	}
	

