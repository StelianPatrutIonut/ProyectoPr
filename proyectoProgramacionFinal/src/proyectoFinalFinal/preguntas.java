package proyectoFinalFinal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.script.ScriptException;

public class preguntas extends menuPrincipal {
	static String respuestaLetras;
	static String respuestaIngles;
	static String preguntaMates;
	static String respuestaLetrasCpu;
	static boolean respuestaInglesCpu;
	static boolean preguntaMatesCpu;
	final static ArrayList<String>opciones2=new ArrayList<>();
	public void generarPreguntas(ArrayList<jugadores> jugadores) throws ScriptException {
		double random = Math.random();

		if (random < 0.33) {

			preguntasLetras();
		} else if (random < 0.67) {

			preguntaIngles();
		} else {

			preguntasMates();
		}

	}

	public void generarpreguntasCpu() throws ScriptException {
		double random = Math.random();

		if (random < 0.33) {

			preguntale();
		} else if (random < 0.67) {

			preguntaIn();
		} else {

			preguntaMa();
		}

	}

	public void preguntale() throws ScriptException {
		// Acciones para preguntas de Letras
		// CPU siempre falla
		String respuesta = null;
		respuestaLetrasCpu = respuesta;

	}

	public void preguntaIn() {
		// Acciones para preguntas de Inglés
		// CPU elige al azar una respuesta
		Random random = new Random();
	    int azar = random.nextInt(4) + 1;
	    Random random2 = new Random();
	    int azar2 = random2.nextInt(4) + 1;
	    if (azar == azar2) {
			respuestaInglesCpu=true;
		}
	    else {
			respuestaInglesCpu=false;
		}
	}

	public void preguntaMa() {
		// Acciones para preguntas de Mates
		// CPU siempre acierta
		boolean correcto = true;
		preguntaMatesCpu = correcto;
	}

	public void preguntasMates() throws ScriptException {
	    Random random = new Random();
	    int longitud = random.nextInt(5) + 4; // Genera un número aleatorio entre 4 y 8 (longitud de la expresión)

	    ArrayList<Integer> numeros = new ArrayList<>();
	    // Genera números aleatorios entre 2 y 12 y los agrega a la lista 'numeros'
	    for (int i = 0; i < longitud; i++) {
	        int numero = random.nextInt(11) + 2;
	        numeros.add(numero);
	    }

	    ArrayList<String> operandos = new ArrayList<>();
	    // Genera operandos aleatorios ('+', '-', '*') y los agrega a la lista 'operandos'
	    for (int i = 0; i < longitud - 1; i++) {
	        int operandoAleatorio = random.nextInt(3);
	        if (operandoAleatorio == 0) {
	            operandos.add("+");
	        } else if (operandoAleatorio == 1) {
	            operandos.add("-");
	        } else {
	            operandos.add("*");
	        }
	    }

	    ArrayList<String> expresion = new ArrayList<>();
	    expresion.add(String.valueOf(random.nextInt(11) + 2)); // Agrega el primer número aleatorio a la expresión

	    // Agrega los operandos y los números restantes a la expresión
	    for (int i = 1; i < longitud; i++) {
	        int operandoAleatorio = random.nextInt(3);
	        int numeroAleatorio = random.nextInt(11) + 2;
	        if (operandoAleatorio == 0) {
	            expresion.add("+");
	            expresion.add(String.valueOf(numeroAleatorio));
	        } else if (operandoAleatorio == 1) {
	            expresion.add("-");
	            expresion.add(String.valueOf(numeroAleatorio));
	        } else {
	            expresion.add("*");
	            expresion.add(String.valueOf(numeroAleatorio));
	        }
	    }

	    System.out.print("La expresion es: ");
	    for (int i = 0; i < expresion.size(); i++) {
	        System.out.print(expresion.get(i));
	    }
	    System.out.println();

	    if (expresion.contains("*")) {
	        // Realiza las multiplicaciones en la expresión
	        for (int i = 0; i < expresion.size(); i++) {
	            if (expresion.get(i).equals("*")) {
	                int num1 = Integer.parseInt(expresion.get(i - 1));
	                int num2 = Integer.parseInt(expresion.get(i + 1));
	                int resultado = num1 * num2;
	                expresion.set(i - 1, String.valueOf(resultado)); // Reemplaza el primer número por el resultado
	                expresion.remove(i); // Elimina el operando de multiplicación
	                expresion.remove(i); // Elimina el segundo número
	                i -= 2; // Ajusta el índice
	            }
	        }
	    }

	    if (expresion.contains("+") || expresion.contains("-")) {
	        // Realiza las sumas y restas en la expresión
	        int resultado = Integer.parseInt(expresion.get(0)); // El resultado empieza en el primer número
	        String operacion = ""; // La operación actual
	        for (int i = 1; i < expresion.size(); i++) {
	            if (i % 2 == 1) { // Si el índice es impar, es un operando
	                operacion = expresion.get(i);
	            } else { // Si el índice es par, es un número
	                int numero = Integer.parseInt(expresion.get(i));
	                if (operacion.equals("+")) {
	                    resultado += numero;
	                } else { // Si la operación es "-", resta el número
	                    resultado -= numero;
	                }
	            }
	        }
	       
	        preguntaMates = preguntaMates.valueOf(resultado); // Asigna el resultado a la variable 'preguntaMates'
	    }
	}

