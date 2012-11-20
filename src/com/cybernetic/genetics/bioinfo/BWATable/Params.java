package com.cybernetic.genetics.bioinfo.BWATable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 
 * @author Tibor Nagy
 * Parameter handling.
 *
 */

public class Params {
	
	private String bamfilename = null;
	private BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
	private boolean skipzero  = false;

	public Params(String[] args) {
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-help") || args[i].equals("--help") || args[i].equals("-h")){
				Help.fullhelp();
			}
			
			if(args[i].equals("-bam")){
				bamfilename = args[i+1];
			}
			
			if(args[i].equals("-out")){
				try {
					out = new BufferedWriter(new FileWriter(args[i+1]));
				} catch (IOException e) {
					System.err.println("Problem with opening the file: "+args[i+1]);
					e.printStackTrace();
				} catch (NullPointerException e){
					System.err.println("Please specify the output file name");
				}
			}
			
			if(args[i].equals("-skipzero")){
				skipzero = true;
			}
		}
		
		if(bamfilename == null){
			Help.parameters();
		}
	}
	
	/**
	 * Getter for the output stream.
	 * @return BufferedWriter
	 */
	public BufferedWriter getOutput(){
		return out;
	}
	
	/**
	 * Getter for BAM file name
	 * @return
	 */
	public String getBamName(){
		return bamfilename;
	}
	
	/**
	 * Getter for skip zero
	 * @return
	 */
	public boolean getSkipZero(){
		return skipzero;
	}

}
