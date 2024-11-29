package pi2;

import javax.swing.*;
import java.awt.*;

public class MenuEstatistica {

    public static void abrirTelaEstatisticas() {
        JFrame telaEstatisticas = new JFrame("Estatísticas de Vendas");
        telaEstatisticas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaEstatisticas.setSize(300, 300);

        JPanel estatisticasPanel = new JPanel();
        estatisticasPanel.setLayout(new BoxLayout(estatisticasPanel, BoxLayout.Y_AXIS));
        estatisticasPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton botaoMinMaxVendas = new JButton("Peças mais e menos vendidas");
        botaoMinMaxVendas.setAlignmentX(Component.CENTER_ALIGNMENT);
        estatisticasPanel.add(botaoMinMaxVendas);
        botaoMinMaxVendas.addActionListener(e -> determinarIngressosVendidos());

        estatisticasPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton botaoOcupacaoPoltronasporSessao = new JButton("Ocupação de poltronas por sessão");
        botaoOcupacaoPoltronasporSessao.setAlignmentX(Component.CENTER_ALIGNMENT);
        estatisticasPanel.add(botaoOcupacaoPoltronasporSessao);
        botaoOcupacaoPoltronasporSessao.addActionListener(e -> ocupacaoPoltronasporSessao());

        estatisticasPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton botaoEstatisticaLucroporSessao = new JButton("Peça e sessão mais lucrativa");
        botaoEstatisticaLucroporSessao.setAlignmentX(Component.CENTER_ALIGNMENT);
        estatisticasPanel.add(botaoEstatisticaLucroporSessao);
        botaoEstatisticaLucroporSessao.addActionListener(e -> minMaxLucroPorPecaeSessao());

        estatisticasPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton botaoLucroMedio = new JButton("Lucro por peça");
        botaoLucroMedio.setAlignmentX(Component.CENTER_ALIGNMENT);
        estatisticasPanel.add(botaoLucroMedio);
        botaoLucroMedio.addActionListener(e -> calcularLucroMedioPorPeca());

        telaEstatisticas.add(estatisticasPanel);

        telaEstatisticas.setLocationRelativeTo(null);
        telaEstatisticas.setVisible(true);
    }

