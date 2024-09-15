import java.util.List;

public class View {
    public static void mostrarIntroducao() {
        System.out.println("Iniciando a Simulação de Treino de Fórmula 1...");
    }

    public static void mostrarTempoPiloto(PilotData dadosPiloto) {
        System.out.println(dadosPiloto);
    }

    public static void mostrarResultados() {
        List<PilotData> pilotosOrdenados = ControleTreinoFormula1.obterPilotosOrdenados();
        System.out.println("Classificação final:");
        for (PilotData dadosPiloto : pilotosOrdenados) {
            System.out.println(dadosPiloto);
        }
    }
}
