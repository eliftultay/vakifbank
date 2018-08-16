import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringJoiner;
import java.sql.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;

public class frame {
	
	private JFrame frame;
	private JTextField txtLoanAmount;
	private JComboBox comboBoxExpiration, comboBoxPayment;
	private JButton btnViewDetails, btnClear;
	private JLabel lblrate, lblBegin, lblEnd, lblTotalLoan, lblTotalInterest, lblMonthlyPayment, lblTotalPayment;
	private JDateChooser dateChooser;
	
	private String [] expirationArray = {"3", "6", "12", "18", "24", "32", "36", "48", "60", "72", "120"};
	private String [] payPerArray = {"1", "3"};
	private float interest, principal, calculatedInterest,monthlyPayment, totalPayment; 
	private int payPer, eTime;
	private String exTime, beginningDate, endDate;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame window = new frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	/**
	 * Create the application.
	 */
	
	public frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 923, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtLoanAmount = new JTextField();
		txtLoanAmount.setText("Loan Amount");
		txtLoanAmount.setBounds(249, 44, 130, 26);
		frame.getContentPane().add(txtLoanAmount);
		txtLoanAmount.setColumns(10);
		
		JLabel lblLoan = new JLabel("Loan Amount: ");
		lblLoan.setBounds(52, 40, 138, 34);
		frame.getContentPane().add(lblLoan);
		
		JLabel lblExpirationTime = new JLabel("Expiration Time:");
		lblExpirationTime.setBounds(52, 95, 138, 34);
		frame.getContentPane().add(lblExpirationTime);
		
		JLabel lblBankRate = new JLabel("Bank Rate: ");
		lblBankRate.setBounds(52, 156, 138, 34);
		frame.getContentPane().add(lblBankRate);
		
		JLabel lblPaymentPeriod = new JLabel("Payment Period: ");
		lblPaymentPeriod.setBounds(52, 222, 138, 34);
		frame.getContentPane().add(lblPaymentPeriod);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(52, 271, 138, 34);
		frame.getContentPane().add(lblDate);
		
		JLabel lblDefaultTaxes = new JLabel("Default Taxes");
		lblDefaultTaxes.setBounds(52, 332, 138, 16);
		frame.getContentPane().add(lblDefaultTaxes);
		
		JLabel lblBsmv = new JLabel("BSMV: 5 %");
		lblBsmv.setBounds(52, 360, 102, 16);
		frame.getContentPane().add(lblBsmv);
		
		JLabel lblKkdv = new JLabel("KKDV:15 %");
		lblKkdv.setBounds(52, 396, 102, 16);
		frame.getContentPane().add(lblKkdv);
		
		lblrate = new JLabel("Rate");
		lblrate.setBounds(249, 165, 130, 16);
		frame.getContentPane().add(lblrate);
		
		comboBoxExpiration = new JComboBox(expirationArray);
		comboBoxExpiration.setBounds(249, 100, 130, 27);
		frame.getContentPane().add(comboBoxExpiration);
		
		comboBoxPayment = new JComboBox(payPerArray);
		comboBoxPayment.setBounds(249, 222, 130, 27);
		frame.getContentPane().add(comboBoxPayment);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(249, 279, 130, 26);
		frame.getContentPane().add(dateChooser);
		
		btnViewDetails = new JButton("View Details");
		btnViewDetails.setBounds(249, 332, 138, 77);
		frame.getContentPane().add(btnViewDetails);
		
		JLabel lblBeginningDate = new JLabel("Beginning Date");
		lblBeginningDate.setBounds(522, 280, 108, 16);
		frame.getContentPane().add(lblBeginningDate);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(718, 280, 102, 16);
		frame.getContentPane().add(lblEndDate);
		
		lblBegin = new JLabel("begin");
		lblBegin.setBounds(522, 312, 108, 16);
		frame.getContentPane().add(lblBegin);
		
		lblEnd = new JLabel("end");
		lblEnd.setBounds(718, 308, 102, 16);
		frame.getContentPane().add(lblEnd);
		
		btnClear = new JButton("Clear ");
		btnClear.setBounds(603, 355, 117, 29);
		frame.getContentPane().add(btnClear);
		
		lblTotalLoan = new JLabel("Total Loan");
		lblTotalLoan.setBounds(522, 49, 108, 16);
		frame.getContentPane().add(lblTotalLoan);
		
		lblTotalInterest = new JLabel("Total Interest");
		lblTotalInterest.setBounds(718, 49, 102, 16);
		frame.getContentPane().add(lblTotalInterest);
		
		lblMonthlyPayment = new JLabel("Monthly Payment");
		lblMonthlyPayment.setBounds(522, 145, 108, 16);
		frame.getContentPane().add(lblMonthlyPayment);
		
