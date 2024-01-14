package analisadorDependencias;

public class Controller {
	private static Controller controller;
	
	public static synchronized Controller getInstancia() {
		if(controller == null) {
			controller = new Controller();	
		}
		
		return controller;
	}
	
	private Controller() {
		
	}
}
