
import Lucene.IndexDocuments;
import Lucene.StopWords;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String [] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new WindowsLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Display();

            }
        });
    }

}
