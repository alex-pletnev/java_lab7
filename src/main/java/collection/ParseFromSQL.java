package collection;

import SQLutil.SQLManager;
import data.City;
import data.Coordinates;
import data.Government;
import data.Human;
import util.CollectionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ParseFromSQL {
    public void parseSQLToSet(CollectionManager collectionManager) {
        try {
            ArrayList<ArrayList<String>> arrayLists = SQLManager.parseSQL();
            for (ArrayList<String> arrayList : arrayLists) {
                City city = new City();
                city.setId(Long.parseLong(arrayList.get(0)));
                city.setName(arrayList.get(1));
                city.setCoordinates(new Coordinates(Float.parseFloat(arrayList.get(2)), Double.parseDouble(arrayList.get(3))));
                city.setCreationDate(LocalDate.parse(arrayList.get(4), DateTimeFormatter.ISO_LOCAL_DATE));
                city.setArea(Double.parseDouble(arrayList.get(5)));
                city.setPopulation(Integer.parseInt(arrayList.get(6)));
                city.setMetersAboveSeaLevel(Double.parseDouble(arrayList.get(7)));
                city.setTimezone(Float.parseFloat(arrayList.get(8)));
                city.setCapital(Boolean.parseBoolean(arrayList.get(9)));
                city.setGovernment(Government.valueOf(arrayList.get(10)));
                city.setGovernor(new Human(arrayList.get(11)));
                if (city.validation()) {
                    synchronized (collectionManager.getCollection()) {
                        collectionManager.getCollection().add(city);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
