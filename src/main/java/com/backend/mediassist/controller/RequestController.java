package com.backend.mediassist.controller;

import com.backend.mediassist.model.Request;
import com.backend.mediassist.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    
    @Autowired
    private RequestService requestService;
    
    // Create new claim request
    @PostMapping("/create")
    public Request createRequest(@RequestParam Long empId, @RequestParam Double amount) {
        return requestService.createRequest(empId, amount);
    }
    
    // Get all requests (for admin)
    @GetMapping("/all")
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }
    
    // Get requests by employee ID
    @GetMapping("/employee/{empId}")
    public List<Request> getRequestsByEmpId(@PathVariable Long empId) {
        return requestService.getRequestsByEmpId(empId);
    }
    
    // Approve request (admin)
    @PutMapping("/approve/{requestId}")
    public Request approveRequest(@PathVariable Long requestId) {
        return requestService.approveRequest(requestId);
    }
    
    // Reject request (admin)
    @PutMapping("/reject/{requestId}")
    public Request rejectRequest(@PathVariable Long requestId) {
        return requestService.rejectRequest(requestId);
    }
}
