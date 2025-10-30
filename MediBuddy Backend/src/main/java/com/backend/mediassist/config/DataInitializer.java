package com.backend.mediassist.config;

import com.backend.mediassist.model.NetworkHospital;
import com.backend.mediassist.repository.NetworkHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private NetworkHospitalRepository networkHospitalRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Check if hospitals already exist
        if (networkHospitalRepository.count() == 0) {
            initializeNetworkHospitals();
        }
    }
    
    private void initializeNetworkHospitals() {
        // Create 20 hospitals across India with varying copay percentages
        // Premium hospitals: 10-15% copay
        // Standard hospitals: 15-20% copay
        // Government/Budget hospitals: 5-10% copay
        
        networkHospitalRepository.save(new NetworkHospital(
            "Apollo Hospital", "Chennai", "Tamil Nadu",
            "21, Greams Lane, Off Greams Road", "044-28296000",
            "Cardiology, Neurology, Oncology, Orthopedics",
            10.0  // 10% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Fortis Hospital", "Mumbai", "Maharashtra",
            "Mulund Goregaon Link Road", "022-67914444",
            "Cardiology, Gastroenterology, Nephrology, Urology",
            12.0  // 12% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Max Super Speciality Hospital", "New Delhi", "Delhi",
            "Press Enclave Road, Saket", "011-26515050",
            "Oncology, Neurosurgery, Cardiology, Orthopedics",
            15.0  // 15% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Manipal Hospital", "Bangalore", "Karnataka",
            "#98, HAL Airport Road", "080-25023456",
            "Neurology, Cardiology, Oncology, Pediatrics",
            12.0  // 12% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "AIIMS", "New Delhi", "Delhi",
            "Ansari Nagar, New Delhi", "011-26588500",
            "Multi-speciality, Research, Emergency Care",
            5.0   // 5% copay (Government hospital)
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Medanta - The Medicity", "Gurugram", "Haryana",
            "Sector 38, Gurugram", "0124-4141414",
            "Cardiology, Neurosciences, Oncology, Orthopedics",
            15.0  // 15% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Narayana Health", "Bangalore", "Karnataka",
            "258/A, Bommasandra Industrial Area", "080-71222222",
            "Cardiac Sciences, Neurosciences, Oncology",
            10.0  // 10% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Kokilaben Dhirubhai Ambani Hospital", "Mumbai", "Maharashtra",
            "Four Bungalows, Andheri West", "022-30999999",
            "Oncology, Neurosciences, Cardiology, Orthopedics",
            18.0  // 18% copay (Premium)
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Ruby Hall Clinic", "Pune", "Maharashtra",
            "40, Sassoon Road", "020-66455000",
            "Cardiology, Neurology, Orthopedics, Gastroenterology",
            12.0  // 12% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Sankara Nethralaya", "Chennai", "Tamil Nadu",
            "18, College Road, Nungambakkam", "044-28271616",
            "Ophthalmology, Eye Care",
            8.0   // 8% copay (Specialty hospital)
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Lilavati Hospital", "Mumbai", "Maharashtra",
            "A-791, Bandra Reclamation", "022-26445000",
            "Cardiology, Oncology, Neurology, Orthopedics",
            15.0  // 15% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Artemis Hospital", "Gurugram", "Haryana",
            "Sector 51, Gurugram", "0124-4511111",
            "Cardiology, Neurosciences, Oncology, Orthopedics",
            14.0  // 14% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "KIMS Hospital", "Hyderabad", "Telangana",
            "1-112/86, Survey No 55/EE, Kondapur", "040-44885000",
            "Cardiology, Neurology, Oncology, Gastroenterology",
            11.0  // 11% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Sir Ganga Ram Hospital", "New Delhi", "Delhi",
            "Rajinder Nagar, New Delhi", "011-25750000",
            "Multi-speciality, Emergency Care, Surgery",
            10.0  // 10% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Christian Medical College", "Vellore", "Tamil Nadu",
            "Ida Scudder Road, Vellore", "0416-2282020",
            "Multi-speciality, Research, Emergency Care",
            7.0   // 7% copay (Trust hospital)
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Tata Memorial Hospital", "Mumbai", "Maharashtra",
            "Dr Ernest Borges Road, Parel", "022-24177000",
            "Oncology, Cancer Treatment, Research",
            5.0   // 5% copay (Government cancer center)
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Breach Candy Hospital", "Mumbai", "Maharashtra",
            "60-A, Bhulabhai Desai Road", "022-23667888",
            "Cardiology, Neurology, Orthopedics, Maternity",
            16.0  // 16% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Continental Hospital", "Hyderabad", "Telangana",
            "Plot No. 3, Road No. 2, IT & Financial District, Gachibowli", "040-67000000",
            "Cardiology, Neurology, Oncology, Transplant",
            13.0  // 13% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "P.D. Hinduja Hospital", "Mumbai", "Maharashtra",
            "Veer Savarkar Marg, Mahim", "022-24447000",
            "Cardiology, Neurology, Orthopedics, Nephrology",
            14.0  // 14% copay
        ));
        
        networkHospitalRepository.save(new NetworkHospital(
            "Columbia Asia Hospital", "Bangalore", "Karnataka",
            "Kirloskar Business Park, Hebbal", "080-66589000",
            "Cardiology, Neurology, Orthopedics, Pediatrics",
            11.0  // 11% copay
        ));
        
        System.out.println("✅ 20 Network Hospitals initialized successfully with copay percentages!");
    }
}
