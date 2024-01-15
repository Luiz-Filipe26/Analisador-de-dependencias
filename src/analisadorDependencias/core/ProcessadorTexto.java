package analisadorDependencias.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;

import analisadorDependencias.controle.Controller;

public class ProcessadorTexto {

	public static final String DEPENDENCIA_NECESSARIA = "Dependencia necessaria";

	public static String processar(String textoArquivo, String extensao) throws Exception {
		extensao = extensao.toUpperCase();

		if (extensao.equals("HTML")) {
			return processarHTML(textoArquivo);
		} else if (extensao.equals("TXT")) {
			return processarTxt(textoArquivo);
		}

		return null;
	}

	private static String processarHTML(String textoArquivo) throws Exception {
		String textoProcessado = textoArquivo;

		// Fazer unescape de todos espaços
		textoProcessado = textoProcessado.replaceAll("&nbsp;", " ");

		// Fazer unescape de entidades HTML especiais
		textoProcessado = StringEscapeUtils.unescapeHtml4(textoProcessado);

		// Transformar marcações de quebra de linha de tabela em quebra de linha
		textoProcessado = textoProcessado.replaceAll("</tr>", "\r\n");
		textoProcessado = textoProcessado.replaceAll("<tr[^>]*>", "");

		// Remover o início do arquivo
		textoProcessado = textoProcessado.replaceFirst("<html.+\\s+<tbody>", "\r\n  ");

		// Transformar marcações de entrada da coluna por separadores |
		textoProcessado = textoProcessado.replaceAll("<td[^>]*>", "|");
		textoProcessado = textoProcessado.replaceAll("</td[^>]*>", "");

		// Limpar tudo o que estiver entre | e <nobr
		textoProcessado = textoProcessado.replaceAll("\\|[^|]*?<nobr", "<nobr");

		// <nobr> representa um valor individual, condensar tudo entre pipes
		textoProcessado = textoProcessado.replaceAll("<nobr[^>]*>", "|");
		textoProcessado = textoProcessado.replaceAll("\\s*</nobr[^>]*>", "|");
		textoProcessado = textoProcessado.replaceAll("\\|\\s*<[^>]*>\\h*\\|?", "|");

		// Transformar todo duplo pipe em um pipe só
		textoProcessado = textoProcessado.replaceAll("\\|{2}", "|");

		// Apagar todas linhas de cabeçalho definidas pela tag <tbody>
		textoProcessado = textoProcessado.replaceAll(".+tbody.+\\s+", "");

		// Apagar até o ID-programa
		textoProcessado = textoProcessado.replaceAll("(?<=\\n)\\s*\\|([^|]*\\|){2}", "|");

		// Apagar coluna da Ordem
		textoProcessado = textoProcessado.replaceAll("(?<=\\n\\|\\w{4}\\|)[^|]+\\|", "");

		// Simplificar o texto da observação
		textoProcessado = textoProcessado.replaceAll("Objeto n.o inclu.do.+", DEPENDENCIA_NECESSARIA + "|");

		// Apagar a primeira linha vazia
		textoProcessado = textoProcessado.replaceFirst("\\r\\n", "");

		boolean valido = validarResultadoDependencias(textoProcessado);

		Controller.getInstancia().setSucesso(valido);

		return textoProcessado;

	}

	private static boolean validarResultadoDependencias(String texto) throws Exception {
		long quantidadeDeLinhas = texto.lines().count();

		texto = "\n" + texto + "\n";

		Pattern pattern = Pattern.compile("(?<=\\n)\\|\\w{4}\\|([^|]*\\|){2}(\\r?)\\n");

		Matcher matcher = pattern.matcher(texto);

		long quantidadeDeCorrespondencias = matcher.results().count();

		return quantidadeDeCorrespondencias == quantidadeDeLinhas;
	}

	private static String processarTxt(String texto) throws Exception {
		String textoProcessado = "\n" + texto;

		// Eliminar todas linhas que não tem pipes | divisores de coluna
		textoProcessado = textoProcessado.replaceAll("(?<=\\n)[^|]+(\\r?\\n)", "");

		// Apagar a primeira linha porque não contém valores de colunas
		textoProcessado = textoProcessado.replaceFirst(".+\\s+", "");

		// Apagar a primeira coluna de todas as linhas
		textoProcessado = textoProcessado.replaceAll("(?<=\\n)\\|[^|]+", "");

		// Apagar todas colunas de todas as linhas que não forem as 2 primeiras
		textoProcessado = textoProcessado.replaceAll("([^|]+\\|){19}(\\r)?\\n", "\r\n");

		// Apagar todos espaços em branco
		textoProcessado = textoProcessado.replaceAll("\\h+", "");
		textoProcessado = textoProcessado.trim();

		return textoProcessado;
	}
}
