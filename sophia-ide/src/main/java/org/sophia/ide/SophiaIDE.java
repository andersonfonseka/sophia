package org.sophia.ide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.sophia.lexico.AnalisadorLexico;
import org.sophia.lexico.CategoriaSimbolo;
import org.sophia.lexico.Simbolo;

public class SophiaIDE extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextPane editor;
	private JTextPane saida;

	private JLabel status;

	private JTree projeto;
	private DefaultTreeModel modeloProjeto;

	private Path pastaAtual;

	private String arquivoAtual;

	private ExecutorSophia executor;

	private boolean atualizandoHighlighting = false;

	public SophiaIDE() {

		super("Sophia IDE");

		pastaAtual = Path.of(System.getProperty("user.dir"));

		inicializarComponentes();
		configurarLayout();

		setSize(1100, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void inicializarComponentes() {

		editor = new JTextPane();
		saida = new JTextPane();

		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> atualizarHighlighting());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> atualizarHighlighting());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		saida.setEditable(false);

		status = new JLabel("Pronto");

		projeto = criarArvoreProjeto();

		configurarFonteEditor();
	}

	private void configurarLayout() {

		setLayout(new BorderLayout());

		add(criarBarraFerramentas(), BorderLayout.NORTH);

		JScrollPane scrollProjeto = new JScrollPane(projeto);

		scrollProjeto.setPreferredSize(new Dimension(220, 0));

		JScrollPane scrollEditor = criarAreaPrincipal();

		JScrollPane scrollSaida = new JScrollPane(saida);

		JPanel painelSaida = new JPanel(new BorderLayout());

		painelSaida.add(scrollSaida, BorderLayout.CENTER);

		JSplitPane editorSaida = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, painelSaida);

		editorSaida.setResizeWeight(0.75);
		editorSaida.setResizeWeight(0.75);

		JSplitPane projetoEditor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollProjeto, editorSaida);

		projetoEditor.setResizeWeight(0.20);

		add(projetoEditor, BorderLayout.CENTER);

		add(status, BorderLayout.SOUTH);
	}

	private JToolBar criarBarraFerramentas() {

		JToolBar barra = new JToolBar();

		JButton novoPrograma = new JButton("Novo Programa");
		JButton criarProjeto = new JButton("Criar Projeto");
		JButton abrirPrograma = new JButton("Abrir Programa");
		JButton abrirProjeto = new JButton("Abrir Projeto");
		JButton salvar = new JButton("Salvar");
		JButton executar = new JButton("Executar");

		novoPrograma.addActionListener(e -> novoArquivo());
		criarProjeto.addActionListener(e -> criarProjeto());
		abrirPrograma.addActionListener(e -> abrirArquivo());
		abrirProjeto.addActionListener(e -> abrirProjeto());
		
		salvar.addActionListener(e -> salvarArquivo());
		executar.addActionListener(e -> executar());

		barra.add(novoPrograma);
		barra.add(criarProjeto);
		barra.add(abrirPrograma);
		barra.add(abrirProjeto);

		barra.addSeparator();

		barra.add(salvar);

		barra.addSeparator();

		barra.add(executar);

		return barra;
	}

	private void criarProjeto() {

		JFileChooser chooser = new JFileChooser(pastaAtual.toFile());

		chooser.setDialogTitle("Escolha onde criar o projeto");

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path local = chooser.getSelectedFile().toPath();

		String nomeProjeto = JOptionPane.showInputDialog(this, "Nome do projeto:", "Criar projeto Sophia",
				JOptionPane.PLAIN_MESSAGE);

		if (nomeProjeto == null) {
			return;
		}

		nomeProjeto = nomeProjeto.trim();

		if (nomeProjeto.isEmpty()) {

			JOptionPane.showMessageDialog(this, "Informe um nome para o projeto.", "Criar projeto",
					JOptionPane.WARNING_MESSAGE);

			return;
		}

		Path projeto = local.resolve(nomeProjeto);

		try {

			if (Files.exists(projeto)) {
				JOptionPane.showMessageDialog(this, "Já existe um projeto com esse nome.", "Criar projeto", JOptionPane.WARNING_MESSAGE);
				return;
			}

			Files.createDirectories(projeto);

			pastaAtual = projeto;
			arquivoAtual = null;

			editor.setText("");
			saida.setText("");

			atualizarArvoreProjeto();
			atualizarStatus("Projeto criado: " + nomeProjeto);
			editor.requestFocusInWindow();

		} catch (IOException e) {
			mostrarErro("Não foi possível criar o projeto.", e);
		}
	}

	private void novoArquivo() {

		editor.setText("");

		arquivoAtual = null;
		saida.setText("");

		atualizarStatus("Novo programa");
		editor.requestFocusInWindow();
	}

	private JTree criarArvoreProjeto() {

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(pastaAtual.toString());

		modeloProjeto = new DefaultTreeModel(raiz);

		JTree arvore = new JTree(modeloProjeto);

		carregarArquivos(raiz);

		arvore.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {

				if (e.getClickCount() != 2) {
					return;
				}

				javax.swing.tree.TreePath caminho = arvore.getPathForLocation(e.getX(), e.getY());

				if (caminho == null) {
					return;
				}

				Object objeto = ((DefaultMutableTreeNode) caminho.getLastPathComponent()).getUserObject();

				if (objeto instanceof Path arquivo) {
					abrirArquivoProjeto(arquivo.getFileName().toString());
				} else {
					
					File fl = new File(objeto.toString());
					
					if (!fl.isDirectory()) {
						abrirArquivoProjeto(objeto.toString());
					} else {
						pastaAtual = fl.toPath();
					}
				}
			}
		});
		
		return arvore;
	}

	private void abrirProjeto() {

		JFileChooser chooser = new JFileChooser(pastaAtual.toFile());

		chooser.setDialogTitle("Abrir projeto Sophia");

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path pasta = chooser.getSelectedFile().toPath();

		pastaAtual = pasta;

		carregarProjeto(pasta);
	}

	private void carregarProjeto(Path pasta) {

		pastaAtual = pasta;
		arquivoAtual = null;

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(pasta.toString());

		modeloProjeto.setRoot(raiz);

		carregarArquivosProjeto(raiz, pasta);

		modeloProjeto.reload();

		projeto.expandPath(new TreePath(raiz.getPath()));

		atualizarStatus("Projeto aberto: " + pasta.getFileName());
	}

	private void carregarArquivosProjeto(DefaultMutableTreeNode no, Path pasta) {

		try (Stream<Path> arquivos = Files.list(pasta)) {

			arquivos.sorted().forEach(arquivo -> {

				if (Files.isDirectory(arquivo)) {
					DefaultMutableTreeNode pastaNode = new DefaultMutableTreeNode(arquivo.getFileName().toString());
					no.add(pastaNode);
					carregarArquivosProjeto(pastaNode, arquivo);
					return;
				}

				if (arquivo.getFileName().toString().toLowerCase().endsWith(".sph")) {
					DefaultMutableTreeNode arquivoNode = new DefaultMutableTreeNode(arquivo.getFileName());
					arquivoNode.setAllowsChildren(false);
					no.add(arquivoNode);
				}
			});

		} catch (IOException e) {
			mostrarErro("Não foi possível carregar o projeto.", e);
		}
	}

	private void abrirArquivoProjeto(String nomeArquivo) {

		try {

			Path arquivo = Paths.get(pastaAtual.toString(), nomeArquivo);

			if (!Files.exists(arquivo)) {
				mostrarErro("Arquivo não encontrado.", new IOException(arquivo.toString()));
				return;
			}

			String codigo = Files.readString(arquivo, StandardCharsets.UTF_8);

			this.arquivoAtual = nomeArquivo;

			carregarCodigo(codigo);

			atualizarStatus("Arquivo aberto: " + arquivo.getFileName());

		} catch (Exception e) {

			mostrarErro("Não foi possível abrir o arquivo.", e);
		}
	}

	private void carregarArquivos(DefaultMutableTreeNode noExemplos) {

		Path pasta = pastaAtual;

		if (!Files.exists(pasta) || !Files.isDirectory(pasta)) {
			return;
		}

		try (Stream<Path> arquivos = Files.list(pasta)) {

			arquivos.filter(Files::isRegularFile)
					.filter(arquivo -> arquivo.getFileName().toString().toLowerCase().endsWith(".sph")).sorted()
					.forEach(arquivo -> {

						DefaultMutableTreeNode noArquivo = new DefaultMutableTreeNode(arquivo.getFileName().toString());
						noExemplos.add(noArquivo);
					});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JScrollPane criarAreaPrincipal() {

		JScrollPane editorScroll = new JScrollPane(editor);

		NumeracaoLinhas numeracao = new NumeracaoLinhas(editor);

		editorScroll.setRowHeaderView(numeracao);

		return editorScroll;
	}

	private void atualizarHighlighting() {

		if (atualizandoHighlighting) {
			return;
		}

		atualizandoHighlighting = true;

		try {
			destacarGramatica();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			atualizandoHighlighting = false;
		}
	}

	private void executar() {

		saida.setText("");

		String codigo = editor.getText();

		try {

			this.executor = new ExecutorSophia();
			Resultado resultado = executor.executar(codigo);

			if (resultado.isSucesso()) {
				saida.setText(this.executor.getSaida());
				this.executor.limpar();
			} else {

				String mensagem = resultado.getErro();
				saida.setText(mensagem);

				if (resultado.getLinha() > 0) {
					editor.setCaretPosition(editor.getDocument().getDefaultRootElement()
							.getElement(resultado.getLinha() - 1).getStartOffset());
				}
			}

			atualizarStatus("Executado com sucesso");

		} catch (Exception ex) {
			mostrarErro(ex);
		}
	}

	private void abrirArquivo() {

		JFileChooser chooser = new JFileChooser(pastaAtual.toFile());

		chooser.setDialogTitle("Abrir programa Sophia");
		chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Programas Sophia (*.sph)", "sph"));

		int resultado = chooser.showOpenDialog(this);

		if (resultado != JFileChooser.APPROVE_OPTION) {
			return;
		}

		try {

			Path arquivo = chooser.getSelectedFile().toPath();

			String codigo = Files.readString(arquivo, StandardCharsets.UTF_8);

			carregarCodigo(codigo);

			pastaAtual = arquivo;
			atualizarStatus("Arquivo aberto: " + arquivo.getFileName());

		} catch (Exception e) {
			mostrarErro("Não foi possível abrir o arquivo.", e);
		}
	}

	private void carregarCodigo(String codigo) {
		editor.setText(codigo);
		destacarGramatica();
	}

	private void salvarArquivo() {

		if (arquivoAtual == null) {
			salvarComo();
			return;
		}

		try {

			Path arquivo = Path.of(pastaAtual.toString() + "/" + arquivoAtual);

			Files.writeString(arquivo, editor.getText(), StandardCharsets.UTF_8);
			atualizarStatus("Arquivo salvo: " + pastaAtual.getFileName());

			atualizarArvoreProjeto();

		} catch (Exception e) {

			mostrarErro("Não foi possível salvar o arquivo.", e);
		}
	}

	private void atualizarArvoreProjeto() {

		if (pastaAtual == null || !Files.isDirectory(pastaAtual)) {
			return;
		}

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(pastaAtual.getFileName().toString());
		carregarArquivosProjeto(raiz, pastaAtual);

		modeloProjeto.setRoot(raiz);
		modeloProjeto.reload();

		projeto.expandPath(new TreePath(raiz.getPath()));
	}

	private void salvarComo() {

		JFileChooser chooser = new JFileChooser(pastaAtual.toFile());

		chooser.setDialogTitle("Salvar programa Sophia");

		chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Programas Sophia (*.sph)", "sph"));

		int resultado = chooser.showSaveDialog(this);

		if (resultado != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path arquivo = chooser.getSelectedFile().toPath();

		if (!arquivo.toString().toLowerCase().endsWith(".sph")) {
			arquivo = Path.of(arquivo.toString() + ".sph");
		}

		pastaAtual = arquivo.getParent();
		arquivoAtual = arquivo.toString().substring(arquivo.toString().lastIndexOf("\\") + 1);

		salvarArquivo();
	}

	private void atualizarStatus(String mensagem) {
		status.setText(" " + mensagem);
	}

	private void mostrarErro(String mensagem, Exception e) {

		saida.setText(mensagem + "\n\n" + e.getMessage());

		atualizarStatus("Erro");
	}

	private void mostrarErro(Exception e) {
		String mensagem = e.getMessage();
		localizarErroNoEditor(mensagem);
		saida.setText("ERRO\n\n" + mensagem);
		atualizarStatus("Erro");
	}

	private void localizarErroNoEditor(String mensagem) {

		if (mensagem == null) {
			return;
		}

		Pattern pattern = Pattern.compile("linha\\s+(\\d+),\\s*coluna\\s+(\\d+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(mensagem);

		if (!matcher.find()) {
			return;
		}
	}

	private void destacarGramatica() {

		StyledDocument documento = editor.getStyledDocument();

		String codigo = editor.getText();

		documento.setCharacterAttributes(0, documento.getLength(), estiloPadrao(), true);

		try {

			AnalisadorLexico lexico = new AnalisadorLexico();
			List<Simbolo> simbolos = lexico.analisar(codigo);

			for (Simbolo simbolo : simbolos) {
				if (simbolo.getCategoria() == CategoriaSimbolo.FIM_DO_ARQUIVO) {
					continue;
				}
				destacarSimbolo(simbolo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SimpleAttributeSet estiloPadrao() {

		SimpleAttributeSet estilo = new SimpleAttributeSet();

		StyleConstants.setFontFamily(estilo, Font.MONOSPACED);
		StyleConstants.setFontSize(estilo, 16);
		StyleConstants.setForeground(estilo, Color.BLACK);

		return estilo;
	}

	private void destacarSimbolo(Simbolo simbolo) {

		AttributeSet estilo = estiloPara(simbolo.getCategoria());

		if (estilo == null) {
			return;
		}

		int inicio = obterOffset(simbolo.getLinha(), simbolo.getColuna());

		int tamanho = simbolo.getTexto().length();

		if (inicio < 0 || inicio + tamanho > editor.getDocument().getLength()) {
			return;
		}

		editor.getStyledDocument().setCharacterAttributes(inicio, tamanho, estilo, false);
	}

	private AttributeSet estiloPara(CategoriaSimbolo categoria) {

		SimpleAttributeSet estilo = new SimpleAttributeSet();

		switch (categoria) {

		case ESTRUTURA:

			StyleConstants.setForeground(estilo, new Color(130, 0, 180));
			StyleConstants.setBold(estilo, true);
			return estilo;

		case TIPO:

			StyleConstants.setForeground(estilo, new Color(0, 100, 180));
			StyleConstants.setBold(estilo, true);
			return estilo;

		case COMANDO:

			StyleConstants.setForeground(estilo, new Color(0, 130, 70));
			StyleConstants.setBold(estilo, true);
			return estilo;

		case OPERADOR:
		case COMPARACAO:

			StyleConstants.setForeground(estilo, new Color(180, 80, 0));
			return estilo;

		case LITERAL:
		case LITERAL_TEXTO:
		case LITERAL_NUMERO:
		case LITERAL_LOGICO:

			StyleConstants.setForeground(estilo, new Color(0, 100, 180));
			return estilo;

		case COMENTARIO:

			StyleConstants.setForeground(estilo, Color.GRAY);
			StyleConstants.setItalic(estilo, true);
			return estilo;

		default:
			return null;
		}
	}

	private int obterOffset(int linha, int coluna) {

		Element raiz = editor.getDocument().getDefaultRootElement();

		Element elementoLinha = raiz.getElement(linha - 1);

		if (elementoLinha == null) {
			return -1;
		}

		return elementoLinha.getStartOffset() + coluna - 1;
	}

	private void configurarFonteEditor() {

		Font fonteEditor = new Font(Font.MONOSPACED, Font.PLAIN, 16);

		editor.setMargin(new Insets(8, 10, 8, 10));
		editor.setCaretColor(Color.BLACK);
		editor.setFont(fonteEditor);


		Font fonteSaida = new Font(Font.MONOSPACED, Font.PLAIN, 16);

		saida.setMargin(new Insets(8, 10, 8, 10));
		saida.setCaretColor(Color.BLACK);
		saida.setFont(fonteSaida);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			SophiaIDE ide = new SophiaIDE();
			ide.setVisible(true);
		});
	}

}