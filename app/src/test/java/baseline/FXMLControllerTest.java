package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FXMLControllerTest {

    @Test
    void addToObservableList() {
        FXMLController fx = new FXMLController();
        fx.addToObservableList("Appointment", "Dentist", "2021-12-09", "N");

        assertTrue(fx.getObservableList().size() > 0);
    }

    @Test
    void isPopulated() {
        FXMLController fx = new FXMLController();
        assertFalse(fx.isPopulated("Something"));
    }

    @Test
    void isNotPopulated() {
        FXMLController fx = new FXMLController();
        assertTrue(fx.isPopulated(""));
    }

    @Test
    void isDateValid() {
        FXMLController fx = new FXMLController();
        assertTrue(fx.isDateValid(2021,04,20));
    }

    @Test
    void isNotDateValid() {
        FXMLController fx = new FXMLController();
        assertFalse(fx.isDateValid(2021,02,30));
    }

    @Test
    void isDateFormatValid() {
        FXMLController fx = new FXMLController();
        assertFalse(fx.isDateFormatValid("2021-10-20"));
    }

    @Test
    void isDateFormatNotValid() {
        FXMLController fx = new FXMLController();
        assertTrue(fx.isDateFormatValid("10/20/2021"));
    }

}