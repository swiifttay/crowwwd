package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.seat.SeatRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.seat.SeatResponse;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.Seats;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import com.cs203.g1t4.backend.models.exceptions.InvalidCategoryException;
import com.cs203.g1t4.backend.models.exceptions.InvalidSeatingDetailsException;
import com.cs203.g1t4.backend.repository.SeatingDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsService {

    private final SeatingDetailsRepository seatingDetailsRepository;
    private final SeatingDetailsService seatingDetailsService;

    private static final int NUM_SEATS_PER_ROW = 4;
    private static final int NUM_ROWS = 4;

    public Response findSeats(final String eventId, final String category, final String numSeats)
            throws InvalidSeatingDetailsException {

        //Create Seats to store information
        Seats seats = Seats.builder()
                .category(category)
                .build();

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Obtain pricing for each seat
        List<Category> categoryList = eventSeatingDetails.getCategories();
        Category c = null;
        boolean found = false;
        for (int i = 0 ; i < categoryList.size() && !found ; i++) {
            c = categoryList.get(i);
            if (c.getCategory().equals(category)) {
                found = true;
                seats.setPricePerSeat(c.getPrice());
            }
        }

        //If Category is not in the list, throws an InvalidCategoryException back
        if (!found) { throw new InvalidCategoryException(category); }

        //Calculate total cost of seats and update in seats
        seats.setTotalCost(seats.getPricePerSeat() * Integer.parseInt(numSeats));

        //Find seats using Seats Allocation
        List<String> seatAllocation = getSeats(4, c.getSeatsInformationString());

        //Throws seatAllocation == null
        if (seatAllocation == null) { throw new IllegalArgumentException("No Combination can be found"); }

        //Update Seats Allocation Pending status into the seatingDetails
        String updatedSeatsString = returnUpdatedSeatsString(c.getSeatsInformationString(), seatAllocation, '2');
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

    public Response confirmSeats(String eventId, String category, SeatRequest seatRequest) {

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Obtain pricing for each seat
        List<Category> categoryList = eventSeatingDetails.getCategories();
        Category c = null;
        boolean found = false;
        for (int i = 0 ; i < categoryList.size() && !found ; i++) {
            c = categoryList.get(i);
            if (c.getCategory().equals(category)) {
                found = true;
            }
        }

        //If Category is not in the list, throws an InvalidCategoryException back
        if (!found) { throw new InvalidCategoryException(category); }

        //Update Seats Allocation Pending status into the seatingDetails
        String updatedSeatsString = returnUpdatedSeatsString(c.getSeatsInformationString(),
                seatRequest.getAllocatedSeats(), '1');
        seatingDetailsService.updateSeatingDetails(eventId, category, updatedSeatsString);

        //Consider returning ticket here?

        return SuccessResponse.builder()
                .response("Seats confirmed.")
                .build();
    }

    public static List<String> getSeats(int numTickets, String seatsInformationString) {
        List<Integer> combination = getCombi(numTickets);
        return getSeats(numTickets, seatsInformationString, combination);
    }

    public static List<String> getSeats(int numTickets, String seatsInformationString, List<Integer> combination) {
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
    public static int find(int numTickets, String seatsInformationString) {
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
    public static List<Integer> getCombi(int numConsecutiveTickets) {

        return Arrays.asList(numConsecutiveTickets);
    }

    // subsequent getCombi
    public static List<Integer> getCombi(List<Integer> previousCombi, int numConsecutiveTickets) {
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

    public static String returnUpdatedSeatsString(String seatsInformation, List<String> seats, char type) {
        if (seats == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder updatedSeatsInformation = new StringBuilder(seatsInformation);
        for (int i = 0; i < seats.size(); i++) {
            String currentSeat = seats.get(i);
            int seatIndex = (currentSeat.charAt(0) - 'A') * NUM_ROWS + (currentSeat.charAt(1) - '1');
            updatedSeatsInformation.setCharAt(seatIndex, type);
        }

        return updatedSeatsInformation.toString();
    }

    public static void printSeatsMatrix(String seatsInformation) {
        if (seatsInformation == null) {
            System.out.println("No data");
            return;
        }
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_SEATS_PER_ROW; j++) {
                System.out.print(seatsInformation.charAt(i * 4 + j) + " ");
            }
            System.out.println();
        }
    }

    public static void printUpdatedSeatsMatrix(String seatsInformation, List<String> seats) {
        if (seats == null) {
            System.out.println("No seats available");
            return;
        }
        StringBuilder updatedSeatsInformation = new StringBuilder(seatsInformation);
        for (int i = 0; i < seats.size(); i++) {
            String currentSeat = seats.get(i);
            int seatIndex = (currentSeat.charAt(0) - 'A') * NUM_ROWS + (currentSeat.charAt(1) - '1');
            updatedSeatsInformation.setCharAt(seatIndex, '2');
        }

        printSeatsMatrix(updatedSeatsInformation.toString());
    }

    private static void testCase(String seatsInformationAPIcall, int numTickets, int testCase) {
        System.out.printf("------------------test %d--------------------\n", testCase);
        System.out.printf("Seats to fill: %d\n", numTickets);
        System.out.println("Original:");
        printSeatsMatrix(seatsInformationAPIcall);

        System.out.println("Updated:");
        List<String> seats = getSeats(numTickets, seatsInformationAPIcall);
        System.out.println(seats);
        printUpdatedSeatsMatrix(seatsInformationAPIcall, seats);
        System.out.println("--------------------------------------------");
    }


    public static void main(String[] args) {
        int testCase = 0;
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("0000"); // row A
            seatsInformation.append("0000"); // row B
            seatsInformation.append("0000"); // row c
            seatsInformation.append("0000"); // row D

            testCase(seatsInformation.toString(), 1, testCase);
        }
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("1010"); // row A
            seatsInformation.append("0101"); // row B
            seatsInformation.append("1010"); // row c
            seatsInformation.append("0101"); // row D

            testCase(seatsInformation.toString(), 1, testCase);
        }
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("1111"); // row A
            seatsInformation.append("0101"); // row B
            seatsInformation.append("1010"); // row c
            seatsInformation.append("0101"); // row D

            testCase(seatsInformation.toString(), 1, testCase);
        }
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("1111"); // row A
            seatsInformation.append("1100"); // row B
            seatsInformation.append("1100"); // row c
            seatsInformation.append("1111"); // row D

            testCase(seatsInformation.toString(), 2, testCase);
        }
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("1111"); // row A
            seatsInformation.append("1100"); // row B
            seatsInformation.append("1100"); // row c
            seatsInformation.append("1111"); // row D

            testCase(seatsInformation.toString(), 4, testCase);
        }
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("1111"); // row A
            seatsInformation.append("1001"); // row B
            seatsInformation.append("1100"); // row c
            seatsInformation.append("1111"); // row D

            testCase(seatsInformation.toString(), 4, testCase);
        }
        {
            testCase++;
            StringBuilder seatsInformation = new StringBuilder();
            seatsInformation.append("1111"); // row A
            seatsInformation.append("1111"); // row B
            seatsInformation.append("1111"); // row c
            seatsInformation.append("1111"); // row D

            testCase(seatsInformation.toString(), 4, testCase);
        }
    }
}

