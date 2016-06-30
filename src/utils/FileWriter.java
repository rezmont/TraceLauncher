/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * @version 7/29/2013
 */
package utils;

import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.file.*;

/**
 *
 * @author morgan
 */
public class FileWriter {
//    private Path path;
//	private String path;

	private String path;

	public FileWriter(String path) {
//        this.path = Paths.get(path);    
		this.path = path;
	}

	public void write(String stringToWrite) {

		BufferedWriter buffer = null;
		try {
			buffer = new BufferedWriter(new java.io.FileWriter(path, true));
			buffer.write(stringToWrite, 0, stringToWrite.length());
			buffer.close();
			// buffer.write("\n");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*public void write(String stringToWrite){
	 byte[] buf = stringToWrite.getBytes();
	 try{
	 Files.write(path, buf, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
	 } catch (IOException ioe){
	 System.err.println(ioe.getMessage());
	 }
	 }*/
}
