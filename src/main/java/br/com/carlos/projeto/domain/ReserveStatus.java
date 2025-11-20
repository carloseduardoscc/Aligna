package br.com.carlos.projeto.domain;

import java.util.Set;

public enum ReserveStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED;

    public Set<ReserveStatus> possibleTransitions() {
        return switch (this) {
            case PENDING -> Set.of(ACCEPTED, REJECTED, CANCELLED);
            case ACCEPTED -> Set.of(CANCELLED);
            case REJECTED -> Set.of();
            case CANCELLED -> Set.of();
        };
    }
}
