package proyectoFinalFinal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.script.ScriptException;

public class partida extends menuPrincipal {


	
	public static void jugarPartida(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("Que quieres hacer: Practica o Jugar");
		String respuesta = teclado.nextLine().toLowerCase();
		if (respuesta.equals("practica")) {

			System.out.println("La pracitca va a constar de una ronda de 10 preguntas");
			int numRondas = 9;
			while (numRondas >= 0) {
				ArrayList<jugadores> practica = new ArrayList<>();
				jugadores practica1 = new jugadores(" ");
				practica.add(practica1);

				preguntas pr = new preguntas();
				pr.generarPreguntas(practica);
				System.out.println("dame tu respuesta: ");
				String respuesta1 = teclado.nextLine();
				if (pr.compararRespuestas(respuesta1) == true) {
					System.out.println("felicidades siguiente punto");
				}
				numRondas--;

			}
	
		} else {

			System.out.println("Lista de jugadores:");
			if (jugadores.size() == 0) {
				System.out.println("Lo siento no tienes jugadores todavia ");
				System.out.println("Quieres ir a añadir jugadores?-SI o NO");
				String resp = teclado.nextLine();
				if (resp.equalsIgnoreCase("si")) {
					jugadores(jugadores);
				} else {
					menuPartida(jugadores);
				}
			} else {

				for (int i = 0; i < jugadores.size(); i++) {
					System.out.println(jugadores.get(i).getNombre());
				}

			}

			menuPartida(jugadores);
		}
	}
	public static void partidaRapida(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
	    Scanner teclado = new Scanner(System.in);
	
	    int numRonda = 3;
	    int numJugadores = jugadores.size();

	    int ronda = 1;
	 
	    while (ronda <= numRonda) {
	        System.out.println("Ronda " + ronda);

	        for (int i = 0; i < numJugadores; i++) {
	            jugadores jugador = jugadores.get(i);
	            System.out.println("Pregunta para: " + jugador.getNombre());
	            if (jugador.getNombre().startsWith("cpu")) {
	                preguntas pregunta = new preguntas();
	                pregunta.generarpreguntasCpu();

	                if (pregunta.compararRespuestasCpu()) {
	                    jugador.sumarPuntos();
	                }
	            } else {
	                preguntas pregunta = new preguntas();
	                pregunta.generarPreguntas(jugadores);
	                System.out.println("Dame una respuesta:");
	                String respuesta = teclado.nextLine().trim();
	                if (pregunta.compararRespuestas(respuesta)) {
	                    jugador.sumarPuntos();
	                }
	            }
	            System.out.println("Puntos de " + jugador.getNombre() + ": " + jugador.getPuntuacion());
	        }
	        ronda++;
	        
	    }

	    System.out.println("Puntos de cada jugador:");
	    for (int i = 0; i < numJugadores; i++) {
	        System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntuacion());
	        historico.connect();
	        
	        historico.insertarJugador(jugadores.get(i).getNombre(), jugadores.get(i).getPuntuacion());
	    }
	   historico.close();
	   

	    // Guardar jugadores y puntuaciones en el ranking de la base de datos
	     
	    ranking.connect();
	    ranking.generarRaking(jugadores);
	    ranking.close();

