package org.newcih.wxapi.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author NEWCIH
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2850372621446477130L;
    protected Integer id;
    protected LocalDateTime createTime;
    protected String creator;
    protected LocalDateTime updateTime;
    protected String updator;

}
