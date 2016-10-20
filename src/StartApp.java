import userInterface.UI;

public class StartApp {
	public static void main(String args[]) {
		repository.RepoCandidati repoC = new repository.RepoCandidati();
		repository.RepoSectii repoS = new repository.RepoSectii();
		
		repoC.add(new domain.Candidat(1,"C1","0746217312","a1",23));
		repoC.add(new domain.Candidat(2,"C2","0746217313","a2",24));
		repoC.add(new domain.Candidat(3,"C3","0746217314","a2",25));
		repoS.add(new domain.Sectie(1,"S1",12));
		repoS.add(new domain.Sectie(2,"S2",13));
		repoS.add(new domain.Sectie(3,"S3",14));
		
		controller.Controller ctr = new controller.Controller(repoC, repoS);
		
		@SuppressWarnings("unused")
		UI ui= new UI(ctr);
	}
}
