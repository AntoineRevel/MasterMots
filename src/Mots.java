import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class Mots {
    //fichier sélection
    private List<String> mots;


    public Mots(File doc) {
        mots=new ArrayList<String>();
        try {
            Scanner obj = new Scanner(doc);
            while (obj.hasNextLine()) {
                mots.add(obj.nextLine());
        }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<String> motsde(int l){
        List<String> motsL=new ArrayList<String>();
        for (String mot: mots){
            if(mot.length()==l){
                motsL.add(mot);
            }
        }
        return motsL;
    }
}
