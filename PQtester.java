// Unsorted Priority Queue and Array List based heap Priority Queue
// By: Muhammad Saad Mujtaba (ID: 40043156) and Jimmy Lau (ID: 40044187)

import java.util.concurrent.ThreadLocalRandom;

public class PQtester {

	public static void main(String[] args)
	{


		
		
		int maxNumberOfJobs=10000;
		
		Job[] jobsInputArray1= new Job[maxNumberOfJobs];
		Job[] jobsInputArray2 = jobsInputArray1;
	
		for (int i=0; i<jobsInputArray2.length; i++)
		{
			int randomJobLength=ThreadLocalRandom.current().nextInt(1,70); //generates a random Number between 1 and 70 for jobLength
			int randomJobPriority=ThreadLocalRandom.current().nextInt(1,40); //generates a random Number between 1 and 40 for jobPriority
			jobsInputArray2[i]= new Job("JOB_" + i+1, randomJobLength, randomJobPriority);
		}
		
		System.out.println("****************Here are the results when MaxNumberOfJobs is set to 100***************************");
		System.out.println("-------UnSortedListPriorityQueue---------:");
		UnsortedList us1= new UnsortedList(maxNumberOfJobs); //creating an unsorted list
		
		long start= System.currentTimeMillis();
	//here we will insert into the UnsortedList queue:
		for (int j=0; j<maxNumberOfJobs; j++)
		{
			us1.insert(jobsInputArray1[j]); //just dumping all the jobs in the UnsortedList PQ
			jobsInputArray1[j].setTimeStamp(j+1); //setting the TIME STAMP of the JOB so that we know when it was entered in the PQ
			jobsInputArray1[j].setEntryTime(j+1);
			
		}
		
		int time= maxNumberOfJobs+1;
		System.out.println(us1);
		
		int priorityChanges=0;
		int cycles=0;
		//while the UnsortedList PQ is not empty:
		while (us1.getSize()!=0)
		{
			Job j;
			if (cycles%30==0) //starvation
			{
				System.out.println("***Now we are going to remove starved job: ");
				j= us1.removeStarvedJob(); //find oldest job(the one with the lowest entry time) and set its currentpriority to 1
				priorityChanges++;
			}
			else //finding the job with the highest priority:
				j= us1.removeMin();
			
			//System.out.println(us1);
			
		//executing the process on that job:
			us1.executeCurrentProcess(j);
		//printing info about current job j
			System.out.println(j);
		//if the job hasn't finished then we insert it back in the Unsorted PQ
			if (j.getCurrentJobLength()>0)
			{
				us1.insert(j);
				j.setTimeStamp(++time); //increment the time that it was inserted at
			}
			us1.setCycles(++cycles);
			
			cycles++;
		}
		long end= System.currentTimeMillis();
		long totalTime1= end - start; //time it took to run
		
		
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Array list based heap PQ 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	//Here we will use the the PQ implementations to run our simulator:
	//filling up the jobs input array with JOB objects:
		
// Initializing the Priority Queue
		ArrayListPQ PQ1 = new ArrayListPQ();
		
		
// Unsorted Insertion of jobsInputArray into the Priority Queue
		long startTime = System.currentTimeMillis();
		for (int i=0; i<= jobsInputArray2.length-1;i++)
		{
			PQ1.add(jobsInputArray2[i]);
		}
		
		PQ1.arrayToHeap();
		
		
//Function to check if the jobs are in a heap order (use n=100)
		//PQ1.printJobNames();
		
		while (PQ1.getSize() != 0)
		{
			PQ1.executeCurrentProcess();
		}
		
		System.out.println("\n");
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println("Unsorted list Priority Queue is now empty");
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println("Current system time (cycles): " + cycles);
		System.out.println("Total number of jobs executed: " + maxNumberOfJobs);
		System.out.println("Average process waiting time: " + us1.getAverageWaitTime());
		System.out.println("Total number of priority changes: " + priorityChanges);
		System.out.println("Actual system time needed to execute all jobs: " + totalTime1 + "ms");
		System.out.println("\n");
		
		PQ1.stats();
		
		
		long endTime = System.currentTimeMillis();
		long totalTime2 = endTime - startTime;
		System.out.println("Actual System time needed to execute all jobs " + totalTime2 + " ms");
	}
}
