public class Car {

    private String color;
    private String regNo;
    private String engineType;
    private int enginePower;
    private int numberOfPassengers;

    public Car(String color, String regNo, String engineType, int enginePower, int numberOfPassengers) {
        this.color = color;
        this.regNo = regNo;
        this.engineType = engineType;
        this.enginePower = enginePower;
        this.numberOfPassengers = numberOfPassengers;
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
}
