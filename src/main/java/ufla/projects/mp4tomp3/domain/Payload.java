package ufla.projects.mp4tomp3.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload extends PanacheEntity {
    public String userName;
    public Notification notificationType;
    public String phone;
    public String fileName;
    public String email;
    public String bucketName;
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    public Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    public Date updatedAt;
    public Status status;

    public static Payload multiPartToPayload(Mp4ToMp3Body mp4ToMp3Body) {
        return Payload.builder()
                .userName(mp4ToMp3Body.getUserName())
                .notificationType(mp4ToMp3Body.getNotificationType())
                .phone(mp4ToMp3Body.getPhone())
                .fileName(mp4ToMp3Body.getFileName())
                .email(mp4ToMp3Body.getEmail())
                .build();
    }

}
