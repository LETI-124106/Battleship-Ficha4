package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Nested
    @DisplayName("Testes de Inicialização")
    class InitializationTests {

        @Test
        @DisplayName("getRow e getColumn retornam os valores corretos")
        void gettersTest() {
            Position p = new Position(3, 5);
            assertEquals(3, p.getRow());
            assertEquals(5, p.getColumn());
        }

        @Test
        @DisplayName("toString está formatado corretamente")
        void toStringTest() {
            Position p = new Position(2, 4);
            assertEquals("Linha = 2 Coluna = 4", p.toString());
        }
    }

    @Nested
    @DisplayName("Testes de Ocupação e Disparo")
    class OccupancyTests {

        @Test
        @DisplayName("occupy marca a posição como ocupada")
        void occupyTest() {
            Position p = new Position(1, 1);
            p.occupy();
            assertTrue(p.isOccupied());
        }

        @Test
        @DisplayName("shoot marca a posição como atingida")
        void shootTest() {
            Position p = new Position(1, 1);
            p.shoot();
            assertTrue(p.isHit());
        }
    }

    @Nested
    @DisplayName("Testes de Comparação e Igualdade")
    class EqualityTests {

        @Test
        @DisplayName("equals é verdadeiro para mesma posição")
        void equalsTrueTest() {
            Position p1 = new Position(2, 2);
            Position p2 = new Position(2, 2);
            assertEquals(p1, p2);
        }

        @Test
        @DisplayName("equals é falso para posições diferentes")
        void equalsFalseTest() {
            Position p1 = new Position(1, 1);
            Position p2 = new Position(1, 2);
            assertNotEquals(p1, p2);
        }
    }

    @Nested
    @DisplayName("Testes de Adjacência")
    class AdjacencyTests {

        @Test
        @DisplayName("isAdjacentTo é verdadeiro para posições adjacentes")
        void adjacentTrue() {
            Position p1 = new Position(5, 5);
            Position p2 = new Position(6, 6);
            assertTrue(p1.isAdjacentTo(p2));
        }

        @Test
        @DisplayName("isAdjacentTo é falso para posições não adjacentes")
        void adjacentFalse() {
            Position p1 = new Position(1, 1);
            Position p2 = new Position(4, 4);
            assertFalse(p1.isAdjacentTo(p2));
        }
    }
}