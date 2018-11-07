package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException {
            BufferedReader br = null;
            try {
                // TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
                // TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
                String s = FileLevelLoader.class.getClassLoader().getResource("").toString();
                s = s.substring(5);
                s = s.replace("/", "\\");
                File LevelsFile = new File(s + "levels\\Level" + level + ".txt");
                br = new BufferedReader(new FileReader(LevelsFile));
                
                String metaData = br.readLine();
                String[] temp;
                temp = metaData.split("\\s", 3);
                
                _level = Integer.parseInt(temp[0]);
                _height = Integer.parseInt(temp[1]);
                _width = Integer.parseInt(temp[2]);
                
                _map = new char[_height][_width];
                for (int i = 0; i < _height; i++)
                {
                    String line = br.readLine();
                    for(int j = 0; j < _width;j++)
                    {
                        _map[i][j] = line.charAt(j);
                    }
                }
            } catch (IOException ex) {
                throw new LoadLevelException();
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileLevelLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}

	@Override
	public void createEntities() {
            // TODO: tạo các Entity của màn chơi
            // TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

            // TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
            // TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
                
                for (int y = 0; y < _height; y++)
                {
                    for (int x = 0; x < _width; x++)
                    {
                        switch (_map[y][x])
                        {
                            case '#': //Wall
                            {
                                _board.addEntity(x + y * _width, new Wall(x, y, Sprite.wall));
                                break;
                            }
                            case '*': //Brick
                            {
                                _board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
                                break;
                            }
                            case 'x': //Portal
                            {
                                _board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal)));
                                break;
                            }
                            case 'p': //Bomber
                            {
                                _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                                Screen.setOffset(0, 0);
                                _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                                break;
                            }
                            case '1': //Balloon
                            {
                                _board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                                _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                                break;
                            }
                            case '2': //Oneal
                            {
                                _board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                                _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                                break;
                            }
                            case 'b': //Bomb item
                            {
                                _board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass)
                                                , new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
                                break;
                            }
                            case 'f': //Flame item
                            {
                                _board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass)
                                                , new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
                                break;
                            }
                            case 's': //Speed item
                            {
                                _board.addEntity(x + y * _width, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass)
                                                , new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
                                break;
                            }
                            default: //Grass
                            {
                                _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                            }
                        }
                    }
                }
	}

}
