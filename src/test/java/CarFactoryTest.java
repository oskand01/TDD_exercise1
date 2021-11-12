import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarFactoryTest {

    CarFactory carFactory;

    @BeforeEach
    void setUp() {
        VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator = new VehicleRegistrationNumberGenerator(List.of("ABC123"));
        carFactory = new CarFactory(vehicleRegistrationNumberGenerator, "Saab");
        carFactory.addModel("900", "Bensin", 90, 4, List.of());
    }

    @Test
    void test_create_car_success() {
        Car car = carFactory.createNewCar("900", "Red", List.of());

        assertNotNull(car);
        assertEquals("Red", car.getColor());
        assertEquals("ABC123", car.getRegNo());
    }

    @Test
    void test_create_car_with_model_success() {
        Car car = carFactory.createNewCar("900", "Red", List.of());

        assertNotNull(car);
        assertEquals("Bensin", car.getEngineType());
        assertEquals(90, car.getEnginePower());
        assertEquals(4, car.getNumberOfPassengers());
    }

    @Test
    void test_create_car_with_equipment_success() {
        Car car = carFactory.createNewCar("900", "Red", List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"));

        assertNotNull(car);
        assertEquals(List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"), car.getEquipment());
    }

    @Test
    void test_create_car_with_model_equipment_success() {

        carFactory.addModel("901",  "Bensin", 90, 4, List.of("Rattvärme", "Stolsvärme", "Krockkudde"));

        Car car = carFactory.createNewCar("901", "Red", List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak"));

        assertNotNull(car);
        assertEquals(List.of("Xenonljus", "Lättmetallfälgar 24\"", "Stolsvärme bak", "Rattvärme", "Stolsvärme", "Krockkudde"), car.getEquipment());
    }
}
