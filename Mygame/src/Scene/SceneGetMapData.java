package Scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SceneGetMapData {
	private int width;
	private int height;
	private char[][] mapData;
	
	public SceneGetMapData(String filename) {
		//接受一个文件，把文件内容变成matrix
		File newMap=new File(filename);
		Scanner mapConstructor;
		//用于扫描文件内容，可以在后面通过nextLine()读出来
		try {
			mapConstructor=new Scanner(newMap);
			String dimensions=mapConstructor.nextLine();
					//nextLine()用于接受字符串，空格也接受
			width=Integer.parseInt(dimensions.substring(0,dimensions.indexOf("x")));
			
			//System.out.print(dimensions.indexOf("x"));//2
			//数据索引从0开始，substring截取0到dimensions.indexOf("x")-1位的数据
			
			//width，length就是为了得出.map文件中的地图长和高
			//parseInt将String转化为int型	
			height = Integer.parseInt(dimensions.substring(dimensions.indexOf('x')+1));
		    
			mapData=new char[height][width];
			for(int y=0;y<height;y++) {
				String mapLine=mapConstructor.nextLine();
				//nextLine()在上面用过一次，这里指针指向了第二行
				char[] StringtoChar=mapLine.toCharArray();
				//mapLine.toCharArray()能够让string成为char[]数组
				for(int x=0;x<width;x++) {
					mapData[y][x]=StringtoChar[x];
				}				
			}
		
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();//这个是打印异常以及异常出现的位置
		}		
	}
	public char[][] getData(){
		return mapData;
	}


}
//这个类就是用构造函数生成了一个包含地图文件数据的二级数组。
//然后通过getData()就能得到这个二级数组。