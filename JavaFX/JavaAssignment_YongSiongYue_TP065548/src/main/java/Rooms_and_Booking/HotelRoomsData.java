package Rooms_and_Booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HotelRoomsData {
    private static HotelRoomsData instance = new HotelRoomsData();

    private static String filename = "src/main/resources/TextFile-Datas/hotel-rooms.txt";

    ObservableList<HotelRooms> hotelRoomsObservableList;

    public static HotelRoomsData getInstance() {
        return instance;
    }

    public ObservableList<HotelRooms> getHotelRooms() {
        return hotelRoomsObservableList;
    }

    public void loadHotelRoomsData() throws IOException {
        hotelRoomsObservableList = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader reader = Files.newBufferedReader(path);
        String input;
        try {
            while ((input = reader.readLine()) != null) {
                String[] hotelRooms = input.split(",");

                String roomNumber = hotelRooms[0];
                String roomType = hotelRooms[1];
                String roomOccupancy = hotelRooms[2];
                String price = hotelRooms[3];
                String roomAvailability = hotelRooms[4];
                String roomBeds = hotelRooms[5];

                HotelRooms hotelRoomsObject = new HotelRooms(roomNumber, roomType, roomOccupancy, price, roomAvailability, roomBeds);
                hotelRoomsObservableList.add(hotelRoomsObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeHotelRoomData() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter writer = Files.newBufferedWriter(path);
        for (HotelRooms i : hotelRoomsObservableList) {
            writer.write(i.getRoomNumber() + "," + i.getRoomTYpe() + "," + i.getMaxOccupancy() + "," + i.getPrice() + "," + i.getAvailability() + "," + i.getBedType());
            writer.newLine();
        }
        writer.close();
    }

    public void editAvailability(HotelRooms room, Boolean available) {
        int RoomIndex = HotelRoomsData.getInstance().getHotelRooms().indexOf(room);
        if (available) {
            HotelRoomsData.getInstance().getHotelRooms().get(RoomIndex).setAvailability("Available");
        } else {
            HotelRoomsData.getInstance().getHotelRooms().get(RoomIndex).setAvailability("Unavailable");
        }
    }
}