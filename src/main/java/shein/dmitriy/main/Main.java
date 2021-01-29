package shein.dmitriy.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "tickets.json";

        String tmpData = new String(Files.readAllBytes(Paths.get(fileName)));

        Gson gson = new Gson();
        JSONObject tmpJsonObject = gson.fromJson(tmpData, JSONObject.class);

        ArrayList<Integer> minOnWay = new ArrayList<>();

        for (Object o: (ArrayList) tmpJsonObject.get("tickets")) {
            JsonObject tmpTicket = gson.toJsonTree(o).getAsJsonObject();
            Ticket ticket = gson.fromJson(tmpTicket, Ticket.class);

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yy");
            LocalDate departureDate = LocalDate.parse(ticket.getDepartureDate(), formatterDate);
            LocalDate arrivalDate = LocalDate.parse(ticket.getArrivalDate(), formatterDate);
            Period period = Period.between(departureDate, arrivalDate);

            String[] departureTime = ticket.getDepartureTime().split(":");
            String[] arrivalTime = ticket.getArrivalTime().split(":");

            minOnWay.add(60 * 24 * period.getDays() - 60 * Integer.parseInt(departureTime[0]) - Integer.parseInt(departureTime[1])
            + 60 * Integer.parseInt(arrivalTime[0]) + Integer.parseInt(arrivalTime[1]));
            System.out.println(minOnWay);
        }
    }
}
