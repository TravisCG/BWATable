package com.cybernetic.genetics.bioinfo.BWATable;

/**
 * This class represents the columns of the result table
 * @author Tibor Nagy
 *
 */
public class TableColumns {
	private int[] hasread;           // positions where coveraged by reads
	private int reflength;           // Length of the reference
	private int numreads;            // How many reads mapped to the reference
	private double covperc;          // How many length are covered by reads
	private double avgcov;           // Average coverage
	private double stdcpv;           // Coverage standard deviation
	private double avgmq;            // Average mapping quality
	private int unireadnum;          // Number of uniq reads
	
	/**
	 * Constructor for the record. Every record in the table is actually a reference sequence
	 * @param recordname name of the reference file
	 * @param recordlength length of the reference
	 */
	public TableColumns(int recordlength){
		
		hasread = new int[recordlength];
		
		for(int i = 0; i < recordlength; i++){
			hasread[i] = 0;
		}
		
		this.reflength   = recordlength;
		this.numreads    = 0;
		this.covperc     = 0.0;
		this.avgcov      = 0.0;
		this.avgmq       = 0.0;
		this.unireadnum  = 0;
		this.stdcpv      = 0.0;
	}
	
	/**
	 * Update the coverage information. The coverage information is 0 based.
	 * @param from the start position of the coverage (inclusive)
	 * @param to end position of the coverage (exclusive)
	 */
	private void updateCoverage(int from, int to){
		for(int i = from; i < to && i < this.reflength; i++){
			hasread[i]++;
		}
	}
	
	/**
	 * Add this method after you read one record from a SAM/BAM file
	 * @param uniq this read is unique?
	 * @param from read start position (inclusive)
	 * @param to read end position (exclusive)
	 * @param MQ read mapping quality
	 */
	public void updateColumn(boolean uniq, int from, int to, int MQ){
		this.numreads++;
		this.avgmq += MQ;
		updateCoverage(from, to);
		
		if(uniq){
			this.unireadnum++;
		}
	}
	
	/**
	 * After all the reads were processed, call this method to calculate the rest of the statistics
	 */
	public void calculate(){
		double sum1 = 0.0;
		double sum2 = 0.0;
		
		this.avgmq = this.avgmq / (double)this.numreads;

		/* Calculate average coverage and coverage percentages */
		for(int i = 0; i < reflength; i++){
			sum1 += hasread[i];
			if(hasread[i] > 0){
				sum2++;
			}
		}
		
		this.avgcov  = sum1 / (double)reflength;
		this.covperc = sum2 / (double)reflength;
		
		for(int i = 0; i < reflength; i++){
			this.stdcpv += (hasread[i] - this.avgcov)*(hasread[i] - this.avgcov);
		}
		
		this.stdcpv /= (double)reflength;
	}
	
	/**
	 * Getter for the read number
	 * @return
	 */
	public int getReadNum(){
		return this.numreads;
	}
	
	/**
	 * Getter for the coverage percentage (call it after calculate() )
	 * @return
	 */
	public double getCovPerc(){
		return this.covperc;
	}
	
	/**
	 * Getter for the average coverage (call it after calculate() )
	 * @return
	 */
	public double getAvgCov(){
		return this.avgcov;
	}
	
	/**
	 * Getter for the average mapping quality (call it after calculate() )
	 * @return
	 */
	public double getAvgMQ(){
		return this.avgmq;
	}
	
	/**
	 * Getter for the unique read number
	 * @return
	 */
	public int getUniqueReadNum(){
		return this.unireadnum;
	}
	
	/**
	 * Getter for the standard deviation of coverage
	 * @return
	 */
	public double getStdCov(){
		return this.stdcpv;
	}
	
	/**
	 * Getter for the reference length
	 * @return
	 */
	public int getRefLength(){
		return this.reflength;
	}
}
