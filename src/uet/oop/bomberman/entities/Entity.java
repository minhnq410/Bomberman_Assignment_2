package uet.oop.bomberman.entities;

import java.awt.Rectangle;

import javax.naming.TimeLimitExceededException;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Lớp đại diện cho tất cả thực thể trong game (Bomber, Enemy, Wall, Brick,...)
 */
public abstract class Entity implements IRender
{

	protected double _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;

	/**
	 * Phương thức này được gọi liên tục trong vòng lặp game, mục đích để xử lý sự
	 * kiện và cập nhật trạng thái Entity
	 */
	@Override
	public abstract void update();

	/**
	 * Phương thức này được gọi liên tục trong vòng lặp game, mục đích để cập nhật
	 * hình ảnh của entity theo trạng thái
	 */
	@Override
	public abstract void render(Screen screen);

	public void remove()
	{
		_removed = true;
	}

	public boolean isRemoved()
	{
		return _removed;
	}

	public Sprite getSprite()
	{
		return _sprite;
	}

	/**
	 * Phương thức này được gọi để xử lý khi hai entity va chạm vào nhau
	 * 
	 * @param e
	 * @return
	 */
	public abstract boolean collide(Entity e);

	public Rectangle getBounds()
	{
		if (this instanceof Bomber || this instanceof Enemy)
			return new Rectangle((int) _x, (int) _y, Game.TILES_SIZE, Game.TILES_SIZE);
		else return new Rectangle(Coordinates.tileToPixel(_x), Coordinates.tileToPixel(_y + 1), Game.TILES_SIZE,
				Game.TILES_SIZE);
	}

	public double getX()
	{
		return _x;
	}

	public double getY()
	{
		return _y;
	}

	public int getXTile()
	{
		return Coordinates.pixelToTile(_x + _sprite.SIZE / 2);
	}

	public int getYTile()
	{
		return Coordinates.pixelToTile(_y - _sprite.SIZE / 2);
	}
}
