package steamducks.SistemaRecap.models;

public class Criterio {

    private int id;
    private String nome;
    private String descricao;
    private Integer nota; // Novo campo para armazenar a nota do critério

    // Construtor completo com ID e Nota
    public Criterio(int idCriterio, String nome, String descricao, Integer nota) {
        this.id = idCriterio;
        this.nome = nome;
        this.descricao = descricao;
        this.nota = nota;
    }

    // Construtor com ID (sem Nota)
    public Criterio(int idCriterio, String nome, String descricao) {
        this(idCriterio, nome, descricao, null);
    }

    // Construtor para novo Criterio (sem ID e sem Nota)
    public Criterio(String nome, String descricao) {
        this(0, nome, descricao, null);
    }

    // Construtor padrão (vazio)
    public Criterio() {
        this(0, "", "", null);
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

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return nome; // Exibe apenas o nome do critério
    }
}
