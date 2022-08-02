package data;

import java.io.Serializable;

public enum Government implements Serializable {

    ARISTOCRACY("ARISTOCRACY", "аристовратия"),
    GERONTOCRACY("GERONTOCRACY", "геронтократия"),
    DESPOTISM("DESPOTISM", "деспотизм"),
    TIMOCRACY("TIMOCRACY", "тимократия");

    private final String upReg;
    private final String lowReg;
    private static final long serialVersionUID = 12L;

    Government(String upReg, String lowReg) {
        this.upReg = upReg;
        this.lowReg = lowReg;
    }

    public String getUpReg() {
        return upReg;
    }

    public String getLowReg() {
        return lowReg;
    }

    @Override
    public String toString() {
        return "Government{" +
                "upReg='" + upReg + '\'' +
                ", lowReg='" + lowReg + '\'' +
                "} " + super.toString();
    }
}
