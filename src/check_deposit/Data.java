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
	
	Date date;
	String description;
	double amount;
	
	public Data(Date date, String description, double amount) {
		this.date = date;
		this.description = description;
		this.amount = amount;
		// TODO(Yuan): TO BE IMPLEMENTED LATER
	}
	
	// Get one-line text to display on displayArea, except for the Balance column.
	public String getLine() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		StringBuilder str = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat amountFormat = new DecimalFormat("#,##0.00");
		
		str.append(sdf.format(date));
		str.append("    ");
		str.append(description);
		String amountString = amountFormat.format(amount);
		for (int i = 54 - description.length() - amountString.length(); i >= 0; i--)
		{
			str.append(" ");
		}
		str.append(amountString);
		return str.toString();
	}
	
	// Get the amount in this transaction data.
	public double getAmount() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return amount;
	}
	
	// Get the fee in this transaction data.
	public double getFee() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		if(amount > 0)
		{
			return -amount * 0.01;
		}
		else
		{
			return amount * 0.03;
		}
	}
	
	// Whether this transaction is deposit or withdraw.
	public boolean isDeposit() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return amount > 0;
	}
	
	// Get the date for this transaction data.
	public Date getDate() {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		return date;
	}

	// A static method to sort the input data array according to transaction date
	// We will only deal data from index 0 to index size (exclusive).
	public static void sortData(Data[] dataArray, int size) {
		// TODO(Yuan): TO BE IMPLEMENTED LATER
		for(int i = size; i > 0; i--)
		{
			for(int j = 0; j < i - 1; j++)
			{
				if(dataArray[j].getDate().after(dataArray[j + 1].getDate()))
				{
					Data tmp = dataArray[j];
					dataArray[j] = dataArray[j + 1];
					dataArray[j + 1] = tmp;
				}
						
			}
		}
	}
}
