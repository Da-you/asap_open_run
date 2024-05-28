package com.asap.openrun.doamin.user.service;

import static com.asap.openrun.global.utils.encryption.TicketNumberService.ticketNumber;

import com.asap.openrun.doamin.product.RedisRepository;
import com.asap.openrun.doamin.product.domain.Product;
import com.asap.openrun.doamin.product.repository.ProductRepository;
import com.asap.openrun.doamin.user.domain.User;
import com.asap.openrun.doamin.user.domain.UserProductHistory;
import com.asap.openrun.doamin.user.dto.response.UserResponse.UserTicket;
import com.asap.openrun.doamin.user.dto.response.UserResponse.UserTicketList;
import com.asap.openrun.doamin.user.repository.UserHistoryRepository;
import com.asap.openrun.doamin.user.repository.UserRepository;
import com.asap.openrun.global.common.error.BusinessException;
import com.asap.openrun.global.common.error.ErrorCode;
import com.asap.openrun.global.utils.encryption.TicketNumberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTicketService {

  private final UserHistoryRepository userHistoryRepo;
  private final UserRepository userRepo;
  private final ProductRepository productRepo;
  private final RedisRepository redisRepo;

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void createTicketingByRedis(String asapName, String serialNumber) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    Boolean hasLeftStock = redisRepo.hasStockInRedis(serialNumber);
    if (hasLeftStock) {
      decreaseStockInRedis(serialNumber);
    } else {
      decreaseStockInDB(serialNumber);
    }
    userHistoryRepo.save(UserProductHistory.of(user, serialNumber, ticketNumber()));
  }

  private void decreaseStockInDB(String serialNumber) {
    Product product = productRepo.findBySerialNumberWithPessimisticLock(serialNumber);
    if (product.getStock() - 1 < 0) {
      throw new BusinessException(ErrorCode.PRODUCT_IS_SOLD_OUT);
    }
    product.decrease();
    productRepo.save(product);

  }

  private void decreaseStockInRedis(String serialNumber) {
    Boolean success = redisRepo.decreaseStock(serialNumber, 1);
    if (!success) {
      throw new BusinessException(ErrorCode.PRODUCT_IS_SOLD_OUT);
    }
  }

  @Transactional
  public void receiveProduct(String asapName, Long historyId) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    UserProductHistory history = getUserProductHistory(historyId, user);
    history.setIsReceive();
  }

  private UserProductHistory getUserProductHistory(Long historyId, User user) {
    UserProductHistory history = userHistoryRepo.findByUserAndId(user, historyId);
    if (history == null) {
      throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
    return history;
  }

  // 내 예메 내역 리스트
  @Transactional(readOnly = true)
  public List<UserTicketList> getHistories(String asapName) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    List<UserProductHistory> all = userHistoryRepo.findAllByUser(user);

    return all.stream()
        .map(history -> new UserTicketList(
            history.getId(),
            history.getTicketNumber(),
            history.isReceived()
        ))
        .toList();
  }

  // 내 예매 내역 단건 조회
  @Transactional(readOnly = true)
  public UserTicket getHistory(String asapName, Long historyId) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    UserProductHistory history = getUserProductHistory(historyId, user);
    Product product = productRepo.findBySerialNumber(history.getSerialNumber());

    return UserTicket.builder()
        .productName(product.getProductName())
        .isOpen(product.isOpen())
        .eventStartDate(product.getEventStartDate())
        .eventEndDate(product.getEventEndDate())
        .build();
  }

  // 예매 취소
  @Transactional
  public void cancelTicket(String asapName, Long historyId) {
    User user = userRepo.findByAsapName(asapName)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

    UserProductHistory history = getUserProductHistory(historyId, user);

    history.softDelete();
  }
}
