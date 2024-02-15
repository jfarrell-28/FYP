package util.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class Members {
    public static void main(String[] args) {
        readCSV("C:\\Users\\Jobby\\Desktop\\College\\Final Year Project\\TripAdvisorDataset\\member.csv");
    }

    private static void readCSV(String fileName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            String[] nextLine;

            try {
                while ((nextLine = reader.readNext()) != null) {
                    // Process the member.csv data here
                    for (String value : nextLine) {
                        System.out.print(value + " ");
                    }
                    System.out.println();
                }
            } catch (CsvValidationException e) {
                e.printStackTrace(); // Handle the CsvValidationException
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
