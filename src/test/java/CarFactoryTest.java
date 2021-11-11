import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarFactoryTest {

    VehicleRegistrationNumberGenerator vehicleRegistrationNumberGenerator;
    CarFactory carFactory;

    @BeforeEach
    void setUp() {
        vehicleRegistrationNumberGenerator = new VehicleRegistrationNumberGenerator(List.of("ABC123"));
        carFactory = new CarFactory(vehicleRegistrationNumberGenerator, "Saab");
    }

    @Test
    void test_create_car_success() {
        carFactory.addModel("900", "Bensin", 90, 4);

        Car car = carFactory.createNewCar("900", "Red");

        assertNotNull(car);
        assertEquals("Red", car.getColor());
        assertEquals("ABC123", car.getRegNo());
    }

    @Test
    void test_create_car_with_model_success() {

        carFactory.addModel("900", "Bensin", 90, 4);
        Car car = carFactory.createNewCar("900", "Red");

        assertNotNull(car);
        assertEquals("Bensin", car.getEngineType());
        assertEquals(90, car.getEnginePower());
        assertEquals(4, car.getNumberOfPassengers());
    }
}
