package src.p03.c01;

/**
 * Clase AdaptadorParqueSincronizado. 
 * 
 * 
 * @author Sergio Osuna y Miguel Collado
 *
 */
public class AdaptadorParqueSincronizado implements IParque{
	
	private IParque parque;
	private static AdaptadorParqueSincronizado instancia = new AdaptadorParqueSincronizado();
	
	private AdaptadorParqueSincronizado() {
		parque = new Parque();
	}
	
	public static AdaptadorParqueSincronizado getInstancia() {
		return instancia;
	}
	
	@Override
	public synchronized void entrarAlParque(String puerta) throws InterruptedException {
		parque.entrarAlParque(puerta);
	}
	
	@Override
	public synchronized void salirDelParque(String puerta) throws InterruptedException {
		parque.salirDelParque(puerta);
	}
	
}
