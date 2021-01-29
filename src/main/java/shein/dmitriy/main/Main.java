package shein.dmitriy.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "tickets.json";
        int average = 0;

        String tmpData = new String(Files.readAllBytes(Paths.get(fileName)));

        Gson gson = new Gson();
        JSONObject tmpJsonObject = gson.fromJson(tmpData, JSONObject.class);
//        JSONObject tpm2 = gson.toJsonTree(tmpData).getAsJsonObject();

        ArrayList<Integer> minOnWayArray = new ArrayList<>();


        for (Object o: (ArrayList) tmpJsonObject.get("tickets")) {
            JsonObject tmpTicket = gson.toJsonTree(o).getAsJsonObject();
            Ticket ticket = gson.fromJson(tmpTicket, Ticket.class);

            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yy");
            LocalDate departureDate = LocalDate.parse(ticket.getDepartureDate(), formatterDate);
            LocalDate arrivalDate = LocalDate.parse(ticket.getArrivalDate(), formatterDate);
            Period period = Period.between(departureDate, arrivalDate);

            String[] departureTime = ticket.getDepartureTime().split(":");
            String[] arrivalTime = ticket.getArrivalTime().split(":");

            int minOnWay =  60 * 24 * period.getDays() - 60 * Integer.parseInt(departureTime[0]) - Integer.parseInt(departureTime[1])
                    + 60 * Integer.parseInt(arrivalTime[0]) + Integer.parseInt(arrivalTime[1]);
            minOnWayArray.add(minOnWay);
            average += minOnWay;
            System.out.println(minOnWayArray);
        }
        average = average / minOnWayArray.size();
        System.out.println("Среднее время в пути: " + average / 60 + ":" + average % 60);
        Collections.sort(minOnWayArray);
        System.out.println(minOnWayArray);
        int indexPercentiles = minOnWayArray.size() * 90 / 100;
        System.out.println( minOnWayArray.get(indexPercentiles - 1));


    }
}
