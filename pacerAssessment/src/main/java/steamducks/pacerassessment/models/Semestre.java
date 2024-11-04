package steamducks.pacerassessment.models;

public class Semestre {

    private int id;
    private String nome;

    public Semestre(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

    @Override
    public String toString() {

        return nome;
    }
}
