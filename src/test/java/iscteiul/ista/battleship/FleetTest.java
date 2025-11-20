package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    Fleet fleet;

    @BeforeEach
    void setup() {
        fleet = new Fleet();
    }

    @Test
    @DisplayName("A frota começa vazia")
    void testFleetStartsEmpty() {
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("Adicionar navio válido deve ter sucesso")
    void testAddValidShip() {
        IShip ship = new Barge(Compass.EAST, new Position(0, 0));

        boolean result = fleet.addShip(ship);

        assertTrue(result);
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("Navio fora do tabuleiro não deve ser adicionado")
    void testAddShipOutsideBoard() {
        IShip ship = new Barge(Compass.NORTH, new Position(50, 50));

        assertFalse(fleet.addShip(ship));
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("Navio que colide com outro não pode ser adicionado")
    void testAddCollidingShip() {
        IShip ship1 = new Barge(Compass.EAST, new Position(0, 0));
        IShip ship2 = new Barge(Compass.EAST, new Position(0, 0));

        fleet.addShip(ship1);
        assertFalse(fleet.addShip(ship2));
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("shipAt retorna o navio correto")
    void testShipAt() {
        IShip ship = new Barge(Compass.EAST, new Position(0, 0));
        fleet.addShip(ship);

        assertEquals(ship, fleet.shipAt(new Position(0, 0)));
    }

    @Test
    @DisplayName("getFloatingShips retorna apenas navios flutuantes")
    void testGetFloatingShips() {
        IShip ship = new Barge(Compass.EAST, new Position(0, 0));
        fleet.addShip(ship);

        assertEquals(1, fleet.getFloatingShips().size());
    }

    @Test
    @DisplayName("getFloatingShips não retorna navios afundados")
    void testFloatingShipsExcludesSunk() {
        IShip ship = new Barge(Compass.EAST, new Position(0, 0));
        fleet.addShip(ship);

        ship.shoot(new Position(0, 0)); // afunda barca

        assertEquals(0, fleet.getFloatingShips().size());
    }

    @Test
    @DisplayName("getShipsLike filtra navios por categoria")
    void testGetShipsLike() {
        fleet.addShip(new Barge(Compass.EAST, new Position(0, 0)));

        assertEquals(1, fleet.getShipsLike("Barca").size());
        assertEquals(0, fleet.getShipsLike("Galeão").size());
    }
}
