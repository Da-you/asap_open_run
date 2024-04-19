package com.asap.openrun.doamin.user.service;

import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.domain.UserProductHistory;
import com.asap.openrun.doamin.user.dto.response.UserResponse.UserTicket;
import com.asap.openrun.doamin.user.repository.UserHistoryRepository;
import com.asap.openrun.doamin.user.repository.UserRepository;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTicketService {

  private final UserHistoryRepository userHistoryRepo;
  private final UserRepository userRepo;
  private final ProductRepository productRepo;


  @Transactional
  public void createTicketing(String asapName, String serialNumber) {
    User user = userRepo.findByAsapName(asapName);

    Product product = productRepo.findBySerialNumber(serialNumber);
    if (!product.isOpen()) {
      throw new BusinessException(ErrorCode.SERVER_ERROR); // 티켓팅 오픈전
    }
    if (product.getRemainingStock() == 0) {
      throw new BusinessException(ErrorCode.SERVER_ERROR);
    }
    product.decrease();
    log.info("호출!");
    userHistoryRepo.save(UserProductHistory.from(user, product));
  }

  @Transactional
  public void receiveProduct(String asapName, Long historyId) {
    UserProductHistory history = userHistoryRepo.findById(historyId)
        .orElseThrow(() -> new BusinessException(ErrorCode.SERVER_ERROR));

    history.setIsReceive();
  }

  // 내 예메 내역 리스트
  @Transactional(readOnly = true)
  public List<UserTicket> getHistories(String asapName) {
    User user = userRepo.findByAsapName(asapName);

    List<UserProductHistory> all = userHistoryRepo.findAllByUser(user);

    return all.stream()
        .map(history -> new UserTicket(
            history.getId(),
            history.getProduct().getBrand().getBrandName(),
            history.getProduct().getProductName(),
            history.getProduct().isOpen(),
            history.getProduct().getEventStartDate(),
            history.getProduct().getEventEndDate()
        ))
        .toList();

  }

  // 내 예매 내역 단건 조회
  @Transactional(readOnly = true)
  public UserTicket getHistory(String asapName, Long historyId) {
    User user = userRepo.findByAsapName(asapName);

    UserProductHistory history = userHistoryRepo.findByUserAndId(user, historyId);

    return UserTicket.builder()
        .brandName(history.getProduct().getBrand().getBrandName())
        .productName(history.getProduct().getProductName())
        .isOpen(history.getProduct().isOpen())
        .eventStartDate(history.getProduct().getEventStartDate())
        .eventEndDate(history.getProduct().getEventStartDate())
        .build();
  }

  // 예매 취소
  @Transactional
  public void cancelTicket(String asapName, Long historyId) {
    User user = userRepo.findByAsapName(asapName);

    UserProductHistory history = userHistoryRepo.findByUserAndId(user, historyId);

    history.softDelete();
  }
}
