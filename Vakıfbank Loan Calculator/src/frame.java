import java.awt.EventQueue;

import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.StringJoiner;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JComboBox;
import com.toedter.calendar.*;
import javax.swing.JButton;
import java.awt.Label;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;

public class frame {
	
	private JFrame frame;
	private JTextField txtLoanAmount;
	private JComboBox comboBoxExpiration, comboBoxPayment;
	private JButton btnViewDetails;
	private JLabel lblrate, lblTotalLoan, lblTotalInterest, lblTotalPayment;
	private JDateChooser dateChooser;
	
	private String [] expirationArray = {"3", "6", "12", "18", "24", "32", "36", "48", "60", "72", "120"};
	private String [] payPerArray = {"1", "3"};
	private float interest, principal, calculatedInterest,monthlyPayment, totalPayment; 
	private int payPer, eTime;
	private String exTime, beginningDate, endDate;
	private String d, m, y;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame window = new frame();
					//window.frame.setVisible(true);
					window.frame.show();
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
		frame.setBounds(100, 100, 850, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtLoanAmount = new JTextField();
		txtLoanAmount.setToolTipText("decimal number value");
		txtLoanAmount.setBounds(220, 40, 130, 35);
		frame.getContentPane().add(txtLoanAmount);
		txtLoanAmount.setColumns(10);
		
		JLabel lblLoan = new JLabel("Loan Amount: ");
		lblLoan.setBounds(50, 40, 130, 35);
		frame.getContentPane().add(lblLoan);
		
		JLabel lblExpirationTime = new JLabel("Expiration Time:");
		lblExpirationTime.setBounds(50, 95, 130, 35);
		frame.getContentPane().add(lblExpirationTime);
		
		JLabel lblBankRate = new JLabel("Bank Rate: ");
		lblBankRate.setBounds(50, 150, 130, 35);
		frame.getContentPane().add(lblBankRate);
		
		JLabel lblPaymentPeriod = new JLabel("Payment Period: ");
		lblPaymentPeriod.setBounds(50, 205, 130, 35);
		frame.getContentPane().add(lblPaymentPeriod);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(50, 260, 130, 35);
		frame.getContentPane().add(lblDate);
		
		JLabel lblDefaultTaxes = new JLabel("Default Taxes");
		lblDefaultTaxes.setBounds(50, 310, 138, 16);
		frame.getContentPane().add(lblDefaultTaxes);
		
		JLabel lblBsmv = new JLabel("BSMV: 5 %");
		lblBsmv.setBounds(50, 340, 100, 20);
		frame.getContentPane().add(lblBsmv);
		
		JLabel lblKkdv = new JLabel("KKDV:15 %");
		lblKkdv.setBounds(50, 370, 100, 20);
		frame.getContentPane().add(lblKkdv);
		
		lblrate = new JLabel("Rate");
		lblrate.setBounds(220, 150, 130, 35);
		frame.getContentPane().add(lblrate);
		
		comboBoxExpiration = new JComboBox(expirationArray);
		comboBoxExpiration.setToolTipText("months");
		comboBoxExpiration.setBounds(220, 95, 130, 35);
		frame.getContentPane().add(comboBoxExpiration);
		
		comboBoxPayment = new JComboBox(payPerArray);
		comboBoxPayment.setToolTipText("months");
		comboBoxPayment.setBounds(220, 205, 130, 35);
		frame.getContentPane().add(comboBoxPayment);
		
		dateChooser = new JDateChooser();
		dateChooser.setToolTipText("pick date");
		dateChooser.setBounds(220, 260, 130, 35);
		frame.getContentPane().add(dateChooser);
		
		btnViewDetails = new JButton("View Details");
		btnViewDetails.setToolTipText("click to see results");
		btnViewDetails.setBounds(220, 310, 130, 80);
		frame.getContentPane().add(btnViewDetails);
		
		lblTotalLoan = new JLabel("");
		lblTotalLoan.setBounds(530, 75, 100, 20);
		frame.getContentPane().add(lblTotalLoan);
		
		lblTotalInterest = new JLabel("");
		lblTotalInterest.setBounds(700, 75, 102, 20);
		frame.getContentPane().add(lblTotalInterest);
		
		lblTotalPayment = new JLabel("");
		lblTotalPayment.setBounds(615, 145, 100, 16);
		frame.getContentPane().add(lblTotalPayment);
		
		Label label = new Label("New label");
		label.setBackground(Color.BLACK);
		label.setBounds(440, 40, 1, 369);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Total Loan");
		label_1.setBounds(530, 40, 100, 35);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Total Interest");
		label_2.setBounds(700, 40, 100, 35);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Total Payment");
		label_3.setBounds(615, 110, 100, 35);
		frame.getContentPane().add(label_3);
		
		JTable table = new JTable();
		table.setBounds(530, 205, 270, 185);
		table.setRowHeight(30);
		table.setBackground(Color.white);
		table.setForeground(Color.black);
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Payment Date");
		model.addColumn("Payment Amount");
		table.setModel(model);
		//frame.getContentPane().add(table);
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(530, 206, 270, 185);
		frame.getContentPane().add(pane);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		btnViewDetails.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int len = model.getRowCount();
				for(int i = 0; i < len; i++) {
					model.removeRow(0);
				}
				check();
				calculateInterest(principal, interest, eTime);
				equalizedPurchaseMonthlyPayment(principal, interest, eTime);
				totalPayment(principal);
				lblTotalInterest.setText(Float.toString(calculatedInterest));
				lblTotalPayment.setText(Float.toString(totalPayment));
				lblTotalLoan.setText(txtLoanAmount.getText());
				getDate();
				int numberOfRows = Integer.valueOf(exTime) / payPer;
				int month;
				int year;
				month = Integer.valueOf(m);
				year = Integer.valueOf(y);
				String monthString = "";
				String yearString;
				
				for(int i = 0; i < numberOfRows; i++) {
					if(month != 12) {
						month++;
					} else {
						month = 1;
						year++;
					}
					
					if (month < 10) {
						monthString = "0" + month;
					} else {
						monthString = String.valueOf(month);
					}
					yearString = String.valueOf(year);
					
					String paymentDate = d + "." + monthString + "." + yearString;
					
					model.addRow(new String[]{paymentDate, String.valueOf(monthlyPayment)});
				}
				
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
		
		d = dayF.format(dateChooser.getDate());
		m = monthF.format(dateChooser.getDate());
		y = yearF.format(dateChooser.getDate());
		
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
}
