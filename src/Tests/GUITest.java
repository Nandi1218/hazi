package Tests;

import UI.Main;
import org.junit.jupiter.api.Test;

import static UI.Main.gui;
import static org.junit.jupiter.api.Assertions.*;

class GUITest {

    @Test
    void setMenuPanelsToFalse() {
        gui.vendorDataMenuPanel.setVisible(true);
        gui.setMenuPanelsToFalse();
        assertFalse(gui.vendorDataMenuPanel.isVisible());
    }

    @Test
    void findProducer() {
        assertEquals(gui.findProducer(Main.producers.get(3).getName()),Main.producers.get(3));

    }
}