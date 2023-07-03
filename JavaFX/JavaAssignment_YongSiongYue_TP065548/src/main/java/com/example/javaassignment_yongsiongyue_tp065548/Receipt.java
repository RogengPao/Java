package com.example.javaassignment_yongsiongyue_tp065548;

import Rooms_and_Booking.HotelBookingData;
import Rooms_and_Booking.HotelBookings;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Receipt {
    public Label roomNumber, invoiceDate, dueDate, invoiceGmail, invoiceRoomNumber, invoiceRoomType, invoiceCustomerName, invoiceTotalDays, invoiceTotalPrice, invoiceRoomNumber2, invoiceRoomType2, invoiceDays2, invoicePrice2, invoiceTotal2, subtotal, invoiceTax, grandTotal, totalAmount;

    public void initialize(String number, String type) {
        roomNumber.setText(number);
        invoiceRoomType.setText("Room Type:" + type);

        invoiceDate.setText("Invoice date: " + LocalDate.now());
        dueDate.setText("Due date: " + LocalDate.now());

        for (HotelBookings i : HotelBookingData.getInstance().getHotelBookings()) {
            if (i.getRoomNumber().equals(roomNumber.getText()) && i.getStatus().equals("Checked In")) {

                invoiceGmail.setText(i.getCustomerEmail());
                invoiceRoomNumber.setText("Room Number: " + number);

                invoiceCustomerName.setText("Name: " + i.getCustomerName());
                invoiceTotalDays.setText("Total Days: " + i.getTotalDays());
                invoiceTotalPrice.setText("Total Price: " + i.getTotalPrice());

                invoiceRoomNumber2.setText(number);
                invoiceRoomType.setText(type);
                invoicePrice2.setText("350");
                invoiceDays2.setText(i.getTotalDays());
                invoiceTotal2.setText(i.getTotalPrice());

                subtotal.setText(i.getTotalPrice());

                double totalPrice = Integer.parseInt(i.getTotalPrice());
                double tax = totalPrice * 10/100;

                DecimalFormat df = new DecimalFormat("0.00");

                invoiceTax.setText(String.valueOf(df.format(tax)));

                double grandtotalprice = totalPrice + tax;
                grandTotal.setText(String.valueOf(df.format(grandtotalprice)));

                totalAmount.setText(grandTotal.getText());


            }
        }
    }

}
