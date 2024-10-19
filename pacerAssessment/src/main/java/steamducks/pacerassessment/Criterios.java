package steamducks.pacerassessment;

public class Criterios {

    private int id;
    private String nome;
    private String descricao;

    public Criterios(String nome, String descricao, int id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Criterios(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Criterios() {

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
