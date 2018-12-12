package controller.doctorControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import objects.OrderTicket;

public class PieChartController {

    @FXML
    private PieChart pieChart;

    @FXML
    void initialize() {

    }

    public void setInformation(ObservableList<OrderTicket> items) {
        int countMonday = 0;
        int countTuesday = 0;
        int countWednesday = 0;
        int countThursday = 0;
        int countFriday = 0;

        for(OrderTicket orderTicket : items){
            switch (orderTicket.getDay()){
                case "понедельник":
                    countMonday++;
                    break;
                case "вторник":
                    countTuesday++;
                    break;
                case "среда":
                    countWednesday++;
                    break;
                case "четверг":
                    countThursday++;
                    break;
                case "пятница":
                    countFriday++;
                    break;
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("понедельник", countMonday),
                new PieChart.Data("вторник", countTuesday),
                new PieChart.Data("среда", countWednesday),
                new PieChart.Data("четверг", countThursday),
                new PieChart.Data("пятница", countFriday)
        );
        pieChart.setData(pieChartData);
    }
}
