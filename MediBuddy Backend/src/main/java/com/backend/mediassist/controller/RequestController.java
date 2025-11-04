package com.backend.mediassist.controller;

import com.backend.mediassist.model.Request;
import com.backend.mediassist.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Claims & Requests", description = "APIs for managing insurance claim requests")
public class RequestController {
    
    @Autowired
    private RequestService requestService;
    
    @Operation(summary = "Create claim request", description = "Create a new claim request for reimbursement")
    @PostMapping("/create")
    public Request createRequest(
            @Parameter(description = "Employee ID") @RequestParam Long empId,
            @Parameter(description = "Claim amount") @RequestParam Double amount,
            @Parameter(description = "Hospital ID") @RequestParam Long hospitalId) {
        return requestService.createRequest(empId, amount, hospitalId);
    }
    
    @Operation(summary = "Get all requests", description = "Get all claim requests (Admin only)")
    @GetMapping("/all")
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }
    
    @Operation(summary = "Get filtered requests", description = "Filter requests by status and date range")
    @GetMapping("/filtered")
    public List<Request> getFilteredRequests(
            @Parameter(description = "Request status (PENDING/APPROVED/REJECTED)") @RequestParam(required = false) String status,
            @Parameter(description = "Start date") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return requestService.getFilteredRequests(status, startDate, endDate);
    }
    
    @Operation(summary = "Get employee requests", description = "Get all requests for a specific employee")
    @GetMapping("/employee/{empId}")
    public List<Request> getRequestsByEmpId(@Parameter(description = "Employee ID") @PathVariable Long empId) {
        return requestService.getRequestsByEmpId(empId);
    }
    
    @Operation(summary = "Get requests by status", description = "Get all requests with specific status")
    @GetMapping("/status/{status}")
    public List<Request> getRequestsByStatus(@Parameter(description = "Request status") @PathVariable String status) {
        return requestService.getRequestsByStatus(status);
    }
    
    @Operation(summary = "Approve request", description = "Approve a claim request (Admin only)")
    @PutMapping("/approve/{requestId}")
    public Request approveRequest(@Parameter(description = "Request ID") @PathVariable Long requestId) {
        return requestService.approveRequest(requestId);
    }
    
    @Operation(summary = "Reject request", description = "Reject a claim request (Admin only)")
    @PutMapping("/reject/{requestId}")
    public Request rejectRequest(@Parameter(description = "Request ID") @PathVariable Long requestId) {
        return requestService.rejectRequest(requestId);
    }
}
