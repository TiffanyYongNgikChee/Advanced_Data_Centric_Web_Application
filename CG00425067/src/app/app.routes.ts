import { Routes } from '@angular/router';
import { DetailsComponent } from './components/details/details.component';
import { VehicleComponent } from './components/vehicle/vehicle.component';
import { ErrorComponent } from './components/error/error.component';
import { CreateComponent } from './components/create/create.component';

export const routes: Routes = [
    { path: 'vehicles', component: VehicleComponent },
    { path: 'vehicleDetails/:reg', component: DetailsComponent },
    { path: 'error', component: ErrorComponent },
    { path: 'create', component:CreateComponent},
    { path: '', redirectTo: '/vehicles', pathMatch: 'full' },
    { path: '**', redirectTo: '/error' }
    
];
