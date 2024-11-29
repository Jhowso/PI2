package pi2;

import javax.swing.*;
import java.awt.*;

public class MenuCadastro {
    public static void abrirTelaCadastro() {
        JFrame telaCadastro = new JFrame("Cadastro Usuário");
        telaCadastro.setLayout(new BoxLayout(telaCadastro.getContentPane(), BoxLayout.Y_AXIS));
        telaCadastro.setSize(600, 300);
        ImageIcon icone = new ImageIcon("iconeTeatro.png");
        Image imagemIcone = icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        telaCadastro.setIconImage(imagemIcone);

        String[] textosCadastro = {"Nome: ", "CPF: ", "Telefone: ", "Endereço: ", "Data de Nascimento: "};
        JLabel[] labelsCadastro = new JLabel[5];
        JTextField[] textFieldsCadastro = new JTextField[5];
        JButton salvarButton = new JButton("Salvar");

        for (int i = 0; i < 5; i++) {
            labelsCadastro[i] = new JLabel(textosCadastro[i]);
            textFieldsCadastro[i] = new JTextField(20);

            labelsCadastro[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            textFieldsCadastro[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            textFieldsCadastro[i].setMaximumSize(new Dimension(400, 20));

            telaCadastro.add(labelsCadastro[i]);
            telaCadastro.add(textFieldsCadastro[i]);
        }

        telaCadastro.add(Box.createVerticalStrut(20));
        salvarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        telaCadastro.add(salvarButton);

        salvarButton.addActionListener(e -> Usuario.salvarCadastroUsuario(textFieldsCadastro, telaCadastro));
        telaCadastro.setLocationRelativeTo(null);
        telaCadastro.setVisible(true);
    }
}
