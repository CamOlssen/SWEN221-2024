// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.connect.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import swen221.connect.core.Board;
import swen221.connect.core.Game;
import swen221.connect.util.Position;

public class ConnectTests {

	@Test public void test_01() {
		String output = "|_|_|_|_|\n" +
				"|_|_|_|_|\n" +
				"|_|_|_|_|\n" +
				"|_|_|_|_|\n";

		Board board = new Board();

		assertEquals(output,board.toString());
	}

	/**
	 * Test token placing
	 */
	@Test public void test_02(){
		Game game = new Game(new Board());
		try {
			game.placeToken(new Position(1, 1), Board.Token.WHITE);
		} catch(IllegalArgumentException e){assert false;}
	}

	/**
	 * Test that black cannot move when not black's turn
	 */
	@Test public void test_03(){
		Game game = new Game(new Board());
		Throwable exception = assertThrows(IllegalArgumentException.class, () ->game.placeToken(new Position(1, 1), Board.Token.BLACK));
		assertEquals(exception.getMessage(), "Illegal move: not black player's turn");
	}

	@Test public void test_03a(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0,0), Board.Token.WHITE);
		game.placeToken(new Position(0,1), Board.Token.BLACK);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> game.placeToken(new Position(3,3), Board.Token.BLACK));
		assertEquals("Illegal move: not black player's turn", exception.getMessage());
	}

	/**
	 * Test that white cannot move when not white's turn
	 */
	@Test public void test_04(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0,0), Board.Token.WHITE);
		Throwable exception = assertThrows(IllegalArgumentException.class, () ->game.placeToken(new Position(1, 1), Board.Token.WHITE));
		assertEquals(exception.getMessage(), "Illegal move: not white player's turn");
	}

	@Test public void test_04a(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0,0), Board.Token.WHITE);
		game.placeToken(new Position(0,1), Board.Token.BLACK);
		game.placeToken(new Position(1,1), Board.Token.WHITE);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> game.placeToken(new Position(1,2), Board.Token.WHITE));
		assertEquals("Illegal move: not white player's turn", exception.getMessage());
	}

	/**
	 * Test placing tokens on top of each other
	 */
	@Test public void test_05(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0,0), Board.Token.WHITE);
		Throwable exception = assertThrows(IllegalArgumentException.class, () ->game.placeToken(new Position(0, 0), Board.Token.BLACK));
		assertEquals(exception.getMessage(), "Illegal move: cannot place token on another token");
	}

	/**
	 * Test placing token after game is over
	 */
	@Test public void test_06(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0, 0), Board.Token.WHITE);
		game.placeToken(new Position(1, 3), Board.Token.BLACK);
		game.placeToken(new Position(0, 1), Board.Token.WHITE);
		game.placeToken(new Position(1, 2), Board.Token.BLACK);
		game.placeToken(new Position(0, 2), Board.Token.WHITE);
		game.placeToken(new Position(2, 3), Board.Token.BLACK);
		game.placeToken(new Position(0, 3), Board.Token.WHITE);
		Throwable exception = assertThrows(IllegalArgumentException.class, () ->game.placeToken(new Position(1, 1), Board.Token.BLACK));
		assertEquals(exception.getMessage(), "Illegal move: cannot place token after game is over");
	}

	/**
	 * Test capture rule
	 */
	@Test public void test_07(){
		String output = "|W|_|W|_|\n" +
				"|_|_|_|_|\n" +
				"|_|_|_|_|\n" +
				"|_|_|_|_|\n";
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Board.Token.WHITE);
		game.placeToken(new Position(1,0), Board.Token.BLACK);
		game.placeToken(new Position(2,0), Board.Token.WHITE);
		assertEquals(output,board.toString());
	}

	/**
	 * Tests column win rule
	 */
	@Test public void test_08(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0, 0), Board.Token.WHITE);
		game.placeToken(new Position(1, 3), Board.Token.BLACK);
		game.placeToken(new Position(0, 1), Board.Token.WHITE);
		game.placeToken(new Position(1, 2), Board.Token.BLACK);
		game.placeToken(new Position(0, 2), Board.Token.WHITE);
		game.placeToken(new Position(2, 3), Board.Token.BLACK);
		game.placeToken(new Position(0, 3), Board.Token.WHITE);
		assertTrue(game.getStatus() == Game.Status.WHITEWON);
	}

	/**
	 * Test row win rule
	 */
	@Test public void test_09(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0, 1), Board.Token.WHITE);
		game.placeToken(new Position(0, 3), Board.Token.BLACK);
		game.placeToken(new Position(1, 1), Board.Token.WHITE);
		game.placeToken(new Position(1, 2), Board.Token.BLACK);
		game.placeToken(new Position(2, 1), Board.Token.WHITE);
		game.placeToken(new Position(2, 3), Board.Token.BLACK);
		game.placeToken(new Position(3, 1), Board.Token.WHITE);
		assertTrue(game.getStatus() == Game.Status.WHITEWON);
	}

	/**
	 * Test stalemate rule
	 */
	@Test public void test_10(){
		Game game = new Game(new Board());
		game.placeToken(new Position(0, 0), Board.Token.WHITE);
		game.placeToken(new Position(3, 3), Board.Token.BLACK);
		game.placeToken(new Position(3, 0), Board.Token.WHITE);
		game.placeToken(new Position(0, 3), Board.Token.BLACK);
		game.placeToken(new Position(2, 2), Board.Token.WHITE);
		game.placeToken(new Position(1, 1), Board.Token.BLACK);
		game.placeToken(new Position(3, 1), Board.Token.WHITE);
		game.placeToken(new Position(0, 2), Board.Token.BLACK);
		game.placeToken(new Position(0, 1), Board.Token.WHITE);
		game.placeToken(new Position(3, 2), Board.Token.BLACK);
		game.placeToken(new Position(1, 0), Board.Token.WHITE);
		game.placeToken(new Position(1, 3), Board.Token.BLACK);
		game.placeToken(new Position(1, 2), Board.Token.WHITE);
		game.placeToken(new Position(1, 1), Board.Token.BLACK);
		game.placeToken(new Position(2, 1), Board.Token.WHITE);
		game.placeToken(new Position(2, 2), Board.Token.BLACK);
		assertTrue(game.getStatus() == Game.Status.STALEMATE);
	}

	/**
	 * Test placing out of bounds
	 */
	@Test public void test_11(){
		Game game = new Game(new Board());
		Throwable exception1 = assertThrows(IllegalArgumentException.class, () ->game.placeToken(new Position(4,2), Board.Token.WHITE));
		assertTrue(exception1.getMessage().contains("Invalid X component: "));
		Throwable exception2 = assertThrows(IllegalArgumentException.class, () ->game.placeToken(new Position(2,4), Board.Token.BLACK));
		assertTrue(exception2.getMessage().contains("Invalid Y component: "));
	}
}
