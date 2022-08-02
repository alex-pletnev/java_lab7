package util;

import java.io.Serializable;

public class Reply implements Serializable {
    private String answer;
    private boolean status = true;

    public Reply(String answer) {
        this.answer = answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return answer;
    }
}
