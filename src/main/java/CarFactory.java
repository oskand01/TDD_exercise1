import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarFactory {

    private VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator;
    private String brand;
    private Map<String, Model> models = new HashMap<>();
    private Map<String, CarPackage> carPackages = new HashMap<>();

    public CarFactory(VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator, String brand) {
        this.vehicleRegistrationNumberGenerator = vehicleRegistrationNumberGenerator;
        this.brand = brand;
    }

    public Car createNewCar(String modelAsString, String color, List<String> equipment, List<String> packages) throws MissingModelException, MissingPackageException {
        Model model = models.get(modelAsString);
        if (model == null) throw new MissingModelException(modelAsString);
        List<String> allEquipment = new ArrayList<>(equipment);
        allEquipment.addAll(model.getEquipment());

        appendPackageEquipment(packages, allEquipment);

        return new Car(
                color,
                brand,
                vehicleRegistrationNumberGenerator.getNextRegNo(),
                model.getEngineType(),
                model.getEnginePower(),
                model.getNumberOfPassengers(),
                allEquipment, packages);
    }

    private void appendPackageEquipment(List<String> packages, List<String> allEquipment) throws MissingPackageException {
        for (String carPackageName : packages) {
            CarPackage carPackage = carPackages.get(carPackageName);
            if (carPackage == null) {
                throw new MissingPackageException(carPackageName);
            }
            allEquipment.addAll(carPackage.getEquipment());
            if(carPackage.getInheritFromPackageName() != null) {
                appendPackageEquipment(List.of(carPackage.getInheritFromPackageName()), allEquipment);

            }

        }
    }

    public void addModel(String model, String engineType, int enginePower, int numberOfPassengers, List<String> equipment) {
        models.put(model, new Model(model, engineType, enginePower, numberOfPassengers, equipment));
    }

    public void addPackage(String packageName, List<String> equipment, String inheritFromPackageName) {
        carPackages.put(packageName, new CarPackage(packageName, equipment, inheritFromPackageName));
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

    private static class CarPackage {
        private String name;
        private List<String> equipment;
        private String inheritFromPackageName;

        public CarPackage(String name, List<String> equipment, String inheritFromPackageName) {

            this.name = name;
            this.equipment = equipment;
            this.inheritFromPackageName = inheritFromPackageName;
        }

        public String getName() {
            return name;
        }

        public String getInheritFromPackageName() {
            return inheritFromPackageName;
        }

        public List<String> getEquipment() {
            return equipment;
        }
    }
}
