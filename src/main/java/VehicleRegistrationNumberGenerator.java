import java.util.ArrayList;
import java.util.List;

public class VehicleRegistrationNumberGenerator {

    private List<String> regNumbers;

    public VehicleRegistrationNumberGenerator(List<String> regNumbers) {
        this.regNumbers = new ArrayList<>(regNumbers);
    }

    public String getNextRegNo() {
        return regNumbers.get(0);
    }
}
