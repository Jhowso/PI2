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
    public static String cpfAtual;
    public Usuario(String nome, String cpf, String telefone, String endereco, String dataNascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                '}';
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

    public static void cadastrarUsuario(String nome, String cpf, String telefone, String endereco, String dataNascimento, JFrame telaCadastro) {
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
                telaCadastro.dispose();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar informações do usuário: " + e.getMessage());
            }
        } catch (Erros e) {
            JOptionPane.showMessageDialog(null, "CPF inválido: " + e.getMessage());
        }
    }

    public static boolean validarUsuarioCadastrado(){
        JPanel panel = new JPanel();
        JTextField campocpf = new JTextField(15);

        panel.add(new JLabel("Digite seu CPF: "));
        panel.add(campocpf);

        int resultado = JOptionPane.showConfirmDialog(null, panel, "Informe seu CPF: ", JOptionPane.OK_CANCEL_OPTION);
        String cpf;

        if (resultado == JOptionPane.OK_OPTION) {
            cpf = campocpf.getText();
            try {
                validarCPF(cpf);
            } catch (Erros ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            boolean usuarioEncontrado = false;
            for (Usuario usuario : usuarios) {
                if (usuario.getCpf().equals(cpf)) {
                    usuarioEncontrado = true;
                    Usuario.cpfAtual = cpf;
                    break;
                }
            }

            if (usuarioEncontrado) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não está cadastrado. Realize o cadastro primeiro");
                MenuCadastro.abrirTelaCadastro();
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return false;
        }
    }

    public static void validarCPF(String cpf) throws Erros {
        // Remove qualquer caractere não numérico
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            throw new Erros("CPF deve ter 11 dígitos.");
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            throw new Erros("CPF não pode ter todos os dígitos iguais.");
        }

        // Calcula e verifica os dígitos verificadores
        int soma1 = 0, soma2 = 0;
        int peso1 = 10, peso2 = 11;

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 9; i++) {
            soma1 += Character.getNumericValue(cpf.charAt(i)) * peso1--;
        }
        int digito1 = 11 - (soma1 % 11);
        if (digito1 >= 10) digito1 = 0; // Se o dígito for 10 ou 11, o valor é 0

        // Calcula o segundo dígito verificador
        for (int i = 0; i < 9; i++) {
            soma2 += Character.getNumericValue(cpf.charAt(i)) * peso2--;
        }
        soma2 += digito1 * 2;
        int digito2 = 11 - (soma2 % 11);
        if (digito2 >= 10) digito2 = 0; // Se o dígito for 10 ou 11, o valor é 0

        // Verifica se os dígitos verificadores calculados coincidem com os informados
        if (cpf.charAt(9) != (digito1 + '0') || cpf.charAt(10) != (digito2 + '0')) {
            throw new Erros("CPF inválido.");
        }
    }

    public static void salvarCadastroUsuario(JTextField[] textFieldsCadastro, JFrame telaCadastro) {
        try {
            for (JTextField textField : textFieldsCadastro) {
                if (textField.getText() == null || textField.getText().isEmpty()) {
                    throw new Erros("Os campos não podem ser vazios!");
                }
            }

            String nome = textFieldsCadastro[0].getText();
            String cpf = textFieldsCadastro[1].getText();
            String telefone = textFieldsCadastro[2].getText();
            String endereco = textFieldsCadastro[3].getText();
            String dataNascimento = textFieldsCadastro[4].getText();

            Usuario.cadastrarUsuario(nome, cpf, telefone, endereco, dataNascimento, telaCadastro);
        } catch (Erros ex) {
            JOptionPane.showMessageDialog(telaCadastro, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
