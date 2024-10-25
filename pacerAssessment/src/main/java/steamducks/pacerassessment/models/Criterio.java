package steamducks.pacerassessment.models;

public class Criterio {

    private int id;
    private String nome;
    private String descricao;

    // Construtor completo com ID
    public Criterio(int idCriterio, String nome, String descricao) {
        this.id = idCriterio;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Construtor para novo Criterio (sem ID)
    public Criterio(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Construtor padrão (vazio)
    public Criterio() {
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return nome; // Exibe apenas o nome do critério
    }
}
