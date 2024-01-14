package analisadorDependencias;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class View extends JFrame {
    private JButton jButtonLerDependencias;
    private JButton jButtonLerObjetos;
    private JButton jButtonReiniciarDados;
    private JTextArea jTextAreaDependencias;
    private JTextArea jTextAreaObjetos;
    private JLabel jLabelMensagens;
    
    private final int LARGURA_JANELA = 600;
    private final int ALTURA_JANELA = 600;
    private final int ALTURA_BOTAO = 30;
    private final int ALTURA_LABEL = 30;
    private final int GAP = 15;
    
	private final int LARGURA_TEXT_AREA = (LARGURA_JANELA - 3 * GAP) / 2;
	private final int ALTURA_TEXT_AREA = ALTURA_JANELA - ALTURA_BOTAO - ALTURA_LABEL - GAP * 4;    


    public View() {
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(LARGURA_JANELA, ALTURA_JANELA));
        
        criarComponentes();
		definirComponentes();
		adicionarListeners();
        adicionarComponentes();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void criarComponentes() {
        jButtonLerDependencias = new JButton("Ler Dependências");
        jButtonLerObjetos = new JButton("Ler objetos do ambiente");
        jButtonReiniciarDados = new JButton("Reiniciar dados");
        jTextAreaDependencias = new JTextArea();
        jTextAreaObjetos = new JTextArea();
        jLabelMensagens = new JLabel("Mensagens serão exibidas aqui.");	
	}
    
    private void definirComponentes() {
    	
		jLabelMensagens.setBounds(GAP, GAP, LARGURA_JANELA - GAP * 2, ALTURA_LABEL);
		jLabelMensagens.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMensagens.setBackground(Color.WHITE);
		jLabelMensagens.setOpaque(true);

    	
    	jTextAreaDependencias.setBounds(GAP, ALTURA_LABEL + 2 * GAP, LARGURA_TEXT_AREA, ALTURA_TEXT_AREA);
		jTextAreaObjetos.setBounds(LARGURA_TEXT_AREA + 2 * GAP, ALTURA_LABEL + 2 * GAP, LARGURA_TEXT_AREA, ALTURA_TEXT_AREA);

    	setBotaoArea(jButtonLerDependencias, 0);
    	setBotaoArea(jButtonLerObjetos, 1);
    	setBotaoArea(jButtonReiniciarDados, 2);
    }
    
    private void setBotaoArea(JButton botaoAtual, int numero) {
    	int larguraBotao = (LARGURA_JANELA - 4 * GAP) / 3;
    	int x = GAP + (GAP + larguraBotao) * numero;
    	int y = ALTURA_JANELA - ALTURA_BOTAO - GAP;
    	
    	botaoAtual.setBounds(x, y, larguraBotao, ALTURA_BOTAO);
    }
    
    private void adicionarListeners() {
    	jButtonLerDependencias.addActionListener(e -> lerDependencias());
    }
    
    private void lerDependencias() {
		String texto = LeitorHTML.lerDependencias();
		String textoProcessado = ProcessadorHTML.processarHTML(texto);
		
		System.out.println(textoProcessado);
    }
    
    private void adicionarComponentes() {
    	add(jLabelMensagens);	
        add(jTextAreaDependencias);
        add(jTextAreaObjetos);
        add(jButtonLerDependencias);
        add(jButtonLerObjetos);
        add(jButtonReiniciarDados);
    }
}