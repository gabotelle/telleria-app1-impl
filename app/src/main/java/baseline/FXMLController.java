package baseline;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Gabriel Telleria
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

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
                clearAllList();
        }

        void clearAllList() {
                data.clear();
                completed.clear();
                incomplete.clear();
        }

        @FXML
        void loadList(ActionEvent event) {
                File file  = getFile();
                uploadSaveFile(file);
        }

        public void uploadSaveFile(File file) {
                if (file != null) {
                        try {
                                FileInputStream inputFile = new FileInputStream(file);
                                Scanner sc = new Scanner(inputFile);
                                clearAllList();
                                String[] input = sc.nextLine().split("&");
                                for(int i = 0; i < input.length; i += 4){
                                        if(isDateFormatValid(input[i+2])){
                                                addToObservableList(input[i], input[i+1], "", input[i+3]);
                                        }
                                        else {
                                                addToObservableList(input[i], input[i + 1], input[i + 2], input[i + 3]);
                                        }
                                }
                                sc.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                else{
                        errorText.setText("File does not exist");
                }
        }

        @FXML
        void userGuide(ActionEvent event){
                ClipboardContent link = new ClipboardContent();
                link.putString("https://ucf.zoom.us/rec/share/3RN3dPdA6zI5V6mYzqCAIN81bHnauB1hTrQSx_mvRdcWXF7lk91Yj8WOTrJXeVFg.hWRmoTXkK7yiueiW");
                Clipboard.getSystemClipboard().setContent(link);
                errorText.setText("Link copied to clipboard: https://ucf.zoom.us/rec/share/3RN3dPdA6zI5V6mYzqCAIN81bHnauB1hTrQSx_mvRdcWXF7lk91Yj8WOTrJXeVFg.hWRmoTXkK7yiueiW");
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
                File file  = getFile();
                writeSaveFile(file);
        }

        public void writeSaveFile(File file) {
                if (file != null) {
                        try {
                                PrintWriter writer;
                                writer = new PrintWriter(file);
                                StringBuilder saveText = new StringBuilder();
                                for (ToDoItem item: data) {
                                        saveText.append(item.getName()).append("&").append(item.getDescription())
                                                .append("&").append(item.getDate()).append("&")
                                                .append(item.getCompleted()).append("&");
                                }
                                writer.println(saveText);
                                writer.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                else{
                        errorText.setText("File does not exist");
                }
        }

        private File getFile(){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Open File");
                return chooser.showOpenDialog(new Stage());
        }



        @FXML
        void showAll(ActionEvent event) {
                tableView.setItems(data);
        }

        public ObservableList<ToDoItem> getObservableList() {
                return data;
        }

}
