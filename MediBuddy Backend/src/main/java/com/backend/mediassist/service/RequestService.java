package com.backend.mediassist.service;

import com.backend.mediassist.model.Insurance;
import com.backend.mediassist.model.NetworkHospital;
import com.backend.mediassist.model.Request;
import com.backend.mediassist.repository.InsuranceRepository;
import com.backend.mediassist.repository.NetworkHospitalRepository;
import com.backend.mediassist.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {
    
    @Autowired
    private RequestRepository requestRepository;
    
    @Autowired
    private InsuranceRepository insuranceRepository;
    
    @Autowired
    private NetworkHospitalRepository networkHospitalRepository;
    
    // Create new claim request with hospital selection
    public Request createRequest(Long empId, Double amount, Long hospitalId) {
        // Check if user has any pending requests
        List<Request> pendingRequests = requestRepository.findByEmpIdAndStatus(empId, "PENDING");
        if (!pendingRequests.isEmpty()) {
            throw new RuntimeException("You already have a pending request. Please wait for admin approval before making another request.");
        }
        
        Request request = new Request();
        request.setEmpId(empId);
        request.setRequestAmount(amount);
        request.setHospitalId(hospitalId);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        
        return requestRepository.save(request);
    }
    
    // Get all requests (for admin)
    public List<Request> getAllRequests() {
        return requestRepository.findAllOrderByCreatedAtDesc();
    }
    
    // Get requests by employee ID
    public List<Request> getRequestsByEmpId(Long empId) {
        return requestRepository.findByEmpId(empId);
    }
    
    // Approve request with copay deduction
    public Request approveRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        
        if (request == null) {
            throw new RuntimeException("Request not found");
        }
        
        // Check if already processed
        if (!"PENDING".equals(request.getStatus())) {
            throw new RuntimeException("Request has already been processed");
        }
        
        // Get insurance and validate balance
        Insurance insurance = insuranceRepository.findByEmpId(request.getEmpId());
        if (insurance == null) {
            throw new RuntimeException("No insurance found for this employee");
        }
        
        // Get hospital and calculate copay
        NetworkHospital hospital = networkHospitalRepository.findById(request.getHospitalId()).orElse(null);
        Double copayAmount = 0.0;
        Double approvedAmount = request.getRequestAmount();
        
        if (hospital != null) {
            // Calculate copay based on hospital's copay percentage
            copayAmount = (request.getRequestAmount() * hospital.getCopayPercentage()) / 100;
            approvedAmount = request.getRequestAmount() - copayAmount;
        }
        
        // Check if user has sufficient balance for the approved amount
        if (insurance.getAmountRemaining() < approvedAmount) {
            throw new RuntimeException("Insufficient insurance balance. Available: " + 
                insurance.getAmountRemaining() + ", Required: " + approvedAmount);
        }
        
        // Update insurance amount: deduct approved amount (what insurance pays)
        Double newAmount = insurance.getAmountRemaining() - approvedAmount;
        insurance.setAmountRemaining(newAmount);
        insuranceRepository.save(insurance);
        
        // Update request
        request.setStatus("APPROVED");
        request.setApprovedAmount(approvedAmount);
        request.setCopayAmount(copayAmount);
        request.setApprovedAt(LocalDateTime.now());
        
        return requestRepository.save(request);
    }
    
    // Reject request (admin)
    public Request rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        
        if (request == null) {
            return null;
        }
        
        request.setStatus("REJECTED");
        request.setApprovedAt(LocalDateTime.now());
        return requestRepository.save(request);
    }
    
    // Filter requests by status
    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }
    
    // Filter requests by status and date range
    public List<Request> getRequestsByStatusAndDateRange(String status, LocalDateTime startDate, LocalDateTime endDate) {
        return requestRepository.findByStatusAndDateRange(status, startDate, endDate);
    }
    
    // Filter requests by date range
    public List<Request> getRequestsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return requestRepository.findByDateRange(startDate, endDate);
    }
    
    // Get filtered requests with multiple criteria
    public List<Request> getFilteredRequests(String status, LocalDateTime startDate, LocalDateTime endDate) {
        List<Request> requests = getAllRequests();
        
        // Filter by status
        if (status != null && !status.isEmpty() && !status.equals("ALL")) {
            requests = requests.stream()
                    .filter(r -> r.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
        
        // Filter by date range
        if (startDate != null && endDate != null) {
            requests = requests.stream()
                    .filter(r -> r.getCreatedAt() != null && 
                                !r.getCreatedAt().isBefore(startDate) && 
                                !r.getCreatedAt().isAfter(endDate.plusDays(1)))
                    .collect(Collectors.toList());
        }
        
        return requests;
    }
}
