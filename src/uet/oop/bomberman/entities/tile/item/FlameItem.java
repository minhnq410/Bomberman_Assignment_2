package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item
{

	private Board _board;

	public FlameItem(int x, int y, Sprite sprite, Board board)
	{
		super(x, y, sprite);
		_board = board;
	}

	@Override
	public boolean collide(Entity e)
	{
		if (e instanceof Bomber)
		{
			_board.addEntity((int) (this._x + this._y * _board.getLevel().getWidth()),
					new Grass((int) this._x, (int) this._y, Sprite.grass));
			Game.addBombRadius(1);
			;
		}
		return false;
	}

}
