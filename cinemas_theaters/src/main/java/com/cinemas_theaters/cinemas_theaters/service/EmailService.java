package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.JwtUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Reservation;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public  void sendUserActivation(RegisteredUser registeredUser, String token) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();


        String activationLink = "http://localhost:8080/login.html?activate="+token;

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

}
