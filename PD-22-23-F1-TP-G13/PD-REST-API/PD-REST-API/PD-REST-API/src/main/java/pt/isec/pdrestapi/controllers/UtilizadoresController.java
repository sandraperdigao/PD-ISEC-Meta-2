package pt.isec.pdrestapi.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pt.isec.pdrestapi.models.UserConfig;

import java.sql.*;

@RestController
@RequestMapping("utilizadores")
public class UtilizadoresController {

    @GetMapping
    public ResponseEntity<String> getUtilizadores(@AuthenticationPrincipal Jwt principal)
    {
        if(principal.getClaimAsString("scope").equals("ADMIN")){
            Connection dbConn;
            try {
                //dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
                dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");

                Statement statement = dbConn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM utilizador");
                StringBuilder sb = new StringBuilder();
                sb.append("Utilizadores:");
                while (resultSet.next()){
                    sb.append("\nUtilizador: ID="+resultSet.getString(1) + " Username=" + resultSet.getString(2));
                }
                statement.close();
                dbConn.close();
                return ResponseEntity.ok().body(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @PostMapping()
    public ResponseEntity<UserConfig> addUltilizador(@AuthenticationPrincipal Jwt principal, @RequestBody UserConfig config)
    {
        if(principal.getClaimAsString("scope").equals("ADMIN")) {
            Connection dbConn;
            try {
                dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");
                // dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
                Statement statement = dbConn.createStatement();
                statement.executeUpdate("INSERT INTO utilizador VALUES (NULL,'" + config.getUsername() + "','" + config.getName() +
                        "','" + config.getPassword() + "','" + "0" + "','" + "0" + "')");
                statement.close();
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok().body(config);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminaUtilizador(@AuthenticationPrincipal Jwt principal, @PathVariable("id") String id) {
        if (principal.getClaimAsString("scope").equals("ADMIN")) {
            Connection dbConn;
            try {
                //dbConn = DriverManager.getConnection("jdbc:sqlite:C:/Users/sandr/IdeaProjects/PD-22-23-F1-TP-G13/PD-22-23-F1-TP-G13/PD-helper.db");
                dbConn = DriverManager.getConnection("jdbc:sqlite:/Users/filipe/Desktop/5ano_1semestre/PD-22-23-F1-TP-G13/PD-helper.db");

                Statement statement = dbConn.createStatement();
                int retorno = statement.executeUpdate("DELETE FROM utilizador where id='" + id + "'");
                statement.close();
                dbConn.close();
                if(retorno == 0){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizador com o id:" + id + " não existe!");

                }
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok().body("Utilizador com o id:" + id + " foi removido com sucesso.");

        }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

}
