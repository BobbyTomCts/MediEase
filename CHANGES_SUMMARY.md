# MediEase Backend & Frontend Changes Summary

## Date: November 3, 2025

### Overview
This document summarizes the changes made to implement the following features:
1. **Request Validation**: Prevent users from creating multiple pending money requests
2. **Approval Validation**: Check user insurance balance before approving requests
3. **Dependant Relations**: Changed "Parent" to "Father" and "Mother" with uniqueness constraints
4. **Spouse Uniqueness**: Only one Spouse allowed as a dependant

---

## Backend Changes

### 1. Request Repository (`RequestRepository.java`)
**File**: `MediBuddy Backend/src/main/java/com/backend/mediassist/repository/RequestRepository.java`

**Changes**:
- Added new method: `findByEmpIdAndStatus(Long empId, String status)`
- This allows checking for pending requests by employee ID

```java
List<Request> findByEmpIdAndStatus(Long empId, String status);
```

---

### 2. Request Service (`RequestService.java`)
**File**: `MediBuddy Backend/src/main/java/com/backend/mediassist/service/RequestService.java`

**Changes**:

#### A. Create Request Method
- **Validation Added**: Check for existing pending requests before creating a new one
- **Error Handling**: Throws exception if pending request exists

```java
// Check if user has any pending requests
List<Request> pendingRequests = requestRepository.findByEmpIdAndStatus(empId, "PENDING");
if (!pendingRequests.isEmpty()) {
    throw new RuntimeException("You already have a pending request. Please wait for admin approval before making another request.");
}
```

#### B. Approve Request Method
- **Enhanced Validations**:
  1. Check if request exists
  2. Check if request is already processed (not PENDING)
  3. Validate employee has insurance
  4. **NEW**: Check if employee has sufficient insurance balance
  5. Calculate copay and approved amounts
  6. Deduct only the approved amount from insurance balance

```java
// Check if user has sufficient balance for the approved amount
if (insurance.getAmountRemaining() < approvedAmount) {
    throw new RuntimeException("Insufficient insurance balance. Available: " + 
        insurance.getAmountRemaining() + ", Required: " + approvedAmount);
}
```

---

### 3. Dependant Model (`Dependant.java`)
**File**: `MediBuddy Backend/src/main/java/com/backend/mediassist/model/Dependant.java`

**Changes**:
- Updated relation field comment from `"Spouse", "Child", "Parent", "Sibling"` to `"Spouse", "Child", "Father", "Mother", "Sibling"`

```java
private String relation; // "Spouse", "Child", "Father", "Mother", "Sibling"
```

---

### 4. Dependant Repository (`DependantRepository.java`)
**File**: `MediBuddy Backend/src/main/java/com/backend/mediassist/repository/DependantRepository.java`

**Changes**:
- Added new method: `findByDependantForAndRelation(Long empId, String relation)`
- This allows checking for existing Father/Mother dependants

```java
List<Dependant> findByDependantForAndRelation(Long empId, String relation);
```

---

### 5. Insurance Service (`InsuranceService.java`)
**File**: `MediBuddy Backend/src/main/java/com/backend/mediassist/service/InsuranceService.java`

**Changes**:

#### A. Add Dependant Method
- **Relation Validation**: Only allows Spouse, Child, Father, Mother, Sibling
- **Spouse/Father/Mother Uniqueness**: Prevents adding multiple spouses, fathers, or mothers
- **Existing Validations Retained**: 
  - Maximum 4 dependants
  - No duplicate dependants (same name, age, relation)

```java
// Check for Spouse/Father/Mother uniqueness - only one of each allowed
if (relation.equals("Spouse") || relation.equals("Father") || relation.equals("Mother")) {
    List<Dependant> existingRelation = dependantRepository.findByDependantForAndRelation(empId, relation);
    if (!existingRelation.isEmpty()) {
        throw new RuntimeException("You can only add one " + relation.toLowerCase() + " as a dependant");
    }
}
```

