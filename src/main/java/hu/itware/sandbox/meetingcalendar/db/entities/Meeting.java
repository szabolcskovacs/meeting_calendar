package hu.itware.sandbox.meetingcalendar.db.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "meetings")
@Data
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "meeting_start")
    private Calendar meetingStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "meeting_end")
    private Calendar meetingEnd;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MeetingUnit> meetingUnits;


}
