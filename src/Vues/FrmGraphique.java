package Vues;

import Controlers.CtrlGraphique;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                ////////Graph 1////////////////////////////////////////////////////////////////////////////////
                DefaultCategoryDataset donnees = new DefaultCategoryDataset();
                String age;
                Double salaire;

                // Remplir le dataSet à partir des données provenant de la HashMap
                for (String valeur : ctrlGraphique.GetAvgAgeParSal().keySet())
                {
                    salaire = ctrlGraphique.GetAvgAgeParSal().get(valeur);
                    age = valeur;
                    donnees.setValue(salaire,"",age);
                }

                // Créer le graphique avec ses options
                JFreeChart chart1 = ChartFactory.createLineChart(
                        "Moyenne des salaires par age",
                        "Age",
                        "Salaire",
                        donnees,
                        PlotOrientation.VERTICAL,false, true, false);

                // le graphique sera dans un JPanel
                ChartFrame demograph =new ChartFrame("", chart1);
                demograph.setVisible(true);
                ChartPanel graph = new ChartPanel(chart1);

                // Ajout du graphique dans le JPanel
                pnlGraph1.add(graph);
                //Mettre à jour le JPanel
                pnlGraph1.validate();

                ////////Graph 2////////////////////////////////////////////////////////////////////////////////
                DefaultPieDataset dataset = new DefaultPieDataset( );
                Double pourFemme;
                Double pourHomme;
                for (Integer valeur : ctrlGraphique.GetPourcSexe().keySet())
                {
                    pourHomme = ctrlGraphique.GetPourcSexe().get(valeur);
                    pourFemme = Double.valueOf(valeur);

                    dataset.setValue(pourFemme,pourHomme);
                }
                chart1 = ChartFactory.createRingChart("Pourcentage de homme et femme", dataset, true, false, false);
                RingPlot plot = (RingPlot) chart1.getPlot();
                plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
                plot.setSectionDepth(0.5);
                ChartPanel graph2 = new ChartPanel(chart1);
                pnlGraph2.add(graph2);
                pnlGraph2.validate();
                ////////Graph 4////////////////////////////////////////////////////////////////////////////////
                        donnees = new DefaultCategoryDataset();

                        for (Map.Entry valeur : ctrlGraphique.GetMontantParMagasin().entrySet())
                        {
                            Integer montant = Integer.parseInt(((String[])valeur.getValue())[1].toString());
                            String nomMagasin = ((String[])valeur.getValue())[0].toString();
                            String nomSemestre = ((String[])valeur.getValue())[2].toString();
                            //donnees.setValue(prix,nomAction,nomTrader);
                            donnees.setValue(montant,nomMagasin,nomSemestre);
                        }

                        chart1 = ChartFactory.createBarChart(
                                "Montant des ventes par magasin",
                                "Magasins",
                                "Montant des ventes",
                                donnees,
                                PlotOrientation.VERTICAL,
                                true, true, false);
                        // le graphique sera dans un JPanel
                         ChartFrame demograph4 =new ChartFrame("", chart1);
                         demograph4.setVisible(true);
                        ChartPanel graph4 = new ChartPanel(chart1);

                // Ajout du graphique dans le JPanel
                pnlGraph4.add(graph4);
                //Mettre à jour le JPanel
                pnlGraph4.validate();

                    }

        });


    }
}
