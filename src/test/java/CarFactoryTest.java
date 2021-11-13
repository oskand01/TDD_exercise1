import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarFactoryTest {

    CarFactory carFactory;

    @BeforeEach
    void setUp() {
        VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator = new VehicleRegistrationNumberGenerator(List.of("ABC123"));
        carFactory = new CarFactory(vehicleRegistrationNumberGenerator, "Saab");
        carFactory.addModel("900", "Bensin", 90, 4, List.of(), List.of("Plus", "Business"), 100);
    }

    @Test
    void test_create_car_success() throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of());

        assertNotNull(car);
        assertEquals("Red", car.getColor());
        assertEquals("ABC123", car.getRegNo());
    }

    @Test
    void test_create_car_with_model_success() throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of());

        assertNotNull(car);
        assertEquals("Bensin", car.getEngineType());
        assertEquals(90, car.getEnginePower());
        assertEquals(4, car.getNumberOfPassengers());
    }

    @Test
    void test_create_car_with_equipment_success() throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        Car car = carFactory.createNewCar("900", "Red", List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"), List.of());

        assertNotNull(car);
        assertEquals(List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"), car.getEquipment());
    }

    @Test
    void test_create_car_with_model_equipment_success() throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {

        carFactory.addModel("901", "Bensin", 90, 4, List.of("Rattvärme", "Stolsvärme", "Krockkudde"), List.of(), 100);

        Car car = carFactory.createNewCar("901", "Red", List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"), List.of());

        assertNotNull(car);
        assertEquals(List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak", "Rattvärme", "Stolsvärme", "Krockkudde"), car.getEquipment());
    }

    @Test
    void test_create_car_with_model_failure() {
        MissingModelException missingModelException = assertThrows(MissingModelException.class,
                () -> carFactory.createNewCar("missing model", "", List.of(), List.of()));

        assertEquals("missing model", missingModelException.getMessage());
    }

    @Test
    void test_create_car_with_package_success() throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        carFactory.addPackage("Plus", List.of("Elmanövrerade backspeglar", "Taklucka"), null, 0);

        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of("Plus"));

        assertEquals(List.of("Plus"), car.getPackages());
        assertEquals(List.of("Elmanövrerade backspeglar", "Taklucka"), car.getEquipment());
    }

    @Test
    void test_create_car_with_inherited_package_success() throws MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException, IllegalCombinationOfEquipmentException {
        carFactory.addPackage("Plus", List.of("Elmanövrerade backspeglar", "Taklucka"), null, 0);
        carFactory.addPackage("Business", List.of("Bluetooth integration", "Backkamera"), "Plus", 0);

        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of("Business"));

        assertEquals(List.of("Business"), car.getPackages());
        assertEquals(List.of("Bluetooth integration", "Backkamera", "Elmanövrerade backspeglar", "Taklucka"), car.getEquipment());
    }

    @Test
    void test_create_car_fail_because_illegal_model_and_package_combination() throws MissingModelException, MissingPackageException {
        carFactory.addModel("901", "Bensin", 90, 4, List.of(), List.of("Plus"), 100);
        carFactory.addPackage("Sport", List.of("Sportratt", "Kjolpaket"), null, 0);

        IllegalModelAndPackageCombinationException illegalModelAndPackageCombinationException = assertThrows(IllegalModelAndPackageCombinationException.class, () ->
                carFactory.createNewCar("901", "Red", List.of(), List.of("Sport")));

        assertEquals("Sport", illegalModelAndPackageCombinationException.getMessage());
    }

    @Test
    void test_create_car_fail_because_illegal_combination_of_equipment() throws MissingModelException, MissingPackageException {
        carFactory.addModel("901", "Bensin", 90, 4, List.of("Lysen"), List.of(), 100);

        IllegalCombinationOfEquipmentException illegalCombinationOfEquipmentException = assertThrows(IllegalCombinationOfEquipmentException.class, () ->
                carFactory.createNewCar("901", "Red", List.of("Lysen"), List.of()));

        assertEquals("Lysen", illegalCombinationOfEquipmentException.getMessage());
    }

    @Test
    void test_create_car_with_price_success() throws IllegalCombinationOfEquipmentException, MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException {
        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of());

        assertEquals(100, car.getPrice());

    }

    @Test
    void test_create_car_with_price_with_equipment_success() throws IllegalCombinationOfEquipmentException, MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException {
        carFactory.addEquipment("lysen", 50);

        Car car = carFactory.createNewCar("900", "Red", List.of("lysen"), List.of());

        assertEquals(150, car.getPrice());

    }

    @Test
    void test_create_car_with_price_with_aquipment_and_package_success() throws IllegalCombinationOfEquipmentException, MissingModelException, MissingPackageException, IllegalModelAndPackageCombinationException {
        carFactory.addEquipment("lysen", 50);
        carFactory.addEquipment("Elmanövrerade backspeglar", 25);
        carFactory.addEquipment("Taklucka", 30);
        carFactory.addPackage("Plus", List.of("Elmanövrerade backspeglar", "Taklucka"), null, 40);

        Car car = carFactory.createNewCar("900", "Red", List.of("lysen"), List.of("Plus"));

        assertEquals(190, car.getPrice());

    }
}
