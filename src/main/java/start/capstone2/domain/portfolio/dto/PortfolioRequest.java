package start.capstone2.domain.portfolio.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import start.capstone2.domain.portfolio.*;
import start.capstone2.domain.user.User;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioRequest {

    // private User user;

    private String title;
    private String startDate;
    private String endDate;
    private Integer contribution;
    private String purpose;
    private String content;

    // TODO: 일단 이미지 X
//    private PortfolioCardImage cardImage;
//    private List<PortfolioImage> images;
    private List<Long> shardGroupIds = new ArrayList<>();

}
