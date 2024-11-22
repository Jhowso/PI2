package pi2;

public class Ingresso {
    private String cpfCliente, nomePeca, sessao, horario;
    private int poltrona;
    private double preco;

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

}
