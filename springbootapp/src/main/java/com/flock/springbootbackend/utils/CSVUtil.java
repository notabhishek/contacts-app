package com.flock.springbootbackend.utils;

import com.flock.springbootbackend.model.Contact;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static String TYPE = "text/csv";
    static String[] HEADERS = {"Name", "Email", "Phone", "Address"};

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Contact> csvToContacts(InputStream is) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                                            .withIgnoreHeaderCase().withTrim());
            List<Contact> contacts = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord : csvRecords) {
                Contact contact = new Contact();
                contact.setName(csvRecord.get("Name"));
                contact.setEmail(csvRecord.get("Email"));
                contact.setPhone(csvRecord.get("Phone"));
                contact.setAddress(csvRecord.get("Address"));

                contacts.add(contact);
            }
            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV File: " + e.getMessage());
        }
    }
}
