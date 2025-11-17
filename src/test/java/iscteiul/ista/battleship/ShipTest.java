package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Nested
    @DisplayName("Criação e propriedades básicas da Barge")
    class BargeBasics {

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

            assertEquals(1, b.getPositions().size());
            assertEquals(pos, b.getPositions().get(0));
        }

        @Test
        @DisplayName("stillFloating deve ser true enquanto não for atingida")
        void stillFloatingBeforeHit() {
            Barge b = new Barge(Compass.NORTH, new Position(2, 2));
            assertTrue(b.stillFloating());
        }

        @Test
        @DisplayName("stillFloating deve ser false depois de ser atingida")
        void stillFloatingAfterHit() {
            Barge b = new Barge(Compass.NORTH, new Position(2, 2));
            Position target = new Position(2, 2);

            assertTrue(b.stillFloating());
            b.shoot(target);
            assertFalse(b.stillFloating());
        }

        @Test
        @DisplayName("occupies deve devolver true na posição correta")
        void occupiesOwnPosition() {
            Position pos = new Position(4, 7);
            Barge b = new Barge(Compass.SOUTH, pos);
            assertTrue(b.occupies(new Position(4, 7)));
        }

        @Test
        @DisplayName("occupies deve devolver false noutra posição")
        void doesNotOccupyOtherPosition() {
            Barge b = new Barge(Compass.SOUTH, new Position(4, 7));
            assertFalse(b.occupies(new Position(1, 1)));
        }
    }

    @Nested
    @DisplayName("Limites top/bottom/left/right")
    class Bounds {

        @Test
        @DisplayName("Barge tem limites iguais pois tamanho = 1")
        void boundsForBarge() {
            Position pos = new Position(5, 3);
            Barge b = new Barge(Compass.WEST, pos);

            assertEquals(5, b.getTopMostPos());
            assertEquals(5, b.getBottomMostPos());
            assertEquals(3, b.getLeftMostPos());
            assertEquals(3, b.getRightMostPos());
        }
    }

    @Nested
    @DisplayName("Proximidade entre barcos e posições")
    class Proximity {

        @Test
        @DisplayName("tooCloseTo(IPosition) é true quando a posição é adjacente")
        void tooCloseToAdjacentPosition() {
            Barge b = new Barge(Compass.NORTH, new Position(5, 5));
            Position adjacent = new Position(4, 5);
            assertTrue(b.tooCloseTo(adjacent));
        }

        @Test
        @DisplayName("tooCloseTo(IPosition) é false quando posição é distante")
        void notTooCloseToDistantPosition() {
            Barge b = new Barge(Compass.NORTH, new Position(5, 5));
            Position far = new Position(10, 10);
            assertFalse(b.tooCloseTo(far));
        }

        @Test
        @DisplayName("Barcos adjacentes estão tooClose")
        void tooCloseToOtherShipAdjacent() {
            Barge b1 = new Barge(Compass.NORTH, new Position(5, 5));
            Barge b2 = new Barge(Compass.SOUTH, new Position(5, 6));

            assertTrue(b1.tooCloseTo(b2));
            assertTrue(b2.tooCloseTo(b1));
        }

        @Test
        @DisplayName("Barcos distantes não estão tooClose")
        void notTooCloseToOtherShipFar() {
            Barge b1 = new Barge(Compass.NORTH, new Position(1, 1));
            Barge b2 = new Barge(Compass.SOUTH, new Position(10, 10));

            assertFalse(b1.tooCloseTo(b2));
            assertFalse(b2.tooCloseTo(b1));
        }
    }

    @Nested
    @DisplayName("Disparos sobre barcos")
    class Shooting {

        @Test
        @DisplayName("shoot marca hit apenas na posição correta")
        void shootMarksOnlyCorrectPosition() {
            Barge b = new Barge(Compass.NORTH, new Position(3, 3));
            IPosition occupied = b.getPositions().get(0);

            assertFalse(occupied.isHit());
            b.shoot(new Position(3, 3));
            assertTrue(occupied.isHit());
        }

        @Test
        @DisplayName("shoot numa posição errada não altera o estado")
        void shootWrongPositionDoesNothing() {
            Barge b = new Barge(Compass.NORTH, new Position(3, 3));
            IPosition occupied = b.getPositions().get(0);

            b.shoot(new Position(1, 1));

            assertFalse(occupied.isHit());
            assertTrue(b.stillFloating());
        }
    }

    @Nested
    @DisplayName("Ship.buildShip factory")
    class ShipFactory {

        @Test
        @DisplayName("buildShip cria Barge corretamente")
        void buildShipCreatesBarge() {
            Position pos = new Position(2, 2);
            Ship s = Ship.buildShip("barca", Compass.EAST, pos);

            assertNotNull(s);
            assertTrue(s instanceof Barge);
            assertEquals("Barca", s.getCategory());
            assertEquals(1, s.getSize());
            assertEquals(pos, s.getPosition());
        }

        @Test
        @DisplayName("buildShip devolve null para tipo desconhecido")
        void buildShipUnknownKindReturnsNull() {
            Ship s = Ship.buildShip("submarino", Compass.NORTH, new Position(1, 1));
            assertNull(s);
        }
    }
}