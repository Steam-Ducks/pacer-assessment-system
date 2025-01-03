package steamducks.SistemaRecap.models;

public class Avaliacao {
    private int id;
    private int nota;
    private String emailAvaliador;
    private String emailAvaliado;
    private int idSprint;
    private int idCriterio;

    // Construtor
    public Avaliacao(int id, int nota, String emailAvaliador, String emailAvaliado, int idSprint, int idCriterio) {
        this.id = id;
        this.nota = nota;
        this.emailAvaliador = emailAvaliador;
        this.emailAvaliado = emailAvaliado;
        this.idSprint = idSprint;
        this.idCriterio = idCriterio;
    }

    public Avaliacao(int nota, String emailAvaliador, String emailAvaliado, int idSprint, int idCriterio) {
        this.nota = nota;
        this.emailAvaliador = emailAvaliador;
        this.emailAvaliado = emailAvaliado;
        this.idSprint = idSprint;
        this.idCriterio = idCriterio;
    }

    public Avaliacao (){}

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getEmailAvaliador() {
        return emailAvaliador;
    }

    public void setEmailAvaliador(String emailAvaliador) {
        this.emailAvaliador = emailAvaliador;
    }

    public String getEmailAvaliado() {
        return emailAvaliado;
    }

    public void setEmailAvaliado(String emailAvaliado) {
        this.emailAvaliado = emailAvaliado;
    }

    public int getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(int idSprint) {
        this.idSprint = idSprint;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", nota=" + nota +
                ", emailAvaliador='" + emailAvaliador + '\'' +
                ", emailAvaliado='" + emailAvaliado + '\'' +
                ", idSprint=" + idSprint +
                ", idCriterio=" + idCriterio +
                '}';
    }
}
