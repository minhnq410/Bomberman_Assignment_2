package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Ghost extends Enemy
{

	public Ghost(int x, int y, Board board)
	{
		super(x, y, board, Sprite.ghost_dead, Game.getBomberSpeed() / 2, 100);

		_sprite = Sprite.ghost_left1;

		_ai = new AILow();
		_direction = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite()
	{
		switch (_direction)
		{
		case 0:
		case 1:
			_sprite = Sprite.movingSprite(Sprite.ghost_right1, Sprite.ghost_right2, Sprite.ghost_right3, _animate,
					60);
			break;
		case 2:
		case 3:
			_sprite = Sprite.movingSprite(Sprite.ghost_left1, Sprite.ghost_left2, Sprite.ghost_left3, _animate,
					60);
			break;
		}
	}
        
        @Override
	public boolean canMove(double x, double y)
	{
		return !(_board
				.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE / 4),
						Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE / 4))
				.collide(this)
				&& !(_board.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE / 4),
						Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE / 4)) instanceof Grass))
                                && !(_board.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE / 4),
                                                Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE / 4)) instanceof Brick);
		
	}
        
        
}
