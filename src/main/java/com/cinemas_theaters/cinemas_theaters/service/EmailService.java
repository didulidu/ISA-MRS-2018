package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendAdminActivation(RegisteredUser admin, String token) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();
        String activationLink = "https://theatres.herokuapp.com/admin/registration/"+token;

        mail.setTo(admin.getEmail());
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Activate your account");
        mail.setText("Follow the lin below to proceed with registration: " + "\n\n" + activationLink);

        mailSender.send(mail);
    }

    public void sendBidRejected(RegisteredUser user, Bid bid) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Your bid has been rejected");
        mail.setText("Your bid for item " + bid.getItem().getName() + " has been rejected.");

        mailSender.send(mail);
    }

    public void sendBidAccepted(RegisteredUser user, Bid bid) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Your bid has been accepted");
        mail.setText("Your bid for item " + bid.getItem().getName() + " has been accepted!");

        mailSender.send(mail);
    }

    public void sendOfferAccepted(RegisteredUser user, Item item) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Your item has been accepted");
        mail.setText("Your item " + item.getName() + " has been accepted!");

        mailSender.send(mail);
    }

    public void sendOfferRejected(RegisteredUser user, Item item) throws MailException{
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(user.getEmail());
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Your item has been rejected");
        mail.setText("Your item " + item.getName() + " has been rejected!");

        mailSender.send(mail);
    }

    public  void sendUserActivation(RegisteredUser registeredUser, String token) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();

        String activationLink = "https://theatres.herokuapp.com/login.html?activate="+token;

        mail.setTo("isamrstest@gmail.com");
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Activate your account");
        mail.setText("Follow the link below to proceed with registration:" + "\n\n" +  activationLink);

        mailSender.send(mail);
    }

    public void sendReservedTicketInfo(RegisteredUser registeredUser, Reservation reservation) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();

        String seats = "";

        for(Ticket ticket: reservation.getTickets()){
            seats += "Row: " + ticket.getSeat().getChairRow()+ ", Column: " + ticket.getSeat().getChairNumber() + " \n\t";
        }

        mail.setTo("isamrstest@gmail.com");
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("Thank your for reserving a ticket for " + reservation.getShowTitle());
        mail.setText("Reservation info: \n\n" +
                     "Theatre name: " + reservation.getProjection().getShow().getTheatre().getName() + "\n" +
                     "Show name: " + reservation.getProjection().getShow().getTitle() +  "\n" +
                     "Projection date: " + reservation.getProjectionDate() + "\n" +
                     "Hall: " + reservation.getProjection().getHall().getName() + "\n" +
                     "Seat: " + seats
        );

        mailSender.send(mail);
    }

    public void sendInvitationInfo (RegisteredUser registeredUser, Invitation invitation) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();

        String invitationLink = "https://theatres.herokuapp.com/registeredUserInvitations.html";

        mail.setTo("isamrstest@gmail.com");
        mail.setFrom("isamrs2018@gmail.com");
        mail.setSubject("You have an invitation from " + invitation.getInvitationSender().getName() + " " + invitation.getInvitationSender().getLastname()
                + " for " + invitation.getReservation().getProjection().getShow().getTitle());
        mail.setText("Invitation info: \n\n" +
                "Theatre name: " + invitation.getReservation().getProjection().getShow().getTheatre().getName() + "\n" +
                "Show name: " + invitation.getReservation().getProjection().getShow().getTitle() +  "\n" +
                "Projection date: " + invitation.getReservation().getProjectionDate() + "\n" +
                "Hall: " + invitation.getReservation().getProjection().getHall().getName() + "\n\n" +
                "You can accept or decline the invitation via the link below \n\n" + invitationLink
        );

        mailSender.send(mail);
    }

}
