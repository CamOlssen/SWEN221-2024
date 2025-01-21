// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.picturepuzzle.moves;

import swen221.picturepuzzle.model.Board;
import swen221.picturepuzzle.model.Cell;
import swen221.picturepuzzle.model.Location;
import swen221.picturepuzzle.model.Operation;

/**
 * Responsible for rotating the image data in a given cell in a clockwise
 * direction.
 *
 * @author betty
 *
 */
public class Rotation implements Operation {
	/**
	 * Location of cell which is to be rotated.
	 */
	private final Location location;
	/**
	 * Number of steps to rotate (in a clockwise direction) where each step is a
	 * 90degree rotation.
	 */
	private final int steps;

	/**
	 * Construction a rotation for the cell at a given location, rotating a given
	 * number of steps.
	 *
	 * @param loc   Location of cell to be rotated.
	 * @param steps Number of rotations to apply.
	 */
	public Rotation(Location loc, int steps) {
		this.location = loc;
		this.steps = steps;
	}

	/**
	 * Apply rotation to the selected cell.
	 *
	 * @param board Board where rotation is being applied.
	 */
	@Override
	public void apply(Board board) {
		Cell cell = board.getCellAt(this.location);
		if(cell == null) return;

		int[] image = cell.getImage();
		int[] rotated = new int[image.length];
		int cellWidth = cell.getWidth();

		//Rotate for as many steps as we want
		for(int i = 0; i < steps; i++){
			//Iterate through the old image array and put the pixel values at corresponding positions into a new array
			//k & j are our y and x values in the image. We loop through each row/column of pixels and apply the transformation
			for(int j = 0; j < cellWidth; j++){
				for(int k = 0; k < cellWidth; k++){
					rotated[j*cellWidth+k] = image[(cellWidth - j - 1) * cellWidth + k];
				}
			}

			//Apply the RGB values to the cell. Need a separate loop for this as it requires rotated[] to be fully created
			for(int j = 0; j < cellWidth; j++){
				for(int k = 0; k < cellWidth; k++){
					cell.setRGB(j, k, rotated[j * cellWidth + k]);
				}
			}
		}
	}
}
