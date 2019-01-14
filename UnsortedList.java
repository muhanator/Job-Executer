public class UnsortedList {
	Job[] myArray;
	private int size;
	private int availableIndex;
	private long jobsExecuted;
	private long averageWaitTime;
	private int cycles;
	
	public long getJobsExecuted() {
		return jobsExecuted;
	}

	public void setJobsExecuted(long jobsExecuted) {
		this.jobsExecuted = jobsExecuted;
	}

	public long getAverageWaitTime() {
		return averageWaitTime;
	}

	public void setAverageWaitTime(long averageWaitTime) {
		this.averageWaitTime = averageWaitTime;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getAvailableIndex() {
		return availableIndex;
	}

	public void setAvailableIndex(int availableIndex) {
		this.availableIndex = availableIndex;
	}

	public UnsortedList()
	{
		myArray= new Job[10];
		size=0;
		availableIndex=0;
	}
	
	public UnsortedList(int s)
	{
		myArray= new Job[s];
		size=0;
		availableIndex=0;
		jobsExecuted=0;
		averageWaitTime=0;
	}
	
	public String toString()
	{
		String US= "";
		for (int i=0; i<myArray.length; i++)
		{
			if (myArray[i]!=null)
				US+= myArray[i].getJobName() + ", Priority: " + myArray[i].getFinalPriority() +  ", Time:" + myArray[i].getTimeStamp() + "\n";
		}
		return US;
	}
	
	public Job[] copyArrayDoubling(Job[] oldArray)
	{
		Job[] newArray= new Job[oldArray.length*2];
		for (int i=0; i<oldArray.length; i++)
			newArray[i]= oldArray[i];
	
		return newArray;
	}
	
	public void insert(Job j)
	{
	//doubling priority queue if necessary:
		if (availableIndex>=myArray.length)
			myArray= copyArrayDoubling(myArray);
	//inserting the job passed in the unsortedlist priority queue
		myArray[availableIndex]= j;
		availableIndex++;
		this.setSize(++size);
	}
	
//method that removes the JOb object with the highest PQ: 
	public Job removeMin()
	{
		int minIndex=0;
		int size= this.getSize();
	//finding the highest priority
		for (int i=1; i<size; i++)
		{
			//if (myArray[i] != null && myArray[minIndex]!=null)
			//{
				if (myArray[minIndex].getFinalPriority()==myArray[i].getFinalPriority())
				{
					if (myArray[minIndex].getTimeStamp()>myArray[i].getTimeStamp())
						minIndex=i;
				}
				else if (myArray[minIndex].getFinalPriority()>myArray[i].getFinalPriority())
					minIndex=i;
			//}
		}
		Job j= new Job(myArray[minIndex]);
		myArray[minIndex]=null;
		this.setSize(--size);
		shiftBackLeft(minIndex);
		availableIndex--;
		return j;
	}
	
	//method that shifts all the elements in the passedArray 1 element back:
		public void shiftBackLeft(int index)
		{
			for (int i=index; i<size; i++)
				myArray[i]= myArray[i+1];
			myArray[size]=null;
		}
	
//method that removes the starved JOB object (the Job object that has the smallest entry time) :
	public Job removeStarvedJob()
	{
		int minIndex=0;
		int size= this.getSize();
	//finding the highest priority
		for (int i=1; i<size; i++)
		{
			if (myArray[minIndex].getEntryTime()>myArray[i].getEntryTime())
				minIndex=i;
		}
		Job j= new Job(myArray[minIndex]);
		myArray[minIndex]=null;
		this.setSize(--size);
		shiftBackLeft(minIndex);
		availableIndex--;
		return j;
	}
	
//method that executes the current Process 
	public void executeCurrentProcess(Job j)
	{
	//execute:
		int jobLength= j.getCurrentJobLength();
		j.setCurrentJobLength(--jobLength);
		
		j.setWaitTime(cycles - j.getEntryTime());
		jobsExecuted++;
		averageWaitTime= averageWaitTime + j.getWaitTime()/jobsExecuted;
	}
}