package analisadorDependencias.controle;

import analisadorDependencias.core.ComparadorObjetos;
import analisadorDependencias.view.View;

public class Controller {
	private static Controller controller;
	private View view;
	private ComparadorObjetos comparadorObjetos;

	public static synchronized Controller getInstancia() {
		if (controller == null) {
			controller = new Controller();
		}

		return controller;
	}

	private Controller() {

	}

	public void setView(View view) {
		this.view = view;
	}

	public void setComparadorObjetos(ComparadorObjetos comparadorObjetos) {
		this.comparadorObjetos = comparadorObjetos;
	}

	public void armazenarDependencias(String textoDependencias) throws Exception {
		comparadorObjetos.armazenarDependencias(textoDependencias);
	}

	public void armazenarObjetosAmbiente(String objetosAmbiente) throws Exception {
		comparadorObjetos.armazenarObjetosAmbiente(objetosAmbiente);
	}

	public String getDependenciasNomes() {
		return comparadorObjetos.getDependenciasNomes();
	}

	public String compararObjetos() {
		return comparadorObjetos.compararObjetos();
	}

	public void reiniciarDados() {
		comparadorObjetos.reiniciarDados();
	}

	public void setSucesso(boolean sucesso) {
		view.setSucesso(sucesso);
	}
}
