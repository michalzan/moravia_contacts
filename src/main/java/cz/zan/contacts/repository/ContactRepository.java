package cz.zan.contacts.repository;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import cz.zan.contacts.model.Contact;
import cz.zan.contacts.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Michal Záň
 */
public class ContactRepository {

    public boolean save (List<Contact> contacts, File file) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(file != null ? file : new File("data.xml"), new Contacts(contacts));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Contact> importFile(File file) {
        XmlMapper xmlMapper = new XmlMapper();
        Contacts contacts = null;
        try {
            String xml = inputStreamToString(new FileInputStream(file));
            contacts = xmlMapper.readValue(xml, Contacts.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contacts != null ? contacts.getContacts() : null;
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
