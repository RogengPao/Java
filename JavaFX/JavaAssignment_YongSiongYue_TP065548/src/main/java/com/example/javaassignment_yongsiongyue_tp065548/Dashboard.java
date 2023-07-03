package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import Rooms_and_Booking.HotelRooms;
import Rooms_and_Booking.HotelRoomsData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    public LineChart<String, Number> occupancyLineChart;
    @FXML
    public PieChart floor1Chart, floor2Chart, toatalFloorChart;
    @FXML
    public ListView<HotelBookings> listViewCheckOut;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(HotelBookingData.getInstance().getHotelBookings().size());


        int[] dayCounts = new int[7]; // Array to store the counts for each day

        for (HotelBookings booking : HotelBookingData.getInstance().getHotelBookings()) {
            LocalDate startDate = LocalDate.parse(booking.getStartDate());
            LocalDate endDate = LocalDate.parse(booking.getEndDate());

            for (int i = 0; i < 7; i++) {
                LocalDate currentDate = LocalDate.now().minusDays(i);
                if (currentDate.isEqual(startDate) || currentDate.isEqual(endDate) || (currentDate.isAfter(startDate) && currentDate.isBefore(endDate))) {
                    dayCounts[i] += 1;
                }
            }
        }

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Rooms Occupied in the last 7 days");

        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().minusDays(6).toString(), dayCounts[6]));
        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().minusDays(5).toString(), dayCounts[5]));
        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().minusDays(4).toString(), dayCounts[4]));
        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().minusDays(3).toString(), dayCounts[3]));
        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().minusDays(2).toString(), dayCounts[2]));
        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().minusDays(1).toString(), dayCounts[1]));
        dataSeries.getData().add(new XYChart.Data<>(LocalDate.now().toString(), dayCounts[0]));
        occupancyLineChart.getData().add(dataSeries);



        int availableRoomsFloorTotal = 0, availableRoomsFloor1 = 0, availableRoomsFloor2 = 0;

        for (HotelRooms i : HotelRoomsData.getInstance().getHotelRooms()) {
            if (i.getAvailability().equals("Available")) {
                availableRoomsFloorTotal += 1;

                if (Integer.parseInt(i.getRoomNumber().split("-")[1]) <= 10) {
                    availableRoomsFloor1 += 1;
                } else {
                    availableRoomsFloor2 += 1;
                }
            }
        }

        ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList(
                new PieChart.Data("Available", availableRoomsFloor1),
                new PieChart.Data("Unavailable", 10 - availableRoomsFloor1)
        );

        ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(
                new PieChart.Data("Available", availableRoomsFloor2),
                new PieChart.Data("Unavailable", 10 - availableRoomsFloor2)
        );

        ObservableList<PieChart.Data> pieChartTotal = FXCollections.observableArrayList(
                new PieChart.Data("Available", availableRoomsFloorTotal),
                new PieChart.Data("Unavailable", 20 - availableRoomsFloorTotal)
        );
        Platform.runLater(() -> {
            pieChartData1.get(0).getNode().setStyle("-fx-pie-color: #48b7ae;");
            pieChartData1.get(1).getNode().setStyle("-fx-pie-color: #02a2d3;");

            pieChartData2.get(0).getNode().setStyle("-fx-pie-color: #48b7ae;");
            pieChartData2.get(1).getNode().setStyle("-fx-pie-color: #02a2d3;");

            pieChartTotal.get(0).getNode().setStyle("-fx-pie-color: #48b7ae;");
            pieChartTotal.get(1).getNode().setStyle("-fx-pie-color: #02a2d3;");
        });

        floor1Chart.setData(pieChartData1);
        floor2Chart.setData(pieChartData2);
        toatalFloorChart.setData(pieChartTotal);
        listViewCheckOut.setItems(HotelBookingData.getInstance().getHotelBookings().filtered(booking -> booking.getStatus().equals("Checked In")));
    }
}
