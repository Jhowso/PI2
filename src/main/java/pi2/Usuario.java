package pi2;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome, cpf, telefone, endereco, dataNascimento;
    static final List<Usuario> usuarios = new ArrayList<>();
    public Usuario(String nome, String cpf, String telefone, String endereco, String dataNascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
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

    public static void cadastrarUsuario(String nome, String cpf, String telefone, String endereco, String dataNascimento) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                JOptionPane.showMessageDialog(null, "Usuário já está cadastrado");
                return;
            }
        }

        try {
            Teatro.validarCPF(cpf);
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
}
