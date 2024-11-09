package steamducks.pacerassessment.models;

import java.time.LocalDate;

public class Sprint {
    private int idSprint;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int idSemestre ;

    // Construtor completo
    public Sprint(int idSprint, String nome, LocalDate dataInicio, LocalDate dataFim, int idSemestre) {
        this.idSprint = idSprint;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idSemestre = idSemestre;
    }

    // Construtor sem id (para novos sprints)
    public Sprint(String nome, LocalDate dataInicio, LocalDate dataFim, int idSemestre) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idSemestre = idSemestre;
    }

    public Sprint(String nome, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public  Sprint(){}
    // Getters e Setters
    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }

    @Override
    public String toString() {
        return nome;
    }
}