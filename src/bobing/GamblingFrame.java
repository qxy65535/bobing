package bobing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


public class GamblingFrame extends JFrame{
	
	private FlowLayout gamblingFrameLayout;
	private JLabel currentPlayer;
	private JLabel[] dice;
	private Icon[] diceImg;
	private JList<String> playerList;

	private String[] playerListData;
	private JButton btnStart;
	private JButton btnStop;
	private DiceThread rollDiceThread;
	
	private GamblingGame gamblingGame;
	private int[] diceNum;
	private int prize;
	private String prizeName;
	
	public GamblingFrame(){
		super("博饼游戏");
		Font font = new Font("宋体", Font.PLAIN, 19); 
		UIManager.put("Button.font",font); 
		UIManager.put("Label.font",font); 
			
		gamblingFrameLayout = new FlowLayout(FlowLayout.CENTER, 20, 50);
		setLayout(gamblingFrameLayout);
		
		initViews();
		
	}
	
	private void initViews(){
		gamblingGame = new GamblingGame();
		//diceNum = new int[6];
		
		currentPlayer = new JLabel("", JLabel.CENTER);
		currentPlayer.setPreferredSize(new Dimension(900, 60));
		currentPlayer.setText("当前玩家：" + "");
		currentPlayer.setFont(new Font("黑体", Font.PLAIN, 30));
		add(currentPlayer);
		
		dice = new JLabel[6];
		diceImg = new ImageIcon[6];
		for (int i = 0; i < dice.length; ++i){
			dice[i] = new JLabel(/*String.valueOf(i), JLabel.CENTER*/);
			diceImg[i] = new ImageIcon(getClass().getResource("/" + String.valueOf(i+1) + ".jpg"));
			dice[i].setPreferredSize(new Dimension(80, 80));
			dice[i].setBorder(BorderFactory.createLineBorder(new Color(Integer.decode("#c1c1c1")), 1));
			//dice[i].setFont(new Font("黑体", Font.PLAIN, 50));
			dice[i].setIcon(diceImg[i]);
			add(dice[i]);
		}
		
		Box box = Box.createVerticalBox();
		playerList = new JList<String>();
		setPlayerListData(0);
		playerList.setListData(playerListData);
		playerList.setFont(new Font("宋体", Font.PLAIN, 19));
		//playerList.setPreferredSize(new Dimension(810, 200));
		playerList.setVisibleRowCount(8);
		box.add(new JScrollPane(playerList));
		box.add(Box.createVerticalStrut(30));
		//add(playerList);
		
		
		Box buttonBox = Box.createHorizontalBox();
		btnStart = new JButton("掷骰子");
		btnStart.setFont(new Font("黑体", Font.PLAIN, 20));
		btnStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				if (rollDiceThread == null || !rollDiceThread.isAlive()){
					rollDiceThread = new DiceThread();
					rollDiceThread.start();
				}
			}
		});
		//btnStart.setMargin(new Insets(0, 100, 0, 0));
		buttonBox.add(btnStart);
		buttonBox.add(Box.createHorizontalStrut(50));
		
		btnStop = new JButton("停　止");
		btnStop.setFont(new Font("黑体", Font.PLAIN, 20));
		btnStop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){

				if (rollDiceThread != null)
					rollDiceThread.stopThread();

			}
		});
		//btnStop.setMargin(new Insets(100, 0, 0, 0));
		buttonBox.add(btnStop);
		
		box.add(buttonBox);
		add(box);
		
	}
	
	public void setGameSettings(int n, int[] prize){
		System.out.println(String.valueOf(n));
		gamblingGame.setPlayerNum(n);
		gamblingGame.setTotalPrize(prize);
		setPlayerListData(n);
		
		playerList.setListData(playerListData);
		
		if (n > 0)
			currentPlayer.setText("当前玩家：" + gamblingGame.getCurrentPlayerName());
	}
	
	private void setPlayerListData(int n){

		playerListData = new String[n+1];
		//playerListData[0] = String.format(" 玩家名    一秀   二举   三红   四进   对堂   状元   五子登科   状元插金花   六杯红 ");
		playerListData[0] = String.format(" 玩家名    一秀   二举   三红   四进   对堂   状元   六杯红 ");
		for (int i = 0; i < gamblingGame.getPlayerCount(); ++i){
			playerListData[i+1] = gamblingGame.getPlayerInfo(i);
		}
	}
	
	private void showPrize(){
		
		switch (prize){
		case Prize.YI_XIU:
			prizeName = "一秀";
			break;
		case Prize.ER_JU:
			prizeName = "二举";
			break;
		case Prize.SAN_HONG:
			prizeName = "三红";
			break;
		case Prize.SI_JIN:
			prizeName = "四进";
			break;
		case Prize.DUI_TANG:
			prizeName = "对堂";
			break;
		case Prize.WU_ZI_DENG_KE:
			prizeName = "五子登科";
			break;
		case Prize.ZHUANG_YUAN:
			prizeName = "状元";
			break;
		case Prize.LIU_BEI_HONG:
			prizeName = "六杯红";
			break;
		case Prize.CHA_JIN_HUA:
			prizeName = "状元插金花";
			break;
		default:
			
		}
		
		if (prize == -1)
			JOptionPane.showMessageDialog(this, "很遗憾，您未获奖");
		else{
			StringBuffer str = new StringBuffer();
			for (int tmp: diceNum){
				str.append(String.valueOf(tmp) + ",");
			}
			if (gamblingGame.isAwardEmpty(prize))
				JOptionPane.showMessageDialog(this, str.toString() + "很遗憾，" + prizeName + "的奖品已被领取完");
			else{
				JOptionPane.showMessageDialog(this, str.toString() + prizeName + "，恭喜！");
				playerListData[gamblingGame.getCurrentPlayer()+1] = gamblingGame.getCurrentPlayerInfo();
				playerList.setListData(playerListData);
			}
		}
		if (gamblingGame.gameOver()){
			JOptionPane.showMessageDialog(this, "奖品领取完毕，游戏结束！");
			btnStart.setEnabled(false);
			btnStop.setEnabled(false);
		}
		else{
			gamblingGame.nextPlayer();
			currentPlayer.setText("当前玩家：" + gamblingGame.getCurrentPlayerName());
		}

		//System.out.println(" "+currentPlayerNum);
	}
	
	private class DiceThread extends Thread{
		private boolean run = true;
//		private int pos;
//		public DiceThread(int pos){
//			this.pos = pos;
//		}
		@Override
		public void run(){
			run = true;
			while (run){
				diceNum = gamblingGame.rollDice();
				for (int i = 0; i < dice.length; ++i){
					dice[i].setIcon(diceImg[diceNum[i]-1]);
				}

			}
//			for (int i = 0; i < diceNum.length; ++i)
//				diceNum[i] = i+1;
//			diceNum[0]=4;
//			diceNum[1]=4;
//			diceNum[2]=4;
//			diceNum[3]=4;
//			diceNum[4]=4;
//			diceNum[5]=4;
			prize = gamblingGame.judgePrize(diceNum);
			System.out.println(String.valueOf(prize));
			showPrize();
		}
		
		public void stopThread(){
			run = false;
		}
	}
}
