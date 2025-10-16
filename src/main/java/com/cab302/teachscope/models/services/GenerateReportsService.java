package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.FormDao;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class GenerateReportsService {

    /**
     * The DAO used by the service
     */
    private final FormDao formDao;

    /**
     * Constructor
     * @param formDao Form data access object.
     */
    public GenerateReportsService(FormDao formDao) {this.formDao = formDao;}

    public void createReport(String studentID) {
        // Check student ID is valid

        // Get average scores for student
        Map<String, Double> averageValues;
        try {
            averageValues = formDao.findAverageScoresForStudent(studentID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Create and populate dataset
        DefaultCategoryDataset averageScores = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : averageValues.entrySet()) {
            averageScores.addValue(entry.getValue() + 1, "Score", entry.getKey().substring(0, entry.getKey().length() - 5));
        }

        // Create graph
        JFreeChart barChart = ChartFactory.createBarChart(
          "Student Average Scores",
          "Attribute",
          "Score",
          averageScores
        );

//        ChartPanel chartPanel = new ChartPanel(barChart);
//        JFrame frame = new JFrame();
//        frame.setSize(800, 600);
//        frame.setContentPane(chartPanel);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);


        // Create PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            BufferedImage barChartImage = barChart.createBufferedImage(1200, 300);
            PDImageXObject pdfBarChartImage = JPEGFactory.createFromImage(document, barChartImage, 1.0f);
            contentStream.drawImage(pdfBarChartImage, 30, 30, 500, 125);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            document.save("C:\\dev\\CAB302_TeachScope\\pdfs\\" + studentID + ".pdf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
