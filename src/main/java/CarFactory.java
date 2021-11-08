public class CarFactory {

    private String brand;

    public CarFactory(String brand) {
        this.brand = brand;
    }

    public Car createNewCar(String color) {
        VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator = new VehicleRegistrationNumberGenerator();

        return new Car(color, brand, vehicleRegistrationNumberGenerator.nextVehicleRegistrationNumber());
    }
}
