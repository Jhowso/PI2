package pi2;

import javax.swing.*;

public class MenuImpressaoIngresso {
    public void abrirTelaImpressaoIngresso() {
        Usuario.validarUsuarioCadastrado();
        imprimirIngresso();
    }

    public void imprimirIngresso(){
        String cpf = Usuario.cpfAtual;
        boolean ingressoEncontrado = false;
        for (Ingresso ingresso : Ingresso.ingressos) {
            if (cpf.equals(ingresso.getCpfCliente())) {
                JOptionPane.showMessageDialog(null, "Ingresso existente para esse usuário: \n\n" + ingresso);
                ingressoEncontrado = true;
            }
        }

        if (!ingressoEncontrado) {
            JOptionPane.showMessageDialog(null, "Nenhum ingresso encontrado para esse CPF.");
        }
    }
}
