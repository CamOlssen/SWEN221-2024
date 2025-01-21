package swen221.conway.rules;

import swen221.conway.model.*;
import swen221.conway.util.ConwayAbstractRule;

public class CellDecayUnderpopulationRule extends ConwayAbstractRule {
    @Override
    public int apply(int x, int y, int neighbours, BoardView board) {
        if (neighbours < 2) {
            int cellstate = board.getCellState(x,y);
            System.out.println(cellstate);
            if(cellstate >= ALIVE && cellstate < DEAD){
                cellstate++;
                System.out.println(cellstate);
                return cellstate;
            }
        }
        return Rule.NOT_APPLICABLE;
    }
}
