package fun.felipe;

import fun.felipe.entities.Locate;
import fun.felipe.scheduler.GetAllStatusTask;
import fun.felipe.view.ClientView;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Client {
    private static Client instance;
    private final String address;
    private final int port;
    private final List<Locate> locates;
    private static ClientView view;
    private static ScheduledExecutorService scheduledExecutorService;
    private static ScheduledFuture<?> getAllStatusTask;

    public Client() {
        this.address = "localhost";
        this.port = 45000;
        this.locates = List.of(
                new Locate("luz_guarita", "Luz Guarita"),
                new Locate("ar_guarita", "Ar Guarita"),
                new Locate("luz_estacionamento", "Luz Estacionamento"),
                new Locate("luz_galpao_externo", "Luz Galpão Externo"),
                new Locate("luz_galpao_interno", "Luz Galpão Interno"),
                new Locate("luz_escritorios", "Luz Escritórios"),
                new Locate("ar_escritorios", "Ar Escritórios"),
                new Locate("luz_sala_reunioes", "Luz Sala Reuniões"),
                new Locate("ar_sala_reunioes", "Luz Sala Reuniões")
        );


    }

    public static void main(String[] args) {
        instance = new Client();
        view = new ClientView();
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        getAllStatusTask = scheduledExecutorService.scheduleAtFixedRate(new GetAllStatusTask(), 0, 1, TimeUnit.SECONDS);
    }

    public static Client getInstance() {
        return instance;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public List<Locate> getLocates() {
        return this.locates;
    }

    public static ClientView getView() {
        return view;
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public static ScheduledFuture<?> getGetAllStatusTask() {
        return getAllStatusTask;
    }

    public static void setGetAllStatusTask(ScheduledFuture<?> getAllStatusTask) {
        Client.getAllStatusTask = getAllStatusTask;
    }
}