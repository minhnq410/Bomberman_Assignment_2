package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;
        
        this.collide(_board.getEntityAt(Coordinates.pixelToTile(this._x), Coordinates.pixelToTile(this._y - this.getSprite().SIZE*3/4)));
        
        animate();
        
        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        
    	if (_input.space && _timeBetweenPutBombs < -25 && Game.getBombRate() > 0)
    	{
    		this.placeBomb(this.getXTile(), this.getYTile());
    		Game.addBombRate(-1);
    		_timeBetweenPutBombs = 0;
    	}
    	// TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
    }

    protected void placeBomb(int x, int y) {
       _bombs.add(new Bomb(x, y, _board));
    	// TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
    	double _speed = 1;
    	if (_input.up)
    	{
	    	if (canMove(this._x + this.getSprite().SIZE/4, this._y - this.getSprite().SIZE/4) && canMove(this._x, this._y - this.getSprite().SIZE/4))
	    	{
	    		move(0, _speed);
	    		_moving = true;
	    		
	    	}
    	}
    	else if (_input.down)
    	{
	    	if (canMove(this._x + this.getSprite().SIZE/4, this._y + this.getSprite().SIZE*3/4) && canMove(this._x, this._y + this.getSprite().SIZE*3/4))
	    	{
	    		move(0, _speed);
	    		_moving = true;
	    	}
    	}
    	else if (_input.left)
    	{
	    	if (canMove(this._x - this.getSprite().SIZE/4 - 1, this._y + this.getSprite().SIZE/2 + 1) && canMove(this._x - this.getSprite().SIZE/4 - 1, this._y))
	    	{
	    		move(_speed, 0);
	    		_moving = true;
	    	}
    	}
    	else if (_input.right)
    	{
	    	if (canMove(this._x + this.getSprite().SIZE/4 + 1, this._y + this.getSprite().SIZE/2 + 1) && canMove(this._x + this.getSprite().SIZE/4 + 1, this._y))
	    	{
	    		move(_speed, 0);
	    		_moving = true;
	    	}
    	}
    	else _moving = false;
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
    }

    @Override
    public boolean canMove(double x, double y) 
    {
    	if (_board.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE/4), Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE/4)).collide(this) && !(_board.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE/4), Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE/4)) instanceof Grass))
    		return false;
    	
    	else return true;
    }

    @Override
    public void move(double xa, double ya) 
    {
    	if (_input.up)
    	{
    		this._y -= ya;
    		_direction = 0;
    	}
    	if (_input.right) 
    	{
    		this._x += xa;
    		_direction = 1;
    	}
    	if (_input.down) 
    	{
    		this._y += ya;
    		_direction = 2;
    	}
    	if (_input.left) 
    	{
    		this._x -= xa;
    		_direction = 3;
    	}
    	// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
    }

    @Override
    public boolean collide(Entity e) {
    	return e.collide(this);
    	
    	
    	// TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy

       
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
