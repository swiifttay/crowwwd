package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.seat.FindSeatRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatCancelRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatsConfirmRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.exceptions.InvalidCategoryException;
import com.cs203.g1t4.backend.models.exceptions.InvalidSeatingDetailsException;
import org.springframework.stereotype.Service;

@Service
public interface SeatsService {

    public Response findSeats(final FindSeatRequest findSeatRequest)
            throws InvalidCategoryException, InvalidSeatingDetailsException;

    public Response confirmSeats(final String username, final SeatsConfirmRequest seatsConfirmRequest);

    public Response cancelSeats(final SeatCancelRequest seatRequest);
}

