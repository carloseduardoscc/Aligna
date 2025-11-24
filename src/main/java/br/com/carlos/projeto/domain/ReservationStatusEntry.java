package br.com.carlos.projeto.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Access(AccessType.FIELD)
@Entity(name = "reservation_status_entry_tb")
public class ReservationStatusEntry {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeStamp;
    @Enumerated(EnumType.STRING)
    private ReserveStatus status;

    /// BELONGS TO
    @ManyToOne
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    protected ReservationStatusEntry() {
    }

    public ReservationStatusEntry(ReserveStatus status, Reserve reserve) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.reserve = reserve;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void setStatus(ReserveStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    private void setReserve(Reserve reserve) {
        if (reserve == null) {
            throw new IllegalArgumentException("Reserve cannot be null");
        }
        this.reserve = reserve;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public ReserveStatus getStatus() {
        return status;
    }

    public Reserve getReserve() {
        return reserve;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReservationStatusEntry that = (ReservationStatusEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
