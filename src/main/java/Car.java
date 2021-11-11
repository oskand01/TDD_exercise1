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

    public void setColor(String color) {
        this.color = color;
    }



    public String getRegNo() {
        return this.regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
