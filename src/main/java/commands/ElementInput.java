package commands;

import data.City;
import data.Coordinates;
import data.Government;
import data.Human;
import exception.IncorrectCoordinateException;

import java.util.Scanner;

public class ElementInput {
    public static City getNewElement(Scanner input, int mode) {
            if (mode == 0) System.out.println("name: ");
            boolean nameFlag = false;
            String name = null;
            while (!nameFlag) {
                String nameStr = input.nextLine();
                if (nameStr == null || nameStr.equals("")) {
                    if (mode == 0) System.err.print("Поле не может быть null, Строка не может быть пустой\n");
                    if (mode == 1) return null;
                } else {
                    name = nameStr;
                    nameFlag = true;
                }
            }

            if (mode == 0) System.out.println("coordinates x:");
            boolean xFlag = false;
            float x = 0;

            while (!xFlag) {
                String xStr = input.nextLine();
                try {
                    x = Float.parseFloat(xStr);
                    if (x > 209) {
                        throw new IncorrectCoordinateException();
                    }
                    xFlag = true;
                } catch (NumberFormatException ex) {
                    if (mode == 0) System.err.println("Максимальное значение поля: 209(тип данных float)");
                    if (mode == 1) return null;
                }
            }

            if (mode == 0) System.out.println("coordinates y:");
            boolean yFlag = false;
            Double y = 0.0;

            while (!yFlag) {
                String yStr = input.nextLine();
                try {
                    y = Double.parseDouble(yStr);
                    if (y > 33) {
                        throw new IncorrectCoordinateException();
                    }
                    yFlag = true;
                } catch (NumberFormatException ex) {
                    if (mode == 0)
                        System.err.println("Максимальное значение поля: 33, Поле не может быть null(тип данных Double)");
                    if (mode == 1) return null;
                }
            }
            Coordinates coordinates = new Coordinates(x, y);
            if (mode == 0) System.out.println("area: ");
            boolean areaFlag = false;
            double area = 0;
            while (!areaFlag) {
                String areaStr = input.nextLine();
                try {
                    area = Double.parseDouble(areaStr);
                    if (area < 0) {
                        throw new NumberFormatException();
                    }
                    areaFlag = true;
                } catch (NumberFormatException ex) {
                    if (mode == 0) System.err.println("Значение поля должно быть больше 0(тип данных double)");
                    if (mode == 1) return null;
                }
            }
            if (mode == 0) System.out.println("population: ");
            boolean popFlag = false;
            Integer pop = 0;
            while (!popFlag) {
                String popStr = input.nextLine();
                try {
                    pop = Integer.parseInt(popStr);
                    if (pop <= 0) {
                        throw new NumberFormatException();
                    }
                    popFlag = true;
                } catch (NumberFormatException ex) {
                    if (mode == 0)
                        System.err.println("Значение поля должно быть больше 0, Поле не может быть null(тип данных Integer)");
                    if (mode == 1) return null;
                }
            }
            if (mode == 0) System.out.println("metersAboveSeaLevel: ");
            boolean sealFlag = false;
            double seal = 0;
            while (!sealFlag) {
                String sealStr = input.nextLine();
                try {
                    seal = Double.parseDouble(sealStr);
                    sealFlag = true;
                } catch (NumberFormatException ex) {
                    if (mode == 0) System.err.println("(тип данных double)");
                    if (mode == 1) return null;
                }
            }
            if (mode == 0) System.out.println("timezone: ");
            boolean timeZFlag = false;
            float timeZ = 0;
            while (!timeZFlag) {
                String timeZStr = input.nextLine();
                try {
                    timeZ = Float.parseFloat(timeZStr);
                    if (!(timeZ > -13 && timeZ <= 15)) {
                        throw new NumberFormatException();
                    }
                    timeZFlag = true;
                } catch (NumberFormatException ex) {
                    if (mode == 0)
                        System.err.println("Значение поля должно быть больше -13, Максимальное значение поля: 15(тип данных float)");
                    if (mode == 1) return null;
                }
            }
            if (mode == 0) System.out.println("capital: ");
            boolean capitalFlag = false;
            boolean capital = false;
            while (!capitalFlag) {
                String capitalStr = input.nextLine();
                if (capitalStr.matches("[Tt]rue|[Ff]alse")) {
                    capital = Boolean.parseBoolean(capitalStr);
                    capitalFlag = true;
                } else {
                    if (mode == 0) System.err.print("(тип данных boolean)\n");
                    if (mode == 1) return null;
                }
            }

            if (mode == 0) System.out.println("government:\nARISTOCRACY,\nGERONTOCRACY,\n DESPOTISM,\n TIMOCRACY;");
            Government government = null;
            boolean governmentFlag = false;
            while (!governmentFlag) {
                String governmentStr = input.nextLine();
                if (governmentStr.matches("ARISTOCRACY|GERONTOCRACY|DESPOTISM|TIMOCRACY")) {
                    government = Government.valueOf(governmentStr);
                    governmentFlag = true;
                } else {
                    if (mode == 0)
                        System.err.print("допустимые значения:\\nARISTOCRACY,\\nGERONTOCRACY,\\n DESPOTISM,\\n TIMOCRACY;\"");
                    if (mode == 1) return null;
                }
            }
            if (mode == 0) System.out.println("governor: ");
            String gName = null;
            boolean gNameFlag = false;
            while (!gNameFlag) {
                String gNameStr = input.nextLine();
                if (gNameStr == null || gNameStr.equals("")) {
                    if (mode == 0) System.err.print("Поле не может быть null, Строка не может быть пустой\n");
                    if (mode == 1) return null;
                } else {
                    gName = gNameStr;
                    gNameFlag = true;
                }
            }
            Human governor = new Human(gName);
            return new City(0, name, coordinates, area, pop, seal, timeZ, capital, government, governor);

    }
}
