package steamducks.pacerassessment.models;

import javafx.collections.ObservableList;

public class Equipe {

    private int idEquipe;
    private String nome;
    private String gitHub;
    private ObservableList<Usuario> usuarios;
    private int IdSemestre;

    public Equipe(int idEquipe, String nome, String gitHub, ObservableList<Usuario> usuarios, int semestre) {
        this.idEquipe = idEquipe;
        this.nome = nome;
        this.gitHub = gitHub;
        this.usuarios = usuarios;
        this.IdSemestre = semestre;
    }

    public Equipe(String nome, String gitHub, ObservableList<Usuario> usuarios, int semestre) {
        this.nome = nome;
        this.gitHub = gitHub;
        this.usuarios = usuarios;
        this.IdSemestre = semestre;
    }

    public Equipe() {

    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public int getSemestre() {
        return IdSemestre;
    }

    public void setSemestre(int semestre) {
        this.IdSemestre = semestre;
    }

    public ObservableList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ObservableList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        StringBuilder usuariosString = new StringBuilder();
        for (Usuario usuario : usuarios) {
            usuariosString.append(usuario.toString()).append(", \n");
        }
        if (usuariosString.length() > 0) {
            usuariosString.setLength(usuariosString.length() - 2);
        }

        return "Equipe{" +
                "nome='" + nome + '\'' +
                ", gitHub='" + gitHub + '\'' +
                ", semestre='" + IdSemestre + '\'' +
                ", usuarios=[" + usuariosString + "]" +
                '}';
    }
}
