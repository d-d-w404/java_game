package Scene;

import java.util.ArrayList;

import Block.Block;
import Block.BlockType;

public class SceneAddBlocks {
	private char[][] walls;
	private ArrayList<Block> blocks =new ArrayList<Block>();
	private static int blockSize=100;
	
	
	public SceneAddBlocks(SceneGetMapData scenegetmapdata) {
		walls=scenegetmapdata.getData();
		//把Game中生成的worldmap中的数据传入walls,并在fillBlocks()
		//中画出来。
		fillBlocks();
	}
	
	private void fillBlocks() {
		for(int i=0;i<walls.length;i++) {
			for(int j=0;j<walls[i].length;j++) {
				switch(walls[i][j]) {
				case 'g'://这里不能用""，有点怪
					blocks.add(new Block(BlockType.PLATFORM,j*blockSize,i*blockSize,blockSize,blockSize,false));
					continue;
				case '1':
					blocks.add(new Block(BlockType.BLOCK, j*blockSize, i*blockSize, blockSize, blockSize,true));
					continue;
				case 'd':
					blocks.add(new Block(BlockType.CAOPI, j*blockSize, i*blockSize, blockSize, blockSize,true));
					continue;
				case 'c':
					blocks.add(new Block(BlockType.CAOSHI, j*blockSize, i*blockSize, blockSize, blockSize,true));
					continue;
				case 's':
					blocks.add(new Block(BlockType.SHITOU, j*blockSize, i*blockSize, blockSize, blockSize,true));
					continue;
				case 'y':
					blocks.add(new Block(BlockType.YOUKUANG, j*blockSize, i*blockSize, blockSize, blockSize,true));
					continue;
				case 'z':
					blocks.add(new Block(BlockType.ZUOKUANG, j*blockSize, i*blockSize, blockSize, blockSize,true));
					continue;
				}
			}
		}
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public char[][] getwalls(){
		return walls;
	}

}
//从Scenemap中得到含有地图文件数据的二维数组
//依据数组的信息生成Blocks数列