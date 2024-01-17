public class Estadia {
    private String responsavelNome;
    private String cpfResponsavel;
    private String telefoneResponsavel;
    private String emailResponsavel;
    private String enderecoResponsavel;
    private int idadeResponsavel;
    private String criancaNome;
    private int idadeCrianca;
    private String sexoCrianca;
    private String tempoUtilizado;

    public Estadia(
            String responsavelNome,
            String cpfResponsavel,
            String telefoneResponsavel,
            String emailResponsavel,
            String enderecoResponsavel,
            int idadeResponsavel,
            String criancaNome,
            int idadeCrianca,
            String sexoCrianca,
            String tempoUtilizado
    ) {
        this.responsavelNome = responsavelNome;
        this.cpfResponsavel = cpfResponsavel;
        this.telefoneResponsavel = telefoneResponsavel;
        this.emailResponsavel = emailResponsavel;
        this.enderecoResponsavel = enderecoResponsavel;
        this.idadeResponsavel = idadeResponsavel;
        this.criancaNome = criancaNome;
        this.idadeCrianca = idadeCrianca;
        this.sexoCrianca = sexoCrianca;
        this.tempoUtilizado = tempoUtilizado;
    }

    public String getResponsavelNome() {
        return responsavelNome;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public String getEnderecoResponsavel() {
        return enderecoResponsavel;
    }

    public int getIdadeResponsavel() {
        return idadeResponsavel;
    }

    public String getCriancaNome() {
        return criancaNome;
    }

    public int getIdadeCrianca() {
        return idadeCrianca;
    }

    public String getSexoCrianca() {
        return sexoCrianca;
    }

    public String getTempoUtilizado() {
        return tempoUtilizado;
    }

    public void setTempoUtilizado(String tempoUtilizado) {
        this.tempoUtilizado = tempoUtilizado;
    }
}
