package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioDatabaseSchemaRequest {

    private String schema;
    private String description;

}
