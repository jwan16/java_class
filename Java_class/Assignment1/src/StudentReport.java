package Assignment1;
import java.util.*;
import java.io.*;
import java.text.*;


public class StudentReport {
	public static void main(String[] args) throws Exception{
		File file = new File("asg1_sample_input.txt");		// define File object
		Scanner scanner = new Scanner(file); 				// define a Scanner object to read the file
		String line;											// define a String object for each line of the file
			
		
		// Count number of comma 
		int countComma = 0;
		line = scanner.nextLine();
	    for (int i=0; i<line.length(); i++) {
	      if (line.substring(i, i+1).equals(",")) {   // substring(from position, to position)
	        countComma++;
	      }
	    }
	    
	    // calculate the number of columns
	    int numOfAssignment = (countComma + 1 - 2) / 2; 	// calculate number of assignments
	    int numOfCol = numOfAssignment + 3;		// numOfCol = numOfassignments + Student name + Student ID + Overall
	    
		// Count number of lines of the file
		int numOfRow = 1;						// define int object to store number of lines
		while(scanner.hasNextLine()) {
			numOfRow++;
			scanner.nextLine();
		}
		
		// reset the scanner object
		scanner.close();
		scanner = new Scanner(file);									
		
		// define a 2D string array to store report's data   
		String[][] stringArray2D = new String[numOfRow][numOfCol];
		int[] rating = new int[numOfAssignment];
		int row = 0;
		
		// Read each line of the file and store them into array
		while(scanner.hasNextLine()) {
			String[] arr1 = new String[numOfCol];
			line = scanner.nextLine();
			
			// If it is the first line, arr1 becomes the predefined title
			if (row == 0) {
				String[] headerArray = line.split(", ");
				
				// complete the rating array
				for (int i=0; i<numOfAssignment; i++) {
					rating[i] = Integer.parseInt(headerArray[i * 2 + 3]);
				}
				// complete the header array
				arr1[0] = "ID"; 
				arr1[1] = "Name";
				for (int i=0; i<numOfAssignment; i++) {
					arr1[i+2] = headerArray[i * 2 + 2];
				}
				arr1[numOfCol-1] = "Overall";
			}	else {
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
		
	    for (int i=1; i<14; i++) {
	      sum = 0;   // reset the sum for the next row
	      for (int j=2; j<8; j++) {
	    	  	sum += Float.parseFloat(stringArray2D[i][j]) * rating[j-2] / 100;
	      }
	      String sumStr = myFormatter.format(sum);				// turn float object sum to string object sumStr
	      stringArray2D[i][8] = sumStr;
	    }
		
	    // Calculate Average of each column
	    String[] colAverage = new String[9];
	    colAverage[0] = "";
	    colAverage[1] = "Average: ";
	    for (int i=2; i<9; i++) {
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
