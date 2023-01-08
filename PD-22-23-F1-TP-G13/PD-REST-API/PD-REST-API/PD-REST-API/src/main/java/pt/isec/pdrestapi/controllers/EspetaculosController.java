package pt.isec.pdrestapi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("espetaculos")
public class EspetaculosController {

    @GetMapping("{filtro}")
    public ResponseEntity<String> getEspetaculos(@PathVariable("filtro")  String filter,
                                                 @RequestParam("datainicio") String datainicio,
                                                 @RequestParam("datafim") String datafim)
    {
        Connection dbConn;
        try {
            //dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
            dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");

            Statement statement = dbConn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM espetaculo");
            StringBuilder sb = new StringBuilder();
            String dataEspetaculoAux;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicioEspetaculo = simpleDateFormat1.parse(datainicio);
            Date dataFimEspetaculo = simpleDateFormat1.parse(datafim);
            sb.append("Espetaculos:");
            while (resultSet.next()){
                dataEspetaculoAux = resultSet.getString(4);
                Date dataEspetaculo = simpleDateFormat.parse(dataEspetaculoAux);
                if(dataEspetaculo.getTime() >= dataInicioEspetaculo.getTime() && dataEspetaculo.getTime() <= dataFimEspetaculo.getTime()){
                    sb.append("\nEspetaculo: ID="+resultSet.getString(1) + " Descricao=" + resultSet.getString(2) +
                            " Tipo=" + resultSet.getString(3) + " Data=" + resultSet.getString(4)
                            + " Duracao=" + resultSet.getString(5) + " Local=" + resultSet.getString(6)
                            + " Localidade=" + resultSet.getString(7) + " País=" + resultSet.getString(8)
                            + " Classificacao Etária=" + resultSet.getString(9));
                }
            }
            statement.close();
            dbConn.close();
            return ResponseEntity.ok().body(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping()
    public ResponseEntity<String> getEspetaculos()
    {
        Connection dbConn;
        try {
           // dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
            dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");

            Statement statement = dbConn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM espetaculo");
            StringBuilder sb = new StringBuilder();
            sb.append("Espetaculos:");
            while (resultSet.next()){
                    sb.append("\nEspetaculo: ID="+resultSet.getString(1) + " Descricao=" + resultSet.getString(2) +
                            " Tipo=" + resultSet.getString(3) + " Data=" + resultSet.getString(4)
                            + " Duracao=" + resultSet.getString(5) + " Local=" + resultSet.getString(6)
                            + " Localidade=" + resultSet.getString(7) + " País=" + resultSet.getString(8)
                            + " Classificacao Etária=" + resultSet.getString(9));
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
