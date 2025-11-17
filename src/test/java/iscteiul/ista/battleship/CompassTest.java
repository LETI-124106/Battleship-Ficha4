package iscteiul.ista.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("Testes para o enum Compass")
class CompassTest {

    @Test
    @DisplayName("getDirection devolve o char correto para cada constante")
    void getDirectionReturnsCorrectChar() {
        assertAll("chars das direções",
                () -> assertEquals('n', Compass.NORTH.getDirection()),
                () -> assertEquals('s', Compass.SOUTH.getDirection()),
                () -> assertEquals('e', Compass.EAST.getDirection()),
                () -> assertEquals('o', Compass.WEST.getDirection()),
                () -> assertEquals('u', Compass.UNKNOWN.getDirection())
        );
    }

    @Test
    @DisplayName("toString devolve o mesmo char que getDirection")
    void toStringReturnsSameAsGetDirection() {
        for (Compass c : Compass.values()) {
            assertEquals(String.valueOf(c.getDirection()), c.toString());
        }
    }

    @Test
    @DisplayName("charToCompass converte caracteres válidos nas constantes esperadas")
    void charToCompassValidChars() {
        assertAll("conversão de chars válidos",
                () -> assertEquals(Compass.NORTH, Compass.charToCompass('n')),
                () -> assertEquals(Compass.SOUTH, Compass.charToCompass('s')),
                () -> assertEquals(Compass.EAST, Compass.charToCompass('e')),
                () -> assertEquals(Compass.WEST, Compass.charToCompass('o'))
        );
    }

    @Test
    @DisplayName("charToCompass devolve UNKNOWN para caracteres inválidos ou maiúsculos")
    void charToCompassInvalidCharReturnsUnknown() {
        assertAll("chars inválidos",
                () -> assertEquals(Compass.UNKNOWN, Compass.charToCompass('x')),
                () -> assertEquals(Compass.UNKNOWN, Compass.charToCompass('N'))
        );
    }

}