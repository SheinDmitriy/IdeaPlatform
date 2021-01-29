package shein.dmitriy.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "tickets.json";

        String tmpData = new String(Files.readAllBytes(Paths.get(fileName)));

        Gson gson = new Gson();
        JSONObject tmpJsonObject = gson.fromJson(tmpData, JSONObject.class);

        ArrayList<Ticket> tickets = new ArrayList<>();

        for (Object o: (ArrayList) tmpJsonObject.get("tickets")) {
            JsonObject tmpTicket = gson.toJsonTree(o).getAsJsonObject();
            Ticket ticket = gson.fromJson(tmpTicket, Ticket.class);
            tickets.add(ticket);
        }
    }
}
