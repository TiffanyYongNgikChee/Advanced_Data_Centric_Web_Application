import { Component,inject ,OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { GarageService } from '../../../services/garage.service';
import { ActivatedRoute,Router } from '@angular/router';
import { Vehicle } from '../../../services/interfaces';
@Component({
  selector: 'app-details',
  imports: [CommonModule],
  templateUrl: './details.component.html',
  styleUrl: './details.component.css'
})
export class DetailsComponent implements OnInit{
  private garageService = inject(GarageService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  vehicle: Vehicle | null = null;
   ngOnInit() {
     // Get the registration parameter from the route
     this.route.paramMap.subscribe(params => {
      const reg = params.get('reg');
      if (reg) {
        // Use the registration to fetch vehicle details
        this.garageService.getVehicleByReg(reg).subscribe(
          (data: Vehicle) => {
            this.vehicle = data;
            console.log('Retrieved vehicle details:', this.vehicle);
          },
          error => {
            console.error('Error fetching vehicle details:', error);
          }
        );
      }
     });
   }

   goBack() {
    this.router.navigate(['/vehicles']);
  }
}
