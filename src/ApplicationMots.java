import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class ApplicationMots {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_gras = "\u001B[1m";

    public static final String cheminFR = "ressources/motsFR.txt";
    public static final String cheminAn = "ressources/mots.txt";
    private final static String[] options = {"1- Jouer ",
            "2- Langue",
            "3- Longueur mots",
            "4- Exit",
    };
    private static String langue = cheminAn;
    private static int longeur = 5;

    public static void printMenu(String[] options) {
        System.out.println("_____Menu_____");
        for (String option : options) {
            System.out.println(option);
        }

        System.out.print("Choose your option : ");
    }

    public static void main(String[] args) {
        Scanner saisieUtilisateur = new Scanner(System.in);
        menu(saisieUtilisateur);
    }

    private static void menu(Scanner saisieUtilisateur) {
        printMenu(options);
        int option;
        while (true) {
            option = saisieUtilisateur.nextInt();
            switch (option) {
                case 1:
                    start(saisieUtilisateur);
                    break;
                case 2:
                    choixLangue(saisieUtilisateur);
                    break;
                case 3:
                    choixLongeur(saisieUtilisateur);
                    break;
                case 4:
                    exit(0);
            }
        }

    }

    private static void choixLongeur(Scanner saisieUtilisateur) {
        System.out.print("Entré la longeur voulus : ");
        longeur = saisieUtilisateur.nextInt();
        menu(saisieUtilisateur);
    }

    private static void choixLangue(Scanner saisieUtilisateur) {
        System.out.println("1- Francais");
        System.out.println("2- Anglais");
        int option;
        try {
            option = saisieUtilisateur.nextInt();
            switch (option) {
                case 1:
                    langue = cheminFR;
                    break;
                case 2:
                    langue = cheminAn;
                    break;
                case 3:
                    exit(0);
            }
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and " + options.length);
            saisieUtilisateur.next();
        }
        menu(saisieUtilisateur);

    }


    public static void start(Scanner saisieUtilisateur) {
        System.out.println("https://hellowordl.net");
        System.out.print("On joue avec des mots de " + longeur + " lettres en ");
        if (langue.equals(cheminAn)) System.out.println("anglais.");
        if (langue.equals(cheminFR)) System.out.println("francais.");
        MotsPossible MP = new MotsPossible(longeur, langue);
        //MP.premier();
        System.out.println("Pour coder la réponse du jeux :");
        System.out.println("0- pour une lettre qui n'est pas dans le mot (gris)");
        System.out.println("1- pour une lettre dans le mot mais pas au bon endroit (jaunes)");
        System.out.println("2- pour une lettre au bon endroit (vert)");
        String firtProp = ouverture(MP);
        System.out.print("Réponse du jeux : ");
        MP.elimination(new Reponse(firtProp, saisieUtilisateur.next()));

        while (MP.getMotsPossible().size() > 1) {
            String prop;
            List<String> choix = MP.choix();

            if (choix.size() == 1) {
                prop = choix.get(0);
            } else {
                int indice;
                System.out.print("Numéros du mot proposé : ");
                indice = saisieUtilisateur.nextInt();
                prop = choix.get(indice - 1);
                System.out.println("Proposition : " + ANSI_RED + prop + ANSI_RESET);
            }
            System.out.print("Réponse du jeux : ");
            MP.elimination(new Reponse(prop, saisieUtilisateur.next()));

        }
        System.out.println("___________________Fini !!!___________________");
        System.out.println("Press Enter to continue");
        saisieUtilisateur.nextLine();
        saisieUtilisateur.nextLine();
        menu(saisieUtilisateur);

    }

    private static String ouverture(MotsPossible MP) {
        HashMap<Integer, String> bestOuverture = new HashMap<>();
        if (langue.equals(cheminAn)) {
            bestOuverture.put(2, "ho" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "27.489");
            bestOuverture.put(3, "eat" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "462.316");
            bestOuverture.put(4, "sale" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "2146.642");
            bestOuverture.put(5, "tares" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "4175.682");
        }

        if (langue.equals(cheminFR)){
            bestOuverture.put(2, "au" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "48,374");
            bestOuverture.put(3, "aie" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "374,294");
            bestOuverture.put(4, "taie" + ANSI_RESET + " avec une espérence de " + ANSI_gras + "1929,883");

        }
        int longeur = MP.getLongueur();
        String prop;
        if (bestOuverture.containsKey(longeur)) {
            prop = bestOuverture.get(longeur);
            System.out.print("Meilleur ouverture : ");
            System.out.println(ANSI_RED + prop+ ANSI_RESET + " mots éliminé.");
            return prop.substring(0, longeur);
        }
        prop = MP.random();
        if (longeur == 6) {
            System.out.println("Patience calcul de l'esperance long");
            System.out.println("Proposition d'une ouverture aléatoire : " + ANSI_RED + prop + ANSI_RESET + " avec une espérence de " + ANSI_gras + String.format("%.3f", MP.calculEsperance(prop)) + ANSI_RESET + " mots éliminé.");
        } else {
            System.out.println("Proposition d'une ouverture aléatoire : " + ANSI_RED + prop + ANSI_RESET);
        }

        //System.out.println(prop);
        return prop;
    }
}
