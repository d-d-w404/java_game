package prefs;

public class PRFS {
	//Game Dimensions
		public static int GameWidth = 600;
		public static int GameHeight = 600;
		//Refresh Speed
		public static int fps = 10;
		public static int frmNum = 0;
		//感觉这个就控制我的人物的刷新率相对于真正的刷新率是多少
		//fps=20就代表：
		//系统每刷新一次，frmNum加一，当加到20时就再次变为0，并draw一次
		//像我的block的draw就是系统每刷新一次，draw一次
		//而我人物的draw就是每frmNum次系统刷新后draw一次。

}
