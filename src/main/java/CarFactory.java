public class CarFactory {

    private String brand;

    public CarFactory(String brand) {
        this.brand = brand;
    }

    public Car createNewCar(String color) {

        return new Car(color, brand);
    }
}
