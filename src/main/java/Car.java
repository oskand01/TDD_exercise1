import java.util.List;

public class Car {

    private String color;
    private String regNo;
    private String brand;
    private String engineType;
    private int enginePower;
    private int numberOfPassengers;
    private List<String> equipment;
    private List<String> packages;
    private int price;

    public Car(String color,
               String brand,
               String regNo,
               String engineType,
               int enginePower,
               int numberOfPassengers,
               List<String> equipment,
               List<String> packages,
               int price) {

        this.color = color;
        this.brand = brand;
        this.regNo = regNo;
        this.engineType = engineType;
        this.enginePower = enginePower;
        this.numberOfPassengers = numberOfPassengers;
        this.equipment = equipment;
        this.packages = packages;
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public String getRegNo() {
        return this.regNo;
    }

    public String getEngineType() {
        return engineType;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public String getBrand() {
        return brand;
    }

    public List<String> getPackages() {
        return packages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
