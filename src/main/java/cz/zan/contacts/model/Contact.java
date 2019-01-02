package cz.zan.contacts.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Michal Záň
 */
@JacksonXmlRootElement(localName = "contact")
public class Contact {

    private SimpleStringProperty id = new SimpleStringProperty();

    private SimpleStringProperty firstName = new SimpleStringProperty();

    private SimpleStringProperty lastName = new SimpleStringProperty();

    private SimpleStringProperty address = new SimpleStringProperty();

    @JacksonXmlProperty(localName = "phoneNumber")
    @JacksonXmlElementWrapper(localName = "phoneNumbers")
    private SimpleListProperty<String> phoneNumbers = new SimpleListProperty<>();

    private SimpleStringProperty note = new SimpleStringProperty();

    public Contact() {
        this.id.set(UUID.randomUUID().toString());
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public ObservableList<String> getPhoneNumbers() {
        return phoneNumbers.get();
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers.set(FXCollections.observableArrayList(phoneNumbers));
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
