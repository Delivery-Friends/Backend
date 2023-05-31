package DeliveryFriends.Backend.Domain.Dto.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TossRes {
    String mId;
    String version;
    String lastTransactionKey;
    String paymentKey;
    String orderId;
    String orderName;
    String currency;
    String method;
    String status;
    String requestedAt;
    String approvedAt;
    Boolean useEscrow;
    Boolean cultureExpense;
    TossReceipt receipt;
    TossCheckout checkout;
    String discount;
    String cancels;
    String secret;
    String type;
    String easyPay;
    String country;
    String failure;
    Long totalAmount;
    Long balanceAmount;
    Long suppliedAmount;
    Long vat;
    Long taxFreeAmount;
    Long taxExemptionAmount;
}