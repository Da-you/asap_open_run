package com.asap.openrun.global.utils.encryption;

import java.security.SecureRandom;

public class TicketNumberService {

  public static String ticketNumber() {
    SecureRandom secureRandom = new SecureRandom();
    int ticketNumber = secureRandom.nextInt(1_0000_0000);
    return String.format("%08d", ticketNumber);
  }

}
