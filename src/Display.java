import Lucene.IndexDocuments;
import Lucene.QueryParsing;
import Lucene.StopWords;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.queryparser.classic.ParseException;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Display extends JFrame implements ActionListener {
    private JTextField searchTextArea;
    private JEditorPane resultsField;
    private QueryParsing queryParsing;
    private JTextArea showContent;

    public Display() {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (size.getWidth() - 900) / 2, ((int) size.getHeight() - 700) / 2);
        //////////////////////////////////////////////////////
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(1000, 700);
        this.setResizable(false);
        SpringLayout startLayout;
        startLayout = new SpringLayout();
        JPanel content = new JPanel();
        getContentPane().add(content);

        content.setLayout(startLayout);
        content.setBackground(Color.CYAN);

        JButton stopWordsBtn = new JButton("Create stop words‌");
        stopWordsBtn.setActionCommand("SW");
        stopWordsBtn.addActionListener(this);

        startLayout.putConstraint(SpringLayout.WEST, stopWordsBtn, 400, SpringLayout.WEST, getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH, stopWordsBtn, 20, SpringLayout.NORTH, getContentPane());

        JButton indexBtn = new JButton("Create indexes");
        indexBtn.setActionCommand("IN");
        indexBtn.addActionListener(this);

        startLayout.putConstraint(SpringLayout.WEST, indexBtn, 200, SpringLayout.WEST, getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH, indexBtn, 20, SpringLayout.NORTH, getContentPane());


        searchTextArea = new JTextField();
        searchTextArea.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        searchTextArea.setColumns(20);
        startLayout.putConstraint(SpringLayout.WEST, searchTextArea, 400, SpringLayout.WEST, getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH, searchTextArea, 120, SpringLayout.NORTH, getContentPane());

        JButton searchBtn = new JButton("Search");
        searchBtn.setActionCommand("SE");
        searchBtn.addActionListener(this);

        startLayout.putConstraint(SpringLayout.WEST, searchBtn, 200, SpringLayout.WEST, getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH, searchBtn, 120, SpringLayout.NORTH, getContentPane());

        resultsField = new JEditorPane();

        resultsField.setFont(new Font("Serif",Font.PLAIN,20));
        resultsField.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        resultsField.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        resultsField.setEditable(false);
        resultsField.setPreferredSize(new Dimension(400,200));

        JScrollPane scroll = new JScrollPane(resultsField);

        startLayout.putConstraint(SpringLayout.WEST, scroll, 200, SpringLayout.WEST, getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH, scroll, 200, SpringLayout.NORTH, getContentPane());

        showContent = new JTextArea();
        showContent.setFont(new Font("Serif",Font.PLAIN,16));
        showContent.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        showContent.setColumns(40);
        showContent.setRows(10);
        showContent.setLineWrap(true);
        showContent.setWrapStyleWord(true);
        JScrollPane showScroll = new JScrollPane(showContent);
        startLayout.putConstraint(SpringLayout.WEST, showScroll, 200, SpringLayout.WEST, getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH, showScroll, 450, SpringLayout.NORTH, getContentPane());

        content.add(stopWordsBtn);
        content.add(indexBtn);
        content.add(searchTextArea);
        content.add(searchBtn);
        content.add(scroll);
        content.add(showScroll);

        this.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SW")) {
            //make stop words
            StopWords stopWords = new StopWords();
            try {
                stopWords.createStopWords();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("IN")) {
            //make indexes
            IndexDocuments indexDocuments = new IndexDocuments();
            try {
                indexDocuments.createDocumentIndecies();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("SE")) {
            //search and display
            String text = searchTextArea.getText();
            String url = "<html>";
            try {
                queryParsing = new QueryParsing(text, 8);
                for (int i = 0; i < queryParsing.docsInfo.size(); i++) {
                    url += "<a href=" + i + ">" + queryParsing.docsInfo.get(i).get("title") + "</a><br />";
                }
                url+="</html>";
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            System.out.println(url);
            resultsField.setText(url);
        }

        resultsField.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    String href = e.getSourceElement().getAttributes().getAttribute(HTML.Tag.A).toString();
                    href = href.replaceAll(" ","");
                    href = href.substring(5,href.length());
                    int id = Integer.parseInt(href);
                    showContent.setText(queryParsing.docsInfo.get(id).get("content").toString());
                }
            }
        });

    }
}
