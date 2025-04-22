import { Component,inject ,OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { GarageService } from '../../../services/garage.service';
import { ActivatedRoute,Router } from '@angular/router';
import { Vehicle } from '../../../services/interfaces';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-details',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './details.component.html',
  styleUrl: './details.component.css'

})
export class DetailsComponent implements OnInit{
  // Inject dependencies
  private garageService = inject(GarageService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  public reg: string = ''; // Vehicle registration from route
  public isLoading: boolean = false;  // Flag to show loading state
  
  // Store error state and message.
  public error: boolean = false;
  public errorMessage: string = '';

  vehicle: Vehicle | null = null; // Fetched vehicle object
  vehicleForm!: FormGroup; // Form group for UI binding
  isSubmitting = false; // Flag to indicate submission
  submitMessage = ''; // Success/error message after submitting
  currentMechanicId: string = ''; // ID of the mechanic being assigned

   ngOnInit() {
    // Initialize form
    this.initForm();
     // Fetch vehicle registration from route and retrieve details
     this.route.paramMap.subscribe(params => {
      const reg = params.get('reg');
      if (reg) {
        this.reg = reg;
        // Use the registration to fetch vehicle details
        this.garageService.getVehicleByReg(reg).subscribe(
          (data: Vehicle) => {
            this.vehicle = data;
            console.log('Retrieved vehicle details:', this.vehicle);
            this.populateForm(data);

            // Set loading to false
            this.isLoading = false;
             
             // Initialize the current mechanic ID
             this.currentMechanicId = data.mechanic.mid;
          },
          // Redirect based on error type
          error => {
            this.isLoading = false;
            console.error('Error fetching vehicle details:', error);

            if (error.status === 404) {
              this.router.navigate(['/error'], {
                queryParams: {
                  message: `Vehicle with registration "${this.reg}" was not found. It may have been deleted.`
                }
              });
            } else if (error.status === 0) {
              this.router.navigate(['/error'], {
                queryParams: {
                  message: 'Server is unreachable. Please try again later.'
                }
              });
            } else {
              // Dynamic error message for server-side/internal errors (like 500)
              const apiUrl = `http://localhost:4200/api/vehicle/${this.reg}`;
              this.router.navigate(['/error'], {
                queryParams: {
                  message: `Status: ${error.status}\nMessage: Http failure response for ${apiUrl}: ${error.status} ${error.statusText || 'Internal Server Error'}`
                }
              });
            }
          }
        );
      }
     });
   }
   // Initialize the form structure and validation
   initForm() {
    this.vehicleForm = this.fb.group({
      reg: [{value: '', disabled: true}],
      make: [{value: '', disabled: true}],
      model: [{value: '', disabled: true}],
      mechanicId: ['', Validators.required],
      mechanicName: [{value: '', disabled: true}],
      garageLocation: [{value: '', disabled: true}],
    });
    
    // Keep track of mechanic ID entered by the user
    this.vehicleForm.get('mechanicId')?.valueChanges.subscribe(value => {
      this.currentMechanicId = value;
    });
  }
  // Populate form with vehicle data
  populateForm(vehicle: Vehicle) {
    this.vehicleForm.patchValue({
      reg: vehicle.reg,
      make: vehicle.make,
      model: vehicle.model,
      mechanicId: vehicle.mechanic?.mid || 'Not assigned',
      mechanicName: vehicle.mechanic?.name|| 'Not assigned',
      garageLocation: vehicle.mechanic?.garage?.location || 'Not assigned',
    });
  }

  // Called when user submits updated mechanic ID
  updateMechanic() {
    console.log('Current Mechanic ID:', this.currentMechanicId);
    // Validate mechanic ID and vehicle exist
    if (!this.currentMechanicId || !this.vehicle) {
      this.submitMessage = 'Error: Mechanic ID and vehicle registration are required';
      return;
    }
    
    this.isSubmitting = true;
    this.submitMessage = '';
    
    // Call service to update vehicle mechanic
    this.garageService.updateVehicleMechanic(this.vehicle.reg, this.currentMechanicId)
      .subscribe(
        response => {
          this.isSubmitting = false;
          this.submitMessage = 'Vehicle mechanic updated successfully';
          console.log('Update successful:', response);
          
          // Update the local vehicle object to reflect changes
          if (this.vehicle) {
            this.vehicle.mechanic.mid = this.currentMechanicId;
          }
        },
        error => {
          this.isSubmitting = false;
          console.error('Error updating vehicle:', error);
          // Redirect to error page with specific messages
          if (error.status === 404 || error.status === 400) {
            this.router.navigate(['/error'], {
              queryParams: {
                message: `Mechanic with ID "${this.currentMechanicId}" does not exist.`
              }
            });
          } else if (error.status === 0) {
            this.router.navigate(['/error'], {
              queryParams: {
                message: 'Server connection lost. Please try again later.'
              }
            });
          } else {
            this.router.navigate(['/error'], {
              queryParams: {
                message: `Status: 500 Message: Mechanic ${this.currentMechanicId} doesn't exist`
              }
            });
          }
        }
      );
  }
   // Navigate back to vehicles list
   goBack() {
    this.router.navigate(['/vehicles']);
  }
}
