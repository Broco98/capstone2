package start.capstone2.controller.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.capstone2.dto.ResponseResult;
import start.capstone2.dto.portfolio.PortfolioDatabaseRequest;
import start.capstone2.dto.portfolio.PortfolioDatabaseResponse;
import start.capstone2.dto.portfolio.PortfolioDatabaseSchemaRequest;
import start.capstone2.service.portfolio.PortfolioDatabaseSchemaService;
import start.capstone2.service.portfolio.PortfolioDatabaseService;

import java.util.List;

@Tag(name = "Portfolio Database Schema Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio-database")
public class PortfolioDatabaseSchemaController {

    private final PortfolioDatabaseSchemaService schemaService;

    @Operation(summary = "create portfolio database schema")
    @PostMapping("/{databaseId}/schema")
    public Long createPortfolioDatabase(HttpServletRequest servletRequest, @PathVariable Long databaseId, PortfolioDatabaseSchemaRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        return schemaService.createPortfolioDatabaseSchema(userId, databaseId, request);
    }

    @Operation(summary = "update portfolio database schema")
    @PutMapping("/{databaseId}/schema/{schemaId}")
    public void updatePortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long databaseId, @PathVariable Long schemaId, PortfolioDatabaseSchemaRequest request) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        schemaService.updatePortfolioDatabaseSchema(userId, databaseId, schemaId, request);
    }

    @Operation(summary = "delete portfolio database schema")
    @DeleteMapping("/{databaseId}/schema/{schemaId}")
    public void deletePortfolioPortfolioDesign(HttpServletRequest servletRequest, @PathVariable Long databaseId, @PathVariable Long schemaId) {
        Long userId = (Long) servletRequest.getAttribute("userId");
        schemaService.deletePortfolioDatabaseSchema(userId, databaseId, schemaId);
    }

}
