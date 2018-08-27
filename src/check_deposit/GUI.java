package check_deposit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

/**
 * GUI class is the UI application for the check deposit system, using swing JFrame.
 * All data showing on the UI will come from {@code Data} class.
 */
public class GUI extends JFrame {

	// Default Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Size of the application window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	// Size of the initial data array
	private static final int INIT_DATA_ARRAY_SIZE = 2;
	
	// Variable declaration on swing UI components
	private JTextField dateField, descriptionField, amountField, warning;
	private JTextArea displayArea;
	private JPanel panel, panelText, panelInput, panelButton;
	private JButton writeCheckButton, depositButton;
	private Font fixedFont;
	
	// Helper String used to store format display header line
	private String header;
	// Helper object to transform the Date display format
	private SimpleDateFormat sdf;
	// Helper object to transform the money number format
	private DecimalFormat amountFormatter;
	
	// Array storage of the transaction Data
	private Data[] dataArray;
	// Current data index in the data array
	private int dataIndex = 0;
	
	public GUI() {
		// Set the JFrame application window size
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// Get current display screen dimension (width and height)
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Display the JFrame application window in the middle of the screen.
		// Otherwise, by default it will display on the very top-left corner.
		this.setLocation((screenSize.width - WINDOW_WIDTH) / 2, 
						 (screenSize.height - WINDOW_HEIGHT) / 2);
		
		// Set the application title on the header bar
		this.setTitle("Check Deposit Application");
		// If click the window close button, application will be terminated
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// SimpleDateFormat object is used to convert a java.util.Date object
		// to a certain format of String.
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);
		
		// DecimalFormat object is used to convert any number into a certain format of String.
		amountFormatter = new DecimalFormat("#,##0.00");
		
		// Create a Font object used to display text on the UI.
		fixedFont = new Font(Font.MONOSPACED, Font.BOLD, 12);
		// This is the main panel where all the UI object will display on
		panel = new JPanel();
		
		// Initialize some UI objects and component to show the initial UI.
		setTextInput();
		addButton();
		addWarning();
		initDisplayArea();
		
		// Set the main panel to be the content pane of the whole application
		this.setContentPane(panel);
		// Set the application window to be visible
		this.setVisible(true);
		
