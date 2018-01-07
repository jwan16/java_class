import java.util.*;
import java.io.*;
import java.text.*;


public class StudentReport {
	public static void main(String[] args) throws Exception{
		File file = new File("asg1_sample_input.txt");				 	// define File object
		Scanner scanner = new Scanner(file); 							// define a Scanner object to read the file
		String line;													// define a String object for each line of the file
		 				
		// Count number of lines of the file
		int lineCount = 0;												// define int object to store number of lines
		while(scanner.hasNextLine()) {
			lineCount++;
			scanner.nextLine();
		}
		scanner.close();
		scanner = new Scanner(file);									// reset Scanner object
		
		// define a 2D string array to store report data   
		String[][] stringArray2D = new String[lineCount][9];			
		
		int row = 0;
		// Read each line of the file and store them into array
		while(scanner.hasNextLine()) {
			String[] arr1 = new String[10];
			line = scanner.nextLine();
			
			// If it is the first line, arr1 becomes the predefined title
			if (row == 0) {
				arr1 = new String[] {"ID", "Name", "Asg 1", "Asg 2", "Quiz 1", "Quiz 2", "Project", "Lab", "Overall"};
			} else {
				arr1 = line.split(", ");
			}
			
			for (int i=0; i<arr1.length; i++) {
				stringArray2D[row][i] = arr1[i];
			}
			row++;
		}
		
		
		// Calculate Overall of each row
		DecimalFormat myFormatter = new DecimalFormat("#0.00");		// Create Decimal Formatter
		float sum;			
		float rating[] = {10, 10, 15, 15, 35, 15};
	    for (int i=1; i<14; i++) {
	      sum = 0;   // reset the sum for the next row
	      for (int j=2; j<8; j++) {
	        sum += Float.parseFloat(stringArray2D[i][j]) * rating[j-2] / 100;
	      }
	      String sumStr = myFormatter.format(sum);				// turn float object sum to string object sumStr
	      stringArray2D[i][8] = sumStr;
	    }
		
	    // Calculate Average of each column
	    String colAverage[] = new String[9];
	    colAverage[0] = "";
	    colAverage[1] = "Average: ";
	    for (int i=2; i<9; i++) {
	    	sum = 0;											//reset the sum for the next column
		    for (int j=1; j<lineCount; j++) {
		    	sum += Float.parseFloat(stringArray2D[j][i]);
		    }
		    float avg = sum / (lineCount - 1);
		    String avgStr = myFormatter.format(avg);
		    colAverage[i] = avgStr;
	    }
	    
		// Printing the result
		for (int i=0; i<stringArray2D.length; i++) {
			System.out.printf("%-10s%-22s%-7s%-7s%-7s%-7s%-9s%-7s%-7s \n", 
					stringArray2D[i][0], 
					stringArray2D[i][1], 
					stringArray2D[i][2], 
					stringArray2D[i][3], 
					stringArray2D[i][4], 
					stringArray2D[i][5], 
					stringArray2D[i][6], 
					stringArray2D[i][7], 
					stringArray2D[i][8]);
		}
		
		// print the average row
		System.out.printf("%-10s%22s%-7s%-7s%-7s%-7s%-9s%-7s%-7s \n", 
				colAverage[0], 
				colAverage[1], 
				colAverage[2], 
				colAverage[3], 
				colAverage[4], 
				colAverage[5], 
				colAverage[6], 
				colAverage[7], 
				colAverage[8]);
		scanner.close();
	}
}
