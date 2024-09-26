package steamducks.pacerassessment;

public class Criterios {

    private static int contadorId = 0;
    private int id;
    private String nome;
    private String descricao;

    public Criterios(String nome, String descricao) {
        this.id = contadorId++;
        this.nome = nome;
        this.descricao = descricao;
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

    public int getId() {
        return id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
