package analisadorDependencias.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import analisadorDependencias.controle.Controller;
import analisadorDependencias.core.LeitorArquivo;
import analisadorDependencias.core.ProcessadorTexto;

@SuppressWarnings("serial")
public class View extends JFrame {
	private JButton jButtonLerDependencias;
	private JButton jButtonLerObjetos;
	private JButton jButtonReiniciarDados;
	private JTextArea jTextAreaDependencias;
	private JTextArea JTextAreaComparacao;
	private JScrollPane jScrollPaneDependencias;
	private JScrollPane jScrollPaneComparacao;
	private JLabel jLabelMensagens;
	private JLabel jLabelDependencias;
	private JLabel jLabelComparacao;

	private final int LARGURA_JANELA = 900;
	private final int ALTURA_JANELA = 600;
	private final int ALTURA_BOTAO = 30;
	private final int ALTURA_LABEL = 30;
	private final int GAP = 15;

	private final int LARGURA_TEXT_AREA = (LARGURA_JANELA - 3 * GAP) / 2;
	private final int ALTURA_TEXT_AREA = ALTURA_JANELA - ALTURA_BOTAO - 2 * ALTURA_LABEL - 5 * GAP;

	private boolean sucesso = true;

	public View() {
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(LARGURA_JANELA, ALTURA_JANELA));
		setTitle("Analisador de Dependências");

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
		JTextAreaComparacao = new JTextArea();
		jLabelMensagens = new JLabel("Mensagens serão exibidas aqui.");
		jLabelDependencias = new JLabel("O nome das dependências");
		jLabelComparacao = new JLabel("As dependências faltantes");
		jScrollPaneDependencias = new JScrollPane(jTextAreaDependencias);
		jScrollPaneComparacao = new JScrollPane(JTextAreaComparacao);
	}

	private void definirComponentes() {

		jTextAreaDependencias.setEditable(false);
		JTextAreaComparacao.setEditable(false);

		jButtonLerObjetos.setEnabled(false);
		jButtonReiniciarDados.setEnabled(false);

		jLabelMensagens.setBounds(GAP, GAP, LARGURA_JANELA - GAP * 2, ALTURA_LABEL);
		jLabelMensagens.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMensagens.setBackground(Color.WHITE);
		jLabelMensagens.setOpaque(true);

		int x = GAP;
		int y = ALTURA_LABEL + 2 * GAP;
		jLabelDependencias.setBounds(x, y, LARGURA_TEXT_AREA, ALTURA_LABEL);
		jLabelDependencias.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelDependencias.setBackground(Color.WHITE);
		jLabelDependencias.setOpaque(true);

		x += GAP + LARGURA_TEXT_AREA;
		jLabelComparacao.setBounds(x, y, LARGURA_TEXT_AREA, ALTURA_LABEL);
		jLabelComparacao.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelComparacao.setBackground(Color.WHITE);
		jLabelComparacao.setOpaque(true);

		jScrollPaneDependencias.setViewportView(jTextAreaDependencias);
		x = GAP;
		y = 2 * ALTURA_LABEL + 3 * GAP;
		jScrollPaneDependencias.setBounds(x, y, LARGURA_TEXT_AREA, ALTURA_TEXT_AREA);

		jScrollPaneComparacao.setViewportView(JTextAreaComparacao);
		x += GAP + LARGURA_TEXT_AREA;
		jScrollPaneComparacao.setBounds(x, y, LARGURA_TEXT_AREA, ALTURA_TEXT_AREA);

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

	private void adicionarComponentes() {
		add(jLabelMensagens);
		add(jLabelDependencias);
		add(jLabelComparacao);
		add(jScrollPaneDependencias);
		add(jScrollPaneComparacao);
		add(jButtonLerDependencias);
		add(jButtonLerObjetos);
		add(jButtonReiniciarDados);
	}

	// =====================PARTE LÓGICA DA VIEW==========================

	private void adicionarListeners() {
		jButtonLerDependencias.addActionListener(e -> lerDependencias());
		jButtonLerObjetos.addActionListener(e -> lerObjetosAmbiente());
		jButtonReiniciarDados.addActionListener(e -> reiniciarDados());
	}

	private void lerDependencias() {
		String texto = lerArquivo("html");

		if (texto == null) {
			return;
		}

		String textoProcessado = processarTexto(texto, "HTML");

		if (textoProcessado == null) {
			return;
		}

		try {
			Controller.getInstancia().armazenarDependencias(textoProcessado);
		} catch (Exception e) {
			exibirMensagem("Exceção ao armazenar dependências!");
			exibirPopUp("Exceção ao armazenar dependências: " + e.getMessage());
			return;
		}

		jTextAreaDependencias.setText(Controller.getInstancia().getDependenciasNomes());

		jButtonLerDependencias.setEnabled(false);
		jButtonLerObjetos.setEnabled(true);
	}

	private void lerObjetosAmbiente() {
		String texto = lerArquivo("txt");

		if (texto == null) {
			return;
		}

		String textoProcessado = processarTexto(texto, "TXT");

		if (textoProcessado == null) {
			return;
		}

		try {
			Controller.getInstancia().armazenarObjetosAmbiente(textoProcessado);
		} catch (Exception e) {
			exibirMensagem("Exceção ao armazenar objetos do ambiente!");
			exibirPopUp("Exceção ao armazenar objetos do ambiente: " + e.getMessage());
			return;
		}

		jButtonLerObjetos.setEnabled(false);
		jButtonReiniciarDados.setEnabled(true);

		String objetosFaltantes = Controller.getInstancia().compararObjetos();
		JTextAreaComparacao.setText(objetosFaltantes);
	}

	private void reiniciarDados() {
		Controller.getInstancia().reiniciarDados();
		jTextAreaDependencias.setText("");
		JTextAreaComparacao.setText("");

		jButtonReiniciarDados.setEnabled(false);
		jButtonLerDependencias.setEnabled(true);
	}

	private String lerArquivo(String extensao) {
		String texto;

		try {
			texto = LeitorArquivo.lerArquivo(extensao);
			if (texto.equals(LeitorArquivo.NENHUM_ARQUIVO)) {
				exibirMensagem("Nenhum arquivo selecionado!");
				return null;
			} else if (texto.equals(LeitorArquivo.NAO_ENCONTRADO)) {
				exibirMensagem("Nenhum arquivo encontrado!");
				return null;
			}

		} catch (Exception e) {
			exibirMensagem("Exceção ao ler " + extensao.toUpperCase() + "!");
			exibirPopUp("Exceção ao ler " + extensao.toUpperCase() + ": " + e.getMessage());
			return null;
		}

		return texto;
	}

	private String processarTexto(String texto, String extensao) {
		String textoProcessado = "";

		try {
			textoProcessado = ProcessadorTexto.processar(texto, extensao);
			if (!sucesso) {
				exibirMensagem("Erro ao processar " + extensao + "!");
				return null;
			}
		} catch (Exception e) {
			exibirMensagem("Exceção ao processar " + extensao + "!");
			exibirPopUp("Exceção ao processar " + extensao + ": " + e.getMessage());
			return null;
		}

		return textoProcessado;
	}

	public void exibirMensagem(String texto) {
		jLabelMensagens.setText(texto);
	}

	public void exibirPopUp(String texto) {
		JOptionPane.showMessageDialog(null, texto);
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
}