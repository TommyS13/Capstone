import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;


public class TranslatorGUI extends JFrame implements ActionListener{

    private HashMap<String,String> engTranslations;
    private HashMap<String,String> spanTranslations;
    JTextField englishText;
    JTextField spanishText;
    JButton transToSButton;
    JButton transToEButton;
    JButton clearBut;
    JTextField inputText;
    JButton addTran;
    JFrame frame;

    public TranslatorGUI() throws FileNotFoundException {

        frame = new JFrame("Translator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        JLabel engLabel = new JLabel("English: ");
        inputPanel.add(engLabel);
        englishText = new JTextField(50);
        inputPanel.add(englishText);
        englishText.addActionListener(this);
        JLabel spanLabel = new JLabel("Spanish: ");
        inputPanel.add(spanLabel);
        spanishText = new JTextField(50);
        inputPanel.add(spanishText);
        spanishText.addActionListener(this);
        frame.add(inputPanel,BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        transToSButton = new JButton("Translate to Spanish");
        transToSButton.addActionListener(this);
        buttonPanel.add(transToSButton);

        transToEButton = new JButton("Translate to English");
        transToEButton.addActionListener(this);
        buttonPanel.add(transToEButton);

        clearBut = new JButton("Clear");
        buttonPanel.add(clearBut);
        clearBut.addActionListener(this);
        frame.add(buttonPanel,BorderLayout.CENTER);

        JPanel transPanel = new JPanel();
        JLabel inputLabel = new JLabel("Add a translation to help out! (English,Spanish)");
        transPanel.add(inputLabel);


        inputText = new JTextField(50);
        transPanel.add(inputText);
        inputText.addActionListener(this);

        addTran = new JButton("Add Translation");
        transPanel.add(addTran);
        addTran.addActionListener(this);
        frame.add(transPanel,BorderLayout.SOUTH);

        engTranslations = loadEngTrans();
        spanTranslations = loadSpanTrans();
        frame.setTitle("Bienvenidos, Welcome!");
        frame.pack();
        frame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == transToSButton) {
            String spanishTrans = textTranslate(englishText.getText(), engTranslations);
            spanishText.setText(spanishTrans);
        }
        else if(e.getSource() == transToEButton){
            String englishTrans = textTranslate(spanishText.getText(), spanTranslations);
            englishText.setText(englishTrans);
        }
        else if(e.getSource() == clearBut){
            clearText();
        }else if(e.getSource() == addTran){
            addWords(inputText.getText());
        }
    }

    public void addWords(String text) {
        String[] arr = text.split(",");
        if(engTranslations.containsKey(arr[0])){
            JOptionPane.showMessageDialog(frame, "Translation already exists :)");
        }else{
            engTranslations.put(arr[0], arr[1]);
        }
    }

    public String textTranslate(String aString, HashMap aTranslation){
        String span = "";
        String text = aString.trim();
        text = text.toLowerCase();

        /*In the event that the string is a sentence, this will split the string when there is a space and
         * will put each word into an array.
         * We will then iterate over the array to then get the translation from aTranslation and then add it to the String span.
         */
        String[] words = text.split(" ");

        for (String word : words) {
            String temp;
            if (!aTranslation.containsKey(word)) {
                temp = word;
                temp = temp.toLowerCase();
                span += temp + " ";
            } else {
                temp = (String) aTranslation.get(word);
                span += temp + " ";
            }
        }
            return span;
    }

    public void clearText(){
        String clear = "";
        englishText.setText(clear);
        spanishText.setText(clear);
    }

    public HashMap<String,String> loadEngTrans(){
        HashMap<String, String> engTranslations = new HashMap<String, String>();

        try {
            //read CSV file
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("Dictionary.csv"), "UTF-8"));


            //parse each line of CSV file
            String line = reader.readLine();
            while (line != null) {
                //split line on comma
                String[] parts = line.split(",");

                //add translation to HashMap
                engTranslations.put(parts[0], parts[1]);

                //read next line
                line = reader.readLine();
            }

            //close reader
            reader.close();
        } catch (IOException e) {
            //print error message
            System.out.println("Error reading CSV file");
            e.printStackTrace();
        }

        //return HashMap of engTranslations
        return engTranslations;
    }

    public HashMap<String, String> loadSpanTrans() {
        HashMap<String, String> spanTranslations = new HashMap<String, String>();

        try {
            //read CSV file
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("Dictionary.csv"), "UTF-8"));


            //parse each line of CSV file
            String line = reader.readLine();
            while (line != null) {
                //split line on comma
                String[] parts = line.split(",");

                //add translation to HashMap
                spanTranslations.put(parts[1], parts[0]);

                //read next line
                line = reader.readLine();
            }

            //close reader
            reader.close();
        } catch (IOException e) {
            //print error message
            System.out.println("Error reading CSV file");
            e.printStackTrace();
        }

        //return HashMap of engTranslations
        return spanTranslations;
    }






    public static void main(String[] args) throws FileNotFoundException {
        TranslatorGUI gui = new TranslatorGUI();
    }
}
