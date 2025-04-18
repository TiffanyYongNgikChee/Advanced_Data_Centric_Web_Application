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
 
   ngOnInit() {
     // Get vehicle details from service
     this.garageService.getAllVehicles().subscribe((data: any) => {
       // Assign the data to the vehicles array.
       this.vehicles = data;
 
       // debug
       console.log('Vehicle Details:', this.vehicles);
     });
   }
}
