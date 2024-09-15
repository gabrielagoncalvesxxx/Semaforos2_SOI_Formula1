import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Controler {
    private static final int MAX_CARROS_PISTA = 5;
    private static final Semaphore semaforoPista = new Semaphore(MAX_CARROS_PISTA);
    private static final List<PilotData> pilotos = Collections.synchronizedList(new ArrayList<>());

    public static void executarSimulacao() {
        for (int i = 0; i < 7; i++) {
            new Thread(new Equipe(i)).start();
        }
    }

    public static List<PilotData> obterPilotosOrdenados() {
        List<PilotData> pilotosOrdenados = new ArrayList<>(pilotos);
        pilotosOrdenados.sort(Comparator.comparingLong(pd -> pd.tempoVolta));
        return pilotosOrdenados;
    }

    static class Equipe implements Runnable {
        private final int idEquipe;

        Equipe(int idEquipe) {
            this.idEquipe = idEquipe;
        }

        @Override
        public void run() {
            for (int idCarro = 0; idCarro < 2; idCarro++) {
                new Thread(new Piloto(idEquipe, idCarro)).start();
            }
        }
    }

    static class Piloto implements Runnable {
        private final int idEquipe;
        private final int idCarro;
        private final Random random = new Random();

        Piloto(int idEquipe, int idCarro) {
            this.idEquipe = idEquipe;
            this.idCarro = idCarro;
        }

        @Override
        public void run() {
            try {
                semaforoPista.acquire();
                long tempoInicio = System.currentTimeMillis();

                System.out.println("Piloto " + idEquipe + "-" + idCarro + " está na pista.");

                for (int volta = 0; volta < 3; volta++) {
                    Thread.sleep(1000 + random.nextInt(2000)); // Simula o tempo de volta
                }

                long tempoFim = System.currentTimeMillis();
                long tempoVolta = tempoFim - tempoInicio;
                pilotos.add(new PilotData(idEquipe, idCarro, tempoVolta));

                View.mostrarTempoPiloto(new PilotData(idEquipe, idCarro, tempoVolta));

                semaforoPista.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class PilotData {
        private final int idEquipe;
        private final int idCarro;
        private final long tempoVolta;

        PilotData(int idEquipe, int idCarro, long tempoVolta) {
            this.idEquipe = idEquipe;
            this.idCarro = idCarro;
            this.tempoVolta = tempoVolta;
        }

        @Override
        public String toString() {
            return "Equipe " + idEquipe + ", Carro " + idCarro + ": " + tempoVolta + " ms";
        }
    }
}
