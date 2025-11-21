package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {

    private IFleet fleet;
    private List<IPosition> shots;

    private Integer countInvalidShots;
    private Integer countRepeatedShots;
    private Integer countHits;
    private Integer countSinks;

    public Game(IFleet fleet) {
        this.fleet = fleet;
        this.shots = new ArrayList<>();

        this.countInvalidShots = 0;
        this.countRepeatedShots = 0;
        this.countHits = 0;
        this.countSinks = 0;
    }

    @Override
    public IShip fire(IPosition pos) {

        // tiro inválido
        if (!validShot(pos)) {
            countInvalidShots++;
            return null;
        }

        // tiro repetido
        if (repeatedShot(pos)) {
            countRepeatedShots++;
            return null;
        }

        // tiro válido — registar
        shots.add(pos);

        IShip s = fleet.shipAt(pos);

        // acerto
        if (s != null) {
            s.shoot(pos);
            countHits++;

            // Se afundou neste tiro
            if (!s.stillFloating()) {
                countSinks++;
            }

            return s;
        }

        return null;
    }

    @Override
    public List<IPosition> getShots() {
        return shots;
    }

    @Override
    public int getRepeatedShots() {
        return countRepeatedShots;
    }

    @Override
    public int getInvalidShots() {
        return countInvalidShots;
    }

    @Override
    public int getHits() {
        return countHits;
    }

    @Override
    public int getSunkShips() {
        return countSinks;
    }

    @Override
    public int getRemainingShips() {
        return fleet.getFloatingShips().size();
    }

    private boolean validShot(IPosition pos) {
        return pos.getRow() >= 0 && pos.getRow() < Fleet.BOARD_SIZE &&
                pos.getColumn() >= 0 && pos.getColumn() < Fleet.BOARD_SIZE;
    }

    private boolean repeatedShot(IPosition pos) {
        return shots.contains(pos);
    }


    // Métodos de debug opcional
    public void printBoard(List<IPosition> positions, Character marker) {
        char[][] map = new char[Fleet.BOARD_SIZE][Fleet.BOARD_SIZE];

        for (int r = 0; r < Fleet.BOARD_SIZE; r++)
            for (int c = 0; c < Fleet.BOARD_SIZE; c++)
                map[r][c] = '.';

        for (IPosition pos : positions)
            map[pos.getRow()][pos.getColumn()] = marker;

        for (int row = 0; row < Fleet.BOARD_SIZE; row++) {
            for (int col = 0; col < Fleet.BOARD_SIZE; col++)
                System.out.print(map[row][col]);
            System.out.println();
        }
    }

    public void printValidShots() {
        printBoard(getShots(), 'X');
    }

    public void printFleet() {
        List<IPosition> shipPositions = new ArrayList<>();

        for (IShip s : fleet.getShips())
            shipPositions.addAll(s.getPositions());

        printBoard(shipPositions, '#');
    }
}
