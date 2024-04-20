package Password_Check;

import java.util.Objects;
import java.util.Scanner;

public class Generator {
    Alphabet alphabet;
    public static Scanner keyboard;

    public Generator(Scanner scanner) {
        keyboard = scanner;
    }

    public Generator(boolean IncludeUpper, boolean IncludeLower, boolean IncludeNum, boolean IncludeSym) {
        alphabet = new Alphabet(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
    }

    public void mainLoop() {
        System.out.println("Şifre kontrol servisine hoşgeldiniz! :)");
        printMenu();

        String userOption = "-1";

        while (!userOption.equals("4")) {

            userOption = keyboard.next();

            switch (userOption) {
                case "1" -> {
                    requestPassword();
                    printMenu();
                }
                case "2" -> {
                    checkPassword();
                    printMenu();
                }
                case "3" -> {
                    printUsefulInfo();
                    printMenu();
                }
                case "4" -> printQuitMessage();
                default -> {
                    System.out.println();
                    System.out.println("Lütfen mevcut komutlardan birini seçin.");
                    printMenu();
                }
            }
        }
    }

    private Password GeneratePassword(int length) {
        final StringBuilder pass = new StringBuilder("");

        final int alphabetLength = alphabet.getAlphabet().length();

        int max = alphabetLength - 1;
        int min = 0;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * range) + min;
            pass.append(alphabet.getAlphabet().charAt(index));
        }

        return new Password(pass.toString());
    }

    private void printUsefulInfo() {
        System.out.println();
        System.out.println("İzin veriliyorsa minimum 8 veya daha fazla karakterden oluşan şifre uzunluğu kullanın");
        System.out.println("İzin veriliyorsa küçük ve büyük harf alfabetik karakterleri, sayıları ve simgeleri ekleyin");
        System.out.println("Mümkün olduğunda rastgele şifreler oluşturun");
        System.out.println("Aynı şifreyi iki kez kullanmaktan kaçının (örneğin birden fazla kullanıcı hesabında ve/veya yazılım sisteminde)");
        System.out.println("Karakter tekrarlarından, klavye kalıplarından, sözlük sözcüklerinden, ," +
                "\nKullanıcı adları, akraba veya evcil hayvan adları, romantik bağlantılar (güncel veya geçmiş) " +
                "ve biyografik bilgiler (ör. kimlik numaraları, ataların adları veya tarihleri) gibi harf veya rakam dizilerinden kaçının.");
    }

    private void requestPassword() {
        boolean IncludeUpper = false;
        boolean IncludeLower = false;
        boolean IncludeNum = false;
        boolean IncludeSym = false;

        boolean correctParams;

        System.out.println();
        System.out.println("Merhaba, şifre oluşturucuya hoş geldiniz."
                + " Aşağıdaki sorular Evet veya Hayır'a göre cevaplayınız. \n");

        do {
            String input;
            correctParams = false;

            do {
                System.out.println("Küçük harflerin \"abcd...\" kullanılmasını ister misiniz? ");
                input = keyboard.next();
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("evet") && !input.equalsIgnoreCase("hayır"));

            if (isInclude(input)) IncludeLower = true;

            do {
                System.out.println("Büyük harflerin \"ABCD...\" kullanılmasını istiyor musunuz? ");
                input = keyboard.next();
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("evet") && !input.equalsIgnoreCase("hayır"));

            if (isInclude(input)) IncludeUpper = true;

            do {
                System.out.println("\"1234...\" Numaralarının kullanılmasını istiyor musunuz?");
                input = keyboard.next();
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("evet") && !input.equalsIgnoreCase("hayır"));

            if (isInclude(input)) IncludeNum = true;

            do {
                System.out.println("\"!@#$...\" Sembollerinin kullanılmasını istiyor musunuz? ");
                input = keyboard.next();
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("evet") && !input.equalsIgnoreCase("hayır"));

            if (isInclude(input)) IncludeSym = true;

            //No Pool Selected
            if (!IncludeUpper && !IncludeLower && !IncludeNum && !IncludeSym) {
                System.out.println("Oluşturmak için hiçbir karakter seçmediniz " +
                        "şifreniz, yanıtlarınızdan en az biri Evet olmalıdır!!\n");
                correctParams = true;
            }

        } while (correctParams);

        System.out.println("Harika! Şimdi şifrenin uzunluğunu girin:");
        int length = keyboard.nextInt();

        final Generator generator = new Generator(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
        final Password password = generator.GeneratePassword(length);

        System.err.println("Oluşturulan şifreniz -> " + password);
    }

    private boolean isInclude(String Input) {
        if (Input.equalsIgnoreCase("evet")) {
            return true;
        }
        else {
            return false;
        }
    }

    private void PasswordRequestError(String i) {
        if (!i.equalsIgnoreCase("evet") && !i.equalsIgnoreCase("hayır")) {
            System.out.println("Yanlış karakter girdiniz tekrar üzerinden geçelim. \n");
        }
    }

    private void checkPassword() {
        String input;

        System.out.print("\nŞifrenizi giriniz :");
        input = keyboard.next();

        final Password p = new Password(input);

        System.out.println(p.calculateScore());
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1 - Şifre Oluşturucu");
        System.out.println("2 - Şifre Uzunluk Kontrolü");
        System.out.println("3 - Kullanışlı bilgi");
        System.out.println("4 - Çıkış");
        System.out.print("Seçim:");
    }

    private void printQuitMessage() {
        System.out.println("Programı kapatıyorum hoşçakalın!");
    }
}
