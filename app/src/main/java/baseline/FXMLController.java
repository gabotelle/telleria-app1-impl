package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class FXMLController {

        @FXML
        private Button incompleteButton;

        @FXML
        private Button completeButton;

        @FXML
        private Button allItemsButton;

        @FXML
        private Button clearButton;

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
        private Button addButton;

        @FXML
        private Button removeButton;

        @FXML
        private TextField fileField;

        @FXML
        private Button saveButton;

        @FXML
        private Button loadButton;

        private final ObservableList<ToDoItem> data = FXCollections.observableArrayList();
        private final ObservableList<ToDoItem> completed = FXCollections.observableArrayList();
        private final ObservableList<ToDoItem> incomplete = FXCollections.observableArrayList();

        @FXML
        void addToList(ActionEvent event) {
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
                data.add(new ToDoItem(
                        nameField.getText(),
                        descriptionField.getText(),
                        dateField.getText(),
                        isCompleted.getText()
                ));
                nameField.clear();
                descriptionField.clear();
                dateField.clear();
                isCompleted.clear();
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

}
