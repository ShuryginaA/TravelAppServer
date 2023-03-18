package com.travelapp.server.dto;

import com.travelapp.server.entity.Room;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {

    private Room.RoomType roomType;

    private int maxNumberOfAdults;

    private int maxNumberOfKids;

    private String additionalInfo;

}
