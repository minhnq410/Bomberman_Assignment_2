package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.character.Character;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param radius độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * biến last dùng để đánh dấu cho segment cuối cùng
		 */

		// TODO: tạo các segment dưới đây
		if (_direction == 0) // Up
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{
				
				if (i == _flameSegments.length - 1)
				{
					_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() - i - 1, _direction, true);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), new LayeredEntity((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY(), new Grass((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY(), Sprite.grass), _flameSegments[i]));
				}
				else 
				{
					_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() - i - 1, _direction, false);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), new LayeredEntity((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY(), new Grass((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY(), Sprite.grass), _flameSegments[i]));
				}
				
			}
		}
		if (_direction == 1) // Right
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{
				
				if (i == _flameSegments.length - 1)
				{
					_flameSegments[i] = new FlameSegment((int) this.getX() + i + 1, (int) this.getY(), _direction, true);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), _flameSegments[i]);
				}
				else
				{
					_flameSegments[i] = new FlameSegment((int) this.getX() + i + 1, (int) this.getY(), _direction, false);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), _flameSegments[i]);
				}
				
			}
		}
		if (_direction == 2) // Down
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{
				
				if (i == _flameSegments.length - 1)
				{
					_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() + i + 1, _direction, true);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), _flameSegments[i]);
				}
				else
				{
					_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() + i + 1, _direction, false);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), _flameSegments[i]);
				}
				
			}
		}
		if (_direction == 3) // Left
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{				
				if (i == _flameSegments.length - 1)
				{
					_flameSegments[i] = new FlameSegment((int) this.getX() - i - 1, (int) this.getY(), _direction, true);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), _flameSegments[i]);
				}
				else
				{
					_flameSegments[i] = new FlameSegment((int) this.getX() - i - 1, (int) this.getY(), _direction, false);
					if (_board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY()) instanceof LayeredEntity)
					{
						LayeredEntity tmp = (LayeredEntity) _board.getEntityAt((int) _flameSegments[i].getX(), (int) _flameSegments[i].getY());
						tmp.addToTop(_flameSegments[i]);
					}
					else _board.addEntity((int) (_flameSegments[i].getX() + _flameSegments[i].getY() * 31), _flameSegments[i]);
				}
		
			}
		}
	}

	/**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
	private int calculatePermitedDistance() {
		return _radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	//Xóa vết lửa sau khi bom nổ
	@Override
	public void update() 
	{
			for (int i = 0; i < _board._entities.length; i++)
			{
				if (_board._entities[i] instanceof LayeredEntity) 
				{
					LayeredEntity tmp = (LayeredEntity) _board._entities[i];
					if (tmp.getTopEntity() instanceof FlameSegment)
					{
						while (!(tmp.getTopEntity() instanceof Grass) && !(tmp.getTopEntity() instanceof Item))
						{
							tmp.getTopEntity().remove();
							tmp.update();
						}
					}
				}
				else if (_board._entities[i] instanceof FlameSegment)
				{
					_board.addEntity(i, new Grass((int) _board._entities[i].getX(),(int) _board._entities[i].getY(), Sprite.grass));
				}
			}
	}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Character)
		{
			((Character) e).kill();
			return true;
		}
		return false;
	}
}
