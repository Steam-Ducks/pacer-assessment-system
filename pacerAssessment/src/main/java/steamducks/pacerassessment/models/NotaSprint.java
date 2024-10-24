package steamducks.pacerassessment.models;

public class NotaSprint {

    private int nota;
    private String turma;
    private String equipe;
    private String sprint;

    public NotaSprint(int nota, String turma, String equipe, String sprint) {
        this.nota = nota;
        this.turma = turma;
        this.equipe = equipe;
        this.sprint = sprint;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }
}

