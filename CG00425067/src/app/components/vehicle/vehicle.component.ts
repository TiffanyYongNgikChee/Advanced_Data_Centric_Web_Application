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
  private garageService = inject(GarageService);

  public vehicles: Vehicle[] = [];
  public isLoading: boolean = true;
   
   // Property to store error messages
   public error: boolean = false;
   public errorMessage: string = '';
 
   ngOnInit() {
     // Get vehicle details from service
     this.garageService.getAllVehicles().subscribe({
      next: (data: any) => {
        // Assign the data to the vehicles array.
        this.vehicles = data;
        this.error = false;
        this.errorMessage = '';
        this.isLoading = false;
 
       // debug
       console.log('Vehicle Details:', this.vehicles);
       },
       error: (error) => {
         console.error('Error fetching vehicles:', error);
         this.error = true;
         this.isLoading = false;
         this.errorMessage = 'Message: Http failure response for http://localhost:4200/api/vehicle : 500 Internal Server Error';
         console.log(this.error);
       }
     });
   }

   updateVehicle(reg: String) {
      window.location.href = `/vehicleDetails/${reg}`;
   }
}
