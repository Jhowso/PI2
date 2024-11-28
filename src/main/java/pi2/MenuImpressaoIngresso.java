package pi2;

import javax.swing.*;

public class MenuImpressaoIngresso {
    public void abrirTelaImpressaoIngresso() {
        Usuario.validarUsuarioCadastrado();
        String cpf = Usuario.cpfAtual;
        boolean ingressoEncontrado = false;

        for (Ingresso ingresso : Ingresso.ingressos) {
            if (cpf.equals(ingresso.getCpfCliente())) {
                JOptionPane.showMessageDialog(null, "Ingresso existente para esse usuário: \n\n" + ingresso.toString());
                ingressoEncontrado = true;
            }
        }

        if (!ingressoEncontrado) {
            JOptionPane.showMessageDialog(null, "Nenhum ingresso encontrado para esse CPF.");
        }
    }
}
