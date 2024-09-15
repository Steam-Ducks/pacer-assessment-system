package steamducks.pacerassessment;

public class Criterios {

    private static int contadorId = 1;
    private int id;
    private String criterio;

    public Criterios(String criterio) {
        this.id = contadorId++;
        this.criterio = criterio;
    }

    public int getId() {
        return id;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }
}
