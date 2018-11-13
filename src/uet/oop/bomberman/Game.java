package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục, Gọi phương
 * thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas implements MouseListener
{

	public static final int TILES_SIZE = 16, WIDTH = TILES_SIZE * (31 / 2), HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;

	public static final String TITLE = "BombermanGame";

	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final double BOMBERSPEED = 1.0;

	public static final int TIME = 200;
	public static final int POINTS = 0;

	protected static int SCREENDELAY = 3;

	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;

	protected int _screenDelay = SCREENDELAY;

	private Keyboard _input;
	private boolean _running = false;
	private boolean _paused = true;
	private boolean _menu = true;
	
	private Board _board;
	private Screen screen;
	private Frame _frame;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game(Frame frame)
	{
		_frame = frame;
		_frame.setTitle(TITLE);

		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
		addMouseListener(this);
	}

	private void renderGame()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		_board.render(screen);

		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = screen._pixels[i];
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);

		g.dispose();
		bs.show();
	}

	private void renderScreen()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		Graphics g = bs.getDrawGraphics();

		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update()
	{
		_input.update();
		_board.update();
	}

	public void start()
	{		
		
		//while (_menu)
			//renderScreen();
		_running = true;
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; // nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		while (_running)
		{
			/* try
			{
				Thread.sleep(13);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			} */
			//TODO: Giải quyết fps
			
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				update();
				updates++;
				delta--;
			}
			
			if (_input.esc) _paused = true;
			
			
			if (_paused)
			{
				
				if (_screenDelay <= 0)
				{
					_board.setShow(3);
					_paused = false;
				}
				renderScreen();
			}
			else
			{
				renderGame();
			}
			
			frames++;
			if (System.currentTimeMillis() - timer > 1000)
			{
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
			
				if (_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}

	public static double getBomberSpeed()
	{
		return bomberSpeed;
	}

	public static int getBombRate()
	{
		return bombRate;
	}

	public static int getBombRadius()
	{
		return bombRadius;
	}

	public static void addBomberSpeed(double i)
	{
		bomberSpeed += i;
	}

	public static void addBombRadius(int i)
	{
		bombRadius += i;
	}

	public static void addBombRate(int i)
	{
		bombRate += i;
	}

	public void resetScreenDelay()
	{
		_screenDelay = SCREENDELAY;
	}

	public Board getBoard()
	{
		return _board;
	}

	public boolean isPaused()
	{
		return _paused;
	}

	public void pause()
	{
		_paused = true;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		/* Rectangle playButton = new Rectangle(Game.WIDTH, Game.HEIGHT + 50, 200, 100);
		if (playButton.contains(e.getX(), e.getY()));
		{
			_menu = false;
			_running = true;
			_board.loadLevel(1);
		} */
		// TODO: Giải quyết nullpointerexception khi click vào play
	}

	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

}
