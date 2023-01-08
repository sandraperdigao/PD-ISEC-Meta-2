package pt.isec.pdrestapi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@RequestMapping("reservas")
public class ReservasController {

    @GetMapping
    public ResponseEntity<String> getReservasPagas(Authentication auth)
    {
        Connection dbConn;
        try {
            //dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
             dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");
            Statement statement1 = dbConn.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT id from UTILIZADOR where username= '" + auth.getName() + "'");
            int iduser = Integer.parseInt(resultSet.getString(1));


            Statement statement = dbConn.createStatement();
            ResultSet resultSet1 = statement.executeQuery("SELECT * from RESERVA where pago= " + 1 + " AND id_utilizador= " + iduser);
            StringBuilder sb = new StringBuilder();
            sb.append("Reservas Pagas:");
            while (resultSet1.next()){
                sb.append("\nReserva: ID="+resultSet1.getString(1) + " Data=" + resultSet1.getString(2) +
                        " ID do User=" + resultSet1.getString(4) + " ID do espetaculo=" + resultSet1.getString(5));
            }
            statement.close();
            dbConn.close();
            return ResponseEntity.ok().body(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pendente")
    public ResponseEntity<String> getReservasPorPagas(Authentication auth)
    {
        Connection dbConn;
        try {
            //dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
            dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");
            Statement statement1 = dbConn.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT id from UTILIZADOR where username= '" + auth.getName() + "'");
            int iduser = Integer.parseInt(resultSet.getString(1));


            Statement statement = dbConn.createStatement();
            ResultSet resultSet1 = statement.executeQuery("SELECT * from RESERVA where pago= " + 0 + " AND id_utilizador= " + iduser);
            StringBuilder sb = new StringBuilder();
            sb.append("Reservas por pagar:");
            while (resultSet1.next()){
                sb.append("\nReserva: ID="+resultSet1.getString(1) + " Data=" + resultSet1.getString(2) +
                        " ID do User=" + resultSet1.getString(4) + " ID do espetaculo=" + resultSet1.getString(5));
            }
            statement.close();
            dbConn.close();
            return ResponseEntity.ok().body(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
