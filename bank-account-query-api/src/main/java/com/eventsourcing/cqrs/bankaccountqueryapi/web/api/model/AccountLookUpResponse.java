package com.eventsourcing.cqrs.bankaccountqueryapi.web.api.model;

import com.eventsourcing.cqrs.bankaccountcommon.dto.response.BaseResponse;
import com.eventsourcing.cqrs.bankaccountqueryapi.domain.BankAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookUpResponse extends BaseResponse {

    private List<BankAccountEntity> accounts;

    public AccountLookUpResponse(String message) {
        super(message);
    }
}
