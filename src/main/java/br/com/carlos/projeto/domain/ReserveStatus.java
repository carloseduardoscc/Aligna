package br.com.carlos.projeto.domain;

import java.util.Set;

public enum ReserveStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    CANCELLED("CANCELLED");

    private final String status;

    ReserveStatus(String status) {
        this.status = status;
    }

    public Set<ReserveStatus> possibleTransitions() {
        return switch (this) {
            case PENDING -> Set.of(ACCEPTED, REJECTED, CANCELLED);
            case ACCEPTED -> Set.of(CANCELLED);
            case REJECTED -> Set.of();
            case CANCELLED -> Set.of();
        };
    }


    @Override
    public String toString() {
        return status;
    }
}
