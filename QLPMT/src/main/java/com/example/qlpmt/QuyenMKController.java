package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Date;


public class QuyenMKController  implements Initializable {
    @FXML
    private ImageView exit;
    @FXML
    private ImageView minimize;
    @FXML
    private Button quenmk;
    @FXML
    private MFXTextField username;
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            // Get the current stage
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            // Close the stage
            stage.close();
        });


        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });
        quenmk.setOnMouseClicked(event -> {
            // Get the current stage
//            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//            // Close the stage
//            stage.close();
//
//            // Get the email from the text field
//            String email = username.getText();
//
//            // Connect to the database
//            try {
//                Connection connection = DBConnection.getConnection();
//                String sql = "SELECT * FROM TaiKhoan WHERE Email = ?";
//                PreparedStatement statement = connection.prepareStatement(sql);
//
//
//                statement.setString(1, email);
//                ResultSet rs = statement.executeQuery();
//
//                // Check if a user with the given email exists
//                try
//                {
//                    if (rs.next()) {
//                        // Generate a new random password
//                        String newPassword ="123456";
//
//                        // Update the user's password in the database
//                        String sq2 = "UPDATE TaiKhoan SET mk = ? WHERE email = ?";
//                        PreparedStatement updateStmt = connection.prepareStatement(sq2);
//                            updateStmt.setString(1, newPassword);
//                            updateStmt.setString(2, email);
//                            updateStmt.executeUpdate();
//
//
//                        // Send the new account information to the user's email
//                        sendNewAccountInfo(email, newPassword);
//
//                    } else {
//                        // Handle the case where no user with the given email exists
//                        System.out.println("No user with the given email exists.");
//                    }
//                }
//                catch(SQLException e)
//                {
//                    e.printStackTrace();
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
            sendNewAccountInfo("tranquyen2132004@gmail.com", "123456");
        });
    }
    private void sendNewAccountInfo(String email, String newPassword) {
        final String fromEmail = "tranquyen2132004@gmail.com"; //requires valid gmail id
        final String password = "Quyen@213"; // correct password for gmail id
        final String toEmail = "tranquyen2132004@gmail.com"; // can be any email id

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        sendEmail(session, toEmail,"SSLEmail Testing Subject", "SSLEmail Testing Body");
    }

    private static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
