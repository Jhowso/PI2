package pi2;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal {
    MenuCompra menuCompra = new MenuCompra();
    MenuImpressaoIngresso menuImpressaoIngresso = new MenuImpressaoIngresso();
    private String cpf;

    public void menuPrincipal() {
        JFrame tela = new JFrame();
        Usuario.carregarUsuarios();
        Ingresso.carregarIngressos();

        tela.setTitle("Menu Principal");
        tela.setSize(600, 600);
        tela.setLocationRelativeTo(null);

        JLabel imagemLabel = new JLabel();
        try {
            ImageIcon imagem = new ImageIcon("layoutTeatro.jpg");
            Image scaledImage = imagem.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imagemLabel.setText("Imagem não encontrada");
            imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JLabel text = new JLabel("Bem-vindo ao Teatro ABC!");
        text.setHorizontalAlignment(SwingConstants.CENTER);

        JButton botaoCadastroButton = new JButton("Cadastrar usuário");
        JButton compraIngressoButton = new JButton("Comprar ingresso");
        JButton imprimirIngressoButton = new JButton("Imprimir ingresso");
        JButton estatisticaVendasButton = new JButton("Estatística de Vendas");

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centralPanel.add(text);
        centralPanel.add(Box.createVerticalStrut(20));
        centralPanel.add(imagemLabel);
        centralPanel.add(Box.createVerticalStrut(20));
        centralPanel.add(botaoCadastroButton);
        centralPanel.add(Box.createVerticalStrut(20));
        centralPanel.add(compraIngressoButton);
        centralPanel.add(Box.createVerticalStrut(20));
        centralPanel.add(imprimirIngressoButton);
        centralPanel.add(Box.createVerticalStrut(20));
        centralPanel.add(estatisticaVendasButton);

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.add(centralPanel);

        tela.add(contentPanel);

        botaoCadastroButton.addActionListener(e -> MenuCadastro.abrirTelaCadastro());
        compraIngressoButton.addActionListener(e -> menuCompra.abrirTelaCompra(cpf));
        imprimirIngressoButton.addActionListener(e -> menuImpressaoIngresso.abrirTelaImpressaoIngresso());
        estatisticaVendasButton.addActionListener(e -> MenuEstatistica.abrirTelaEstatisticas());

        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
