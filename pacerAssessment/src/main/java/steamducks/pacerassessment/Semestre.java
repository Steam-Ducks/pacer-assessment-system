package steamducks.pacerassessment;

import java.util.Arrays;

public class Semestre {
    private String nome;
    private String[] criterios;

    public Semestre(String nome, String[] criterios) {
        this.nome = nome;
        this.criterios = criterios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getCriterios() {
        return criterios;
    }

    public void setCriterios(String[] criterios) {
        this.criterios = criterios;
    }

    @Override
    public String toString() {
        return "Semestre{" +
                "nome='" + nome + '\'' +
                ", criterios='" + Arrays.toString(criterios) + '\'' +
                '}';

    }
}
