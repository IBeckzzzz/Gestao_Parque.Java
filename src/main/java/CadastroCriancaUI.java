import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCriancaUI {
    private JFrame frame;
    private JTextField responsavelField, nomeField, idadeField;
    private JComboBox<String> sexoComboBox;
    private CadastroResponsavelUI cadastroResponsavelUI;
    private Responsavel responsavel;
    private Crianca crianca;

    public CadastroCriancaUI(Responsavel responsavel, CadastroResponsavelUI cadastroResponsavelUI) {
        this.cadastroResponsavelUI = cadastroResponsavelUI;
        this.responsavel = responsavel;
        this.crianca = null;

        frame = new JFrame("Cadastro de Criança");
        frame.setSize(490, 385);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel("Etapa 2 de 3 - Dados da Criança", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setForeground(Color.BLUE);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(tituloLabel, BorderLayout.NORTH);

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        criarCampoNaoEditavel("Responsável:", responsavel.getNome(), camposPanel);
        criarCampo("Nome:", 200, 25, camposPanel);
        criarCampo("Idade:", 50, 25, camposPanel);

        JLabel labelSexo = new JLabel("Sexo:");
        sexoComboBox = new JComboBox<>(new String[]{"Masculino", "Feminino", "Não Informar"});
        sexoComboBox.setPreferredSize(new Dimension(200, 25));
        camposPanel.add(labelSexo);
        camposPanel.add(sexoComboBox);

        JPanel painelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        painelCentral.add(camposPanel);

        frame.add(painelCentral, BorderLayout.CENTER);

        JButton avancarButton = new JButton("Avançar");
        avancarButton.setPreferredSize(new Dimension(80, 30));
         avancarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                avancarParaProximaEtapa();
            }
        });


        JButton voltarButton = new JButton("Voltar");
        voltarButton.setPreferredSize(new Dimension(80, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarParaEtapaAnterior();
            }
        });

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botoesPanel.add(voltarButton);
        botoesPanel.add(avancarButton);
        frame.add(botoesPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void criarCampo(String label, int largura, int altura, JPanel panel) {
        JLabel labelCampo = new JLabel(label);
        if (label.equals("Nome:")) {
            nomeField = new JTextField();
            nomeField.setPreferredSize(new Dimension(largura, altura));
            panel.add(labelCampo);
            panel.add(nomeField);
        } else if (label.equals("Idade:")) {
            idadeField = new JTextField();
            idadeField.setPreferredSize(new Dimension(largura, altura));
            panel.add(labelCampo);
            panel.add(idadeField);
        } else {
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(largura, altura));
            panel.add(labelCampo);
            panel.add(textField);
        }
    }

    private void criarCampoNaoEditavel(String label, String valor, JPanel panel) {
        JLabel labelCampo = new JLabel(label);
        if (label.equals("Responsável:")) {
            responsavelField = new JTextField(valor);
            responsavelField.setPreferredSize(new Dimension(200, 25));
            responsavelField.setEditable(false);
            panel.add(labelCampo);
            panel.add(responsavelField);
        } else {
            JLabel valorLabel = new JLabel(valor);
            valorLabel.setPreferredSize(new Dimension(200, 25));
            panel.add(labelCampo);
            panel.add(valorLabel);
        }
    }

    private void avancarParaProximaEtapa() {
    crianca = getCrianca();

    // Verificar se a criança é válida antes de avançar para a próxima etapa
    if (crianca != null) {
        SwingUtilities.invokeLater(() -> {
            CadastroEstadiaUI cadastroEstadiaUI = new CadastroEstadiaUI(responsavel, crianca, this);
            cadastroEstadiaUI.mostrarTela();
        });
        frame.dispose();
    }
 }

    private void voltarParaEtapaAnterior() {
        SwingUtilities.invokeLater(() -> {
            if (cadastroResponsavelUI != null) {
                cadastroResponsavelUI.setCrianca(crianca);
                cadastroResponsavelUI.mostrarTela();
            } else {
                System.out.println("cadastroResponsavelUI é null. Não é possível voltar para a etapa anterior.");
            }
            frame.dispose();
        });
    }

    public Crianca getCrianca() {
    String nome = nomeField.getText();
    int idade;
    try {
        idade = Integer.parseInt(idadeField.getText());
        if (idade > 10) {
            JOptionPane.showMessageDialog(frame, "A idade deve ser menor ou igual a 10.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            // Pode adicionar mais lógica aqui, como limpar o campo ou pedir ao usuário para corrigir.
            return null; // Ou retorne um objeto vazio, dependendo da sua lógica.
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Por favor, insira um valor válido para a idade.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        // Pode adicionar mais lógica aqui, como limpar o campo ou pedir ao usuário para corrigir.
        return null; // Ou retorne um objeto vazio, dependendo da sua lógica.
    }

    String sexo = (String) sexoComboBox.getSelectedItem();

    return new Crianca(nome, idade, sexo);
}

    public void mostrarTela() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public void setCrianca(Crianca crianca) {
        this.crianca = crianca;
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        Responsavel responsavel = new Responsavel("Nome do Responsável", "", "", "", "", 0);
        CadastroResponsavelUI cadastroResponsavelUI = new CadastroResponsavelUI("", "", "", "", "", "");
        new CadastroCriancaUI(responsavel, cadastroResponsavelUI).mostrarTela();
    });
}
    
    public CadastroEstadiaUI getProximaEtapa() {
    crianca = getCrianca();
    return new CadastroEstadiaUI(responsavel, crianca, this);
}
    
    public Responsavel getResponsavel() {
    return cadastroResponsavelUI.getResponsavel();
}
}
