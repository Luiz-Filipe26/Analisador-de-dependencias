package analisadorDependencias.controle;

import analisadorDependencias.core.ComparadorObjetos;
import analisadorDependencias.view.View;

public class Main {
	
	public static void main(String[] args) {
		View view = new View();
		view.setVisible(true);
		
		Controller.getInstancia().setView(view);
		Controller.getInstancia().setComparadorObjetos(new ComparadorObjetos());
	}
}
