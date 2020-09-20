package space.ifel.core.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login {
    String alias;
    String ip;
    String when;

    public Login(String login) {
        String[] loginBits = login.split("-");
        this.alias = loginBits[0];
        this.ip = loginBits[1];
        this.when = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public String getAlias() {
        return alias;
    }

    public String getIp() {
        return ip;
    }

    public String getWhen() {
        return when;
    }
}
