package space.ifel.core.protocols;

public enum Directory {
    SUCCESS                     (100),
    LOGIN_FAILED                (101),
    BLOCKED                     (102),
    NOT_ALLOWED                 (103),
    TO_MANY_ATTEMPTS            (104),
    TIMED_BAN                   (105),
    PERMANENT_BAN               (106),
    SESSION_EXPIRED             (107);

    private final int levelCode;

    Directory(int levelCode) {
        this.levelCode = levelCode;
    }

    public int getLevelCode() {
        return this.levelCode;
    }
}