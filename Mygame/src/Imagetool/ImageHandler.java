package Imagetool;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
	public static Image loadImage(String filename) {
		Image img=null;
		try {
			File imgFile=new File(filename);
			img=ImageIO.read(imgFile);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	//由于不是全部的图都是序列图，有的时候会出现很多个图合成一个动作
	//所以我打算写一个con_Image()函数把多个图合成一个Image[]对象
	public static Image[] con_Image(String file) {
		int numOfImages=picCount(file);
        Image[] conImage=new Image[numOfImages];
        for(int i=0;i<numOfImages;i++) {
        	
            //String filename=file.concat("/");
            String filename=file.concat("/"+String.valueOf(i+1)+".gif");
            //filename=file.concat(".gif");
            //System.out.println(filename);
            Image simpleImage=loadImage(filename);
            conImage[i]=simpleImage;
        }
		return conImage;

		
	}
	
	
	//loadImage函数只是简单的从图像文件生成一个Image对象，不用切割
	//是用于地图中的块等一些图片文件中没有子图的
	
	
	//由于还是要知道图片的个数，并在Player类中写出有几张图片，有点麻烦
	//且耦合有点高（感觉就是这个意思）
	//所以我打算加一个统计文件夹中文件个数的函数，作为numOfImages,这样
	//我以后有了新的组图，只需要把它放入相关的文件中就行，直接在Player中
	//调用con_Image()即可，不需要输入numOfImages
	//但是注意，文件夹中只能有图片，其他类型的文件也能被算进去。（以后改进）
	
	private static int picCount(String filename) {
		File file=new File(filename);//filename是文件的路径
		File list[] = file.listFiles();//用于获得文件夹file下有多少个文件
		return list.length;
		}

	
	public static Image[] split(int numOfImages,String filename) {
		Image mainImage=null;
		//整个文件都导入时生成的大图，可能带有几个不同状态的小图
		Image[] splitImages=new Image[numOfImages];
		try {
			File imgFile=new File(filename);
			mainImage=ImageIO.read(imgFile);
			
			//下面是定义每个小图的长和宽
			int imageWidth=mainImage.getWidth(null)/numOfImages;
			int imageHeight=mainImage.getHeight(null);
			//上面的代码通过mainImage得到它的长度和宽度，在文件中，我们不知道
			//图像的长和宽，这就是我们要生成mainImage的原因
			//我们还可以知道图形文件的布局必然是子图一列排的
			
			for(int i=0;i<numOfImages;i++) {
				BufferedImage splitImg=new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
				//splitImg相当于每一个子图了
				splitImg.createGraphics().drawImage(mainImage,0,0,imageWidth,imageHeight,i*imageWidth,0,(i+1)*imageWidth,imageHeight,null);
				//上面的代码使用了drawImage，似乎是人物的画图
				splitImages[i]=splitImg;
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return splitImages;
	}//通过split函数将文件中的组图分成一组Image数组

}

//就是一个用于导入图形的类
