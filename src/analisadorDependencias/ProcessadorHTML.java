package analisadorDependencias;

import org.apache.commons.text.StringEscapeUtils;

public class ProcessadorHTML {

	public static String processarHTML(String textoArquivo) {
		String textoProcessado = textoArquivo;

		//Fazer unescape de todos espaços
		textoProcessado = textoProcessado.replaceAll("&nbsp;", " ");
		
		//Fazer unescape de entidades HTML especiais
		textoProcessado = StringEscapeUtils.unescapeHtml4(textoProcessado);			
				
		//Transformar marcações de quebra de linha de tabela em quebra de linha
		textoProcessado = textoProcessado.replaceAll("</tr>", "\r\n");
		textoProcessado = textoProcessado.replaceAll("<tr[^>]*>", "");
		
		//Remover o início do arquivo
		textoProcessado = textoProcessado.replaceFirst("<html.+\\s+<tbody>", "\r\n  ");
		
		//Transformar marcações de entrada da coluna por separadores |
		textoProcessado = textoProcessado.replaceAll("<td[^>]*>", "|");
		textoProcessado = textoProcessado.replaceAll("</td[^>]*>", "");
		
		//Limpar tudo o que estiver entre | e <nobr
		textoProcessado = textoProcessado.replaceAll("\\|[^|]*?<nobr", "<nobr");
		
		//<nobr> representa um valor individual, condensar tudo entre pipes
		textoProcessado = textoProcessado.replaceAll("<nobr[^>]*>", "|");
		textoProcessado = textoProcessado.replaceAll("\\s*</nobr[^>]*>", "|");
		textoProcessado = textoProcessado.replaceAll("\\|\\s*<[^>]*>\\h*\\|?", "|");
		
		//Transformar todo duplo pipe em um pipe só
		textoProcessado = textoProcessado.replaceAll("\\|{2}", "|");
		
		//Apagar todas linhas de cabeçalho definidas pela tag <tbody>
		textoProcessado = textoProcessado.replaceAll(".+tbody.+\\s+", "");
		
		//Apagar até o ID-programa
		textoProcessado = textoProcessado.replaceAll("(?<=\\n)\\s*\\|([^|]*\\|){2}", "|");
		
		//Apagar coluna da Ordem
		textoProcessado = textoProcessado.replaceAll("(?<=\\n\\|\\w{4}\\|)[^|]+\\|", "");		
		 
		//Simplificar o texto da observação
		textoProcessado = textoProcessado.replaceAll("Objeto n.o inclu.do.+", "Dependencia necessaria");
		
		//Apagar a primeira linha vazia
		textoProcessado = textoProcessado.replaceFirst("\\r\\n", "");
		
		return textoProcessado;
	}
}
