package space.ifel.core.protocols;

public enum Clients {
    GAMECLIENT      (300),
    WEBSITE         (301),
    GAMEMASTER      (302);

    private final int clientType;

    Clients(int clientType) {
        this.clientType = clientType;
    }

    public int getClientType() {
        return this.clientType;
    }
}
