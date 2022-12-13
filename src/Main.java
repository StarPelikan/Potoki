import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] fruits = {"Яблоки", "Груши", "Персик"};
        int[] prices = {85, 100, 200};
        int fruitNumber = 0;
        int productCount = 0;

// Херабора  что вычитал, чтение файла
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");
        XPath xPath = XPathFactory.newInstance().newXPath();
        boolean isLoadEnabled = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(doc));
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(doc);
        boolean isSaveEnabled = Boolean.parseBoolean(xPath
                .compile("/config/save/enabled")
                .evaluate(doc));
        String saveFileName = xPath
                .compile("/config/save/fileName")
                .evaluate(doc);
        String saveFormat = xPath
                .compile("/config/save/format")
                .evaluate(doc);
        boolean isLogEnabled = Boolean.parseBoolean(xPath
                .compile("/config/log/enabled")
                .evaluate(doc));
        String logFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);


        Basket basket = new Basket(fruits, prices);
        File log = new File("client.csv");
        File textFile = new File("basket.bin");
        File jsonFile = new File("basket.json");

        ClientLog clientLog = new ClientLog();

//Загрузка из файла с помощью хераборы
//Разобрался почему не заходит в файл ... в настройках false, то бишь несмотреть, а создавайть новый....
        if (isLoadEnabled) {
            switch (loadFormat) {
                case "json":
                    basket.loadFromJson(new File(loadFileName));
                    System.out.println("загрузить вашу корзину из формата json ?");
                    scanner.nextLine().equals("");
                    basket = Basket.loadFromJson(jsonFile);
                    basket.printCart();
                    break;
                case "text":
                    basket.loadFromTxtFile(new File(loadFileName));
                    scanner.nextLine().equals("");
                    basket = Basket.loadFromTxtFile(textFile);
                    basket.printCart();
                    System.out.println(" ");
                    break;
            }
        } else {
            basket = new Basket(fruits, prices);
        }


//Вывод на экран и проверка
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
            //     Сохранение у формате через херабору
            if (isSaveEnabled) {
                switch (saveFormat) {
                    case "json":
                        basket.saveJson(new File(saveFileName));
                        break;
                    case "text":
                        basket.saveTxt(new File(saveFileName));
                        break;
                }
            }

            clientLog.log(fruitNumber, productCount);
            if (isLogEnabled) {
                clientLog.exportAsCSV(log);
            }
            basket.printCart();
        }
    }

}