    public static void calcularLucroMedioPorPeca() {
        double[] lucroPorPeca = new double[MenuCompra.nomePecas.length];
        int[] ingressosPorPeca = new int[MenuCompra.nomePecas.length];

        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.nomePecas.length; i++) {
                if (ingresso.getNomePeca().equals(MenuCompra.nomePecas[i])) {
                    lucroPorPeca[i] += ingresso.getPreco();
                    ingressosPorPeca[i]++;
                    break;
                }
            }
        }

        String mensagem = "Lucro médio do teatro por peça:\n";
        for (int i = 0; i < MenuCompra.nomePecas.length; i++) {
            if (ingressosPorPeca[i] > 0) {
                double lucroMedio = lucroPorPeca[i] / ingressosPorPeca[i];
                mensagem += MenuCompra.nomePecas[i] + ": R$ " + String.format("%.2f", lucroMedio) + "\n";
            } else {
                mensagem += MenuCompra.nomePecas[i] + ": Nenhum ingresso vendido\n";
            }
        }

        JOptionPane.showMessageDialog(null, mensagem, "Lucro Médio por Peça", JOptionPane.INFORMATION_MESSAGE);
    }


    private static void minMaxLucroPorPecaeSessao() {
        double[] lucroPorSessao = new double[MenuCompra.sessoes.length];
        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.sessoes.length; i++) {
                if (ingresso.getPreco() == MenuCompra.preco[i]) {
                    lucroPorSessao[i] += ingresso.getPreco();
                    break;
                }
            }
        }

        double maiorLucroSessao = lucroPorSessao[0];
        double menorLucroSessao = lucroPorSessao[0];
        String sessaoMaisLucrativa = MenuCompra.sessoes[0];
        String sessaoMenosLucrativa = MenuCompra.sessoes[0];

        for (int i = 1; i < lucroPorSessao.length; i++) {
            if (lucroPorSessao[i] > maiorLucroSessao) {
                maiorLucroSessao = lucroPorSessao[i];
                sessaoMaisLucrativa = MenuCompra.sessoes[i];
            }
            if (lucroPorSessao[i] < menorLucroSessao) {
                menorLucroSessao = lucroPorSessao[i];
                sessaoMenosLucrativa = MenuCompra.sessoes[i];
            }
        }

        double[] lucroPorPeca = new double[MenuCompra.nomePecas.length];
        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.nomePecas.length; i++) {
                if (ingresso.getNomePeca().equals(MenuCompra.nomePecas[i])) {
                    lucroPorPeca[i] += ingresso.getPreco();
                    break;
                }
            }
        }

        double maiorLucroPeca = lucroPorPeca[0];
        double menorLucroPeca = lucroPorPeca[0];
        String pecaMaisLucrativa = MenuCompra.nomePecas[0];
        String pecaMenosLucrativa = MenuCompra.nomePecas[0];

        for (int i = 1; i < lucroPorPeca.length; i++) {
            if (lucroPorPeca[i] > maiorLucroPeca) {
                maiorLucroPeca = lucroPorPeca[i];
                pecaMaisLucrativa = MenuCompra.nomePecas[i];
            }
            if (lucroPorPeca[i] < menorLucroPeca) {
                menorLucroPeca = lucroPorPeca[i];
                pecaMenosLucrativa = MenuCompra.nomePecas[i];
            }
        }

        String mensagem = "Sessão com maior lucro: " + sessaoMaisLucrativa + " (R$ " + String.format("%.2f", maiorLucroSessao) + " em vendas)\n" +
                "Sessão com menor lucro: " + sessaoMenosLucrativa + " (R$ " + String.format("%.2f", menorLucroSessao) + " em vendas)\n\n" +
                "Peça com maior lucro: " + pecaMaisLucrativa + " (R$ " + String.format("%.2f", maiorLucroPeca) + " em vendas)\n" +
                "Peça com menor lucro: " + pecaMenosLucrativa + " (R$ " + String.format("%.2f", menorLucroPeca) + " em vendas)";

        JOptionPane.showMessageDialog(null, mensagem, "Estatísticas de Lucro", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void ocupacaoPoltronasporSessao() {
        int[] ocupacaoPorSessao = new int[MenuCompra.sessoes.length];

        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.sessoes.length; i++) {
                if (ingresso.getSessao().equals(MenuCompra.sessoes[i])) {
                    ocupacaoPorSessao[i]++;
                    break;
                }
            }
        }

        int maiorOcupacao = ocupacaoPorSessao[0];
        int menorOcupacao = ocupacaoPorSessao[0];
        String sessaoMaiorOcupacao = MenuCompra.sessoes[0];
        String sessaoMenorOcupacao = MenuCompra.sessoes[0];

        for (int i = 1; i < ocupacaoPorSessao.length; i++) {
            if (ocupacaoPorSessao[i] > maiorOcupacao) {
                maiorOcupacao = ocupacaoPorSessao[i];
                sessaoMaiorOcupacao = MenuCompra.sessoes[i];
            }
            if (ocupacaoPorSessao[i] < menorOcupacao) {
                menorOcupacao = ocupacaoPorSessao[i];
                sessaoMenorOcupacao = MenuCompra.sessoes[i];
            }
        }

        JOptionPane.showMessageDialog(null,
                "Sessão com maior ocupação: " + sessaoMaiorOcupacao + " (" + maiorOcupacao + " poltronas ocupadas)\n" +
                        "Sessão com menor ocupação: " + sessaoMenorOcupacao + " (" + menorOcupacao + " poltronas ocupadas)");
    }


    public static void determinarIngressosVendidos() {
        int[] vendasPorPeca = new int[MenuCompra.nomePecas.length];

        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.nomePecas.length; i++) {
                if (ingresso.getNomePeca().equals(MenuCompra.nomePecas[i])) {
                    vendasPorPeca[i]++;
                    break;
                }
            }
        }

        int maisVendas = vendasPorPeca[0];
        int menosVendas = vendasPorPeca[0];
        String pecaMaisVendida = MenuCompra.nomePecas[0];
        String pecaMenosVendida = MenuCompra.nomePecas[0];

        for (int i = 1; i < vendasPorPeca.length; i++) {
            int vendas = vendasPorPeca[i];
            String nomePeca = MenuCompra.nomePecas[i];

            if (vendas > maisVendas) {
                maisVendas = vendas;
                pecaMaisVendida = nomePeca;
            }
            if (vendas < menosVendas) {
                menosVendas = vendas;
                pecaMenosVendida = nomePeca;
            }
        }

        JOptionPane.showMessageDialog(null,
                "Peça com mais ingressos vendidos: " + pecaMaisVendida + " (Vendas: " + maisVendas + ")\n" +
                "Peça com menos ingressos vendidos: " + pecaMenosVendida + " (Vendas: " + menosVendas + ")");
    }
}
