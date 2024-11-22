package pi2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Teatro {
    JFrame tela = new JFrame();
    private static final JPanel resultadoPanel = new JPanel();
    static boolean[][] assentosPreSelecionados;
    final static String[] sessoes = {"Platéia A", "Platéia B", "Camarotes", "Frisas", "Balcão Nobre"}; // Platéia A = 0; Platéia B = 1; Frisas = 2; Camarotes = 3; Balcão Nobre = 4;
    final static int[][] qtdAssentos = {
            {5, 5},   // Plateia A com 5 linhas e 5 colunas totalizando 25 assentos
            {10, 10}, // Plateia B com 10 linhas e 10 colunas totalizando 100 assentos
            {6, 5},   // 6 frisas e 5 assentos em cada frisa totalizando 30 assentos
            {5, 10},  // 5 camarotes e 10 assentos em cada camarote totalizando 50 assentos
            {5, 10}   // Balcão Nobre com 10 linhas e 5 colunas totalizando 50 assentos
    };
    final static String[] nomePecas = {"Lago dos Cisnes", "Chapeuzinho Vermelho", "Moby-Dick"};
    final static String[] nomeHorario = {"Manhã", "Tarde", "Noite"};
    final static double[] preco = {
            40.0,
            60.0,
            80.0,
            120.0,
            250.0
    };
    static int linhas, colunas;
    static boolean[][] assentosReservados = new boolean[linhas][colunas];

    public void menuPrincipal() {
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


        botaoCadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame telaCadastro = new JFrame("Cadastro Usuário");

                telaCadastro.setLayout(new BoxLayout(telaCadastro.getContentPane(), BoxLayout.Y_AXIS));
                telaCadastro.setSize(600, 300);

                String[] textosCadastro = {"Nome: ", "CPF: ", "Telefone: ", "Endereço: ", "Data de Nascimento: "};
                JLabel[] labelsCadastro = new JLabel[5];
                JTextField[] textFieldsCadastro = new JTextField[5];
                JButton salvarButton = new JButton("Salvar");

                for (int i = 0; i < 5; i++) {
                    labelsCadastro[i] = new JLabel(textosCadastro[i]);
                    textFieldsCadastro[i] = new JTextField(20);

                    labelsCadastro[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                    textFieldsCadastro[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                    textFieldsCadastro[i].setMaximumSize(new Dimension(400, 20));

                    telaCadastro.add(labelsCadastro[i]);
                    telaCadastro.add(textFieldsCadastro[i]);
                }

                telaCadastro.add(Box.createVerticalStrut(20));
                salvarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                telaCadastro.add(salvarButton);

                salvarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (textFieldsCadastro[0].getText() == null || textFieldsCadastro[0].getText().isEmpty() ||
                                    textFieldsCadastro[1].getText() == null || textFieldsCadastro[1].getText().isEmpty() ||
                                    textFieldsCadastro[2].getText() == null || textFieldsCadastro[2].getText().isEmpty() ||
                                    textFieldsCadastro[3].getText() == null || textFieldsCadastro[3].getText().isEmpty() ||
                                    textFieldsCadastro[4].getText() == null || textFieldsCadastro[4].getText().isEmpty()) {
                                throw new Erros("Os campos não podem ser vazios!");
                            } else {
                                String nome = textFieldsCadastro[0].getText();
                                String cpf = textFieldsCadastro[1].getText();
                                String telefone = textFieldsCadastro[2].getText();
                                String endereco = textFieldsCadastro[3].getText();
                                String dataNascimento = textFieldsCadastro[4].getText();

                                Usuario.cadastrarUsuario(nome, cpf, telefone, endereco, dataNascimento);
                            }
                        } catch (Erros ex) {
                            JOptionPane.showMessageDialog(telaCadastro, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                telaCadastro.setVisible(true);
            }
        });

        compraIngressoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame telaCompra = new JFrame("Compra de Ingressos");
                telaCompra.setLayout(new BorderLayout());
                telaCompra.setSize(1050, 600);

                JPanel panel = new JPanel();
                JTextField campocpf = new JTextField(15);

                panel.add(new JLabel("Digite seu CPF: "));
                panel.add(campocpf);

                int resultado = JOptionPane.showConfirmDialog(null, panel, "Compra de ingresso", JOptionPane.OK_CANCEL_OPTION);
                String cpf;

                if (resultado == JOptionPane.OK_OPTION) {
                    cpf = campocpf.getText();
                    try {
                        validarCPF(cpf);
                    } catch (Erros ex) {
                        JOptionPane.showMessageDialog(telaCompra, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    boolean usuarioEncontrado = false;
                    for (Usuario usuario : Usuario.usuarios) {
                        if (usuario.getCpf().equals(cpf)) {
                            usuarioEncontrado = true;
                            break;
                        }
                    }

                    if (usuarioEncontrado) {
                        JOptionPane.showMessageDialog(null, "Usuário já está cadastrado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário não está cadastrado. Realize o cadastro primeiro");
                        botaoCadastroButton.doClick();
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operação cancelada.");
                    return;
                } /*
                Fim das validações para entrar na compra do ingresso
                */

                JPanel principalPanel = new JPanel();
                principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.Y_AXIS));
                principalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Painel de seleção da peça
                JPanel pecaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel textPeca = new JLabel("Selecione a peça desejada:  ");
                pecaPanel.add(textPeca);
                String[] nomePecas = {"Lago dos Cisnes", "Chapeuzinho Vermelho", "Moby-Dick"};
                JRadioButton[] peca = new JRadioButton[3];
                ButtonGroup grupoPeca = new ButtonGroup();
                for (int i = 0; i < 3; i++) {
                    peca[i] = new JRadioButton(nomePecas[i]);
                    grupoPeca.add(peca[i]);
                    pecaPanel.add(peca[i]);
                }
                principalPanel.add(pecaPanel);

                // Painel de seleção do horário
                JPanel panelHorario = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel textHorario = new JLabel("Selecione o horário de preferência:");
                String[] nomeHorario = {"Manhã", "Tarde", "Noite"};
                ButtonGroup grupoHorario = new ButtonGroup();
                JRadioButton[] horario = new JRadioButton[3];
                panelHorario.add(textHorario);
                for (int i = 0; i < 3; i++) {
                    horario[i] = new JRadioButton(nomeHorario[i]);
                    grupoHorario.add(horario[i]);
                    panelHorario.add(horario[i]);
                }
                principalPanel.add(panelHorario);

                // Painel de seleção da sessão
                JPanel panelSessao = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel textSessao = new JLabel("Selecione a sessão de preferência: ");
                String[] nomeSessao = {"Plateia A R$ 40,00", "Plateia B R$ 60,00", "Camarotes R$ 80,00", "Frisas R$ 120,00", "Balcão Nobre R$ 250,00"};
                JRadioButton[] sessao = new JRadioButton[5];
                ButtonGroup grupoSessao = new ButtonGroup();
                panelSessao.add(textSessao);
                for (int i = 0; i < 5; i++) {
                    sessao[i] = new JRadioButton(nomeSessao[i]);
                    grupoSessao.add(sessao[i]);
                    panelSessao.add(sessao[i]);
                }
                principalPanel.add(panelSessao);

                JButton confirmaSelecaoButton = new JButton("Confirmar seleção");
                confirmaSelecaoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                principalPanel.add(confirmaSelecaoButton);

                telaCompra.add(principalPanel, BorderLayout.NORTH);
                telaCompra.add(resultadoPanel, BorderLayout.CENTER);

                confirmaSelecaoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (grupoPeca.getSelection() == null || grupoHorario.getSelection() == null || grupoSessao.getSelection() == null) {
                            JOptionPane.showMessageDialog(null, "Por favor, selecione peça, horário e sessão válidos.");
                        } else {
                            int pecaSelecionada = -1;
                            int horarioSelecionado = -1;
                            int sessaoSelecionada = -1;

                            // Verifica qual peça foi selecionada
                            for (int i = 0; i < peca.length; i++) {
                                if (peca[i].isSelected()) {
                                    pecaSelecionada = i;
                                    break;
                                }
                            }

                            // Verifica qual horário foi selecionado
                            for (int i = 0; i < horario.length; i++) {
                                if (horario[i].isSelected()) {
                                    horarioSelecionado = i;
                                    break;
                                }
                            }

                            // Verifica qual sessão foi selecionada
                            for (int i = 0; i < sessao.length; i++) {
                                if (sessao[i].isSelected()) {
                                    sessaoSelecionada = i;
                                    break;
                                }
                            }

                            // Exibe os assentos de acordo com a peça, horário e sessão selecionados
                            if (pecaSelecionada != -1 && horarioSelecionado != -1 && sessaoSelecionada != -1) {
                                exibirAssentos(resultadoPanel, qtdAssentos, pecaSelecionada, horarioSelecionado, sessaoSelecionada, cpf);
                                telaCompra.revalidate();
                                telaCompra.repaint();
                            }
                        }
                    }
                });
                telaCompra.setVisible(true);
            }
        });

        imprimirIngressoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });


        estatisticaVendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }

    public static void inicializarMatriz(int totalLinhas, int totalColunas) {
        linhas = totalLinhas;
        colunas = totalColunas;
        assentosReservados = new boolean[linhas][colunas];
    }

    public static void exibirAssentos(JPanel resultadoPanel, int[][] qtdAssentos, int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada, String cpf) {
        resultadoPanel.removeAll();
        resultadoPanel.setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("Peça: " + nomePecas[pecaSelecionada]));
        infoPanel.add(new JLabel("Horário: " + nomeHorario[horarioSelecionado]));
        infoPanel.add(new JLabel("Sessão: " + sessoes[sessaoSelecionada]));
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorFormatado = formatoMoeda.format(preco[sessaoSelecionada]);
        infoPanel.add(new JLabel("Valor: " + valorFormatado));
        infoPanel.add(new JLabel("Disposição dos Assentos:"));

        resultadoPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel assentosPanel = new JPanel();
        int linhas = qtdAssentos[sessaoSelecionada][0];
        int colunas = qtdAssentos[sessaoSelecionada][1];
        assentosPanel.setLayout(new GridLayout(linhas, colunas));

        inicializarMatriz(linhas, colunas);


        char letra = 'A';
        int cont = 1;
        for (int i = 0; i < linhas; i++) {
            if(sessaoSelecionada == 2){
                assentosPanel.add(new JLabel("Camarote " + cont));
            }

            if(sessaoSelecionada == 3){
                assentosPanel.add(new JLabel("Frisa " + cont));
            }
            for (int j = 0; j < colunas; j++) {

                String identificacao = letra + String.valueOf(j + 1);
                JButton botaoAssento = new JButton(identificacao);
                int linha = i;
                int coluna = j;

                // Verifica se o assento está reservado
                boolean reservado = false;
                for (Ingresso ingresso : Ingresso.ingressos) {
                    if (ingresso.getNomePeca().equals(nomePecas[pecaSelecionada]) &&
                            ingresso.getHorario().equals(nomeHorario[horarioSelecionado]) &&
                            ingresso.getSessao().equals(sessoes[sessaoSelecionada]) &&
                            ingresso.getPoltrona() == (linha * colunas + coluna + 1)) {
                        reservado = true;
                        break;
                    }
                }
                assentosPreSelecionados = new boolean[linhas][colunas];
                if (reservado) {
                    botaoAssento.setBackground(Color.RED);
                    botaoAssento.setText("Reservado");
                    botaoAssento.setEnabled(false);
                } else {

                    botaoAssento.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Alterna entre pré-seleção e desmarcação
                            if (!assentosPreSelecionados[linha][coluna]) {
                                // Pré-selecionar o assento
                                botaoAssento.setBackground(Color.YELLOW);
                                assentosPreSelecionados[linha][coluna] = true;
                                // Se já estava pré-selecionado, abrir o diálogo de confirmação
                                int confirmacao = JOptionPane.showConfirmDialog(
                                        null,
                                        "Confirma a seleção do assento?",
                                        "Confirmação",
                                        JOptionPane.OK_CANCEL_OPTION
                                );

                                if (confirmacao == JOptionPane.OK_OPTION) {
                                    // Reservar o assento
                                    Ingresso.reservarAssentos(cpf, pecaSelecionada, horarioSelecionado, sessaoSelecionada, linha, coluna, botaoAssento);

                                    // Atualizar visualmente como "Reservado"
                                    botaoAssento.setBackground(Color.RED);
                                    botaoAssento.setText("Reservado");
                                    botaoAssento.setEnabled(false);

                                    // Remover do estado pré-selecionado
                                    assentosPreSelecionados[linha][coluna] = false;
                                } else if (confirmacao == JOptionPane.CANCEL_OPTION || confirmacao == JOptionPane.CLOSED_OPTION) {
                                    // Cancelar a pré-seleção e resetar o botão
                                    botaoAssento.setBackground(null); // Cor padrão
                                    assentosPreSelecionados[linha][coluna] = false;
                                }
                            }
                        }
                    });
                }

                assentosPanel.add(botaoAssento);
            }
            letra++;
            cont++;
        }
        resultadoPanel.add(assentosPanel, BorderLayout.CENTER);
        resultadoPanel.revalidate();
        resultadoPanel.repaint();
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








