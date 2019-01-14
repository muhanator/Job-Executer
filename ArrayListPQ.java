public class ArrayListPQ{

	
	//Attributes:
		private int size;
		private int cycles;
		private int jobsExecuted;
		private Job[] myArray;
		private long averageWaitTime;
		private long totalWaitTime;
		private long priorityChange;
		
	
	public ArrayListPQ()
	{
	
		myArray = new Job[10];
		size=1;
		cycles=0;
		jobsExecuted=0;
		averageWaitTime=0;
		priorityChange=0;
		
	}
	
	public void add(Job j1) {
		
			// should perform upheap operation
			if (size==1) //if the array is empty, then we just put the element at the 1st index
				myArray[1]= j1 ;
			else
			{
				dynamicResizing();
				myArray[size]= j1;
				myArray[size].setEntryTime(cycles);
			}
			size++;
			cycles++;
			

	}
	
	
	
	
	public void stats()
	{
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println("ArrayList based Heap Priority Queue is now empty");
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println("Jobs Executed:" + (jobsExecuted-1));
		System.out.println("Total Cycles: " + cycles);
		System.out.println("Average Wait Time per job: " + averageWaitTime);
		System.out.println("Total number of priority changes: " + priorityChange);
	}
	
	public void executeCurrentProcess() {
		
		if(myArray[1].getCurrentJobLength() == 0)
		{
			myArray[1].setWaitTime(cycles - myArray[1].getEntryTime());
			jobsExecuted++;
			averageWaitTime = averageWaitTime + myArray[1].getWaitTime()/jobsExecuted;
			removeMin();
			cycles++;
			return;
		}
		
		// Operation to be performed during ONE cycle
		myArray[1].setCurrentJobLength(myArray[1].getCurrentJobLength()-1);
		// remove and re-add to the PQ behind the the other jobs that have the same priority		
		Job tempJob = myArray[1];
		removeMin();
		tempJob.setEntryTime(cycles);
		add(tempJob);
		arrayToHeap();
		
		System.out.println("In Process: " + myArray[1].getJobName());
		System.out.println("Job Length: " + myArray[1].getJobLength());
		System.out.println("Current remaining Length: " + myArray[1].getCurrentJobLength());
		System.out.println("Initial Priority: " + myArray[1].getJobPriority());
		System.out.println("Final Priority: " + myArray[1].getFinalPriority());
		System.out.println("Entry Time in PQ: " + myArray[1].getEntryTime() + "\n");		
		
		
		if(cycles%30 == 0)
		{
			int longestWait = 1;
			int indexOfJob = size-1;
			
			// search from the back to find the highest priorty 
			for(int i = 1; i<=size-1;i++)
			{
				if(myArray[i].getEntryTime() > longestWait && myArray[i].getCurrentJobLength() == myArray[i].getJobLength())
				{
					indexOfJob = i;
				}
				
				
			}
			
				//myArray[indexOfJob].setJobPriority(1);
				myArray[indexOfJob].setFinalPriority(1);
				priorityChange++;
				
			
		}
		
		
	}
	
	// heap sorts the array into a heap
	public void arrayToHeap(){
		
		// Build heap (rearrange array) 
        for (int i = size / 2 - 1 ; i >= 1; i--) {
             heapify(myArray, size, i);    
        }
		
  
	}
	
	private void heapify(Job Jarray[], int n, int i) {
		
		int highestPriority = i; // Initialize highestPriority as index 1 of the array  
		int leftChild = 2 * i; // leftchild is 2*index  
		int rightChild = 2 * i + 1; // rightchild = 2*index + 1

		// If leftChild has equal priority to parent node but was in the PQ more recently than Parent
		if (leftChild < n && Jarray[leftChild].getFinalPriority() == Jarray[highestPriority].getFinalPriority() && Jarray[leftChild].getEntryTime() < Jarray[highestPriority].getEntryTime())
		{
			highestPriority = leftChild;
		}
		
		// If rightChild has equal priority to parent node but was in the PQ more recently than Parent
		if (rightChild < n && Jarray[rightChild].getFinalPriority() == Jarray[highestPriority].getFinalPriority() && Jarray[rightChild].getEntryTime() < Jarray[highestPriority].getEntryTime())
		{
			highestPriority = rightChild;
		}
		//If left child is smaller or equal than root  
		if (leftChild < n && Jarray[leftChild].getFinalPriority() < Jarray[highestPriority].getFinalPriority())
			highestPriority = leftChild; 

		// If right child is smaller or equal than the root  
		if (rightChild < n && Jarray[rightChild].getFinalPriority() < Jarray[highestPriority].getFinalPriority()) 
			highestPriority = rightChild; 

		// If highestPriority is not root then we set root to the highest priority key 
		if (highestPriority != i) { 
			
			// Swap operation, swap job in root and 
			Job temp = Jarray[i]; 
			Jarray[i] = Jarray[highestPriority]; 
			Jarray[highestPriority] = temp; 

			// Recursively heapify the affected sub-tree 
			heapify(Jarray, n, highestPriority); 
		}
		
		// Otherwise the input jobs array is already in the form of a heap
		
		
	}
	
	
	public void removeMin()
	{
		//if the ArrayList is empty or the index is bigger than the size, return null
		
	    
        // Swap root and right most element of the heap 
        Job tempJob = myArray[1]; 
        myArray[1] = myArray[size-1]; 
        myArray[size-1] = tempJob; 
        size--;
        
        // Bubbledown to restore the heap property 
        heapify(myArray, size, 1); // It should be size -1 or size -2 
        
				
				
	}
	
	public void printJobNames()
	{
		for(int i=1; i<size;i++)
		{
			System.out.println(myArray[i].getJobName() + " priority: "+ myArray[i].getFinalPriority());
		}
	}
	
	public int getSize()
	{
		return size;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////Dynamic Resizing and its methods.
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void dynamicResizing()
	{
		if (size==0)
			myArray= new Job[10];
	//if the array is full then we create a new one and resize it
	    else if (size==myArray.length)
			myArray= copyArrayDoubling(myArray);
		else if (size< (myArray.length/4)) 
			myArray= copyArrayHalfing(myArray);
	}
//method that doubles the size of the array and returns it 
	public Job[] copyArrayDoubling(Job[] oldArray)
	{
		
		Job[] newArray= new Job[oldArray.length*2];
		for (int i=0; i<oldArray.length; i++)
			newArray[i]= oldArray[i];
	
		return newArray;
	}
//method that halfs the array is less than 25% of the space is used:
	
	public Job[] copyArrayHalfing(Job[] oldArray)
	{
		Job[] newArray= (Job[]) new Job[oldArray.length/2];
		for (int i=0; i<size; i++)
			newArray[i]= oldArray[i];
			
		return newArray;
	}
	
	
}
