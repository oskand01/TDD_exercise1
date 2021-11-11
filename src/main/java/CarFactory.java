import java.util.HashMap;
import java.util.Map;

public class CarFactory {

    private VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator;
    private String brand;
    private Map<String, Model> models = new HashMap<>();

    public CarFactory(VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator, String brand) {
        this.vehicleRegistrationNumberGenerator = vehicleRegistrationNumberGenerator;
        this.brand = brand;
    }

    public Car createNewCar(String modelAsString, String color) {
        Model model = models.get(modelAsString);
        if(model==null) throw new RuntimeException("Unknown model " + modelAsString);
        return new Car(color, vehicleRegistrationNumberGenerator.getNextRegNo(), model.getEngineType(), model.getEnginePower(), model.getNumberOfPassengers());
    }

    public void addModel(String model, String engineType, int enginePower, int numberOfPassengers) {
        models.put(model, new Model(model, engineType, enginePower, numberOfPassengers));
    }

    public static class Model {
        String model;
        String engineType;
        int enginePower;
        int numberOfPassengers;

        public Model(String model, String engineType, int enginePower, int numberOfPassengers) {
            this.model = model;
            this.engineType = engineType;
            this.enginePower = enginePower;
            this.numberOfPassengers = numberOfPassengers;
        }

        public String getModel() {
            return model;
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
}
