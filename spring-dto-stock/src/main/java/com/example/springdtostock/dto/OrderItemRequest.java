package com.example.springdtostock.dto;

public record OrderItemRequest(
        Integer productId,
        Integer amount) {
}

//import lombok.AllArgsConstructor;
//        import lombok.Getter;
//        import lombok.NoArgsConstructor;
//        import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class OrderItemRequest {
//    private Integer productId;
//    private Integer amount;
//}