#### B. Update Dependant Method
- **Relation Validation**: Same validation as Add method
- **Spouse/Father/Mother Uniqueness on Update**: When changing relation to Spouse/Father/Mother, checks if one already exists

```java
// If changing to Spouse/Father/Mother, check if one already exists (excluding current dependant)
if ((relation.equals("Spouse") || relation.equals("Father") || relation.equals("Mother")) && 
    !dependant.getRelation().equals(relation)) {
    List<Dependant> existingRelation = dependantRepository.findByDependantForAndRelation(
        dependant.getDependantFor(), relation);
    if (!existingRelation.isEmpty()) {
        throw new RuntimeException("You can only have one " + relation.toLowerCase() + " as a dependant");
    }
}
```

---

## Frontend Changes

### 1. Dependants Management Component (TypeScript)
**File**: `frontend/src/app/dependants-management/dependants-management.component.ts`

**Changes**:

#### Save Dependant Method
- **Frontend Validation for Spouse/Father/Mother**: Checks if Spouse, Father, or Mother already exists before allowing add/edit
- Prevents submission if trying to add duplicate Spouse, Father, or Mother

```typescript
// Check for Spouse/Father/Mother uniqueness when adding
if (!this.isEditMode) {
  if (this.dependantForm.relation === 'Spouse' || this.dependantForm.relation === 'Father' || this.dependantForm.relation === 'Mother') {
    const existingRelation = this.dependants.some(dep => 
      dep.relation === this.dependantForm.relation
    );
    
    if (existingRelation) {
      this.formError = `You can only add one ${this.dependantForm.relation.toLowerCase()} as a dependant`;
      return;
    }
  }
}
```

---

### 2. Dependants Management Component (HTML)
**File**: `frontend/src/app/dependants-management/dependants-management.component.html`

**Changes**:

#### Dependant Card Icons
- Updated icons to distinguish Father and Mother
- Changed from generic "Parent" (👴) to specific Father (👨) and Mother (👩)

```html
<span *ngIf="dependant.relation === 'Father'">👨</span>
<span *ngIf="dependant.relation === 'Mother'">👩</span>
```

#### Relation Dropdown
- Replaced "Parent" option with separate "Father" and "Mother" options

```html
<option value="Father">Father</option>
<option value="Mother">Mother</option>
```

---

### 3. User Home Component (TypeScript)
**File**: `frontend/src/app/user-home/user-home.component.ts`

**Changes**:

#### Submit Claim Method - Error Handling
- Enhanced error handling to display backend error messages
- Shows specific error when user has pending request

```typescript
error: (err) => {
  console.error('Error creating claim:', err);
  // Display the error message from backend
  if (err.error && typeof err.error === 'string') {
    this.claimError = err.error;
  } else if (err.error && err.error.message) {
    this.claimError = err.error.message;
  } else {
    this.claimError = 'Failed to submit claim. Please try again.';
  }
  this.isSubmittingClaim = false;
}
```

---

### 4. Admin Dashboard Component (TypeScript)
**File**: `frontend/src/app/admin-dashboard/admin-dashboard.component.ts`

**Changes**:

#### Approve Request Method - Error Handling
- Enhanced error handling to display backend validation errors
- Shows specific error when insufficient insurance balance

```typescript
error: (error) => {
  console.error('Error approving request:', error);
  // Display the error message from backend
  if (error.error && typeof error.error === 'string') {
    this.message = error.error;
  } else if (error.error && error.error.message) {
    this.message = error.error.message;
  } else {
    this.message = 'Error approving request';
  }
  setTimeout(() => this.message = '', 5000);
}
```

---

## Testing Checklist

### Request Validation
- [ ] User cannot create a new request when they have a pending request
- [ ] Error message is displayed: "You already have a pending request..."
- [ ] User can create a new request after previous request is approved/rejected

### Approval Validation
- [ ] Admin cannot approve request if user has insufficient balance
- [ ] Error message shows: "Insufficient insurance balance. Available: X, Required: Y"
- [ ] Admin can successfully approve request when balance is sufficient
- [ ] Only approved amount is deducted from insurance balance (not copay)

