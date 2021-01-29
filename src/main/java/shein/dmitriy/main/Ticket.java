package shein.dmitriy.main;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Ticket {
    String origin;
    @SerializedName("screen_name")
    String originName;
    String destination;
    @SerializedName("destination_name")
    String destinationName;
    @SerializedName("departure_date")
    String departureDate;
    @SerializedName("departure_time")
    String departureTime;
    @SerializedName("arrival_date")
    String arrivalDate;
    @SerializedName("arrival_time")
    String arrivalTime;
    String carrier;
    int stops;
    int price;
}
