package steamducks.pacerassessment;

public class Criterios {

    private static int contadorId = 0;
    private int Id;
    private String nome;
    private String descricao;

    public Criterios(String nome, String descricao, int id) {
        this.Id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Criterios(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Criterios() {

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

    public int getId() {
        return Id;
    }



    public void setId(int id) {
        Id = id;
    }
}
