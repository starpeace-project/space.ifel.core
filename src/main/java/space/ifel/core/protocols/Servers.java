package space.ifel.core.protocols;

public enum Servers {
    DIRECTORY       (200),
    INTERFACE       (201),
    CACHE           (202),
    MAIL            (203),
    NEWS            (204),
    GAMEMASTER      (205);

    private final int serverType;

    Servers(int serverType) {
        this.serverType = serverType;
    }

    public int getServerType() {
        return this.serverType;
    }
}
