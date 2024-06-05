package start.capstone2.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PortfolioDatabaseResponse {
    private Long id;
    private String name;
    List<PortfolioDatabaseSchemaResponse> schemas;
    
}
