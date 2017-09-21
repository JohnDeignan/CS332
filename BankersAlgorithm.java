import java.util.Scanner;

public class BankersAlgoritm 
{
	public static void main(String[] args)
	{
		boolean cont = true;
		
			while(cont)//loop until valid input
			{
				Scanner read = new Scanner(System.in);
				System.out.println("1. Enter custom matrices and vectors."); //choice one to enter information
				System.out.println("2. Use pre-made test cases."); //choice two to use premade information
				int choice = read.nextInt();
		
				if(choice == 1) //if enter information
				{
					int i;//iterator
					int j;//iterator
					int resources = 0;//hold amount of resources
					int processes = 0;//hold amount of processes
					boolean con = true;//used to loop
					
					while(con)//loop until valid input
					{
						System.out.println("Enter valid number of resources: ");
						resources = read.nextInt();
						if(resources < 0)//if invalid input
						{
							error();//throw error
							continue;//skip clause to end loop
						}
						con = false;//to end loop
					}
					con = true;//prepare for next loop
					
					//process is repeated for all input procedures
					while(con)
					{
						System.out.println("Enter valid number of processes: ");
						processes = read.nextInt();
						if(processes < 0)
						{
							error();
							continue;
						}
						con = false;
					}
					con = true;
					
					int[][] claim = new int[processes][resources]; //create claim matrix to be filled
					while(con)
					{
						System.out.println("Enter valid claim values accordingly: ");
						for(i = 0; i < processes; i++)
							for(j = 0; j < resources; j++)
							{
								System.out.println("P" + (i + 1) + ", R" + (j + 1) + ": ");
								claim[i][j] = read.nextInt();
								if(claim[i][j] < 0)
								{
									error();
									j--;
									continue;
								}
							}
						con = false;
					}
					con = true;
					
					int[][] alloc = new int[processes][resources]; //create allocation matrix to be filled
					while(con)
					{
						System.out.println("Enter valid allocation values accordingly: ");
						for(i = 0; i < processes; i++)
							for(j = 0; j < resources; j++)
							{
								System.out.println("P" + (i + 1) + ", R" + (j + 1) + ": ");
								alloc[i][j] = read.nextInt();
								if(alloc[i][j] < 0)
								{
									error();
									j--;
									continue;
								}
							}
						con = false;
					}
					con = true;					
					
					int[] resource = new int[resources];//create resource vector to be filled
					while(con)
					{
						System.out.println("Enter valid resource values accordingly: ");
						for(i = 0; i < resources; i++)
						{
								System.out.println("R" + (i + 1) + ": ");
								resource[i] = read.nextInt();
								if(resource[i] < 0)
								{
									error();
									i--;
									continue;
								}
						}
						con = false;
					}
					
					int[] available = getAvailable(alloc, resource);//call function to get available vector
					int[][] ca = getCA(claim, alloc);//call function to get C-A matrix
					
					/**
					 * if a negative number is in the available vector,
					 * print error and terminate program
					 */
					int availLength = available.length;
					for (i = 0; i < availLength; i++)
					{
						if(available[i] < 0)
						{
							System.out.println("Available vector cannot have negative units.\nProgram terminating.");
							System.exit(0);
						}
					}
					
					/**
					 * if a negative number is in the C-A matrix,
					 * print error and terminate program
					 */
					int row = ca.length;
					int col = ca[0].length;
					for(i = 0; i < row; i++)
						for(j = 0; j < col; j++)
						{
							if(ca[i][j] < 0)
							{
								System.out.println("C-A matrix cannot have negative units.\nProgram terminating.");
								System.exit(0);
							}
						}
					
					printTest(claim, alloc, resource, available, ca);//if valid print matrices and vectors
					testProcesses(claim, alloc, ca, available);//run algorithm
					cont = false;//end loop
				}
				else if(choice == 2)//if using test cases
				{
					//create test cases and get remaining information
					int[][] firstClaim = { {3, 2, 2}, {6, 1, 3}, {3, 1, 4}, {4, 2, 2} };
					int[][] firstAllocation = { {1, 0, 0}, {5, 1, 1}, {2, 1, 1}, {0, 0, 2} };
					int[] firstResource = {9, 3, 6};
					int[] firstAvailable = getAvailable(firstAllocation, firstResource);
					int[][] firstCA = getCA(firstClaim, firstAllocation);
					int[][] secondClaim = { {6, 0, 1, 2}, {1, 7, 5, 0}, {2, 3, 5, 6}, {1, 6, 5, 3}, {1, 6, 5, 6} };
					int[][] secondAllocation = { {4, 0, 0, 1}, {1, 1, 0, 0}, {1, 2, 5, 4}, {0, 6, 3, 3}, {1, 4, 1, 2} };
					int[] secondResource = {9, 13, 10, 11};
					int[] secondAvailable = getAvailable(secondAllocation, secondResource);
					int[][] secondCA = getCA(secondClaim, secondAllocation);
					
					//print test cases and perform algorithm
					printTest(firstClaim, firstAllocation, firstResource, firstAvailable, firstCA);
					testProcesses(firstClaim, firstAllocation, firstCA, firstAvailable);
					printTest(secondClaim, secondAllocation, secondResource, secondAvailable, secondCA);
					testProcesses(secondClaim, secondAllocation, secondCA, secondAvailable);
					cont = false;//end loop
				}
				else//if no selection is made, start loop over
					System.out.println("A valid choice must be made:");
			}
	}
	
