package check_deposit;

import java.util.Date;
import java.util.List;

/**
 * Data class is the backend Data representation for each check deposit transaction.
 * It contains necessary user input information related with this transaction.
 * It will be used to display on the {@code GUI} class.
 */
public class Data {
	
	public Data(Date date, String description, double amount) {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
	}
	
	// Get one-line text to display on displayArea, except for the Balance column.
	public String getLine() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return "";
	}
	
	// Get the amount in this transaction data.
	public double getAmount() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return 0.0;
	}
	
	// Get the fee in this transaction data.
	public double getFee() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return 0.0;
	}
	
	// Whether this transaction is deposit or withdraw.
	public boolean isDeposit() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return false;
	}
	
	// Get the date for this transaction data.
	public Date getDate() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return null;
	}

	// A static method to sort the input data array according to transaction date
	// We will only deal data from index 0 to index size (exclusive).
	public static void sortData(Data[] dataArray, int size) {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
	}
}
