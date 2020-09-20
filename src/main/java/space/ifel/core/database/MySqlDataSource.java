package space.ifel.core.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import space.ifel.config.Config;

import java.beans.PropertyVetoException;

public class MySqlDataSource {
    private static MySqlDataSource ds;
    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    private MySqlDataSource(Config config) throws PropertyVetoException {
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl(getJdbcUrl(config));
        cpds.setUser(config.get().getMysql().getDatabaseUsername());
        cpds.setPassword(config.get().getMysql().getDatabasePassword());

        cpds.setMinPoolSize(10);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(config.get().getMysql().getConnectionPoolLimit());
    }

    private static String getJdbcUrl(Config config) {
        return "jdbc:mysql://" +
                config.get().getMysql().getServerAddress() +
                config.get().getMysql().getServerPort() +
                config.get().getMysql().getDatabaseName();
    }

    /**
     *Static method for getting instance.
     * @throws PropertyVetoException
     */
    public static MySqlDataSource getInstance(Config config) throws PropertyVetoException{
        if(ds == null){
            ds = new MySqlDataSource(config);
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
