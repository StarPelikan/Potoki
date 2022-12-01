import java.io.*;

import static java.lang.Integer.parseInt;

public class Basket implements Serializable {
    protected String[] fruitName;
    protected int[] price;
    protected int[] fruitAmount;


    public Basket(String[] fruitNumber, int[] price) {
        this.fruitName = fruitNumber;
        this.price = price;
        this.fruitAmount = new int[fruitNumber.length];
    }

    public Basket(String[] fruitNumber, int[] price, int[] fruitAmount) {
        this.fruitName = fruitNumber;
        this.price = price;
        this.fruitAmount = fruitAmount;
    }

    public void addToCart(int fruitNumber, int amount) {
        fruitAmount[fruitNumber] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        int sumFruit = 0;
        for (int i = 0; i < fruitName.length; i++) {
            if (fruitAmount[i] != 0) {
                System.out.println(fruitName[i] + " " + fruitAmount[i] + " шт., " + price[i] + " руб., " + (fruitAmount[i] * price[i]) + " рублей в сумме.");
                sumFruit += fruitAmount[i] * price[i];
            }
        }
        System.out.println("Итого в корзине: " + sumFruit + " рублей.");
    }

    public void saveBin(File file){
        try (FileOutputStream out = new FileOutputStream(file);
             ObjectOutputStream objO = new ObjectOutputStream(out)){
            objO.writeObject(this);
        } catch (Exception sav) {
            System.out.println( sav.getMessage());
        }
    }
    public static Basket loadFromBinFile (File file) throws IOException,ClassNotFoundException{
        FileInputStream inp =new FileInputStream(file);
        ObjectInputStream objI =new ObjectInputStream(inp);
        return(Basket) objI.readObject();
    }

//    public void saveTxt(File textFile) throws IOException {
//        try (PrintWriter out = new PrintWriter(textFile)) {
//            for (String st : fruitName) {
//                out.write(st + ",");
//            }
//            out.write("\n");
//            for (int pr : price) {
//                out.write(pr + ",");
//            }
//            out.write("\n");
//            for (int amount : fruitAmount) {
//                out.write(amount + ",");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public static Basket loadFromTxtFile(File textFile) {
//        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
//            String fruitName = br.readLine();
//            String price = br.readLine();
//            String fruitAmount = br.readLine();
//
//            String[] fruitStr = fruitName.split(",");
//
//            String[] priceStr = price.split(",");
//            int[] pricesInt = new int[priceStr.length];
//            for (int i = 0; i < pricesInt.length; i++) {
//                pricesInt[i] = Integer.parseInt(priceStr[i]);
//            }
//
//            String[] amountStr = fruitAmount.split(",");
//            int[] amountInt = new int[amountStr.length];
//            for (int i = 0; i < amountInt.length; i++) {
//                amountInt[i] = Integer.parseInt(amountStr[i]);
//            }
//            return new Basket(fruitStr, pricesInt, amountInt);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}



