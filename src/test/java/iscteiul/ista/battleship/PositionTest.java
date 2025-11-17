package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para a classe Position")
class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(3, 5);
    }

    @Test
    @DisplayName("getRow e getColumn devolvem os valores passados ao construtor")
    void constructorStoresRowAndColumn() {
        assertAll("posição inicial",
                () -> assertEquals(3, position.getRow()),
                () -> assertEquals(5, position.getColumn())
        );
    }

    @Test
    @DisplayName("equals é verdadeiro quando comparado com ele próprio (reflexividade)")
    void equalsIsReflexive() {
        assertEquals(position, position);
    }

    @Test
    @DisplayName("Duas Position com mesma linha e coluna são iguais e têm o mesmo hashCode")
    void equalsSameRowAndColumn() {
        Position other = new Position(3, 5);

        assertAll("igualdade",
                () -> assertEquals(position, other),
                () -> assertEquals(other, position),
                () -> assertEquals(position.hashCode(), other.hashCode())
        );
    }

    @Test
    @DisplayName("Position não é igual a null nem a objetos de outra classe")
    void equalsNullAndDifferentClass() {
        assertNotEquals(null, position);
        assertNotEquals(position, "não sou uma Position");
    }

    @Test
    @DisplayName("Position com linha diferente não é igual")
    void notEqualsDifferentRow() {
        Position other = new Position(4, 5);
        assertNotEquals(position, other);
    }

    @Test
    @DisplayName("Position com coluna diferente não é igual")
    void notEqualsDifferentColumn() {
        Position other = new Position(3, 6);
        assertNotEquals(position, other);
    }

    @Test
    @DisplayName("isAdjacentTo devolve true para posições adjacentes (incluindo a própria)")
    void isAdjacentToAdjacentPositions() {
        assertAll("posições adjacentes",
                () -> assertTrue(position.isAdjacentTo(new Position(3, 5)), "mesma posição"),
                () -> assertTrue(position.isAdjacentTo(new Position(2, 5)), "acima"),
                () -> assertTrue(position.isAdjacentTo(new Position(4, 5)), "abaixo"),
                () -> assertTrue(position.isAdjacentTo(new Position(3, 4)), "esquerda"),
                () -> assertTrue(position.isAdjacentTo(new Position(3, 6)), "direita"),
                () -> assertTrue(position.isAdjacentTo(new Position(2, 4)), "diagonal sup-esq"),
                () -> assertTrue(position.isAdjacentTo(new Position(2, 6)), "diagonal sup-dir"),
                () -> assertTrue(position.isAdjacentTo(new Position(4, 4)), "diagonal inf-esq"),
                () -> assertTrue(position.isAdjacentTo(new Position(4, 6)), "diagonal inf-dir")
        );
    }

    @Test
    @DisplayName("isAdjacentTo devolve false para posições não adjacentes")
    void isAdjacentToNonAdjacentPositions() {
        assertAll("posições não adjacentes",
                () -> assertFalse(position.isAdjacentTo(new Position(1, 5)), "duas linhas acima"),
                () -> assertFalse(position.isAdjacentTo(new Position(5, 5)), "duas linhas abaixo"),
                () -> assertFalse(position.isAdjacentTo(new Position(3, 7)), "duas colunas à direita"),
                () -> assertFalse(position.isAdjacentTo(new Position(3, 3)), "duas colunas à esquerda"),
                () -> assertFalse(position.isAdjacentTo(new Position(1, 7)), "diagonal distante")
        );
    }

    @Test
    @DisplayName("occupy marca a posição como ocupada")
    void occupyMarksPositionAsOccupied() {
        assertFalse(position.isOccupied(), "por omissão não deve estar ocupada");
        position.occupy();
        assertTrue(position.isOccupied(), "depois de occupy deve estar ocupada");
    }

    @Test
    @DisplayName("shoot marca a posição como atingida")
    void shootMarksPositionAsHit() {
        assertFalse(position.isHit(), "por omissão não deve estar atingida");
        position.shoot();
        assertTrue(position.isHit(), "depois de shoot deve estar atingida");
    }

    @Test
    @DisplayName("toString devolve string com linha e coluna")
    void toStringReturnsExpectedFormat() {
        String s = position.toString();
        assertAll("formato toString",
                () -> assertTrue(s.contains("Linha = 3")),
                () -> assertTrue(s.contains("Coluna = 5"))
        );
    }
}