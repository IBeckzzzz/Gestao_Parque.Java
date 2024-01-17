import javax.swing.*;

public class BrinquedoManager {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Cadastro do Responsável
            Responsavel responsavel = cadastrarResponsavel();

            // Agora você pode usar o objeto 'responsavel' como quiser
            System.out.println("Dados do responsável na main: " + responsavel);
        });
    }

    private static Responsavel cadastrarResponsavel() {
    CadastroResponsavelUI responsavelUI = new CadastroResponsavelUI("", "", "", "", "", "");
    responsavelUI.mostrarTela(); 
    return responsavelUI.getResponsavel();
}
}
