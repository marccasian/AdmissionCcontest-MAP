package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
import java.io.PrintWriter;

import domain.Sectie;
import domain.ValidatorException;
import java.io.File;

public class RepoSectiiFile extends RepoSectii {
	
	private String fName;
	
	public RepoSectiiFile(String fName) {
		
		this.fName=fName;
		loadData();
	}

	private void loadData(){
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(fName));
			String line;

			while((line = br.readLine())!= null)
			{
				String[] fields = line.split(";");
				
				if(fields.length != 3){
					try {
						throw new Exception("Fisier corupt/invalid!");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				Sectie t = new Sectie(Integer.parseInt(fields[0]),fields[1],Integer.parseInt(fields[2]));
				try {
					super.add(t);
				} catch (ValidatorException e) {
					System.out.println(e.getMessage());
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Can't find the file!");
		} catch (IOException e) {
			System.out.println("Can't read the file!");
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Cannot close fileReader");
				}

			}
		}
	}
	
	
	public void saveData(){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fName));
			
			for (Sectie s: all){
				String st = s.toString().replace("|", ";");
				bw.write(st,0,st.length());
				bw.newLine();
				bw.flush();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Can't find the file!");
		} catch (IOException e) {
			System.out.println("Can't read the file!");
		}
		finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					System.out.println("Cannot close fileReader");
				}
			}
		}
	}
	
}