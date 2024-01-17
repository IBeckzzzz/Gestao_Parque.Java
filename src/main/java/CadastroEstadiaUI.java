import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroEstadiaUI {
    private JFrame frame;
    private JTextField responsavelField, criancaField, tempoUtilizadoField, totalAPagarField;
    private CadastroCriancaUI cadastroCriancaUI;

    public CadastroEstadiaUI(Responsavel responsavel, Crianca crianca, CadastroCriancaUI cadastroCriancaUI) {
        this.cadastroCriancaUI = cadastroCriancaUI;

        frame = new JFrame("Cadastro de Estadia");
        frame.setSize(490, 385);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel("Etapa 3 de 3 - Dados da Estadia", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setForeground(Color.BLUE);
        tituloLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(tituloLabel, BorderLayout.NORTH);

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Ajuste para incluir o novo campo

        criarCampoNaoEditavel("Responsável:", responsavel.getNome(), camposPanel);
        criarCampoNaoEditavel("Criança:", crianca.getNome(), camposPanel);
        tempoUtilizadoField = new JTextField();
        totalAPagarField = new JTextField(); // Adicionado campo totalAPagarField
        criarCampo("Tempo Utilizado:", 200, 25, camposPanel);
        criarCampoNaoEditavel("Total a Pagar:", "", camposPanel); // Adicionado campo no painel

        JPanel painelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        painelCentral.add(camposPanel);

        frame.add(painelCentral, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setPreferredSize(new Dimension(80, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarParaEtapaAnterior(responsavel, crianca);
            }
        });

        JButton calcularButton = new JButton("Calcular e Mostrar Resumo");
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarResumo(responsavel, crianca);
            }
        });

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botoesPanel.add(voltarButton);
        botoesPanel.add(calcularButton);
        frame.add(botoesPanel, BorderLayout.SOUTH);
    }

    private void criarCampo(String label, int largura, int altura, JPanel panel) {
        JLabel labelCampo = new JLabel(label);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(largura, altura));
        panel.add(labelCampo);
        panel.add(textField);

        // Se o label for "Tempo Utilizado", configure o campo na classe para uso posterior
        if (label.equals("Tempo Utilizado:")) {
            tempoUtilizadoField = textField;
        }
    }

    private void criarCampoNaoEditavel(String label, String valor, JPanel panel) {
        JLabel labelCampo = new JLabel(label);

        if (label.equals("Responsável:") || label.equals("Criança:") || label.equals("Total a Pagar:")) {
            JTextField textField = new JTextField(valor);
            textField.setPreferredSize(new Dimension(200, 25));
            textField.setEditable(false);
            panel.add(labelCampo);
            panel.add(textField);

            // Se o label for "Total a Pagar:", configure o campo na classe para uso posterior
            if (label.equals("Total a Pagar:")) {
                totalAPagarField = textField;
            }
        } else {
            JLabel valorLabel = new JLabel(valor);
            valorLabel.setPreferredSize(new Dimension(200, 25));
            panel.add(labelCampo);
            panel.add(valorLabel);
        }
    }

    private void voltarParaEtapaAnterior(Responsavel responsavel, Crianca crianca) {
        SwingUtilities.invokeLater(() -> {
            if (cadastroCriancaUI != null) {
                cadastroCriancaUI.mostrarTela();
            } else {
                System.out.println("cadastroCriancaUI é null. Não é possível voltar para a etapa anterior.");
            }
            frame.dispose();
        });
    }

    public void mostrarTela() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    private void mostrarResumo(Responsavel responsavel, Crianca crianca) {
        String responsavelNome = responsavel.getNome();
        String cpfResponsavel = responsavel.getCpf();
        String telefoneResponsavel = responsavel.getTelefone();
        String emailResponsavel = responsavel.getEmail();
        String enderecoResponsavel = responsavel.getEndereco();
        int idadeResponsavel = responsavel.getIdade();
        String criancaNome = crianca.getNome();
        int idadeCrianca = crianca.getIdade();
        String sexoCrianca = crianca.getSexo();
        String tempoUtilizado = tempoUtilizadoField.getText();

        // Verificar se o campo "Tempo Utilizado" não está vazio
        if (tempoUtilizado.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira o Tempo Utilizado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;  // Saia do método se o campo estiver vazio
        }

        try {
            // Adicione o cálculo do total a pagar com base no tempo utilizado e aplique o desconto conforme os critérios
            double tempoUtilizadoValor = Double.parseDouble(tempoUtilizado);
            double valorPorMinuto = 1.50;
            double totalAPagar = tempoUtilizadoValor * valorPorMinuto;

            // Aplicar desconto conforme os critérios
            double desconto = 0.0;
            if (tempoUtilizadoValor > 20) {
                desconto = 0.05;
            }
            if (tempoUtilizadoValor > 40) {
                desconto = 0.10;
            }
            if (tempoUtilizadoValor > 60) {
                desconto = 0.15;
            }

            // Calcular o total com desconto
            double totalComDesconto = totalAPagar * (1 - desconto);

            // Atualizar o campo de total a pagar
            totalAPagarField.setText(String.format("%.2f", totalComDesconto));

            String mensagemResumo = String.format("Dados da Estadia \nNome Responsável: %s\nCPF Responsável: %s\nTelefone Responsável: %s\nE-mail Responsável: %s\nEndereço: %s\nIdade Responsável: %s\nCriança: %s\nIdade Criança: %s\nSexo da Criança: %s\nTempo Utilizado: %s\nTotal a Pagar: %.2f", responsavelNome, cpfResponsavel, telefoneResponsavel, emailResponsavel, enderecoResponsavel, idadeResponsavel, criancaNome, idadeCrianca, sexoCrianca, tempoUtilizado, totalComDesconto);

            JOptionPane.showMessageDialog(frame, mensagemResumo, "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Por favor, insira um valor válido para o Tempo Utilizado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Responsavel responsavel = new Responsavel("Nome do Responsável", "", "", "", "", 0);
            Crianca crianca = new Crianca("Nome da Criança", 5, "Masculino");
            CadastroEstadiaUI cadastroEstadiaUI = new CadastroEstadiaUI(responsavel, crianca, null);
            cadastroEstadiaUI.mostrarTela();
        });
    }
}
