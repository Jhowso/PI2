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
        tela.setSize(600, 500);
        tela.setLocationRelativeTo(null);
        ImageIcon icone = new ImageIcon("iconeTeatro.png");
        Image imagemIcone = icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        tela.setIconImage(imagemIcone);

        JLabel text = new JLabel("Bem-vindo ao Teatro ABC!");

        JLabel imagemLabel = new JLabel();
        try {
            ImageIcon imagem = new ImageIcon("layoutTeatro.jpg");
            Image scaledImage = imagem.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imagemLabel.setText("Imagem não encontrada");
            imagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JButton botaoCadastroButton = new JButton("Cadastrar usuário");
        JButton compraIngressoButton = new JButton("Comprar ingresso");
        JButton imprimirIngressoButton = new JButton("Imprimir ingresso");
        JButton estatisticaVendasButton = new JButton("Estatística de Vendas");

        // Alinhando todos os componentes horizontalmente no centro
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoCadastroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        compraIngressoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imprimirIngressoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        estatisticaVendasButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Criando um painel para agrupar os botões, de forma a centralizá-los
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adicionando os botões ao painel
        painelBotoes.add(Box.createVerticalStrut(20));
        painelBotoes.add(botaoCadastroButton);
        painelBotoes.add(Box.createVerticalStrut(20));
        painelBotoes.add(compraIngressoButton);
        painelBotoes.add(Box.createVerticalStrut(20));
        painelBotoes.add(imprimirIngressoButton);
        painelBotoes.add(Box.createVerticalStrut(20));
        painelBotoes.add(estatisticaVendasButton);

        // Adicionando os componentes principais na tela
        tela.add(text);
        tela.add(Box.createVerticalStrut(20));
        tela.add(imagemLabel);
        tela.add(Box.createVerticalStrut(20));
        tela.add(painelBotoes);

        botaoCadastroButton.addActionListener(e -> MenuCadastro.abrirTelaCadastro());
        compraIngressoButton.addActionListener(e -> menuCompra.abrirTelaCompra(cpf));
        imprimirIngressoButton.addActionListener(e -> menuImpressaoIngresso.abrirTelaImpressaoIngresso());
        estatisticaVendasButton.addActionListener(e -> MenuEstatistica.abrirTelaEstatisticas());

        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }

}
