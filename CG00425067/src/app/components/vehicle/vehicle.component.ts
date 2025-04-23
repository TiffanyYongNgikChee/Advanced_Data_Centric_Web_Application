import { Component, inject } from '@angular/core';
 import { CommonModule } from '@angular/common';
 import { GarageService } from '../../../services/garage.service';
 import { Vehicle } from '../../../services/interfaces';

@Component({
  selector: 'app-vehicle',
  standalone:true,
  imports: [CommonModule],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.css'
})
export class VehicleComponent {
  // Inject the garage service to communicate with backend APIs
  private garageService = inject(GarageService);
  // Array to hold the list of vehicles retrieved from the backend
  public vehicles: Vehicle[] = [];
  // Loading state for the component
  public isLoading: boolean = true;
   
  // Error handling state and message
  public error: boolean = false;
  public errorMessage: string = '';

  // Lifecycle hook - runs after component is initialized
   ngOnInit() {
     // Fetch all vehicles from the backend
     this.garageService.getAllVehicles().subscribe({
      // Success response handler
      next: (data: any) => {
        // Assign the retrieved data to local state
        this.vehicles = data;
        // Reset error states
        this.error = false;
        this.errorMessage = '';
        this.isLoading = false;
 
       // Debug: log the fetched vehicles
       console.log('Vehicle Details:', this.vehicles);
       },
       // Error response handler
       error: (error) => {
        // Log the error and update the error state/message
         console.error('Error fetching vehicles:', error);
         this.error = true;
         this.isLoading = false;
         // Hardcoded error message (you could enhance this for more dynamic handling)
         this.errorMessage = 'Message: Http failure response for http://localhost:4200/api/vehicle : 500 Internal Server Error';
         console.log(this.error);
       }
     });
   }
   // Method to redirect user to the vehicle details page based on selected vehicle's registration
   updateVehicle(reg: String) {
      window.location.href = `/vehicleDetails/${reg}`;
   }
   // Method to navigate to vehicle creation page
    createNewVehicle() {
      console.log('Navigating to create-vehicle');
      window.location.href = '/create';
    }
}
