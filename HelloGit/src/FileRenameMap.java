

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

//THIS IS WORKING APPLICATION
public class FileRenameMap {
	private static List<String> allDirs = new ArrayList<String>();
	private static List<String> emptyFolders = new ArrayList<String>();
	public static void main(String[] args) {
		String folderPath = "C:\\ENG_songs";
		renameIt(new File(folderPath));
		Map<String,String> allSongs = new HashMap<String,String>();
  		deleteIt(new File(folderPath),allSongs);
  		deleteEmptyFolders();
  		sortEachFolder();
  		//TODO sort by views https://www.youtube.com/results?search_query=pray+for+me&pbj=1 
	}
	
private static void sortEachFolder() {
		for (String allDir : allDirs) {
			File srcDir = new File(allDir);
			String destination = "";
			if(allDir.contains("2018")){
				System.out.println("2018 FOLDER FOUND "+allDir);
				destination = "C:\\SONGS\\2018";
			}else if(allDir.contains("2017")){
				destination = "C:\\SONGS\\NEW";
				System.out.println("2017 FOLDER FOUND "+allDir);
			}else {
				destination = "C:\\SONGS\\OLD";
				System.out.println("OTHER "+allDir);
			}
			
			File destDir = new File(destination);
			copyContents(srcDir,destDir);
			
		}
		System.out.println("COPY COMPLETED");
	}

private static void copyContents(File srcDir, File destDir) {
	String[] srcFiles = srcDir.list();
	for(String srcFile : srcFiles) {
		File srcFileMP3 = new File(srcDir+"\\"+srcFile);
		try {
			System.out.println("FILE COPYING "+srcFileMP3.toString());
			if(!new File(destDir,srcFile).exists()) {
				FileUtils.copyFileToDirectory(srcFileMP3, destDir);
			}else {
				System.out.println("File "+srcFile+ " exists");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//copyFileToDirectory(srcFileMP3, destDir);//         throws IOException
	}
}

private static void deleteEmptyFolders() {
		System.out.println("Delete these empty folders "+emptyFolders);
		for(String emptyfolder :emptyFolders) {
			System.out.println("DELETE THIS FOLDER "+emptyfolder);
			File deleteThis = new File(emptyfolder);
			deleteThis.delete();
		}
	}

public static void renameIt(File node){
		System.out.println(node.getAbsoluteFile());
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				allDirs.add(node.getAbsoluteFile()+"\\"+filename);
				fileNameLogic(node.getAbsoluteFile()+"\\"+filename);
			}
		}
}


public static void deleteIt(File node,Map<String,String> allSongs){
	
	System.out.println("FOLDER IS " + node.getAbsoluteFile());
	if(node.isDirectory()){
		String[] subNote = node.list();
		System.out.println("THIS HAS size "+subNote.length);
		if(subNote.length<1) {
			emptyFolders.add(node.getAbsoluteFile().toString());
			System.out.println(emptyFolders);
		}
		for(String filename : subNote){
			if(filename.contains(".mp3") || filename.contains(".m4a")) {
				if(allSongs.get(filename) != null) {
					System.out.println("DELETE THIS FILE__ "+filename+" __, __FOUND IN__ "+allSongs.get(filename));
					File deleteThis = new File(node.getAbsoluteFile().toString()+"\\"+filename);
					if(deleteThis.delete()){
		    			System.out.println(deleteThis.toString() + " is deleted!");
		    		}else{
		    			System.out.println("Delete operation is failed.");
		    		}
					
				}else {
					//allSongs.add(filename);
					allSongs.put(filename, node.getName());
				}
				
			}else {
				deleteIt(new File(node, filename),allSongs);
			}
		}
		//TODO delete the folder if size is 0
	}
}
	private static void fileNameLogic(String fileName) {
		File f = new File(fileName);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	return name.endsWith("mp3") || name.endsWith("m4a");
		    }
		});
		
		File[] notMatchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	return !(name.endsWith("mp3") || name.endsWith("m4a"));
		    }
		});
		
		for(File files : matchingFiles){
			String presentFileName = files.getName();
			int subString = 0;
			for(int i=0;i<=presentFileName.length();i++) {
				if(Character.isLetter(presentFileName.charAt(i))){
					break;
				}else {
					subString++;
				}
			}
			if(subString > 0) {
				String modified = presentFileName.substring(subString);
				File renameFile = new File(fileName+"\\"+modified);
				System.out.println("PRESENT "+presentFileName);
				System.out.println("MODIFIE "+modified);
				if(renameFile.exists()){
					System.out.println("SKIPPED SKIPPED SKIPPED"+files.getName());
					files.delete();
					
					
				}else{
					boolean success = files.renameTo(renameFile);
				}
			}
		}
		
		for(File file:notMatchingFiles) {
			System.out.println("DELETE this "+file);
			file.delete();
		}
		
	}
}
