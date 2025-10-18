package com.cab302.teachscope.models.services;

import com.cab302.teachscope.models.dao.FormDao;
import com.cab302.teachscope.models.dao.StudentDao;
import com.cab302.teachscope.models.entities.Student;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateReportsService {

    /**
     * The form DAO used by the service
     */
    private final FormDao formDao;

    /**
     * The student DAO used by the service
     */
    private final StudentDao studentDao;

    /**
     * Constructor
     *
     * @param formDao Form data access object.
     */
    public GenerateReportsService(FormDao formDao, StudentDao studentDao) {
        this.formDao = formDao;
        this.studentDao = studentDao;
    }

    public void createReport(String studentID, int term, int fromWeek, int toWeek) {
        // Check student ID is valid
        if (studentID == null || !studentID.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$")) {
            throw new IllegalArgumentException("Invalid form ID");
        }

        // Check term is valid
        if (term < 1 || term > 4) {
            throw new IllegalArgumentException("Term must be between 1-4");
        }

        // Check weeks are valid
        if (fromWeek < 0 || fromWeek > 12 || fromWeek > toWeek) {
            throw new IllegalArgumentException("Invalid term");
        }

        // Get student details to input into text areas
        Student student;
        try {
            student = studentDao.getStudent(studentID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Ensure student exists
        if (student == null) {
            System.err.println("No student found for report: " + studentID);
            return;
        }



        // Get data to display
        Map<String, Double> averageValues;
        Map<String, Double> totalAverageValues;
        Map<String, Object> additionalStats;
        try {
            averageValues = formDao.findAverageScoresForStudent(studentID, term, fromWeek, toWeek); // Student averages
            totalAverageValues = formDao.findGlobalAverageScores(term, fromWeek, toWeek); // Class averages
            additionalStats = formDao.findAverageAttendanceAndEmotionForStudent(studentID, term, fromWeek, toWeek); // Additional stats
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Create and populate dataset for individual student
        DefaultCategoryDataset averageScores = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : averageValues.entrySet()) {
            averageScores.addValue(entry.getValue(), "Average", entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1, entry.getKey().length() - 5).replaceAll("([a-z])([A-Z])", "$1 $2"));
        }

        // Create and popular dataset for class averages
        DefaultCategoryDataset classAverages = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : totalAverageValues.entrySet()) {
            classAverages.addValue(entry.getValue(), "Class Average", entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1, entry.getKey().length() - 5).replaceAll("([a-z])([A-Z])", "$1 $2"));
        }

        // Create bar graph for student
        JFreeChart barChart = ChartFactory.createBarChart(
                student.getFirstName() + "'s Average Scores",
                "Attribute",
                "Score",
                averageScores
        );

        // Get the plot to modify
        CategoryPlot plot = barChart.getCategoryPlot();

        // Set Y-axis range to maximum 2 and .5 increments
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 2.1);
        rangeAxis.setTickUnit(new NumberTickUnit(0.5));

        // Make bar names fit by rotating them
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4));

        // Style the graph to look closer to mockup
        // Fonts
        Font titleFont = new Font("Poppins", Font.BOLD, 24);
        Font labelFont = new Font("Poppins", Font.PLAIN, 16);
        Font tickFont = new Font("Poppins", Font.PLAIN, 16);

        barChart.getTitle().setFont(titleFont);
        domainAxis.setLabelFont(labelFont);
        domainAxis.setTickLabelFont(tickFont);
        plot.getRangeAxis().setLabelFont(labelFont);
        plot.getRangeAxis().setTickLabelFont(tickFont);

        // Colour
        BarRenderer barRenderer = getBarRenderer();
        plot.setRenderer(0, barRenderer);

        // Adjust bar width and spacing
        barRenderer.setItemMargin(0.05);
        domainAxis.setCategoryMargin(0.1);

        // Adjust other styling
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setShadowVisible(false);

        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setShadowVisible(false);
        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);
        barChart.setBackgroundPaint(Color.WHITE);

        // Add class average line to same graph rather than its own
        plot.setDataset(1, classAverages);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD); // This puts it in front of the bars
        plot.mapDatasetToRangeAxis(1, 0);
        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        lineRenderer.setSeriesPaint(0, Color.RED);
        lineRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(1, lineRenderer);

        // Create PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // Place text and images onto the PDF
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            BufferedImage barChartImage = barChart.createBufferedImage(1200, 500);
            PDImageXObject pdfBarChartImage = JPEGFactory.createFromImage(document, barChartImage, 1.0f);

            // Add Images
            // Logo
            PDImageXObject logo = PDImageXObject.createFromFile("src/main/resources/images/logo_teachscope.png", document);
            float aspect = (float) logo.getWidth() / logo.getHeight();
            float width = 100;
            float height = width / aspect;
            contentStream.drawImage(logo, 0, 700, width * 3, height * 3);

            // Supporting Image
