package com.cs203.g1t4.backend.models.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HoldingAreaComparator implements Comparator<String> {
    private boolean isFan;

    @Override
    public int compare(String added, String original) {
        Random rand = new Random();
        int randInt = rand.nextInt(100);
        if ((isFan && randInt >= 65) || (!isFan && randInt <= 35)) {
            return 1;
        }
        return 0;
    }
}


