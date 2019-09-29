/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linenumber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author lenovo
 */
public class TgsCtrl {
    
    private LineNumberTugas view;
    private List<Integer> list = new ArrayList<>();
    
    public TgsCtrl (LineNumberTugas view){
        this.view = view;
        this.view.getjButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    proses();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TgsCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.view.getSaveBut().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        
    }
    
    private void proses() throws FileNotFoundException {
        JFileChooser loadFile = view.getLoadFile();
        StyledDocument doc = view.getjTextPane1().getStyledDocument();
//        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(view.getName()));
        if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(loadFile.getSelectedFile()));
                String data = null;
                doc.insertString(0, "", null);
                view.getjTextPane1().setText("");
                while ((data = reader.readLine()) != null) {
                    doc.insertString(doc.getLength(), data + "\n", null);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TgsCtrl.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException | BadLocationException ex) {
                Logger.getLogger(TgsCtrl.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        JOptionPane.showMessageDialog(null, "Jumlah line ", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                    } catch (IOException ex) {
                        Logger.getLogger(TgsCtrl.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void save() {
        JFileChooser loadFile = view.getLoadFile();
        if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
            BufferedWriter writer = null;
            try {
                String contents = view.getjTextPane1().getText();
                if (contents != null && !contents.isEmpty()) {
                    writer = new BufferedWriter(new FileWriter(loadFile.getSelectedFile()));
                    writer.write(contents);
                    JOptionPane.showMessageDialog(null, "Data anda berhasil tersimpan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TgsCtrl.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(TgsCtrl.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                        view.getjTextPane1().setText("");

                    } catch (IOException ex) {
                        Logger.getLogger(TgsCtrl.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
}
