package cz.zan.contacts.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * @author Michal Záň
 */
@JacksonXmlRootElement(localName = "contacts")
public class Contacts {

    @JacksonXmlProperty(localName = "contact")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Contact> contacts;

    public Contacts() {
    }

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
