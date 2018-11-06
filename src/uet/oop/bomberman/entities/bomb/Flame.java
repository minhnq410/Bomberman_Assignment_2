package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;

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
				_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() - i - 1, _direction, false);
				if (i == _flameSegments.length - 1)_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() - i - 1, _direction, true);
			}
		}
		if (_direction == 1) // Right
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{
				_flameSegments[i] = new FlameSegment((int) this.getX() + i + 1, (int) this.getY(), _direction, false);
				if (i == _flameSegments.length - 1)_flameSegments[i] = new FlameSegment((int) this.getX() + i + 1, (int) this.getY(), _direction, true);
			}
		}
		if (_direction == 2) // Down
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{
				_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() + i + 1, _direction, false);
				if (i == _flameSegments.length - 1)_flameSegments[i] = new FlameSegment((int) this.getX(), (int) this.getY() + i + 1, _direction, true);
			}
		}
		if (_direction == 3) // Left
		{
			for (int i = 0; i < _flameSegments.length; i++)
			{
				_flameSegments[i] = new FlameSegment((int) this.getX() - i - 1, (int) this.getY(), _direction, false);
				if (i == _flameSegments.length - 1)_flameSegments[i] = new FlameSegment((int) this.getX() - i - 1, (int) this.getY(), _direction, true);
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

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
		return true;
	}
}
