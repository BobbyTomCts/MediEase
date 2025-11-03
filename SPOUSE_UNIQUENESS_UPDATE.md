# Spouse Uniqueness Constraint Update

## Date: November 3, 2025

### Overview
Added uniqueness constraint for Spouse dependants - users can now only add ONE spouse as a dependant, similar to Father and Mother.

---

## Changes Made

### Backend

#### InsuranceService.java - Add Dependant Method
**Before:**
```java
// Check for Father/Mother uniqueness - only one of each allowed
if (relation.equals("Father") || relation.equals("Mother")) {
    // validation code
}
```

**After:**
```java
// Check for Spouse/Father/Mother uniqueness - only one of each allowed
if (relation.equals("Spouse") || relation.equals("Father") || relation.equals("Mother")) {
    List<Dependant> existingRelation = dependantRepository.findByDependantForAndRelation(empId, relation);
    if (!existingRelation.isEmpty()) {
        throw new RuntimeException("You can only add one " + relation.toLowerCase() + " as a dependant");
    }
}
```

#### InsuranceService.java - Update Dependant Method
**Before:**
```java
// If changing to Father or Mother, check if one already exists
if ((relation.equals("Father") || relation.equals("Mother")) && 
    !dependant.getRelation().equals(relation)) {
    // validation code
}
```

**After:**
```java
// If changing to Spouse/Father/Mother, check if one already exists
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

### Frontend

#### dependants-management.component.ts - Save Dependant Method
**Before:**
```typescript
if (this.dependantForm.relation === 'Father' || this.dependantForm.relation === 'Mother') {
    // validation code
}
```

**After:**
```typescript
if (this.dependantForm.relation === 'Spouse' || this.dependantForm.relation === 'Father' || this.dependantForm.relation === 'Mother') {
    const existingRelation = this.dependants.some(dep => 
        dep.relation === this.dependantForm.relation
    );
    
    if (existingRelation) {
        this.formError = `You can only add one ${this.dependantForm.relation.toLowerCase()} as a dependant`;
        return;
    }
}
```

Same update applied to the edit mode validation as well.

---

## Error Messages

### New Error Messages
- "You can only add one spouse as a dependant"
- "You can only have one spouse as a dependant"

---

## Testing

### Test Cases
1. ✅ User can add one spouse as a dependant
2. ✅ User cannot add a second spouse
3. ✅ Error message displays: "You can only add one spouse as a dependant"
4. ✅ When editing a dependant, changing relation to Spouse validates uniqueness
5. ✅ Backend validates and rejects duplicate spouse attempts
6. ✅ Frontend validates and prevents duplicate spouse attempts

---

## Files Modified

1. **Backend**: `MediBuddy Backend/src/main/java/com/backend/mediassist/service/InsuranceService.java`
2. **Frontend**: `frontend/src/app/dependants-management/dependants-management.component.ts`
3. **Documentation**: `CHANGES_SUMMARY.md`

---

## Uniqueness Rules Summary

| Relation | Max Allowed | Validation |
|----------|-------------|------------|
| Spouse   | 1           | ✅ Enforced |
| Father   | 1           | ✅ Enforced |
| Mother   | 1           | ✅ Enforced |
| Child    | Multiple    | ❌ Not enforced |
| Sibling  | Multiple    | ❌ Not enforced |

**Total Maximum Dependants**: 4 (any combination)

---

## Example Scenarios

### Valid Combinations
1. 1 Spouse + 1 Father + 1 Mother + 1 Child = 4 dependants ✅
2. 1 Spouse + 2 Children + 1 Sibling = 4 dependants ✅
3. 1 Father + 1 Mother + 2 Children = 4 dependants ✅

### Invalid Combinations
1. 2 Spouses + 1 Father + 1 Mother = ❌ Error: "You can only add one spouse..."
2. 1 Spouse + 2 Fathers + 1 Child = ❌ Error: "You can only add one father..."
3. 1 Spouse + 1 Father + 2 Mothers = ❌ Error: "You can only add one mother..."

---

## Notes

- This update makes the application logic more realistic
- In real-world scenarios, a person typically has only one spouse
- The validation prevents data inconsistencies
- Both frontend and backend validations ensure security and UX
