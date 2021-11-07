package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FXMLController {

        @FXML
        private TableView<ToDoItem> tableView;

        @FXML
        private TableColumn<ToDoItem, String> nameCol;

        @FXML
        private TableColumn<ToDoItem, String> descriptionCol;

        @FXML
        private TableColumn<ToDoItem, String> dateCol;

        @FXML
        private TableColumn<ToDoItem, String> completedCol;

        @FXML
        private TextField nameField;

        @FXML
        private TextField descriptionField;

        @FXML
        private TextField dateField;

        @FXML
        private TextField isCompleted;

        @FXML
        private TextField fileField;

        @FXML
        private Button saveButton;

        @FXML
        private Button loadButton;

        @FXML
        private Text errorText;

        private final ObservableList<ToDoItem> data = FXCollections.observableArrayList();
        private final ObservableList<ToDoItem> completed = FXCollections.observableArrayList();
        private final ObservableList<ToDoItem> incomplete = FXCollections.observableArrayList();

        @FXML
        public void initialize(){
                nameCol.setCellValueFactory(
                        new PropertyValueFactory<>("name")
                );
                nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
                nameCol.setOnEditCommit(
                        t -> (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue())
                );
                descriptionCol.setCellValueFactory(
                        new PropertyValueFactory<>("description")
                );
                descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
                descriptionCol.setOnEditCommit(
                        t -> (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue())
                );
                dateCol.setCellValueFactory(
                        new PropertyValueFactory<>("date")
                );
                dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
                dateCol.setOnEditCommit(
                        t -> (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDate(t.getNewValue())
                );
                completedCol.setCellValueFactory(
                        new PropertyValueFactory<>("completed")
                );
                completedCol.setCellFactory(TextFieldTableCell.forTableColumn());
                completedCol.setOnEditCommit(
                        t -> (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCompleted(t.getNewValue())
                );
                tableView.setItems(data);
        }

        @FXML
        void addToList(ActionEvent event) {
            if(isPopulated(nameField.getText()) || isPopulated(descriptionField.getText())){
                    errorText.setText("Name and Description have to be populated.");
                    return;
            }
            else if(!dateField.getText().equals("") && isDateFormatValid(dateField.getText())){
                errorText.setText("Date should be in <YYYY-MM-DD> format.");
                return;
            }
            else if(!dateField.getText().equals("")){
                    String[] date = dateField.getText().split("-");
                    int year = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int day = Integer.parseInt(date[2]);

                    if(!isDateValid(year, month, day)){
                        errorText.setText("Date has to be a valid date!");
                        return;
                    }
            }
            if(isCompletedValid(isCompleted.getText())){
                    if(isCompleted.getText().equals("")){
                            addToObservableList(nameField.getText(), descriptionField.getText(), dateField.getText(), "N");
                    }
                    else{
                            addToObservableList(nameField.getText(), descriptionField.getText(), dateField.getText(), isCompleted.getText().toUpperCase(Locale.ROOT));
                    }
            }
            else{
                    errorText.setText("Completed can only be Y or N");
                    return;
            }
            errorText.setText("");
            nameField.clear();
            descriptionField.clear();
            dateField.clear();
            isCompleted.clear();
        }

        private boolean isCompletedValid(String completedInput) {
                return completedInput.equalsIgnoreCase("Y") ||
                        completedInput.equalsIgnoreCase("N") ||
                        completedInput.equals("");
        }

        public void addToObservableList(String name, String description, String date, String completed){
            data.add(new ToDoItem(name, description, date, completed));
        }

        public boolean isPopulated(String input){
                return input.equals("");
        }
        public boolean isDateFormatValid(String input){
                return !input.matches("[0-9]{4}[-][0-9]{2}[-][0-9]{2}");
        }
        public boolean isDateValid(int year, int month, int day) {
                boolean valid = true;
                month--;
                Calendar calendar = new GregorianCalendar(year, month, day);
                if (year != calendar.get(Calendar.YEAR)) {
                        valid = false;
                }
                else if (month != calendar.get(Calendar.MONTH)) valid = false;
                else if (day != calendar.get(Calendar.DAY_OF_MONTH)) valid = false;
                return valid;
        }

        @FXML
        void clearAll(ActionEvent event) {
                data.clear();
                completed.clear();
                incomplete.clear();
        }

        @FXML
        void loadList(ActionEvent event) {

        }

        @FXML
        void onlyComplete(ActionEvent event) {
                completed.clear();
                for (ToDoItem item: data) {
                        if(item.getCompleted().equalsIgnoreCase("Y")){
                                completed.add(item);
                        }
                }
                tableView.setItems(completed);
        }

        @FXML
        void onlyIncomplete(ActionEvent event) {
                incomplete.clear();
                for (ToDoItem item: data) {
                        if(item.getCompleted().equalsIgnoreCase("N")){
                                incomplete.add(item);
                        }
                }
                tableView.setItems(incomplete);
        }

        @FXML
        void removeList(ActionEvent event) {
                data.removeIf(item -> nameField.getText().equals(item.getName()));
                completed.removeIf(item -> nameField.getText().equals(item.getName()));
                incomplete.removeIf(item -> nameField.getText().equals(item.getName()));

        }

        @FXML
        void saveList(ActionEvent event) {

        }

        @FXML
        void showAll(ActionEvent event) {
                tableView.setItems(data);
        }

        public ObservableList<ToDoItem> getObservableList() {
                return data;
        }
}
