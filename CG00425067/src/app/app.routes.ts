import { Routes } from '@angular/router';
import { DetailsComponent } from './components/details/details.component';
import { VehicleComponent } from './components/vehicle/vehicle.component';
import { ErrorComponent } from './components/error/error.component';

export const routes: Routes = [
    { path: 'vehicles', component: VehicleComponent },
    { path: 'vehicleDetails/:reg', component: DetailsComponent },
    { path: 'error', component: ErrorComponent },
    { path: '', redirectTo: '/vehicles', pathMatch: 'full' },
    { path: '**', redirectTo: '/error' }
];
