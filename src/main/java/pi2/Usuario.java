package pi2;

public class Usuario {
    private String nome, cpf, telefone, endereco, dataNascimento;

    public Usuario(String nome, String cpf, String telefone, String endereco, String dataNascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = cpf;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }
}
