package repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import domain.Candidat;
import domain.Inscriere;
import domain.Sectie;

public class RepoInscrieriSerializat extends RepoInscrieri{

	public RepoInscrieriSerializat(){
		//save(new Inscriere(1, new Candidat(1,"Casian","0747127916","Cluj Napoca",19),new Sectie(1,"UBB Info Romana",200)));
		//save(new Inscriere(2, new Candidat(2,"Roxana","0751451605","Cluj Napoca",20),new Sectie(2,"UMF MG Romana",300)));
		//serializeInscrieri(all);
		all = deserializeInscrieri("Inscrieri.bin");
	}
	
	public static void serializeInscrieri( ArrayList<Inscriere> list)
	{
		String fName = "Inscrieri.bin";
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
	public static ArrayList<Inscriere>  deserializeInscrieri(String fName)
	{
		ArrayList<Inscriere> list=null;
		ObjectInputStream os=null;
		try {
			os=new ObjectInputStream(new FileInputStream(fName));
			list=new ArrayList<>();
			try {
				list=(ArrayList<Inscriere>)os.readObject();
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
