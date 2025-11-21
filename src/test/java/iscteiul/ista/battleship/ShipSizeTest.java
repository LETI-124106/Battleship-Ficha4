package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para barcos de vários tamanhos")
class ShipSizeTest {

    // -------------------------------
    //  GRUPO 1: Barcos 1, 2 e 3
    // -------------------------------
    @Nested
    @DisplayName("Barcos de tamanho 1, 2 e 3")
    class SmallShipsTests {

        @Test
        @DisplayName("Barge (tamanho 1) é criada corretamente e deixa de flutuar após ser atingida")
        void bargeSize1FloatingAndSink() {
            IShip s = new Barge(Compass.NORTH, new Position(1, 1));

            assertEquals(1, s.getSize());
            assertEquals(1, s.getPositions().size());
            assertTrue(s.stillFloating());

            // acertar na única posição
            IPosition pos = s.getPositions().get(0);
            s.shoot(pos);

            assertFalse(s.stillFloating());
            assertTrue(pos.isHit());
        }

        @Test
        @DisplayName("Caravel (tamanho 2) é criada com duas posições e afunda após dois tiros")
        void caravelSize2PositionsAndSink() throws Exception {
            IShip s = new Caravel(Compass.EAST, new Position(2, 3));

            assertEquals(2, s.getSize());
            List<IPosition> positions = s.getPositions();
            assertEquals(2, positions.size());

            // começa a flutuar
            assertTrue(s.stillFloating());

            // acertar em todas as posições
            for (IPosition p : positions) {
                s.shoot(p);
            }

            assertFalse(s.stillFloating());
        }

        @Test
        @DisplayName("Carrack (tamanho 3) tem três posições distintas")
        void carrackSize3HasThreePositions() throws Exception {
            IShip s = new Carrack(Compass.SOUTH, new Position(4, 4));

            assertEquals(3, s.getSize());
            List<IPosition> positions = s.getPositions();
            assertEquals(3, positions.size());

            // as três posições devem ser distintas
            assertNotEquals(positions.get(0), positions.get(1));
            assertNotEquals(positions.get(1), positions.get(2));
            assertNotEquals(positions.get(0), positions.get(2));
        }
    }

    // -------------------------------
    //  GRUPO 2: Barcos 4 e 5
    // -------------------------------
    @Nested
    @DisplayName("Barcos de tamanho 4 e 5")
    class LargeShipsTests {

        @Test
        @DisplayName("Frigate (tamanho 4) afunda apenas depois de todas as posições serem atingidas")
        void frigateSize4SinkAfterAllShots() throws Exception {
            IShip s = new Frigate(Compass.WEST, new Position(5, 5));

            assertEquals(4, s.getSize());
            List<IPosition> positions = s.getPositions();
            assertEquals(4, positions.size());

            // acertar em 3 posições apenas -> ainda flutua
            for (int i = 0; i < positions.size() - 1; i++) {
                s.shoot(positions.get(i));
            }
            assertTrue(s.stillFloating());

            // acertar na última posição -> deixa de flutuar
            s.shoot(positions.get(positions.size() - 1));
            assertFalse(s.stillFloating());
        }

        @Test
        @DisplayName("Galleon (tamanho 5) é criado com cinco posições e todas podem ser atingidas")
        void galleonSize5PositionsAndHits() throws Exception {
            IShip s = new Galleon(Compass.NORTH, new Position(3, 3));

            assertEquals(5, s.getSize());
            List<IPosition> positions = s.getPositions();
            assertEquals(5, positions.size());

            for (IPosition p : positions) {
                assertFalse(p.isHit());
                s.shoot(p);
                assertTrue(p.isHit());
            }

            assertFalse(s.stillFloating());
        }
    }
}
