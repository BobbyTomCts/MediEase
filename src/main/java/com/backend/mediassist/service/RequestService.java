package com.backend.mediassist.service;

import com.backend.mediassist.model.Insurance;
import com.backend.mediassist.model.Request;
import com.backend.mediassist.repository.InsuranceRepository;
import com.backend.mediassist.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestService {
    
    @Autowired
    private RequestRepository requestRepository;
    
    @Autowired
    private InsuranceRepository insuranceRepository;
    
    // Create new claim request
    public Request createRequest(Long empId, Double amount) {
        Request request = new Request();
        request.setEmpId(empId);
        request.setRequestAmount(amount);
        request.setStatus("PENDING");
        
        return requestRepository.save(request);
    }
    
    // Get all requests (for admin)
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }
    
    // Get requests by employee ID
    public List<Request> getRequestsByEmpId(Long empId) {
        return requestRepository.findByEmpId(empId);
    }
    
    // Approve request (admin)
    public Request approveRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        
        if (request == null) {
            return null;
        }
        
        // Update insurance amount
        Insurance insurance = insuranceRepository.findByEmpId(request.getEmpId());
        if (insurance != null) {
            Double newAmount = insurance.getAmountRemaining() - request.getRequestAmount();
            insurance.setAmountRemaining(newAmount);
            insuranceRepository.save(insurance);
        }
        
        request.setStatus("APPROVED");
        return requestRepository.save(request);
    }
    
    // Reject request (admin)
    public Request rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        
        if (request == null) {
            return null;
        }
        
        request.setStatus("REJECTED");
        return requestRepository.save(request);
    }
}
