package analisadorDependencias.core;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LeitorArquivo {
	
	private static int TAMANHO_STRINGBUILDER_HTML = 2600000;
	private static int TAMANHO_STRINGBUILDER_TXT = 60000;
	
	public static String NENHUM_ARQUIVO = "nenhum arquivo escolhido";
	public static String NAO_ENCONTRADO = "caminho nao encontrado";
	
	public static String lerArquivo(String extensaoArquivo) throws Exception {
		if(!extensaoArquivo.equals("html") && !extensaoArquivo.equals("txt")) {
			return null;
		}
		
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos " + extensaoArquivo.toUpperCase(), extensaoArquivo));
        int escolha = fileChooser.showOpenDialog(null);

        if (escolha != JFileChooser.APPROVE_OPTION) {
        	return NENHUM_ARQUIVO;
        }
        
        String caminhoArquivo = fileChooser.getSelectedFile().getPath();
        
        if (caminhoArquivo == null) {
            return NAO_ENCONTRADO;
        }
		
		try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
			int tamanhoStringBuilder = extensaoArquivo.equals("html") ? TAMANHO_STRINGBUILDER_HTML : TAMANHO_STRINGBUILDER_TXT;
					
            StringBuilder stringBuilder = new StringBuilder(tamanhoStringBuilder);
			String linha;

            while ((linha = reader.readLine()) != null) {
                stringBuilder.append(linha).append("\r\n");
            }

            String textoArquivo = stringBuilder.toString();
            
            return textoArquivo;
        } catch (Exception e) {
        	throw e;
        }
	}
}
