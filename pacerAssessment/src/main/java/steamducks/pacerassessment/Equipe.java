package steamducks.pacerassessment;

import javafx.collections.ObservableList;

public class Equipe {

    private String nome;
    private String gitHub;

    private ObservableList<Aluno> alunos;

    public Equipe(String nome, String gitHub, ObservableList<Aluno> alunos, String semestre) {
        this.nome = nome;
        this.gitHub = gitHub;
        this.alunos = alunos;
        this.semestre = semestre;
    }

    //private Semestre semestre;
    private String semestre;


    public String getNome() {
        return nome;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getSemestre() {
        return semestre;
    }

    public ObservableList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ObservableList<Aluno> alunos) {
        this.alunos = alunos;
    }


    @Override
    public String toString() {
        StringBuilder alunosString = new StringBuilder();
        for (Aluno aluno : alunos) {
            alunosString.append(aluno.toString()).append(", \n");
        }
        if (alunosString.length() > 0) {
            alunosString.setLength(alunosString.length() - 2);
        }

        return "Equipe{" +
                "nome='" + nome + '\'' +
                ", gitHub='" + gitHub + '\'' +
                ", semestre='" + semestre + '\'' +
                ", alunos=[" + alunosString + "]" +
                '}';
    }
}
