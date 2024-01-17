public class Responsavel {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;
    private int idade;

      public Responsavel(String nome, String cpf, String telefone, String email, String endereco, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.idade = idade;
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

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getIdade() {
        return idade;
    }

}
