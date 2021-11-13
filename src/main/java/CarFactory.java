import java.util.*;
import java.util.stream.Collectors;

public class CarFactory {

    private VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator;
    private String brand;
    private Map<String, Model> models = new HashMap<>();
    private Map<String, CarPackage> carPackages = new HashMap<>();
    private Map<String, Equipment> equipments = new HashMap<>();

    public CarFactory(VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator, String brand) {
        this.vehicleRegistrationNumberGenerator = vehicleRegistrationNumberGenerator;
        this.brand = brand;
    }

    public Car createNewCar(String modelName, String color, List<String> equipment, List<String> packages) throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        Model model = getModelName(modelName);

        model.verifyCompatiblePackages(packages);

        return new Car(
                color,
                brand,
                vehicleRegistrationNumberGenerator.getNextRegNo(),
                model.getEngineType(),
                model.getEnginePower(),
                model.getNumberOfPassengers(),
                combineEquipmentAndCheckForDuplicates(equipment, packages, model),
                packages,
                calculatePrice(equipment, packages, model));
    }

    private int calculatePrice(List<String> equipment, List<String> packages, Model model) {

        return model.getPrice()
                + calculateEquipmentPrice(equipment)
                + calculateEquipmentPrice(model.getEquipment())
                + calculatePackagePrice(packages);
    }

    private List<String> combineEquipmentAndCheckForDuplicates(List<String> equipment, List<String> packages, Model model) throws MissingPackageException, IllegalCombinationOfEquipmentException {
        List<String> allEquipment = new ArrayList<>(equipment);
        allEquipment.addAll(model.getEquipment());
        appendPackageEquipment(packages, allEquipment);

        List<String> equipmentDuplicates = findDuplicates(allEquipment);
        if (!equipmentDuplicates.isEmpty()) {
            throw new IllegalCombinationOfEquipmentException(String.join(",", equipmentDuplicates));
        }

        return allEquipment;
    }

    private Model getModelName(String modelAsString) throws MissingModelException {
        Model model = models.get(modelAsString);

        if (model == null) throw new MissingModelException(modelAsString);
        return model;
    }

    private int calculateEquipmentPrice(List<String> allEquipment) {
        int equipmentPrice = allEquipment.stream()
                .map(equ -> equipments.getOrDefault(equ, new Equipment(null, 0)))
                .mapToInt(Equipment::getPrice)
                .sum();
        return equipmentPrice;
    }

    private int calculatePackagePrice(List<String> allPackages) {
        int packagePrice = allPackages.stream()
                .map(equ -> carPackages.getOrDefault(equ, new CarPackage(null, List.of(), null, 0)))
                .mapToInt(CarPackage::getPrice)
                .sum();
        return packagePrice;
    }

    private void appendPackageEquipment(List<String> packages, List<String> allEquipment) throws MissingPackageException {
        for (String carPackageName : packages) {
            CarPackage carPackage = carPackages.get(carPackageName);
            if (carPackage == null) {
                throw new MissingPackageException(carPackageName);
            }
            allEquipment.addAll(carPackage.getEquipment());
            if (carPackage.getInheritFromPackageName() != null) {
                appendPackageEquipment(List.of(carPackage.getInheritFromPackageName()), allEquipment);
            }
        }
    }

    public void addModel(String model, String engineType, int enginePower, int numberOfPassengers, List<String> equipment, List<String> compatiblePackages, int price) {
        models.put(model, new Model(model, engineType, enginePower, numberOfPassengers, equipment, compatiblePackages, price));
    }

    public void addPackage(String packageName, List<String> equipment, String inheritFromPackageName, int price) {
        carPackages.put(packageName, new CarPackage(packageName, equipment, inheritFromPackageName, price));
    }

    public List<String> findDuplicates(List<String> listContainingDuplicates) {
        final Set<String> setToReturn = new HashSet<>();
        final Set<String> set1 = new HashSet<>();

        for (String yourStr : listContainingDuplicates) {
            if (!set1.add(yourStr)) {
                setToReturn.add(yourStr);
            }
        }
        return setToReturn.stream().collect(Collectors.toList());
    }

    public void addEquipment(String equipment, int price) {
        equipments.put(equipment, new Equipment(equipment, price));
    }

    static class Model {
        String model;
        String engineType;
        int enginePower;
        int numberOfPassengers;
        List<String> equipment;
        List<String> compatiblePackages;
        int price;

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

        public void verifyCompatiblePackages(List<String> packages) throws IllegalModelAndPackageCombinationException {
            if (!getCompatiblePackages().containsAll(packages)) {
                throw new IllegalModelAndPackageCombinationException(String.join(",", packages));
            }
        }
    }

    static class CarPackage {
        String name;
        List<String> equipment;
        String inheritFromPackageName;
        int price;

        public CarPackage(String name, List<String> equipment, String inheritFromPackageName, int price) {

            this.name = name;
            this.equipment = equipment;
            this.inheritFromPackageName = inheritFromPackageName;
            this.price = price;
        }

        public String getInheritFromPackageName() {
            return inheritFromPackageName;
        }

        public List<String> getEquipment() {
            return equipment;
        }

        public int getPrice() {
            return price;
        }
    }

    static class Equipment {
        String name;
        int price;

        public Equipment(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
    }
}
