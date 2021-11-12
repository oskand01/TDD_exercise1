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
        carFactory.addModel("900", "Bensin", 90, 4, List.of());
    }

    @Test
    void test_create_car_success() throws MissingModelException, MissingPackageException {
        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of());

        assertNotNull(car);
        assertEquals("Red", car.getColor());
        assertEquals("ABC123", car.getRegNo());
    }

    @Test
    void test_create_car_with_model_success() throws MissingModelException, MissingPackageException {
        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of());

        assertNotNull(car);
        assertEquals("Bensin", car.getEngineType());
        assertEquals(90, car.getEnginePower());
        assertEquals(4, car.getNumberOfPassengers());
    }

    @Test
    void test_create_car_with_equipment_success() throws MissingModelException, MissingPackageException {
        Car car = carFactory.createNewCar("900", "Red", List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"), List.of());

        assertNotNull(car);
        assertEquals(List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"), car.getEquipment());
    }

    @Test
    void test_create_car_with_model_equipment_success() throws MissingModelException, MissingPackageException {

        carFactory.addModel("901", "Bensin", 90, 4, List.of("Rattvärme", "Stolsvärme", "Krockkudde"));

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
    void test_create_car_with_package_success() throws MissingModelException, MissingPackageException {
        carFactory.addPackage("Plus", List.of("Elmanövrerade backspeglar", "Taklucka"));

        Car car = carFactory.createNewCar("900", "Red", List.of(), List.of("Plus"));

        assertEquals(List.of("Plus"), car.getPackages());
        assertEquals(List.of("Elmanövrerade backspeglar", "Taklucka"), car.getEquipment());
    }

}
