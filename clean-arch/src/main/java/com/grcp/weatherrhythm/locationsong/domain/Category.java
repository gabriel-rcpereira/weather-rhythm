package com.grcp.weatherrhythm.locationsong.domain;

import java.util.Optional;
import java.util.stream.Stream;

public enum Category {

    PARTY {
        boolean isCategory(double value) {
            double thirtyDegrees = 30D;
            return value > thirtyDegrees;
        }
    },
    POP {
        boolean isCategory(double value) {
            double fifteenDegrees = 15D;
            double thirtyDegrees = 30D;
            return value >= fifteenDegrees && value <= thirtyDegrees;
        }
    },
    ROCK {
        boolean isCategory(double value) {
            double tenDegrees = 10D;
            double fourteenDegrees = 14D;
            return value >= tenDegrees && value <= fourteenDegrees;
        }
    },
    CLASSICAL {
        boolean isCategory(double value) {
            double tenDegrees = 10D;
            return value < tenDegrees;
        }
    };

    abstract boolean isCategory(double value);

    public static Optional<Category> retrieveCategory(double value) {
        return Stream.of(values())
                .filter(category -> category.isCategory(value))
                .findFirst();
    }
}
