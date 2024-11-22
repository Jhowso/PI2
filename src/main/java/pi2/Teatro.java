package pi2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Teatro {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Ingresso> ingressos = new ArrayList<>();
    JFrame tela = new JFrame();
    private static JPanel resultadoPanel = new JPanel();

    final static String[][] horarios = new String[3][3]; // 3 peças uma de manhã, de tarde e a noite, e 5 sessões:
    final static String[] sessoes = {"Platéia A", "Platéia B", "Camarotes", "Frisas", "Balcão Nobre"}; // Platéia A = 0; Platéia B = 1; Frisas = 2; Camarotes = 3; Balcão Nobre = 4;
    final static int[][] qtdAssentos = {
            {5, 5},   // Plateia A com 5 linhas e 5 colunas totalizando 25 assentos
            {10, 10}, // Plateia B com 10 linhas e 10 colunas totalizando 100 assentos
            {6, 5},   // 6 frisas e 5 assentos em cada frisa totalizando 30 assentos
            {5, 10},  // 5 camarotes e 10 assentos em cada camarote totalizando 50 assentos
            {5, 10}   // Balcão Nobre com 10 linhas e 5 colunas totalizando 50 assentos
    };
    final static boolean[][][] qtdAssentosOcupado = {
            new boolean[5][5],   // Plateia A
            new boolean[10][10], // Plateia B
            new boolean[6][5],   // Frisas
            new boolean[5][10],  // Camarotes
            new boolean[5][10]   // Balcão Nobre
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
    static JButton botaoAssento;

    static int linhas, colunas;
    static boolean[][] assentosReservados = new boolean[linhas][colunas];

    public void menuPrincipal() {
        carregarUsuarios();
        carregarIngressos();
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

                                cadastrarUsuario(nome, cpf, telefone, endereco, dataNascimento);
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
                    for (Usuario usuario : usuarios) {
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
                }// Fim das validações para realizar a compra do ingresso

                JPanel principalPanel = new JPanel();
                principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.Y_AXIS));

                // Painel de seleção da peça
                JPanel pecaPanel = new JPanel();
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
                JPanel panelHorario = new JPanel();
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

                // Painel de seleção da sessão.
                JPanel panelSessao = new JPanel();
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

                            // Exibe os assentos de acordo com a peça, horário e sessão selecionados.
                            if (pecaSelecionada != -1 && horarioSelecionado != -1 && sessaoSelecionada != -1) {
                                horarios[pecaSelecionada][horarioSelecionado] = sessoes[sessaoSelecionada];
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

    public void cadastrarUsuario(String nome, String cpf, String telefone, String endereco, String dataNascimento) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                JOptionPane.showMessageDialog(null, "Usuário já está cadastrado");
                return;
            }
        }

        try {
            validarCPF(cpf);
            usuarios.add(new Usuario(nome, cpf, telefone, endereco, dataNascimento));
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            try (FileWriter escritor = new FileWriter("usuarios.txt", true)) {
                escritor.write(nome + "," + cpf + "," + telefone + "," + endereco + "," + dataNascimento + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar informações do usuário: " + e.getMessage());
            }
        } catch (Erros e) {
            JOptionPane.showMessageDialog(null, "CPF inválido: " + e.getMessage());
        }
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
        infoPanel.add(new JLabel("Valor: " + preco[sessaoSelecionada]));
        infoPanel.add(new JLabel("Disposição dos Assentos:"));

        resultadoPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel assentosPanel = new JPanel();
        int linhas = qtdAssentos[sessaoSelecionada][0];
        int colunas = qtdAssentos[sessaoSelecionada][1];
        assentosPanel.setLayout(new GridLayout(linhas, colunas));

        // Inicializa a matriz de assentos reservados
        inicializarMatriz(linhas, colunas);

        char letra = 'A';
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                String identificacao = letra + String.valueOf(j + 1);
                JButton botaoAssento = new JButton(identificacao);
                int linha = i;
                int coluna = j;

                // Verifica se o assento está reservado
                boolean reservado = false;
                for (Ingresso ingresso : ingressos) {
                    if (ingresso.getNomePeca().equals(nomePecas[pecaSelecionada]) &&
                            ingresso.getHorario().equals(nomeHorario[horarioSelecionado]) &&
                            ingresso.getSessao().equals(sessoes[sessaoSelecionada]) &&
                            ingresso.getPoltrona() == (linha * colunas + coluna + 1)) {
                        reservado = true;
                        break;
                    }
                }

                if (reservado) {
                    botaoAssento.setBackground(Color.RED);
                    botaoAssento.setText("Reservado");
                    botaoAssento.setEnabled(false);
                } else {
                    botaoAssento.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            reservarAssentos(cpf, pecaSelecionada, horarioSelecionado, sessaoSelecionada, linha, coluna, botaoAssento);
                        }
                    });
                }

                assentosPanel.add(botaoAssento);
            }
            letra++;
        }

        resultadoPanel.add(assentosPanel, BorderLayout.CENTER);
        resultadoPanel.revalidate();
        resultadoPanel.repaint();
    }

    private static void reservarAssentos(String cpf, int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada, int linha, int coluna, JButton botaoAssento) {
        if (linha >= assentosReservados.length || coluna >= assentosReservados[linha].length) {
            JOptionPane.showMessageDialog(null, "Índice fora dos limites!");
            return;
        }

        if (assentosReservados[linha][coluna]) {
            JOptionPane.showMessageDialog(null, "Assento já reservado!");
        } else {
            assentosReservados[linha][coluna] = true;
            botaoAssento.setBackground(Color.RED);
            botaoAssento.setText("Reservado");
            String poltrona = linha * colunas + coluna + 1 + "";
            ingressos.add(new Ingresso(cpf, nomePecas[pecaSelecionada], nomeHorario[horarioSelecionado], sessoes[sessaoSelecionada], linha * colunas + coluna + 1, preco[sessaoSelecionada]));
            try (FileWriter escritor = new FileWriter("ingressos.txt", true)) {
                escritor.write(cpf + "," + pecaSelecionada + "," + horarioSelecionado + "," + sessaoSelecionada + "," + poltrona + "," + sessaoSelecionada + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar informações do ingresso: " + e.getMessage());
            }
        }
    }

    public static void carregarUsuarios() {
        // Lê as linhas do arquivo usuarios.txt, lê cada linha e cria um novo objeto de usuário para cada linha que tenha os 5 elementos.
        try (BufferedReader leitor = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                // Quebra a linha pelo delimitador ","
                String[] dados = linha.split(",");
                // Verifica se tem os 5 campos
                if (dados.length == 5) {
                    String nome = dados[0];
                    String cpf = dados[1];
                    String telefone = dados[2];
                    String endereco = dados[3];
                    String dataNascimento = dados[4];
                    usuarios.add(new Usuario(nome, cpf, telefone, endereco, dataNascimento));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public static void carregarIngressos() {
        try (BufferedReader leitor = new BufferedReader(new FileReader("ingressos.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                // Quebra a linha pelo delimitador ","
                String[] dados = linha.split(",");

                // Verifica se tem os 6 campos
                if (dados.length == 6) {
                    String cpf = dados[0];
                    int pecaSelecionada = Integer.parseInt(dados[1]);
                    int horarioSelecionado = Integer.parseInt(dados[2]);
                    int sessaoSelecionada = Integer.parseInt(dados[3]);
                    int poltrona = Integer.parseInt(dados[4]);
                    double preco = Double.parseDouble(dados[5]);
                    ingressos.add(new Ingresso(cpf, nomePecas[pecaSelecionada], nomeHorario[horarioSelecionado], sessoes[sessaoSelecionada], poltrona, preco));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar ingressos: " + e.getMessage());
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








