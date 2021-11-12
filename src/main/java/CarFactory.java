import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarFactory {

    private VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator;
    private String brand;
    private Map<String, Model> models = new HashMap<>();

    public CarFactory(VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator, String brand) {
        this.vehicleRegistrationNumberGenerator = vehicleRegistrationNumberGenerator;
        this.brand = brand;
    }

    public Car createNewCar(String modelAsString, String color, List<String> equipment) throws MissingModelException {
        Model model = models.get(modelAsString);
        if (model == null) throw new MissingModelException(modelAsString);
        List<String> allEquipment = new ArrayList<>(equipment);
        allEquipment.addAll(model.getEquipment());

        return new Car(
                color,
                vehicleRegistrationNumberGenerator.getNextRegNo(),
                model.getEngineType(),
                model.getEnginePower(),
                model.getNumberOfPassengers(),
                allEquipment);
    }

    public void addModel(String model, String engineType, int enginePower, int numberOfPassengers, List<String> equipment) {
        models.put(model, new Model(model, engineType, enginePower, numberOfPassengers, equipment));
    }

    public static class Model {
        String model;
        String engineType;
        int enginePower;
        int numberOfPassengers;
        private List<String> equipment;

        public Model(String model, String engineType, int enginePower, int numberOfPassengers, List<String> equipment) {
            this.model = model;
            this.engineType = engineType;
            this.enginePower = enginePower;
            this.numberOfPassengers = numberOfPassengers;
            this.equipment = equipment;
        }

        public List<String> getEquipment() {
            return equipment;
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
