module space.ifel.core {
    requires space.ifel.config;
    requires java.desktop;
    requires c3p0;
    requires java.naming;
    requires java.sql;

    exports space.ifel.core.models;
    exports space.ifel.core.database;
    exports space.ifel.core.messages;
    exports space.ifel.core.protocols;
    exports space.ifel.core.servers;
}