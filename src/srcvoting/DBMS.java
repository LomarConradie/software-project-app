/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srcvoting;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Louis
 */
public class DBMS {
    
    String[] canNum;
    String[] canName;
    String[] canSurname;
    String[] canDep;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    
    public DBMS() {
        displayAllPersons();
    }

    //Louis la Grange
    private void openDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/srcvoting", "root", "");
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "err: " + e.toString());
            loadToErrorLog(e);
        }
    }

    //Louis la Grange
    private void displayAllPersons() {
        openDBConnection();
        try {
            rs = stmt.executeQuery("SELECT * FROM person");
            
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    System.out.println(rs.getMetaData().getColumnName(i + 1) + "\t" + rs.getString(i + 1));
                }
            }
            System.out.println("===END===");
            con.close();
        } catch (SQLException ex) {
            System.out.println("" + ex);
            loadToErrorLog(ex);
        }
    }

    //Louis La Grange
    public String[] getTopCandidatesForDepartment(String department) {
        openDBConnection();
        
        String[] candidate = new String[9];
        Arrays.fill(candidate, "No Votes");
        try {
            rs = stmt.executeQuery("SELECT DISTINCT candidate.C_Name, candidate.C_Surname, candidate.C_CellNumber, candidate.C_Email, vote.C_CandidateNumber, vote.Department, COUNT(vote.C_CandidateNumber), (COUNT(vote.C_CandidateNumber)* 100 / (SELECT COUNT(*) FROM vote WHERE Department = '" + department + "')) AS perc, C_Bio FROM vote, candidate WHERE department = '" + department + "' AND vote.C_CandidateNumber = candidate.C_CandidateNumber GROUP BY vote.C_CandidateNumber ORDER BY perc");
            
            while (rs.next()) {
                
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //System.out.println(rs.getMetaData().getColumnName(i + 1) + "\t" + rs.getString(i + 1));
                    candidate[i] = rs.getString(i + 1);
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("" + ex);
            loadToErrorLog(ex);
        }
        return candidate;
    }
    //Johann Bekker

    /**
     *
     * @param studentNumber
     * @param password
     * @return
     */
    public boolean login(String studentNumber, String password) {
        openDBConnection();
        try {
            //searches database for entered details
            String q = "SELECT * FROM person WHERE P_StudentNumber='" + studentNumber + "'"
                    + "AND P_Password='" + password + "'";
            rs = stmt.executeQuery(q);
            
            if (rs.next()) {
                
                return true;
                //next page if the login attempt is successful
                //voting screen or results screen

            } else {
                JOptionPane.showMessageDialog(null, "Your student number or password is incorrect. Please try again.");
            }
            con.close();
        } catch (HeadlessException | NumberFormatException | SQLException i) {
            loadToErrorLog(i);
            System.err.println("Exception caught!");
            System.err.println(i.getMessage());
            JOptionPane.showMessageDialog(null, "Ensure that you enter your details are correct!");
        }
        return false;
    }

    //Johann Bekker
    public boolean addNewStudent(String studentNumber, String name, String surname, String cellNumber, String email, String password) {
        openDBConnection();
        try {
            
            String q = "SELECT * FROM person WHERE P_StudentNumber='" + studentNumber + "'";
            rs = stmt.executeQuery(q);

            //checks if student already has an account
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "You already have an account!");
                //ensures that the password is entered correctly
            } else { //enters student details into the database
                String sqlu = "INSERT INTO person "
                        + "(P_StudentNumber,P_Name,P_Surname,P_CellNumber,P_Email,P_Password)"
                        + "VALUES ('" + studentNumber + "','"
                        + name + "','"
                        + surname + "','"
                        + cellNumber + "','"
                        + email + "','"
                        + password + "') ";
                stmt.execute(sqlu);
                con.close();
                return true;
            }
        } catch (HeadlessException | SQLException | NumberFormatException i) {
            loadToErrorLog(i);
            System.err.println("Exception caught!");
            System.err.println(i.getMessage());
            JOptionPane.showMessageDialog(null, "Ensure that you entered your details correctly!");
        }
        return false;
    }
    
    public boolean addNewCandidate(String candidateNumber, String name, String surname, String cellNumber, String email, String department, String bio) {
        openDBConnection();
        try {
            
            String q = "SELECT * FROM candidate WHERE C_CandidateNumber='" + candidateNumber + "'";
            rs = stmt.executeQuery(q);

            //checks if student already has an account
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Candidate Already registered!");

                //ensures that the password is entered correctly
            } else { //enters student details into the database
                String sqlu = "INSERT INTO candidate "
                        + "(C_CandidateNumber,C_Name,C_Surname,C_CellNumber,C_Email,C_Department,C_Bio)"
                        + "VALUES ('" + candidateNumber + "','"
                        + name + "','"
                        + surname + "','"
                        + cellNumber + "','"
                        + email + "','"
                        + department + "','"
                        + bio + "') ";
                stmt.execute(sqlu);
                con.close();
                return true;
            }
            
        } catch (HeadlessException | SQLException | NumberFormatException i) {
            loadToErrorLog(i);
            System.err.println("Exception caught!");
            System.err.println(i.getMessage());
            //JOptionPane.showMessageDialog(null, "Ensure that you entered your details correctly!");
        }
        return false;
    }
    
    public void copyFileUsingStream(File source, String candidateNum) {
        InputStream is = null;
        OutputStream os = null;
        File destination = new File("assets/candidate/" + candidateNum + ".jpg");
        //&& !destination.isDirectory()
        if (destination.exists()) {
            // do something
            int option = JOptionPane.showConfirmDialog(null, "A candidate image already exists for this user, do you want to overwrite it?", "Overwrite?", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                try {
                    is = new FileInputStream(source);
                    os = new FileOutputStream(destination);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
                    loadToErrorLog(ex);
                } catch (IOException ex) {
                    Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
                    loadToErrorLog(ex);
                } finally {
                    try {
                        is.close();
                        os.close();
                    } catch (IOException ex) {
                        Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
                        loadToErrorLog(ex);
                    }
                }
            }
        } else {
            try {
                is = new FileInputStream(source);
                os = new FileOutputStream(destination);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
                loadToErrorLog(ex);
            } catch (IOException ex) {
                Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
                loadToErrorLog(ex);
            } finally {
                try {
                    is.close();
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
                    loadToErrorLog(ex);
                }
            }
        }
        
    }

    //Louis la Grange
    public boolean isAdmin(String studentNumber) {
        openDBConnection();
        try {
            
            String q = "SELECT * FROM person WHERE P_StudentNumber='" + studentNumber + "' AND P_Admin = 1";
            rs = stmt.executeQuery(q);

            //checks if person has an admin account
            if (rs.next()) {
                con.close();
                return true;
            }
            
        } catch (HeadlessException | SQLException | NumberFormatException i) {
            loadToErrorLog(i);
            System.err.println("Exception caught!");
            System.err.println(i.getMessage());
        }
        return false;
    }

    //Erick Lochner 
    //Gets the number of values in the candidate table. 
    public int getCount() {
        //Creates integer that will store the amount of entries in the database.  
        int count = 0;
        //Try/catch statement to retrieve entries from database. 
        try {
            //Creates a statement variable so that a query can be created. 
            Statement getCount = con.createStatement();
            //The query is received in the resultset. 
            ResultSet result = getCount.executeQuery("SELECT COUNT(C_CandidateNumber) FROM candidate");
            while (result.next()) {
                count = Integer.parseInt(result.getString(1));
            }
            //Catch statement is entered when a error occurs. 
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loadToErrorLog(e);
        }
        return count;
        
    }
