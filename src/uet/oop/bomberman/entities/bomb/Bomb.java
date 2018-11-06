package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	protected int _baseRadius = 3;
	
	protected Board _board;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}

    /**
     * Xử lý Bomb nổ
     */
	protected void explode() {
		_exploded = true;
		int leftRadius = 0, rightRadius = 0, upRadius = 0, downRadius = 0;
		for (int i = (int) this.getX() + 1; i <= (int) this.getX() + _baseRadius; i++)
		{
			if (_board.getEntityAt(i, this.getY()) instanceof Grass)
				rightRadius++;
			else break;
		}
		for (int i = (int) this.getX() - 1; i >= (int) this.getX() - _baseRadius; i--)
		{
			if (i == 0) break;
			if (_board.getEntityAt(i, this.getY()) instanceof Grass)
				leftRadius++;
			else break;
		}
		for (int i = (int) this.getY() + 1; i <= (int) this.getY() + _baseRadius; i++)
		{
			if (_board.getEntityAt(this.getX(), i) instanceof Grass)
				downRadius++;
			else break;
		}
		for (int i = (int) this.getY() - 1; i >= (int) this.getY() - _baseRadius; i--)
		{
			if (i == 0) break;
			if (_board.getEntityAt(this.getX(), i) instanceof Grass)
				upRadius++;
			else break;
		}
		// TODO: xử lý khi Character đứng tại vị trí Bomb
		
		// TODO: tạo các Flame
		_flames = new Flame[4];
		_flames[0] = new Flame((int) this.getX(),(int) this.getY(), 0, upRadius, _board);
		_flames[1] = new Flame((int) this.getX(),(int) this.getY(), 1, rightRadius, _board);
		_flames[2] = new Flame((int) this.getX(),(int) this.getY(), 2, downRadius, _board);
		_flames[3] = new Flame((int) this.getX(),(int) this.getY(), 3, leftRadius, _board);
		
		
	}
	
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

	@Override
	public boolean collide(Entity e) {
        // TODO: xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThru)
        // TODO: xử lý va chạm với Flame của Bomb khác
        return false;
	}
}
