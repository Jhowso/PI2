package pi2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class MenuCompra {
    final static String[] sessoes = {"Platéia A", "Platéia B", "Camarotes", "Frisas", "Balcão Nobre"}; // Platéia A = 0; Platéia B = 1; Frisas = 2; Camarotes = 3; Balcão Nobre = 4;
    final static String[] nomePecas = {"Lago dos Cisnes", "Chapeuzinho Vermelho", "Moby-Dick"};
    final static String[] nomeHorario = {"Manhã", "Tarde", "Noite"};
    final static double[] preco = {
            40.0,
            60.0,
            80.0,
            120.0,
            250.0
    };
    private static final JPanel resultadoPanel = new JPanel();
    static boolean[][] assentosPreSelecionados;
    private JButton[][] botoesAssentos;
    final static int[][] qtdAssentos = {
            {5, 5},   // Plateia A com 5 linhas e 5 colunas totalizando 25 assentos
            {10, 10}, // Plateia B com 10 linhas e 10 colunas totalizando 100 assentos
            {6, 5},   // 6 frisas e 5 assentos em cada frisa totalizando 30 assentos
            {5, 10},  // 5 camarotes e 10 assentos em cada camarote totalizando 50 assentos
            {5, 10}   // Balcão Nobre com 10 linhas e 5 colunas totalizando 50 assentos
    };
    static int linhas, colunas;
    static boolean[][] assentosReservados = new boolean[linhas][colunas];
    JPanel assentosPanel = new JPanel();

    public void abrirTelaCompra(String cpf) {
        boolean usuarioEncontrado = Usuario.validarUsuarioCadastrado();
        if (usuarioEncontrado) {
            resultadoPanel.removeAll();
            JFrame telaCompra = new JFrame("Compra de Ingressos");
            telaCompra.setLayout(new BorderLayout());
            telaCompra.setSize(1050, 600);
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

            confirmaSelecaoButton.addActionListener(e -> confirmaSelecaoCompraFunc(cpf, grupoPeca, grupoHorario, grupoSessao, peca, horario, sessao, telaCompra));
            telaCompra.setVisible(true);
        }
    }

    private void confirmaSelecaoCompraFunc(String cpf, ButtonGroup grupoPeca, ButtonGroup grupoHorario, ButtonGroup grupoSessao, JRadioButton[] peca, JRadioButton[] horario, JRadioButton[] sessao, JFrame telaCompra) {
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

    public void exibirAssentos(JPanel resultadoPanel, int[][] qtdAssentos, int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada, String cpf) {
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
        int linhas = qtdAssentos[sessaoSelecionada][0];
        int colunas = qtdAssentos[sessaoSelecionada][1];
        assentosPanel.setLayout(new GridLayout(linhas, colunas));

        botoesAssentos = new JButton[linhas][colunas]; // Inicializa a matriz de botões
        assentosPreSelecionados = new boolean[linhas][colunas]; // Inicializa a matriz de pré-seleção
        inicializarAssentos(linhas, colunas);
        char letra = 'A';
        int cont = 1;
        for (int i = 0; i < linhas; i++) {
            if (sessaoSelecionada == 2) {
                assentosPanel.add(new JLabel("Camarote " + cont));
            }

            if (sessaoSelecionada == 3) {
                assentosPanel.add(new JLabel("Frisa " + cont));
            }
            for (int j = 0; j < colunas; j++) {

                String identificacao = letra + String.valueOf(j + 1);
                JButton botaoAssento = new JButton(identificacao);
                botoesAssentos[i][j] = botaoAssento; // Armazena o botão na matriz
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
                            } else {
                                // Desmarcar o assento
                                botaoAssento.setBackground(null);
                                assentosPreSelecionados[linha][coluna] = false;
                            }
                        }
                    });
                }

                assentosPanel.add(botaoAssento);
            }
            letra++;
            cont++;
        }
        JButton botaoConfirmaCompra = new JButton("Concluir compra");
        resultadoPanel.add(assentosPanel, BorderLayout.CENTER);
        resultadoPanel.add(botaoConfirmaCompra, BorderLayout.SOUTH);
        botaoConfirmaCompra.addActionListener(e -> botaoConfirmaCompraFunc(cpf, pecaSelecionada, horarioSelecionado, sessaoSelecionada));
    }

    private void botaoConfirmaCompraFunc(String cpf, int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada) {
        // Verificar se há assentos pré-selecionados
        boolean assentoSelecionado = false;
        String listaAssentosSelecionados = ""; // Usando String simples para acumular os assentos

        // Identificar todos os assentos pré-selecionados
        for (int i = 0; i < botoesAssentos.length; i++) {
            for (int j = 0; j < botoesAssentos[i].length; j++) {
                if (assentosPreSelecionados[i][j]) {
                    assentoSelecionado = true;
                    // Criar a identificação do assento
                    String identificacao = (char) ('A' + i) + String.valueOf(j + 1);

                    // Adicionar o assento à lista de selecionados
                    listaAssentosSelecionados += identificacao + "\n"; // Concatenar com nova linha

                    // Verificar se o botão já foi reservado
                    if (botoesAssentos[i][j].getBackground() == Color.RED) {
                        JOptionPane.showMessageDialog(null, "O assento " + identificacao + " já foi reservado.");
                    }
                }
            }
        }
        if (assentoSelecionado) {
            // Exibir a lista de assentos selecionados e pedir confirmação
            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    "Confirma a seleção dos seguintes assentos?\n" + listaAssentosSelecionados,
                    "Confirmação de Seleção",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (confirmacao == JOptionPane.OK_OPTION) {
                for (int i = 0; i < botoesAssentos.length; i++) {
                    for (int j = 0; j < botoesAssentos[i].length; j++) {
                        if (assentosPreSelecionados[i][j]) {
                            // Reservar o assento
                            reservarAssentos(cpf, pecaSelecionada, horarioSelecionado, sessaoSelecionada, i, j, botoesAssentos[i][j]);
                            // Atualizar visualmente como "Reservado"
                            botoesAssentos[i][j].setBackground(Color.RED);
                            botoesAssentos[i][j].setText("Reservado");
                            botoesAssentos[i][j].setEnabled(false);
                            // Remover o assento da pré-seleção
                            assentosPreSelecionados[i][j] = false;
                        }
                    }
                }
            } else {
                // Se a confirmação for cancelada, cancelar a seleção
                for (int i = 0; i < botoesAssentos.length; i++) {
                    for (int j = 0; j < botoesAssentos[i].length; j++) {
                        if (assentosPreSelecionados[i][j]) {
                            // Resetar a cor dos assentos
                            botoesAssentos[i][j].setBackground(null);
                            assentosPreSelecionados[i][j] = false; // Resetar a pré-seleção
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum assento foi selecionado.");
        }
    }

    public void reservarAssentos(String cpf, int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada, int linha, int coluna, JButton botaoAssento) {
        cpf = Usuario.cpfAtual;
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

            Ingresso.ingressos.add(new Ingresso(cpf, nomePecas[pecaSelecionada], nomeHorario[horarioSelecionado], sessoes[sessaoSelecionada], linha * colunas + coluna + 1, preco[sessaoSelecionada]));
            // Grava as informações da reserva de assento no arquivo ingressos.txt
            try (FileWriter escritor = new FileWriter("ingressos.txt", true)) {
                escritor.write(cpf + "," + pecaSelecionada + "," + horarioSelecionado + "," + sessaoSelecionada + "," + poltrona + "," + sessaoSelecionada + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar informações do ingresso: " + e.getMessage());
            }
        }
    }

    public static void inicializarAssentos(int totalLinhas, int totalColunas) {
        linhas = totalLinhas;
        colunas = totalColunas;
        assentosReservados = new boolean[linhas][colunas];
    }
}
