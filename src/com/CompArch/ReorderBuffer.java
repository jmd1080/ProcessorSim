package com.CompArch;

public class ReorderBuffer {
	private int instruct[];
	private int dest[];
	private int result[];
	private boolean valid[];
	private int size;
	private int head;
	private int tail;
	private Simulator sim;

	ReorderBuffer (Simulator s, int length)
	{
		sim = s;
		head = 0;
		tail = 0;
		size = length;
		instruct = new int [length];
		dest = new int [length];
		result = new int [length];
		valid = new boolean [length];
		for (int i = 0; i < length; i++)
			valid[i] = false;
	}
	
	void setResult (int index, int val)
	{
		result[index] = val;
		valid[index] = true;
	}
	
	int insert (int[] instruction)
	{
		instruct[head] = instruction[0];
		
		// Dest if branch operation
		if (instruction[0] == 17 | instruction[0] == 18)
			dest[head] = instruction[3];
		else
			dest[head] = instruction[1];
				
		int result = head;
		
		head++;
		
		if (head >= size)
			head = 0;
		
		return result;
	}
	
	void tick()
	{
		//Check if current instruction is valid, if so then write it and move on to the next
		if (valid[tail])
		{
			sim.regFile.set(dest[tail], result[tail]);
			valid[tail] = false;
			tail++;
			if (tail >= size)
				tail = 0;
		}
	}
}