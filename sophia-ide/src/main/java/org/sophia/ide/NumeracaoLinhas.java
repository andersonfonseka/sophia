package org.sophia.ide;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import java.awt.*;

public class NumeracaoLinhas extends JComponent {

    private final JTextPane editor;

    public NumeracaoLinhas(JTextPane editor) {

        this.editor = editor;

        setFont(editor.getFont());

        editor.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        repaint();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        repaint();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        repaint();
                    }
                }
        );

        editor.addCaretListener(e -> repaint());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        FontMetrics metrics =
                g.getFontMetrics(editor.getFont());

        int alturaLinha =
                metrics.getHeight();

        int primeiraLinha =
                editor.getInsets().top;

        Element elementoRaiz =
                editor.getDocument().getDefaultRootElement();

        int quantidadeLinhas =
                elementoRaiz.getElementCount();

        int largura =
                getWidth();

        for (int i = 0; i < quantidadeLinhas; i++) {

            int y =
                    primeiraLinha
                    + (i + 1) * alturaLinha
                    - metrics.getDescent();

            String numero =
                    String.valueOf(i + 1);

            int larguraNumero =
                    metrics.stringWidth(numero);

            g.drawString(
                    numero,
                    largura - larguraNumero - 8,
                    y
            );
        }
    }

    @Override
    public Dimension getPreferredSize() {

        FontMetrics metrics =
                getFontMetrics(editor.getFont());

        int linhas =
                editor.getDocument()
                      .getDefaultRootElement()
                      .getElementCount();

        int largura =
                metrics.stringWidth(
                        String.valueOf(linhas)
                ) + 20;

        return new Dimension(
                largura,
                editor.getHeight()
        );
    }
}