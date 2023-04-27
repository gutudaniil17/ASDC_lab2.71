import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.lang.String;

/**
 * Основной класс - участники соревнования
 * имплементирует интерфейс Comporable для сортировки участников по какому либо параметру
 */

public class Competitor implements Comparable {
    private String name;
    private String surname;
    private String country;
    private int yearOfBirthday;
    private Category category;
    private int numberInTable;

    /**
     * Конструктор без параметров
     */
    public Competitor() {
    }

    /**
     * Фабричный метод для создания копии объекта
     * @param obj - объект, который будет скопирован
     * @return - копия объекта
     */
    public static Competitor createCompetitorFromObject(Object obj){
        Competitor competitor = new Competitor();
        Competitor competitor1 = (Competitor) obj;
        String name = competitor1.name;
        String surname = competitor1.surname;
        String country = competitor1.country;
        int yearOfBirthday = competitor1.yearOfBirthday;
        int numberInTable = competitor1.numberInTable;
        Category category = competitor1.category;
        competitor = new Competitor();
        competitor.setAllParam(name,surname,country,yearOfBirthday,category,numberInTable);

        return competitor;
    }

    /**
     * Фабричный метод для создания объекта со строки
     * @param obj - строка, содержащая параметры объекта
     * @return
     */
    public static Competitor createCompetitorFromString(Object obj){
        Competitor competitor;
        String csvLine = (String) obj;
        String[] arrParam = csvLine.split(";");
        String name = arrParam[0];
        String surname = arrParam[1];
        String country = arrParam[2];
        int yearOfBirthday = Integer.parseInt(arrParam[3]);
        int numberInTable = Integer.parseInt(arrParam[4]);
        Category category = Category.valueOf(arrParam[5]);
        competitor = new Competitor();
        competitor.setAllParam(name,surname,country,yearOfBirthday,category,numberInTable);
        return competitor;
    }

    /**
     * Метод задающий параметры объекту
     * @param name - имя
     * @param surname - фамилия
     * @param country - страна
     * @param dateOfBirthday - год рожения
     * @param category - спортивная категория
     * @param numberInTable - номер в таблице
     */
    public void setAllParam(String name, String surname, String country, int dateOfBirthday, Category category, int numberInTable){
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.yearOfBirthday = dateOfBirthday;
        this.category = category;
        this.numberInTable = numberInTable;
    }


    /**
     * Метод для получения значения участника по таблице
     * @return
     */
    public int getNumberInTable() {
        return numberInTable;
    }

    ///Constructor from string
/*    public Competitor(String csvLine) {
        String[] arrParam = csvLine.split(";");
        name = arrParam[0];
        surname = arrParam[1];
        country = arrParam[2];
        yearOfBirthday = Integer.parseInt(arrParam[3]);
        numberInTable = Integer.parseInt(arrParam[4]);
        category = Category.valueOf(arrParam[5]);
    }*/

    /**
     * Конструктор копирования
     * @param comp - объект, который будет скопирован
     */
    ///Constructor copy
    public Competitor(Competitor comp) {
        this.name = comp.name;
        this.surname = comp.surname;
        this.country = comp.country;
        this.category = comp.category;
        this.yearOfBirthday = comp.yearOfBirthday;
        this.numberInTable = comp.numberInTable;
    }

    /**
     * Метод копирования
     * @return - возвращает копию объекта
     */
    public Competitor copy() {
        return createCompetitorFromObject(this);
    }


    /**
     * Метод сравнения на эквивалентность объектов
     * @param o - объект, с которым будет сравниваться
     * @return - возвращает истину, если два объекта одинаковы и ложь, в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competitor that = (Competitor) o;
        return yearOfBirthday == that.yearOfBirthday &&
                numberInTable == that.numberInTable &&
                name.equals(that.name) &&
                surname.equals(that.surname) &&
                country.equals(that.country) &&
                category == that.category;
    }


    /**
     * Метод чтения списка объектов с файла
     * @param resource - файл для чтения
     * @return - список объектов
     */
    public static ArrayList<Competitor> input(File resource) {
        ArrayList<Competitor> competitorList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(resource))) {
            String st;
            while ((st = br.readLine()) != null) {
                if (st.isEmpty()) {
                } else {
                    competitorList.add(createCompetitorFromString(st));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return competitorList;
    }


    /**
     * Метод чтения объектов со строки
     * @param resource - строка с параметрами объектов
     * @return - список объектов
     */
    public static ArrayList<Competitor> input(String resource) {
        ArrayList<Competitor> competitorList = new ArrayList<>();
        String[] array = resource.split("/n");
        for (String str : array) {
            competitorList.add(createCompetitorFromString(str));
        }
        return competitorList;
    }

    /**
     * Метод парсирования объекта в строку
     * @return возвращает строчное представление объекта
     */
    public String competitorToCSV() {
        String name = this.name;
        String surname = this.surname;
        String country = this.country;
        String year = String.valueOf(this.yearOfBirthday);
        String number = String.valueOf(this.numberInTable);
        String category = String.valueOf(this.category);
        return new String(name + ";" + surname + ";" + country + ";" + year + ";" + number + ";" + category);
    }

    /**
     * Метод записи списка объектов в файл
     * @param competitors - список объектов для записи
     * @param resources - файл для записи
     */
    public static void output(ArrayList<Competitor> competitors, File resources) {
        try {
            FileWriter fileWriter = new FileWriter(resources);
            for (Competitor comp : competitors) {
                fileWriter.write(comp.competitorToCSV() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод вывода списка объектов в консоль
     * @param competitors - список объектов для вывода
     */
    public static void output(ArrayList<Competitor> competitors) {
        for (Competitor comp : competitors) {
            System.out.println(comp.toString());
        }
    }


    /**
     * Метод сравнения объектов
     * @param competitor the object to be compared.
     * @return -
     */
    @Override
    public int compareTo(Object competitor) {
        int comparNumber = ((Competitor) competitor).getNumberInTable();
        return this.numberInTable - comparNumber;
    }

    /**
     * Метод для строчного представления объекта
     * @return - возвращает объект ввиде строк
     */
    @Override
    public String toString() {
        return "Competitor{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", country='" + country + '\'' +
                ", yearOfBirthday=" + yearOfBirthday +
                ", category=" + category +
                ", numberInTable=" + numberInTable +
                '}';
    }
}
