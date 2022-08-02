package data;


import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Objects;

public class Human implements Serializable {
    private static final long serialVersionUID = 13L;

    @Expose
   private String name; //Поле не может быть null, Строка не может быть пустой

    public Human(String name) {
        if (name == null) {
            throw new NullPointerException("Значением поля name не может быть null!!");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Значением поля name не может быть null!!");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(name, human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                '}';
    }



}
