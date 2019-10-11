package com.karimandco.admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p.radyna
 */
public class BDD {

    //Création des variables pour l'authentification à la BDD
    private String AdresseBDD;
    private String PortBDD;
    private String NomBDD;
    private String UtilisateurBDD;
    private String MdpBDD;

    public Connection connexion;

    //inscriptions de données dans les variables
    public BDD() {
        this.AdresseBDD = "www.cnadal.fr";
        this.PortBDD = "3306";
        this.NomBDD = "sio2_cv";
        this.UtilisateurBDD = "sio2_cv";
        this.MdpBDD = "formation2020";
    }

    public Connection getConnexion() {
        return connexion;
    }

    public void ConnexionBDD() {
        if (this.connexion == null) {
            try {
                String ChaineConnexion = "jdbc:mysql://" + this.AdresseBDD + ":" + this.PortBDD + "/" + this.NomBDD;
                this.connexion = (Connection) DriverManager.getConnection(ChaineConnexion, this.UtilisateurBDD, this.MdpBDD);
                if (this.connexion != null) {
                    System.out.println("Connecté à la bdd");
                } else {
                    System.out.println("Non connecté à la bdd");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.connexion = null;
        }
    }

    public String[][] SelectAll(String nomTable) {
        String[][] resultat = null;

        if (this.connexion != null) {
            Boolean error_connexion = false;
            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    int nbr_ligne = 0;
                    ResultSet res = maRequete.executeQuery("SELECT COUNT(*) FROM " + nomTable);
                    while (res.next()) {
                        nbr_ligne = res.getInt(1);
                    }

                    ResultSet lesTuples = maRequete.executeQuery("SELECT * FROM " + nomTable);
                    int nbr_colonne = lesTuples.getMetaData().getColumnCount() + 1;
                    int compteur_ligne = 0;

                    resultat = new String[nbr_ligne][nbr_colonne];

                    while (lesTuples.next()) {
                        for (int i = 1; i < nbr_colonne; i++) {
                            String col_name = lesTuples.getMetaData().getColumnName(i);

                            resultat[compteur_ligne][i] = lesTuples.getString(col_name);
                        }
                        compteur_ligne++;
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }
        return resultat;
    }

    public Boolean InsertFormInscription(String nom, String prenom, String identifiant, String courriel, String numeroTelephone, String dateNaissance, String mdp) {
        Boolean requeteOK = false;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();
                    if (maRequete.executeUpdate("INSERT INTO utilisateurs (nom, prenom, identifiant, courriel, num_telephone, date_de_naissance, mot_de_passe, photo) VALUES ('" + nom + "', '" + prenom + "', '" + identifiant + "', '" + courriel + "', '" + numeroTelephone + "', '" + dateNaissance + "', '" + mdp + "', '')") == 1) {
                        requeteOK = true;
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }
        return requeteOK;
    }

    public Boolean SelectFormConnexion(String identifiant, String mdp) {
        Boolean requeteOK = false;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    ResultSet lesTuples = maRequete.executeQuery("SELECT * FROM utilisateurs WHERE statut = 1 AND identifiant='" + identifiant + "' AND mot_de_passe='" + mdp + "'");
                    if (lesTuples.next()) {
                        requeteOK = true;
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }

        return requeteOK;
    }

    public Boolean SelectFormConnexionAdmin(String identifiant, String mdp) {
        Boolean requeteOK = false;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    ResultSet lesTuples = maRequete.executeQuery("SELECT * FROM utilisateurs WHERE statut = 1 AND identifiant='" + identifiant + "' AND mot_de_passe='" + mdp + "'");
                    if (lesTuples.next()) {
                        requeteOK = true;
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }

        return requeteOK;
    }

    public Boolean DeleteAllTableUtilisateurs() {
        Boolean requeteOK = false;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    if (maRequete.executeUpdate("DELETE FROM utilisateurs WHERE statut = 0") > 0) {
                        requeteOK = true;
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }
        return requeteOK;
    }

    public Boolean Delete(Integer id) {
        Boolean requeteOK = false;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    if (maRequete.executeUpdate("DELETE FROM utilisateurs WHERE statut = 0 AND id = '" + id + "'") > 0) {
                        requeteOK = true;
                    } else {
                        requeteOK = false;
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }

        return requeteOK;
    }

    public String[] SelectAllUpdate(Integer id) {
        String[] resultat = new String[10];

        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete_bis = this.connexion.createStatement();
                    ResultSet lesTuples_bis = maRequete_bis.executeQuery("SELECT * FROM utilisateurs WHERE id = '" + id + "'");

                    // on attend au max 1 Tuple !!!!!
                    if (lesTuples_bis.next()) {
                        Statement maRequete = this.connexion.createStatement();
                        ResultSet lesTuples = maRequete.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'utilisateurs'");

                        Integer i = 0;

                        while (lesTuples.next()) {
                            resultat[i] = lesTuples_bis.getString(lesTuples.getString("COLUMN_NAME"));
                            i++;
                        }
                    }
                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }
        return resultat;
    }

    public Boolean InsertFormUpdate(Integer id, String nom, String prenom, String identifiant, String courriel, String num_telephone, String date_de_naissance) {

        Boolean requeteOK = false;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    if (maRequete.executeUpdate("UPDATE utilisateurs SET nom = '" + nom + "', prenom = '" + prenom + "', identifiant = '" + identifiant + "', courriel = '" + courriel + "', num_telephone = '" + num_telephone + "', date_de_naissance = '" + date_de_naissance + "' WHERE id = '" + id + "'") > 0) {
                        requeteOK = true;
                    }

                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }

        return requeteOK;
    }

    /**
     * Cette méthode permet d'exécuter une requête SQL de type SELECT pour
     * recupérer la date depuis le serveur de base de données.
     *
     * @return String date
     */
    public String SelectTimeServer() {
        String resultat = null;
        if (this.connexion != null) {
            Boolean error_connexion = false;

            while (error_connexion == false) {
                try {
                    Statement maRequete = this.connexion.createStatement();

                    ResultSet lesTuples = maRequete.executeQuery("SELECT CURRENT_DATE");
                    if (lesTuples.next()) {
                        resultat = lesTuples.getString("CURRENT_DATE");
                    }

                    error_connexion = true;
                } catch (SQLException ex) {
                    if (ex.toString().contains("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure")) {
                        error_connexion = false;
                        this.connexion = null;
                        this.ConnexionBDD();
                    } else {
                        Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
                        error_connexion = true;
                    }
                }
            }
        }

        return resultat;
    }

}
