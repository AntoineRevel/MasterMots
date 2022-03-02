import java.util.Arrays;
import java.util.Collection;

public class Reponse {


    enum Rep{
        Correct,
        WrongSpot,
        NotInTheWorld,
    }

    private final char[] proposition;
    private final Rep[] reponse;

    public Reponse(String proposition, Rep[] reponse) {
        this.proposition = proposition.toCharArray();
        this.reponse = reponse;
    }

    public Reponse(String motPropose, String rep){
        if(rep.length()!=motPropose.length()){
            throw (new IllegalArgumentException("Mauvaisse longeur"));
        }
        if(rep.length()!=motPropose.length()){
            throw new RuntimeException("mauvaise entré utilisateur, Atendu :"+ motPropose.length()+" au lieux de :"+ rep.length());
        } else {
            this.reponse= new Rep[motPropose.length()];
            this.proposition=motPropose.toCharArray();
            int i=0;
            for(String r :rep.split("")){
                if(r.equals("0")){
                    this.reponse[i]=Rep.NotInTheWorld;
                } else if (r.equals("1")){
                    this.reponse[i]=Rep.WrongSpot;
                } else if (r.equals("2")){
                    this.reponse[i]=Rep.Correct;
                } else {
                    throw new RuntimeException("Mauvaise valeur : "+r);
                }
                i++;
            }
        }
    }

    public char getProposition(int i) {
        return proposition[i];
    }

    public Rep getReponse(int i) {
        return reponse[i];
    }


    @Override
    public String toString() {
        return "Reponse : "+ Arrays.toString(reponse);
    }
}
