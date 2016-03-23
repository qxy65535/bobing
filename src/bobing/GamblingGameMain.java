package bobing;

import javax.swing.JFrame;

public class GamblingGameMain {
	public static void main(String[] args){
		
		//System.out.println("Gambling");
		GamblingFrame gamblingFrame = new GamblingFrame();
		gamblingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//gamblingFrame.setResizable(false);
		gamblingFrame.setSize(900, 670);
		gamblingFrame.setVisible(true);
		
		GameSettingDialog gameSettingDialog = new GameSettingDialog(gamblingFrame, true, "”Œœ∑…Ë÷√");
		gameSettingDialog.setLocationRelativeTo(gamblingFrame);
		gameSettingDialog.setVisible(true);
		
		System.out.println("a");
	}
	
}
