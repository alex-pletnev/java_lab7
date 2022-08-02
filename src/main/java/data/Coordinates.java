package data;


import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable, Comparable<Coordinates> {
    private static final long serialVersionUID = 11L;
    @Expose
    private float x; //Максимальное значение поля: 209
    @Expose
    Double y; //Максимальное значение поля: 33, Поле не может быть null

    public Coordinates(float x, Double y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.x, x) == 0 && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Coordinates o) {
        return (int) (Math.abs(getX()) + Math.abs(getY()))
                - (int) (Math.abs(o.getX()) + Math.abs(getY()));
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