//Erick Lochner 
    //Gets the information from the database to return to the frontend class. 

    public void getDetails() {
        //Calls method to open the database connection. 
        openDBConnection();
        //Recieves the size of the array from the getCount method. 
        int arraySize = getCount();
        //Declares the size of the arrays. 
        canNum = new String[arraySize];
        canName = new String[arraySize];
        canSurname = new String[arraySize];
        canDep = new String[arraySize];
        int teller = 0;
        //Try/catch statement for when dat is retreived from the database. 
        try {
            //Creates a statement variable so that a query can be created. 
            Statement canInfo = con.createStatement();
            //The query is received in the resultset. 
            ResultSet result = canInfo.executeQuery("SELECT C_CandidateNumber,C_Name,C_Surname,C_Department FROM candidate");
            //While loop to iterate through the results recieved. 
            while (result.next()) {
                //Data is moved from the resultset to the arrays. 
                canNum[teller] = result.getString(1);
                System.out.println("DB test" + canNum[teller]);
                canName[teller] = result.getString(2);
                canSurname[teller] = result.getString(3);
                canDep[teller] = result.getString(4);
                teller++;
            }
            //Catch statement is entered when a error occurs. 
        } catch (Exception e) {
            System.out.println(e + "testing data");
            loadToErrorLog(e);
        }
        
    }
//Erick Lochner
//Method to return the arrays to the main class. 

    public String[] getNumArray() {
        return canNum;
    }
//Erick Lochner
//Method to return the arrays to the main class. 

    public String[] getNameArray() {
        return canName;
    }
//Erick Lochner
//Method to return the arrays to the main class. 

    public String[] getSurnameArray() {
        return canSurname;
    }
//Erick Lochner
//Method to return the arrays to the main class. 

    public String[] getDepArray() {
        return canDep;
    }
