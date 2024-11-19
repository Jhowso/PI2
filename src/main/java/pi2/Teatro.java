package pi2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Teatro {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Ingresso> ingressos = new ArrayList<>();
    JFrame tela = new JFrame();

    String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    int[][] assentos = {
            {5, 5},   // Plateia A com 5 linhas e 5 colunas totalizando 25 assentos
            {10, 10}, // Plateia B com 10 linhas e 10 colunas
            {6, 5},   // 6 frisas e 5 assentos em cada frisa
            {5, 10},  // 5 camarotes e 10 assentos em cada camarote
            {5, 10}   // Balcão Nobre com 10 linhas e 5 colunas
    };
    private double[] preco = {40.0, 60.0, 80.0, 120.0, 250.0};


    public void menuPrincipal() {
        String nome, cpf, telefone, endereco, dataNascimento;
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

        botaoCadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame telaCadastro = new JFrame("Cadastro Usuário");

                telaCadastro.setLayout(new BoxLayout(telaCadastro.getContentPane(), BoxLayout.Y_AXIS));
                telaCadastro.setSize(850, 300);

                String[] textosCadastro = {"Nome: ", "CPF: ", "Telefone: ", "Endereço: ", "Data de Nascimento: "};
                JLabel[] labelsCadastro = new JLabel[5];
                JTextField[] textFieldsCadastro = new JTextField[5];
                JButton salvarButton = new JButton("Salvar");

                for (int i = 0; i < 5; i++) {
                    labelsCadastro[i] = new JLabel(textosCadastro[i]);
                    textFieldsCadastro[i] = new JTextField(20);

                    labelsCadastro[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                    textFieldsCadastro[i].setAlignmentX(Component.CENTER_ALIGNMENT);

                    telaCadastro.add(labelsCadastro[i]);
                    telaCadastro.add(textFieldsCadastro[i]);
                }

                telaCadastro.add(Box.createVerticalStrut(20));
                salvarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                telaCadastro.add(salvarButton);

                salvarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            try{
                            if (textFieldsCadastro[0].getText() == null || textFieldsCadastro[0].getText().isEmpty() ||
                                textFieldsCadastro[1].getText() == null || textFieldsCadastro[1].getText().isEmpty() ||
                                textFieldsCadastro[2].getText() == null || textFieldsCadastro[2].getText().isEmpty() ||
                                textFieldsCadastro[3].getText() == null || textFieldsCadastro[3].getText().isEmpty() ||
                                textFieldsCadastro[4].getText() == null || textFieldsCadastro[4].getText().isEmpty()){
                                throw new Erros("Os campos não podem ser vazios!");
                            }
                                else{
                                    String nome = textFieldsCadastro[0].getText();
                                    String cpf = textFieldsCadastro[1].getText();
                                    String telefone = textFieldsCadastro[2].getText();
                                    String endereco = textFieldsCadastro[3].getText();
                                    String dataNascimento = textFieldsCadastro[4].getText();

                                    cadastrarUsuario(nome, cpf, telefone, endereco, dataNascimento);
                                    }
                            }
                            catch (Erros ex){
                                JOptionPane.showMessageDialog(telaCadastro, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }

                    }
                });
                telaCadastro.setVisible(true);
            }
        });
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }

    public void cadastrarUsuario(String nome, String cpf, String telefone, String endereco, String dataNascimento) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                JOptionPane.showMessageDialog(null, "Usuário já está cadastrado");
                return;
            }
        }
        try{
            validarCPF(cpf);
            usuarios.add(new Usuario(nome, cpf, telefone, endereco, dataNascimento));
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        }catch (Erros e){
            JOptionPane.showMessageDialog(null, "CPF inválido: " + e.getMessage());
        }
    }

    public static void validarCPF(String cpf) throws Erros {

        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            throw new Erros("CPF deve ter 11 dígitos.");
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            throw new Erros("CPF não pode ter todos os dígitos iguais.");
        }
    }
}



    /*
    public void exibirAssentos(String[] areas, int[][] assentos) {
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamento ao fechar a janela
        tela.setLayout(new java.awt.GridLayout(0, 10)); // Layout da janela (ajustável)
        tela.setSize(800, 600); // Tamanho da janela
        tela.setTitle("Mapa de Assentos - Teatro ABC");

        for (int i = 0; i < areas.length; i++) {
            JLabel textArea = new JLabel("Selecione seu assento em: " + areas[i]);
            tela.add(textArea); // Adiciona o nome da área

            int linhas = assentos[i][0]; // Número de linhas para a área atual
            int colunas = assentos[i][1]; // Número de colunas para a área atual
            JButton[][] botaoAssentos = new JButton[linhas][colunas]; // Matriz de botões

            for (int linha = 0; linha < linhas; linha++) {
                for (int coluna = 0; coluna < colunas; coluna++) {
                    botaoAssentos[linha][coluna] = new JButton("L"); // Cria botão

                }
            }
        }
    }*/







