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
        tela.setLayout(new BoxLayout(tela.getContentPane(), BoxLayout.Y_AXIS));
        tela.setSize(500, 400);
        tela.setLocationRelativeTo(null);

        JLabel text = new JLabel("Bem-vindo ao Teatro ABC!");
        JButton botaoCadastroButton = new JButton("Cadastrar usuário");
        JButton compraIngressoButton = new JButton("Comprar ingresso");
        JButton imprimirIngressoButton = new JButton("Imprimir ingresso");
        JButton estatisticaVendasButton = new JButton("Estatística de Vendas");

        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCadastroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        compraIngressoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imprimirIngressoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        estatisticaVendasButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        tela.add(text);
        tela.add(Box.createVerticalStrut(20));
        tela.add(botaoCadastroButton);
        tela.add(Box.createVerticalStrut(20));
        tela.add(compraIngressoButton);
        tela.add(Box.createVerticalStrut(20));
        tela.add(imprimirIngressoButton);
        tela.add(Box.createVerticalStrut(20));
        tela.add(estatisticaVendasButton);

        botaoCadastroButton.addActionListener(e -> MenuCadastro.abrirTelaCadastro());
        compraIngressoButton.addActionListener(e -> menuCompra.abrirTelaCompra(cpf));
        imprimirIngressoButton.addActionListener(e -> menuImpressaoIngresso.abrirTelaImpressaoIngresso());
        estatisticaVendasButton.addActionListener(e -> MenuEstatistica.abrirTelaEstatisticas());
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
