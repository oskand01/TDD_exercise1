import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarFactoryTest {

    @Test
    void test_create_car_success() {
        CarFactory carFactory = new CarFactory();
        Car car = carFactory.createNewCar("Red");

        assertNotNull(car);
        assertEquals("Red", car.getColor());

    }
}
