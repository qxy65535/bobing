package bobing;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GameSettingDialog extends JDialog implements ActionListener{
	
	private GamblingFrame parent;
	private FlowLayout gameSettingLayout;
	private JButton btnEnsure;
	
	private JLabel lbPlayerNum = new JLabel("���������");
	private JTextField tfPlayerNum = new JTextField(10);
	
	private JLabel[] lbPrizeNum = new JLabel[Prize.SHOW_PRIZE_COUNT];
	private JTextField[] tfPrizeNum = new JTextField[Prize.SHOW_PRIZE_COUNT];
	private int[] prizeNum = new int[Prize.SHOW_PRIZE_COUNT];
	
	private String[] prizeName = {"һ�㣺", "���٣�", "���죺", "�Ľ���", "���ã�", "״Ԫ��", /*"���ӵǿƣ�", "��𻨣�", */"�����죺"};
	
	private final int CODE_NO_INPUT = 101;
	private final int CODE_NOT_NUMBER = 102;
	private final int CODE_IS_ZERO = 103;
	
	public GameSettingDialog(GamblingFrame parent, boolean modal, String title){
		super(parent, modal);
		this.parent = parent;
		
		gameSettingLayout = new FlowLayout(FlowLayout.CENTER, 20, 20);
		setTitle(title);
		setSize(350, 470);
		setResizable(false);
		setLayout(gameSettingLayout);
		
		initViews();
	}
	
	private void initViews(){
		Box whole = Box.createVerticalBox();
		Box boxPlayerNum = Box.createHorizontalBox();
		Box boxPrize;
		
		tfPlayerNum.setFont(new Font("����", Font.PLAIN, 20));
		boxPlayerNum.add(lbPlayerNum);
		boxPlayerNum.add(tfPlayerNum);
		whole.add(boxPlayerNum);
		whole.add(Box.createVerticalStrut(15));
		
		for (int i = 0; i < Prize.SHOW_PRIZE_COUNT; ++i){
			boxPrize = Box.createHorizontalBox();
			
			lbPrizeNum[i] = new JLabel(prizeName[i]);
			tfPrizeNum[i] = new JTextField(10);
			tfPrizeNum[i].setFont(new Font("����", Font.PLAIN, 20));
			boxPrize.add(lbPrizeNum[i]);
			boxPrize.add(tfPrizeNum[i]);
			whole.add(boxPrize);
			whole.add(Box.createVerticalStrut(15));
		}
		
		whole.add(Box.createVerticalStrut(10));
		btnEnsure = new JButton("ȷ��");
		btnEnsure.addActionListener(this);
		whole.add(btnEnsure);
		
		//whole.setBounds(20, 20, 20, 20);
		add(whole);
	}
	
	public void actionPerformed(ActionEvent event){
		if (event.getSource() == btnEnsure){
			System.out.println("click button");
			
			switch (checkInput()){
			case CODE_NO_INPUT:
				JOptionPane.showMessageDialog(this, "���ڿ��");
				return;
			case CODE_NOT_NUMBER:
				JOptionPane.showMessageDialog(this, "ֻ����д���֣�");
				return;
			case CODE_IS_ZERO:
				JOptionPane.showMessageDialog(this, "���������������0��");
				return;
			}
			
			for (int i = 0; i < tfPrizeNum.length; ++i)
				prizeNum[i] = Integer.valueOf(tfPrizeNum[i].getText());
			
			parent.setGameSettings(Integer.valueOf(tfPlayerNum.getText()), prizeNum);
			System.out.println(tfPlayerNum.getText());
			this.dispose();
		}
	}
	
	private int checkInput(){
		
		if (tfPlayerNum.getText().equals("")){
			return 101;
		}
		else if (!isNumber(tfPlayerNum.getText())){
			return 102;
		}
		else if (Integer.valueOf(tfPlayerNum.getText()) == 0){
			return 103;
		}
		
		for (JTextField tmp: tfPrizeNum){
			if (tmp.getText().equals("")){
				return CODE_NO_INPUT;
			}
			else if (!isNumber(tmp.getText())){
				return CODE_NOT_NUMBER;
			}
//			else if (Integer.valueOf(tmp.getText()) == 0){
//				return CODE_IS_ZERO;
//			}
		}
		
		return 0;
	}
	
	private boolean isNumber(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	 }
	
	
    protected void processWindowEvent(WindowEvent event) {  
        if (event.getID() == WindowEvent.WINDOW_CLOSING){
        	System.out.println("click close");
			System.exit(0);
        	return; //ֱ�ӷ��أ���ֹĬ�϶�������ֹ���ڹر�  
        }
        super.processWindowEvent(event); //������ִ�д����¼���Ĭ�϶���(�磺����)  
    }  
	
}
