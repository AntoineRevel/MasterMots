

import java.util.List;
import java.util.Scanner;

//https://hellowordl.net

import static java.lang.System.exit;

public class ApplicationMots {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }

        System.out.print("Choose your option : ");
    }
    private static String[] options = {"1- Jouer ",
            "2- Langue",
            "3- Longueur mots",
            "4- Surprise",
            "5- Exit",
    };

    private static String langue="mots.txt";
    private static int longeur=5;

    public static void main(String[] args) {
        Scanner saisieUtilisateur = new Scanner(System.in);
        menu(saisieUtilisateur);


    }

    private static void menu(Scanner saisieUtilisateur){
        printMenu(options);
        int option=1;
        while (true){
            try {
                option = saisieUtilisateur.nextInt();
                switch (option){
                    case 1: start(saisieUtilisateur); break;
                    case 2: choixLangue(saisieUtilisateur); break;
                    case 3: choixLongeur(saisieUtilisateur); break;
                    case 4: System.out.println("Mon bébé Fabien");
                            menu(saisieUtilisateur); break;
                    case 5: exit(0);
                }
            }
            catch (Exception ex){
                System.out.println("Please enter an integer value between 1 and " + options.length);
                saisieUtilisateur.next();
            }
        }

    }

    private static void choixLongeur(Scanner saisieUtilisateur) {
        System.out.print("Entré la longeur voulus : ");
        longeur=saisieUtilisateur.nextInt();
        menu(saisieUtilisateur);
    }

    private static void choixLangue(Scanner saisieUtilisateur) {
        System.out.println("1- Francais");
        System.out.println("2- Anglais");
        int option=0;
        try {
            option = saisieUtilisateur.nextInt();
            switch (option){
                case 1: langue="motsFR.txt"; break;
                case 2: langue="mots.txt"; break;
                case 3: exit(0);
            }
        }
        catch (Exception ex){
            System.out.println("Please enter an integer value between 1 and " + options.length);
            saisieUtilisateur.next();
        }
        menu(saisieUtilisateur);

    }



    public static void start(Scanner saisieUtilisateur){
        System.out.print("On joue avec des mots de "+longeur+" lettres en ");
        if(langue.equals("mots.txt")) System.out.println("anglais");
        if(langue.equals("motsFR.txt")) System.out.println("f00rancais");
        MotsPossible MP=new MotsPossible(longeur,langue);
        //MP.premier();
        String premiereProp=MP.random();
        System.out.println("Proposition du premier mots aléatoire :");
        System.out.println(premiereProp);

        //String prope=saisieUtilisateur.next();
        System.out.println("Pour coder la réponse du jeux :");
        System.out.println("0- pour une letre qui n'est pas dans le mot (gris)");
        System.out.println("1- pour une letre dans le mot mais pas au bon endroit (jaunes)");
        System.out.println("2- pour une letre au bon endroit (vert)");
        System.out.print("Réponse du jeux : ");
        MP.elimination(new Reponse(premiereProp,saisieUtilisateur.next()));

        while(MP.getMotsPossible().size()>1){
            String prop;
            List<String> choix=MP.choix();

            if (choix.size()==1){
                prop=choix.get(0);
            } else {
                System.out.print("Proposition de mot : ");
                prop=saisieUtilisateur.next();
            }
            System.out.print("Réponse du jeux : ");
            MP.elimination(new Reponse(prop,saisieUtilisateur.next()));

        }
        System.out.println("___________________Fini !!!___________________");
        System.out.println("Entré pour rejouer");
        saisieUtilisateur.nextLine();
        saisieUtilisateur.nextLine();
        menu(saisieUtilisateur);


    }
}
