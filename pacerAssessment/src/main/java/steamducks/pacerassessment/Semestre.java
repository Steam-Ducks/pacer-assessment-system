package steamducks.pacerassessment;

import javafx.collections.ObservableList;

public class Semestre {
    private int id;
    private String nome;
    private ObservableList<Criterios> criterios;

    public Semestre(int id, String nome, ObservableList<Criterios> criterios) {
        this.id = id;
        this.nome = nome;
        this.criterios = criterios;
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

    public ObservableList<Criterios> getCriterios() {
        return criterios;
    }

    public void setCriterios(ObservableList<Criterios> criterios) {
        this.criterios = criterios;
    }

    @Override
    public String toString() {
        StringBuilder criteriosString = new StringBuilder();
        for (Criterios criterio : criterios) {
            criteriosString.append(criterio.getNome()).append(", "); // Exibir o nome dos critérios
        }
        if (criteriosString.length() > 0) {
            criteriosString.setLength(criteriosString.length() - 2); // Remover a última vírgula e espaço
        }

        return "Semestre{" +
                "nome='" + nome + '\'' +
                ", criterios=[" + criteriosString + "]" +
                '}';
    }
}