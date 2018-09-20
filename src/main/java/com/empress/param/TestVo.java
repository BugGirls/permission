package com.empress.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/7/5
 */
@Data
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull(message = "ID不能为空")
    @Max(value = 10, message = "ID不能大于10")
    @Min(value = 0, message = "ID至少大于等于0")
    private Integer id;

    @NotEmpty
    private List<String> str;
}
