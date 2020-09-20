package com.ssu.spec.repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileAccessor {
	private File specFilePath = null;

	public FileAccessor() {
		this(RepositConstants.SPEC_FILE_PATH);
	}
	
	public FileAccessor(String path) {
		specFilePath = new File(path);
	}
	
	public ArrayList<String> getFileList(int fileType) {
		if(specFilePath.isFile()) {
			return null;
		}
		File[] fileList = specFilePath.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		File tmpFile = null;
		try {
			for(int i=0; i<fileList.length; i++) {
				tmpFile = fileList[i];
				switch (fileType) {
				case RepositConstants.ALL_TYPE :
					fileNames.add(tmpFile.getName()); break;
				case RepositConstants.FILE_TYPE :
					if(tmpFile.isFile()) fileNames.add(tmpFile.getName()); break;
				case RepositConstants.DIR_TYPE :
					if(tmpFile.isDirectory()) fileNames.add(tmpFile.getName()); break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return fileNames;
	}
	
	public ArrayList<String> getFileList(String bottomPath, int fileType) {
		File bottomFile = new File(specFilePath.getPath()+"/"+bottomPath);
		if(bottomFile.isFile()) {
			return null;
		}
		File[] fileList = bottomFile.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		File tmpFile = null;
		
		for(int i=0; i<fileList.length; i++) {
			tmpFile = fileList[i];
			fileNames.add(tmpFile.getName());
		}
		
		return fileNames;
	}
	
	public String getSpecFileString(String fileType, String fileName) {
		String specFileStr = null;
		String specFileName = specFilePath.getPath()+"/"+fileType+"/"+fileName;
		
		try {
			specFileStr = new String(Files.readAllBytes(Paths.get(specFileName)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return specFileStr;
	}
	
	public String getFileString(String fileName) {
		String specFileStr = null;
		String specFileName = specFilePath.getPath()+"/"+fileName;
		
		try {
			System.out.println("[FileAccessor] filePath : "+specFileName);
			specFileStr = new String(Files.readAllBytes(Paths.get(specFileName)));
			System.out.println("[FileAccessor] read file");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return specFileStr;
	}
	
	public int generateFile() {
		return 0;
	}
	
	private int changeFile() {
		return 0;
	}
}
