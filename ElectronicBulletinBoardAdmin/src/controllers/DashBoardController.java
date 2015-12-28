/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class DashBoardController implements Initializable {

    @FXML
    AnchorPane postperdayPane;
    @FXML
    AnchorPane feedsPostedpane;
    @FXML
    AnchorPane taskAllocatedBBpane;

    @FXML
    PieChart chart;

    public void postPerDayChart() throws SQLException {
        ResultSet rs = Database.executeQuery("select publish_date, count(*) as count\n"
                + "from (select publish_date from electronic_bulletin_board.contentimage union all\n"
                + "      select publish_date from electronic_bulletin_board.admincontentimage\n"
                + "     ) t\n"
                + "group by publish_date;");
        ResultSet rs2 = Database.executeQuery("select publish_date, count(*) as count\n"
                + "from (select publish_date from electronic_bulletin_board.contentheadline union all\n"
                + "      select publish_date from electronic_bulletin_board.admincontentheadline\n"
                + "     ) t\n"
                + "group by publish_date;");
        ResultSet rs3 = Database.executeQuery("select publish_date, count(*) as count\n"
                + "from (select publish_date from electronic_bulletin_board.contenttext union all\n"
                + "      select publish_date from electronic_bulletin_board.admincontenttext\n"
                + "     ) t\n"
                + "group by publish_date;");
        /**
         * for line chart
         */
        //for category located in x axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        //for number located in y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of post");
        //creating line chart empty currently
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle("Post Published per day");
        lineChart.setPrefSize(650, 500);
        lineChart.setLayoutX(66);
        lineChart.setLayoutY(19);

        ObservableList<XYChart.Series<String, Integer>> answer = FXCollections.observableArrayList();

        //add as many lines as u like
        XYChart.Series<String, Integer> ImageContentSeries = new XYChart.Series<String, Integer>();
        XYChart.Series<String, Integer> headlineContentSeries = new XYChart.Series<String, Integer>();
        XYChart.Series<String, Integer> textContentSeries = new XYChart.Series<String, Integer>();

        ImageContentSeries.setName("Content Type: Image");   //shows which is what
        headlineContentSeries.setName("Content Type: Headline");   //shows which is what
        textContentSeries.setName("Content Type: Text");   //shows which is what
        //  cSeries.setName("C");

        //adding into series first
        while (rs.next()) {
            Date date = rs.getDate("publish_date");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String text = df.format(date);
            ImageContentSeries.getData().add(new XYChart.Data(text, rs.getInt("count")));
        }
        while (rs2.next()) {
            Date date = rs2.getDate("publish_date");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String text = df.format(date);
            headlineContentSeries.getData().add(new XYChart.Data(text, rs2.getInt("count")));
        }
        while (rs3.next()) {
            Date date = rs3.getDate("publish_date");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String text = df.format(date);
            textContentSeries.getData().add(new XYChart.Data(text, rs3.getInt("count")));
        }

        answer.addAll(ImageContentSeries, headlineContentSeries, textContentSeries);

        lineChart.setData(answer);  //gets data overhere to populate
        postperdayPane.getChildren().add(lineChart);

    }

    public void totalFeedPostedChart() throws SQLException {
        ResultSet rs = Database.executeQuery("SELECT count(*) as count FROM electronic_bulletin_board.contentimage;");
        ResultSet rs2 = Database.executeQuery("SELECT count(*) as count FROM electronic_bulletin_board.contentheadline;");
        ResultSet rs3 = Database.executeQuery("SELECT count(*) as count FROM electronic_bulletin_board.contenttext;");
        ResultSet rs4 = Database.executeQuery("SELECT count(*) as count FROM electronic_bulletin_board.admincontentimage;");
        ResultSet rs5 = Database.executeQuery("SELECT count(*) as count FROM electronic_bulletin_board.admincontentheadline;");
        ResultSet rs6 = Database.executeQuery("SELECT count(*) as count FROM electronic_bulletin_board.admincontenttext;");

        /**
         * for bar chart
         */
        //for category located in x axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Feeders");
        //for number located in y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Feeds");
        //creating bar chart empty currently
        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Total Feeds Posted");
        bc.setPrefSize(400, 485);
        bc.setLayoutX(66);
        bc.setLayoutY(19);

        XYChart.Series imagecontent = new XYChart.Series();
        imagecontent.setName("Image Content");
        while (rs.next()) {
            imagecontent.getData().add(new XYChart.Data("Content Feeder", rs.getInt("count")));
        }
        while (rs4.next()) {
            imagecontent.getData().add(new XYChart.Data("Administrator", rs4.getInt("count")));
        }
        XYChart.Series headlinecontent = new XYChart.Series();
        headlinecontent.setName("Headline Content");
        while (rs2.next()) {
            headlinecontent.getData().add(new XYChart.Data("Content Feeder", rs2.getInt("count")));
        }
        while (rs5.next()) {
            headlinecontent.getData().add(new XYChart.Data("Administrator", rs5.getInt("count")));
        }

        XYChart.Series textcontent = new XYChart.Series();
        textcontent.setName("Text Content");
        while (rs3.next()) {
            textcontent.getData().add(new XYChart.Data("Content Feeder", rs3.getInt("count")));
        }
        while (rs6.next()) {
            textcontent.getData().add(new XYChart.Data("Administrator", rs6.getInt("count")));
        }

        bc.getData().addAll(imagecontent, headlinecontent, textcontent);

        feedsPostedpane.getChildren().add(bc);

    }

    public void bulletinboardTaskallocation() throws SQLException {
        chart.setTitle("Posts Allocated for each Bulletin Board");
        ResultSet rs = Database.executeQuery("select idnoticeboard, count(*) as count\n"
                + "                from (select idnoticeboard from electronic_bulletin_board.noticeboard_content union all\n"
                + "                select idnoticeboard from electronic_bulletin_board.noticeboard_admincontent\n"
                + "                ) t\n"
                + "                group by idnoticeboard;");

        while (rs.next()) {
            ResultSet rs2 = Database.executeQuery("SELECT noticeboardname FROM electronic_bulletin_board.noticeboard where idnoticeboard = " + rs.getInt("idnoticeboard"));
            while (rs2.next()) {
                chart.getData().add(new PieChart.Data(rs2.getString("noticeboardname"), rs.getInt("count")));
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            postPerDayChart();
            totalFeedPostedChart();
            bulletinboardTaskallocation();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
