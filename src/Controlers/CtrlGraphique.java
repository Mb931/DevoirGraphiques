package Controlers;

import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    public HashMap<String, Double> GetAvgAgeParSal()
    {
        HashMap<String, Double> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT ageEmp , AVG(salaireEmp) from employe GROUP BY ageEmp;");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(String.valueOf(rs.getInt("ageEmp")),rs.getDouble("AVG(salaireEmp)"));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }
    public HashMap<Integer, Double> GetPourcSexe()
    {
        HashMap<Integer, Double> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("Select sexe,(100)-(53) as femme,round(COUNT(sexe)*100/(Select COUNT(numEmp) from employe ),0) as homme from employe where sexe= \"homme\"");
            rs = ps.executeQuery();
            while(rs.next())
            {
                datas.put(rs.getInt("femme"), rs.getDouble("homme"));
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    public HashMap<Integer,String[]> GetMontantParMagasin()
    {
        HashMap<Integer,String[]> datas = new HashMap();
        try {
            cnx = ConnexionBDD.getCnx();
            ps = cnx.prepareStatement("SELECT nomMagasin,montant,nomSemestre from vente;");
            rs = ps.executeQuery();
            int i = 1;
            while(rs.next())
            {
                datas.put(i, new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }







}