		dataArray = new Data[INIT_DATA_ARRAY_SIZE];
	}
	
	// Initialize the display area of the list of historical transactions
	private void initDisplayArea() {
		// Generate the header line of the display area
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.setLength(0);
		strBuilder.append("    Date");
		strBuilder.append("         Check #");
		strBuilder.append("    Description");
		strBuilder.append("                             Amount");
		strBuilder.append("          Fee");
		strBuilder.append("        Balance\n  ");
		for (int i = 0; i < 102; i++) {
			strBuilder.append('-');
		}
		strBuilder.append('\n');
		header = strBuilder.toString();
		
		// Create the a JTextArea to be the displayArea
		displayArea = new JTextArea(20, 106);
		displayArea.setFont(fixedFont);
		// displayArea should not be user-editable
		displayArea.setEditable(false);
		// Add the header line to the display area as initial display
		displayArea.append(header);
		
		// Add a scroll bar to the display area, in case too many to display
		JScrollPane scroller = new JScrollPane(displayArea);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scroller);
	}
	
	// Initialize the warning text field on the UI.
	private void addWarning() {
		warning = new JTextField(80);
		warning.setEditable(false);
		warning.setBackground(Color.white);
		warning.setFont(fixedFont);
		warning.setForeground(Color.red);
		panel.add(warning);
	}
	
	// Initialize the user input boxes and corresponding text.
	private void setTextInput() {
		panelText = new JPanel();
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.setLength(0);
		strBuilder.append("       Date");
		for (int i = 0; i < 40; i++) {
			strBuilder.append(' ');
		}
		
		strBuilder.append("Description");
		for(int i = 0; i < 80; i++) {
			strBuilder.append(' ');
		}
		
		strBuilder.append("Amount");
		
		// strBuilder now contains the three labels for three input fields.
		JLabel textLabel = new JLabel(strBuilder.toString());
		panelText.add(textLabel);
		panel.add(panelText);
		
		// Create the date input field, and default set it to be today's date.
		panelInput = new JPanel();
		Date today = new Date();
		dateField = new JTextField(20);
		dateField.setFont(fixedFont);
		dateField.setText(sdf.format(today));
		panelInput.add(dateField);
		
		// Create the description input field.
		descriptionField = new JTextField(60);
		descriptionField.setFont(fixedFont);
		panelInput.add(descriptionField);
		
		// Add the money sign right before the money amount input field.
		JLabel moneySign = new JLabel("  $");
		panelInput.add(moneySign);
		
		// Create the amount display field.
		amountField = new JTextField(5);
		amountField.setFont(fixedFont);
		panelInput.add(amountField);
		
		panel.add(panelInput);
		
	}
	
	// Initialize all the buttons on the UI.
	private void addButton() {
		panelButton = new JPanel();
		// Create two buttons for write check and make deposit
		writeCheckButton = new JButton("Write Check");
		depositButton = new JButton("Make Deposit");
		// This spaceLabel is used for display some spaces between the two buttons
		JLabel spaceLabel = new JLabel("                 ");
		
		// Add action listener to each button
		writeCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				processTransaction(false);
			}
		});
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				processTransaction(true);
			}
		});
		
		// Add the buttons and internal spaces to the panel.
		panelButton.add(writeCheckButton);
		panelButton.add(spaceLabel);
		panelButton.add(depositButton);
		panel.add(panelButton);
	}
	
	// Method to actually process a transaction.
	// If isDeposit is true, the input amount will be added to the balance.
	private void processTransaction(boolean isDeposit) {
		// reset the warning text field.
		warning.setText("");
		
		Date date;
		double amount;
		try {
			amount = Double.parseDouble(amountField.getText());
		} catch (NumberFormatException e) {
			warning.setText("Invalid value for Amount. Use correct numbers, like 9.99.");
			return;
		}
		
		if (amount < 0.01) {
			warning.setText("Amount has to be larger than $0.01");
			return;
		} else if (amount > 10000000) {
			warning.setText("Amount is too big");
			return;
		}
		
		String description = descriptionField.getText().trim();
		
		try {
			date = sdf.parse(dateField.getText());
		} catch (ParseException e) {
			warning.setText("Invalid vale for Date. Use mm/dd/yyyy");
			return;
		}

		// If code reaches here, all the input is validated, we can reset the input field's text.
		// Then convert the amount to be negative if it's not deposit.
		// Add a new Data object into the dataList
		// Print the new transaction details on the displayArea.
		resetInput();
		amount = isDeposit ? amount : -amount;
		dataArray[dataIndex] = new Data(date, description, amount);
		dataIndex++;
		Data.sortData(dataArray, dataIndex);
		// Resize the data array in case it's already full.
		resizeDataArray();
		printDetails();
	}
	
	// Resize the data array in case it's already full.
	// Here we use concept of dynamic array.
	// Every time it's full, we create new array with double size, and copy the data to new array.
	// Please note that this will still guarantee O(1) insertion time.
	private void resizeDataArray() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
	}
	
	// Reset the user input fields. dateField does not need to be reset.
	private void resetInput() {
		amountField.setText("");
		descriptionField.setText("");
	}
	
	// Print the historical transaction details on displayArea.
	private void printDetails() {
		double balance = 0.0;
		String balanceString;
		
		// Reset the content and add the header to the area.
		displayArea.setText("");
		displayArea.append(header);
		
		// Each data means one historical transaction and will be displayed in one line.
		for (int index = 0; index < dataIndex; index++) {
			Data data = dataArray[index];
			// Add first few spaces to the displayArea on each line.
			displayArea.append("    ");
			// Get the one-line formatted text from data object to display.
			displayArea.append(data.getLine());
			
			// Compute the current new balance
			balance += data.getAmount() + data.getFee();
			
			// Format the balance to predefined string money format
			balanceString = amountFormatter.format(balance);
			// This for-loop is used to make balance strings in all lines align on right
			for (int i = 0; i < 15 - balanceString.length(); i++) {
				displayArea.append(" ");
			}
			displayArea.append(balanceString);
			displayArea.append("\n");
		}
	}

	// Entry point of the whole check deposit application
	public static void main(String[] args) {
		new GUI();
	}
}
