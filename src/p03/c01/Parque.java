package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Clase AdaptadorParqueSincronizado. 
 * 
 * 
 * @author Sergio Osuna y Miguel Collado
 *
 */
public class Parque implements IParque {

	private final static int MAX_ENTRADAS = 20;
	private final static int MAX_SALIDAS = 0;

	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;

	public Parque() { 
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		
	}

	@Override
	public synchronized void entrarAlParque(String puerta) throws InterruptedException {

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		// verificamos si podemos entrar, si no, esperamos
		while (!comprobarAntesDeEntrar()) {
            wait();
        }
		if (contadoresPersonasPuerta.get(puerta) < MAX_ENTRADAS) {

			// Aumentamos el contador total y el individual
			contadorPersonasTotales++;
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

		}
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
	
		checkInvariante();
		
		notifyAll();
	}

	public synchronized void salirDelParque(String puerta) throws InterruptedException {
		// Si no hay salidas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		// verificamos si podemos salir, si no, esperamos
		while (!comprobarAntesDeSalir()) {
            wait();
        }
		
		if (contadoresPersonasPuerta.get(puerta) > 0) {
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);
			contadorPersonasTotales--;
		}
		
		imprimirInfo(puerta, "Salida");
		
		checkInvariante();

		notifyAll();
	}

	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); 

		// Iteramos por todas las puertas e imprimimos sus entradas
		for (String p : contadoresPersonasPuerta.keySet()) {
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}

	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
		Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
		while (iterPuertas.hasMoreElements()) {
			sumaContadoresPuerta += iterPuertas.nextElement();
		}
		return sumaContadoresPuerta;
	}

	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales
				: "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
	}

	protected boolean comprobarAntesDeEntrar() { 
		if (contadorPersonasTotales == MAX_ENTRADAS ){
			return false;
		}
		return true;
	}

	protected boolean comprobarAntesDeSalir() { 
		if (contadorPersonasTotales == MAX_SALIDAS ){
			return false;
		}
		return true;
	}

}
