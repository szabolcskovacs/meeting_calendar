package hu.itware.sandbox.meetingcalendar.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Entity
@Table(name = "meeting_units")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "unit_start", unique=true)
    private Calendar unitStart;

}
