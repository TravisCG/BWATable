package com.cybernetic.genetics.bioinfo.BWATable;

import java.io.File;

import net.sf.samtools.*;

public class BWATable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Params param = new Params(args);
		
		/* I do not interested in SAM errors */
        SAMFileReader.setDefaultValidationStringency(SAMFileReader.ValidationStringency.SILENT);
        
		SAMFileReader bam = new SAMFileReader(new File(param.getBamName()));
		ResultTable table = new ResultTable(bam.getFileHeader());
		boolean uniq;

		for(SAMRecord record : bam){
			
			if(record.getAttribute("XA") == null){
				uniq = true;
			}
			else{
				String[] alternatives = ((String)record.getAttribute("XA")).split(";");
				uniq = false;
				for(String altplaces : alternatives){
					String[] fields = altplaces.split(",");
					table.add(fields[0], uniq, Math.abs(Integer.parseInt(fields[1])),
							                   Math.abs(Integer.parseInt(fields[1])) + record.getReadLength(),
							                   record.getMappingQuality());
				}
			}
			
			table.add(record.getReferenceName(), uniq, record.getAlignmentStart(), record.getAlignmentEnd(), record.getMappingQuality());

		}
		
		table.summary(param.getOutput(), param.getSkipZero());
		/* Everything is fine, time to release resources */
		bam.close();
	}

}
