package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
    IFleet fleet;

    @BeforeEach
    void setup() {
        fleet = new Fleet();
        game = new Game(fleet);
    }

    @Nested
    @DisplayName("Inicialização do jogo")
    class InitTests {
        @Test
        @DisplayName("Ao iniciar o jogo a frota deve estar vazia")
        void testGameStartsEmpty() {
            assertTrue(game.getShots().isEmpty());
            assertEquals(0, game.getInvalidShots());
            assertEquals(0, game.getRepeatedShots());
            assertEquals(0, game.getHits());
            assertEquals(0, game.getSunkShips());
        }
    }

    @Nested
    @DisplayName("Tiros e validações de jogada")
    class ShotTests {
        @Test
        @DisplayName("Disparo fora do tabuleiro deve ser inválido")
        void testInvalidShot() {
            IPosition pos = new Position(-1, 10);
            game.fire(pos);
            assertEquals(1, game.getInvalidShots());
            assertEquals(0, game.getShots().size());
        }

        @Test
        @DisplayName("Tiro repetido deve ser contabilizado mas não adicionado à lista")
        void testRepeatedShot() {
            IPosition pos = new Position(1, 1);
            game.fire(pos);
            game.fire(pos);

            assertEquals(1, game.getRepeatedShots());
            assertEquals(1, game.getShots().size());
        }

        @Test
        @DisplayName("Disparo num navio deve contar como acerto")
        void testHitShot() {
            IShip ship = new Barge(Compass.EAST, new Position(0, 0));
            fleet.addShip(ship);
            game.fire(new Position(0, 0));
            assertEquals(1, game.getHits());
        }
    }

    @Nested
    @DisplayName("Afundar navios e contagem")
    class SinkTests {
        @Test
        @DisplayName("Disparo que afunda navio aumenta contagem de afundamentos")
        void testSinkShip() {
            IShip ship = new Barge(Compass.EAST, new Position(0, 0));
            fleet.addShip(ship);
            game.fire(new Position(0, 0));
            assertEquals(1, game.getSunkShips());
        }

        @Test
        @DisplayName("Afundar navio reduz quantidade de navios restantes")
        void testRemainingShipsAfterSink() {
            IShip ship = new Barge(Compass.EAST, new Position(2, 2));
            fleet.addShip(ship);

            assertEquals(1, game.getRemainingShips());
            game.fire(new Position(2, 2));
            assertEquals(0, game.getRemainingShips());
        }

        @Test
        @DisplayName("Acertar vários navios conta hits corretamente")
        void testMultipleShipsHit() {
            IShip ship1 = new Barge(Compass.EAST, new Position(0, 0));
            IShip ship2 = new Barge(Compass.EAST, new Position(2, 0));
            fleet.addShip(ship1);
            fleet.addShip(ship2);

            game.fire(new Position(0, 0));
            game.fire(new Position(2, 0));

            assertEquals(2, game.getHits());
        }
    }
}
