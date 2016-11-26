package model;

import domain.Candidat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class CandidatDataModel {

    public static ObservableList<Candidat> getStudentDataModel()
    {
        List<Candidat> l=new ArrayList<Candidat>();
        l.add(new Candidat(1,"C1","1234567890","a1",13));
        l.add(new Candidat(2,"C2","1234567890","a2",18));
        l.add(new Candidat(3,"C3","1234567890","a3",23));
        l.add(new Candidat(4,"C4","1234567890","a4",67));
        ObservableList<Candidat> studentDataModel = FXCollections.observableArrayList(l);
        return studentDataModel;
    }

}
