package cz.zan.contacts.controller;

import cz.zan.contacts.repository.ContactRepository;
import cz.zan.contacts.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private ContactRepository repository = new ContactRepository();

    @FXML private TableView<Contact> tableView;

    private Contact selection;
    private ObservableList<Contact> data;

    @FXML
    public void initialize() {
        data = tableView.getItems();
        tableView.setOnMouseClicked((MouseEvent event) -> {

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int index = tableView.getSelectionModel().getSelectedIndex();
                if (tableView.getSelectionModel().getSelectedIndex() != -1) {
                    selection = tableView.getItems().get(index);
                }

                if (event.getClickCount() >= 2) {
                    try {
                        openDialog(selection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        File data = new File("data.xml");
        if (data.exists()) {
            _load(data, "Persisted data file is corrupted!");
        }
    }

    private void openDialog(Contact selection) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dialog.fxml"));
        Parent parent = fxmlLoader.load();
        DialogController dialogController = fxmlLoader.getController();
        dialogController.setData(data, selection);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        tableView.refresh();
    }

    @FXML
    public void addPerson(ActionEvent actionEvent) {
        try {
            openDialog(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        _save(null);
    }

    @FXML
    public void exportData(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select target file");
        File target = fileChooser.showSaveDialog(new Stage());
        if (target != null) _save(target);
    }

    @FXML
    public void importData(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select source file");
        File source = fileChooser.showOpenDialog(new Stage());
        if (source != null) {
            _load(source, "There was a problem loading selected file!");
        }
    }

    private void _save(File file) {
        List<Contact> contacts = new ArrayList<>(tableView.getItems());
        if (repository.save(contacts, file)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Content was saved.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Content was not saved!");
            alert.show();
        }
    }

    private void _load(File data, String message) {
        List<Contact> contacts = repository.importFile(data);
        if (contacts != null) {
            tableView.setItems(FXCollections.observableArrayList(contacts));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(message);
            alert.show();
        }
    }

    public void remove(ActionEvent actionEvent) {
        if (selection != null) {
            tableView.getItems().remove(selection);
        }
    }
}
