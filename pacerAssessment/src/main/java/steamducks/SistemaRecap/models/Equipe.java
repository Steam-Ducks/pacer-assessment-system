package steamducks.SistemaRecap.models;

import javafx.collections.ObservableList;

public class Equipe {

    private int idEquipe;
    private String nome;
    private String github;
    private ObservableList<Usuario> usuarios;
    private int idSemestre;

    public Equipe(int idEquipe, String nome, String github, ObservableList<Usuario> usuarios, int idSemestre) {
        this.idEquipe = idEquipe;
        this.nome = nome;
        this.github = github;
        this.usuarios = usuarios;
        this.idSemestre = idSemestre;
    }

    public Equipe(String nome, String github, ObservableList<Usuario> usuarios, int idSemestre) {
        this.nome = nome;
        this.github = github;
        this.usuarios = usuarios;
        this.idSemestre = idSemestre;
    }

    public Equipe(int idEquipe, String nome, String github, int idSemestre) {
        this.idEquipe = idEquipe;
        this.nome = nome;
        this.github = github;
        this.idSemestre = idSemestre;
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

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
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

        return nome;
    }
}