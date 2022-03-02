import java.io.File;
import java.util.*;

public class MotsPossible {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final int longueur;
    private final List<Reponse.Rep[]> possibiliter;
    private List<String> motsPossible;
    private String langue;

    private int div;


    public MotsPossible(int longueur,String langue) {
        this.langue=langue;
        File doc = new File(langue);
        Mots mots = new Mots(doc);
        this.longueur = longueur;
        motsPossible = mots.motsde(longueur);
        this.possibiliter = possibiliter();

    }

    private List<Reponse.Rep[]> possibiliter() {
        List<Reponse.Rep[]> possibiliter;
        List<Reponse.Rep[]> possibiliterMem = new ArrayList<>();
        for (Reponse.Rep rep : Reponse.Rep.values()) {
            Reponse.Rep[] tab = new Reponse.Rep[longueur];
            tab[0] = rep;
            possibiliterMem.add(tab);
        }
        for (int i = 1; i < longueur; i++) {
            possibiliter = new ArrayList<>();
            for (Reponse.Rep[] tab : possibiliterMem) {
                for (Reponse.Rep rep : Reponse.Rep.values()) {
                    Reponse.Rep[] tabNew = tab.clone();
                    tabNew[i] = rep;
                    possibiliter.add(tabNew);
                }
            }
            possibiliterMem = possibiliter;
        }

        possibiliter = possibiliterMem;
        return possibiliter;
    }

    public String premier() {
        System.out.println("Il y a " + motsPossible.size() + " mots posible restant");
        System.out.println(motsPossible);
        List<HashMap<Character, Integer>> stat = new ArrayList<>(longueur);
        for (int i = 0; i < longueur; i++) {
            stat.add(new HashMap<>());
            for (String mot : motsPossible) {
                char c = mot.toCharArray()[i];
                HashMap<Character, Integer> dic = stat.get(i);
                if (!(dic.containsKey(c))) {
                    dic.put(c, 1);
                } else {
                    dic.put(c, dic.get(c) + 1);
                }
            }
        }
        for (HashMap<Character, Integer> dic : stat) {
            System.out.println(dic);
        }


        return null;
    }


    public void elimination(Reponse reponse) {
        int avant=motsPossible.size();
        this.motsPossible = elimine(reponse);
       int mtm=motsPossible.size();
       int dif=avant-mtm;
        System.out.println("On avait "+ avant +" mots possible et on en élimine " +dif+".");
        System.out.println("On a donc "+mtm+" mots restant :");
        if(motsPossible.size()==1){
            System.out.println(ANSI_GREEN+ motsPossible.get(0)+ANSI_RESET);
        }
        //System.out.println(motsPossible);
    }

    private double proba(String mot, Reponse.Rep[] reponse) {
        int probaXsize=elimine(new Reponse(mot,reponse)).size();
        int nbElimination=motsPossible.size()-probaXsize;
        int mult=probaXsize*nbElimination;
        //if(mult!=0) div=div+nbElimination;
        div=div+nbElimination;
        //System.out.print(mult+"="+probaXsize+"*"+nbElimination+",");
        return (double)mult;
    }

    private List<String> elimine(Reponse reponse){
        List<String> newMotsPossible = new ArrayList<>(motsPossible);
        List<Character> letresPresente=new ArrayList<>();
        for (int i = 0; i < longueur; i++) {
            Reponse.Rep rep = reponse.getReponse(i);
            char c = reponse.getProposition(i);
            letresPresente.add(c);
            if (rep == Reponse.Rep.Correct) {
                for (String str : motsPossible) {
                    if (str.charAt(i) != c) {
                        newMotsPossible.remove(str);
                    }
                }
            } else if (rep == Reponse.Rep.WrongSpot) {

                for (String str : motsPossible) {
                    if (!(str.contains(String.valueOf(c)) && str.indexOf(c) != i)) {
                        newMotsPossible.remove(str);

                    }
                }
            } else if (rep == Reponse.Rep.NotInTheWorld) {
                for (String str : motsPossible) {
                    if (str.contains(String.valueOf(c)) && !(letresPresente.contains(c))) {
                        newMotsPossible.remove(str);
                    }
                }
            }


        }

        return newMotsPossible;

    }

    private double calculEsperance(String mot) {
        double Esperance = 0;
        div=0;
        for (Reponse.Rep[] rep : possibiliter) {
            Esperance=Esperance+proba(mot,rep);
        }
        return Esperance;
    }

    public List<String> choix() {
        List<String> listMeilleur=new ArrayList<>();
        double con=0;
        HashMap<String, Double> dic = new HashMap<>();
        int i=1;
        int T= motsPossible.size();
        for (String str : motsPossible) {
            double E=calculEsperance(str);
            System.out.print("["+i+"/"+T+"] ");
            System.out.println(str+" avec un score de: "+E);
            i++;
            dic.put(str,E);
            double max=E;
            if (con<max){
                con=max;
            }
        }

        for (Map.Entry<String,Double> e:dic.entrySet()){
            if (e.getValue()==con){
                listMeilleur.add(e.getKey());
            }

        }
        if (listMeilleur.size()==1){
            System.out.print("La meilleure proposition est ");
            System.out.print(ANSI_RED+ listMeilleur.get(0)+ANSI_RESET);
        } else {
            System.out.println("Les mots qui retire le plus sont : ");
            System.out.println(listMeilleur);
        }
        double esp=(double)con;
        System.out.println(" avec une espérence de "+con +" mots éliminé.");//String.format("%.3f",esp)
        return listMeilleur;
    }

    public String random(){
        //return motsPossible.get((int)(Math.random() * ((motsPossible.size()))));
        return"basis";
    }

    public List<String> getMotsPossible() {
        return motsPossible;
    }

    public List<Reponse.Rep[]> getPossibiliter() {
        return possibiliter;
    }
}