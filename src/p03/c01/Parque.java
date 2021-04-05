package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque {

	private final static int MAX_ENTRADAS = 20;
	private final static int MAX_SALIDAS = 20;

	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;

	public Parque() { // TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		// TODO
	}

	@Override
	public synchronized void entrarAlParque(String puerta) {

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		comprobarAntesDeEntrar();
		if (contadoresPersonasPuerta.get(puerta) < MAX_ENTRADAS) {

			// Aumentamos el contador total y el individual
			contadorPersonasTotales++;
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

		}
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");

		// TODO
		checkInvariante();
		// TODO
	}

	//
	// TODO Método salirDelParque
	//
	public synchronized void salirDelParque(String puerta) {
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.put(puerta, 0);
		}

		comprobarAntesDeSalir();
		if (contadoresPersonasPuerta.get(puerta) > 0) {
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);
			contadorPersonasTotales--;
		}
		
		imprimirInfo(puerta, "Salida");

	}

	private void imprimirInfo(String puerta, String movimiento) {
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); // + " tiempo medio de estancia: " +
																					// tmedio);

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
		// TODO
		// TODO
	}

	protected void comprobarAntesDeEntrar() { // TODO
		assert sumarContadoresPuerta() < MAX_ENTRADAS
			: "INV: El total de personas en el parque no puede ser mayor que el establecido en MAX_ENTRADAS";
	}

	protected void comprobarAntesDeSalir() { // TODO
		assert sumarContadoresPuerta() > 0
			: "INV: Tiene que haber almenos una persona en el parque para poder salir";
	}

}
