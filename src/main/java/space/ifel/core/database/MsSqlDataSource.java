package space.ifel.core.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import space.ifel.config.Config;

import java.beans.PropertyVetoException;

public class MsSqlDataSource {
    private static MsSqlDataSource ds;
    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    private MsSqlDataSource(Config config) throws PropertyVetoException {
        cpds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        cpds.setJdbcUrl(getJdbcUrl(config));
        cpds.setUser(config.get().getMysql().getDatabaseUsername());
        cpds.setPassword(config.get().getMysql().getDatabasePassword());

        cpds.setMinPoolSize(10);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(config.get().getMysql().getConnectionPoolLimit());
    }

    private static String getJdbcUrl(Config config) {
        return "jdbc:sqlserver://" +
                config.get().getMssql().getServerAddress() +
                config.get().getMssql().getServerPort() + ";" +
                "servername=" + config.get().getMssql().getDatabaseName() +
                ";integratedSecurity=true;authenticationScheme=JavaKerberos";
    }

    /**
     * Static method for getting instance.
     *
     * @throws PropertyVetoException
     */
    public static MsSqlDataSource getInstance(Config config) throws PropertyVetoException {
        if (ds == null) {
            ds = new MsSqlDataSource(config);
        }
        return ds;
    }

    public ComboPooledDataSource getCpds() {
        return cpds;
    }

    public void setCpds(ComboPooledDataSource cpds) {
        this.cpds = cpds;
    }
}