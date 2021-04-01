package src.p03.c01;

/**
 * Clase principal SistemaLanzador. 
 * 
 * 
 * @author Sergio Osuna y Miguel Collado
 *
 */
public class SistemaLanzador {
	public static void main(String[] args) {
		
		IParque parque = AdaptadorParqueSincronizado.getInstancia();
		char letra_puerta = 'A';
		
		System.out.println("¡Parque abierto!");
		
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			// 
			// TODO
			//
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread(salidas).start();
			
		}
	}	
}