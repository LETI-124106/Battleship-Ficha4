package iscteiul.ista.battleship;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BargeTest {
    @Test
    @DisplayName("Deve criar uma Barge corretamente com posição e orientação válidas")
    void createBarge() {
        Position pos = new Position(3, 5);
        Compass bearing = Compass.EAST;

        Barge b = new Barge(bearing, pos);

        assertEquals("Barca", b.getCategory());
        assertEquals(bearing, b.getBearing());
        assertEquals(pos, b.getPosition());
        assertEquals(1, b.getSize());

        // lista de posições gerada pela subclasse
        assertEquals(1, b.getPositions().size());
        assertEquals(pos, b.getPositions().get(0));
    }

}