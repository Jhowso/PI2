package pi2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ingresso {
    private String cpfCliente, nomePeca, sessao, horario, identificacao;
    private int poltrona;
    private double preco;
    static final List<Ingresso> ingressos = new ArrayList<>();

    public Ingresso(String cpfCliente, String nomePeca, String horario, String sessao, int poltrona, double preco, String identificacao){
        this.cpfCliente = cpfCliente;
        this.nomePeca = nomePeca;
        this.horario = horario;
        this.sessao = sessao;
        this.poltrona = poltrona;
        this.preco = preco;
        this.identificacao = identificacao;
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

    public String getIdentificacao(){
        return identificacao;
    }

    @Override
    public String toString() {
        String nomeUsuario = buscarNomeUsuarioporCpf(cpfCliente);
        return "Ingresso para o cliente: " + nomeUsuario + "\n" +
                "Peça: " + nomePeca + "\n" +
                "Sessão: " + sessao + "\n" +
                "Horário: " + horario + "\n" +
                "Identificação: " + identificacao + "\n" +
                "Preço: R$ " + preco;
    }

    private String buscarNomeUsuarioporCpf(String cpf){
        for(Usuario usuario : Usuario.usuarios){
            if(usuario.getCpf().equals(cpf)){
                return usuario.getNome();
            }
        }
        return "Usuário não encontrado";
    }

    public static void carregarIngressos() {
        try (BufferedReader leitor = new BufferedReader(new FileReader("ingressos.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                // Ignora linhas que começam com "#"
                if(linha.startsWith("#")){
                    continue;
                }

                String[] dados = linha.split(",");

                // Verifica se tem os 7 campos
                if (dados.length == 7) {
                    String cpf = dados[0];
                    int pecaSelecionada = Integer.parseInt(dados[1]);
                    int horarioSelecionado = Integer.parseInt(dados[2]);
                    int sessaoSelecionada = Integer.parseInt(dados[3]);
                    int poltrona = Integer.parseInt(dados[4]);
                    double preco = Double.parseDouble(dados[5]);
                    String identificacao = dados[6];
                    ingressos.add(new Ingresso(cpf, MenuCompra.nomePecas[pecaSelecionada], MenuCompra.nomeHorario[horarioSelecionado],MenuCompra.sessoes[sessaoSelecionada], poltrona, MenuCompra.preco[sessaoSelecionada], identificacao));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar ingressos: " + e.getMessage());
        }
    }

}
