package pi2;

public class Ingresso {
    private String cpfCliente, nomePeca, sessao, area;
    private int poltrona;
    private double preco;

    public Ingresso(String cpfCliente, String nomePeca, String sessao, String area, int poltrona, double preco){
        this.cpfCliente = cpfCliente;
        this.nomePeca = nomePeca;
        this.sessao = sessao;
        this.area = area;
        this.poltrona = poltrona;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public int getPoltrona() {
        return poltrona;
    }

    public String getArea() {
        return area;
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
