/*
 * ISOM 3320 Assignment 1
 * Wan Yi Chun (20266612)
 */

package Assignment1;
import java.util.*;
import java.io.*;
import java.text.*;


public class StudentReport {
	public static void main(String[] args) throws Exception{
		File file = new File("asg1_sample_input.txt");				// define File object
		Scanner scanner = new Scanner(file); 						// define a Scanner object to read the file
		String line;													// define a String object for each line of the file
		DecimalFormat myFormatter = new DecimalFormat("#0.00");		// define the Decimal Formatter
		
		// Count number of comma 
		int countComma = 0;
		line = scanner.nextLine();
	    for (int i=0; i<line.length(); i++) {
	      if (line.substring(i, i+1).equals(",")) {   				// substring(from position, to position)
	        countComma++;
	      }
	    }
	    // calculate the number of columns
	    int numOfAssignment = (countComma + 1 - 2) / 2; 				// calculate number of assignments
	    int numOfCol = numOfAssignment + 3;							// numOfCol = numOfassignments + Student name + Student ID + Overall
	    
		// Count number of lines of the file
		int numOfRow = 1;											// define int object to store number of lines
																	// initialize it as 1 because 1 row is counted in comma counting
		while(scanner.hasNextLine()) {
			numOfRow++;												// Add 1 to numOfRow
			scanner.nextLine();										// Go to next line
		}
		
		// reset the scanner object
		scanner.close();
		scanner = new Scanner(file);									
		
		// define a 2D string array to store report's data   
		String[][] stringArray2D = new String[numOfRow][numOfCol];
		int[] rating = new int[numOfAssignment];						// define an integer array to store rating ratio
		
		// Read each line of the file and store them into array
		int row = 0;													// initialize the row for the scanner
		while(scanner.hasNextLine()) {
			String[] arr1 = new String[numOfCol];					// define a string array to store each line of the document
			line = scanner.nextLine();								
			
			// special handling for the first line of the document (header)
			if (row == 0) {
				String[] headerArray = line.split(", ");
				
				// Assign the rating ratio into rating[] array
				for (int i=0; i<numOfAssignment; i++) {
					rating[i] = Integer.parseInt(headerArray[i * 2 + 3]);
				}
				
				// define the header array
				arr1[0] = "ID"; 										// assign the ID header
				arr1[1] = "Name";									// assign the Name header
				for (int i=0; i<numOfAssignment; i++) {				// assign each assignment name from the document
					arr1[i+2] = headerArray[i * 2 + 2];
				}
				arr1[numOfCol-1] = "Overall";						// assign the last element of header array as "Overall"
			}	
			else {
				arr1 = line.split(", "); 							// split line into array by ", "
			}
			
			for (int i=0; i<arr1.length; i++) {
				if (i > 1 && row > 0) {								// special handling for all numbers
					// convert the numbers into 2 decimal places
					float mark = Float.parseFloat(arr1[i]);			
					stringArray2D[row][i] = myFormatter.format(mark);
				} else {
					stringArray2D[row][i] = arr1[i];					// assign the information into 2D array
				}
			}
			row++;
		}

		
		// Calculate Overall of each row
		
		float sum;			
		
	    for (int i=1; i<numOfRow; i++) {
	      sum = 0;   // reset the sum for the next row
	      for (int j=2; j<numOfCol-1; j++) {
	    	  	
	    	  	sum += Float.parseFloat(stringArray2D[i][j]) * rating[j-2] / 100;
	      }
	      String sumStr = myFormatter.format(sum);				// turn float object sum to string object sumStr
	      stringArray2D[i][numOfCol-1] = sumStr;
	    }
		
	    // Calculate Average of each column
	    String[] colAverage = new String[numOfCol];
	    colAverage[0] = "";
	    colAverage[1] = "Average: ";
	    for (int i=2; i<numOfCol
	    		; i++) {
	    	sum = 0;											// reset the sum for the next column
		    for (int j=1; j<numOfRow; j++) {
		    	sum += Float.parseFloat(stringArray2D[j][i]);
		    }
		    float avg = sum / (numOfRow - 1);
		    String avgStr = myFormatter.format(avg);
		    colAverage[i] = avgStr;
	    }
	    
		// Print out the table
	    // Define the printing 
	    int[] textLength = new int[numOfCol];
	    textLength[0] = 10;
	    textLength[1] = 22;
	    for (int c=2; c<stringArray2D[0].length; c++) {
	    		int maxc = stringArray2D[0][c].length();

	        for (int r = 1; r < numOfRow; r++)
	        {
	            if (stringArray2D[r][c].length() > maxc)
	                maxc = stringArray2D[r][c].length();
	        }
	        textLength[c] = maxc + 2;
	    }
	    
		for (int i=0; i<stringArray2D.length; i++) {
			for (int j=0; j<stringArray2D[i].length; j++) {
				System.out.printf("%-" + textLength[j] + "s", stringArray2D[i][j]);
			}
			System.out.print("\n");
		}
		
		// print the average row
		for (int j=0; j<colAverage.length; j++) {
			if (j == 1)
				System.out.printf("%" + textLength[j] + "s", colAverage[j]);
			else
				System.out.printf("%-" + textLength[j] + "s", colAverage[j]);
		}
		
		scanner.close();
		
		
	}
}
