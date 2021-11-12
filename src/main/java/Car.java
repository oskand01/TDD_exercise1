import java.util.List;

public class Car {

    private String color;
    private String regNo;
    private String engineType;
    private int enginePower;
    private int numberOfPassengers;
    private List<String> equipment;

    public Car(String color, String regNo, String engineType, int enginePower, int numberOfPassengers, List<String> equipment) {
        this.color = color;
        this.regNo = regNo;
        this.engineType = engineType;
        this.enginePower = enginePower;
        this.numberOfPassengers = numberOfPassengers;
        this.equipment = equipment;
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
}
