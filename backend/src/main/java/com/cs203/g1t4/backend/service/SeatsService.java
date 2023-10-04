package com.cs203.g1t4.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatsService {

  private static int NUM_SEATS_PER_ROW = 4;
  private static int NUM_ROWS = 4;
 
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
      String seatRow = (char)((seatsStart / NUM_ROWS) + 'A') + "";

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
    // find the first occurence of the empty seats
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

  public static void printSeatsMatrix(String seatsInformation) {
    if (seatsInformation == null) {
      System.out.println("no data");
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
      System.out.println("no seats available");
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

  public static void main(String[] args) {
    int testCase = 0;
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("0000"); // row A
      seatsInformation.append("0000"); // row B
      seatsInformation.append("0000"); // row c
      seatsInformation.append("0000"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 1;

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
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("1010"); // row A
      seatsInformation.append("0101"); // row B
      seatsInformation.append("1010"); // row c
      seatsInformation.append("0101"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 1;

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
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("1111"); // row A
      seatsInformation.append("0101"); // row B
      seatsInformation.append("1010"); // row c
      seatsInformation.append("0101"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 1;

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
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("1111"); // row A
      seatsInformation.append("1100"); // row B
      seatsInformation.append("1100"); // row c
      seatsInformation.append("1111"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 2;

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
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("1111"); // row A
      seatsInformation.append("1100"); // row B
      seatsInformation.append("1100"); // row c
      seatsInformation.append("1111"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 4;

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
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("1111"); // row A
      seatsInformation.append("1001"); // row B
      seatsInformation.append("1100"); // row c
      seatsInformation.append("1111"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 4;

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
    {
      testCase++;
      StringBuilder seatsInformation = new StringBuilder();
      seatsInformation.append("1111"); // row A
      seatsInformation.append("1111"); // row B
      seatsInformation.append("1111"); // row c
      seatsInformation.append("1111"); // row D

      String seatsInformationAPIcall = seatsInformation.toString();

      int numTickets = 4;

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
  } 
}