	//to print matrices and vectors properly
	public static void printTest(int[][] claim, int[][] alloc, int[] resource, int[] available, int[][] CA)
	{
		int i;
		int j;
		int col = claim[0].length;
		int row = claim.length;
		
		System.out.println("\nClaim Matrix:\n");
		for(i = 1; i < col + 1; i++)
		{
			System.out.print("\tR" + i);
		}
		System.out.print("\n");
		for(i = 1; i < row + 1; i++)
		{
			System.out.print("P" + i + ":\t");
			for(j = 0; j < col; j++)
			{
				System.out.print(claim[i-1][j] + "\t");
			}
			System.out.print("\n");
		}
		
		System.out.println("\nAllocation Matrix:\n");
		for(i = 1; i < col + 1; i++)
		{
			System.out.print("\tR" + i);
		}
		System.out.print("\n");
		for(i = 1; i < row + 1; i++)
		{
			System.out.print("P" + i + ":\t");
			for(j = 0; j < col; j++)
			{
				System.out.print(alloc[i-1][j] + "\t");
			}
			System.out.print("\n");
		}
		
		System.out.println("\nC-A Matrix:\n");
		for(i = 1; i < col + 1; i++)
		{
			System.out.print("\tR" + i);
		}
		System.out.print("\n");
		for(i = 1; i < row + 1; i++)
		{
			System.out.print("P" + i + ":\t");
			for(j = 0; j < col; j++)
				System.out.print(CA[i-1][j] + "\t");
			System.out.print("\n");
		}
			
		System.out.println("\nResource Vector:");
		for(i = 1; i < col + 1; i++)
		{
			System.out.print("R" + i + "\t");
		}
		System.out.print("\n");
		for(i = 0; i < col; i++)
			System.out.print(resource[i] + "\t");
		
		System.out.println("\n");
		System.out.println("\nAvailable Vector:");
		for(i = 1; i < col + 1; i++)
		{
			System.out.print("R" + i + "\t");
		}
		System.out.print("\n");
		for(i = 0; i < col; i++)
			System.out.print(available[i] + "\t");
		
		System.out.print("\n\n");
	}
	
