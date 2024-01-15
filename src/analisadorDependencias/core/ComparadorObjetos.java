package analisadorDependencias.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import analisadorDependencias.util.Objeto;

public class ComparadorObjetos {
	private List<Objeto> dependencias = new ArrayList<>();
	private List<Objeto> objetosAmbiente = new ArrayList<>();
	private List<Objeto> objetosFaltantes = new ArrayList<>();
	
	private final int TAMANHO_STRINGBUILDER_NOMES = 40000;
	private final int TAMANHO_STRINGBUILDER_MENSAGEM = 5000;
	
	
	public void armazenarDependencias(String textoDependencias) throws Exception {
		armazenarObjetos(textoDependencias, dependencias);
	}
	
	public void armazenarObjetosAmbiente(String textoObjetosAmbiente) throws Exception {
		armazenarObjetos(textoObjetosAmbiente, objetosAmbiente);	
	}
	
	private void armazenarObjetos(String textoObjetos, List<Objeto> objetos) throws Exception {
		String[] objetosLinhas = textoObjetos.split("\\r?\\n");
		
		Set<Objeto> objetosUnicos = new LinkedHashSet<>();
		
		for(String objetosLinha : objetosLinhas) {
			String[] atributosObjeto = objetosLinha.substring(1).split("\\|");
			
			String tipo = atributosObjeto[0];
			String nome = atributosObjeto[1];
			String observacao = atributosObjeto.length > 2 ? atributosObjeto[2] : null;
			
			if(observacao == null || observacao.equals(ProcessadorTexto.DEPENDENCIA_NECESSARIA)) {
				Objeto objeto = new Objeto(tipo, nome);
			
				objetosUnicos.add(objeto);
			}				
		}
		
		objetos.clear();
		objetos.addAll(objetosUnicos);
	}
	
	public String compararObjetos() {
		for (Objeto dependencia : dependencias) {
			if (!objetosAmbiente.contains(dependencia)) {
				objetosFaltantes.add(dependencia);
			}
		}
		
		if(objetosFaltantes.isEmpty()) {
			return "Nenhum objeto faltante!";
		}
		
		StringBuilder mensagem = new StringBuilder(TAMANHO_STRINGBUILDER_MENSAGEM);
		mensagem.append("Dependências faltantes\n")
				.append("TIPO\tNOME\n");
		
		
		for(Objeto objeto : objetosFaltantes) {
			mensagem.append("\n" + objeto.tipo + "\t" + objeto.nome);
		}
		
		return mensagem.toString();
	}
	
	public String getDependenciasNomes() {
		StringBuilder stringBuilder = new StringBuilder(TAMANHO_STRINGBUILDER_NOMES);
		
		for (Objeto dependencia : dependencias) {
			stringBuilder.append(dependencia.nome + "\n");
		}
		
		return stringBuilder.toString();
	}
	
	public void reiniciarDados() {
		dependencias.clear();
		objetosAmbiente.clear();
		objetosFaltantes.clear();
	}
}
