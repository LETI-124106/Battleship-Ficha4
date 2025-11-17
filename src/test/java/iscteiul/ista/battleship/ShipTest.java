package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    @DisplayName("Deve criar uma Barge corretamente com posição e orientação válidas")
    void createBarge() {
        Position pos = new Position(3, 5);
        Compass bearing = Compass.EAST;

        Barge b = new Barge(bearing, pos);

        assertEquals("Barca", b.getCategory());   // nome definido na Barge
        assertEquals(bearing, b.getBearing());
        assertEquals(pos, b.getPosition());
        assertEquals(1, b.getSize());

        // A Barge ocupa exatamente a posição inicial
        assertEquals(1, b.getPositions().size());
        assertEquals(pos, b.getPositions().get(0));
    }

    @Test
    @DisplayName("stillFloating deve ser true enquanto a posição da Barge não for atingida")
    void stillFloatingBeforeHit() {
        Barge b = new Barge(Compass.NORTH, new Position(2, 2));

        assertTrue(b.stillFloating());
    }

    @Test
    @DisplayName("stillFloating deve ser false depois de a Barge levar um hit na posição que ocupa")
    void stillFloatingAfterHit() {
        Barge b = new Barge(Compass.NORTH, new Position(2, 2));
        Position target = new Position(2, 2);

        // antes do tiro ainda está a flutuar
        assertTrue(b.stillFloating());

        b.shoot(target);

        // como a barca só tem 1 posição, depois do hit já não está a flutuar
        assertFalse(b.stillFloating());
    }

    @Test
    @DisplayName("occupies deve devolver true para a posição ocupada pela Barge")
    void occupiesOwnPosition() {
        Position pos = new Position(4, 7);
        Barge b = new Barge(Compass.SOUTH, pos);

        Position samePos = new Position(4, 7); // mesma célula

        assertTrue(b.occupies(samePos));
    }

    @Test
    @DisplayName("occupies deve devolver false para posições fora do barco")
    void doesNotOccupyOtherPosition() {
        Barge b = new Barge(Compass.SOUTH, new Position(4, 7));

        Position other = new Position(1, 1);

        assertFalse(b.occupies(other));
    }

    @Test
    @DisplayName("getTop/Bottom/Left/RightMostPos devem coincidir para uma Barge (tamanho 1)")
    void boundsForBarge() {
        Position pos = new Position(5, 3);
        Barge b = new Barge(Compass.WEST, pos);

        assertEquals(5, b.getTopMostPos());
        assertEquals(5, b.getBottomMostPos());
        assertEquals(3, b.getLeftMostPos());
        assertEquals(3, b.getRightMostPos());
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) deve ser true quando a posição é adjacente ao barco")
    void tooCloseToAdjacentPosition() {
        Barge b = new Barge(Compass.NORTH, new Position(5, 5));

        // posição adjacente (por exemplo, acima)
        Position adjacent = new Position(4, 5);

        assertTrue(b.tooCloseTo(adjacent));
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) deve ser false para posições afastadas")
    void notTooCloseToDistantPosition() {
        Barge b = new Barge(Compass.NORTH, new Position(5, 5));

        Position far = new Position(10, 10);

        assertFalse(b.tooCloseTo(far));
    }

    @Test
    @DisplayName("tooCloseTo(IShip) deve ser true para barcos encostados")
    void tooCloseToOtherShipAdjacent() {
        // primeira barca em (5,5)
        Barge b1 = new Barge(Compass.NORTH, new Position(5, 5));

        // segunda barca em posição adjacente, por ex. (5,6)
        Barge b2 = new Barge(Compass.SOUTH, new Position(5, 6));

        assertTrue(b1.tooCloseTo(b2));
        assertTrue(b2.tooCloseTo(b1));
    }

    @Test
    @DisplayName("tooCloseTo(IShip) deve ser false para barcos afastados")
    void notTooCloseToOtherShipFar() {
        Barge b1 = new Barge(Compass.NORTH, new Position(1, 1));
        Barge b2 = new Barge(Compass.SOUTH, new Position(10, 10));

        assertFalse(b1.tooCloseTo(b2));
        assertFalse(b2.tooCloseTo(b1));
    }

    @Test
    @DisplayName("shoot só deve marcar como hit a posição correta")
    void shootMarksOnlyCorrectPosition() {
        Barge b = new Barge(Compass.NORTH, new Position(3, 3));

        List<IPosition> positions = b.getPositions();
        IPosition occupied = positions.get(0);

        // garantir que inicialmente não está hit
        assertFalse(occupied.isHit());

        // disparar para a posição ocupada
        b.shoot(new Position(3, 3));

        assertTrue(occupied.isHit());
    }

    @Test
    @DisplayName("shoot numa posição errada não deve alterar o estado da Barge")
    void shootWrongPositionDoesNothing() {
        Barge b = new Barge(Compass.NORTH, new Position(3, 3));
        IPosition occupied = b.getPositions().get(0);

        b.shoot(new Position(1, 1)); // tiro falhado

        // continua sem hit
        assertFalse(occupied.isHit());
        assertTrue(b.stillFloating());
    }

    @Test
    @DisplayName("Ship.buildShip deve criar o tipo correto de barco para 'barca'")
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
    @DisplayName("Ship.buildShip deve devolver null para um tipo de barco desconhecido")
    void buildShipUnknownKindReturnsNull() {
        Ship s = Ship.buildShip("submarino", Compass.NORTH, new Position(1, 1));

        assertNull(s);
    }
}