	//to calculate available vector
	public static int[] getAvailable(int[][] alloc, int[] resource)
	{
		int i;
		int j;
		int col = alloc[0].length;
		int row = alloc.length;
		
		int[] available = new int[col];//create available vector of length column (amount of resources)
		int[] temp = new int[col];//create temporary array of same length
		
		//loop through allocation matrix, adding every resource up and storing in proper array index
		for(i = 0; i < row; i++)
		{
			for(j = 0; j < col; j++)
				temp[j] += alloc [i][j];
		}
		
		//loop through vectors and set available to resource minus temporary array at proper index
		for(i = 0; i < col; i++)
			available[i] = resource[i] - temp[i];
		
		return available;//return available vector
	}
	
	
	//to calculate C-A matrix
	public static int[][] getCA(int[][]claim, int[][]alloc)
	{
		int i;
		int j;
		int col = alloc[0].length;
		int row = alloc.length;
		
		int[][] CA = new int[row][col];//create C-A matrix
		
		//loop through all matrices
		for(i = 0; i < row; i++)
		{
			for(j = 0; j < col; j++)
			{
				CA[i][j] = claim[i][j] - alloc[i][j];//set C-A to claim minus allocation at proper indexes
			}
		}
		return CA;//return C-A vector
	}
	
	
	//to test processes for block and safe state
	public static void testProcesses(int[][] claim, int[][] alloc, int[][]CA, int[] available)
	{
		int i;
		int j;
		int x;
		int col = alloc[0].length;
		int row = alloc.length;
		int total = row;
		int lockCount = 0;
		int compCount = 0;
		int seq = 0;
		int[] sequence = new int[row];
		String safeSeq = "";
		boolean cont = true;
		boolean[] blocked = new boolean[row];//to hold blocked processes
		boolean[] safe = new boolean[row];//hold locked and unlocked processes
		boolean[] complete = new boolean[row];//hold processes that have been completed
		
		//set boolean values
		for(i = 0; i < row; i++)
		{
			safe[i] = true;
			complete[i] = false;
			blocked[i] = false;
		}
		
		//begin loop 
		while(cont)
		{
			for(i = 0; i < row; i++)//cycle rows
			{
				if(safe[i] == true && complete[i] == false)//if process is unlocked and not already completed
				{
					for(j = 0; j < col; j++)//cycle columns of process
					{
						if(available[j] + alloc[i][j] >= claim[i][j])//if available index and allocation matrix index are greater than or equal to claim index
						{
							/**
							 * if the process is at the end of a row and all
							 * clauses are valid, the process is complete and 
							 * the values in that process are added to the 
							 * available vector
							 */
							if(j == col - 1)
							{
								System.out.println("Process " + (i + 1) + " complete.\nState is safe, proceeding...");//print process that has been completed
								for(x = 0; x < col; x++)
									available[x] += alloc[i][x];
								complete[i] = true;//process i has been completed
								for(x = 0; x < safe.length; x++) 
									safe[x] = true;//unlock all processes to be tested again
								compCount++;//add 1 to compCount
								sequence[seq] = i+1;
								seq++;
							}
							else
								continue;//if resource is validated and you are not at the last resource, continue loop to test other resources
						}
						/**
						 * if the available index and allocation matrix 
						 * index are less than the claim index the process
						 * will not be completed and the process is locked
						 */
						else if(available[j] + alloc[i][j] < claim[i][j])
						{
							System.out.println("Process " + (i + 1) + " could not be completed.\nState is unsafe, proceeding...");
							safe[i] = false; //lock process
							j = col;
						}
						else
							continue;
					}		
				}
				/**
				 * if the process is still locked and it has not 
				 * been completed the process is blocked
				 * 
				 *  the only way for a process to meet this case is
				 *  for no other processes are completed before the time
				 *  it is looped back to 
				 */
				else if(safe[i] == false && complete[i] == false)
				{
					cont = false;
					blocked[i] = true;
					lockCount++;
					total--;
				}
				//if all processes have been completed
				else if(compCount == total)
					cont = false;
				//if all processes are blocked
				else if(lockCount == row)
					cont = false;
			}
		}
		
		//print blocked processes
		for(i = 0; i < row; i++)
			if(blocked[i] == true)
				System.out.println("Process " + (i+1) + " is blocked.");
		
		/**
		 * if the lockCount does not equal zero, all 
		 * or some of the processes are blocked
		 * 
		 * these processes are printed
		 * 
		 * if lockCount does equal zero, all processes
		 * were completed and a statement showing that 
		 * is printed
		 */
		if(lockCount != 0)
		{
			if(lockCount == row)
				System.out.println("Processes are in deadlock.\nAll processes are locked.");
			else
				System.out.println("Processes are in deadlock;\n" + lockCount + " process(es) are blocked.");
		}
		else if(lockCount == 0)
		{
			System.out.println("Processes have been executed.");
			for(i = 0; i < row; i++)
				safeSeq += sequence[i] + ", ";
			System.out.println("Safe sequence: " + safeSeq);
		}
		
		System.out.println("\n");
	}
	
	//to print error when negative number is entered
	public static void error()
	{
		System.out.println("Number not valid, please enter non-negative value.");
	}
}
