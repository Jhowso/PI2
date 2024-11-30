package pi2;

import javax.swing.*;
import java.util.List;

public class MenuImpressaoIngresso {
    public void abrirTelaImpressaoIngresso() {
        boolean usuarioEncontrado = Usuario.validarUsuarioCadastrado();
        if (usuarioEncontrado) {
            imprimirIngresso();
        }
    }

    public void imprimirIngresso(){
        String cpf = Usuario.cpfAtual;
        boolean ingressoEncontrado = false;
        for (Ingresso ingresso : Ingresso.ingressos) {
            if (cpf.equals(ingresso.getCpfCliente())) {
                JOptionPane.showMessageDialog(null, ingresso);
                ingressoEncontrado = true;
            }
        }

        if (!ingressoEncontrado) {
            JOptionPane.showMessageDialog(null, "Nenhum ingresso encontrado para esse CPF.");
        }
    }

    public void imprimirIngresso(int pecaSelecionada, int horarioSelecionado, int sessaoSelecionada, List<String> assentosCompradosAgora) {
        if (assentosCompradosAgora.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum ingresso comprado nesta operação.");
            return;
        }

        // Verifica quais ingressos são os assentos comprados no momento do clique do botão de compra.
        for(Ingresso ingresso : Ingresso.ingressos){
            for (String assento : assentosCompradosAgora) {
                        if(ingresso.getIdentificacao().equals(assento)
                        && MenuCompra.nomePecas[pecaSelecionada].equals(ingresso.getNomePeca())
                        && MenuCompra.nomeHorario[horarioSelecionado].equals(ingresso.getHorario())
                        && MenuCompra.sessoes[sessaoSelecionada].equals(ingresso.getSessao())){
                    JOptionPane.showMessageDialog(null, ingresso);
                }
            }
        }
    }
}
