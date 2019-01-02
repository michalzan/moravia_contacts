package cz.zan.contacts.controller;

import cz.zan.contacts.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;

/**
 * @author Michal Záň
 */
public class DialogController {

    private static final String NEW_PHONE_NUMBER = "New phone number";
    @FXML
    public TextField dialogFirstName;
    @FXML
    public TextField dialogLastName;
    @FXML
    public TextField dialogAddress;
    @FXML
    public TextField dialogNote;
    @FXML
    public ListView<String> dialogPhones;

    private ObservableList<Contact> data;
    private Contact selection;

    @FXML
    public void initialize() {
        dialogPhones.setEditable(true);
        dialogPhones.setItems(FXCollections.observableArrayList(NEW_PHONE_NUMBER));

        dialogPhones.setCellFactory(TextFieldListCell.forListView());

        dialogPhones.setOnEditCommit(t -> {
            dialogPhones.getItems().set(t.getIndex(), t.getNewValue());
            if (t.getNewValue().isEmpty()) dialogPhones.getItems().remove(t.getIndex());
            if(dialogPhones.getSelectionModel().getSelectedIndices().contains(dialogPhones.getItems().size()-1))
                dialogPhones.getItems().add(NEW_PHONE_NUMBER);
        });
    }

    public void setData(ObservableList<Contact> data, Contact selection) {
        this.data = data;
        this.selection = selection;

        if (selection != null) {
            dialogFirstName.setText(selection.getFirstName());
            dialogLastName.setText(selection.getLastName());
            dialogAddress.setText(selection.getAddress());
            dialogNote.setText(selection.getNote());
            dialogPhones.setItems(selection.getPhoneNumbers());
            dialogPhones.getItems().add(NEW_PHONE_NUMBER);
        }
    }

    public void cancelDialog(ActionEvent actionEvent) {
        close(actionEvent);
    }

    public void confirmDialog(ActionEvent actionEvent) {
        Contact target = selection == null ? new Contact() : selection;

        target.setFirstName(dialogFirstName.getText());
        target.setLastName(dialogLastName.getText());
        target.setAddress(dialogAddress.getText());
        target.setNote(dialogNote.getText());
        dialogPhones.getItems().removeIf(NEW_PHONE_NUMBER::equals);
        target.setPhoneNumbers(dialogPhones.getItems());

        if (selection == null) {
            data.add(target);
        }
        close(actionEvent);
    }

    private void close(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
