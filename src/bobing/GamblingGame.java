package bobing;

import java.util.Random;

public class GamblingGame {
	
	private int[] prizeNum;
	private int[] prizeCount;
	private int[] dice;
	private int[] diceCount;
	
	private String[] playerName;
	private int playerNum;
	private int currentPlayerNum;
	private int[][] playerPrizeInfo;
	
	public GamblingGame(){
		prizeNum = new int[Prize.PRIZE_COUNT];
		prizeCount = new int[Prize.PRIZE_COUNT];
		dice = new int[6];
		diceCount = new int[6];
		this.playerNum = 0;
//		setPlayers(playerNum);
//		
		for (int i = 0; i < Prize.PRIZE_COUNT; ++i){
//			prizeNum[i] = 0;
			prizeCount[i] = 0;
		}
	}
	
	public GamblingGame(int playerNum){
		prizeNum = new int[Prize.PRIZE_COUNT];
		prizeCount = new int[Prize.PRIZE_COUNT];
		dice = new int[6];
		diceCount = new int[6];
		this.playerNum = playerNum;
		setPlayers();
		
		for (int i = 0; i < Prize.PRIZE_COUNT; ++i){
//			prizeNum[i] = 0;
			prizeCount[i] = 0;
		}
		
//		for (int tmp: prizeNum){
//			System.out.printf("%d ", tmp);
//		}
	}
	
//	private void resetGame(){
//		for (int i = 0; i < Prize.PRIZE_COUNT; ++i){
//			prizeCount[i] = 0;
//		}
//	}
	
	public int[] rollDice(){
		Random random = new Random();
		
//		for (int i = 0; i < diceCount.length; ++i){
//			diceCount[i] = 0;
//		}
		
		for (int i = 0; i < dice.length; ++i){
			dice[i] = random.nextInt(dice.length) + 1;

		}
		
//		for (int tmp: dice)
//			System.out.printf("%d ", tmp);
//		System.out.println();
		return dice;
	}
	
	public int[] getDiceNum(){
		return dice;
	}
	
	public int judgePrize(int[] dice){
		for (int tmp: dice)
			System.out.printf("%d ", tmp);
		System.out.println();
		boolean duitang = true;
		int prize = -1;
		
		diceCount = new int[6];
		for (int i = 0; i < diceCount.length; ++i)
			diceCount[dice[i]-1]++;
		
		
		for (int i = 0; i < diceCount.length; ++i){
			if (duitang && diceCount[i] != 1)
				duitang = false;
		}
		if (duitang){
			prize = Prize.DUI_TANG;
			if (prize != -1){
				setPrize(prize);
			} 
			return prize;
		}
		
		
		for (int i = 0; i < diceCount.length; ++i){
			if (diceCount[i] == 4 && i != 3){
				prize = Prize.SI_JIN;
				//setPrize(prize);
				break;
			}
			
			if (diceCount[i] == 5){
				prize = Prize.WU_ZI_DENG_KE;
				//setPrize(prize);
				break;
			}
				
//			if (duitang && diceCount[i] != 1)
//				duitang = false;
		}

		
		if (prize == -1){
			
			if (diceCount[3] == 1)
				prize = Prize.YI_XIU;
			else if (diceCount[3] == 2)
				prize = Prize.ER_JU;
			else if (diceCount[3] == 3)
				prize = Prize.SAN_HONG;
			else if (diceCount[3] == 4 && diceCount[0] == 2)
				prize = Prize.CHA_JIN_HUA;
			else if (diceCount[3] == 4)
				prize = Prize.ZHUANG_YUAN;
			else if (diceCount[3] == 6)
				prize = Prize.LIU_BEI_HONG;
			
		}
		
		if (prize != -1){
			if (prize >= Prize.ZHUANG_YUAN && prize < Prize.LIU_BEI_HONG)
				setPrize(Prize.ZHUANG_YUAN);
			else
				setPrize(prize);
		}
		
		return prize;
	}
	
	public boolean isAwardEmpty(int prize){
		System.out.println(String.valueOf(prizeNum.length));
		return (prizeCount[prize] > prizeNum[prize]);
	}
	
	public void setPlayerNum(int n){
		playerNum = n;
		setPlayers();
	}
	
	public void setTotalPrize(int[] prize){
		//prizeNum = prize.clone();
		for (int i = 0, j = 0 ; i < prizeNum.length; ++i){
			if (i != Prize.WU_ZI_DENG_KE && i != Prize.CHA_JIN_HUA){
				prizeNum[i] = prize[j++];
			}
		}
	}

	private void setPlayers(){
		playerPrizeInfo = new int[playerNum][Prize.PRIZE_COUNT];
		playerName = new String[playerNum];
		for (int i = 0; i < playerNum; ++i){
			playerName[i] = "player" + String.valueOf(i+1);
		}
		currentPlayerNum = 0;
	}
	
	public int getPlayerCount(){
		if (playerName != null)
			return playerName.length;
		else return 0;
	}
	
	public int getCurrentPlayer(){
		return currentPlayerNum;
	}
	
	public String[] getPlayerNames(){
		return playerName;
	}
	
	public String getPlayerName(int pos){
		return playerName[pos];
	}
	
	public String getCurrentPlayerName(){
		if (playerName != null)
			return playerName[currentPlayerNum];
		else return null;
	}
	
	public String getPlayerInfo(int pos){
		StringBuffer str = new StringBuffer();
		str.append(String.format(" %-11s", playerName[pos]));
		for (int i = 0; i < Prize.PRIZE_COUNT; ++i){
			if (i == 0)
				str.append(String.format("%s", playerPrizeInfo[pos][i]));
			else if (i == 3)
				str.append(String.format("%6s", playerPrizeInfo[pos][i]));
			else if (i == 6)
				;
//				str.append(String.format("%9s", playerPrizeInfo[pos][i]));
			else if (i == 7)
				;
//				str.append(String.format("%12s", playerPrizeInfo[pos][i]));
			else if (i == 8)
				str.append(String.format("%8s", playerPrizeInfo[pos][i]));
//				str.append(String.format("%10s", playerPrizeInfo[pos][i]));
			else
				str.append(String.format("%7s", playerPrizeInfo[pos][i]));
		}
		return str.toString();
	}
	
	public String getCurrentPlayerInfo(){
		return getPlayerInfo(currentPlayerNum);
	}
	
	public void nextPlayer(){
		currentPlayerNum++;
		if (currentPlayerNum == playerNum)
			currentPlayerNum = 0;
	}
	
	private void setPrize(int prize){
		if (prize == Prize.LIU_BEI_HONG){
			prizeCount = prizeNum.clone();
			playerPrizeInfo[currentPlayerNum] = prizeNum.clone();
			return;
		}
		if (prizeCount[prize] <= prizeNum[prize])
			prizeCount[prize]++;
		if (prizeCount[prize] <= prizeNum[prize])
			playerPrizeInfo[currentPlayerNum][prize]++;
	}
	
	public boolean gameOver(){
		for (int i = 0; i < Prize.PRIZE_COUNT; ++i){
			if (prizeCount[i] < prizeNum[i])
				return false;
		}
		return true;
	}

}
