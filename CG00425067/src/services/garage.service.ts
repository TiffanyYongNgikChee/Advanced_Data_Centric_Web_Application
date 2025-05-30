// import necessary packages
import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Vehicle } from './interfaces';

// Get API URL from environment.ts
const GARAGE_API_BASE = environment.garage_api_base;

@Injectable({
  providedIn: 'root'
})

export class GarageService {
  // inject HttpClient
  private httpClient = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api';  
  constructor(private http: HttpClient) { }

  // Returns all vehicles
  getAllVehicles(): Observable<Vehicle[]> {
    let url = `${GARAGE_API_BASE}/vehicle/all`;
    
    // debug
    console.log('Making API request to:', url);

    return this.httpClient.get<Vehicle[]>(url);
  }
  
  // Get a specific event by its ID
  getVehiclesByMake(make: string): Observable<any> {
    
    const url = `${GARAGE_API_BASE}/vehicle?make=${make}`;
    
    // debug
    console.log('Making API request to:', url);

    return this.httpClient.get<any>(url);
  }

   // Get vehicle by reg.
   getVehicleByReg(reg: string): Observable<Vehicle> {
    // URL with the reg parameter.
    const url = `${GARAGE_API_BASE}/vehicle/one?reg=${reg}`;
    
    // debug
    console.log('Making API request to:', url);

    return this.httpClient.get<Vehicle>(url);
  }

  // Update vehicle mechanic
  updateVehicleMechanic(reg: string, mid: string): Observable<any> {
    const url = `${GARAGE_API_BASE}/vehicle/${reg}`;
    const body = { mid };
    
    // debug
    // console.log('Making API PUT request to:', url, 'with body:', body);

    return this.httpClient.put<any>(url, body);
  }

  // Create new vehicle
  createVehicle(vehicleData: any): Observable<Vehicle> {
    return this.http.post<Vehicle>(`${GARAGE_API_BASE}/vehicle`, vehicleData);
  }
}