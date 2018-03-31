package TDIMCO.gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by Thomas on 27-3-2018.
 */
public class FirstGui extends JFrame implements ActionListener, KeyListener, WindowListener, MouseListener{

    public static void main(String[] args) {
        new FirstGui().setVisible(true);
    }

    JLabel label;
    JTextArea textArea;
    JTextField textField;
    String s;
    String s2;

    public FirstGui() {
        super("TDIMCO APP");
        s="";
        s2 ="";

//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Dimension dim = tk.getScreenSize();
//        setSize(dim);
        setSize(400,200);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel thePanel = new JPanel();
        label = new JLabel("The Label");
        label.setToolTipText("Dawg waddup");
        thePanel.add(label);

        JButton button1 = new JButton("Send");
        button1.setToolTipText("Doesn't do anything either");
        button1.addActionListener(this);
        thePanel.add(button1);

        textField = new JTextField(s2, 15);
        textField.addKeyListener(this);
        thePanel.add(textField);

        textArea = new JTextArea(15, 15);
        textArea.addKeyListener(this);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fc.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }

        thePanel.add(fc);

        thePanel.add(textArea);

        add(thePanel);

        addWindowListener(this);

        addMouseListener(this);


//
//        JButton button = new JButton("ButtonEx1");
//        JButton button2 = new JButton("ButtonEx2");
//
//        button.addActionListener(this);
//        button2.addActionListener(this);
//
//        add(button);
//        add(button2);
//
//        label = new JLabel("");
//        add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();

        if(name.equals("Send")) {
            label.setText("Button 1 has been pressed");
        } else if (name.equals("ButtonEx2")) {
            label.setText("Button 2 has been pressed");
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            s += e.getKeyChar();
            System.out.println(s);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println(s);
            textArea.append(s + "\n");
            s = "";
            textField.setText("");
        }
        else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            System.out.println("BACKSPACE");
            s = s.substring(0, s.length()-1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        textArea.append("Mouse Panel Pos: " + e.getX() + " " + e.getY() + "\n");
        textArea.append("Mouse Screen Pos: " + e.getXOnScreen() + " " + e.getYOnScreen() + "\n");
        textArea.append("Mouse Button: " + e.getButton()  + "\n");
        textArea.append("Mouse Clicks: " + e.getClickCount()  + "\n");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
