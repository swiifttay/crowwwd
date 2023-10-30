package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.seat.SeatRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatsConfirmRequest;
import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.seat.SeatResponse;
import com.cs203.g1t4.backend.data.response.ticket.SingleTicketResponse;
import com.cs203.g1t4.backend.data.response.ticket.TicketResponse;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.Seats;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import com.cs203.g1t4.backend.models.exceptions.InvalidCategoryException;
import com.cs203.g1t4.backend.models.exceptions.InvalidSeatingDetailsException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.SeatingDetailsRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsService {

    private final UserRepository userRepository;
    private final SeatingDetailsRepository seatingDetailsRepository;
    private final SeatingDetailsService seatingDetailsService;
    private final TicketService ticketService;

    private static final int NUM_SEATS_PER_ROW = 4;
    private static final int NUM_ROWS = 4;

    public Response findSeats(final String eventId, final String category, final String numSeats)
            throws InvalidCategoryException, InvalidSeatingDetailsException {

        //Create Seats to store information to be returned to frontend
        Seats seats = Seats.builder()
                .category(category)
                .build();

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Obtain pricing for each seat
        Category cat = findCategory(eventSeatingDetails, category);

        //Obtain the pricePerSeat from cat
        seats.setPricePerSeat(cat.getPrice());

        //Calculate total cost of seats and update in seats
        seats.setTotalCost(seats.getPricePerSeat() * Integer.parseInt(numSeats));

        //Find seats using Seats Allocation
        List<String> seatAllocation = getSeats(Integer.parseInt(numSeats), cat.getSeatsInformationString());

        //Throws seatAllocation == null
        if (seatAllocation == null) { throw new IllegalArgumentException("No Combination can be found"); }

        //Update Seats Allocation Pending status into the seatingDetails
        String updatedSeatsString = returnUpdatedSeatsString(cat.getSeatsInformationString(), seatAllocation, '2');
        seatingDetailsService.updateSeatingDetails(eventId, category, updatedSeatsString);

        //Update Seating allocation into seats
        String[] array = new String[seatAllocation.size()];
        for (int i = 0 ; i < seatAllocation.size() ; i++) {
            array[i] = seatAllocation.get(i);
        }
        seats.setAllocatedSeats(array);

        return SeatResponse.builder()
                        .seats(seats)
                        .build();
    }

    public Response confirmSeats(final String eventId, final String category, final String username, final SeatsConfirmRequest seatsConfirmRequest) {

        //Find User object from username
        userRepository.findByUsername(username).orElseThrow(() -> new InvalidTokenException());

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Find Category
        Category c = findCategory(eventSeatingDetails, category);

        //Update Seats Allocation Pending status into the seatingDetails
        List<String> allocatedSeats = seatsConfirmRequest.getAllocatedSeats();
        String updatedSeatsString = returnUpdatedSeatsString(c.getSeatsInformationString(),
                allocatedSeats, '1');
        seatingDetailsService.updateSeatingDetails(eventId, category, updatedSeatsString);

        //Return Tickets
        List<Ticket> ticketList = new ArrayList<>();
        List<TicketRequest> ticketRequestList = seatsConfirmRequest.returnTicketRequestListFromRequest(eventId);
        for (int i = 0 ; i < ticketRequestList.size() ; i++) {
            SingleTicketResponse singleTicketResponse = ticketService.createTicket(ticketRequestList.get(i), username);
            ticketList.add(singleTicketResponse.getTicket());
        }

        return TicketResponse.builder()
                .tickets(ticketList)
                .build();
    }

    public Response cancelSeats(final String eventId, final String category, final SeatRequest seatRequest) {

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Find Category
        Category c = findCategory(eventSeatingDetails, category);

        //Update Seats Allocation Pending status into the seatingDetails
        String updatedSeatsString = returnUpdatedSeatsString(c.getSeatsInformationString(),
                seatRequest.getAllocatedSeats(), '0');
        seatingDetailsService.updateSeatingDetails(eventId, category, updatedSeatsString);

        //Consider returning ticket here?

        return SuccessResponse.builder()
                .response("Seats cancelled.")
                .build();
    }

    private Category findCategory(final EventSeatingDetails eventSeatingDetails, final String category)
            throws InvalidCategoryException{
        List<Category> categoryList = eventSeatingDetails.getCategories();
        for (int i = 0 ; i < categoryList.size(); i++) {
            Category cat = categoryList.get(i);
            if (cat.getCategory().equals(category)) {
                return cat;
            }
        }
        throw new InvalidCategoryException(category);
    }

    private List<String> getSeats(int numTickets, String seatsInformationString) {
        List<Integer> combination = getCombi(numTickets);
        return getSeats(numTickets, seatsInformationString, combination);
    }

    private List<String> getSeats(int numTickets, String seatsInformationString, List<Integer> combination) {
        if (combination == null) {
            return null;
        }
        StringBuilder updatedSeatsInformationString = new StringBuilder(seatsInformationString);

        List<String> allocatedSeatsInformation = new ArrayList<>();

        // loop through the combination
        for (int i = 0; i < combination.size(); i++) {
            int numConsecutiveTickets = combination.get(i);
            int seatsStart = find(numConsecutiveTickets, updatedSeatsInformationString.toString());
            if (seatsStart < 0) {
                break;
            }
            String seatRow = String.valueOf((char)((seatsStart / NUM_ROWS) + 'A'));

            // set in allocatedSeatsInformation
            for (int j = 0; j < numConsecutiveTickets; j++) {
                int rowNum = (seatsStart + j) % NUM_ROWS + 1;
                updatedSeatsInformationString.setCharAt(seatsStart + j, '2');
                allocatedSeatsInformation.add(seatRow + rowNum);
            }

        }

        if (allocatedSeatsInformation.size() != numTickets) {
            List<Integer> newCombi = getCombi(combination, numTickets);
            return getSeats(numTickets, seatsInformationString, newCombi);
        }
        return allocatedSeatsInformation;
    }

    // positive result: represents the starting num in the seatsInformationString
    // negative result: no suitable seat found
    private int find(int numTickets, String seatsInformationString) {
        // find the first occurrence of the empty seats
        int firstEmpty = seatsInformationString.indexOf('0');

        // if no more seats
        if (firstEmpty < 0) {
            return -1;
        }

        int seatsLeftToFind = numTickets - 1;
        int endIndex = firstEmpty;

        int firstEmptyRow = firstEmpty / NUM_ROWS;
        // check if there is a sequence
        while (seatsLeftToFind > 0 && endIndex > 0) {
            endIndex = seatsInformationString.indexOf('0', endIndex + 1);

            // check if it is all still in the same row
            if (endIndex / NUM_ROWS != firstEmptyRow) {
                firstEmpty = endIndex;
                seatsLeftToFind = numTickets - 1;
            } else {
                seatsLeftToFind--;
            }

        }

        if (seatsLeftToFind == 0) {
            return firstEmpty;
        }
        return -1;
    }

    // initial getCombi
    private List<Integer> getCombi(int numConsecutiveTickets) {

        return Arrays.asList(numConsecutiveTickets);
    }

    // subsequent getCombi
    private List<Integer> getCombi(List<Integer> previousCombi, int numConsecutiveTickets) {
        if (previousCombi.size() == numConsecutiveTickets) {
            return null;
        }
        // see what is the total num of tickets needed
        if (previousCombi.equals(Arrays.asList(2, 2))) {
            return Arrays.asList(3, 1);
        }

        List<Integer> editedPrev = new ArrayList<>();
        for (int i = 1; i < previousCombi.size(); i++) {
            editedPrev.add(previousCombi.get(i));
        }

        // follow the sequence
        int firstValue = previousCombi.get(0);
        int newFirst = firstValue / 2;
        int newSecond = firstValue - newFirst;
        if (newFirst < newSecond) {
            int temp = newSecond;
            newSecond = newFirst;
            newFirst = temp;
        }

        // create new combi
        List<Integer> toReturn = new ArrayList<>();
        toReturn.add(newFirst);
        toReturn.add(newSecond);
        if (editedPrev.size() != 0)
            toReturn.addAll(editedPrev);
        return toReturn;
    }

    private String returnUpdatedSeatsString(String seatsInformation, List<String> seats, char type) {
        char lookFor = (type == '1') ? '0' : '1';
        if (seats == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder updatedSeatsInformation = new StringBuilder(seatsInformation);
        for (int i = 0; i < seats.size(); i++) {
            String currentSeat = seats.get(i);
            int seatIndex = (currentSeat.charAt(0) - 'A') * NUM_ROWS + (currentSeat.charAt(1) - '1');
//            if (updatedSeatsInformation.charAt(seatIndex) != lookFor) { throw new IllegalArgumentException(); }
            updatedSeatsInformation.setCharAt(seatIndex, type);
        }

        return updatedSeatsInformation.toString();
    }

//    private void printSeatsMatrix(String seatsInformation) {
//        if (seatsInformation == null) {
//            System.out.println("No data");
//            return;
//        }
//        for (int i = 0; i < NUM_ROWS; i++) {
//            for (int j = 0; j < NUM_SEATS_PER_ROW; j++) {
//                System.out.print(seatsInformation.charAt(i * 4 + j) + " ");
//            }
//            System.out.println();
//        }
//    }

//    private void printUpdatedSeatsMatrix(String seatsInformation, List<String> seats) {
//        if (seats == null) {
//            System.out.println("No seats available");
//            return;
//        }
//        StringBuilder updatedSeatsInformation = new StringBuilder(seatsInformation);
//        for (int i = 0; i < seats.size(); i++) {
//            String currentSeat = seats.get(i);
//            int seatIndex = (currentSeat.charAt(0) - 'A') * NUM_ROWS + (currentSeat.charAt(1) - '1');
//            updatedSeatsInformation.setCharAt(seatIndex, '2');
//        }
//
//        printSeatsMatrix(updatedSeatsInformation.toString());
//    }

//    private void testCase(String seatsInformationAPIcall, int numTickets, int testCase) {
//        System.out.printf("------------------test %d--------------------\n", testCase);
//        System.out.printf("Seats to fill: %d\n", numTickets);
//        System.out.println("Original:");
//        printSeatsMatrix(seatsInformationAPIcall);
//
//        System.out.println("Updated:");
//        List<String> seats = getSeats(numTickets, seatsInformationAPIcall);
//        System.out.println(seats);
//        printUpdatedSeatsMatrix(seatsInformationAPIcall, seats);
//        System.out.println("--------------------------------------------");
//    }


//    public static void main(String[] args) {
//        SeatsService seatsService = new SeatsService();
//        int testCase = 0;
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("0000"); // row A
//            seatsInformation.append("0000"); // row B
//            seatsInformation.append("0000"); // row c
//            seatsInformation.append("0000"); // row D
//
//            testCase(seatsInformation.toString(), 1, testCase);
//        }
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("1010"); // row A
//            seatsInformation.append("0101"); // row B
//            seatsInformation.append("1010"); // row c
//            seatsInformation.append("0101"); // row D
//
//            testCase(seatsInformation.toString(), 1, testCase);
//        }
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("1111"); // row A
//            seatsInformation.append("0101"); // row B
//            seatsInformation.append("1010"); // row c
//            seatsInformation.append("0101"); // row D
//
//            testCase(seatsInformation.toString(), 1, testCase);
//        }
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("1111"); // row A
//            seatsInformation.append("1100"); // row B
//            seatsInformation.append("1100"); // row c
//            seatsInformation.append("1111"); // row D
//
//            testCase(seatsInformation.toString(), 2, testCase);
//        }
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("1111"); // row A
//            seatsInformation.append("1100"); // row B
//            seatsInformation.append("1100"); // row c
//            seatsInformation.append("1111"); // row D
//
//            testCase(seatsInformation.toString(), 4, testCase);
//        }
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("1111"); // row A
//            seatsInformation.append("1001"); // row B
//            seatsInformation.append("1100"); // row c
//            seatsInformation.append("1111"); // row D
//
//            testCase(seatsInformation.toString(), 4, testCase);
//        }
//        {
//            testCase++;
//            StringBuilder seatsInformation = new StringBuilder();
//            seatsInformation.append("1111"); // row A
//            seatsInformation.append("1111"); // row B
//            seatsInformation.append("1111"); // row c
//            seatsInformation.append("1111"); // row D
//
//            testCase(seatsInformation.toString(), 4, testCase);
//        }
//    }
}
