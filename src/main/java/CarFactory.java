import java.util.*;
import java.util.stream.Collectors;

public class CarFactory {

    private VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator;
    private String brand;
    private Map<String, Model> models = new HashMap<>();
    private Map<String, CarPackage> carPackages = new HashMap<>();

    public CarFactory(VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator, String brand) {
        this.vehicleRegistrationNumberGenerator = vehicleRegistrationNumberGenerator;
        this.brand = brand;
    }

    public Car createNewCar(String modelAsString, String color, List<String> equipment, List<String> packages) throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        Model model = models.get(modelAsString);
        if (model == null) throw new MissingModelException(modelAsString);
        if(!model.getCompatiblePackages().containsAll(packages)) {
            throw new IllegalModelAndPackageCombinationException(String.join(",", packages));
        }
        List<String> allEquipment = new ArrayList<>(equipment);
        allEquipment.addAll(model.getEquipment());

        appendPackageEquipment(packages, allEquipment);
        List<String> equipmentDuplicates = findDuplicates(allEquipment);
        if(!equipmentDuplicates.isEmpty()) {
            throw new IllegalCombinationOfEquipmentException(String.join(",", equipmentDuplicates));
        }

        return new Car(
                color,
                brand,
                vehicleRegistrationNumberGenerator.getNextRegNo(),
                model.getEngineType(),
                model.getEnginePower(),
                model.getNumberOfPassengers(),
                allEquipment, packages,model.getPrice());
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

    public void addModel(String model, String engineType, int enginePower, int numberOfPassengers, List<String> equipment, List<String> compatiblePackages, int price) {
        models.put(model, new Model(model, engineType, enginePower, numberOfPassengers, equipment, compatiblePackages, price));
    }

    public void addPackage(String packageName, List<String> equipment, String inheritFromPackageName) {
        carPackages.put(packageName, new CarPackage(packageName, equipment, inheritFromPackageName));
    }

    public List<String> findDuplicates(List<String> listContainingDuplicates)
    {
        final Set<String> setToReturn = new HashSet<>();
        final Set<String> set1 = new HashSet<>();

        for (String yourStr : listContainingDuplicates)
        {
            if (!set1.add(yourStr))
            {
                setToReturn.add(yourStr);
            }
        }
        return setToReturn.stream().collect(Collectors.toList());
    }

    public static class Model {
        String model;
        String engineType;
        int enginePower;
        int numberOfPassengers;
        private List<String> equipment;
        private List<String> compatiblePackages;
        private int price;

        public Model(String model,
                     String engineType,
                     int enginePower,
                     int numberOfPassengers,
                     List<String> equipment,
                     List<String> compatiblePackages,
                     int price) {
            this.model = model;
            this.engineType = engineType;
            this.enginePower = enginePower;
            this.numberOfPassengers = numberOfPassengers;
            this.equipment = equipment;
            this.compatiblePackages = compatiblePackages;
            this.price = price;
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

        public List<String> getCompatiblePackages() {
            return compatiblePackages;
        }

        public int getPrice() {
            return price;
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
