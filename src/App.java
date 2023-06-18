import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.time.LocalDate;


public class App {
    //generovanie hadoneho dna
    public static List<LocalDate> getNarodeniny(int pocetNarodenin){
        //vytvorenie pola pre ukladanie potrebnych hodnot
        List<LocalDate> narodeniny = new ArrayList<>();
        //urcenie prveho datumu a nasledne sa od neho prechadza dalej. V tomto pripade je jedno o aky rok sa jedna je dolezity len mesiac a den nie rok. Za rok moze byt kludne dosadene lubovolne cislo. 
        LocalDate zaciatokRoka = LocalDate.of(2023, 1, 1);

        //generovanie nahodneho dna a ukladanie do premennej
        Random ranodom = new Random();

        //vytvaranie zoznamu.
        for(int i = 0; i < pocetNarodenin; i++){
            //generovanie nahodneho dna narodenia
            int nahodny_pocet_narodenin = ranodom.nextInt(365);
            LocalDate narodenie = zaciatokRoka.plusDays(nahodny_pocet_narodenin);

            //pridanie do pola
            narodeniny.add(narodenie);
        }

        //vratenie pola obsahujuceho vsetky nahodne generovane narodeniny
        return narodeniny;
    }

    //hladanie rovnakych datumov
    public static LocalDate getRovnake(List<LocalDate> narodeniny){
        Set<LocalDate> jedinecneNarodeniny = new HashSet<>();

        //prechadzanie zoznamu a pri najdeni datumu ktory je totozny tak vyhodi hodnotu
        for(LocalDate narodenie : narodeniny){
            if (jedinecneNarodeniny.contains(narodenie)){
                return narodenie;
            }
            jedinecneNarodeniny.add(narodenie);
        }
        return null;
    }

    /* ===== main ===== */
    public static void main(String[] args) {
        System.out.println("Narodeninovy paradox: ");

        //scanner
        Scanner scanner = new Scanner(System.in);

        int pocetNarodenin = 0;

        //zadanie poctu narodenin hodnota musi byt viac ako 0 ale mensia ako 100
        while(true){
            System.out.println("Zadaj pocet narodenin: (menej ako 100 ale viac ako 1): ");
            String vstup = scanner.nextLine();

            //zistenie ci vstup je cislo v rozsahu 1-100
            try {
                pocetNarodenin = Integer.parseInt(vstup);
                if(pocetNarodenin >= 1 && pocetNarodenin <= 100){
                    break;
                }else{
                    System.out.println("Nespravne zadaj cislo v rozsahu 1 az 100: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Nespravny vstup. Zadaj len cislo od 1 az 100. ");
            }
        }

        //vypisanie poctu narodenin
        System.out.println("Zadal si " + pocetNarodenin + " narodenin. Tieto hodnoty su: ");
        List<LocalDate> narodeniny = getNarodeniny(pocetNarodenin);
        for(int i = 0; i < narodeniny.size(); i++){
            LocalDate narodenie = narodeniny.get(i);

            //predelenie ked sa najdu rovnake datumy
            if(i != 0){
                System.out.print(", ");
            }

            System.out.print(narodenie.getDayOfMonth() + " " + narodenie.getMonth());
        }
        System.out.println();

        LocalDate rovnake = getRovnake(narodeniny);

        //vypisanie rovnakych narodenin
        System.out.println("V tejto simulaci,");
        if (rovnake != null){
            System.out.println("mali ludia narodeniny rovnake pre: " + rovnake.getDayOfMonth() +" "+ rovnake.getMonth());
        }else{
            System.out.println("neboli ziadne dni s rovnakymi narodeninami.");
        }

        //generovanie 100 000 simulacii s danym mnozstvom dat pre lepsiu statistiku
        int simRovnake = 0;

        //prechadzanie a ratanie poctu kedy maju ludia narodeniny v ten isty den
        for(int i = 0; i < 100_000; i++){
            List<LocalDate> randomNarodeniny = getNarodeniny(pocetNarodenin);

            //spocitanie rovnakych narodenin pre 100 000 simulacii
            if(getRovnake(randomNarodeniny) != null){
                simRovnake++;
            }
        }

        //vypisanie hodnot
        System.out.println("100 000 simulacii prebehlo.");
        double pravdepodobnost = Math.round((simRovnake / 100000.0) * 100.0 * 100.0) / 100.0; //statistika

        System.out.println("V 100 000 simulaciach pre " + pocetNarodenin + " narodenin ludi bolo " + simRovnake + " . ");
        System.out.println("Toto znamena ze " + pocetNarodenin + " ludi ma pravdepodobnost " + pravdepodobnost + " % ze budu mat narodeniny v rovnaky den. ");

    }
}



