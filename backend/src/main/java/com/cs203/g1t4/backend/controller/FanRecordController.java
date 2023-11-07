package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.FanRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FanRecordController {
    private final FanRecordService fanRecordService;

//    @PostMapping("/fanRecord")
//    public ResponseEntity<Response> addFanRecord(@Valid @RequestBody FanRecordRequest fanRecordRequest, @AuthenticationPrincipal UserDetails userDetails) {
//
//        String username = userDetails.getUsername();
//
//        Response response = fanRecordService.createFanRecord(fanRecordRequest, username);
//
//        return ResponseEntity.ok(response);
//
//    }

    @GetMapping("/fanRecord")
    public ResponseEntity<Response> getAllFanRecordsUnderUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        Response response = fanRecordService.findAllFanRecordsUnderUser(username);

        return ResponseEntity.ok(response);
    }

}
