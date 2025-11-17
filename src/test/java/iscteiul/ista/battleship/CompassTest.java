package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompassTest {

    @Nested
    @DisplayName("Testes de toString e getDirection")
    class ToStringTests {

        @Test
        @DisplayName("toString devolve o mesmo char de getDirection")
        void toStringMatchesDirection() {
            for (Compass c : Compass.values()) {
                assertEquals(String.valueOf(c.getDirection()), c.toString());
            }
        }
    }

    @Nested
    @DisplayName("Testes de charToCompass")
    class ConversionTests {

        @Test
        @DisplayName("charToCompass converte caracteres válidos")
        void validCharConversion() {
            assertEquals(Compass.NORTH, Compass.charToCompass('n'));
            assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
            assertEquals(Compass.EAST,  Compass.charToCompass('e'));
            assertEquals(Compass.WEST,  Compass.charToCompass('o'));
        }

        @Test
        @DisplayName("charToCompass devolve UNKNOWN para caracteres inválidos")
        void invalidCharConversion() {
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('?'));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
        }
    }
}
