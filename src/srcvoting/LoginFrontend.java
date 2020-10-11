/*
 *This is the Object used to create the Login screen Visually
 Coded by : Dennis Liebenberg
 Last edited : 4:58PM 22 August 2019
 */
package srcvoting;

import java.awt.Color;
import static java.awt.Color.white;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 * @author Dennis Liebenberg Student Number: QFJDGRL93
 */
class LoginFrontend extends JFrame {

    public LoginFrontend() {
        
        JFrame loginFrame = new JFrame();//Constructor of the login frame
        JPanel LoginPanel = new JPanel(); //Create the main panel forcomponents
        loginFrame.setMinimumSize(new Dimension(600, 600));
        //loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //    loginFrame.setResizable(false);

        //Loads pearson icon into icon Buffer
        BufferedImage icon = null;
        BufferedImage image = null;
        try {
            icon = ImageIO.read(new File("assets/img/pes.png"));
            loginFrame.setIconImage(icon);//Set icon to pearson image
            image = ImageIO.read(new File("assets/img/Pearson.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(SignupFrontend.class.getName()).log(Level.SEVERE, null, ex);
            loadToErrorLog(ex);
        }

        final BufferedImage finaleImage = image;
        final BufferedImage finaleIcon = icon;

        //Loads background into Buffer
        /**
         * The image gets Drawn by the paintComponent Every time the system
         * calls it This allows the image to be resized to the size of the frame
         * The Panel Background is created here as well
         */
        JPanel Background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int destinationWidth = loginFrame.getWidth();
                //Get the width of the frame currently
                int destinationHeight = loginFrame.getHeight();
                //Get the height of the frame currently
                //Draw image to the specified size
                g.drawImage(finaleImage, 0, 0, destinationWidth, destinationHeight, 0,
                        0, finaleImage.getWidth(null), finaleImage.getHeight(null), null);
            }
        };
        /**
         * The Background panel will use the GridBagLayout because its the best
         * LayoutManager to keep a component centered The main panel for the
         * components are added on to the background panel to keep it centered
         * Despite the forms size
         */
        Background.setLayout(new GridBagLayout());
        Background.add(LoginPanel);

        /**
         * The BoxLayout is used for the LoginPanel to ensure all the components
         * get placed centered underneath each other
         */
        LoginPanel.setLayout(new BoxLayout(LoginPanel, BoxLayout.Y_AXIS));
        LoginPanel.setBorder(new EmptyBorder(new Insets(50, 80, 50, 80)));
        //The empty border leaves a transparent margin without any associated drawing.
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //This ensures the frame closes
        loginFrame.setTitle("SRC VOTING LOGIN");//Title of the Login Frame
        loginFrame.setSize(500, 500);//The starting size of the Frame
        loginFrame.setLocationRelativeTo(null);
        //The form will open on the center of the screen

        /**
         * Components gets created
         */
        JLabel Title = new JLabel();
        JLabel lblStudentNum = new JLabel();
        JLabel lblPassword = new JLabel();
        JTextField StudentNum = new JTextField();
        JPasswordField Password = new JPasswordField();
        JButton Sign_in = new JButton();
        JButton Sign_up = new JButton();

        /**
         * The Components size gets initialized
         */
        //LoginPanel.setPreferredSize(new Dimension(500, 600));
        //StudentNum.setSize(20, 60);
        //Password.setSize(20, 60);
        //StudentNum.setMaximumSize(LoginPanel.getPreferredSize());
        //The components size change as the form size changes
        //Password.setMaximumSize(LoginPanel.getPreferredSize());
        Sign_in.setPreferredSize(new Dimension(200, 30));
        Sign_up.setPreferredSize(new Dimension(200, 30));
        lblStudentNum.setText("Student Number");
        lblPassword.setText("Password");
        Sign_in.setText("Sign In");
        Sign_up.setText("Sign Up");
        Password.setEchoChar('•');
        Title.setText("LOGIN");

        Color Pearson_Blue = new Color(0, 126, 162);

        Sign_in.setBackground(Pearson_Blue);
        Sign_in.setForeground(white);

        Sign_up.setBackground(Pearson_Blue);
        Sign_up.setForeground(white);

        Title.setForeground(Color.gray);
        Title.setFont(new Font("Arial", Font.BOLD, 30));//Change title style

        /**
         * Create a Border around the TextFields
         */
        /*StudentNum.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Password.setBorder(BorderFactory.createLineBorder(Color.black, 1));*/

        /**
         * Background color of the LoginPanel is set to white
         */
        LoginPanel.setBackground(Color.white);

        /**
         * The components are aligned in the center of the Panel at all times
         */
        Title.setAlignmentX(CENTER_ALIGNMENT);
        Title.setAlignmentY(CENTER_ALIGNMENT);
        lblStudentNum.setAlignmentY(CENTER_ALIGNMENT);
        lblStudentNum.setAlignmentX(CENTER_ALIGNMENT);
        lblPassword.setAlignmentY(CENTER_ALIGNMENT);
        lblPassword.setAlignmentX(CENTER_ALIGNMENT);

        StudentNum.setAlignmentX(CENTER_ALIGNMENT);
        StudentNum.setAlignmentY(CENTER_ALIGNMENT);
        Password.setAlignmentX(CENTER_ALIGNMENT);
        Password.setAlignmentY(CENTER_ALIGNMENT);
        Sign_in.setAlignmentX(CENTER_ALIGNMENT);
        Sign_in.setAlignmentY(CENTER_ALIGNMENT);
        Sign_up.setAlignmentX(CENTER_ALIGNMENT);
        Sign_up.setAlignmentY(CENTER_ALIGNMENT);

        /**
         * When the user Click on the Student Number TextField all text is
         * selected
         */
        
        Password.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Sign_in.doClick();
                }
            }
        });

        /**
         * The components Get added to the panel Between each component is a
         * "RigidArea" which is basically a space between them
         */
        LoginPanel.add(Title);
        LoginPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        LoginPanel.add(lblStudentNum);
        LoginPanel.add(Box.createRigidArea(new Dimension(10, 05)));
        LoginPanel.add(StudentNum);
        LoginPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        LoginPanel.add(lblPassword);
        LoginPanel.add(Box.createRigidArea(new Dimension(10, 05)));
        LoginPanel.add(Password);
        LoginPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        LoginPanel.add(Sign_in);
        LoginPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        LoginPanel.add(Sign_up);

        /**
         * The Background Panel is added into the Frame The Frame has been set
         * visible
         */
        loginFrame.add(Background);
        //     loginFrame.pack();
        loginFrame.setVisible(true); 

        Sign_in.addActionListener((e) -> {
            DBMS dbms = new DBMS();
            if (dbms.isAdmin(StudentNum.getText())) {
                //Checks if User is admin
                if (dbms.login(StudentNum.getText(), Password.getText())) {
                    //Validates user credentials & opens the Admin screen if successful
                    AdminFrontend af = new AdminFrontend();
                    loginFrame.dispose();
                }
            } else if (dbms.hasVoted(StudentNum.getText())) {
                //Validates user credentials & opens the Voting screen if successful
                JOptionPane.showMessageDialog(null, "You cannot Sign In, because you have already voted");
            } else if (dbms.login(StudentNum.getText(), Password.getText())) {
                //Validates user credentials & opens the Voting screen if successful
                VotingFrontend vf = new VotingFrontend(StudentNum.getText());
                loginFrame.dispose();
            }

        });

        Sign_up.addActionListener((e) -> {

            //this.dispose();
            StudentNum.setText("");
            Password.setText("");
            loginFrame.setFocusable(false);
            loginFrame.setEnabled(false);
            SignupFrontend su = new SignupFrontend(loginFrame);
            
        });

    }

    private void loadToErrorLog(IOException ex) 
    {
         BufferedWriter bw = null;

      try {
         // APPEND MODE SET HERE
         bw = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
	 bw.write("Error date:"+LocalDateTime.now()+" - Error:"+ex);
	 bw.newLine();
	 bw.flush();
      } catch (IOException ioe) {
	 ioe.printStackTrace();
      } finally {                       // always close the file
	 if (bw != null) try {
	    bw.close();
	 } catch (IOException ioe2) {
	    // just ignore it
	 }
      } // end try/catch/finally
    }
    
}
