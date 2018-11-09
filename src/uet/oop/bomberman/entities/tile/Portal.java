package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {

	private Board _board;
	public Portal(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite);
		_board = board;
	}
	
	@Override
	public boolean collide(Entity e) {
		if (e instanceof Bomber)
		{
			boolean allEnemiesDead = true;
			for (int i = 0; i < _board._characters.size(); i++)
			{
				if (_board._characters.get(i) instanceof Enemy)
				{
					allEnemiesDead = false;
					break;
				}
			}
			if (allEnemiesDead) _board.nextLevel();
		}
		return false;
	}

}
