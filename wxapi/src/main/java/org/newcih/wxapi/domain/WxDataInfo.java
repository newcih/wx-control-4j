package org.newcih.wxapi.domain;

import domain.WxInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author NEWCIH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxDataInfo extends BaseEntity {

    private WxInfo wxInfo;
    private String status;
    private Integer version = 0;
    private String name;
    private LocalDateTime tokenUpdateTime = LocalDateTime.MIN;

}
