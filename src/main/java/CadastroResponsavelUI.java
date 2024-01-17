import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class CadastroResponsavelUI {
    private final JFrame frame;
    private JTextField nomeField, cpfField, telefoneField, emailField, enderecoField, idadeField;
    private Crianca crianca;
    private CadastroCriancaUI cadastroCriancaUI;
    private boolean avancarClicado = false;

    public CadastroResponsavelUI(String nome, String cpf, String telefone, String email, String endereco, String idade) {
        frame = new JFrame("Cadastro de Responsável");
        frame.setSize(490, 385);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel("Etapa 1 de 3 - Dados do Responsável", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setForeground(Color.BLUE);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(tituloLabel, BorderLayout.NORTH);

        JPanel camposPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        criarCampo("Nome:", 200, 25, camposPanel, nome);
        criarCampo("CPF:", 200, 25, camposPanel, cpf);
        criarCampo("Telefone:", 200, 25, camposPanel, telefone);
        criarCampo("E-mail:", 200, 25, camposPanel, email);
        criarCampo("Endereço:", 200, 25, camposPanel, endereco);
        criarCampoIdade("Idade:", 50, 25, camposPanel, idade);

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
                voltarParaEtapaAnterior(getResponsavel());
            }
        });
        voltarButton.setVisible(false);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botoesPanel.add(voltarButton);
        botoesPanel.add(avancarButton);
        frame.add(botoesPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void criarCampo(String label, int largura, int altura, JPanel panel, String valor) {
        JLabel labelCampo = new JLabel(label);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(largura, altura));
        textField.setText(valor);
        panel.add(labelCampo);
        panel.add(textField);

        // lógica para atribuir o campo correto com base no rótulo
        if (label.equals("Nome:")) {
            nomeField = textField;
        } else if (label.equals("CPF:")) {
            cpfField = textField;
        } else if (label.equals("Telefone:")) {
            telefoneField = textField;
        } else if (label.equals("E-mail:")) {
            emailField = textField;
        } else if (label.equals("Endereço:")) {
            enderecoField = textField;
        }
    }

    private void criarCampoIdade(String label, int largura, int altura, JPanel panel, String valor) {
        JLabel labelCampo = new JLabel(label);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(largura, altura));
        textField.setText(valor);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
        panel.add(labelCampo);
        panel.add(textField);

        // lógica para atribuir o campo correto com base no rótulo
        if (label.equals("Idade:")) {
            idadeField = textField;
        }
    }

    private void avancarParaProximaEtapa() {
    // Configura a flag para indicar que o botão "Avançar" foi clicado
    avancarClicado = true;

    // Verifica se o responsável é válido
    Responsavel responsavel = getResponsavel();
    if (responsavel != null) {
        SwingUtilities.invokeLater(() -> {
            if (cadastroCriancaUI == null) {
                cadastroCriancaUI = new CadastroCriancaUI(responsavel, this);
            } else {
                cadastroCriancaUI.setResponsavel(responsavel);
                cadastroCriancaUI.mostrarTela();
            }
        });
        frame.dispose();
    }
}

    private void voltarParaEtapaAnterior(Responsavel responsavel) {
        SwingUtilities.invokeLater(() -> {
            if (cadastroCriancaUI != null) {
                cadastroCriancaUI.setCrianca(crianca);
                cadastroCriancaUI.mostrarTela();
            } else {
                System.out.println("cadastroCriancaUI é null. Não é possível voltar para a etapa anterior.");
            }
            frame.dispose();
        });
    }

    public Responsavel getResponsavel() {
    String nome = nomeField.getText();
    String cpf = cpfField.getText();
    String telefone = telefoneField.getText();
    String email = emailField.getText();
    String endereco = enderecoField.getText();

    // Verifica se o campo de idade não está vazio e é maior ou igual a 18
    int idade = 0;
    if (!idadeField.getText().isEmpty()) {
        idade = Integer.parseInt(idadeField.getText());
        if (idade < 18) {
            JOptionPane.showMessageDialog(frame, "A idade do responsável deve ser igual ou maior que 18 anos.");
            return null; // Retorna null se a idade não for válida
        }
    } else {
        // Mostra o aviso apenas se o botão "Avançar" foi clicado
        if (avancarClicado) {
            JOptionPane.showMessageDialog(frame, "Por favor, preencha a idade do responsável.");
            return null; // Retorna null se o campo idade estiver vazio
        }
    }

    return new Responsavel(nome, cpf, telefone, email, endereco, idade);
}

    public void mostrarTela() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void setCrianca(Crianca crianca) {
        this.crianca = crianca;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CadastroResponsavelUI cadastroResponsavelUI = new CadastroResponsavelUI("", "", "", "", "", "");
            cadastroResponsavelUI.mostrarTela();
        });
    }

    public CadastroCriancaUI getProximaEtapa() {
        return new CadastroCriancaUI(getResponsavel(), this);
    }
}
