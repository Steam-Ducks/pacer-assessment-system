package steamducks.pacerassessment;

import java.util.Arrays;

public class Semestre {
    private String nome;
    private String[] atributos;

    public Semestre(String nome, String[] atributos) {
        this.nome = nome;
        this.atributos = atributos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getAtributos() {
        return atributos;
    }

    public void setAtributos(String[] atributos) {
        this.atributos = atributos;
    }

    @Override
    public String toString() {
        return "Semestre{" +
                "nome='" + nome + '\'' +
                ", atributos='" + Arrays.toString(atributos) + '\'' +
                '}';

    }
}