		lblTotalPayment = new JLabel("Total Payment");
		lblTotalPayment.setBounds(718, 145, 102, 16);
		frame.getContentPane().add(lblTotalPayment);
		btnViewDetails.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				check();
				calculateInterest(principal, interest, eTime);
				equalizedPurchaseMonthlyPayment(principal, interest, eTime);
				totalPayment(principal);
				lblTotalInterest.setText(Float.toString(calculatedInterest));
				lblMonthlyPayment.setText(Float.toString(monthlyPayment));
				lblTotalPayment.setText(Float.toString(totalPayment));
				lblTotalLoan.setText(txtLoanAmount.getText());
				getDate();
				
			}
		});
	}
	
	public void check() {
		principal = Float.parseFloat(txtLoanAmount.getText());
		exTime = comboBoxExpiration.getSelectedItem().toString();
		if (comboBoxExpiration.getSelectedIndex()==0) {
			interest = (float) 2.11;
		} else if (comboBoxExpiration.getSelectedIndex()==1) {
			interest = (float) 2.11;
		} else if (comboBoxExpiration.getSelectedIndex()==2) {
			interest = (float) 2.10;
		} else if (comboBoxExpiration.getSelectedIndex()==3) {
			interest = (float) 2.10;
		} else if (comboBoxExpiration.getSelectedIndex()==4) {
			interest = (float) 2.09;
		} else if (comboBoxExpiration.getSelectedIndex()==5) {
			interest = (float) 2.09;
		} else if (comboBoxExpiration.getSelectedIndex()==6) {
			interest = (float) 2.08;
		} else if (comboBoxExpiration.getSelectedIndex()==7) {
			interest = (float) 2.08;
		} else if (comboBoxExpiration.getSelectedIndex()==8) {
			interest = (float) 2.07;
		} else if (comboBoxExpiration.getSelectedIndex()==9) {
			interest = (float) 2.06;
		} else {
			interest = (float) 2.05;
		}
		eTime = Integer.parseInt(exTime);
		payPer = Integer.parseInt(comboBoxPayment.getSelectedItem().toString());
		lblrate.setText(Float.toString(interest));
	}
	
	public void getDate() {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dayF = new SimpleDateFormat("dd");
		DateFormat monthF = new SimpleDateFormat("MM");
		DateFormat yearF = new SimpleDateFormat("yyyy");
		
		String d, m, y;
		int day, year;
		
		lblBegin.setText(df.format(dateChooser.getDate()));
		d = dayF.format(dateChooser.getDate());
		m = monthF.format(dateChooser.getDate());
		y = yearF.format(dateChooser.getDate());
		day = Integer.parseInt(d);
		year = Integer.parseInt(y);
		
		//endDate(day, m, year, eTime);
		
	}
	
	public void calculateInterest(float principal, float interestRate, int expirationTime) {
		
		calculatedInterest = principal * interestRate * expirationTime/1200;
	
	}
	
	public void equalizedPurchaseMonthlyPayment(float principal, float interestRate, float expirationTime) {
		
		float totalPrinciple = principal + calculatedInterest;
		float totalInterest = (interestRate*20/100) +interestRate;
		float newInterest = totalInterest/100; 	
		float totalRate = 1+newInterest;
		monthlyPayment = (float) (totalPrinciple * (newInterest * Math.pow(totalRate, expirationTime)/(Math.pow(totalRate, expirationTime)-1)));
		
	}
	
	public void totalPayment(float principle) {
		totalPayment = principle + calculatedInterest;
	}

	
	/*public void endDate(int day, String month, int year, int exprationDay ) {
		
		String dayS, yearS;
		int sum = 0;
		dayS = Integer.toString(day);
		sum = year + exprationDay;
		yearS = Integer.toString(year);
		
		StringJoiner joiner = new StringJoiner("/");
		joiner.add(dayS);
		joiner.add(month);
		joiner.add(yearS);
		endDate = joiner.toString();
		
	}
	
	public String getEndDate() {
		return beginningDate;
	}*/
	
	/*
	
	public void beginningDate(int day, String month, int year) {
		
		String dayS, yearS;
		dayS = Integer.toString(day);
		yearS = Integer.toString(year);
		
		StringJoiner joiner = new StringJoiner("/");
		joiner.add(dayS);
		joiner.add(month);
		joiner.add(yearS);
		beginningDate = joiner.toString();
		
	}
	
	public String getBeginningDate() {
		return beginningDate;
	}
	
	
	*/

	/*@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==comboBoxExpiration) {
			JComboBox cbe = (JComboBox)e.getSource();
			exTime = (String)cbe.getSelectedItem();
			System.out.println(exTime + "Selected" );
		}
		
		if (e.getSource()==comboBoxPayment) {
			JComboBox cbp = (JComboBox)e.getSource();
			payPer = (Integer)cbp.getSelectedItem();
			System.out.println(payPer + "Selected" );
		}
		
	}*/
	
}
