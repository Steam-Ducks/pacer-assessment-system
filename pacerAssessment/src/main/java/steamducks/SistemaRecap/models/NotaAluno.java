package steamducks.SistemaRecap.models;

public class NotaAluno {

    private int nota;
    private String aluno;
    private String criterio;
    private String descCriterio;
    private String sprint;

    public NotaAluno(int nota, String aluno, String criterio, String descCriterio, String sprint) {
        this.nota = nota;
        this.aluno = aluno;
        this.criterio = criterio;
        this.descCriterio = descCriterio;
        this.sprint = sprint;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getDescCriterio() {
        return descCriterio;
    }

    public void setDescCriterio(String descCriterio) {
        this.descCriterio = descCriterio;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }
}
