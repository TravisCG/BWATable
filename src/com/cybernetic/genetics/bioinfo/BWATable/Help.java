package com.cybernetic.genetics.bioinfo.BWATable;

/**
 * 
 * @author Tibor Nagy
 * Simple help printing class
 *
 */
public class Help {
	public static void fullhelp(){
		System.out.println("BWATable\n\nCreate table from a BAM file");
		System.out.println("This table contains some statistics about the references");
		System.out.println("Parameters:\n");
		parameters();
	}
	
	public static void parameters(){
		System.out.println("-bam:      Bam file name");
		System.out.println("-out:      output file name (default: stdout)");
		System.out.println("-skipzero: Skip zero mapped references (default: false)");
		System.exit(0);
	}
}
