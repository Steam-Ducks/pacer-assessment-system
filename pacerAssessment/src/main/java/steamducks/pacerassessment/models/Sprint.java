package steamducks.pacerassessment.models;

import java.time.LocalDate;

public class Sprint {
    private int idSprint;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int idSemestre;

    // Construtor completo
    public Sprint(int idSprint, LocalDate dataInicio, LocalDate dataFim, int idSemestre) {
        this.idSprint = idSprint;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idSemestre = idSemestre;
    }

    // Construtor sem id (para novos sprints)
    public Sprint(LocalDate dataInicio, LocalDate dataFim, int idSemestre) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idSemestre = idSemestre;
    }

    // Getters e Setters
    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
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
        return "Sprint{" +
                "idSprint=" + idSprint +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", idSemestre=" + idSemestre +
                '}';
    }
}
