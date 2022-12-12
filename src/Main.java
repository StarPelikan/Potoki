import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] fruits = {"Яблоки", "Груши", "Персик"};
        int[] prices = {85, 100, 200};
        int fruitNumber = 0;
        int productCount = 0;


        Basket basket = new Basket(fruits, prices);
        File logFile = new File("log.csv");
        File basketFile = new File("basket.bin");
        File jsonFile = new File("basket.json");

        ClientLog clientLog = new ClientLog();

        if (jsonFile.exists()) {
            System.out.println("загрузить вашу корзину?");
            scanner.nextLine().equals("");
            basket = Basket.loadFromJson(jsonFile);
            basket.printCart();
            System.out.println(" ");
        } else {
            basket = new Basket(fruits, prices);
        }


        System.out.println("Свеживы Завоз Фруктов, Налетай забирай дорогой ");
        for (int i = 0; i < fruits.length; i++) {
            System.out.println((i + 1) + ". " + fruits[i] + " " + prices[i] + " руб. за 1 кг.");
        }

        while (true) {

            System.out.println("Выберай, дорогой, фрукты сколько кг тебе  или end ");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                System.out.println("Уже уходишь? Заходи приходи, сладкий фрукт у меня ещё купи.");
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Брат ты ошибся с выбором.");
                continue;
            }
            String y = parts[1];
            String x = parts[0];
            try {
                fruitNumber = Integer.parseInt(x) - 1;
                productCount = Integer.parseInt(y);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Вы ввели не число. Попробуй еще");
                continue;
            }
            if (fruitNumber + 1 > fruits.length || fruitNumber < 0) {
                System.out.println("Ошибка, слишком большое число или отрицательное");
                continue;
            }
            if (productCount < 0) {
                System.out.println("ЭЭЭ, товарищ количество продуктов не может быть отрицательным.");
                continue;
            }


            basket.addToCart(fruitNumber, productCount);
            basket.saveJson(jsonFile);
            clientLog.log(fruitNumber, productCount);
            clientLog.exportAsCSV(logFile);
            basket.printCart();

        }

    }

}
