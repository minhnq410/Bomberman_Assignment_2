package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import uet.oop.bomberman.entities.tile.Grass;

public abstract class Enemy extends Character
{

	protected int _points;

	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;

	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;

	private Sound killed_sound = TinySound.loadSound("sounds/enemykilled.wav");
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points)
	{
		super(x, y, board);

		_points = points;
		_speed = speed;

		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;

		_timeAfter = 20;
		_deadSprite = dead;
	}

	@Override
	public void update()
	{
		animate();

		if (!_alive)
		{
			afterKill();
			return;
		}

		if (_alive)
			calculateMove();
		this.collide(_board.getEntityAt(Coordinates.pixelToTile(this._x),
				Coordinates.pixelToTile(this._y - Game.TILES_SIZE)));
	}

	@Override
	public void render(Screen screen)
	{

		if (_alive)
			chooseSprite();
		else
		{
			if (_timeAfter > 0)
			{
				_sprite = _deadSprite;
				_animate = 0;
			}
			else
			{
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}

		}

		screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
	}

	@Override
	public void calculateMove()
	{
		// TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho
		// _direction
		// hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính
		// toán hay không
		// TODO: sử dụng move() để di chuyển
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		int xa = 0, ya = 0;
		if (_steps <= 0)
		{
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}

		if (_direction == 0)
		{
			ya++;
			if (canMove(this._x + this.getSprite().SIZE / 2, this._y + this.getSprite().SIZE * 3 / 4 + 1)
					&& canMove(this._x, this._y + this.getSprite().SIZE * 3 / 4))
			{
				_steps--;
				move(0, _speed);
				_moving = true;
			}
			else
			{
				_steps = 0;
				_moving = false;

			}
		}
		if (_direction == 1)
		{
			xa++;
			if (canMove(this._x + this.getSprite().SIZE / 2 + 1, this._y + this.getSprite().SIZE / 2 + 1)
					&& canMove(this._x + this.getSprite().SIZE / 2 + 1, this._y))
			{
				_steps--;
				move(_speed, 0);
				_moving = true;
			}
			else
			{
				_steps = 0;
				_moving = false;

			}
		}
		if (_direction == 2)
		{
			xa--;
			if (canMove(this._x - this.getSprite().SIZE / 2 - 1, this._y + this.getSprite().SIZE / 2 + 1)
					&& canMove(this._x - this.getSprite().SIZE / 2 - 1, this._y))
			{
				_steps--;
				move(-_speed, 0);
				_moving = true;
			}
			else
			{
				_steps = 0;
				_moving = false;

			}
		}
		if (_direction == 3)
		{
			ya--;
			if (canMove(this._x + this.getSprite().SIZE / 2, this._y - this.getSprite().SIZE / 4 - 1)
					&& canMove(this._x, this._y - this.getSprite().SIZE / 4))
			{
				_steps--;
				move(0, -_speed);
				_moving = true;
			}
			else
			{
				_steps = 0;
				_moving = false;

			}
		}
	}

	@Override
	public void move(double xa, double ya)
	{
		if (!_alive)
			return;
		_y += ya;
		_x += xa;

	}

	@Override
	public boolean canMove(double x, double y)
	{
		// TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di
		// chuyển tới đó hay không
		return !(_board
				.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE / 4),
						Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE / 4))
				.collide(this)
				&& !(_board.getEntityAt(Coordinates.pixelToTile(x + this.getSprite().SIZE / 4),
						Coordinates.pixelToTile(y - Game.TILES_SIZE + this.getSprite().SIZE / 4)) instanceof Grass));

	}
	@Override
	public boolean collide(Entity e)
	{
		for (int i = 0; i < _board._characters.size(); i++)
		{
			if (_board._characters.get(i) instanceof Bomber
					&& _board._characters.get(i).getBounds().intersects(this.getBounds()))
			{
				_board._characters.get(i).kill();
			}
		}
		return e.collide(this);
		
	}

	@Override
	public void kill()
	{
		if (!_alive)
			return;
		_alive = false;

		killed_sound.play();
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}

	@Override
	protected void afterKill()
	{
		if (_timeAfter > 0)
			--_timeAfter;
		else
		{
			if (_finalAnimation > 0)
				--_finalAnimation;
			else remove();
		}
	}

	protected abstract void chooseSprite();
}
