package swen221.conway.rules;

import swen221.conway.model.BoardView;
import swen221.conway.model.Rule;
import swen221.conway.util.ConwayAbstractRule;

public class CellDecayHappinessRule extends ConwayAbstractRule {
    @Override
    public int apply(int x, int y, int neighbours, BoardView board) {
        if (neighbours == 3) {
            int cellstate = board.getCellState(x,y);
            if(cellstate > ALIVE && cellstate <= DEAD){
                cellstate--;
                return cellstate;
            }
        }
        return Rule.NOT_APPLICABLE;
    }
}
