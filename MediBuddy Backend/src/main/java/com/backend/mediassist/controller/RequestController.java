package com.backend.mediassist.controller;

import com.backend.mediassist.model.Request;
import com.backend.mediassist.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    
    @Autowired
    private RequestService requestService;
    
    @PostMapping("/create")
    public Request createRequest(
            @RequestParam Long empId,
            @RequestParam Double amount,
            @RequestParam Long hospitalId) {
        return requestService.createRequest(empId, amount, hospitalId);
    }
    
    @GetMapping("/all")
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }
    
    @GetMapping("/filtered")
    public List<Request> getFilteredRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return requestService.getFilteredRequests(status, startDate, endDate);
    }
    
    @GetMapping("/employee/{empId}")
    public List<Request> getRequestsByEmpId(@PathVariable Long empId) {
        return requestService.getRequestsByEmpId(empId);
    }
    
    @GetMapping("/status/{status}")
    public List<Request> getRequestsByStatus(@PathVariable String status) {
        return requestService.getRequestsByStatus(status);
    }
    
    @PutMapping("/approve/{requestId}")
    public Request approveRequest(@PathVariable Long requestId) {
        return requestService.approveRequest(requestId);
    }
    
    @PutMapping("/reject/{requestId}")
    public Request rejectRequest(@PathVariable Long requestId) {
        return requestService.rejectRequest(requestId);
    }
}
