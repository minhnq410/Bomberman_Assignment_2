package uet.oop.bomberman;

import kuusisto.tinysound.TinySound;
import uet.oop.bomberman.gui.Frame;

public class BombermanGame
{

	public static void main(String[] args)
	{
		TinySound.init();
		new Frame();
	}
}
