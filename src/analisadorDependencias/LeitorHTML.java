package analisadorDependencias;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LeitorHTML {
	
	public static String lerDependencias() {
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos HTML", "html"));
        int escolha = fileChooser.showOpenDialog(null);

        if (escolha != JFileChooser.APPROVE_OPTION) {
        	return null;
        }
        
        String caminhoArquivo = fileChooser.getSelectedFile().getPath();
        
        if (caminhoArquivo == null) {
            JOptionPane.showMessageDialog(null, "Caminho do arquivo é nulo.");
            return null;
        }
		
		try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            StringBuilder stringBuilder = new StringBuilder();
			String linha;

            while ((linha = reader.readLine()) != null) {
                stringBuilder.append(linha).append("\r\n");
            }

            String textoArquivo = stringBuilder.toString();
            
            return textoArquivo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Um erro aconteceu ao ler arquivo: " + e.getMessage());
            return null;
        }
	}
	
	
}
