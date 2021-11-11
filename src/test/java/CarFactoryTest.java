import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        Car car = carFactory.createNewCar("900", "Red");

        assertNotNull(car);
        assertEquals("Red", car.getColor());
        assertEquals("ABC123", car.getRegNo());
    }

    @ParameterizedTest
    @CsvSource({
            "900,Bensin,90,4",
            "900 Turbo,Bensin/Turbo,150,4",
            "93,Bensin,110,4",
            "93 aero,Bensin/Turbo,190,4",
            "9-7X,Diesel/Turbo,170,6"
    })
    void test_create_car_with_model_success(String model, String engineType, int enginePower, int numberOfPassengers) {

        Car car = carFactory.createNewCar(model, "Red");

        assertNotNull(car);
        assertEquals(engineType, car.getEngineType());
        assertEquals(enginePower, car.getEnginePower());
        assertEquals(numberOfPassengers, car.getNumberOfPassengers());
    }
}
