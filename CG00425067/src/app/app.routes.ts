import { Routes } from '@angular/router';
import { DetailsComponent } from './components/details/details.component';
import { VehicleComponent } from './components/vehicle/vehicle.component';

export const routes: Routes = [
    { path: 'vehicles', component: VehicleComponent },
    { path: 'vehicleDetails/:reg', component: DetailsComponent },
    { path: '', redirectTo: '/vehicles', pathMatch: 'full' },
    { path: '**', redirectTo: '/vehicles' }
];