//Erick Lochner
    //Metohd that pdates the votes table. 

    public boolean updateVotes(String studentNum, String canNum, String department) {
        //Calls the method to open the database connection.  
        openDBConnection();
        //Try/catch to receive data from the database. 
        try {
            //Creates a statement variable so that a update can be executed. 
            Statement updateVote = con.createStatement();
            //Statement to update votes is executed. 
            updateVote.executeUpdate("INSERT INTO vote (P_StudentNumber,C_CandidateNumber,Department) VALUES ('" + studentNum + "','" + canNum + "','" + "" + department + "')");
            //Closes the connection to the database. 
            con.close();
            //Returns true if the update was successfully executed. 
            return true;
            //Catch statement is entered when a error occurs. 
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loadToErrorLog(e);
            if (e.getMessage().contains("Duplicate entry")) {
            }
            //Returns false if the update was not successfully executed. 
            return false;
        }
        //  return flag;
    }
//Erick Lochner
    //Updates the vote field in the student table. 

    public boolean updateVoteField(String studentNum) {
        //Calls the method to open the database connection.  
        openDBConnection();
        //Try/catch to receive data from the database. 
        try {
            //Creates a statement variable so that a update can be executed. 
            Statement updateVoteField = con.createStatement();
            //Statement is executed. 
            updateVoteField.executeUpdate("UPDATE person SET P_Voted = '1' WHERE P_StudentNumber LIKE '" + studentNum + "'");
            //Closes the connection to the database.
            con.close();
            //Returns true if the update was successfully executed. 
            return true;
            //Catch statement is entered when a error occurs.
        } catch (Exception e) {
            loadToErrorLog(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
            //Returns false if the update was not successfully executed. 
            return false;
        }
        
    }

    //Louis la Grange
    public boolean hasVoted(String studentNumber) {
        openDBConnection();
        try {
            
            String q = "SELECT P_Voted FROM person WHERE P_StudentNumber='" + studentNumber + "' AND P_Voted = 1";
            rs = stmt.executeQuery(q);

            //checks if person has already voted
            if (rs.next()) {
                con.close();
                return true;
            }
        } catch (HeadlessException | SQLException | NumberFormatException i) {
            System.err.println("Exception caught! Probably not voted yet");
            System.err.println(i.getMessage());
        }
        return false;
    }
    
    private void loadToErrorLog(Exception ex) {
        BufferedWriter bw = null;
        
        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
            bw.write("Error date:" + LocalDateTime.now() + " - Error:" + ex);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        } // end try/catch/finally
    }
    
    private void loadToErrorLog(SQLException ex) {
        BufferedWriter bw = null;
        
        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
            bw.write("Error date:" + LocalDateTime.now() + " - Error:" + ex);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        } // end try/catch/finally
    }
    
    private void loadToErrorLog(IOException ex) {
        BufferedWriter bw = null;
        
        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
            bw.write("Error date:" + LocalDateTime.now() + " - Error:" + ex);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        } // end try/catch/finally
    }
    
    private void loadToErrorLog(FileNotFoundException ex) {
        BufferedWriter bw = null;
        
        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
            bw.write("Error date:" + LocalDateTime.now() + " - Error:" + ex);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        } // end try/catch/finally
    }
    
    public String bio;//public variable for biography value 

    public String Biography(String candidate) {//Biography funciton returning bio from dbms as string variable
        //Calls method to open the database connection. 
        openDBConnection();
        //Try/catch statement for when dat is retreived from the database. 
        try {
            //Creates a statement variable so that a query can be created. 
            Statement canInfo = con.createStatement();
            //Query to get biography from DB and compare candidate number
            ResultSet result = canInfo.executeQuery("SELECT C_Bio FROM candidate Where C_CandidateNumber = '" + candidate + "'");
            //Next result form query 
            result.next();
            bio = result.getString(1);
            //Catch statement is entered when a error occurs. 
        } catch (Exception e) {
            System.out.println(e + "testing data");
            loadToErrorLog(e);
        }
        return bio;//returning bio string variable
    }
    
    public boolean resetVotes() {
        openDBConnection();
        try {
            
            String q = "DELETE FROM vote";
            stmt.execute(q);
            if (resetVotedFieldForPeople()) {
                con.close();
                return true;
            }
            
        } catch (HeadlessException | SQLException | NumberFormatException i) {
            loadToErrorLog(i);
            System.err.println("Exception caught!");
            System.err.println(i.getMessage());
        }
        return false;
    }
    
    public boolean deleteAllCandidates() {
        if (resetVotes()) {
            openDBConnection();
            try {
                
                String q = "DELETE FROM candidate";
                stmt.execute(q);
                if (resetVotedFieldForPeople()) {
                    con.close();
                    return true;
                }
                
            } catch (HeadlessException | SQLException | NumberFormatException i) {
                loadToErrorLog(i);
                System.err.println("Exception caught!");
                System.err.println(i.getMessage());
            }
        }
        return false;
    }
    
    public boolean resetVotedFieldForPeople() {
        openDBConnection();
        try {
            
            String q = "UPDATE person SET P_Voted = 0";
            stmt.execute(q);
            
            return true;
            
        } catch (HeadlessException | SQLException | NumberFormatException i) {
            loadToErrorLog(i);
            System.err.println("Exception caught!");
            System.err.println(i.getMessage());
        }
        return false;
    }
    
}
