package baseline;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Gabriel Telleria
 */

import javafx.beans.property.SimpleStringProperty;

public class ToDoItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty date;
    private SimpleStringProperty completed;

    public ToDoItem(String name, String description, String date, String completed){
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.completed = new SimpleStringProperty(completed);
    }
    public String getName(){
        return name.get();
    }
    public String getDescription(){
        return description.get();
    }
    public String getDate(){
        return date.get();
    }
    public String getCompleted(){
        return completed.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String description){
        this.description.set(description);
    }

    public void setDate(String date){
        this.date.set(date);
    }

    public void setCompleted(String completed) {
        this.completed.set(completed);
    }
}
