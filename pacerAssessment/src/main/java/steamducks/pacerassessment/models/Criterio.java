package steamducks.pacerassessment.models;

public class Criterio {

    private int id;
    private String nome;
    private String descricao;

    public Criterio(String nome, String descricao, int id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Criterio(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Criterio() {

    }

    public Criterio(int idCriterio, String nome, String descricao) {
        this.id = idCriterio;
        this.nome = nome;
        this.descricao = descricao;
    }

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
public String toString(){
        return nome; //Exibe apenas o nome do criterio
    };
}