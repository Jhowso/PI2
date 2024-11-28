package pi2;

import javax.swing.*;
import java.awt.*;

public class MenuEstatistica {
    /*
    Estatística de Vendas: Essa funcionalidade permite visualizar:
    - Qual peça teve mais e menos ingressos vendidos;
    - Qual sessão teve maior e menor ocupação de poltronas;
    - Qual a peça/sessão mais lucrativa e menos lucrativa;
    - Lucro médio do teatro com todas as áreas por peça.
     */
    public static void abrirTelaEstatisticas() {
        JFrame telaEstatisticas = new JFrame("Estatísticas de Vendas");
        telaEstatisticas.setLayout(new BorderLayout());
        telaEstatisticas.setSize(500, 500);
        JPanel estatisticasPanel = new JPanel();

        estatisticasPanel.setLayout(new BoxLayout(estatisticasPanel, BoxLayout.Y_AXIS));
        estatisticasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botao_min_max_Vendas = new JButton("Peças mais e menos vendidas");
        estatisticasPanel.add(botao_min_max_Vendas);
        botao_min_max_Vendas.addActionListener(e -> determinarIngressosVendidos());

        JButton botaoOcupacaoPoltronasporSessao = new JButton("Ocupação de poltronas por sessão");
        estatisticasPanel.add(botaoOcupacaoPoltronasporSessao);
        botaoOcupacaoPoltronasporSessao.addActionListener(e -> ocupacaoPoltronasporSessao());

        telaEstatisticas.add(estatisticasPanel);
        telaEstatisticas.setVisible(true);
    }

    private static void ocupacaoPoltronasporSessao() {
        // Array para armazenar o número de poltronas ocupadas por sessão
        int[] ocupacaoPorSessao = new int[MenuCompra.sessoes.length];

        // Conta as poltronas ocupadas por sessão
        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.sessoes.length; i++) {
                if (ingresso.getSessao().equals(MenuCompra.sessoes[i])) {
                    ocupacaoPorSessao[i]++;
                    break;
                }
            }
        }

        // Inicializa as variáveis de maior e menor ocupação
        int maiorOcupacao = ocupacaoPorSessao[0];
        int menorOcupacao = ocupacaoPorSessao[0];
        String sessaoMaiorOcupacao = MenuCompra.sessoes[0];
        String sessaoMenorOcupacao = MenuCompra.sessoes[0];

        // Determina a sessão com maior e menor ocupação
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

        // Exibe o resultado em uma janela de diálogo
        JOptionPane.showMessageDialog(null,
                "Sessão com maior ocupação: " + sessaoMaiorOcupacao + " (" + maiorOcupacao + " poltronas ocupadas)\n" +
                        "Sessão com menor ocupação: " + sessaoMenorOcupacao + " (" + menorOcupacao + " poltronas ocupadas)");
    }


    public static void determinarIngressosVendidos() {
        // Array para contar os ingressos vendidos por peça
        int[] vendasPorPeca = new int[MenuCompra.nomePecas.length];

        // Conta as vendas de ingressos para cada peça
        for (Ingresso ingresso : Ingresso.ingressos) {
            for (int i = 0; i < MenuCompra.nomePecas.length; i++) {
                if (ingresso.getNomePeca().equals(MenuCompra.nomePecas[i])) {
                    vendasPorPeca[i]++;
                    break;
                }
            }
        }

        // Inicializa as variáveis de maior e menor com base na primeira peça
        int maisVendas = vendasPorPeca[0];
        int menosVendas = vendasPorPeca[0];
        String pecaMaisVendida = MenuCompra.nomePecas[0];
        String pecaMenosVendida = MenuCompra.nomePecas[0];

        // Determina a peça com maior e menor número de vendas
        for (int i = 1; i < vendasPorPeca.length; i++) { // Começa do índice 1
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