package steamducks.pacerassessment.models;

import javafx.collections.ObservableList;

public class Equipe {

    private String nome;
    private String gitHub;
    private ObservableList<Usuario> usuarios;
    private String semestre;

    public Equipe(String nome, String gitHub, ObservableList<Usuario> usuarios, String semestre) {
        this.nome = nome;
        this.gitHub = gitHub;
        this.usuarios = usuarios;
        this.semestre = semestre;
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

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
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
                ", semestre='" + semestre + '\'' +
                ", usuarios=[" + usuariosString + "]" +
                '}';
    }
}
