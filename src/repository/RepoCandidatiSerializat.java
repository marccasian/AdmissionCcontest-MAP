package repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import domain.Candidat;

public class RepoCandidatiSerializat extends RepoCandidati{

	public RepoCandidatiSerializat(){
		all = deserializeCandidat("Candidati.bin");
	}
	
	public static void serializeCandidat( ArrayList<Candidat> list)
	{
		String fName = "Candidati.bin";
		ObjectOutputStream os=null;
		try {
			os=new ObjectOutputStream(new FileOutputStream(fName));
			os.writeObject(list);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Candidat>  deserializeCandidat(String fName)
	{
		ArrayList<Candidat> list=null;
		ObjectInputStream os=null;
		try {
			os=new ObjectInputStream(new FileInputStream(fName));
			list=new ArrayList<>();
			try {
				list=(ArrayList<Candidat>)os.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if (os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}