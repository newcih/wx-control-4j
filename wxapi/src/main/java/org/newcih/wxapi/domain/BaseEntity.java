package org.newcih.wxapi.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author NEWCIH
 */
@Data
public abstract class BaseEntity {

    protected Integer id;
    protected LocalDateTime createTime;
    protected String creator;
    protected LocalDateTime updateTime;
    protected String updator;

}
