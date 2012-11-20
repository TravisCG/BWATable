package com.cybernetic.genetics.bioinfo.BWATable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import net.sf.samtools.SAMFileHeader;
import net.sf.samtools.SAMSequenceRecord;

/**
 * This class holds the information for the output
 * @author Tibor Nagy
 *
 */
public class ResultTable {
	
	private HashMap<String, TableColumns> table;
	
	public ResultTable(SAMFileHeader header){
		SAMSequenceRecord reference;
		int i = 0;
		
		table = new HashMap<String, TableColumns>();
		
		/* Fill the table with reference names from SAM/BAM header */
		while( (reference = header.getSequence(i)) != null )
		{
			table.put(reference.getSequenceName(), new TableColumns(reference.getSequenceLength()));
			i++;
		}
		
	}
	
	/**
	 * Add new information about the mapping.
	 * If the reference name is not found in the header, the program ignore it.
	 * Also ignore the unmapped reads.
	 * @param refid Identity of the reference
	 * @param uniq It is unique?
	 * @param from start position (inclusive)
	 * @param to end position (exclusive)
	 * @param MQ mapping quality
	 */
	public void add(String refid, boolean uniq, int from, int to, int MQ){
		
		if(table.containsKey(refid)){
			/* Update existing record */
			TableColumns entry = table.get(refid);
			entry.updateColumn(uniq, from, to, MQ);
		}
		
	}
	
	/**
	 * Summarize the results and print it out
	 */
	public void summary(BufferedWriter output, boolean skipzero){
		
		try{
			
			output.write("Refid RefLength ReadNumber CoveragePercentage AverageCoverage StdCoverage AverageMappingQuality UniqReadNumber\n");
			for(String key : table.keySet()){
				
				TableColumns entry = table.get(key);
				entry.calculate();
				
				if(!skipzero || entry.getReadNum() != 0 ){
					output.write("\""+key+"\""+" "+entry.getRefLength() + " " +
				                                   entry.getReadNum()   + " " +
				                                   entry.getCovPerc()   + " " +
							                       entry.getAvgCov()    + " " +
				                                   entry.getStdCov()    + " " +
							                       entry.getAvgMQ()     + " " +
				                                   entry.getUniqueReadNum()+"\n");
				}
				
			}
			output.close();
			
		}catch(IOException e){
			System.err.println("Can not write out results");
		}
	}
}
