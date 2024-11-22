package pi2;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static pi2.Teatro.*;

public class Ingresso {
    private String cpfCliente, nomePeca, sessao, horario;
    private int poltrona;
    private double preco;
    static final List<Ingresso> ingressos = new ArrayList<>();

    public Ingresso(String cpfCliente, String nomePeca, String horario, String sessao, int poltrona, double preco){
        this.cpfCliente = cpfCliente;
        this.nomePeca = nomePeca;
        this.horario = horario;
        this.sessao = sessao;
        this.poltrona = poltrona;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public int getPoltrona() {
        return poltrona;
    }

    public String getHorario() {
        return horario;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public String getSessao() {
        return sessao;
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
                    ingressos.add(new Ingresso(cpf, Teatro.nomePecas[pecaSelecionada], Teatro.nomeHorario[horarioSelecionado],Teatro.sessoes[sessaoSelecionada], poltrona, preco));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar ingressos: " + e.getMessage());
        }
    }

    static void reservarAssentos(String cpf, int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada, int linha, int coluna, JButton botaoAssento) {
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

            ingressos.add(new Ingresso(cpf, nomePecas[pecaSelecionada], nomeHorario[horarioSelecionado], sessoes[sessaoSelecionada], linha * colunas + coluna + 1, Teatro.preco[sessaoSelecionada]));
            // Grava as informações da reserva de assento no arquivo ingressos.txt
            try (FileWriter escritor = new FileWriter("ingressos.txt", true)) {
                escritor.write(cpf + "," + pecaSelecionada + "," + horarioSelecionado + "," + sessaoSelecionada + "," + poltrona + "," + sessaoSelecionada + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar informações do ingresso: " + e.getMessage());
            }
        }
    }

}