	    menuPartida(jugadores);
	}

	public static void partidaCorta(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
	    Scanner teclado = new Scanner(System.in);
	  
	    int numRonda = 5;
	    int numJugadores = jugadores.size();

	    int ronda = 1;
	    while (ronda <= numRonda) {
	        System.out.println("Ronda " + ronda);

	        for (int i = 0; i < numJugadores; i++) {
	            jugadores jugador = jugadores.get(i);
	            System.out.println("Pregunta para: " + jugador.getNombre());
	            if (jugador.getNombre().startsWith("cpu")) {
	                preguntas pregunta = new preguntas();
	                pregunta.generarpreguntasCpu();

	                if (pregunta.compararRespuestasCpu()) {
	                    jugador.sumarPuntos();
	                }
	            } else {
	                preguntas pregunta = new preguntas();
	                pregunta.generarPreguntas(jugadores);
	                System.out.println("Dame una respuesta:");
	                String respuesta = teclado.nextLine().trim();
	                if (pregunta.compararRespuestas(respuesta)) {
	                    jugador.sumarPuntos();
	                }
	            }
	            System.out.println("Puntos de " + jugador.getNombre() + ": " + jugador.getPuntuacion());
	        }
	        ronda++;
	    }

	    System.out.println("Puntos de cada jugador:");
	    for (int i = 0; i < numJugadores; i++) {
	        System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntuacion());
	        historico.insertarJugador(jugadores.get(i).getNombre(), jugadores.get(i).getPuntuacion());
	    }
	   historico.close();
	   
	    // Guardar jugadores y puntuaciones en el ranking de la base de datos
	    ;
	 
	    ranking.connect();
	    ranking.generarRaking(jugadores);
	    ranking.close();

	    menuPartida(jugadores);
	}

	public static void partidaNormal(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
		
		    Scanner teclado = new Scanner(System.in);
		    
		    int numRonda = 10;
		    int numJugadores = jugadores.size();

		    int ronda = 1;
		    while (ronda <= numRonda) {
		        System.out.println("Ronda " + ronda);

		        for (int i = 0; i < numJugadores; i++) {
		            jugadores jugador = jugadores.get(i);
		            System.out.println("Pregunta para: " + jugador.getNombre());
		            if (jugador.getNombre().startsWith("cpu")) {
		                preguntas pregunta = new preguntas();
		                pregunta.generarpreguntasCpu();

		                if (pregunta.compararRespuestasCpu()) {
		                    jugador.sumarPuntos();
		                }
		            } else {
		                preguntas pregunta = new preguntas();
		                pregunta.generarPreguntas(jugadores);
		                System.out.println("Dame una respuesta:");
		                String respuesta = teclado.nextLine().trim();
		                if (pregunta.compararRespuestas(respuesta)) {
		                    jugador.sumarPuntos();
		                }
		            }
		            System.out.println("Puntos de " + jugador.getNombre() + ": " + jugador.getPuntuacion());
		        }
		        ronda++;
		    }

		    System.out.println("Puntos de cada jugador:");
		    for (int i = 0; i < numJugadores; i++) {
		        System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntuacion());
		    }
		    System.out.println("Puntos de cada jugador:");
		    for (int i = 0; i < numJugadores; i++) {
		        System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntuacion());
		        historico.connect();
		        historico.insertarJugador(jugadores.get(i).getNombre(), jugadores.get(i).getPuntuacion());
		    }
		   historico.close();
		   
		 
		    ranking.connect();
		    ranking.generarRaking(jugadores);
		    ranking.close();

		    menuPartida(jugadores);
		}
	public static void partidaLarga(ArrayList<jugadores> jugadores) throws ScriptException, IOException, SQLException {
		  Scanner teclado = new Scanner(System.in);
		
		    int numRonda = 20;
		    int numJugadores = jugadores.size();

		    int ronda = 1;
		    while (ronda <= numRonda) {
		        System.out.println("Ronda " + ronda);

		        for (int i = 0; i < numJugadores; i++) {
		            jugadores jugador = jugadores.get(i);
		            System.out.println("Pregunta para: " + jugador.getNombre());
		            if (jugador.getNombre().startsWith("cpu")) {
		                preguntas pregunta = new preguntas();
		                pregunta.generarpreguntasCpu();

		                if (pregunta.compararRespuestasCpu()) {
		                    jugador.sumarPuntos();
		                }
		            } else {
		                preguntas pregunta = new preguntas();
		                pregunta.generarPreguntas(jugadores);
		                System.out.println("Dame una respuesta:");
		                String respuesta = teclado.nextLine().trim();
		                if (pregunta.compararRespuestas(respuesta)) {
		                    jugador.sumarPuntos();
		                }
		            }
		            System.out.println("Puntos de " + jugador.getNombre() + ": " + jugador.getPuntuacion());
		        }
		        ronda++;
		    }

		    System.out.println("Puntos de cada jugador:");
		    for (int i = 0; i < numJugadores; i++) {
		        System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntuacion());
		    }

		    System.out.println("Puntos de cada jugador:");
		    for (int i = 0; i < numJugadores; i++) {
		        System.out.println(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntuacion());
		        historico.connect();
		        historico.insertarJugador(jugadores.get(i).getNombre(), jugadores.get(i).getPuntuacion());
		    }
		   historico.close();
		   
		    ranking ranking = new ranking();

		    ranking.connect();
		    ranking.generarRaking(jugadores);
		    ranking.close();

		    menuPartida(jugadores);
	}
}
