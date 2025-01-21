package swen221.conway.rules;

import swen221.conway.model.*;
import swen221.conway.util.ConwayAbstractRule;

public class CellDecayOverpopulationRule extends ConwayAbstractRule {

    @Override
    public int apply(int x, int y, int neighbours, BoardView board) {
        if(neighbours > 3){
            int cellstate = board.getCellState(x, y);
            if(cellstate >= ConwayAbstractRule.ALIVE && cellstate < ConwayAbstractRule.DEAD){
                cellstate++;
                return cellstate;
            }
        }
        return Rule.NOT_APPLICABLE;
    }
}
