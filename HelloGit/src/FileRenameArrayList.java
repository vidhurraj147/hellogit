

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileRenameArrayList {
	
	public static void main(String[] args) {
		// fileNameLogin();
		
		renameIt(new File("C:\\ENG_songs"));
		List<String> allSongs = new ArrayList<String>();
  		deleteIt(new File("C:\\ENG_songs"),allSongs);
	}
	
public static void renameIt(File node){
		
		System.out.println(node.getAbsoluteFile());
		
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				// displayIt(new File(node, filename));
				fileNameLogic(node.getAbsoluteFile()+"\\"+filename);
			}
		}
}


public static void deleteIt(File node,List<String> allSongs){
	
	System.out.println("LOCATION - "+node.getAbsoluteFile());
	
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			if(filename.contains(".mp3") || filename.contains(".m4a")) {
				if(allSongs.contains(filename)) {
					System.out.println("-------------------------------------DELETE THIS FILE "+filename);
				}else {
					allSongs.add(filename);
				}
				
			}else {
				deleteIt(new File(node, filename),allSongs);
			}
		}
	}
}
	private static void fileNameLogic(String fileName) {
		// String fileName = "C:\\Users\\HPSams\\Downloads\\ENG\\13042018";
		File f = new File(fileName);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        //return name.startsWith("temp") && name.endsWith("txt");
		        //return name.endsWith("m4a");
		    	return name.endsWith("mp3") || name.endsWith("m4a");
		    }
		});
		
		for(File files : matchingFiles){
			//boolean success = files.renameTo(files);
			String presentFileName = files.getName();
			//TODO: Write a login to see it has any numbers
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
				}else{
					boolean success = files.renameTo(renameFile);
				}
			}
		}
		
	}
}