//            PDImageXObject supportingImage = PDImageXObject.createFromFile("src/main/resources/images/login_picture.png", document);
//            float aspect2 = (float) supportingImage.getWidth() / supportingImage.getHeight();
//            float width2 = 100;
//            float height2 = width2 / aspect2;
//            contentStream.drawImage(supportingImage, 370, 650, width2 * (float) 2.2, height2 * (float) 2.2);

            // Graph
            contentStream.drawImage(pdfBarChartImage, 0, 360, 600, 250);

            // Text
            PDType0Font fontBold = PDType0Font.load(document, new File("src/main/resources/fonts/PlaypenSans-Bold.ttf"));
            PDType0Font fontRegular = PDType0Font.load(document, new File("src/main/resources/fonts/PlaypenSans-Regular.ttf"));
            Color navyBlue = new Color(0x24, 0x31, 0x81);

            // Title/Subtitle
            contentStream.beginText();
            contentStream.setFont(fontBold, 18); // font + size
            contentStream.setNonStrokingColor(navyBlue); // set text color
            contentStream.newLineAtOffset(75, 700); // x, y position
            contentStream.showText(student.getFirstName() + " " + student.getLastName() + "'s Report");
            contentStream.setFont(fontRegular, 18); // font + size
            contentStream.newLineAtOffset(0, -25); // x, y position
            contentStream.showText("Term 1 (Week 1 - Week 10)");

            // Additional Student Metrics
            // Attendance
            contentStream.setFont(fontBold, 16); // font + size
            contentStream.newLineAtOffset(-40, -360); // x, y position
            contentStream.showText("Attendance Rate:");

            contentStream.setFont(fontRegular, 14); // font + size
            contentStream.newLineAtOffset(150, 0); // x, y position
            contentStream.showText((double) additionalStats.get("avgAttendance") * 20 + "%");

            // Homework
            contentStream.setFont(fontBold, 16); // font + size
            contentStream.newLineAtOffset(70, 0); // x, y position
            contentStream.showText("Homework completion:");

            contentStream.setFont(fontRegular, 14); // font + size
            contentStream.newLineAtOffset(190, 0); // x, y position
            contentStream.showText("~" + (double) additionalStats.get("avgHomeworkDone") * 20 + "%");

            // Days Late
            contentStream.setFont(fontBold, 16); // font + size
            contentStream.newLineAtOffset(-410, -50); // x, y position
            contentStream.showText("Days Late:");

            contentStream.setFont(fontRegular, 14); // font + size
            contentStream.newLineAtOffset(90, 0); // x, y position
            contentStream.showText(additionalStats.get("totalDaysLate").toString());

            // Emotional State
            contentStream.setFont(fontBold, 16); // font + size
            contentStream.newLineAtOffset(130, 0); // x, y position
            contentStream.showText("Most Common Emotional State:");

            contentStream.setFont(fontRegular, 14); // font + size
            contentStream.newLineAtOffset(260, 0); // x, y position
            contentStream.showText(additionalStats.get("mostCommonEmotionalState").toString());

            // Teacher notes


            contentStream.endText();
        } catch (IOException e) {
            throw new IllegalArgumentException("Something went wrong...");
        }

        try {
            // Ensure 'pdfs' folder exists
            String userHome = System.getProperty("user.home");

            File pdfDir = new File(userHome, "/Documents/pdfs");
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }

            // Save the PDF
            String fileName = student.getFirstName() + "-" + student.getLastName() + "-Report.pdf";
            document.save(new File(pdfDir, fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateAll(int term, int fromWeek, int toWeek) {
        // Check term is valid
        if (term < 1 || term > 4) {
            throw new IllegalArgumentException("Term must be between 1-4");
        }

        // Check weeks are valid
        if (fromWeek < 0 || fromWeek > 12 || fromWeek > toWeek) {
            throw new IllegalArgumentException("Invalid term");
        }

        // Get students with forms within time range
        List<String> students;

        try {
            students = formDao.findStudentsInRange(term, fromWeek, toWeek);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // For each student in the list, call the report method
        for (String student : students) {
            createReport(student, term, fromWeek, toWeek);
        }
    }

    /**
     * Returns a bar renderer to colour columns as specified
     *
     * @return Bar Renderer
     */
    private static BarRenderer getBarRenderer() {
        Color[] colors = {
                new Color(108, 229, 232),
                new Color(65, 184, 213),
                new Color(45, 139, 186),
                new Color(47, 95, 152),
                new Color(69, 70, 146),
                new Color(134, 70, 156),
                new Color(188, 63, 141),
                new Color(238, 77, 128),
        };

        return new BarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                return colors[column % colors.length]; // color by bar (column)
            }
        };
    }
}
