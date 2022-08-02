package data;


import SQLutil.SQLManager;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class City implements Comparable<City>, Serializable {
    private static final long serialVersionUID = 10L;
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Expose
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Expose
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Expose
    private Coordinates coordinates; //Поле не может быть null
    @Expose(deserialize = false)
    @SerializedName("creationDate")
    private String creationDateStr;
    @Expose
    private double area; //Значение поля должно быть больше 0
    @Expose
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    @Expose
    private double metersAboveSeaLevel;
    @Expose
    private float timezone; //Значение поля должно быть больше -13, Максимальное значение поля: 15
    @Expose
    private boolean capital;
    @Expose
    private Government government; //Поле может быть null
    @Expose
    private Human governor; //Поле может быть null
    private String username;

    public City() {
    }

    public City(String name, Coordinates coordinates, double area,
                Integer population, double metersAboveSeaLevel,
                float timezone, boolean capital,
                Government government, Human governor) {
        id = SQLManager.getNextId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.capital = capital;
        this.government = government;
        this.governor = governor;
        this.creationDateStr = String.valueOf(creationDate);

    }
    public City(long id,String name, Coordinates coordinates, double area,
                Integer population, double metersAboveSeaLevel,
                float timezone, boolean capital,
                Government government, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.capital = capital;
        this.government = government;
        this.governor = governor;
        this.creationDateStr = String.valueOf(creationDate);

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public double getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public float getTimezone() {
        return timezone;
    }

    public boolean isCapital() {
        return capital;
    }

    public Government getGovernment() {
        return government;
    }

    public Human getGovernor() {
        return governor;
    }

    public String getCreationDateStr() {
        return creationDateStr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDateStr(String creationDateStr) {
        this.creationDateStr = creationDateStr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(double metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    public boolean validation() {
        if (name == null || name.equals("")) {
            return false;
        }
        if (coordinates == null || coordinates.getX() > 209 || coordinates.getY() > 33) {
            return false;
        }
        if (area < 0) {
            return false;
        }
        if (population == null || population < 0) {
            return false;
        }
        if (timezone < -13 || timezone > 15) {
            return false;
        }
        if (government == null) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        return governor != null && !governor.getName().equals("");
    }

    @Override
    public int compareTo(City city) {
            return this.getCoordinates().compareTo(city.getCoordinates());

    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id && Double.compare(city.area, area) == 0 &&
                Double.compare(city.metersAboveSeaLevel, metersAboveSeaLevel) == 0 &&
                Float.compare(city.timezone, timezone) == 0 && capital == city.capital &&
                name.equals(city.name) && coordinates.equals(city.coordinates) &&
                creationDate.equals(city.creationDate) && population.equals(city.population) &&
                government == city.government && governor.equals(city.governor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate,
                area, population, metersAboveSeaLevel, timezone,
                capital, government, governor);
    }

    @Override
    public String toString() {
        return "City{" +
                "creationDate=" + creationDate +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDateStr='" + creationDateStr + '\'' +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", timezone=" + timezone +
                ", capital=" + capital +
                ", government=" + government +
                ", governor=" + governor +
                '}';
    }
}