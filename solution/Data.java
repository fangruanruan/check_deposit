import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Data class is the backend Data representation for each check deposit transaction.
 * It contains necessary user input information related with this transaction.
 * It will be used to display on the {@code GUI} class.
 */
public class Data {
	
	private Date date;
	private String description;
	private double amount;
	
	public Data(Date date, String description, double amount) {
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
	
	// Get one-line text to display on displayArea, except for the Balance column.
	public String getLine() {
		StringBuilder strBuilder = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat amountFormat =  new DecimalFormat("#,##0.00");
		
		strBuilder.append(sdf.format(date));
		strBuilder.append("    ");
		strBuilder.append(description);
		String amountString = amountFormat.format(amount);
		for (int i = 54 - description.length() - amountString.length(); i >= 0; i--)
			strBuilder.append(' ');
		strBuilder.append(amountString);
		return strBuilder.toString();
	}
	
	// Get the amount in this transaction data.
	public double getAmount() {
		return amount;
	}
	
	// Get the fee in this transaction data.
	public double getFee() {
		if (amount > 0) {
			return -amount * 0.01;
		} else {
			return amount * 0.03;
		}
	}
	
	// Whether this transaction is deposit or withdraw.
	public boolean isDeposit() {
		return amount > 0;
	}
	
	// Get the date for this transaction data.
	public Date getDate() {
		return date;
	}

	// A static method to sort the input data array according to transaction date
	// We will only deal data from index 0 to index size (exclusive).
	public static void sortData(Data[] dataArray, int size) {
		// This uses standard bubble sort. O(n^2) time.
		for (int i = size - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if (dataArray[j].getDate().after(dataArray[j + 1].getDate())) {
					Data temp = dataArray[j];
					dataArray[j] = dataArray[j + 1];
					dataArray[j + 1] = temp;
				}
			}
		}
	}
}
