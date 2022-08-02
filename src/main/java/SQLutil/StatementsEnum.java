package SQLutil;

public enum StatementsEnum {
    CREATE_AN_ACCOUNT("INSERT INTO users (username, password) VALUES(?, ?)"),
    GET_EXIST_USERS("SELECT * FROM users WHERE username = ?"),
    SING_IN("SELECT * FROM users WHERE username = ? AND password = ?"),
    ADD("INSERT INTO Cities " +
            "(id, name, x, y, creationDate, area, population, metersAboveSeaLevel, timezone, capital, government, " +
            "governor, username) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
    CLEAR("DELETE FROM Cities WHERE username = ?"),
    REMOVE_BY_ID("DELETE FROM Cities WHERE username = ? AND id = ?"),
    REMOVE_GREATER("DELETE FROM Cities WHERE ABS(x) + ABS(y) - ABS(?) - ABS(?) > 0 AND username = ?"),
    ALL_CITY("SELECT * FROM Cities"),
    GET_NEXT_ID("SELECT nextval('sequence')"),
    FIND_CITY_BY_ID("SELECT * FROM Cities WHERE username = ? AND id = ?"),
    FIND_ANY_GREATER("SELECT * FROM Cities WHERE username = ? AND ABS(x) + ABS(y) - ABS(?) - ABS(?)")

    ;

    private final String statement;

    StatementsEnum(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