	public void preguntasLetras() throws ScriptException {
	    boolean punto = false;
	    Scanner teclado = new Scanner(System.in);
	    ArrayList<String> palabras = new ArrayList<>();

	    // Lee el archivo de diccionario.txt y guarda las palabras en la lista 'palabras'
	    try (FileReader fr = new FileReader("src/proyectoFinalFinal/diccionario.txt");
	         BufferedReader br = new BufferedReader(fr)) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            palabras.add(linea);
	        }
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    }

	    Random rand = new Random();
	    String palabraAleatoria = palabras.get(rand.nextInt(palabras.size())); // Obtiene una palabra aleatoria del diccionario

	    int longitudPalabra = palabraAleatoria.length();
	    int division = longitudPalabra / 3;
	    int index = 0;

	    char[] auxPalabra = new char[longitudPalabra + division];

	    //  reemplazando cada tercer caracter con '*'
	    for (int i = 0; i < longitudPalabra; i++) {
	        if ((i + 1) % 3 == 0) {
	            auxPalabra[i] = '*';
	        } else {
	            auxPalabra[i] = palabraAleatoria.charAt(i);
	        }
	    }

	    String adivina = new String(auxPalabra);
	  
	    System.out.println("La palabra es: " + adivina);
	    respuestaLetras = palabraAleatoria;

	   

	}

	public void preguntaIngles() throws ScriptException {

	    ArrayList<String> opciones = new ArrayList<>();


	    try (FileReader fr = new FileReader("src/proyectoFinalFinal/ingles.txt");
	            BufferedReader br = new BufferedReader(fr)) {
	        int numPreguntas = 5000 / 5; // número de preguntas en el archivo
	        int preguntaAleatoria = (int) (Math.random() * numPreguntas) + 1; // seleccionar una pregunta aleatoria
	        int primeraLinea = (preguntaAleatoria - 1) * 5 + 1; // calcular la línea de la primera pregunta

	        // Leer la pregunta
	        String pregunta = null;
	        for (int i = 1; i < primeraLinea; i++) {
	            br.readLine(); // Saltar las líneas anteriores a la pregunta
	        }
	        pregunta = br.readLine();
	        System.out.println(pregunta);

	        // Leer las opciones
	        for (int i = 0; i < 4; i++) {
	            String opcion = br.readLine();
	            opciones.add(opcion);
	        }
	        // Guardar la primera opción en respuestaVerdadera
	        String respuestaVerdadera = opciones.get(0);
	        
	      

	     

	        // Desordenar opciones2
	        Collections.shuffle(opciones);

	        // Imprimir las opciones desordenadas
	        System.out.println("Opciones desordenadas:");
	        System.out.println( opciones.get(0));
	        System.out.println( opciones.get(1));
	        System.out.println(opciones.get(2));
	        System.out.println( opciones.get(3));

	     respuestaIngles=respuestaVerdadera;
	    
	       
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    }
	}

	public boolean compararRespuestas(String respuesta) {
		if (respuestaLetras != null && respuestaLetras.equals(respuesta)) {
			return true;
		} else if (respuestaIngles != null && respuestaIngles.equals(respuesta) ) {
			return true;
		} else if (preguntaMates != null && preguntaMates.equals(respuesta)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean compararRespuestasCpu() {
		{
			if (respuestaLetrasCpu != null) {
				return true;
			} else if (respuestaInglesCpu == true) {
				return true;
			} else if (preguntaMatesCpu == true) {
				return true;
			} else {
				return false;
			}
		}

	}

}
