package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
        private final int CHASE_THRESHOLD = 5;
	
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
                // Nếu khoảng cách (theo Tiles) giữa Bomber và Enemy nhỏ hơn bằng CHASE_THRESHOLD thì Enemy sẽ có xu hướng đi về phía Bomber,
                // nếu khoảng cách lớn hơn CHASE_THRESHOLD thì như AILow.
                int X_distance = _bomber.getXTile() - _e.getXTile();
                int Y_distance = _bomber.getYTile() - _e.getYTile();
                boolean _chasing = Math.abs(X_distance) <= CHASE_THRESHOLD && Math.abs(Y_distance) <= CHASE_THRESHOLD;
                
                if ( _chasing )
                {
                    int moveHorizontal = random.nextInt(2);
                    if (moveHorizontal == 1)
                    {
                        if (X_distance < 0) return 2; // Trái
                        else return 1; // Phải
                    } else {
                        if (Y_distance < 0) return 3; // Lên
                        else return 0; // Xuống
                    }
                } else {
                    return random.nextInt(4);
                }

	}

}
