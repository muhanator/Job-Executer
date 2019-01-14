
public class Job 
{
/*incremented by 1 each time a job is inserted in the queue from the array, executed and each time an iteration is made to 
   search for the starved process */	
/* The timer however is NOT incremented when
the priority of that starved process is finally modified or for the time it took to update the priority
queue . It is very important to notice that the current time is measured in CPU cycles (not actual time). 
It is also very important to notice that this current time is consequently for time simulation and it does 
not represent the actual system time (which is measured in μsec, ms, etc.).  */
	private static int time=0; 
	
	private String jobName;
	private int jobLength; //always between 1 and 70 cycles
	private int currentJobLength;
	private int jobPriority; //1 has the highest priority, this is the KEY 
	private int finalPriority;   //40 has the lowest priority
	private long entryTime;
	private long endTime;
	private long waitTime;
	
	
	private int timeStamp; //this attribute will allow us to know which entry came 1st if there exist multiple
	
//parametrized constructor:
	public Job(String jobName, int jobLength, int jobPriority)
	{
		this.jobName= jobName;
		this.jobLength= jobLength;
		currentJobLength= jobLength;
		this.jobPriority= jobPriority;
		finalPriority= jobPriority;
		entryTime=0;
		waitTime=0;
		endTime=0;
	}
	
	public Job (Job j)
	{
		this.jobName= j.getJobName();
		this.jobLength= j.getJobLength();
		this.currentJobLength= j.getCurrentJobLength();
		this.jobPriority= j.getJobPriority(); //initial priority NEVER CHANGES
		this.finalPriority= j.getFinalPriority();    //CURRENT PRIORITY
		this.entryTime= j.getEntryTime();
		this.waitTime= j.getWaitTime();
		this.endTime= j.getEndTime();
	}
	
//SETTERS & GETTERS:
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getJobLength() {
		return jobLength;
	}

	public void setJobLength(int jobLength) {
		this.jobLength = jobLength;
	}

	public int getCurrentJobLength() {
		return currentJobLength;
	}

	public void setCurrentJobLength(int currentJobLength) {
		this.currentJobLength = currentJobLength;
	}

	public int getJobPriority() {
		return jobPriority;
	}

	public void setJobPriority(int JobPriority) {
		this.jobPriority = jobPriority;
	}

	public int getFinalPriority() {
		return finalPriority;
	}

	public void setFinalPriority(int finalPriority) {
		this.finalPriority = finalPriority;
	}

	public long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String toString()
	{
		return ("Now executing Job_" + jobName + ". Job length: " + jobLength + " cycles;"   + " Current remaing length: " + currentJobLength + 
				" cycles;" + "Initial priority: " +  jobPriority + " Current priority: " + finalPriority);
	}
}
