import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GarageService } from '../../../services/garage.service';
import { Vehicle } from '../../../services/interfaces';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create.component.html',
  styleUrl: './create.component.css'
})
export class CreateComponent {
// Inject required services
private garageService = inject(GarageService);
private fb = inject(FormBuilder);

// Form group for vehicle creation
vehicleForm: FormGroup;

// Status flags
isSubmitting: boolean = false;
submitError: boolean = false;
errorMessage: string = '';
submitSuccess: boolean = false;

constructor() {
  // Initialize form with validation
  this.vehicleForm = this.fb.group({
    reg: ['', [Validators.required]],
    make: ['', [Validators.required]],
    model: ['', [Validators.required]]
  });
}

// Submit form handler
onSubmit() {
  // Reset status flags
  this.submitError = false;
  this.errorMessage = '';
  this.submitSuccess = false;
  
  if (this.vehicleForm.valid) {
    this.isSubmitting = true;
    
    // Create vehicle data transfer object
    const vehicleData = this.vehicleForm.value;
    
    // Call service to create vehicle
    this.garageService.createVehicle(vehicleData).subscribe({
      next: (response) => {
        console.log('Vehicle created successfully:', response);
        this.isSubmitting = false;
        this.submitSuccess = true;
        this.vehicleForm.reset(); // Reset form after successful submission
        
        // Redirect to vehicle list after 2 seconds
        setTimeout(() => {
          window.location.href = '/vehicles';
        }, 2000);
      },
      error: (error) => {
        console.error('Error creating vehicle:', error);
        this.isSubmitting = false;
        this.submitError = true;
        this.errorMessage = error.error || 'Failed to create vehicle. Please try again.';
      }
    });
  } else {
    // Mark all fields as touched to show validation errors
    Object.keys(this.vehicleForm.controls).forEach(key => {
      this.vehicleForm.controls[key].markAsTouched();
    });
  }
}

// Navigation back to vehicle list
goBack() {
  window.location.href = '/vehicles';
}
}