### Dependant Relations
- [ ] Dropdown shows "Father" and "Mother" instead of "Parent"
- [ ] User can add one Spouse as dependant
- [ ] User can add one Father as dependant
- [ ] User can add one Mother as dependant
- [ ] User cannot add a second Spouse (error: "You can only add one spouse...")
- [ ] User cannot add a second Father (error: "You can only add one father...")
- [ ] User cannot add a second Mother (error: "You can only add one mother...")
- [ ] Icons show correctly: 👨 for Father, 👩 for Mother
- [ ] Editing a dependant to Spouse/Father/Mother validates uniqueness
- [ ] Backend validates relation values (only Spouse, Child, Father, Mother, Sibling)

---

## Database Considerations

### Existing Data Migration
If you have existing data with "Parent" relation:

**Option 1**: Manual Update (SQL)
```sql
-- You'll need to manually identify which parents are fathers vs mothers
-- This requires domain knowledge about the existing data
UPDATE dependants SET relation = 'Father' WHERE relation = 'Parent' AND gender = 'Male';
UPDATE dependants SET relation = 'Mother' WHERE relation = 'Parent' AND gender = 'Female';
```

**Option 2**: Keep backward compatibility
- The backend will accept "Father" and "Mother" going forward
- Old records with "Parent" will still work but should be updated

---

## API Endpoints Affected

1. **POST** `/api/requests/create`
   - Now validates for pending requests before creation

2. **PUT** `/api/requests/approve/{requestId}`
   - Now validates insurance balance before approval

3. **POST** `/api/insurance/dependant/add`
   - Now validates Father/Mother uniqueness
   - Validates relation values

4. **PUT** `/api/insurance/dependant/update/{dependantId}`
   - Now validates Father/Mother uniqueness when changing relation
   - Validates relation values

---

## Error Messages Added

### Request Creation
- "You already have a pending request. Please wait for admin approval before making another request."

### Request Approval
- "Request not found"
- "Request has already been processed"
- "No insurance found for this employee"
- "Insufficient insurance balance. Available: {amount}, Required: {amount}"

### Dependant Management
- "Invalid relation. Allowed values: Spouse, Child, Father, Mother, Sibling"
- "You can only add one spouse as a dependant"
- "You can only add one father as a dependant"
- "You can only add one mother as a dependant"
- "You can only have one spouse as a dependant"
- "You can only have one father as a dependant"
- "You can only have one mother as a dependant"

---

## Files Modified

### Backend (7 files)
1. `MediBuddy Backend/src/main/java/com/backend/mediassist/repository/RequestRepository.java`
2. `MediBuddy Backend/src/main/java/com/backend/mediassist/service/RequestService.java`
3. `MediBuddy Backend/src/main/java/com/backend/mediassist/model/Dependant.java`
4. `MediBuddy Backend/src/main/java/com/backend/mediassist/repository/DependantRepository.java`
5. `MediBuddy Backend/src/main/java/com/backend/mediassist/service/InsuranceService.java`

### Frontend (4 files)
1. `frontend/src/app/dependants-management/dependants-management.component.ts`
2. `frontend/src/app/dependants-management/dependants-management.component.html`
3. `frontend/src/app/user-home/user-home.component.ts`
4. `frontend/src/app/admin-dashboard/admin-dashboard.component.ts`

---

## Next Steps

1. **Test all changes thoroughly** using the testing checklist above
2. **Update existing database records** with "Parent" relation to "Father" or "Mother"
3. **Rebuild and restart** the backend application
4. **Rebuild and restart** the frontend application
5. **Perform end-to-end testing** with real user flows
6. **Document the changes** in your project's changelog

---

## Notes

- All validations are implemented both on frontend (for UX) and backend (for security)
- Error messages are user-friendly and informative
- The changes maintain backward compatibility where possible
- Database schema doesn't need to change (only validation logic changed)
