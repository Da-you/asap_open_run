package com.asap.openrun.doamin.user.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

  @Builder
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UserTicketList {

    private Long historyId;
    private String ticketNumber;
    private boolean isReceived;
  }

  @Builder
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UserTicket {

    private Long historyId;
    private String productName;
    private boolean isOpen;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
  }
  @Builder
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class UserMyPageResponse{
    private String username;
    private String asapName;
    private String nickname;
    private String phoneNumber;
  }

}
