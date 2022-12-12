import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    String log= "fruitNumber,amount\n";

    public  void log (int fruitNumber, int amount ) {
        log += String.format("%d,%d\n",fruitNumber,amount);
    }

    public void exportAsCSV(File txtFile) throws IOException {
        FileWriter writer = new FileWriter(txtFile);
        writer.write(log);
        writer.close();
    }

}
